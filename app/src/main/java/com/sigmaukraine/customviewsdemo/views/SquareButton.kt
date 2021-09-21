package com.sigmaukraine.customviewsdemo.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView

/**
 * Created by Roman Koshulinskyy on 9/19/21.
 *
 */
@SuppressLint("AppCompatCustomView")
class SquareButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : Button(context, attrs, defStyle, defStyleRes) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = widthMeasureSpec.coerceAtLeast(heightMeasureSpec)
        super.onMeasure(size, size)
    }
}