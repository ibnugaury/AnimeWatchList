package com.ibnugaury.animewatchlist.Main

import android.app.Application
import com.ibnugaury.animewatchlist.Database.DatabaseHelper

class Application: Application(){

    override fun onCreate() {
        super.onCreate()
        DatabaseHelper.initDatabaseInstance(this)
    }

}