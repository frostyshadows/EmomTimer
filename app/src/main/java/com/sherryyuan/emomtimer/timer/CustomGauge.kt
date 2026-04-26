package com.sherryyuan.emomtimer.timer

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.sherryyuan.emomtimer.R
import kotlin.math.abs
import androidx.core.content.withStyledAttributes

/**
 * Kotlin version of CustomGauge (https://github.com/pkleczko/CustomGauge), which no longer works
 * as a dependency because it was only on jcenter.
 */
class CustomGauge : View {
    private var mPaint: Paint? = null

    @get:Suppress("unused")
    var strokeWidth: Float = 0f

    @get:Suppress("unused")
    var strokeColor: Int = 0
    private var mRect: RectF? = null
    private var mStrokeCap: String? = null

    @get:Suppress("unused")
    var startAngle: Int = 0

    @get:Suppress("unused")
    var sweepAngle: Int = 0

    @get:Suppress("unused")
    var startValue: Int = 0
    private var mEndValue = 0
    private var mValue = 0
    private var mPointAngle = 0.0
    private var mPoint = 0

    @get:Suppress("unused")
    var pointSize: Int = 0

    @get:Suppress("unused")
    var pointStartColor: Int = 0

    @get:Suppress("unused")
    var pointEndColor: Int = 0

    @get:Suppress("unused")
    var dividerColor: Int = 0
    private var mDividerSize = 0
    private var mDividerStepAngle = 0
    private var mDividersCount = 0

    @get:Suppress("unused")
    var isDividerDrawFirst: Boolean = false

