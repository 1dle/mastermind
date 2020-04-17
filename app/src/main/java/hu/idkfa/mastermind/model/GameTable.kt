package hu.idkfa.mastermind.model

import hu.idkfa.mastermind.repositories.PinStore
import kotlin.random.Random

class GameTable {

    //current position of cursor(row, column)
    private var row = 0
    private var column = 0
    //size of game table
    private val MAX_ROW = 8
    private val MAX_COL = 4

    // _table stores IDs of pins
    private val _table = MutableList<Int>(MAX_ROW * MAX_COL){
        //PinStore.pins.get(it%(PinStore.pins.size)).id
        0
    }
    // numbers needs to be guessed
    val toGuess =  List(MAX_COL){
        Random.nextInt(1, PinStore.size)
    }
    val results = List(MAX_ROW){ RowResult() }

    private fun resetTable(){//set each element of table to null
        for(i in _table.indices){
            _table[i] = 0
        }
    }

    fun asList(): MutableList<Int> = _table
    fun reset(){
        row = 0
        column = 0
    }
}