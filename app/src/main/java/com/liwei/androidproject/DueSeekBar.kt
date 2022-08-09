package com.liwei.androidproject

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/7/13 9:28 上午
 * @Version:        1.0
 */
class DueSeekBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatSeekBar(context, attrs, defStyleAttr) {
    private var isMysetPadding = true

    private val DEFAULT_POINT_RADIUS = 8F.dp / 2F // 默认原点半径
    private val DEFAULT_TEXT_SIZE = 10f // 进度字体大小 px单位
    private val mMarks: ArrayList<Mark> = ArrayList<Mark>() // 标记点集合


    private var res: Resources = resources
//    private lateinit var mBarBitmap: Bitmap
    private lateinit var mBubbleBitmap: Bitmap
    private var mBubbleWidth = 0f
    private var mBubbleHeight = 0f
    private lateinit var mTextPaint: Paint
    private lateinit var mShapePaint: Paint
    private var mTtextSize: Float = DEFAULT_TEXT_SIZE

    private var mMarkPaddingTop = 0
    private var mMarkPaddingLeft = 0
    private var mMarkPaddingRight = 0
    private var mMarkPaddingBottom = 0
    private var progressMax = 0

    init {
        initBitmap()
        initDraw()
        initPadding()
        initMarks()
        progressMax = max
    }

    private fun initBitmap() {
//        mBarBitmap = BitmapFactory.decodeResource(resources, R.drawable.bg_zhou)
        mBubbleBitmap = BitmapFactory.decodeResource(res, R.drawable.popwindow_bg1)
        if (mBubbleBitmap != null) {
            mBubbleWidth = mBubbleBitmap.getWidth().toFloat()
            mBubbleHeight = mBubbleBitmap.getHeight().toFloat()
        } else {
            mBubbleWidth = 0f
            mBubbleHeight = 0f
        }
    }

    private fun initDraw() {
        mShapePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.setTypeface(Typeface.DEFAULT)
        mTextPaint.setTextSize(mTtextSize)
        mTextPaint.setColor(0xff23fc4f.toInt())
    }

    // 初始化padding 使其左右上 留下位置用于展示进度图片
    @Synchronized
    private fun initPadding() {
        val top: Int = getBubbleBitmapHeigh() + mMarkPaddingTop
        val left: Int = getBubbleBitmapWidth() / 2 + mMarkPaddingLeft
        val right: Int = getBubbleBitmapWidth() / 2 + mMarkPaddingRight
        val bottom: Int = mMarkPaddingBottom
        isMysetPadding = true
        setPadding(left, top, right, bottom)
        isMysetPadding = false
    }


    private fun initMarks() {
        mMarks.add(Mark(0))
        mMarks.add(Mark(max))
    }

    private fun getBubbleBitmapWidth(): Int {
        return Math.ceil(mBubbleWidth.toDouble()).toInt()
    }

    private fun getBubbleBitmapHeigh(): Int {
        return Math.ceil(mBubbleHeight.toDouble()).toInt()
    }

