package com.example.dailynote.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.example.dailynote.R

const val Channel_Id= "5";


fun notify(context: Context, title:String?=null, description:String?=null, icon:Int?= null,notificationId:Int?=null)
{

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        createChannel(context, Channel_Id,context.getString(R.string.app_name))

    val notification = createNotification(context
        ,title ?: "title" ,
        description ?: "description",
        icon ?: R.drawable.ic_android)

    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define
        notify(notificationId ?: 1
               , notification)
    }

}

private fun createNotification(context: Context,title: String,description: String,icon: Int): Notification
{
    return NotificationCompat.Builder(context, Channel_Id)
        .setContentTitle(title)
        .setContentText(description)
        .setSmallIcon(icon)
        .build()

}


@RequiresApi(Build.VERSION_CODES.O)
private fun createChannel(context:Context, channelId:String, name:String, importance:Int = NotificationManager.IMPORTANCE_DEFAULT)
{
    val channel = NotificationChannel(channelId,name,importance)
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

