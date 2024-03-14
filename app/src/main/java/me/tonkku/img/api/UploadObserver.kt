package me.tonkku.img.api

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.RequestObserverDelegate

class UploadObserver : RequestObserverDelegate {
  override fun onSuccess(context: Context, uploadInfo: UploadInfo, serverResponse: ServerResponse) {
    Log.d("UploadObserver", "Success: $uploadInfo")

    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Upload response", serverResponse.bodyString)
    clipboard.setPrimaryClip(clip)

    Toast.makeText(context, "Uploaded!", Toast.LENGTH_SHORT).show()
  }

  override fun onError(context: Context, uploadInfo: UploadInfo, exception: Throwable) {
    Log.e("UploadObserver", "Error: $uploadInfo", exception)
    Toast.makeText(context, "An error occurred with the upload", Toast.LENGTH_SHORT).show()
    exception.printStackTrace()
  }

  override fun onCompleted(context: Context, uploadInfo: UploadInfo) {
    Log.d("UploadObserver", "Completed: $uploadInfo")
  }

  override fun onCompletedWhileNotObserving() {
    Log.d("UploadObserver", "Completed while not observing")
  }

  override fun onProgress(context: Context, uploadInfo: UploadInfo) {
    Log.d("UploadObserver", "Progress $uploadInfo")
  }
}