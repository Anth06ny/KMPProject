package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.model.PhotographerAPI
import org.example.project.model.databaseModule
import org.example.project.viewmodel.MainViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(apiModule, databaseModule(),  viewModelModule)
    }

// called by iOS etc
fun initKoin() = initKoin {}

val apiModule = module {
    //Création d'un singleton pour le client HTTP
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
        }
    }

    //Création d'un singleton pour les repository. Get injectera les instances
    //single { PhotographerAPI(get()) }

    //Nouvelle version avec injection automatique des instances
    singleOf(::PhotographerAPI)

    //Si besoin de coroutine
    //single { CoroutineScope(Dispatchers.Default + SupervisorJob() ) }
}


val viewModelModule = module {
    viewModelOf(::MainViewModel)
}