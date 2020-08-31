package com.dev.tictactoe.board

import com.dev.tictactoe.player.Player
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BoardTest {

    @Test
    fun testBoardIsInitializedEmpty() {
        val board = Board(3)
        board.board.forEach { cells ->
            cells.forEach {
                assertThat(it).isEqualTo(Cell.EMPTY)
            }
        }
    }

    @Test
    fun testSetCell() {
        val board = Board(3)
        assertThat(board.getCell(0, 0)).isEqualTo(Cell.EMPTY)
        board.setCell(0, 0, Player.O)
        assertThat(board.getCell(0, 0)).isEqualTo(Cell.O)
        board.setCell(1, 1, Player.X)
        assertThat(board.getCell(1, 1)).isEqualTo(Cell.X)
    }

    @Test
    fun testIsFullyFill() {
        val board = Board(3)
        assertThat(board.isFullyFill).isFalse
        for(row in 0 until 3) {
            for(column in 0 until 3) {
                board.setCell(row,column, Player.X)
            }
        }
        assertThat(board.isFullyFill).isTrue
    }
}