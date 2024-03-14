package me.tonkku.img.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import me.tonkku.img.R
import me.tonkku.img.api.Uploader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun IndexScreen(navController: NavController? = null) {
  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
          Text(text = stringResource(id = R.string.app_name))
        },
        actions = {
          IconButton(onClick = { navController?.navigate("settings") }) {
            Icon(
              imageVector = Icons.Filled.Settings,
              contentDescription = "Settings"
            )
          }
        },
      )
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      val context = LocalContext.current
      val pickMedia = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri -> onFilePicked(context, uri) }
      )
      val pickFile = rememberLauncherForActivityResult(
        contract = StartActivityForResult(),
        onResult = { activityResult -> onFilePicked(context, activityResult.data?.data) }
      )

      Button(onClick = {
        pickMedia.launch(PickVisualMediaRequest(
          mediaType = PickVisualMedia.ImageAndVideo
        ))
      }) {
        Text("Upload image or video")
      }
      Button(onClick = {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
          addCategory(Intent.CATEGORY_OPENABLE)
          type = "*/*"
          addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
          addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        pickFile.launch(intent)
      }) {
        Text("Upload a file")
      }
    }
  }
}

private fun onFilePicked(context: Context, uri: Uri?) {
  if (uri != null) {
    Log.d("Main", "Selected uri: $uri")
    context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    Uploader.upload(context, uri)
  } else {
    Log.d("Main", "No media selected")
  }
}
