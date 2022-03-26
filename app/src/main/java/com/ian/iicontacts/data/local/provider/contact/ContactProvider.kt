package com.ian.iicontacts.data.local.provider.contact

import android.content.*
import android.net.Uri
import android.provider.ContactsContract.*
import com.ian.iicontacts.IIContactsApp
import com.ian.iicontacts.domain.model.Contact
import kotlinx.coroutines.*


class ContactContentProvider(
    private val appContext: Context = IIContactsApp.instanceLazy,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getContacts(offset: Int, loadSize: Int): List<Contact> = withContext(ioDispatcher) {
        val list = mutableListOf<Contact>()
        val cursor = appContext.contentResolver.query(
            Contacts.CONTENT_URI,
            arrayOf(
                Contacts._ID,
                Contacts.HAS_PHONE_NUMBER,
                Contacts.DISPLAY_NAME,
            ),
            null,
            null,
            "${Contacts.DISPLAY_NAME} ASC LIMIT $loadSize OFFSET $offset",
        ) ?: throw IllegalStateException("cannot initiate contact's cursor")

        val idIndex = cursor.getColumnIndex(Contacts._ID)
        val displayNameIndex = cursor.getColumnIndex(Contacts.DISPLAY_NAME)
        val hasPhoneNumberIndex = cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER)

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                list.addAll(
                    getContactDetails(
                        contactId = cursor.getString(idIndex),
                        name = cursor.getString(displayNameIndex),
                        hasPhoneNumber = cursor.getInt(hasPhoneNumberIndex) > 0
                    )
                )
            }
            cursor.close()
        }
        return@withContext list
    }

    private suspend fun getContactDetails(
        contactId: String,
        name: String,
        hasPhoneNumber: Boolean
    ) = withContext(ioDispatcher) {
        val list = mutableListOf<Contact>()

        val person = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId.toLong())
        val photoURI = Uri.withAppendedPath(person, Contacts.Photo.CONTENT_DIRECTORY)

        if (hasPhoneNumber) {
            val cursorInfo = appContext.contentResolver.query(
                CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    CommonDataKinds.Phone.DISPLAY_NAME,
                    CommonDataKinds.Phone.NUMBER,
                ),
                CommonDataKinds.Phone.CONTACT_ID + " = ?",
                arrayOf(contactId),
                null
            ) ?: throw IllegalStateException("Failed to load the phone cursor")

            val displayNameIndex = cursorInfo.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberIndex = cursorInfo.getColumnIndex(CommonDataKinds.Phone.NUMBER)

            while (cursorInfo.moveToNext()) {
                list.add(
                    Contact(
                        id = contactId,
                        name = cursorInfo.getString(displayNameIndex),
                        phoneNumber = cursorInfo.getString(phoneNumberIndex),
                        photoURI = photoURI,
                    )
                )
            }
            cursorInfo.close()
        } else {
            list.add(
                Contact(
                    id = contactId,
                    name = name,
                    phoneNumber = "Nothing",
                    photoURI = photoURI,
                )
            )
        }

        return@withContext list
    }
}