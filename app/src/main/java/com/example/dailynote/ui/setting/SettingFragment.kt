package com.example.dailynote.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.example.dailynote.R
import com.example.dailynote.worker.WorkManager


class MySettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)

       val switchSetting = preferenceManager.findPreference<SwitchPreference>(getString(R.string.EnableSettingKey)) as SwitchPreference

        switchSetting.setOnPreferenceChangeListener { _, newValue ->

            val reminderWork =  WorkManager(requireContext())

            Log.d("TAG", "onCreatePreferences: $newValue ")

            if (newValue == true)
            {
              reminderWork.startReminderWork("reminder","To make reminder")
            }else
                reminderWork.cancelReminderWork()
            true
        }
    }

}