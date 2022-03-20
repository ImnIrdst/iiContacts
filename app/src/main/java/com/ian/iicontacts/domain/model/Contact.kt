package com.ian.iicontacts.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val photo: Bitmap?,
    val photoURI: Uri?,
) {

    val preview: String
        get() = name.split("\\s+".toRegex()).let { "${it.first().first()}${it.last().first()}" }

    companion object {

        private fun dummyContactInitiator(name: String, mobileNumber: String) =
            Contact("", name, mobileNumber, null, null)

        val dummyList = listOf(
            dummyContactInitiator("Dummy Contact 0", "98215421521"),
            dummyContactInitiator("Dummy Contact 1", "0151325144"),
            dummyContactInitiator("Dummy Contact 2", "0031125125912"),
            dummyContactInitiator("Dummy Contact 3", "009831125125912"),
            dummyContactInitiator("Dummy Contact 4", "31125125912"),
            dummyContactInitiator("Dummy Contact 5", "+983124125125"),
        )
    }
}
