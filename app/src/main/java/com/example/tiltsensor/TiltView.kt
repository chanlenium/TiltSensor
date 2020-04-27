package com.example.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {
    private val greenPaint : Paint = Paint()
    private val blackPaint : Paint = Paint()

    // variable to represent center point
    private var cX: Float = 0f
    private var cY: Float = 0f

    private var xCoord: Float = 0f
    private var yCoord: Float = 0f

    init{
        greenPaint.color = Color.GREEN
        blackPaint.style = Paint.Style.STROKE
    }

    // calculate center point
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w/2f
        cY = h/2f
    }

    override fun onDraw(canvas: Canvas?) {  // 도화지
        canvas?.drawCircle(cX, cY, 100f, blackPaint)  // x, y, radius, color
        //canvas?.drawCircle(cX, cY, 100f, greenPaint)
        canvas?.drawCircle(cX + xCoord, cY + yCoord, 100f, greenPaint)
        canvas?.drawLine(-20F, 0F, 20F, 0F, blackPaint)
        canvas?.drawLine(0F, -20F, 0F, 20F, blackPaint)
    }

    // reflect sensor values to View
    fun onSensorEvent(event: SensorEvent){
        //화면을 가로로 돌렸으므로, x축과 y축을 서로 바꿈
        yCoord = event.values[0] * 20
        xCoord = event.values[1] * 20
        invalidate() // call onDraw()
    }
}