package com.example.dailynote.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dailynote.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import java.text.DateFormat
import java.util.*

class AlarmManagerHelper(private val context: AppCompatActivity) {

    val datePick get() = _datePick
    private var _datePick = ""

    val timePick get() = _timePick
    private var _timePick = ""

    private val alarmManager:AlarmManager = context.
    getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private var pendingIntent: PendingIntent?=null

    private var timeInMillSec:Long? = null

    private fun showTimePicker(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()

        val picker =
            MaterialTimePicker.Builder()
                .setInputMode(INPUT_MODE_KEYBOARD)
                .setTitleText("Select Appointment time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(calendar.get(Calendar.HOUR))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build()

        picker.show(context.supportFragmentManager, "tag")


        picker.addOnPositiveButtonClickListener {
            val calendarInMil = Calendar.getInstance().apply {

                this.set(Calendar.YEAR, year)
                this.set(Calendar.MONTH, month)
                this.set(Calendar.DAY_OF_MONTH, day)
                this.set(Calendar.HOUR, picker.hour)
                this.set(Calendar.MINUTE, picker.minute)
                this.set(Calendar.SECOND , 5)
            }.timeInMillis

            val timeSelected = DateFormat.getDateTimeInstance().format(calendarInMil)
            timeInMillSec = calendarInMil
            //_timePick = timeSelected
            //Toast.makeText(context, "set time done", Toast.LENGTH_SHORT).show()
        }
        picker.addOnNegativeButtonClickListener {
            Toast.makeText(context, "see ya another time", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePicker() {
        // Makes only dates from today forward selectable.
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        //data picker builder
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        //show picker builder
        datePicker.show(context.supportFragmentManager, "tag");

        datePicker.addOnPositiveButtonClickListener {
            // Respond to positive button click.

            // from selection
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            //to get year,month,day from the date picker
            calendar.time = Date(it)

            _datePick = datePicker.headerText
            showTimePicker(
                year = calendar.get(Calendar.YEAR),
                month = calendar.get(Calendar.MONTH),
                day = calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        datePicker.addOnNegativeButtonClickListener {
        }

    }

    fun setTimeAndDate() {
        showDatePicker()
    }


    fun startAlarmManager() {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.Extra_Title,"Khalid")
            putExtra(AlarmReceiver.Extra_Description,"This task is imported to do")
            putExtra(AlarmReceiver.Extra_icon, R.drawable.ic_baseline_import_contacts_24)

        }

//        val extras = Bundle()
//        extras.putString(AlarmReceiver.Extra_Title, "Khalid")
//        extras.putString(AlarmReceiver.Extra_Description, "this task is imported to do")
//        extras.putInt(AlarmReceiver.Extra_icon, R.drawable.ic_baseline_import_contacts_24)
//        intent.putExtras(extras)

         pendingIntent = PendingIntent.getBroadcast(
            context,
            RequestPendingIntent_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (timeInMillSec != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillSec!!, pendingIntent)
            Toast.makeText(context,"alarm set",Toast.LENGTH_SHORT).show()
        }else
            Toast.makeText(context,"set time please",Toast.LENGTH_SHORT).show()
    }

    fun stopAlarmManager() {
        if (pendingIntent!=null) {
            alarmManager.cancel(pendingIntent)
            pendingIntent = null
            Toast.makeText(context,"alarm is cleared",Toast.LENGTH_SHORT).show()
        }else
            Toast.makeText(context,"no alarm existed",Toast.LENGTH_SHORT).show()

    }

    companion object {
        //it should be unique for every pending intent in your app
        const val RequestPendingIntent_ID = 123
    }

}