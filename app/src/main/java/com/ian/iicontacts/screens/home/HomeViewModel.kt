package com.ian.iicontacts.screens.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ian.iicontacts.data.repo.ContactRepository

class HomeViewModel(
    contactRepository: ContactRepository = ContactRepository()
) : ViewModel() {

    val contacts = contactRepository.contacts.cachedIn(viewModelScope)
}