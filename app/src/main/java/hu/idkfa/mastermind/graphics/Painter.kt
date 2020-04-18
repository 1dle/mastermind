package hu.idkfa.mastermind.graphics

import android.graphics.*
import hu.idkfa.mastermind.Constants
import hu.idkfa.mastermind.model.Kolor

object Painter {

    /*generate a bitmap whit a circle on it
    * in the function parameters you can give the
    *   size of the circle
    *   has border
    *   color
    *   a text that shows the top of the opject (typically one char)
    *   and the margin (the gap between the canvas side and the circle)
    * */
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
            paint.apply {
                textSize = size.toFloat()
                style = Paint.Style.FILL
                this.color = Color.WHITE
            }
            canvas.centerText(paint, text,size/2f,size/2f)
        }
        return bg
    }
    /*
    * This is a bitmap that shows the end of the every row
    * when the RowResultState is PRE
    * a ractangle whit rounded edges and
    * just a bit lighter color than the background
    * */
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

    /*
    * Same as the resultBG but with a green checkmark on it and
    * shows when the state of row is set to READY
    * */
    val resultChecked = resultBG.copy(resultBG.config, resultBG.isMutable).addCenterText(Paint().apply {
            color = Color.GREEN
            textSize = resultBG.width.toFloat()
            style = Paint.Style.FILL
        },"✓", resultBG.width/2f, resultBG.height/2f)


    /*
    * this image is the resultBG but have two lines on it
    * a horizontal and a vertical line which which intersects in the middle of the rectangle
    * drawResult function draws on this bitmap
     */
    val postResultBG = resultBG.copy(resultBG.config, resultBG.isMutable).apply{

        val unit = Constants.ITEMSIZE_MAIN
        val paint = Paint().apply{
            style = Paint.Style.FILL
            color = Color.parseColor(Kolor.BGDARKER.colorCode)
            flags = Paint.ANTI_ALIAS_FLAG
            strokeWidth = 5f
        }
        Canvas(this).apply {
            //horizontal
            drawLine(unit/2f, 5f, unit/2f, unit-5f, paint)
            //vertical
            drawLine(5f, unit/2f, unit-5f, unit/2f, paint)
        }
    }
    /*
    * and this is a function that generates the image of the result
    * from the RowResult object two properties (black, white)
    * This function draws on top of the resultBG and add two lines
     */
    fun drawResult(blacks : Int, whites: Int): Bitmap {
        val unit = Constants.ITEMSIZE_MAIN
        val background = postResultBG.copy(resultBG.config, resultBG.isMutable)
        val canvas = Canvas(background)
        val paint = Paint().apply{
            style = Paint.Style.FILL
            color = Color.parseColor(Kolor.BGDARKER.colorCode)
            flags = Paint.ANTI_ALIAS_FLAG
            strokeWidth = 5f
        }
        //put black and white walues into arrays (black represents 2, white 1)
        val array = mutableListOf<Int>()
        repeat(blacks){array.add(2)}
        repeat(whites){array.add(1)}

        //drawing white circles
        val rad = unit/8f
        paint.strokeWidth = 0f

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
/**
 * This function draws a text that aligned to the center of the canvas
 */
private fun Canvas.centerText(paint: Paint, text: String, cx: Float, cy:Float) {
    val r = Rect();
    this.getClipBounds(r)
    paint.textAlign = Paint.Align.LEFT
    paint.getTextBounds(text, 0, text.length, r)
    val x = cx - r.width() / 2f - r.left
    val y = cy + r.height() / 2f - r.bottom
    this.drawText(text, x, y, paint)
}
/**
 * This function draws a text that aligned to the center of the bitmap
 */
private fun Bitmap.addCenterText(paint: Paint, text: String, cx: Float, cy:Float): Bitmap = this.apply{
    val canvas = Canvas(this)
    canvas.centerText(paint, text, cx, cy)
}
/**
 * This function draws a text that aligned to the center of the canvas
 * you can choose only the text
 */
fun Bitmap.addCenterText(text: String): Bitmap{
    return this.copy(this.config, this.isMutable).addCenterText(Paint().apply {
        color = Color.WHITE
        textSize = Painter.resultBG.width.toFloat()
        style = Paint.Style.FILL
    },text, this.width/2f, this.height/2f)
}