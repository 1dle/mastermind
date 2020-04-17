package hu.idkfa.mastermind

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.idkfa.mastermind.adapter.FunctionMode
import hu.idkfa.mastermind.adapter.PinHolderAdapter
import hu.idkfa.mastermind.adapter.RowResultsAdapter
import hu.idkfa.mastermind.model.GameTable
import hu.idkfa.mastermind.repositories.PinStore
import hu.idkfa.mastermind.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), PinHolderAdapter.OnPinClickListener, RowResultsAdapter.OnResultClickListener {

    val viewModel = GameViewModel()
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

        rvTable.apply{
            layoutManager = GridLayoutManager(applicationContext,4)
            adapter = PinHolderAdapter(
                gameTable.asList(),
                this@MainActivity,
                FunctionMode.MAIN_TABLE
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

    }

    override fun onResultClick(position: Int) {
        TODO("Not yet implemented")
    }
}
