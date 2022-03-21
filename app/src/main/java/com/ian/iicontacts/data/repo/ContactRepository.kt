package com.ian.iicontacts.data.repo

import androidx.paging.*
import com.ian.iicontacts.data.local.ContactLocalDataSource

class ContactRepository(
    private val contactLocalDataSource: ContactLocalDataSource = ContactLocalDataSource()
) {
    val contacts = Pager(
        PagingConfig(pageSize = 250)
    ) {
        ContactPagingSource(contactLocalDataSource)
    }.flow
}