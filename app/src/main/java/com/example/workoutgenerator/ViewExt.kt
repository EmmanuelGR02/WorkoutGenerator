package com.example.workoutgenerator

import android.view.View
import android.view.animation.AnimationUtils
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.StartOffset
import androidx.interpolator.view.animation.FastOutLinearInInterpolator

fun View.slideInLeft(animTime: Long, startOffset: Long) {
    val slideInLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_left).apply {
        duration = animTime
        interpolator = FastOutLinearInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideInLeft)
}