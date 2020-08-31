package com.dev.tictactoe.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.tictactoe.board.Board
import com.dev.tictactoe.exception.IllegalMoveException
import com.dev.tictactoe.game.GameState
import com.dev.tictactoe.game.TicTacToe

class TicTacToeViewModel : ViewModel() {

    private var _board = MutableLiveData<Board>()
    val board: LiveData<Board>
        get() = _board

    private var _error = MutableLiveData<IllegalMoveException>()
    val error: LiveData<IllegalMoveException>
        get() = _error

    private var _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState>
        get() = _gameState

    private var game: TicTacToe = TicTacToe()

    fun play(row: Int, column: Int) {
        try {
            _board.value = game.updateBoard(row, column)
            val gameState = game.getGameState()
            when (gameState) {
                GameState.Playing -> game.goToNextRound()
            }
            _gameState.value = gameState
        } catch (exception: IllegalMoveException) {
            _error.value = exception
        }
    }

    @VisibleForTesting
    fun setGameForTesting(mockGame: TicTacToe) {
        this.game = mockGame
    }
}