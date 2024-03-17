package me.tonkku.img.api

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class SharedContent(savedStateHandle: SavedStateHandle) : ViewModel() {
  val intent = savedStateHandle.getStateFlow(NavController.KEY_DEEP_LINK_INTENT, Intent()).value
  var content: String? = null
  var hasUri = false
  val type = intent.type
  
  init {
    parseIntent(this)
  }

  fun isImage(): Boolean {
    return this.type?.startsWith("image/") == true
  }
}

private fun parseIntent(self: SharedContent) {
  if (self.intent.action !== Intent.ACTION_SEND) return

  val uri = self.intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
  if (uri != null) {
    self.content = uri.toString()
    self.hasUri = true
    return
  }

  val text = self.intent.getStringExtra(Intent.EXTRA_TEXT)
  self.content = text
}
