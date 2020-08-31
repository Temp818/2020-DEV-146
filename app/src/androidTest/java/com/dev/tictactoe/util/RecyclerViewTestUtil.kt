package com.dev.tictactoe.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withItemCount(expectedCount: Int) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("adapter has ").appendValue(expectedCount).appendText(" items")
    }

    override fun matchesSafely(view: RecyclerView) = view.adapter?.itemCount == expectedCount
}

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("has item at position ").appendValue(position).appendText(" that matches ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView) = itemMatcher.matches(view.findViewHolderForAdapterPosition(position)?.itemView)
    }
}