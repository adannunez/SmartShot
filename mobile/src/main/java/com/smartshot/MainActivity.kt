package com.smartshot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*
import java.util.logging.Logger
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {
    private val logger = Logger.getLogger("MainActivity")
    private lateinit var newShotTask: TimerTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val shotCallView = findViewById<TextView>(R.id.ShotCall)
        newShotTask = Timer().scheduleAtFixedRate(
                delay = 1000,
                period = 3000,
                action = {
                    val oldValue = shotCallView.text
                    var newValue = oldValue
                    while (oldValue == newValue) {
                        newValue = (Random().nextInt(5)+1).toString()
                    }
                    logger.info("Setting value to $newValue")
                    shotCallView.text = newValue.toString()
                }
        )
    }

    override fun onPause() {
        super.onPause()
        newShotTask.cancel()
    }
}
