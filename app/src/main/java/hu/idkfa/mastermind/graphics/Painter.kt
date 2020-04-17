package hu.idkfa.mastermind.graphics

import android.graphics.*
import hu.idkfa.mastermind.Constants
import hu.idkfa.mastermind.model.Kolor

object Painter {

    fun circle(
        size: Int = Constants.ITEMSIZE_MAIN,
        border: Boolean = false,
        color: Kolor = Kolor.BGCOLOR,
        text: String? = null,
        margin: Int = 0
    ): Bitmap{
        val bg = Bitmap.createBitmap(size+margin*2,size+margin*2, Bitmap.Config.ARGB_8888 )
        val paint = Paint().apply {
            style = Paint.Style.FILL
            flags = Paint.ANTI_ALIAS_FLAG
            this.color = Color.parseColor(color.colorCode)
        }
        val canvas = Canvas(bg)
        val radius = size/2f
        canvas.drawCircle(size/2f+margin,size/2f+margin,radius,paint)
        //Körvonal
        if(border){
            paint.apply {
                style = Paint.Style.STROKE
                strokeWidth = 5f
                this.color = Color.BLACK
            }
            canvas.drawCircle(size/2f+margin,size/2f+margin,radius,paint)
        }
        if (text!=null){
            paint.textSize = size.toFloat()
            paint.style = Paint.Style.FILL
            paint.color = Color.WHITE
            canvas.centerText(paint, text,size/2f,size/2f)
        }
        return bg
    }

    val resultBG = Bitmap.createBitmap(Constants.ITEMSIZE_MAIN,Constants.ITEMSIZE_MAIN, Bitmap.Config.ARGB_8888 ).also {
        Canvas(it).apply {
            drawRoundRect(
                RectF(0f,0f,it.width+0f,it.height+0f),
                5f,5f,
                Paint().apply {
                    style = Paint.Style.FILL
                    color = Color.parseColor(Kolor.BGCOLOR.colorCode)
                    flags = Paint.ANTI_ALIAS_FLAG
                }
            )
        }
    }
    //copy case we dont wanna draw on resultBG
    val resultChecked = resultBG.copy(resultBG.config, resultBG.isMutable).addCenterText(Paint().apply {
            color = Color.GREEN
            textSize = resultBG.width.toFloat()
            style = Paint.Style.FILL
        },"✓", resultBG.width/2f, resultBG.height/2f)

    fun drawResult(blacks : Int, whites: Int): Bitmap {
        val unit = Constants.ITEMSIZE_MAIN
        val background = resultBG.copy(resultBG.config, resultBG.isMutable)
        val paint = Paint()

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor(Kolor.BGDARKER.colorCode)
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.strokeWidth = 5f

        val canvas = Canvas(background)
        // két keresztező volnal rajzoolása drawLine (float startX, float startY, float stopX, float stopY, Paint paint)
        //vizszintes
        canvas.drawLine(unit/2f, 5f, unit/2f, unit-5f, paint)
        //fuggoleges
        canvas.drawLine(5f, unit/2f, unit-5f, unit/2f, paint)

        //karikák kirajzolása
        val rad = unit/8f
        paint.strokeWidth = 0f

        var array = mutableListOf<Int>()
        repeat(blacks){array.add(2)}
        repeat(whites){array.add(1)}

        for(i in 0 until array.size){
            if(array[i] != 0){
                if(array[i] == 2){
                    paint.color = Color.BLACK
                }else if(array[i] == 1){
                    paint.color = Color.WHITE
                }
                var cx = unit/4f
                var cy = unit/4f
                if(i==3){
                    cx = 3f*unit/4f
                    cy = cx
                }else if(i==2){
                    cy = 3f*unit/4f
                }else if(i == 1){
                    cx = 3f*unit/4f
                }
                canvas.drawCircle(cx,cy,rad,paint)
            }


        }
        return background
    }


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
private fun Bitmap.addCenterText(paint: Paint, text: String, cx: Float, cy:Float): Bitmap = this.apply{
    val canvas = Canvas(this)
    canvas.centerText(paint, text, cx, cy)
}
fun Bitmap.addCenterText(text: String): Bitmap{
    return this.copy(this.config, this.isMutable).addCenterText(Paint().apply {
        color = Color.WHITE
        textSize = Painter.resultBG.width.toFloat()
        style = Paint.Style.FILL
    },text, this.width/2f, this.height/2f)
}