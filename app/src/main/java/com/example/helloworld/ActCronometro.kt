package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActCronometro : AppCompatActivity() {

    private lateinit var tvCronometro: TextView
    private lateinit var btnBackToMain: Button // 🔥 Se declara correctamente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cronometro) // 🔥 Aseguramos que coincida con el XML

        tvCronometro = findViewById(R.id.tvCronometro)
        btnBackToMain = findViewById(R.id.btnBackToMain) // 🔥 Se inicializa correctamente

        btnBackToMain.setOnClickListener {
            val intent = Intent(this, Main::class.java)
            startActivity(intent)
            finish()
        }
    }
}
