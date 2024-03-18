package com.example.b2004748_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    private var tvInput: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }
    fun onDigit(view: View) {
        val buttonText = (view as Button).text.toString()
        val currentInput = tvInput?.text.toString()
        // Kiem tra xem dau cham thap phan da duoc them chua
        if (buttonText == "." && currentInput.contains(".")) {
            // da co dau cham thap phan, khong them nua
            return
        }
        tvInput?.append(buttonText)
        lastNumeric = true
    }
    fun onClear(vỉew: View) {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }
    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0] // Value One = 20
                    val two = splitValue[1] // Value Two = 10
                    // Tra lai tien to
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
                else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    // Xoa ".0" khi thuc hien phep tinh voi so nguyen
    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.endsWith(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/") || value.contains("*") || value.contains("-") || value.contains("+"))
        }
    }
}