package com.example.paperquestionanswerapp.util

import android.view.View

/**
 * Created by Agam on 22-02-2022.
 */

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}


fun View.toggleArrow(isExpanded: Boolean): Boolean {
    return if (isExpanded) {
        this.animate().setDuration(200).rotation(180f)
        true
    } else {
        this.animate().setDuration(200).rotation(0f)
        false
    }
}
