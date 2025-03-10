package org.example.project.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.example.project.ui.screens.PhotographerScreen
import org.example.project.ui.screens.PhotographersScreen
import org.example.project.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

class Routes {
    @Serializable
    data object PhotographersRoute

    //les paramètres ne peuvent être que des types de base(String, Int, Double...)
    @Serializable
    data class PhotographerRoute(val id: Int)
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navHostController: NavHostController = rememberNavController()
    //viewModel() en dehors de NavHost lie à l'Activité donc partagé entre les écrans
    //viewModel() dans le NavHost lié à la stack d'écran. Une instance par stack d'écran
    val mainViewModel: MainViewModel = koinViewModel<MainViewModel>()

    //Import version avec Composable
    NavHost(
        navController = navHostController, startDestination = Routes.PhotographersRoute,
        modifier = modifier
    ) {
        //Route 1 vers notre SearchScreen
        composable<Routes.PhotographersRoute> {

            //Si créé ici, il sera propre à cet instance de l'écran
            //val mainViewModel : MainViewModel = viewModel()

            //on peut passer le navHostController à un écran s'il déclenche des navigations
            PhotographersScreen(mainViewModel = mainViewModel) {
                navHostController.navigate(Routes.PhotographerRoute(it.id))
            }
        }

        //Route 2 vers un écran de détail
        composable<Routes.PhotographerRoute> {
            val detailRoute = it.toRoute<Routes.PhotographerRoute>()
            val photographer = mainViewModel.dataList.collectAsStateWithLifecycle().value.first { it.id == detailRoute.id }

            PhotographerScreen(photographer = photographer)
        }
    }
}