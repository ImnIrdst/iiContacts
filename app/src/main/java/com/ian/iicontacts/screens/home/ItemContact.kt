package com.ian.iicontacts.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ian.iicontacts.domain.model.Contact
import com.ian.iicontacts.ui.theme.*

@Composable
fun ItemContact(contact: Contact) {
    Card(
        Modifier
            .padding(horizontal = spacingMedium, vertical = spacingTiny)
            .clip(RoundedCornerShape(spacingMedium))
    ) {
        Row(modifier = Modifier.padding(spacingMedium)) {
            Box(
                modifier = Modifier
                    .size(dimenIconLarge)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                    .background(Orange200)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.preview,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacingMedium)
            ) {
                Text(text = contact.name, modifier = Modifier.padding(vertical = spacingTiny))
                Text(text = contact.phone, modifier = Modifier.padding(vertical = spacingTiny))
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    IIContactsTheme(darkTheme = true) {
        ItemContact(Contact.dummyList.first())
    }
}
