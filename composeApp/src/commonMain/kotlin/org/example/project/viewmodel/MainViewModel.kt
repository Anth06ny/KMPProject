package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.project.db.MyDatabase
import org.example.project.model.Photographer
import org.example.project.model.PhotographerAPI

class MainViewModel(
    val photographerAPI: PhotographerAPI,
    val myDatabase: MyDatabase
) : ViewModel() {

    private val photographerQueries = myDatabase.photographerStorageQueries
    private val jsonParser = Json { prettyPrint = true }

    private val _dataList = MutableStateFlow(emptyList<Photographer>())
    val dataList = _dataList.asStateFlow()

    private val _runInProgress = MutableStateFlow(false)
    val runInProgress = _runInProgress.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        loadPhotographers()
    }

    private fun loadPhotographers() {
        _runInProgress.value = true
        _errorMessage.value = ""

        viewModelScope.launch {

            //Version flow
//            photographerAPI.loadPhotographersFlow()
//                .catch {
//                    it.printStackTrace()
//                    _errorMessage.value = it.message ?: "Error"
//                }
//                .onCompletion {
//                    _runInProgress.value = false
//                }
//                .collect{
//                    _dataList.value = PhotographerAPI.loadPhotographers()
//                }

            //Version sans flow
            try {

                val photographers = photographerAPI.loadPhotographers()
                _dataList.value = photographers

                //on sauvegarde en base
                photographerQueries.transaction {
                    //photographerQueries.delete
                    photographers.forEach {
                        photographerQueries.insertPhotographer(
                            it.id.toLong(),
                            it.stageName,
                            it.photoUrl,
                            it.story,
                            jsonParser.encodeToString(it.portfolio)
                        )
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.message ?: "Error"

                //En cas d'erreur on charge en base
                _dataList.value = photographerQueries.selectAllPhotographers().executeAsList().map {
                    Photographer(
                        it.id.toInt(), it.stageName, it.photoUrl, it.story,
                        portfolio = jsonParser.decodeFromString(it.portfolio)
                    )
                }
            }
        }
        _runInProgress.value = false
    }


    fun loadFakeData(runInProgress: Boolean = false, errorMessage: String = "") {
        _runInProgress.value = runInProgress
        _errorMessage.value = errorMessage
        _dataList.value = listOf(
            Photographer(

                id = 1,
                stageName = "Bob la Menace",
                photoUrl = "https://www.amonteiro.fr/img/fakedata.com/bob.jpg",
                story = "Ancien agent secret, Bob a troqué ses gadgets pour un appareil photo après une mission qui a mal tourné. Il traque désormais les instants volés plutôt que les espions.",
                portfolio = listOf(
                    "https://example.com/photo1.jpg",
                    "https://example.com/photo2.jpg",
                    "https://example.com/photo3.jpg"
                )
            ),
            Photographer(
                id = 2,
                stageName = "Jean-Claude Flash",
                photoUrl = "https://www.amonteiro.fr/img/fakedata.com/jc.jpg",
                story = "Ancien champion de rodéo, il s’est reconverti en photographe après une chute mémorable. Maintenant, il dompte la lumière comme un vrai cow-boy.",
                portfolio = listOf(
                    "https://example.com/photo4.jpg",
                    "https://example.com/photo5.jpg",
                    "https://example.com/photo6.jpg"
                )
            )
        )
    }
}