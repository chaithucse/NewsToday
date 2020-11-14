package com.techchai.newstoday.common

import android.content.Context
import android.widget.Toast

/**
 * Extension functions
 * @author Chaitanya
 */

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}