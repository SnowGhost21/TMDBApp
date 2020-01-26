package com.arctouch.codechallenge.view.util

import android.util.DisplayMetrics
import android.util.TypedValue

fun dpToPx(metrics: DisplayMetrics, dp: Float): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            metrics
    ).toInt()
}