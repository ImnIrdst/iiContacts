package com.ian.iicontacts.screens.home

import androidx.lifecycle.ViewModel
import androidx.paging.map
import com.ian.iicontacts.data.repo.contact.ContactRepository
import com.ian.iicontacts.screens.home.helpers.ContactSelectionHelper
import com.ian.iicontacts.screens.home.widget.ContactItemModel
import com.ian.iicontacts.utils.isLoading
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class HomeViewModel(
    contactRepository: ContactRepository = ContactRepository(),
    private val selectionHelper: ContactSelectionHelper = ContactSelectionHelper(),
) : ViewModel() {

    private val initialContactTransferFlow = contactRepository.loadContactsIntoDB()

    private val _queryFlow = MutableStateFlow("")
    val queryFlow: StateFlow<String> get() = _queryFlow

    private val contactListQueryFlow = queryFlow
        .debounce(300)
        .flatMapLatest { contactRepository.filterContacts(it) }
        .map { list -> list.map { ContactItemModel(contact = it) } }


    private val contactListSelectionFlow = selectionHelper.selectionChangedFlow
        .combine(contactListQueryFlow) { _, contactList ->
            contactList.map { it.copy(isSelected = selectionHelper.isSelected(it.contact.id)) }
        }

    val contactListFlow = merge(contactListQueryFlow, contactListSelectionFlow)

    val loadingFlow = initialContactTransferFlow.map { it.isLoading() }

    fun perform(cmd: Cmd) {
        when (cmd) {
            is Cmd.QueryChanged -> _queryFlow.update { cmd.query }
            is Cmd.Select -> selectionHelper.add(cmd.contactId)
            is Cmd.SelectAll -> selectionHelper.selectAll()
        }
    }

    sealed class Cmd {
        data class QueryChanged(val query: String) : Cmd()
        data class Select(val contactId: String) : Cmd()

        object SelectAll : Cmd()
    }
}