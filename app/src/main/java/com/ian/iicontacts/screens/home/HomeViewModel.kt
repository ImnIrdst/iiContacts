package com.ian.iicontacts.screens.home

import androidx.lifecycle.ViewModel
import com.ian.iicontacts.data.repo.contact.ContactRepository
import com.ian.iicontacts.utils.isLoading
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class HomeViewModel(
    contactRepository: ContactRepository = ContactRepository()
) : ViewModel() {

    private val initialContactTransferFlow = contactRepository.loadContactsIntoDB()

    private val _queryFlow = MutableStateFlow("")
    val queryFlow: StateFlow<String> get() = _queryFlow

    val contactFlow = queryFlow
        .debounce(300)
        .flatMapLatest { contactRepository.searchContactsFlow(it) }

    val loadingFlow = initialContactTransferFlow.map { it.isLoading() }

    fun perform(cmd: Cmd) {
        when (cmd) {
            is Cmd.QueryChanged -> _queryFlow.update { cmd.query }
        }
    }

    sealed class Cmd {
        data class QueryChanged(val query: String) : Cmd()
    }
}