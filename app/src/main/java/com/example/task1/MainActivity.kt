package com.example.task1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var board: Array<Array<Button>>
    private var currentPlayer = 'X'
    private var gameActive = true
    private lateinit var playAgainButton: Button
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        playAgainButton = findViewById(R.id.playAgainButton)


        board = Array(3) { row ->
            Array(3) { col ->
                val buttonId = resources.getIdentifier("button${row * 3 + col}", "id", packageName)
                findViewById<Button>(buttonId).apply {
                    setOnClickListener { onButtonClick(this, row, col) }
                }
            }
        }


        playAgainButton.setOnClickListener {
            startGame()
        }
    }

    private fun onButtonClick(button: Button, row: Int, col: Int) {
        if (!gameActive || button.text.isNotEmpty()) return

        button.text = currentPlayer.toString()

        if (checkWin()) {
            statusText.text = "Player $currentPlayer Wins!"
            gameActive = false
            playAgainButton.visibility = Button.VISIBLE
        } else if (isBoardFull()) {
            statusText.text = "It's a Draw!"
            gameActive = false
            playAgainButton.visibility = Button.VISIBLE
        } else {
            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            statusText.text = "Player $currentPlayer's Turn:"
        }
    }

    private fun checkWin(): Boolean {

        for (i in 0..2) {
            if (board[i][0].text == currentPlayer.toString() &&
                board[i][1].text == currentPlayer.toString() &&
                board[i][2].text == currentPlayer.toString()
            ) return true

            if (board[0][i].text == currentPlayer.toString() &&
                board[1][i].text == currentPlayer.toString() &&
                board[2][i].text == currentPlayer.toString()
            ) return true
        }

        if (board[0][0].text == currentPlayer.toString() &&
            board[1][1].text == currentPlayer.toString() &&
            board[2][2].text == currentPlayer.toString()
        ) return true

        if (board[0][2].text == currentPlayer.toString() &&
            board[1][1].text == currentPlayer.toString() &&
            board[2][0].text == currentPlayer.toString()
        ) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it.text.isNotEmpty() } }
    }

    private fun startGame() {

        gameActive = true
        currentPlayer = 'X'
        statusText.text = "Player X's Turn:"
        playAgainButton.visibility = Button.GONE // Hide the Play Again button

        for (row in board) {
            for (button in row) {
                button.text = ""
            }
        }
    }
}




