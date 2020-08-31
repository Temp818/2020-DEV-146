package com.dev.tictactoe

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.tictactoe.adapter.BoardAdapter
import com.dev.tictactoe.databinding.ActivityTictactoeBinding
import com.dev.tictactoe.game.GameState
import com.dev.tictactoe.game.TicTacToe
import com.dev.tictactoe.viewmodel.TicTacToeViewModel
import com.google.android.material.snackbar.Snackbar

class TicTacToeActivity : AppCompatActivity(), BoardAdapter.Listener {

    private lateinit var viewModel: TicTacToeViewModel
    private lateinit var binding: ActivityTictactoeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTictactoeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.rvBoard.layoutManager = GridLayoutManager(this, TicTacToe.BOARD_SIZE)
        val adapter = BoardAdapter(this)
        binding.rvBoard.adapter = adapter

        viewModel = ViewModelProvider(this).get(TicTacToeViewModel::class.java)

        observeBoardUpdate(adapter)
        observeError()
        observeGameState()
    }

    private fun observeBoardUpdate(adapter: BoardAdapter) {
        viewModel.board.observe(this, { board ->
            adapter.board = board
        })
    }

    private fun observeError() {
        viewModel.error.observe(this, {
            Snackbar.make(
                binding.rvBoard,
                R.string.illegal_move,
                Snackbar.LENGTH_SHORT
            ).show()
        })
    }

    private fun observeGameState() {
        viewModel.gameState.observe(this, {
            when (it) {
                is GameState.Draw -> displayAlertDialog(
                    R.string.draw_game_title,
                    getString(R.string.draw_game_message)
                )
                is GameState.Win -> displayAlertDialog(
                    R.string.win_game_title,
                    String.format(getString(R.string.win_game_message), it.winner.name)
                )
            }
        })
    }

    private fun displayAlertDialog(@StringRes title: Int, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .create()
            .show()
    }

    override fun onCellClick(row: Int, column: Int) {
        viewModel.play(row, column)
    }

    @VisibleForTesting
    fun getViewModelForTesting() = viewModel
}