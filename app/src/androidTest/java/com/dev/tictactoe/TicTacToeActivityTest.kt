package com.dev.tictactoe

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev.tictactoe.board.Cell
import com.dev.tictactoe.game.TicTacToe
import com.dev.tictactoe.util.atPosition
import com.dev.tictactoe.util.withItemCount
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TicTacToeActivityTest {

    private val boardSize = TicTacToe.BOARD_SIZE * TicTacToe.BOARD_SIZE

    @get:Rule
    var activityRule: ActivityScenarioRule<TicTacToeActivity> =
        ActivityScenarioRule(TicTacToeActivity::class.java)

    @Test
    fun testBoardIsDisplayed() {
        onView(withId(R.id.rvBoard))
            .check(matches(withItemCount(boardSize)))
    }

    @Test
    fun testCellsAreInitializedEmpty() {
        for (position in 0 until boardSize) {
            onView(withId(R.id.rvBoard)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(
                    position
                )
            )
            onView(withId(R.id.rvBoard)).check(
                matches(
                    atPosition(
                        position,
                        allOf(
                            withId(R.id.buttonCell),
                            withText(Cell.EMPTY.value),
                            isDisplayed()
                        )
                    )
                )
            )
        }
    }

    @Test
    fun testCellUpdated() {
        onView(withId(R.id.rvBoard)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(0),
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.rvBoard)).check(
            matches(
                atPosition(
                    0,
                    allOf(
                        withId(R.id.buttonCell),
                        withText(Cell.X.value),
                        isDisplayed()
                    )
                )
            )
        )

        onView(withId(R.id.rvBoard)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(0),
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )

        onView(withId(R.id.rvBoard)).check(
            matches(
                atPosition(
                    1,
                    allOf(
                        withId(R.id.buttonCell),
                        withText(Cell.O.value),
                        isDisplayed()
                    )
                )
            )
        )
    }
}