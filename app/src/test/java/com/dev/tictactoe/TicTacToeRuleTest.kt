package com.dev.tictactoe

import com.dev.tictactoe.game.TicTacToe
import com.dev.tictactoe.player.Player
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TicTacToeRuleTest {

    @Test
    fun testFirstPlayerIsX() {
        val game = TicTacToe()
        val currentPlayer = game.currentPlayer
        assert(currentPlayer is Player.X)
    }

    @Test
    fun testSwitchPlayerAtEachRound() {
        val game = TicTacToe()
        val maxRound = TicTacToe.BOARD_SIZE * TicTacToe.BOARD_SIZE

        for (round in 0 until maxRound) {
            assertThat(game.currentPlayer).isInstanceOf(if (round % 2 == 0) Player.X::class.java else Player.O::class.java)
            game.goToNextRound()
        }
    }
}