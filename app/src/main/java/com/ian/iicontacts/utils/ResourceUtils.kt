package com.ian.iicontacts.utils

import com.ian.iicontacts.domain.model.Resource

fun <T> Resource<T>.isLoading() = this is Resource.Loading
fun <T> Resource<T>.isSuccess() = this is Resource.Success
fun <T> Resource<T>.isFailure() = this is Resource.Failure