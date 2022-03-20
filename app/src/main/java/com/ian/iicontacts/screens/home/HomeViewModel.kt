package com.ian.iicontacts.screens.home

import androidx.lifecycle.*
import com.ian.iicontacts.data.local.ContactLocalDataSource
import com.ian.iicontacts.domain.model.Contact
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val contactsLocalDataSource: ContactLocalDataSource = ContactLocalDataSource()
) : ViewModel() {

    private val model = MutableStateFlow<List<Contact>>(listOf())

    val contactList: Flow<List<Contact>> = model

    init {
        viewModelScope.launch {
            model.value =
                Contact.dummyList + Contact.dummyList + Contact.dummyList // contactsLocalDataSource.getContacts()
        }
    }
}