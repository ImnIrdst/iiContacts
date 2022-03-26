package com.ian.iicontacts

import android.app.Application

class IIContactsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        private var instance: IIContactsApp? = null

        val instanceLazy by lazy { instance!! }
    }
}