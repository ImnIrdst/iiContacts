package com.ian.iicontacts.domain.model

data class Contact(
    val name: String,
    val phone: String,
) {

    val preview: String
        get() = name.split("\\s+".toRegex()).let { "${it.first().first()}${it.last().first()}" }

    companion object {
        val dummyList = listOf(
            Contact("Dummy Contact 0", "98215421521"),
            Contact("Dummy Contact 1", "0151325144"),
            Contact("Dummy Contact 2", "0031125125912"),
            Contact("Dummy Contact 3", "009831125125912"),
            Contact("Dummy Contact 4", "31125125912"),
            Contact("Dummy Contact 5", "+983124125125"),
        )
    }
}
