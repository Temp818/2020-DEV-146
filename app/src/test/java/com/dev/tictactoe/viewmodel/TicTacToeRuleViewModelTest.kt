package com.dev.tictactoe.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dev.tictactoe.board.Board
import com.dev.tictactoe.board.Cell
import com.dev.tictactoe.game.TicTacToe
import com.dev.tictactoe.player.Player
import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TicTacToeRuleViewModelTest {

    private lateinit var viewModel: TicTacToeViewModel
    private lateinit var game: TicTacToe

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = TicTacToeViewModel()
        game = mock()
        viewModel.setGameForTesting(game)
    }

    @Test
    fun testBoardUpdated() {
        whenever(game.updateBoard(0, 0)).thenReturn(Board(TicTacToe.BOARD_SIZE).also {
            it.setCell(0, 0, Player.O)
        })
        val boardCaptor: KArgumentCaptor<(Board)> = argumentCaptor()
        val observer = mock<Observer<Board>>()

        viewModel.board.observeForever(observer)

        viewModel.play(0, 0)

        verify(observer).onChanged(boardCaptor.capture())
        verify(game).updateBoard(0, 0)
        verify(game).goToNextRound()

        assertThat(boardCaptor.firstValue.board[0][0]).isEqualTo(Cell.O)
    }
}