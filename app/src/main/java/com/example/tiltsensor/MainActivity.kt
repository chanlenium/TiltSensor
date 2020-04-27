package com.example.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var tileView: TiltView // initiate object tileView with type TiltView class

    // Get SensorManager reference to access sensor, register/unregister of listener
    private val sensorManager by lazy{  // late initiation
        getSystemService(Context.SENSOR_SERVICE) as SensorManager   // down casting(상위클래스가 하위클래스로 되는 것)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set screen not to turn off while the app is operating
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Set screen as LANDSCAPTE mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        super.onCreate(savedInstanceState)

        tileView = TiltView(this) //  construction
        setContentView(tileView)    // set tileView as whole layout
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,    // register sensor to be used (this: 엑티비티에서 센서값을 받도록 함)
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),  // dedicate the type of sensor
            SensorManager.SENSOR_DELAY_NORMAL)  // dedicate how often the sensor value recieved
    }

    // call the func when the sensor value is changed
    override fun onSensorChanged(event: SensorEvent?) {
        // values[0] : x축 값: 위로 기울이면 -10~0, 아래로 기울이면 0~10
        // values[1] : y축 값: 왼쪽으로 기울이면 -10~0, 오른쪽으로 기울이면 0~10
        // values[2] : z축 값: 미사용
        event?.let{
            Log.d("MainActivity", "onSensorChanged: x:" +
                    "${event.values[0]}, y: ${event.values[1]}, z: ${event.values[2]}")

            tileView.onSensorEvent(event)   // Pass sensor values to the TiltView
        }
    } // add SensorEventListener

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // sensor is used only when the activity is operating
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
