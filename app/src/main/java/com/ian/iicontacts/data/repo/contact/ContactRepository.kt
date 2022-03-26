package com.ian.iicontacts.data.repo.contact

import androidx.paging.*
import com.ian.iicontacts.data.local.database.AppDatabase
import com.ian.iicontacts.data.local.database.contact.ContactDatabaseDao
import com.ian.iicontacts.data.local.provider.contact.ContactContentProvider
import com.ian.iicontacts.domain.model.Resource
import kotlinx.coroutines.flow.*

class ContactRepository(
    private val database: ContactDatabaseDao = AppDatabase.instance.contactDao(),
    private val contentProvider: ContactContentProvider = ContactContentProvider(),
) {

    fun filterContacts(query: String) =
        Pager(PagingConfig(pageSize = 25)) {
            database.filterContactsByPhone(query.withWildCards())
        }.flow


    fun loadContactsIntoDB(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        var offset = 0
        var loadSize = 50
        while (true) {
            val contactList = contentProvider.getContacts(offset, loadSize)

            if (contactList.isEmpty()) {
                break
            }

            database.insert(contactList)

            offset += loadSize
            loadSize = 500
        }
        emit(Resource.Success(Unit))
    }

    companion object {
        fun String.withWildCards() = "%${this.replace(' ', '%')}%"
    }
}