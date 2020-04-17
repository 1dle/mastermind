package hu.idkfa.mastermind

import android.app.Activity
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import hu.idkfa.mastermind.model.GameTable
import hu.idkfa.mastermind.model.Kolor
import hu.idkfa.mastermind.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), GameTableAdapter.OnKolorClickListener {

    val viewModel = GameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //full screen
        window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)



        val size = Point()
        var a = windowManager.defaultDisplay.getSize(size)
        Constants.inititemSizes(size.x, size.y);

        val gameTable = GameTable();
        rvTable.layoutManager = GridLayoutManager(applicationContext,4)
        rvTable.adapter = GameTableAdapter(gameTable.asList(), this )
    }

    override fun onKolorClick(position: Int) {

    }
}
