package hu.idkfa.mastermind.repositories

import android.graphics.Bitmap
import hu.idkfa.mastermind.Constants
import hu.idkfa.mastermind.graphics.Painter
import hu.idkfa.mastermind.model.Kolor
import hu.idkfa.mastermind.model.Pin

object PinStore {
    /*RED("#ff0000"),
    GREEN("#00ff00"),
    BLUE("#0000ff"),
    CYAN("#00ffff"),
    PINK("#ff00ff"),
    YELLOW("#ffff00")*/

    private val pinBackground : Bitmap = Painter.circle()
    val guessIcon = Painter.circle(text = "?")

    //6pcs pins
    val pins = List(6){
        //+1 because IDs should start from index 1, because 0 represents empty in gameTable isntead of NULL
        Pin(it+1,
            Painter.circle(
                Constants.ITEMSIZE1,//size for rvChooser
                true,
                /*
                it+2 +2 because first two colors are background colors
                 */
                enumValues<Kolor>().get(it+2)
                //last parameter is the text
            ),
            //now we need circles with different sizes and without border
            Painter.circle(
                color = enumValues<Kolor>().get(it+2)
            )
        )
    }
    val size
        get() = pins.size
    fun getChooserIconById(id: Int) = pins.find{ p -> p.id == id }!!.chooserImage
    //if not found element with given id, that means the given id is 0 wich is represents background
    fun getTableIconById(id: Int) = pins.find { p -> p.id == id }?.tableImage ?: pinBackground
}