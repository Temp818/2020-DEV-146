package com.dev.tictactoe.game

import androidx.annotation.VisibleForTesting
import com.dev.tictactoe.board.Board
import com.dev.tictactoe.board.Cell
import com.dev.tictactoe.exception.IllegalMoveException
import com.dev.tictactoe.player.Player

class TicTacToe {

    var currentPlayer: Player = Player.X
    val board = Board(BOARD_SIZE)

    fun updateBoard(row: Int, column: Int): Board {
        if (board.getCell(row, column) == Cell.EMPTY) {
            board.setCell(row, column, currentPlayer)
            return board
        } else {
            throw IllegalMoveException(Throwable("Illegal move"))
        }
    }

    fun goToNextRound() {
        currentPlayer = when (currentPlayer) {
            is Player.X -> Player.O
            is Player.O -> Player.X
        }
    }

    fun getGameState(): GameState {
        val hasWinner = isVerticalWin() || isHorizontalWin() || isDiagonalWin()
        if (hasWinner) {
            return GameState.Win(currentPlayer)
        }
        if (board.isFullyFill) {
            return GameState.Draw
        }
        return GameState.Playing
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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isHorizontalWin(): Boolean {
        var winningCell: Cell
        for (row in board.indices) {
            winningCell = board.getCell(row, 0)
            for (column in board.indices) {
                if (board.getCell(row, column) != winningCell) {
                    winningCell = Cell.EMPTY
                    break
                }
            }
            if (winningCell != Cell.EMPTY) return true
        }
        return false
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isDiagonalWin(): Boolean {
        return isLeftTopStartDiagonalWin() || isRightTopStartDiagonalWin()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isLeftTopStartDiagonalWin(): Boolean {
        val winningCell = board.getCell(0, 0)
        for (row in board.indices) {
            if (board.getCell(row, row) == Cell.EMPTY || board.getCell(row, row) != winningCell) {
                return false
            }
        }
        return true
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isRightTopStartDiagonalWin(): Boolean {
        val lastIndex = board.size - 1
        val winningCell = board.getCell(0, lastIndex)
        for (row in board.indices.reversed()) {
            val column = lastIndex - row
            if (board.getCell(row, column) == Cell.EMPTY || board.getCell(
                    row,
                    column
                ) != winningCell
            ) {
                return false
            }
        }
        return true
    }

    companion object {
        const val BOARD_SIZE = 3
    }
}