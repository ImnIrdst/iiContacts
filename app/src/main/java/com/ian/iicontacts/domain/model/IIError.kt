package com.ian.iicontacts.domain.model

class IIError(throwable: Throwable) : Throwable(throwable) {
    constructor(message: String) : this(Throwable(message))

    fun toHumanReadableString() = message
}