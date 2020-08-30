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

    @Test
    fun testIsVerticalWin() {
        var game = TicTacToe()
        for (column in 0 until TicTacToe.BOARD_SIZE) {
            game.board.setCell(0, column, Player.X)
            game.board.setCell(1, column, Player.X)
            game.board.setCell(2, column, Player.X)
            assertThat(game.isVerticalWin()).isTrue
            game = TicTacToe()
        }
    }

    @Test
    fun testIsNotVerticalWin() {
        var game = TicTacToe()
        for (column in 0 until TicTacToe.BOARD_SIZE) {
            game.board.setCell(0, column, Player.O)
            game.board.setCell(1, column, Player.X)
            game.board.setCell(2, column, Player.X)
            assertThat(game.isVerticalWin()).isFalse
            game = TicTacToe()
        }
    }

    @Test
    fun testIsNotVerticalWinWithEmptyCells() {
        val game = TicTacToe()
        assertThat(game.isVerticalWin()).isFalse
    }

    @Test
    fun testIsHorizontalWin() {
        var game = TicTacToe()
        for (row in 0 until TicTacToe.BOARD_SIZE) {
            game.board.setCell(row, 0, Player.X)
            game.board.setCell(row, 1, Player.X)
            game.board.setCell(row, 2, Player.X)
            assertThat(game.isHorizontalWin()).isTrue
            game = TicTacToe()
        }
    }

    @Test
    fun testIsNotHorizontalWin() {
        var game = TicTacToe()
        for (row in 0 until TicTacToe.BOARD_SIZE) {
            game.board.setCell(row, 0, Player.X)
            game.board.setCell(row, 1, Player.X)
            game.board.setCell(row, 2, Player.O)
            assertThat(game.isHorizontalWin()).isFalse
            game = TicTacToe()
        }
    }

    @Test
    fun testIsNotHorizontalWinEmptyCells() {
        val game = TicTacToe()
        assertThat(game.isHorizontalWin()).isFalse
    }
}