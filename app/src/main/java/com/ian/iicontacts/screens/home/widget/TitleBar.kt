package com.ian.iicontacts.screens.home.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ian.iicontacts.R
import com.ian.iicontacts.ui.theme.spacingMedium

@Composable
fun TitleBar() {
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
}