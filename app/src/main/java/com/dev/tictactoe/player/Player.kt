package com.dev.tictactoe.player

sealed class Player(val name: String) {
    object X: Player("X")
    object O: Player("O")
}