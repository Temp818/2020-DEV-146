package com.dev.tictactoe.game

import androidx.annotation.VisibleForTesting
import com.dev.tictactoe.board.Board
import com.dev.tictactoe.board.Cell
import com.dev.tictactoe.player.Player

class TicTacToe {

    var currentPlayer: Player = Player.X
    val board = Board(BOARD_SIZE)

    fun goToNextRound() {
        currentPlayer = when (currentPlayer) {
            is Player.X -> Player.O
            is Player.O -> Player.X
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isVerticalWin(): Boolean {
        var winningCell: Cell
        for (column in board.indices) {
            winningCell = board.getCell(0, column)
            for (row in board.indices) {
                if (board.getCell(row, column) != winningCell) {
                    winningCell = Cell.EMPTY
                    break
                }
            }
            if (winningCell != Cell.EMPTY) return true
        }
        return false
    }

    companion object {
        const val BOARD_SIZE = 3
    }
}