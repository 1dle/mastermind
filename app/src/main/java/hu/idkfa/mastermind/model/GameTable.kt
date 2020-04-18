package hu.idkfa.mastermind.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.idkfa.mastermind.repositories.PinStore
import kotlin.random.Random

class GameTable{

    //current position of cursor(row, column)
    var row = 0
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
        resetTable()
    }

    /*
    This function add an element to the table and
    returns the index thats need to be updated in the RecyclerView
     */
    fun add(pinId: Int): Int{
        //if table is not full
        if (row*MAX_COL+column < MAX_ROW*MAX_COL){
            //the current row is not full
            if(column != MAX_COL){
                _table.set(row*MAX_COL+column, pinId)
                return row*MAX_COL+column++
            }else{
                //if in the last column show checkmark

                return -2 //wait for press result
            }

        }else{
            return -1 //table is full
            //probably game over
        }



    }

    /*
    This function checks if the current row is full
    and if it is, then change the state of the current RowResult
     */
    fun currentRowFull(): Boolean{
        for( i in row*MAX_COL..row*MAX_COL+3){
            if(_table[i] == 0){
                results[row].state = RowResultState.PRE
                return false
            }
        }
        results[row].state = RowResultState.READY
        return true
    }

    /*
    rate the current row (current row = row)
    this function runs when user tap on current RowResult
    and the RowResult.black and white properties set to the result of the check
    after it, state of row is changed and we move the cursor to the next row
     */
    fun rateCurrentRow(){
        var blacks = 0;
        var whites = 0;

        val dontCheckJ = mutableListOf<Int>()
        val dontCheckI = mutableListOf<Int>()
        //first check for blacks
        for( i in row*MAX_COL..row*MAX_COL+3) {
            for( j in toGuess.indices){
                //ha már van egy találat azzal az elemmel akkor mégegyszer nem nézem
                if(!dontCheckJ.contains(j)){
                    //ha jó helyen van és jó szín
                    if((i%MAX_COL) == j && _table.get(i) == toGuess.get(j)) {
                        dontCheckJ.add(j)
                        dontCheckI.add(i)
                        blacks++
                        break
                    }
                }

            }
        }
        //now check for whites
        for( i in row*MAX_COL..row*MAX_COL+3) {
            if(!dontCheckI.contains(i)){
                for( j in toGuess.indices){
                    //ha már van egy találat azzal az elemmel akkor mégegyszer nem nézem
                    if(!dontCheckJ.contains(j)){
                        //ha jó helyen van és jó szín
                        if((i%MAX_COL) != j && _table.get(i) == toGuess.get(j)) {
                            dontCheckJ.add(j)
                            whites++
                            break
                        }
                    }

                }
            }

        }
        //add results to rowRest
        results[row].black = blacks
        results[row].white = whites
        //change state
        results[row].state = RowResultState.POST
        //move the cursor to the next row
        row++
        column = 0
    }


}