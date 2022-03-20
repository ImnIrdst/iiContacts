package com.ian.iicontacts.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    val contacts = homeViewModel.contactList.collectAsState(listOf())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Box(Modifier.statusBarsPadding()) {
            ListContacts(contacts.value)
        }
    }
}