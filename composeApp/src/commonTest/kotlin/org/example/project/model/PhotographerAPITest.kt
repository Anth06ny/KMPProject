package org.example.project.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.example.project.di.apiModule
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PhotographerAPITest : KoinTest {

    val photographerAPI : PhotographerAPI by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp()  {
        Dispatchers.setMain(StandardTestDispatcher())

        startKoin{
            modules(
                apiModule
            )
        }
    }

    @Test
    fun testAPIWork() = runTest{
        val list = photographerAPI.loadPhotographers()
        assertTrue(list.isNotEmpty())
    }
}