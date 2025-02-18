package com.example.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {

    private lateinit var resultTextView: TextView
    private var currentInput: String = ""
    private var previousNumber: Double? = null
    private var currentOperation: String? = null
    private val decimalFormat = DecimalFormat("#.##########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout) // Cambio aquí para usar layout.xml

        // Vinculación de vistas
        resultTextView = findViewById(R.id.resultTextView)

        val buttons = listOf(
            R.id.zeroButton to "0", R.id.oneButton to "1", R.id.twoButton to "2",
            R.id.threeButton to "3", R.id.fourButton to "4", R.id.fiveButton to "5",
            R.id.sixButton to "6", R.id.sevenButton to "7", R.id.eightButton to "8",
            R.id.nineButton to "9"
        )

        val operations = mapOf(
            R.id.addButton to "+", R.id.subtractButton to "-",
            R.id.multiplyButton to "×", R.id.divideButton to "÷"
        )

        // Configuración de listeners para botones numéricos
        buttons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener { appendNumber(value) }
        }

        // Configuración de listeners para botones de operaciones
        operations.forEach { (id, op) ->
            findViewById<Button>(id).setOnClickListener { setOperation(op) }
        }

        // Otros botones
        findViewById<Button>(R.id.equalButton).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.clearButton).setOnClickListener { clear() }
        findViewById<Button>(R.id.decimalButton).setOnClickListener { appendDecimal() }
        findViewById<Button>(R.id.percentButton).setOnClickListener { calculatePercent() }
        findViewById<Button>(R.id.signButton).setOnClickListener { changeSign() }
    }

    private fun appendNumber(number: String) {
        if (currentInput == "0") currentInput = "" // Evita ceros innecesarios
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
