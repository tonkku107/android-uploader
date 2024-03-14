package me.tonkku.img.screens

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import me.tonkku.img.api.SharedContent
import me.tonkku.img.api.Uploader

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
@Preview(showBackground = true)
fun ShareScreen() {
  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
          Text("Uploader")
        },
      )
    },
  ) { innerPadding ->
    val context = LocalContext.current
    val sharedContent: SharedContent = viewModel()

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      sharedContent.type?.let { Text(it) }
      sharedContent.content?.let { Text(it) }
      if (sharedContent.isImage()) { GlideImage(model = sharedContent.content, contentDescription = "Shared image") }
      Spacer(modifier = Modifier.weight(1f))
      Button(enabled = sharedContent.isImage(), onClick = { doUpload(context, sharedContent.content!!) },  modifier = Modifier
        .align(Alignment.End)
        .padding(16.dp)) {
        Text("Upload")
      }
    }
  }
}

private fun doUpload(context: Context, url: String) {
  val uri = Uri.parse(url)
  Uploader.upload(context, uri)

  val activity = context as? Activity
  activity?.finish()
}
