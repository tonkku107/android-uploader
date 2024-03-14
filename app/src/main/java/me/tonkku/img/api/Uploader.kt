package me.tonkku.img.api

import android.content.Context
import android.net.Uri
import android.widget.Toast
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest

object Uploader {
  fun upload(context: Context, uri: Uri) {
    val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val serverUrl = preferences.getString("url", "")
    val headerName = preferences.getString("header_name", "")
    val headerValue = preferences.getString("header_value", "")

    if (serverUrl!!.isEmpty() || headerName!!.isEmpty() || headerValue!!.isEmpty()) {
      Toast.makeText(context, "Uploader hasn't been configured!", Toast.LENGTH_LONG).show()
      return
    }

    MultipartUploadRequest(context, serverUrl)
      .setMethod("POST")
      .addFileToUpload(uri.toString(), "file")
      .addHeader(headerName, headerValue)
      .startUpload()
  }
}
