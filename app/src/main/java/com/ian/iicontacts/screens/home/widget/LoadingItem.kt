package com.ian.iicontacts.screens.home.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import com.ian.iicontacts.ui.theme.spacingMedium

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .padding(horizontal = spacingMedium)
            .fillMaxWidth()
    ) {
        CircularProgressIndicator(
            Modifier
                .align(Alignment.Center)
                .padding(spacingMedium)
        )
    }
}