    // 修改setpadding 使其在外部调用的时候无效
    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (isMysetPadding) {
            super.setPadding(left, top, right, bottom)
        }
    }

    private var mMarksOffsetTop = 0
    private val mPointRadius: Float = DEFAULT_POINT_RADIUS
    private var mMarksOffsetLeftRight = 0
    private var mText: String = ""
    private var mTextWidth = 0f
    private var mImageOffsetLeft = 0
    private var mImageOffsetTop = 0
    private var mTextPaddingLeft = 0
    private var mTextPaddingTop = 0

    private val mPaint = Paint()

    override fun onDraw(canvas: Canvas) {

        setBackgroundColor(Color.GRAY)

        val barHeight = 1F.dp

        val thumbOffset = thumbOffset
        val offset = thumbOffset - barHeight / 2F

        val barLeft = paddingLeft.toFloat() - thumbOffset
        val barTop = paddingTop + offset
        val barRight = width.toFloat() - (paddingRight - thumbOffset)
        val barBottom = paddingTop + barHeight + offset
        val barWidth = barRight - barLeft

//        canvas.drawLine(barLeft, barTop, barRight, barBottom, mPaint)
        val fl = mBubbleHeight  + mPointRadius - barHeight / 2
        canvas.drawLine(barLeft, fl, barRight, fl + barHeight, mPaint)

//        canvas.drawLine(barLeft, 0F, barLeft, 1000F, mPaint)
//        canvas.drawLine(barRight, 0F, barRight, 1000F, mPaint)
//        for (index in 1.. 9) {
//            val left = progressDrawable.bounds.width() * (index / progressMax.toFloat()) + mBubbleWidth / 2
//
////            val left = barLeft + (barWidth - mPointRadius / 2)  / 10F * index
//            canvas.drawLine(left, 0F, left, 1000F, mPaint)
//        }

//        canvas.drawBitmap(mBarBitmap, null, mBarDst, null)

        // 初始化首尾两端的标记
        val cy: Float = (barTop + barHeight / 2F + mMarksOffsetTop)
        //canvas.drawCircle(mBarDst.left + mPointRadius, cy, mPointRadius, mShapePaint);
        //canvas.drawCircle(getWidth() / 2, cy, mPointRadius, mShapePaint);
        //canvas.drawCircle(mBarDst.right - mPointRadius, cy, mPointRadius, mShapePaint);
        if (mMarks.size > 0) {
            var tmpX: Float

            val mBarDstWidth: Float = barWidth
            for (i in mMarks.indices) {
                tmpX = if (0 == mMarks[i].postion) {
                    barLeft + mPointRadius + mMarksOffsetLeftRight
                } else if (progressMax == mMarks[i].postion) {
                    barLeft + mBarDstWidth - mPointRadius - mMarksOffsetLeftRight
                } else {
//                    barLeft + mBarDstWidth * (mMarks[i].postion / progressMax.toFloat())
                    progressDrawable.bounds.width() * (mMarks[i].postion / progressMax.toFloat()) + mBubbleWidth / 2
                }
                mMarks[i].left = tmpX - mPointRadius
                mMarks[i].top = cy - mPointRadius
                mMarks[i].right = tmpX + mPointRadius
                mMarks[i].bottom = cy + mPointRadius
                canvas.drawCircle(tmpX, cy, mPointRadius, mShapePaint)
            }
        }

        Log.d("--TAG--", "宽度1 " + barWidth)
        Log.d("--TAG--", "宽度2 " + progressDrawable.bounds.width())
        Log.d("--TAG--", "宽度3 " + mBubbleWidth / 2F)
       mText = (progress * 100 / max).toString() + "%"
        mTextWidth = mTextPaint.measureText(mText)

//        progressDrawable.

        val bounds = this.progressDrawable.bounds
        val xImg: Int = bounds.width() * progress / max + mImageOffsetLeft + mMarkPaddingLeft
        val yImg: Float = (mImageOffsetTop + mMarkPaddingTop).toFloat()
        val xText: Float =
            (bounds.width() * progress / max + mBubbleWidth / 2 - mTextWidth / 2 + mTextPaddingLeft
                    + mMarkPaddingLeft)
        val yText: Float = yImg + mTextPaddingTop + mBubbleHeight / 2 + getTextHei() / 4
        canvas.drawBitmap(mBubbleBitmap, xImg.toFloat(), yImg, null)
        canvas.drawText(mText, xText, yText, mTextPaint)
        super.onDraw(canvas)
    }

    private var mTouchDownX = 0f
    private  var mTouchDownY: Float = 0f

    private fun getTextHei(): Float {
        val fm = mTextPaint.fontMetrics
        return Math.ceil((fm.descent - fm.top).toDouble()).toFloat() + 2
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouchDownX = event.x
                mTouchDownY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> if (mTouchDownX == event.x && mTouchDownY == event.y) {
                val x = event.x.toInt()
                val y = event.y.toInt()
                var selectedIndex = -1
                var i = 0
                while (i < mMarks.size) {
                    val px = mMarks[i].left
                    val py = mMarks[i].top
                    if (mMarks[i].contains(x.toFloat(), y.toFloat())) {
                        selectedIndex = i
                        break
                    }
                    i++
                }
                if (selectedIndex != -1) {
                    progress = mMarks[selectedIndex].postion
                    invalidate()
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 替代setpadding
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    fun setMyPadding(left: Int, top: Int, right: Int, bottom: Int) {
        mMarkPaddingTop = top
        mMarkPaddingLeft = left
        mMarkPaddingRight = right
        mMarkPaddingBottom = bottom
        isMysetPadding = true
        setPadding(
            left + getBubbleBitmapWidth() / 2,
            top + getBubbleBitmapHeigh(),
            right + getBubbleBitmapWidth() / 2,
            bottom
        )
        isMysetPadding = false
    }

    /**
     * 设置进度字体大小
     *
     * @param textsize
     */
    fun setTextSize(textsize: Float) {
        mTtextSize = textsize
        mTextPaint.textSize = textsize
    }

    /**
     * 设置进度字体颜色
     *
     * @param color
     */
    fun setTextColor(color: Int) {
        mTextPaint.color = color
    }

    /**
     * 设置标签圆上偏移
     * @param offsetTop
     */
    fun setMarksOffsetTop(offsetTop: Int) {
        mMarksOffsetTop = offsetTop
        invalidate()
    }

    fun setMarksOffsetLeftRight(offset: Int) {
        mMarksOffsetLeftRight = offset
        invalidate()
    }

    /**
     * 调整进度字体的位置 初始位置为图片的正中央
     *
     * @param top
     * @param left
     */
    fun setTextPadding(top: Int, left: Int) {
        mTextPaddingLeft = left
        mTextPaddingTop = top
    }

    /**
     * 调整进图背景图的位置 初始位置为进度条正上方、偏左一半
     *
     * @param top
     * @param left
     */
    fun setImageOffset(top: Int, left: Int) {
        mImageOffsetLeft = left
        mImageOffsetTop = top
    }

    fun getTextPaddingLeft(): Int {
        return mTextPaddingLeft
    }

    fun getTextpaddingTop(): Int {
        return mTextPaddingTop
    }

    fun getImageOffsetLeft(): Int {
        return mImageOffsetLeft
    }

    fun getImageOffsetTop(): Int {
        return mImageOffsetTop
    }

    fun getTextsize(): Float {
        return mTtextSize
    }

    /**
     *
     * 添加标签
     * @param postion seekbar的progress
     */
    fun addMark(postion: Int) {

        mMarks.add(Mark(postion))
    }
}