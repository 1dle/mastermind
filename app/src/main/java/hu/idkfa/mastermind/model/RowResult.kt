package hu.idkfa.mastermind.model

//every row has a RowResult object for calculate row rightness
data class RowResult(var white: Int = 0, //correct color
                     var black: Int = 0, //correct color and correct position
                     var enabled: Boolean = false) //check mark is enabled