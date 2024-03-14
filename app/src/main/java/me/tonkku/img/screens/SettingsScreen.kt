package me.tonkku.img.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun SettingsScreen(navController: NavController? = null) {
  Scaffold(
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
          Text("Settings")
        },
        navigationIcon = {
          IconButton(onClick = { navController?.navigateUp() }) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back"
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
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      val context = LocalContext.current
      val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

      var url by rememberSaveable { mutableStateOf(sharedPreferences.getString("url", "")) }
      var headerName by rememberSaveable { mutableStateOf(sharedPreferences.getString("header_name", "")) }
      var headerValue by rememberSaveable { mutableStateOf(sharedPreferences.getString("header_value", "")) }

      val modifier = Modifier
        .padding(8.dp, 4.dp, 8.dp, 4.dp)
        .fillMaxWidth()

      OutlinedTextField(
        modifier = modifier,
        value = url!!,
        onValueChange = { url = it },
        label = { Text("URL") },
        singleLine = true
      )
      OutlinedTextField(
        modifier = modifier,
        value = headerName!!,
        onValueChange = { headerName = it },
        label = { Text("Authentication Header") },
        singleLine = true
      )
      OutlinedTextField(
        modifier = modifier,
        value = headerValue!!,
        onValueChange = { headerValue = it },
        label = { Text("Authentication Header Value") },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
      )
      Button(
        onClick = {
          val editor = sharedPreferences.edit()
          editor.putString("url", url)
          editor.putString("header_name", headerName)
          editor.putString("header_value", headerValue)
          editor.apply()

          Toast.makeText(context, "Settings saved!", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
          .align(Alignment.End)
          .padding(0.dp, 4.dp, 8.dp, 0.dp)
      ) {
        Text("Save")
      }
    }
  }
}
