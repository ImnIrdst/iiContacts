package com.ian.iicontacts.screens.home

import androidx.lifecycle.ViewModel
import com.ian.iicontacts.data.repo.contact.ContactRepository
import com.ian.iicontacts.utils.isLoading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    contactRepository: ContactRepository = ContactRepository()
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    private val initialContactTransferFlow = contactRepository.loadContactsIntoDB()

    val contactFlow = queryFlow.combine(initialContactTransferFlow) { query, initLoad ->
        if (initLoad.isLoading()) {
            null
        } else {
            query
        }
    }.flatMapLatest { query ->
        if (query != null) {
            contactRepository.searchContactsFlow(query)
        } else {
            flowOf()
        }
    }

    val loadingFlow = initialContactTransferFlow.map { it.isLoading() }
}