package hu.idkfa.mastermind

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    }

    override fun onTablePinClick(position: Int, mode: FunctionMode) {
        //delete selected item, only if in the current row and is not already empty
        //the adapter is multifunctional so we need to check wich table we clicked
        //Toast.makeText(this,gameTable.asList().get(position).toString(), Toast.LENGTH_SHORT).show()
        if(mode == FunctionMode.CHOOSER){
            //if i click on one of elements of chooser
            //add that id to gametable

            gameTable.add(position+1).also{
                if(it >= 0) rvTable.adapter!!.notifyItemChanged(it)

                if(gameTable.currentRowFull()){
                    rvResult.adapter!!.notifyItemChanged(gameTable.row)
                }
            }
        }
    }

    override fun onResultClick(position: Int) {
        //check current rows and move to next
        if( gameTable.results[position].state == RowResultState.READY ){
            gameTable.rateCurrentRow()
            Log.d("asd","b: "+ gameTable.results[position].black.toString() + " w:"+ gameTable.results[position].white.toString() )
            rvResult.adapter!!.notifyItemChanged(gameTable.row-1) //-1 cause in rate method already increased row
        }
    }
}
