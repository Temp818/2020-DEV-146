package com.dev.tictactoe.exception

class IllegalMoveException(val throwable: Throwable?): IllegalStateException(throwable)