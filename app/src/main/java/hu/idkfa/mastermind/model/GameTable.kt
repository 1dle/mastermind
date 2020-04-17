package hu.idkfa.mastermind.model

import kotlin.properties.Delegates

class GameTable {

    private val _table = mutableListOf<Kolor>()
    private var row = 0
    private var column = 0
    private val MAX_ROW = 8
    private val MAX_COL = 4


    private fun initList(){
        //fill table with background-color color circles
        repeat(MAX_ROW * MAX_COL){ _table.add(Kolor.BGDARKER) }
    }

    init{
        initList();
    }


    fun asList() = _table
    fun reset(){
        row = 0
        column = 0
        _table.clear()
        initList()
    }
}