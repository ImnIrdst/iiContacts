package com.ian.iicontacts.data.local.database.convertor

import android.net.Uri
import androidx.room.TypeConverter

class UriConvertor {
    @TypeConverter
    fun fromString(value: String?) = if (value == null) null else Uri.parse(value)

    @TypeConverter
    fun toString(uri: Uri?) = uri?.toString()
}