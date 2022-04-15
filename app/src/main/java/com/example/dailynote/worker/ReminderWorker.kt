package com.example.dailynote.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.dailynote.R
import com.example.dailynote.util.notify
import java.lang.Exception

class ReminderWorker(private val context: Context, workPram: WorkerParameters) :
    Worker(context, workPram) {
    override fun doWork(): Result {
        val channelTitle = inputData.getString(ChannelTitle_Key)
        val channelDescription = inputData.getString(ChannelDescription_Key)
        val channelIcon = inputData.getInt(ChannelIcon_Key, R.drawable.ic_android)
        val notificationId = inputData.getInt(Notification_Reminder_Id_Key, 0)

        return try {

            Log.d("TAG", "doWork: Start")
            notify(
                context = context,
                title = channelTitle,
                description = channelDescription,
                icon = channelIcon,
                notificationId = notificationId
            )

            Result.success()
        }catch (e:Exception)
        {
            Result.retry()
        }

    }

}