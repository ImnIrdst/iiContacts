package com.ian.iicontacts.screens.home

import androidx.lifecycle.ViewModel
import com.ian.iicontacts.domain.model.Contact
import kotlinx.coroutines.flow.*

class HomeViewModel : ViewModel() {

    private val model = MutableStateFlow<List<Contact>>(listOf())

    val contactList: Flow<List<Contact>> = model
        .map { it + Contact(name = "View Model", phone = "2142512512") }

    init {
        model.value = Contact.dummyList
    }
}