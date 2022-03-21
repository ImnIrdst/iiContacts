package com.ian.iicontacts.domain.model

sealed class Resource<T>(
    val message: String? = null
) {
    class Loading<T> : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
    class Failure<T>(error: IIError) : Resource<T>(message = error.toHumanReadableString())
}

