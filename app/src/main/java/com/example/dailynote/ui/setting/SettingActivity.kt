package com.example.dailynote.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dailynote.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)



        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, MySettingsFragment())
            .commit()  }
}