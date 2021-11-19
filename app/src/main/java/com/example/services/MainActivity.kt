package com.example.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.channels.InterruptedByTimeoutException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {




    private val description : String = "hello world this is notification detail"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mInit()

        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss aaa")
        val  dateTime = simpleDateFormat.format(calendar.time).toString()
        //tv_data_time.text = LocalDateTime.now().toString()
       // tv_data_time.text = LocalDateTime.now().
        tv_data_time.text = dateTime

    }

    private fun mInit() {

        btn_notification.setOnClickListener(this)
        btn_start_service.setOnClickListener(this)
        btn_stop_service.setOnClickListener(this)
        btn_luck_draw.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_notification -> notificationAlert()
            R.id.btn_start_service -> mStartService()
            R.id.btn_stop_service -> mStopService()
            R.id.btn_luck_draw -> mLuckyDraw()
        }
    }

    private fun notificationAlert() {
            val notificationId = 111
            val channelId  = "hello"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = getString(R.string.app_name)
                val descriptionText = getString(R.string.app_name)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(channelId, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                // Create an explicit intent for an Activity in your app
                val intent = Intent(this, NotificationDetail::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

                val builder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setContentTitle("My notification")
                    .setContentText("Hello World! Welcome to the Kotlin Language")
                    //.setTimeoutAfter(2000)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

                with(NotificationManagerCompat.from(this)) {
                    // notificationId is a unique int for each notification that you must define
                    notify(notificationId, builder.build())
                }
            }
    }

    private fun mStartService() {
        val intent = Intent(this, MyForegroundService::class.java)
            startForegroundService(intent)
            Log.i("TAG", "Foreground Service....")


//       val intent =  Intent(this, MyForegroundService::class.java)
//                startService(intent)
//            Log.i("TAG", "Background Service....")

    }

    private fun mStopService() {
        val intent = Intent(this, MyForegroundService::class.java)
        stopService(intent)
        Log.i("TAG", "Stop Service...")
    }


    fun mLuckyDraw() {
        startActivity(Intent(this,LuckDrawSpinner::class.java))
    }

    fun simpleNotificaiton() {

        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss aaa z")
        val  dateTime = simpleDateFormat.format(calendar.time).toString()

        val intent = Intent(this, NotificationDetail::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = Notification.Builder(this,"Hello")
            .setContentTitle("Time Service")
            .setContentText(dateTime.toString())
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentIntent(pendingIntent)
            .build()
    }
}