package me.tonkku.img

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import me.tonkku.img.screens.IndexScreen
import me.tonkku.img.screens.SettingsScreen
import me.tonkku.img.screens.ShareScreen
import me.tonkku.img.ui.theme.UploaderTheme
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.animation.rememberSlideDistance

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    enableEdgeToEdge()

    val requestPermission = registerForActivityResult(RequestPermission()) {
      isGranted -> Log.d("Permission", isGranted.toString())
    }

    when {
      checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED -> {
        requestPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
      }
    }

    setContent {
      App()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun App() {
  UploaderTheme {
    val navController = rememberNavController()

    // A surface container using the 'background' color from the theme
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
    ) {
      val slideDistance = rememberSlideDistance()

      NavHost(
        navController = navController,
        startDestination = "index",
        enterTransition = { materialSharedAxisXIn(true, slideDistance) },
        exitTransition = { materialSharedAxisXOut(true, slideDistance) },
        popEnterTransition = { materialSharedAxisXIn(false, slideDistance) },
        popExitTransition = { materialSharedAxisXOut(false, slideDistance) },

      ) {
        composable(route = "index") {
          IndexScreen(navController)
        }
        composable(route = "settings") {
          SettingsScreen(navController)
        }
        composable(route = "share", deepLinks = listOf(
          navDeepLink {
            action = Intent.ACTION_SEND
            mimeType = "*/*"
          }
        )) {
          ShareScreen()
        }
      }
    }
  }
}
