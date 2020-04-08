package hu.idkfa.mastermind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.idkfa.mastermind.viewmodel.GameViewModel

class MainActivity : AppCompatActivity() {

    val viewModel = GameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
