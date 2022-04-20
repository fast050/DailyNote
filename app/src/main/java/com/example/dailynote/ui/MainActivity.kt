package com.example.dailynote.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.dailynote.R
import com.example.dailynote.alarm.AlarmManagerHelper
import com.example.dailynote.databinding.ActivityMainBinding
import com.example.dailynote.ui.setting.SettingActivity
import com.example.dailynote.util.notify
import com.example.dailynote.worker.WorkManager

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alarmManagerHelper = AlarmManagerHelper(this)
        val workManager =WorkManager(this)
        binding.apply {


            btnSetTime.setOnClickListener {
               alarmManagerHelper.setTimeAndDate()
               val time = alarmManagerHelper.datePick
               textShowTimeDate.text = time
            }

            btnStartAlarm.setOnClickListener {
              // Toast.makeText(this@MainActivity,alarmManagerHelper.timePick,Toast.LENGTH_SHORT).show()
               // alarmManagerHelper.startAlarmManager()
                workManager.startReminderWork("khalid","imported task to do")
            }

            btnStopAlarm.setOnClickListener {
             //alarmManagerHelper.stopAlarmManager()
               workManager.cancelReminderWork()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.setting)
        {
            startActivity(Intent(this,SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
