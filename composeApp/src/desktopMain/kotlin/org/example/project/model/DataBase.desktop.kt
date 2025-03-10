package org.example.project.model

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.example.project.db.MyDatabase
import org.koin.dsl.module

actual fun databaseModule() = module {
    single {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
            MyDatabase.Schema.create(it)
        }
        MyDatabase(driver)
    }
}
