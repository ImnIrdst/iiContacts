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

    suspend fun getContacts() = withContext(ioDispatcher) {

        val list = mutableListOf<Contact>()
        val cursor = appContext.contentResolver.query(
            Contacts.CONTENT_URI,
            null,
            null,
            null,
            "${Contacts.DISPLAY_NAME} ASC",
        ) ?: throw IllegalStateException("cannot initiate contact's cursor")

        val idColumnIndex = cursor.getColumnIndex(Contacts._ID)
        val hasPhoneNumberColumnIndexIndex = cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER)

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val contactId = cursor.getString(idColumnIndex)
                if (cursor.getInt(hasPhoneNumberColumnIndexIndex) > 0) {
                    list.addAll(getContactDetails(contactId))
                }
            }
            cursor.close()
        }
        return@withContext list
    }

    private suspend fun getContactDetails(contactId: String) = withContext(ioDispatcher) {
        val list = mutableListOf<Contact>()

        val cursorInfo = appContext.contentResolver.query(
            CommonDataKinds.Phone.CONTENT_URI,
            null,
            CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(contactId),
            null
        ) ?: throw IllegalStateException("Failed to load the phone cursor")

        val displayNameColumnIndex = cursorInfo.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME)
        val phoneNumberColumnsIndex = cursorInfo.getColumnIndex(CommonDataKinds.Phone.NUMBER)

        val inputStream = Contacts.openContactPhotoInputStream(
            appContext.contentResolver,
            ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId.toLong())
        )

        val person = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId.toLong())
        val pURI = Uri.withAppendedPath(person, Contacts.Photo.CONTENT_DIRECTORY)
        val photo: Bitmap? = inputStream?.let { BitmapFactory.decodeStream(it) }

        while (cursorInfo.moveToNext()) {
            val info = Contact(
                contactId,
                cursorInfo.getString(displayNameColumnIndex),
                cursorInfo.getString(phoneNumberColumnsIndex),
                photo,
                pURI,
            )
            list.add(info)
        }
        cursorInfo.close()

        return@withContext list
    }
}