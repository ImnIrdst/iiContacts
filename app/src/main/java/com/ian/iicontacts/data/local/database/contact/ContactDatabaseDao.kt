package com.ian.iicontacts.data.local.database.contact

import androidx.paging.PagingSource
import androidx.room.*
import com.ian.iicontacts.domain.model.Contact

@Dao
interface ContactDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Contact>)


    @Query(
        """SELECT * FROM contact
            WHERE phoneNumber LIKE :query OR name LIKE :query
            ORDER BY name, phoneNumber ASC"""
    )
    @RewriteQueriesToDropUnusedColumns
    fun filterContactsByPhone(query: String): PagingSource<Int, Contact>

}