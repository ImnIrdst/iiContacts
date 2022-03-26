package com.ian.iicontacts.data.local.database

import androidx.room.*
import com.ian.iicontacts.IIContactsApp
import com.ian.iicontacts.data.local.database.contact.ContactDatabaseDao
import com.ian.iicontacts.data.local.database.convertor.UriConvertor
import com.ian.iicontacts.domain.model.Contact

@Database(
    entities = [
        Contact::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(UriConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDatabaseDao

    companion object {
        val instance by lazy {
            Room.databaseBuilder(
                IIContactsApp.instanceLazy.applicationContext,
                AppDatabase::class.java,
                "iiContact.db"
            ).build()
        }
    }
}