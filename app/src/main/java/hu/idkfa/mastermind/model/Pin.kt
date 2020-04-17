package hu.idkfa.mastermind.model

import android.graphics.Bitmap

//every pin has and id, and two images one for the rvChooser and one for the GameTable
data class Pin (val id: Int, val chooserImage: Bitmap, val tableImage: Bitmap)