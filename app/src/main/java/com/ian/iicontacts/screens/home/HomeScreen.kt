package com.ian.iicontacts.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.insets.statusBarsPadding
import com.ian.iicontacts.screens.home.widget.*

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {


        Column(Modifier.statusBarsPadding()) {
            TitleBar()
            SearchTextField()
            ListContacts(homeViewModel.contacts.collectAsLazyPagingItems())
        }
    }
}