package com.dev.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.tictactoe.adapter.BoardAdapter
import com.dev.tictactoe.databinding.ActivityTictactoeBinding
import com.dev.tictactoe.game.TicTacToe
import com.dev.tictactoe.viewmodel.TicTacToeViewModel

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

        viewModel.board.observe(this, { board ->
            adapter.board = board
        })
    }

    override fun onCellClick(row: Int, column: Int) {
        viewModel.play(row, column)
    }
}