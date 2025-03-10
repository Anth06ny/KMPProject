package org.example.project.model

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.example.project.db.MyDatabase
import org.koin.dsl.module

actual fun databaseModule() = module {

    single {
       //trouver le context
        val driver =
            AndroidSqliteDriver(MyDatabase.Schema, get(), "test.db")
        MyDatabase(driver)
    }
}

//actual class DriverFactory(private val context: Context) {
//    actual fun createDriver(): SqlDriver {
//        return AndroidSqliteDriver(MyDatabase.Schema, context, "test.db")
//    }
//}