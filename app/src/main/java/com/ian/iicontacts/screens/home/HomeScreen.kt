package com.ian.iicontacts.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import com.ian.iicontacts.domain.model.Resource.Loading
import com.ian.iicontacts.screens.home.widget.*

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    val contacts = homeViewModel.contactList.collectAsState(Loading())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {


        Column(Modifier.statusBarsPadding()) {
            TitleBar()
            SearchTextField()
            ListContacts(contacts.value, onRefresh = { homeViewModel.loadContacts() })
        }
    }
}