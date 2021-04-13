package com.example.hcanvas.views

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import com.example.hcanvas.R


/**
 * create by 胡汉君  date 2021/4/12-10-45
 *email ：huhanjun@bytedance.com
 */

/***********************************************************
 * * Copyright (C), 2020-2030, Bytedance edu Corp., Ltd.
 * * File:  - PathDrawable.kt
 * * Description: build this module.
 * * Date : 2021/4/12-10-45
 * * Author: 胡汉君@Apps.minddance

 ****************************************************************/
internal class PathDrawable(context: Context) : Drawable(),
    AnimatorUpdateListener {
    companion object {
        private const val TAG = "PathDrawable"
    }

    private val mPath: Path = Path()
    private val mPaint: Paint = Paint()
    private val jumpBitmap1 = BitmapFactory.decodeResource(context.resources, R.drawable.jump_1)
    private val jumpBitmap3 = BitmapFactory.decodeResource(context.resources, R.drawable.jump_3)
    private val leftBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.left)
    private val rightBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.right)
    private var currentBitmap = jumpBitmap1
    private var destinationRect = Rect()
    private var leftRect = Rect()
    private var rightRect = Rect()
    private var count = 0
    private lateinit var mAnimator: ValueAnimator
    fun startAnimating() {
        val b: Rect = bounds

        mAnimator = ValueAnimator.ofInt(0, b.bottom / 2, b.bottom, b.bottom / 2, 0)
        val halfW = halfW(b)
        val halfH = halfH(b)
        val imageH = imageH()
        val imageW = imageW()
        Log.d(TAG, "imageH $imageH imageW $imageW")
        destinationRect.set(
            b.left + (halfW - imageW / 2).toInt(),
            b.top + (halfH - imageH / 2).toInt(),
            b.left + (halfW + imageW / 2).toInt(),
            b.top + (halfH + imageH / 2).toInt()
        )

        leftRect.set(
            b.left,
            b.top + (halfH - leftBitmap.height / 2).toInt(),
            b.left + leftBitmap.width,
            b.top + (halfH + leftBitmap.height / 2).toInt()
        )
        rightRect.set(
            b.right - rightBitmap.width,
            b.top + (halfH - rightBitmap.height / 2).toInt(),
            b.right,
            b.top + (halfH + rightBitmap.height / 2).toInt()
        )
        mAnimator.duration = 1000
        mAnimator.repeatCount = INFINITE
        mAnimator.addUpdateListener(this)
        mAnimator.start()
    }

    private fun imageW() = jumpBitmap1.width

    private fun imageH() = jumpBitmap1.height

    private fun halfH(b: Rect): Float {
        val halfH = (b.bottom - b.top) / 2f
        return halfH
    }

    private fun halfW(b: Rect): Float {
        return (b.right - b.left) / 2f
    }

    override fun draw(canvas: Canvas) {
        canvas.drawPath(mPath, mPaint)
        canvas.drawBitmap(currentBitmap, null, destinationRect, null)
        canvas.drawBitmap(leftBitmap, null, leftRect, null)
        canvas.drawBitmap(rightBitmap, null, rightRect, null)
    }

    override fun setAlpha(alpha: Int) {}
    override fun setColorFilter(cf: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun onAnimationUpdate(animator: ValueAnimator) {
        Log.d(TAG, "onAnimationUpdate animator${animator.animatedValue}")
        mPath.reset()
        val b: Rect = bounds
        mPath.moveTo(b.left.toFloat(), b.bottom.toFloat() / 2)
        mPath.quadTo(
            ((b.right - b.left) / 2).toFloat(),
            (animator.animatedValue as Int).toFloat(), b.right.toFloat(), b.bottom.toFloat() / 2
        )
        ++count
        if (count % 24 == 0) {

            currentBitmap = if (animator.animatedValue as Int > b.bottom / 2) {
                jumpBitmap1
            } else {
                jumpBitmap3
            }
            count = 0
        }
        destinationRect.set(
            b.left + (halfW(b) - imageW() / 2).toInt(),
            b.top + (halfH(b) - imageH() / 2).toInt() + (animator.animatedValue as Int)/8,
            b.left + (halfW(b) + imageW() / 2).toInt(),
            b.top + (halfH(b) + imageH() / 2).toInt() + (animator.animatedValue as Int)/8
        )
        invalidateSelf()
    }

    init {
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 5f
        mPaint.style = Paint.Style.STROKE
    }
}