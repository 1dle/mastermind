package hu.idkfa.mastermind.model

//every row has a RowResult object for calculate row rightness
enum class RowResultState{
    PRE, //brefore check shows: simple background
    READY, //shows: green checkmark
    POST, //after check shows: row result

}
data class RowResult(var white: Int = 0, //correct color
                     var black: Int = 0, //correct color and correct position
                     var state: RowResultState = RowResultState.PRE){
    fun reset(){
        white = 0
        black = 0
        state = RowResultState.PRE
    }
}