package org.example.project.model

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.example.project.db.MyDatabase
import org.koin.dsl.module

actual fun databaseModule() = module {
    single {
        val driver = NativeSqliteDriver(MyDatabase.Schema, "test.db")
        MyDatabase(driver)
    }
}