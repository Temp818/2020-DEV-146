package com.dev.tictactoe.player

sealed class Player() {
    object X: Player()
    object O: Player()
}