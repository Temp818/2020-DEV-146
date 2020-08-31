package com.dev.tictactoe

import com.dev.tictactoe.board.Cell
import com.dev.tictactoe.exception.IllegalMoveException
import com.dev.tictactoe.game.GameState
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

    @Test
    fun testIsLeftTopStartDiagonalWin() {
        val game = TicTacToe()
        game.board.setCell(0, 0, Player.X)
        game.board.setCell(1, 1, Player.X)
        game.board.setCell(2, 2, Player.X)
        assertThat(game.isLeftTopStartDiagonalWin()).isTrue
    }

    @Test
    fun testIsRightTopStartDiagonalWin() {
        val game = TicTacToe()
        game.board.setCell(0, 2, Player.X)
        game.board.setCell(1, 1, Player.X)
        game.board.setCell(2, 0, Player.X)
        assertThat(game.isRightTopStartDiagonalWin()).isTrue
    }

    @Test
    fun testIsNotLeftTopStartDiagonalWin() {
        val game = TicTacToe()
        game.board.setCell(0, 0, Player.X)
        game.board.setCell(1, 1, Player.O)
        game.board.setCell(2, 2, Player.X)
        assertThat(game.isLeftTopStartDiagonalWin()).isFalse
    }

    @Test
    fun testIsNotRightTopStartDiagonalWin() {
        val game = TicTacToe()
        game.board.setCell(0, 2, Player.X)
        game.board.setCell(1, 1, Player.O)
        game.board.setCell(2, 0, Player.X)
        assertThat(game.isRightTopStartDiagonalWin()).isFalse
    }

    @Test
    fun testIsNotLeftTopStartDiagonalWinWithEmptyCells() {
        val game = TicTacToe()
        assertThat(game.isLeftTopStartDiagonalWin()).isFalse
    }

    @Test
    fun testIsNotRightTopStartDiagonalWinWithEmptyCells() {
        val game = TicTacToe()
        assertThat(game.isRightTopStartDiagonalWin()).isFalse
    }

    @Test
    fun testIsDiagonalWin() {
        val game = TicTacToe()
        game.board.setCell(0, 2, Player.X)
        game.board.setCell(1, 1, Player.X)
        game.board.setCell(2, 0, Player.X)
        assertThat(game.isDiagonalWin()).isTrue
    }

    @Test
    fun testIsNotDiagonalWin() {
        val game = TicTacToe()
        game.board.setCell(0, 1, Player.X)
        game.board.setCell(1, 1, Player.X)
        game.board.setCell(2, 0, Player.X)
        assertThat(game.isDiagonalWin()).isFalse
    }

    @Test(expected = IllegalMoveException::class)
    fun testTryToPlayAlreadyPlayedPosition() {
        val game = TicTacToe()
        game.updateBoard(0, 0)
        game.updateBoard(0, 0)
    }

    @Test
    fun testUpdateBoard() {
        val game = TicTacToe()
        assertThat(game.board.board[0][0]).isEqualTo(Cell.EMPTY)
        game.updateBoard(0, 0)
        assertThat(game.board.board[0][0]).isEqualTo(Cell.X)
    }

    @Test
    fun testDrawGame() {
        val game = TicTacToe()
        game.board.setCell(0, 0, Player.O)
        game.board.setCell(0, 1, Player.X)
        game.board.setCell(0, 2, Player.O)

        game.board.setCell(1, 0, Player.X)
        game.board.setCell(1, 1, Player.X)
        game.board.setCell(1, 2, Player.O)

        game.board.setCell(2, 0, Player.O)
        game.board.setCell(2, 1, Player.O)
        game.board.setCell(2, 2, Player.X)

        assertThat(game.getGameState()).isEqualTo(GameState.Draw)
    }

    @Test
    fun testWinGame() {
        val game = TicTacToe()
        game.board.setCell(0, 0, Player.X)
        game.board.setCell(0, 1, Player.X)
        game.board.setCell(0, 2, Player.X)

        assertThat(game.getGameState()).isInstanceOf(GameState.Win::class.java)
        assertThat((game.getGameState() as GameState.Win).winner).isEqualTo(Player.X)
    }

    @Test
    fun testPlayingGame() {
        val game = TicTacToe()
        game.board.setCell(0, 0, Player.X)
        game.board.setCell(0, 1, Player.O)
        game.board.setCell(0, 2, Player.X)

        assertThat(game.getGameState()).isEqualTo(GameState.Playing)
    }
}