package com.ian.iicontacts.screens.home.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.*
import com.ian.iicontacts.domain.model.*
import com.ian.iicontacts.domain.model.Resource.*
import com.ian.iicontacts.ui.theme.spacingMedium
import com.ian.iicontacts.utils.isFailure

@Composable
fun ListContacts(
    contactsResource: Resource<List<Contact>> = Success(Contact.dummyList),
    onRefresh: () -> Unit = {}
) {

    val contactList = remember { mutableStateOf(listOf<Contact>()) }

    if (contactsResource is Success) {
        contactList.value = contactsResource.data
    }
    if (!contactsResource.isFailure()) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(contactsResource is Loading),
            onRefresh = onRefresh,
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = spacingMedium),
                modifier = Modifier.fillMaxSize()
            ) {
                items(contactList.value) { contact ->
                    ItemContact(contact)
                }
            }
        }
    } else {
        Text("Error happened while reading contacts: ${contactsResource.message}")
    }
}