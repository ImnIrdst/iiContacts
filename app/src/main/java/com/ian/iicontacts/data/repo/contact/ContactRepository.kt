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

    fun searchContactsFlow(query: String) =
        Pager(PagingConfig(pageSize = 25)) {
            database.filterContactsByPhone(query.addWildCards())
        }.flow


    fun loadContactsIntoDB(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        var offset = 0
        while (true) {
            val contactList = contentProvider.getContacts(offset, BATCH_SIZE)

            if (contactList.isEmpty()) {
                break
            }

            database.insert(contactList)
            offset += BATCH_SIZE
        }

        emit(Resource.Success(Unit))
    }

    companion object {
        private const val BATCH_SIZE = 100

        fun String.addWildCards() = "%${this.replace(' ', '%')}%"
    }
}