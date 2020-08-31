package com.dev.tictactoe.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.tictactoe.board.Board
import com.dev.tictactoe.game.TicTacToe

class TicTacToeViewModel : ViewModel() {

    private var _board = MutableLiveData<Board>()
    val board: LiveData<Board>
        get() = _board

    private var game: TicTacToe = TicTacToe()

    fun play(row: Int, column: Int) {
        _board.value = game.updateBoard(row, column)
        game.goToNextRound()
    }

    @VisibleForTesting
    fun setGameForTesting(mockGame: TicTacToe) {
        this.game = mockGame
    }
}