package com.zld.expandlayout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.zld.expandlayout.databinding.LayoutExpandViewBinding

/**
 *  Created by lingdong on 2018/5/6.
 *
 *
 **/
class ExpandLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutExpandViewBinding
    private var mListener: ExpandLayoutListener? = null

    private var maxCollapsedLines = 3
    private var contentTextSize = 18f
    private var contentTextColor = 0
    private var expandText = ""
    private var collapseText = ""
    private var expandCollapseTextSize = 18f
    private var expandCollapseTextColor = 0
    private var expandCollapseTextGravity = 0
    private var ellipsizeText = "..."
    private var middlePadding = 0f

    init {
        initAttrs(context, attrs, defStyleAttr)

        binding = LayoutExpandViewBinding.inflate(LayoutInflater.from(context), this, true)
        binding.etvContent.setMaxLineCount(maxCollapsedLines)
        binding.etvContent.textSize = DensityUtil.px2sp(context, contentTextSize)
        binding.etvContent.setTextColor(contentTextColor)
        binding.etvContent.setEllipsizeText(ellipsizeText)

        val layoutParams = binding.tvTip.layoutParams as LayoutParams
        layoutParams.topMargin = middlePadding.toInt()
        binding.tvTip.layoutParams = layoutParams

        binding.tvTip.textSize = DensityUtil.px2sp(context, expandCollapseTextSize)
        binding.tvTip.setTextColor(expandCollapseTextColor)
        binding.tvTip.gravity = when (expandCollapseTextGravity) {
            0 -> Gravity.LEFT
            1 -> Gravity.CENTER
            2 -> Gravity.RIGHT
            else -> Gravity.LEFT
        }

        binding.etvContent.requestLayout()
        binding.tvTip.requestLayout()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandLayout)
        maxCollapsedLines = typedArray.getInt(R.styleable.ExpandLayout_maxCollapsedLines, 3)
        contentTextSize = typedArray.getDimension(R.styleable.ExpandLayout_contentTextSize, DensityUtil.sp2px(context, 18f).toFloat())
        contentTextColor = typedArray.getColor(R.styleable.ExpandLayout_contentTextColor, resources.getColor(R.color.text_black))
        expandText = if (typedArray.getString(R.styleable.ExpandLayout_expandText).isNullOrEmpty()) "全文" else typedArray.getString(R.styleable.ExpandLayout_expandText).toString()
        collapseText = if (typedArray.getString(R.styleable.ExpandLayout_collapseText).isNullOrEmpty()) "收起" else typedArray.getString(R.styleable.ExpandLayout_collapseText).toString()
        expandCollapseTextSize = typedArray.getDimension(R.styleable.ExpandLayout_expandCollapseTextSize, DensityUtil.sp2px(context, 18f).toFloat())
        expandCollapseTextColor = typedArray.getColor(R.styleable.ExpandLayout_expandCollapseTextColor, resources.getColor(R.color.text_blue))
        expandCollapseTextGravity = typedArray.getColor(R.styleable.ExpandLayout_expandCollapseTextGravity, 0)
        ellipsizeText = if (typedArray.getString(R.styleable.ExpandLayout_ellipsizeText).isNullOrEmpty()) "..." else typedArray.getString(R.styleable.ExpandLayout_ellipsizeText).toString()
        middlePadding = typedArray.getDimension(R.styleable.ExpandLayout_middlePadding, 0f)
        typedArray.recycle()
    }

    fun setContent(text: String) {
        binding.tvTip.setOnClickListener {
            binding.etvContent.toggleExpand()
        }
        binding.etvContent.setText(text, object : ExpandTextViewCallback {
            override fun onExpand() {
                binding.tvTip.visibility = View.VISIBLE
                binding.tvTip.text = collapseText
            }

            override fun onCollapse() {
                binding.tvTip.visibility = View.VISIBLE
                binding.tvTip.text = expandText
            }

            override fun onLoss() {
                binding.tvTip.visibility = View.GONE
            }
        })
    }

    fun setContent(text: String, expand: Boolean, listener: ExpandLayoutListener) {
        mListener = listener

        binding.tvTip.setOnClickListener {
            mListener?.onToggleState()
            binding.etvContent.toggleExpand()
        }
        binding.etvContent.setText(text, expand, object : ExpandTextViewCallback {
            override fun onExpand() {
                binding.tvTip.visibility = View.VISIBLE
                binding.tvTip.text = collapseText
            }

            override fun onCollapse() {
                binding.tvTip.visibility = View.VISIBLE
                binding.tvTip.text = expandText
            }

            override fun onLoss() {
                binding.tvTip.visibility = View.GONE
            }
        })
    }
}