    @get:Suppress("unused")
    var isDividerDrawLast: Boolean = false

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.withStyledAttributes(attrs, R.styleable.CustomGauge, 0, 0) {

            // stroke style
            strokeWidth = getDimension(R.styleable.CustomGauge_gaugeStrokeWidth, 10f)
            strokeColor = getColor(
                R.styleable.CustomGauge_gaugeStrokeColor,
                ContextCompat.getColor(context, R.color.colorText)
            )
            strokeCap = getString(R.styleable.CustomGauge_gaugeStrokeCap)!!

            // angle start and sweep (opposite direction 0, 270, 180, 90)
            startAngle = getInt(R.styleable.CustomGauge_gaugeStartAngle, 0)
            sweepAngle = getInt(R.styleable.CustomGauge_gaugeSweepAngle, 360)

            // scale (from mStartValue to mEndValue)
            startValue = getInt(R.styleable.CustomGauge_gaugeStartValue, 0)
            endValue = getInt(R.styleable.CustomGauge_gaugeEndValue, 1000)

            // pointer size and color
            pointSize = getInt(R.styleable.CustomGauge_gaugePointSize, 0)
            pointStartColor = getColor(
                R.styleable.CustomGauge_gaugePointStartColor,
                ContextCompat.getColor(context, R.color.white)
            )
            pointEndColor = getColor(
                R.styleable.CustomGauge_gaugePointEndColor,
                ContextCompat.getColor(context, R.color.white)
            )

            // divider options
            val dividerSize = getInt(R.styleable.CustomGauge_gaugeDividerSize, 0)
            dividerColor = getColor(
                R.styleable.CustomGauge_gaugeDividerColor,
                ContextCompat.getColor(context, R.color.white)
            )
            val dividerStep = getInt(R.styleable.CustomGauge_gaugeDividerStep, 0)
            isDividerDrawFirst =
                getBoolean(R.styleable.CustomGauge_gaugeDividerDrawFirst, true)
            isDividerDrawLast = getBoolean(R.styleable.CustomGauge_gaugeDividerDrawLast, true)

            // calculating one point sweep
            mPointAngle = (abs(sweepAngle).toDouble() / (mEndValue - startValue))

            // calculating divider step
            if (dividerSize > 0) {
                mDividerSize = sweepAngle / (abs(mEndValue - startValue) / dividerSize)
                mDividersCount = 100 / dividerStep
                mDividerStepAngle = sweepAngle / mDividersCount
            }
        }
        init()
    }

    private fun init() {
        //main Paint
        mPaint = Paint()
        mPaint!!.setColor(this.strokeColor)
        mPaint!!.setStrokeWidth(this.strokeWidth)
        mPaint!!.setAntiAlias(true)
        if (!TextUtils.isEmpty(mStrokeCap)) {
            if (mStrokeCap == "BUTT") mPaint!!.setStrokeCap(Paint.Cap.BUTT)
            else if (mStrokeCap == "ROUND") mPaint!!.setStrokeCap(Paint.Cap.ROUND)
        } else mPaint!!.setStrokeCap(Paint.Cap.BUTT)
        mPaint!!.setStyle(Paint.Style.STROKE)
        mRect = RectF()

        mValue = this.startValue
        mPoint = this.startAngle
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = this.strokeWidth
        val size = (if (getWidth() < getHeight()) getWidth() else getHeight()).toFloat()
        val width = size - (2 * padding)
        val height = size - (2 * padding)
        //        float radius = (width > height ? width/2 : height/2);
        val radius = (if (width < height) width / 2 else height / 2)


        val rectLeft = (getWidth() - (2 * padding)) / 2 - radius + padding
        val rectTop = (getHeight() - (2 * padding)) / 2 - radius + padding
        val rectRight = (getWidth() - (2 * padding)) / 2 - radius + padding + width
        val rectBottom = (getHeight() - (2 * padding)) / 2 - radius + padding + height

        mRect!!.set(rectLeft, rectTop, rectRight, rectBottom)

        mPaint!!.setColor(this.strokeColor)
        mPaint!!.setShader(null)
        canvas.drawArc(mRect!!, startAngle.toFloat(), sweepAngle.toFloat(), false, mPaint!!)
        mPaint!!.setColor(this.pointStartColor)
        mPaint!!.setShader(
            LinearGradient(
                getWidth().toFloat(), getHeight().toFloat(), 0f, 0f,
                this.pointEndColor,
                this.pointStartColor, Shader.TileMode.CLAMP
            )
        )
        if (this.pointSize > 0) { //if size of pointer is defined
            if (mPoint > this.startAngle + this.pointSize / 2) {
                canvas.drawArc(
                    mRect!!,
                    (mPoint - this.pointSize / 2).toFloat(),
                    pointSize.toFloat(),
                    false,
                    mPaint!!
                )
            } else { //to avoid excedding start/zero point
                canvas.drawArc(mRect!!, mPoint.toFloat(), pointSize.toFloat(), false, mPaint!!)
            }
        } else { //draw from start point to value point (long pointer)
            if (mValue == this.startValue)  //use non-zero default value for start point (to avoid lack of pointer for start/zero value)
                canvas.drawArc(
                    mRect!!,
                    startAngle.toFloat(),
                    DEFAULT_LONG_POINTER_SIZE.toFloat(),
                    false,
                    mPaint!!
                )
            else canvas.drawArc(
                mRect!!,
                startAngle.toFloat(),
                (mPoint - this.startAngle).toFloat(),
                false,
                mPaint!!
            )
        }

        if (mDividerSize > 0) {
            mPaint!!.setColor(this.dividerColor)
            mPaint!!.setShader(null)
            var i = if (this.isDividerDrawFirst) 0 else 1
            val max = if (this.isDividerDrawLast) mDividersCount + 1 else mDividersCount
            while (i < max) {
                canvas.drawArc(
                    mRect!!,
                    (this.startAngle + i * mDividerStepAngle).toFloat(),
                    mDividerSize.toFloat(),
                    false,
                    mPaint!!
                )
                i++
            }
        }
    }

    var value: Int
        get() = mValue
        set(value) {
            mValue = value
            mPoint = (this.startAngle + (mValue - this.startValue) * mPointAngle).toInt()
            invalidate()
        }

    @get:Suppress("unused")
    var strokeCap: String
        get() = mStrokeCap!!
        set(strokeCap) {
            mStrokeCap = strokeCap
            if (mPaint != null) {
                if (mStrokeCap == "BUTT") {
                    mPaint!!.setStrokeCap(Paint.Cap.BUTT)
                } else if (mStrokeCap == "ROUND") {
                    mPaint!!.setStrokeCap(Paint.Cap.ROUND)
                }
            }
        }

    @get:Suppress("unused")
    var endValue: Int
        get() = mEndValue
        set(endValue) {
            mEndValue = endValue
            mPointAngle = (abs(this.sweepAngle).toDouble() / (mEndValue - this.startValue))
            invalidate()
        }

    fun setDividerStep(dividerStep: Int) {
        if (dividerStep > 0) {
            mDividersCount = 100 / dividerStep
            mDividerStepAngle = this.sweepAngle / mDividersCount
        }
    }

    fun setDividerSize(dividerSize: Int) {
        if (dividerSize > 0) {
            mDividerSize = this.sweepAngle / (abs(mEndValue - this.startValue) / dividerSize)
        }
    }

    companion object {
        private const val DEFAULT_LONG_POINTER_SIZE = 1
    }
}