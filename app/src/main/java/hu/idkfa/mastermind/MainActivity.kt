package hu.idkfa.mastermind

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import hu.idkfa.mastermind.adapter.FunctionMode
import hu.idkfa.mastermind.adapter.PinHolderAdapter
import hu.idkfa.mastermind.adapter.RowResultsAdapter
import hu.idkfa.mastermind.model.GameTable
import hu.idkfa.mastermind.model.RowResultState
import hu.idkfa.mastermind.repositories.PinStore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PinHolderAdapter.OnPinClickListener, RowResultsAdapter.OnResultClickListener {

    lateinit var gameTable: GameTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //full screen
        window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        val size = Point()
        var a = windowManager.defaultDisplay.getSize(size)
        Constants.inititemSizes(size.x, size.y)

        gameTable = GameTable()

        rvTable.apply{
            layoutManager = GridLayoutManager(applicationContext,4)
            adapter = PinHolderAdapter(
                gameTable.mainTable,
                this@MainActivity,
                FunctionMode.MAIN_TABLE
            )
        }


        rvGuess.apply{
            layoutManager = LinearLayoutManager(applicationContext).apply {
                orientation = RecyclerView.HORIZONTAL
            }
            adapter = PinHolderAdapter(
                gameTable.toGuess,
                this@MainActivity,
                FunctionMode.TOGUESS
            )
        }
        rvChooser.apply {
            layoutManager = LinearLayoutManager(applicationContext).apply {
                orientation = RecyclerView.HORIZONTAL
            }
            adapter = PinHolderAdapter(
                (1..PinStore.size).toList(), //every pin by id
                this@MainActivity,
                FunctionMode.CHOOSER
            )

        }

        rvResult.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = RowResultsAdapter(gameTable.results, this@MainActivity)
        }
        Log.d("TO_GUESS", gameTable.toGuess.joinToString(", "){ PinStore.getColorNameById(it) })

    }

    override fun onTablePinClick(position: Int, mode: FunctionMode) {
        //the adapter is multifunctional so we need to check wich table we clicked
        if(mode == FunctionMode.CHOOSER){
            //if i click on one of elements of chooser
            //add that id to gametable

            gameTable.add(position+1).also{
                if(it >= 0){ rvTable.adapter!!.notifyItemChanged(it)
                    if(gameTable.currentRowFull()){
                        rvResult.adapter!!.notifyItemChanged(gameTable.row)
                    }
                }
            }

        }else if(mode == FunctionMode.MAIN_TABLE){
            /**
             * delete selected item, only if in the current row and is not already empty
             * if returns with true, the row resultstate is need to be changed
             */
            if(gameTable.freePosition(position)){
                rvTable.adapter!!.notifyItemChanged(position)
                rvResult.adapter!!.notifyItemChanged(gameTable.row)
            }

        }

    }
    private fun refreshAdapters(){
        rvTable.adapter!!.notifyDataSetChanged()
        rvGuess.adapter!!.notifyDataSetChanged()
        rvResult.adapter!!.notifyDataSetChanged()
    }

    override fun onResultClick(position: Int) {
        //check current rows and move to next
        if( gameTable.results[position].state == RowResultState.READY ){
            val response = gameTable.rateCurrentRow()
            //Log.d("asd","b: "+ gameTable.results[position].black.toString() + " w:"+ gameTable.results[position].white.toString() )
            rvResult.adapter!!.notifyItemChanged(gameTable.row-1) //-1 cause in rate method already increased row
            if(response == Constants.GAME_WON){
                MaterialDialog(this).show {
                    title(text = "Congratulations!")
                    message(text = "You guessed the 4 colors!")
                    positiveButton(text = "Play another game"){
                        //reset game and dismiss dialog
                        gameTable.reset()
                        //after reset refresh ui elements
                        refreshAdapters()
                        //hide dialog
                        dismiss()
                    }
                    negativeButton (text = "Quit") {
                        //quit app and dismiss dialog
                        dismiss()
                        finish()
                    }
                }
            }else if(response == Constants.GAME_OVER){
                MaterialDialog(this).show {
                    title(text = "Game over!")
                    message(text = "Sorry u lost. These were the colors:\n${gameTable.toGuess.joinToString(", "){ PinStore.getColorNameById(it) }}")
                    positiveButton(text = "Play another game"){
                        //reset game and dismiss dialog
                        gameTable.reset()
                        //after reset refresh ui elements
                        refreshAdapters()
                        //hide dialog
                        dismiss()
                    }
                    negativeButton (text = "Quit") {
                        //quit app and dismiss dialog
                        dismiss()
                        finish()
                    }
                }
            }
        }
    }
}
