package hu.idkfa.mastermind.graphics

import android.graphics.*
import android.graphics.drawable.Drawable
import hu.idkfa.mastermind.Constants
import hu.idkfa.mastermind.model.Kolor

object Painter {

    fun circle(size: Int = Constants.ITEMSIZE2, border: Boolean = false, color: Kolor = Kolor.BGCOLOR, text: String? = null): Bitmap{
        val bg = Bitmap.createBitmap(size+10,size+10, Bitmap.Config.ARGB_8888 )
        val paint = Paint().apply {
            style = Paint.Style.FILL
            flags = Paint.ANTI_ALIAS_FLAG
            this.color = Color.parseColor(color.colorCode)
        }

        val canvas = Canvas(bg)
        val radius = size/2f
        canvas.drawCircle(size/2f+5,size/2f+5,radius,paint)
        //Körvonal
        if(border){
            paint.apply {
                style = Paint.Style.STROKE
                strokeWidth = 5f
                this.color = Color.BLACK
            }
            canvas.drawCircle(size/2f+5,size/2f+5,radius,paint)
        }
        if (text!=null){
            paint.textSize = size.toFloat()
            paint.style = Paint.Style.FILL
            paint.color = Color.WHITE
            canvas.centerText(paint, text,size/2f+5,size/2f+5)
        }
        return bg
    }
    private fun Canvas.centerText(paint: Paint, text: String, cx: Float, cy:Float) {
        val r = Rect();
        this.getClipBounds(r)
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, r)
        val x = cx - r.width() / 2f - r.left
        val y = cy + r.height() / 2f - r.bottom
        this.drawText(text, x, y, paint)
    }



}