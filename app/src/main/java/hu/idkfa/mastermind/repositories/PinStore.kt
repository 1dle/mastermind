package hu.idkfa.mastermind.repositories

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
    //6pcs pins
    val pins = List(6){
        Pin(it,
            Painter.circle(
                Constants.ITEMSIZE1,//size for rvChooser
                true,
                enumValues<Kolor>().get(it+2)//+2 because the first two values are background color
                //last parameter is the text
            ),
            //now we need circles with different sizes and without border
            Painter.circle(
                color = enumValues<Kolor>().get(it+2)
            )
        )
    }
    fun getChooserIconById(id: Int) = pins.find{ p -> p.id == id }!!.chooserImage
    fun getTableIconById(id: Int) = pins.find { p -> p.id == id }!!.tableImage
}