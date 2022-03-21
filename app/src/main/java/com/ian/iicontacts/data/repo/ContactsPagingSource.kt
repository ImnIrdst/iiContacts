package com.ian.iicontacts.data.repo

import androidx.paging.*
import com.ian.iicontacts.data.local.ContactLocalDataSource
import com.ian.iicontacts.domain.model.Contact

class ContactPagingSource(
    private val localDataSource: ContactLocalDataSource
) : PagingSource<Int, Contact>() {
    private var initialLoadSize: Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {
        try {
            val nextPageNumber = params.key ?: 1
            if (params.key == null) {
                initialLoadSize = params.loadSize
            }
            val offsetCalc = {
                if (nextPageNumber == 2)
                    initialLoadSize
                else
                    ((nextPageNumber - 1) * params.loadSize) + (initialLoadSize - params.loadSize)
            }
            val offset = offsetCalc.invoke()
            val messages = localDataSource.getContacts(offset, params.loadSize)
            val count = messages.size
            return LoadResult.Page(
                data = messages,
                prevKey = null,
                nextKey = if (count < params.loadSize) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? {
        TODO("Not yet implemented")
    }
}