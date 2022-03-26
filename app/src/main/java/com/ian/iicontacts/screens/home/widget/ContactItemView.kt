package com.ian.iicontacts.screens.home.widget

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ian.iicontacts.R
import com.ian.iicontacts.domain.model.Contact
import com.ian.iicontacts.ui.theme.*

data class ContactItemModel(
    val contact: Contact,
    val isSelected: Boolean = false,
)

@Composable
fun ContactItemView(model: ContactItemModel) {
    val contact = model.contact
    val isSelected = remember { mutableStateOf(model.isSelected) }

    Row(
        modifier = Modifier
            .padding(horizontal = spacingMedium, vertical = spacingSmall)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { isSelected.value = !isSelected.value },
                )
            }
    ) {
        Box(
            modifier = Modifier
                .size(dimenIconLarge)
                .clip(CircleShape)
                .background(if (isSelected.value) SelectionGreen else PrimaryLight)
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
                .weight(1f)
                .padding(horizontal = spacingMedium)
        ) {
            Text(text = contact.name, modifier = Modifier.padding(vertical = spacingTiny))
            Text(
                text = contact.phoneNumber,
                modifier = Modifier.padding(vertical = spacingTiny)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
            contentDescription = "Open ${contact.name}",
            modifier = Modifier
                .padding(spacingSmall)
                .size(dimenIconSmall)
                .align(Alignment.CenterVertically),
        )
    }
}


@Preview
@Composable
fun DefaultPreview() {
    IIContactsTheme(darkTheme = true) {
        ContactItemView(DummyContacts.dummyList.first())
    }
}


object DummyContacts {

    private fun dummyContactInitiator(name: String, mobileNumber: String) =
        Contact("", name, mobileNumber, null)

    val dummyList = listOf(
        dummyContactInitiator("Dummy Contact 0", "98215421521"),
        dummyContactInitiator("Dummy Contact 1", "0151325144"),
        dummyContactInitiator("Dummy Contact 2", "0031125125912"),
        dummyContactInitiator("Dummy Contact 3", "009831125125912"),
        dummyContactInitiator("Dummy Contact 4", "31125125912"),
        dummyContactInitiator("Dummy Contact 5", "+983124125125"),
    ).map { ContactItemModel(contact = it) }
}