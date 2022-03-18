package com.apion.apionhome

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.apion.apionhome.data.model.User
import com.apion.apionhome.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    apiModule,
                    networkModule,
                    viewModelModule,
                    userRepoModule,
                    houseRepoModule,
                    bookMarkRepoModule,
                    communityRepoModule,
                )
            )
        }
    }

    companion object {
        var sessionUser: MutableLiveData<User?> = MutableLiveData(null)
    }
}
