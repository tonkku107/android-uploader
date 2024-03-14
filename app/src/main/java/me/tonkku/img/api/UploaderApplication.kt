package me.tonkku.img.api

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import net.gotev.uploadservice.BuildConfig
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.observer.request.GlobalRequestObserver

class UploaderApplication : Application() {

  companion object {
    const val NOTIFICATION_CHANNEL = "uploader"
  }

  private fun createNotificationChannel() {
    val channel = NotificationChannel(
      NOTIFICATION_CHANNEL,
      "Uploader",
      NotificationManager.IMPORTANCE_LOW
    )
    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(channel)
  }

  override fun onCreate() {
    super.onCreate()

    createNotificationChannel()

    UploadServiceConfig.initialize(
      context = this,
      defaultNotificationChannel = NOTIFICATION_CHANNEL,
      debug = BuildConfig.DEBUG
    )

    GlobalRequestObserver(this, UploadObserver())
  }

}