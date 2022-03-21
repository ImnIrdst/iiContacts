package com.ian.iicontacts.screens.home.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.*
import com.google.accompanist.swiperefresh.*
import com.ian.iicontacts.domain.model.Contact
import com.ian.iicontacts.ui.theme.spacingMedium

@Composable
fun ListContacts(
    contactsPager: LazyPagingItems<Contact>,
) {
    val refreshState = contactsPager.loadState.refresh
    val listState = rememberLazyListState()

    if (refreshState !is LoadState.Error) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshState is LoadState.Loading),
            onRefresh = { contactsPager.retry() },
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = spacingMedium),
                modifier = Modifier.fillMaxSize(),
                state = listState,
            ) {
                items(contactsPager) { contact ->
                    ItemContact(contact!!)
                }
            }
        }
    } else {
        Text(
            text = "Error happened while reading contacts: ${refreshState.error}",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}