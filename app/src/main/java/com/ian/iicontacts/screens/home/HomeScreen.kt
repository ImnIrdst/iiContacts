package com.ian.iicontacts.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import com.ian.iicontacts.*
import com.ian.iicontacts.R
import com.ian.iicontacts.ui.theme.spacingMedium

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    val contacts = homeViewModel.contactList.collectAsState(listOf())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        val searchQuery = remember { mutableStateOf("") }

        Column(Modifier.statusBarsPadding()) {
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacingMedium)
                    .padding(top = spacingMedium)

            )
            TextField(
                value = searchQuery.value,
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = "Search",
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            Toast.makeText(IIContactsApp.instance, "Sort", Toast.LENGTH_SHORT)
                                .show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                            contentDescription = "Sort",
                        )
                    }
                },
                shape = RoundedCornerShape(percent = 50),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent, //hide the indicator
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                onValueChange = { searchQuery.value = it },
                modifier = Modifier
                    .padding(horizontal = spacingMedium)
                    .padding(vertical = spacingMedium)
                    .fillMaxWidth()
            )
            ListContacts(contacts.value)
        }
    }
}