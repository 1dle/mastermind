package hu.idkfa.mastermind.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.idkfa.mastermind.repositories.PinStore
import kotlin.random.Random

class GameTable{

    //current position of cursor(row, column)
    private var row = 0
    private var column = 0
    //size of game table
    private val MAX_ROW = 8
    private val MAX_COL = 4

    // _table stores IDs of pins
    private val _table = MutableList(MAX_ROW * MAX_COL){0}
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

    var mainTable: List<Int> = _table
    fun reset(){
        row = 0
        column = 0
    }
    fun add(pinId: Int): Int{
        _table.set(row*MAX_COL+column, pinId)
        return row*MAX_COL+column++
    }
}