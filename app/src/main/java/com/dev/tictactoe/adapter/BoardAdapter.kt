package com.dev.tictactoe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.dev.tictactoe.R
import com.dev.tictactoe.board.Board
import com.dev.tictactoe.game.TicTacToe

class BoardAdapter(private val listener: Listener) :
    RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    var board: Board = Board(TicTacToe.BOARD_SIZE)
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = TicTacToe.BOARD_SIZE * TicTacToe.BOARD_SIZE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.board_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row: Int = position / TicTacToe.BOARD_SIZE
        val column: Int = position % TicTacToe.BOARD_SIZE
        holder.bind(listener, row, column, board)
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var row: Int = 0
        private var column: Int = 0
        private var listener: Listener? = null
        private val button: Button = itemView.findViewById(R.id.buttonCell)

        init {
            button.setOnClickListener(this)
        }

        fun bind(listener: Listener, row: Int, column: Int, board: Board) {
            this.listener = listener
            this.row = row
            this.column = column
            button.text = board.getCell(row, column).value
        }

        override fun onClick(view: View?) {
            listener?.onCellClick(row, column)
        }
    }

    interface Listener {
        fun onCellClick(row: Int, column: Int)
    }
}