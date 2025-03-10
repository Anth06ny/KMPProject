package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.runBlocking
import org.example.project.di.initKoin
import org.example.project.model.PhotographerAPI

private val koin = initKoin().koin

fun main() = application {

    val photographerAPI = koin.get<PhotographerAPI>()

    runBlocking {
        println(photographerAPI.loadPhotographers())
    }


    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        App()
    }
}
