package com.dev.tictactoe.game

import com.dev.tictactoe.player.Player

sealed class GameState {
    object Playing: GameState()
    object Draw: GameState()
    class Win(val winner: Player): GameState()
}