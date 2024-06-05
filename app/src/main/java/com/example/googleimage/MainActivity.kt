package com.example.googleimage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.googleimage.domain.model.app.Screen
import com.example.googleimage.presentation.image_detail.ImageDetailViewModel
import com.example.googleimage.presentation.image_detail.components.ImageDetailScreen
import com.example.googleimage.presentation.image_list.ImageListScreenEvent
import com.example.googleimage.presentation.image_list.ImageListViewModel
import com.example.googleimage.presentation.image_list.components.ImageListScreen
import com.example.googleimage.ui.theme.GoogleImageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    object CacheKey {
        fun getImageKey(id : Int) : String = "image-$id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleImageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Navigation(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation(navController: NavHostController) {
    val imageListViewModel = hiltViewModel<ImageListViewModel>()

    //Layout for shared transition with navigation compose
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Screen.ImageListScreen.route,
        ) {
            composable(route = Screen.ImageListScreen.route) {
                val imageState by imageListViewModel.screenState.collectAsStateWithLifecycle()
                ImageListScreen(
                    imageListViewModel,
                    imageState,
                    onClickImageItem = { imageId ->
                        navController.navigate(Screen.ImageDetailScreen.withArgs(imageId.toString()))
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }

            composable(
                route = Screen.ImageDetailScreen.route + "/{index}",
                arguments = listOf(
                    navArgument("index") {
                        type = NavType.IntType
                        defaultValue = 0
                        nullable = false
                    }
                )
            ) {
                //reset the viewmodel everytime we enter the screen
                val imageDetailViewModel = hiltViewModel<ImageDetailViewModel>()
                val imageState by imageDetailViewModel.screenState.collectAsStateWithLifecycle()
                val context = LocalContext.current

                ImageDetailScreen(
                    imageState,
                    onClickWebNavigateButton = { link ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                        context.startActivity(intent)
                    },
                    onBackButtonPressed = {
                        imageListViewModel.onEvent(ImageListScreenEvent.OnNavigateBack(it))
                        navController.popBackStack()
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
        }
    }
}
