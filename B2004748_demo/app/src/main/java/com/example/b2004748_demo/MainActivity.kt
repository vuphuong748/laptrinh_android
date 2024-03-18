package com.example.b2004748_demo
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess
class MainActivity : AppCompatActivity() {
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var button4: Button
    lateinit var button5: Button
    lateinit var button6: Button
    lateinit var button7: Button
    lateinit var button8: Button
    lateinit var button9: Button
    lateinit var button10: Button
    lateinit var playerXResult: TextView
    lateinit var playerOResult: TextView
    lateinit var drawResult: TextView
    var playerTurn = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        button10 = findViewById(R.id.button10)
        playerXResult = findViewById(R.id.playerXResult)
        playerOResult = findViewById(R.id.playerOResult)
        drawResult = findViewById(R.id.drawResult)
        button10.setOnClickListener {
            reset()
        }
    }

    var playerXCount = 0
    var playerOCount = 0
    var drawCount = 0
    fun clickfun(view: View) {
        if (playerTurn) {
            val but = view as Button
            var cellID = 0
            //Toast.makeText(this,but.id.toString() , Toast.LENGTH_SHORT).show();
            when (but.id) {
                R.id.button1 -> cellID = 1
                R.id.button2 -> cellID = 2
                R.id.button3 -> cellID = 3
                R.id.button4 -> cellID = 4
                R.id.button5 -> cellID = 5
                R.id.button6 -> cellID = 6
                R.id.button7 -> cellID = 7
                R.id.button8 -> cellID = 8
                R.id.button9 -> cellID = 9

            }
            playerTurn = false;
            Handler().postDelayed(Runnable { playerTurn = true }, 600)
            playnow(but, cellID)

        }
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1
    fun playnow(buttonSelected: Button, currCell: Int) {
        if (activeUser == 1) {
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#F44336"))
            player1.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            val checkWinner = checkwinner()
            if (checkWinner == 1) {
//                Handler().postDelayed(Runnable { reset() }, 2000)
            }
            else
                activeUser = 2

        } else {
            buttonSelected.text = "O"
            buttonSelected.setTextColor(Color.parseColor("#8BC34A"))
            activeUser = 1
            player2.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            val checkWinner = checkwinner()
            if (checkWinner == 1) {}
//                Handler().postDelayed(Runnable { reset() }, 4000)
        }
    }
    fun checkwinner(): Int {
        if (player1.contains(1) && player1.contains(2) && player1.contains(3)) {
            highlightWinningCells(button1, button2, button3)
        }
        if (player1.contains(4) && player1.contains(5) && player1.contains(6)) {
            highlightWinningCells(button4, button5, button6)
        }
        if (player1.contains(7) && player1.contains(8) && player1.contains(9)) {
            highlightWinningCells(button7, button8, button9)
        }
        if (player1.contains(1) && player1.contains(4) && player1.contains(7)) {
            highlightWinningCells(button1, button4, button7)
        }
        if (player1.contains(2) && player1.contains(5) && player1.contains(8)) {
            highlightWinningCells(button2, button5, button8)
        }
        if (player1.contains(3) && player1.contains(6) && player1.contains(9)) {
            highlightWinningCells(button3, button6, button9)
        }
        if (player1.contains(1) && player1.contains(5) && player1.contains(9)) {
            highlightWinningCells(button1, button5, button9)
        }
        if (player1.contains(3) && player1.contains(5) && player1.contains(7)) {
            highlightWinningCells(button3, button5, button7)
        }
        //-----------------------------------------
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)) {
            highlightWinningCells(button1, button2, button3)
        }
        if (player2.contains(4) && player2.contains(5) && player2.contains(6)) {
            highlightWinningCells(button4, button5, button6)
        }
        if (player2.contains(7) && player2.contains(8) && player2.contains(9)) {
            highlightWinningCells(button7, button8, button9)
        }
        if (player2.contains(1) && player2.contains(4) && player2.contains(7)) {
            highlightWinningCells(button1, button4, button7)
        }
        if (player2.contains(2) && player2.contains(5) && player2.contains(8)) {
            highlightWinningCells(button2, button5, button8)
        }
        if (player2.contains(3) && player2.contains(6) && player2.contains(9)) {
            highlightWinningCells(button3, button6, button9)
        }
        if (player2.contains(1) && player2.contains(5) && player2.contains(9)) {
            highlightWinningCells(button1, button5, button9)
        }
        if (player2.contains(3) && player2.contains(5) && player2.contains(7)) {
            highlightWinningCells(button3, button5, button7)
        }
        //-----------------------------------------

        if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
            (player1.contains(1) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) ||
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
            (player1.contains(1) && player1.contains(5) && player1.contains(9)) ||
            (player1.contains(3) && player1.contains(5) && player1.contains(7)) ||
            (player1.contains(2) && player1.contains(5) && player1.contains(8))
        ) {
            playerXCount += 1
            buttonDisable()
            disableReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player X Wins!!"+"\n\n"+"Do you want to play again?")
            build.setPositiveButton("Ok") { dialog, which ->
                //Quay lai mac dinh
                resetButtonColors(button1, button2, button3, button4, button5, button6, button7, button8, button9)
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)

            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1

        } else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(3) && player2.contains(5) && player2.contains(7)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8))
        ) {
            playerOCount += 1
            buttonDisable()
            disableReset()
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player O Wins!"+"\n\n"+"Do you want to play again?")
            build.setPositiveButton("Ok") { dialog, which ->
                //Quay lai mac dinh
                resetButtonColors(button1, button2, button3, button4, button5, button6, button7, button8, button9)
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { build.show() }, 2000)
            return 1
        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(
                4
            ) && emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) &&
            emptyCells.contains(8) && emptyCells.contains(9)
        ) {
            drawCount += 1
            val build = AlertDialog.Builder(this)
            build.setTitle("Game Draw")
            build.setMessage("Nobody Wins" + "\n\n" + "Do you want to play again?")
            build.setPositiveButton("Ok") { dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
            }
            build.show()
            return 1

        }
        return 0
    }

    fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1;
        for (i in 1..9) {
            var buttonselected: Button?
            buttonselected = when (i) {
                1 -> button1
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> {
                    button1
                }
            }
            buttonselected.isEnabled = true
            buttonselected.text = ""
            playerXResult.text = "Player X : $playerXCount"
            playerOResult.text = "Player O : $playerOCount"
            drawResult.text = "Draw: $drawCount"
        }
    }

    fun buttonDisable() {
        for (i in 1..9) {
            val buttonSelected = when (i) {
                1 -> button1
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> {
                    button1
                }

            }
            if (buttonSelected.isEnabled == true)
                buttonSelected.isEnabled = false
        }
    }
    fun disableReset() {
        button10.isEnabled = false
        Handler().postDelayed(Runnable { button10.isEnabled = true }, 2200)
    }

    // Tô màu lên các ô chiến thắng
    fun highlightWinningCells(vararg buttons: Button) {
        buttons.forEach { it.setBackgroundColor(Color.YELLOW) }
    }
    // Xóa màu các ô chiến thắng
    fun resetButtonColors(vararg buttons: Button) {
        buttons.forEach { it.setBackgroundResource(R.drawable.button_border) }
    }

}