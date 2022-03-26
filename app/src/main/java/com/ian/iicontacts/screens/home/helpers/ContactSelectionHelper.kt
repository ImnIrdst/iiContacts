package com.ian.iicontacts.screens.home.helpers

import kotlinx.coroutines.flow.*

data class ContactSelectionHelper(
    private val selectedIds: MutableSet<String> = mutableSetOf(),
    private var isAllSelected: Boolean = false
) {

    private val _selectionChangedFlow = MutableStateFlow(Unit)
    val selectionChangedFlow: Flow<Unit> = _selectionChangedFlow

    fun isSelected(contactId: String) = isAllSelected || selectedIds.contains(contactId)

    fun add(contactId: String) = updateSelectedIdsFlow {
        selectedIds.add(contactId)
    }

    fun selectAll() = updateSelectedIdsFlow {
        isAllSelected = true
    }

    fun clear() = updateSelectedIdsFlow {
        selectedIds.clear()
        isAllSelected = false
    }

    private fun updateSelectedIdsFlow(action: () -> Unit) {
        action()
        _selectionChangedFlow.value = Unit
    }
}