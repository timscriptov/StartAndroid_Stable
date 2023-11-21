package com.startandroid.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ProgressBar

class MCProgressBar : ProgressBar {
    private val mPaint: Paint
    private var mWidth = 0
    private var mHeight = 0
    private var mBlockDrawingProgress = 0f
    private var mShowedBlocks = 1
    private var mIsScaling = true

    constructor(context: Context) : super(context) {
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.setColor(Color.parseColor("#448aff"))
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.setColor(Color.parseColor("#448aff"))
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.setColor(Color.parseColor("#448aff"))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        mBlockDrawingProgress += if (mIsScaling) {
            DEFAULT_SPEED / 2
        } else {
            DEFAULT_SPEED
        }
        if (mBlockDrawingProgress >= 1 && !mIsScaling) {
            mBlockDrawingProgress = 0f
            ++mShowedBlocks
            if (mShowedBlocks > 4) {
                mShowedBlocks = 1
                mIsScaling = true
            }
        } else if (mBlockDrawingProgress >= 0.5 && mIsScaling) {
            mIsScaling = false
            mBlockDrawingProgress = 0f
            mShowedBlocks = 2
        }
        when (mShowedBlocks) {
            1 -> {
                val drawWidth = (mWidth.toFloat() * mBlockDrawingProgress).toInt()
                val drawHeight = (mHeight.toFloat() * mBlockDrawingProgress).toInt()
                canvas.drawRect(0f, drawHeight.toFloat(), (mWidth - drawWidth).toFloat(), mHeight.toFloat(), mPaint)
            }

            2 -> {
                canvas.drawRect(0f, mHeight.toFloat() / 2, mWidth.toFloat() / 2, mHeight.toFloat(), mPaint)
                val blockDrawHeight = (mHeight.toFloat() / 2 * mBlockDrawingProgress).toInt()
                canvas.drawRect(
                    mWidth.toFloat() / 2,
                    blockDrawHeight.toFloat(),
                    mWidth.toFloat(),
                    blockDrawHeight + mHeight.toFloat() / 2,
                    mPaint
                )
            }

            3 -> {
                canvas.drawRect(0f, mHeight.toFloat() / 2, mWidth.toFloat(), mHeight.toFloat(), mPaint)
                val blockDrawHeight = (mHeight.toFloat() / 2 * mBlockDrawingProgress).toInt()
                canvas.drawRect(0f, 0f, mWidth.toFloat() / 2, (blockDrawHeight + 1).toFloat(), mPaint)
            }

            4 -> {
                canvas.drawRect(0f, mHeight.toFloat() / 2, mWidth.toFloat(), mHeight.toFloat(), mPaint)
                canvas.drawRect(0f, 0f, mWidth.toFloat() / 2, mHeight.toFloat() / 2, mPaint)
                val blockDrawHeight = (mHeight.toFloat() / 2 * mBlockDrawingProgress).toInt()
                canvas.drawRect(mWidth.toFloat() / 2, 0f, mWidth.toFloat(), (blockDrawHeight + 1).toFloat(), mPaint)
            }
        }
        invalidate()
    }

    companion object {
        private const val DEFAULT_SPEED = 0.075f
    }
}
