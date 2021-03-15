package com.backbase.assignment.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.backbase.assignment.R
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class RatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val finishedStrokeWidth = FINISHED_STROKE_WIDTH
    private val unfinishedStrokeWidth = UNFINISHED_STROKE_WIDTH
    private val startingDegree = STARTING_POINT_IN_DEGREE
    private val minSize: Int = MIN_SIZE
    private val maxProgress = MAX_PROGRESS
    private var progress = MIN_PROGRESS
    private val finishedOuterRect = RectF()

    private val averagePaint: Paint = Paint().apply {
        color = context.getColor(R.color.colorAccent)
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = finishedStrokeWidth
    }

    private val goodPaint: Paint = Paint().apply {
        color = context.getColor(R.color.conifer)
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = finishedStrokeWidth
    }

    private var drawPaint: Paint = averagePaint

    fun updateProgress(progress: Int) {
        createAnimationAndUpdate(progress)
    }

    private fun createAnimationAndUpdate(newProgress: Int) {
        val progressDuration = getProgressDuration(newProgress)

        drawPaint = if (newProgress >= GOOD_PROGRESS) goodPaint else averagePaint

        ValueAnimator.ofInt(this.progress, newProgress).apply {
            duration = progressDuration.toLong()
            addUpdateListener {
                this@RatingView.progress = it.animatedValue as Int
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val delta: Float = max(finishedStrokeWidth, unfinishedStrokeWidth)
            finishedOuterRect.set(
                delta,
                delta,
                width - delta,
                height - delta
            )
            drawProgress(it)
        }
    }

    private fun drawProgress(canvas: Canvas) {
        canvas.drawArc(
            finishedOuterRect,
            startingDegree.toFloat(),
            getProgressAngle(),
            false,
            drawPaint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec))
    }

    private fun measure(measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        return when (mode) {
            MeasureSpec.EXACTLY -> size
            MeasureSpec.AT_MOST -> min(minSize, size)
            else -> minSize
        }
    }

    private fun getProgressAngle(): Float =
        progress / maxProgress * MAX_DEGREE

    private fun getProgressDuration(newProgress: Int) =
        (abs(newProgress - this.progress) / MAX_PROGRESS) * MAX_DURATION

    companion object {
        private const val MIN_PROGRESS = 0
        private const val MAX_PROGRESS = 100.0f
        private const val MAX_DURATION = 5000
        private const val MAX_DEGREE = 360f
        private const val STARTING_POINT_IN_DEGREE = -120
        private const val FINISHED_STROKE_WIDTH = 4f
        private const val UNFINISHED_STROKE_WIDTH = 1f
        private const val MIN_SIZE = 260
        private const val GOOD_PROGRESS = 50
    }
}