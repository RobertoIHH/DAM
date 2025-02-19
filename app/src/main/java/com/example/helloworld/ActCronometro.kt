package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActCronometro : AppCompatActivity() {

    private lateinit var tvCronometro: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button
    private lateinit var btnBackToMain: Button

    private var running = false
    private var seconds = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cronometro)

        tvCronometro = findViewById(R.id.tvCronometro)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnReset = findViewById(R.id.btnReset)
        btnBackToMain = findViewById(R.id.btnBackToMain)

        btnStart.setOnClickListener { startTimer() }
        btnStop.setOnClickListener { stopTimer() }
        btnReset.setOnClickListener { resetTimer() }
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, Main::class.java)
            startActivity(intent)
            finish()
        }

        updateTimer()
    }

    private fun startTimer() {
        if (!running) {
            running = true
            handler.post(runnable)
        }
    }

    private fun stopTimer() {
        running = false
        handler.removeCallbacks(runnable)
    }

    private fun resetTimer() {
        stopTimer()  // Detenemos el cronómetro
        seconds = 0  // Reiniciamos el contador
        updateTimer()  // Actualizamos la UI
        stopTimer()  // Detenemos el cronómetro
        startTimer()  // Iniciamos automáticamente después del reinicio
    }

    private val runnable = object : Runnable {
        override fun run() {
            if (running) {
                seconds++
                updateTimer()
                handler.postDelayed(this, 1000) // Ejecutar cada segundo
            }
        }
    }

    private fun updateTimer() {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        tvCronometro.text = String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
}
