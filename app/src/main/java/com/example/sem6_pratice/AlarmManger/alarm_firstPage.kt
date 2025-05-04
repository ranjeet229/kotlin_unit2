package com.example.sem6_pratice.AlarmManger

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sem6_pratice.databinding.ActivityAlarmFirstPageBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class alarm_firstPage : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmFirstPageBinding
    private lateinit var picker: MaterialTimePicker
    private var calendar: Calendar? = null
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmFirstPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        binding.btnsettime.setOnClickListener {
            showTimePicker()
        }

        binding.btnsetalrarm.setOnClickListener {
            setAlarm()
        }

        binding.btncancelalarm.setOnClickListener {
            cancelAlarm()
        }
    }

    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(supportFragmentManager, "foxandroid")

        picker.addOnPositiveButtonClickListener {
            val hour = if (picker.hour > 12) picker.hour - 12 else if (picker.hour == 0) 12 else picker.hour
            val amPm = if (picker.hour >= 12) "PM" else "AM"
            val minute = String.format("%02d", picker.minute)
            binding.btnsettime.text = String.format("%02d:%s %s", hour, minute, amPm)

            calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, picker.hour)
                set(Calendar.MINUTE, picker.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                if (before(Calendar.getInstance())) {
                    add(Calendar.DATE, 1)
                }
            }
        }
    }

    private fun setAlarm() {
        if (calendar == null) {
            Toast.makeText(this, "Please select time first", Toast.LENGTH_SHORT).show()
            return
        }

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar!!.timeInMillis,
            pendingIntent
        )

        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "foxandroid",
                "foxandroidReminderChannel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for Alarm Manager"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
