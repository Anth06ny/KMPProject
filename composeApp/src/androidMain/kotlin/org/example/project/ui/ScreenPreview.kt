package org.example.project.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.model.Photographer
import org.example.project.ui.screens.PhotographerScreen
import org.example.project.ui.screens.PhotographersScreen
import org.example.project.ui.theme.AppTheme
import org.example.project.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun PhotographersScreenPreview() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val viewModel : MainViewModel = koinViewModel()
            viewModel.loadFakeData()
            PhotographersScreen(modifier = Modifier.padding(innerPadding), mainViewModel = viewModel)
        }
    }
}

@Preview
@Composable
fun PhotographerScreenPreview() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val photograph = Photographer(
                id = 1,
                stageName = "Bob la Menace",
                photoUrl = "https://www.amonteiro.fr/img/fakedata.com/bob.jpg",
                story = "Ancien agent secret, Bob a troqué ses gadgets pour un appareil photo après une mission qui a mal tourné. Il traque désormais les instants volés plutôt que les espions.",
                portfolio = listOf(
                    "https://example.com/photo1.jpg",
                    "https://example.com/photo2.jpg",
                    "https://example.com/photo3.jpg"
                )
            )
            PhotographerScreen(modifier = Modifier.padding(innerPadding), photograph)
        }
    }
}
