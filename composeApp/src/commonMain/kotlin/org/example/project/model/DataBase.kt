package org.example.project.model

import org.koin.core.module.Module

//Le module à remplir par chaque plateform pour initialiser la base de donnée
expect fun databaseModule(): Module


//class LocalDatabase(driverFactory: DriverFactory){
//    private val database = MyDatabase(driverFactory.createDriver())
//
//    private val query = database.photographerStorageQueries
//}

