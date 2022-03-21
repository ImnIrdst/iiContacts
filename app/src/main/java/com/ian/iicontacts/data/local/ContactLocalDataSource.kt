package com.ian.iicontacts.data.local

import android.content.*
import android.graphics.*
import android.net.Uri
import android.provider.ContactsContract.*
import com.ian.iicontacts.IIContactsApp
import com.ian.iicontacts.domain.model.Contact
import kotlinx.coroutines.*


class ContactLocalDataSource(
    private val appContext: Context = IIContactsApp.instance!!.applicationContext,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getContacts(offset: Int, loadSize: Int) = withContext(ioDispatcher) {
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
        val hasPhoneNumbeIndex = cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER)

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                list.addAll(
                    getContactDetails(
                        contactId = cursor.getString(idIndex),
                        name = cursor.getString(displayNameIndex),
                        hasPhoneNumber = cursor.getInt(hasPhoneNumbeIndex) > 0
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

        val inputStream = Contacts.openContactPhotoInputStream(
            appContext.contentResolver,
            ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId.toLong())
        )

        val person = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId.toLong())
        val pURI = Uri.withAppendedPath(person, Contacts.Photo.CONTENT_DIRECTORY)
        val photo: Bitmap? = inputStream?.let { BitmapFactory.decodeStream(it) }

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
                        contactId,
                        cursorInfo.getString(displayNameIndex),
                        cursorInfo.getString(phoneNumberIndex),
                        photo,
                        pURI,
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
                    photo = photo,
                    photoURI = pURI,
                )
            )
        }

        return@withContext list
    }
}