package com.zld.expandlayout

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 *  Created by lingdong on 2018/5/6.
 *
 *
 **/
class ExpandTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    /**
     * true：展开，false：收起
     */
    private var mExpanded = false

    private var mCallback: ExpandTextViewCallback? = null

    /**
     * 源文字内容
     */
    private var mText = ""
    private var maxLineCount = 3
    private var ellipsizeText = "..."

    fun setMaxLineCount(maxLineCount: Int) {
        this.maxLineCount = maxLineCount
    }

    fun setEllipsizeText(ellipsizeText: String) {
        this.ellipsizeText = ellipsizeText
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val staticLayout = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            StaticLayout(mText, paint, measuredWidth - paddingLeft - paddingRight, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true)
        } else {
            val builder = StaticLayout.Builder.obtain(mText, 0, text.length, paint, measuredWidth - paddingLeft - paddingRight)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(0f, 1f)
                .setIncludePad(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                builder.setUseLineSpacingFromFallbacks(true)
            }

            builder.build()
        }

        /**
         * 总计行数
         */
        var lineCount = staticLayout.lineCount
        if (lineCount > maxLineCount) {
            when (mExpanded) {
                true -> {
                    mCallback?.onExpand()
                    text = mText
                }
                false -> {
                    mCallback?.onCollapse()

                    lineCount = maxLineCount

                    val dotWidth = paint.measureText(ellipsizeText)

                    val start = staticLayout.getLineStart(lineCount - 1)
                    val end = staticLayout.getLineEnd(lineCount - 1)
                    val lineText = mText.substring(start, end)

                    var endIndex = 0
                    for (i in lineText.length - 1 downTo 0) {
                        val str = lineText.substring(i, lineText.length)
                        if (paint.measureText(str) >= dotWidth) {
                            endIndex = i
                            break
                        }
                    }

                    val newEndLineText = lineText.substring(0, endIndex) + ellipsizeText
                    text = mText.substring(0, start) + newEndLineText
                }
            }
        } else {
            mCallback?.onLoss()
            text = mText
        }

        var lineHeight = 0
        for (i in 0 until lineCount) {
            val lienBound = Rect()
            staticLayout.getLineBounds(i, lienBound)
            lineHeight += lienBound.height()
        }

        lineHeight += paddingTop + paddingBottom
        setMeasuredDimension(measuredWidth, lineHeight)
    }

    fun toggleExpand() {
        mExpanded = !mExpanded
        requestLayout()
    }

    fun setText(text: String, callback: ExpandTextViewCallback) {
        mText = text
        mCallback = callback

        setText(text)
    }

    fun setText(text: String, expand: Boolean, callback: ExpandTextViewCallback) {
        mText = text
        mExpanded = expand
        mCallback = callback

        setText(text)
    }
}