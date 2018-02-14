package com.example.asus.midtermexam

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import java.util.*

/**
 * Created by ASUS on 13-Feb-18.
 */
class AlarmClock : AppCompatActivity() {
    lateinit var am: AlarmManager
    lateinit var tp: TimePicker
    lateinit var txt : TextView
    lateinit var con: Context
    lateinit var btnStop: Button
    lateinit var btnStart: Button
    lateinit var updatetxt: TextView
    lateinit var pi: PendingIntent
    var sec:Int = 0
    var nTimer : alarm = alarm()
    var hour:Int = 0
    var min:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm)
        this.con=this
        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        tp = findViewById(R.id.timePicker) as TimePicker
        btnStart = findViewById(R.id.button2) as Button
        btnStop = findViewById(R.id.button3) as Button
        var calendar: Calendar = Calendar.getInstance()
        var myIntent: Intent = Intent(this,MyAlarm::class.java)
        updatetxt= findViewById(R.id.textView2) as TextView
        btnStart.setOnClickListener(object : View.OnClickListener{
            @SuppressLint("NewApi")
            override fun onClick(p0: View?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                    calendar.set(Calendar.HOUR_OF_DAY,tp.hour)
                    calendar.set(Calendar.MINUTE,tp.minute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = tp.hour
                    min = tp.minute
                    sec = 0


                }
                else{




                    calendar.set(Calendar.HOUR_OF_DAY,tp.currentHour)
                    calendar.set(Calendar.MINUTE,tp.currentMinute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = tp.currentHour
                    min = tp.currentMinute
                    // sec = System.currentTimeMillis().toInt()





                }


                var hr_string: String = hour.toString()
                var min_string: String = min.toString()
                var sec_string: String = sec.toString()
                if( hour>12){
                    hr_string = (hour - 12).toString()
                }
                if( min<10){
                    min_string = "0$min"
                }

                setAlarm("Alarm set To: $hr_string : $min_string " )
                myIntent.putExtra("extra","on")
                pi = PendingIntent.getBroadcast(this@AlarmClock,0,myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                am.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pi)

            }


            private fun setAlarm(s: String) {

                updatetxt.setText(s)
            }


        })

        btnStop.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

                updatetxt.setText("Alarm Off")
                pi = PendingIntent.getBroadcast(this@AlarmClock,0,myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                am.cancel(pi)
                myIntent.putExtra("extra","off")
                sendBroadcast(myIntent)
            }
        })

    }









    inner class alarm : AsyncTask<Int, String, Void>() {
        override fun doInBackground(vararg p0: Int?): Void? {
            var limit = p0[0]
            var limit2 = p0[1]
            var limit3 = p0[2]
            var i = limit

            var i2 = limit2

            var i3 = limit3
            try {
                //for (i2 in limit2!! downTo 0) {

                while(i3!! >=0) {

                    if (i2!! >= 60) {
                        while (i2!! > 60) {
                            i2 = i2.minus(60)
                            i3 = i3!!.plus(1)

                        }
                    }
                    while (i2!! >= 0) {

                        if (i!! >= 60) {
                            while (i > 60) {
                                i -= 60
                                i2 = i2!!.plus(1)

                            }
                        }
                        while (i!! > 0) {
                            publishProgress(i.toString(), i2.toString(),i3.toString())
                            Thread.sleep(1000)
                            i = i!!.minus(1)
                        }
                        if (i == 0) {
                            i = 60
                        }
                        i2 = i2!!.minus(1)
                    }

                    i3 = i3!!.minus(1)
                }


                //}
                val vibratorService: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibratorService.vibrate(1000)
                nTimer!!.cancel(true)
                //txt1!!.setText("00:00")

            } catch (ex: InterruptedException) {
                ex.stackTrace
                // saver = limit!!

            }


            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
        }



    }


}