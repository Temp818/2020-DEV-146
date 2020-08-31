package com.dev.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.tictactoe.adapter.BoardAdapter
import com.dev.tictactoe.databinding.ActivityTictactoeBinding
import com.dev.tictactoe.game.TicTacToe

class TicTacToeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTictactoeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTictactoeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.rvBoard.layoutManager = GridLayoutManager(this, TicTacToe.BOARD_SIZE)
        val adapter = BoardAdapter()
        binding.rvBoard.adapter = adapter
    }
}