package hu.idkfa.mastermind

object Constants {
    private var W_WIDTH = 0;
    private var W_HEIGHT = 0;

    //size of game table
    const val MAX_ROW = 8
    const val MAX_COL = 4

    var ITEMSIZE_MAIN = 0; //for elements in rvChooser
    var ITEMSIZE_CHOOSER = 0; //for elemtns in rvTable (GameTable)


    const val GAME_OVER = -1
    const val GAME_WON  = -2

    fun inititemSizes(windowWidth: Int, windowHeight: Int){
        W_WIDTH = windowWidth
        W_HEIGHT = windowHeight
        ITEMSIZE_MAIN = W_HEIGHT/12
        ITEMSIZE_CHOOSER = W_WIDTH/7
    }

}