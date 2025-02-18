package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class ActCalculadora : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var btnBackToMain: Button  // 🔥 Se declara correctamente

    private var currentInput: String = ""
    private var previousNumber: Double? = null
    private var currentOperation: String? = null
    private val decimalFormat = DecimalFormat("#.##########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculadora) // 🔥 Se asegura que coincida con el XML

        // Inicializar vistas
        resultTextView = findViewById(R.id.resultTextView)
        btnBackToMain = findViewById(R.id.btnBackToMain)  // 🔥 Se inicializa correctamente

        // Configurar botón de regreso al menú principal
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, Main::class.java) // 🔥 Se asegura que Intent está importado
            startActivity(intent)
            finish()
        }

        // Configuración de botones numéricos
        val buttons = listOf(
            R.id.zeroButton to "0", R.id.oneButton to "1", R.id.twoButton to "2",
            R.id.threeButton to "3", R.id.fourButton to "4", R.id.fiveButton to "5",
            R.id.sixButton to "6", R.id.sevenButton to "7", R.id.eightButton to "8",
            R.id.nineButton to "9"
        )

        buttons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener { appendNumber(value) }
        }

        // Configuración de botones de operaciones
        val operations = mapOf(
            R.id.addButton to "+", R.id.subtractButton to "-",
            R.id.multiplyButton to "×", R.id.divideButton to "÷"
        )

        operations.forEach { (id, op) ->
            findViewById<Button>(id).setOnClickListener { setOperation(op) }
        }

        // Configuración de otros botones
        findViewById<Button>(R.id.equalButton).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.clearButton).setOnClickListener { clear() }
        findViewById<Button>(R.id.decimalButton).setOnClickListener { appendDecimal() }
        findViewById<Button>(R.id.percentButton).setOnClickListener { calculatePercent() }
        findViewById<Button>(R.id.signButton).setOnClickListener { changeSign() }
    }

    private fun appendNumber(number: String) {
        if (currentInput == "0") currentInput = ""
        currentInput += number
        updateResultTextView()
    }

    private fun setOperation(operation: String) {
        if (currentInput.isNotEmpty()) {
            previousNumber = currentInput.toDouble()
            currentInput = ""
            currentOperation = operation
        }
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && currentOperation != null && previousNumber != null) {
            val currentNumber = currentInput.toDouble()
            val result = when (currentOperation) {
                "+" -> previousNumber!! + currentNumber
                "-" -> previousNumber!! - currentNumber
                "×" -> previousNumber!! * currentNumber
                "÷" -> if (currentNumber != 0.0) previousNumber!! / currentNumber else null
                else -> null
            }

            currentInput = result?.let { decimalFormat.format(it) } ?: "Error"
            updateResultTextView()
            currentOperation = null
            previousNumber = null
        }
    }

    private fun clear() {
        currentInput = ""
        previousNumber = null
        currentOperation = null
        updateResultTextView()
    }

    private fun appendDecimal() {
        if (!currentInput.contains(".")) {
            currentInput += if (currentInput.isEmpty()) "0." else "."
            updateResultTextView()
        }
    }

    private fun calculatePercent() {
        if (currentInput.isNotEmpty()) {
            val number = currentInput.toDouble() / 100.0
            currentInput = decimalFormat.format(number)
            updateResultTextView()
        }
    }

    private fun changeSign() {
        if (currentInput.isNotEmpty()) {
            val number = currentInput.toDouble() * -1
            currentInput = decimalFormat.format(number)
            updateResultTextView()
        }
    }

    private fun updateResultTextView() {
        resultTextView.text = currentInput.ifEmpty { "0" }
    }
}
