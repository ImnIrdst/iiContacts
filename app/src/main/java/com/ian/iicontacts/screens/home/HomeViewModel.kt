package com.ian.iicontacts.screens.home

import androidx.lifecycle.*
import com.ian.iicontacts.data.local.ContactLocalDataSource
import com.ian.iicontacts.domain.model.*
import com.ian.iicontacts.domain.model.Resource.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val contactsLocalDataSource: ContactLocalDataSource = ContactLocalDataSource()
) : ViewModel() {

    private val model = MutableStateFlow<Resource<List<Contact>>>(Success(listOf()))

    val contactList: Flow<Resource<List<Contact>>> = model

    init {
        loadContacts()
    }

    fun loadContacts() {
        viewModelScope.launch {
            model.value = Loading()

            // model.value = Contact.dummyList + Contact.dummyList + Contact.dummyList
            model.value = Success(contactsLocalDataSource.getContacts())
        }
    }
}