package com.dev.tictactoe.board

import com.dev.tictactoe.player.Player

class Board(val size: Int) {
    val board = Array(size) { Array(size, init = { Cell.EMPTY }) }
    val indices: IntRange
        get() = board.indices

    fun getCell(row: Int, column: Int) = board[row][column]

    fun setCell(row: Int, column: Int, player: Player) {
        board[row][column] = when(player) {
            Player.X -> Cell.X
            Player.O -> Cell.O
        }
    }
}