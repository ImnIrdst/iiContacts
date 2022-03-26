package com.ian.iicontacts.domain.model

import android.net.Uri
import androidx.room.*

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey
    val id: String,
    val name: String,
    val phoneNumber: String,
    val photoURI: Uri?,
) {
    val preview: String
        get() = name.split("\\s+".toRegex()).let { "${it.first().first()}${it.last().first()}" }
}
