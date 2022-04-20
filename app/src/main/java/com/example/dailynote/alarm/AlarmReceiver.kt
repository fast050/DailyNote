package com.example.dailynote.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.dailynote.util.notify

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent?) {

        Log.d("TAG", "onReceive: called")
        val title = intent?.extras?.getString(Extra_Title)
        val description = intent?.extras?.getString(Extra_Description)
        val icon = intent?.extras?.getInt(Extra_icon)

        Log.d("TAG", "onReceive: title:$title , description:$description ")

        notify(context, title = title, description = description , icon = icon, notificationId = NotificationID)
    }


    companion object {
        const val Extra_Title = "TITLE"
        const val Extra_Description = "DESCRIPTION"
        const val Extra_icon = "ICON"
        const val NotificationID=123
    }
}