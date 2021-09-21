package com.sigmaukraine.customviewsdemo.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.sigmaukraine.customviewsdemo.R


/**
 * Created by Roman Koshulinskyy on 9/19/21.
 *
 */
// We must provide a constructor that takes a Context and an AttributeSet.
// This constructor allows the UI to create and edit an instance of your view.
class ShapeSelectorView(context: Context?, attrs: AttributeSet) :
    View(context, attrs) {
    private var shapeColor = 0
    private var displayShapeName = false

    private val shapeWidth = 300f
    private val shapeHeight = 300f
    private var textXOffset = 0f
    private val textYOffset = 50f

    private lateinit var paintShape: Paint
    private var trianglePath = Path()

    private val shapeValues = arrayOf("square", "circle", "triangle")
    private var currentShapeIndex = 0


    // Returns selected shape name
    val selectedShape: String
        get() = shapeValues[currentShapeIndex]

    private fun setupAttributes(attrs: AttributeSet) {
        // Obtain a typed array of attributes
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ShapeSelectorView, 0, 0)
        // Extract custom attributes into member variables
        try {
            shapeColor = a.getColor(R.styleable.ShapeSelectorView_shapeColor, Color.BLACK)
            displayShapeName = a.getBoolean(R.styleable.ShapeSelectorView_displayShapeName, false)
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle()
        }
    }

    fun toggleDisplayingShapeName() {
        displayShapeName = !displayShapeName
        invalidate()
        requestLayout()
    }

    fun getShapeColor(): Int {
        return shapeColor
    }

    fun setShapeColor(color: Int) {
        shapeColor = color
        setupPaint()
        invalidate()
        requestLayout()
    }

    fun changeShape() {
        currentShapeIndex = (currentShapeIndex + 1) % shapeValues.size
        invalidate()
    }

    // Change the currentShapeIndex whenever the shape is clicked
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = super.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_DOWN) {
            changeShape()
            return true
        }
        return result
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Defines the extra padding for the shape name text
        val textPadding = 10
        val contentWidth = shapeWidth.toInt()

        // Resolve the width based on our minimum and the measure spec
        val minw = contentWidth + paddingLeft + paddingRight
        val w = resolveSizeAndState(minw, widthMeasureSpec, 0)

        // Ask for a height that would let the view get as big as it can
        var minh = (shapeHeight + paddingBottom + paddingTop).toInt()
        if (displayShapeName) {
            minh += (textYOffset + textPadding).toInt()
        }
        val h = resolveSizeAndState(minh, heightMeasureSpec, 0)

        // Calling this method determines the measured width and height
        // Retrieve with getMeasuredWidth or getMeasuredHeight methods later
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val shapeSelected = shapeValues[currentShapeIndex]
        when (shapeSelected) {
            "square" -> {
                canvas.drawRect(0f, 0f, shapeWidth, shapeHeight, paintShape)
                textXOffset = 0f
            }
            "circle" -> {
                canvas.drawCircle(shapeWidth / 2, shapeHeight / 2, shapeWidth / 2, paintShape)
                textXOffset = 12f
            }
            "triangle" -> {
                canvas.drawPath(getTrianglePath(), paintShape)
                textXOffset = 0f
            }
        }
        if (displayShapeName) {
            canvas.drawText(shapeSelected, 0 + textXOffset, shapeHeight + textYOffset, paintShape)
        }
    }

    private fun getTrianglePath(): Path {
        trianglePath.moveTo(0f, shapeHeight)
        trianglePath.lineTo(shapeWidth, shapeHeight)
        trianglePath.lineTo(shapeWidth / 2, 0f)
        return trianglePath
    }

    private fun setupPaint() {
        paintShape = Paint()
        paintShape.style = Paint.Style.FILL
        paintShape.color = shapeColor
        paintShape.textSize = 30f
    }

    init {
        setupAttributes(attrs)
        setupPaint()
    }
}
