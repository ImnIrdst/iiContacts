package com.ian.iicontacts.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import com.ian.iicontacts.domain.model.Contact
import com.ian.iicontacts.ui.theme.spacingMedium

@Composable
fun ListContacts(contacts: List<Contact> = Contact.dummyList) {
    LazyColumn(contentPadding = PaddingValues(vertical = spacingMedium)) {
        items(contacts) { contact ->
            ItemContact(contact)
        }
    }
}