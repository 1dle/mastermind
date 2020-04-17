package hu.idkfa.mastermind

object Constants {
    private var W_WIDTH = 0;
    private var W_HEIGHT = 0;

    var ITEMSIZE1 = 0; //for elements in rvChooser
    var ITEMSIZE2 = 0; //for elemtns in rvTable (GameTable)

    fun inititemSizes(windowWidth: Int, windowHeight: Int){
        W_WIDTH = windowWidth
        W_HEIGHT = windowHeight
        ITEMSIZE1 = 3*(W_WIDTH/6)/4
        ITEMSIZE2 = W_HEIGHT/12
    }

}