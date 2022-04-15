package com.example.dailynote.worker

import android.content.Context
import androidx.work.*
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class WorkManager(context: Context) {

    private val workManager :WorkManager = WorkManager.getInstance(context)
    private lateinit var workRequest:OneTimeWorkRequest


    fun startReminderWork( channelTitle :String?=null,
                           channelDescription :String?=null,
                           channelIcon :Int?=null,
                           notificationId :Int?=null)
    {

        //Data input to worker (ReminderWorker)
        val data = Data.Builder().apply {
            putString(ChannelTitle_Key , channelTitle)
            putString(ChannelDescription_Key , channelDescription)

            if (channelIcon != null) {
                putInt(ChannelIcon_Key , channelIcon)
            }

            if (notificationId != null) {
                putInt(Notification_Reminder_Id_Key,notificationId)
            }

        }.build()

        //Request work
        workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .addTag(WorkRequest_Tag)
            .setInputData(data).build()

        workManager.enqueue(workRequest)
    }

    fun cancelReminderWork(){
        workManager.cancelAllWorkByTag(WorkRequest_Tag)
    }

    companion object{
        const val WorkRequest_Tag = "cancel_tag"
    }
}