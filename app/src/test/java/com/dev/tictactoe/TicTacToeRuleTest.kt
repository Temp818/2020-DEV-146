package com.dev.tictactoe

import com.dev.tictactoe.game.TicTacToe
import com.dev.tictactoe.player.Player
import org.junit.Test

class TicTacToeRuleTest {

    @Test
    fun testFirstPlayerIsX() {
        val game = TicTacToe()
        val currentPlayer = game.currentPlayer
        assert(currentPlayer is Player.X)
    }
}