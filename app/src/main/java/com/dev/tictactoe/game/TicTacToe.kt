package com.dev.tictactoe.game

import com.dev.tictactoe.player.Player

class TicTacToe {

    var currentPlayer: Player = Player.X

    fun goToNextRound() {
        currentPlayer = when (currentPlayer) {
            is Player.X -> Player.O
            is Player.O -> Player.X
        }
    }

    companion object {
        const val BOARD_SIZE = 3
    }
}