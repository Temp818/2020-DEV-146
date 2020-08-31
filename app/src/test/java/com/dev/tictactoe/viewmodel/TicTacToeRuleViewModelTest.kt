package com.dev.tictactoe.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dev.tictactoe.board.Board
import com.dev.tictactoe.board.Cell
import com.dev.tictactoe.exception.IllegalMoveException
import com.dev.tictactoe.game.GameState
import com.dev.tictactoe.game.TicTacToe
import com.dev.tictactoe.player.Player
import com.nhaarman.mockito_kotlin.*
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
        whenever(game.getGameState()).thenReturn(GameState.Playing)
        val boardCaptor: KArgumentCaptor<(Board)> = argumentCaptor()
        val observer = mock<Observer<Board>>()

        viewModel.board.observeForever(observer)

        viewModel.play(0, 0)

        verify(observer).onChanged(boardCaptor.capture())
        verify(game).updateBoard(0, 0)
        verify(game).goToNextRound()

        assertThat(boardCaptor.firstValue.board[0][0]).isEqualTo(Cell.O)
    }

    @Test
    fun testIllegalMove() {
        whenever(game.updateBoard(0, 0)).thenThrow(IllegalMoveException::class.java)

        val boardCaptor: KArgumentCaptor<(Board)> = argumentCaptor()
        val boardObserver = mock<Observer<Board>>()
        val errorCaptor: KArgumentCaptor<(IllegalMoveException)> = argumentCaptor()
        val errorObserver = mock<Observer<IllegalMoveException>>()

        viewModel.board.observeForever(boardObserver)
        viewModel.error.observeForever(errorObserver)

        viewModel.play(0, 0)

        verify(boardObserver, never()).onChanged(boardCaptor.capture())
        verify(game, never()).goToNextRound()
        verify(errorObserver).onChanged(errorCaptor.capture())
        assertThat(errorCaptor.firstValue).isInstanceOf(IllegalMoveException::class.java)
    }

    @Test
    fun testGameStateUpdated() {
        val gameStateCaptor: KArgumentCaptor<(GameState)> = argumentCaptor()
        val gameStateObserver = mock<Observer<GameState>>()
        viewModel.gameState.observeForever(gameStateObserver)

        whenever(game.getGameState()).thenReturn(GameState.Playing)
        viewModel.play(0, 0)

        whenever(game.getGameState()).thenReturn(GameState.Draw)
        viewModel.play(0, 0)

        whenever(game.getGameState()).thenReturn(GameState.Win(Player.O))
        viewModel.play(0, 0)

        verify(game, times(3)).getGameState()
        verify(gameStateObserver, times(3)).onChanged(gameStateCaptor.capture())
        assertThat(gameStateCaptor.firstValue).isEqualTo(GameState.Playing)
        assertThat(gameStateCaptor.secondValue).isEqualTo(GameState.Draw)
        assertThat(gameStateCaptor.thirdValue).isInstanceOf(GameState.Win::class.java)
        assertThat((gameStateCaptor.thirdValue as GameState.Win).winner).isEqualTo(Player.O)
    }

    @Test
    fun testGoToNextRoundOnlyIfNotEndGame() {
        whenever(game.getGameState()).thenReturn(GameState.Playing)
        viewModel.play(0, 0)
        verify(game).goToNextRound()
    }

    @Test
    fun testNotGoToNextRoundIfDraw() {
        whenever(game.getGameState()).thenReturn(GameState.Draw)
        viewModel.play(0, 0)
        verify(game, never()).goToNextRound()
    }

    @Test
    fun testNotGoToNextRoundIfWin() {
        whenever(game.getGameState()).thenReturn(GameState.Win(Player.X))
        viewModel.play(0, 0)
        verify(game, never()).goToNextRound()
    }
}