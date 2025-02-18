package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainl) // Asigna el layout MainL.xml

        val btnCronometro: Button = findViewById(R.id.btnCronometro)
        val btnCalculadora: Button = findViewById(R.id.btnCalculadora)

        btnCronometro.setOnClickListener {
            val intent = Intent(this, ActCronometro::class.java)
            startActivity(intent)
        }

        btnCalculadora.setOnClickListener {
            val intent = Intent(this, ActCalculadora::class.java)
            startActivity(intent)
        }
    }
}
