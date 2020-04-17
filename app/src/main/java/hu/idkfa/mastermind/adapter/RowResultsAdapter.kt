package hu.idkfa.mastermind.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.idkfa.mastermind.R
import hu.idkfa.mastermind.graphics.Painter
import hu.idkfa.mastermind.model.RowResult
import hu.idkfa.mastermind.model.RowResultState
import hu.idkfa.mastermind.repositories.PinStore
import kotlinx.android.synthetic.main.item_chooser.view.*

class RowResultsAdapter(private var resultList: List<RowResult>, private var onResultClickListener: OnResultClickListener):
    RecyclerView.Adapter<RowResultsAdapter.MyViewHolder>()
{
    class MyViewHolder(val view: View, val onResultClickListener: OnResultClickListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        init{
            view.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
        }
    }
    interface OnResultClickListener{
        fun onResultClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val singleItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chooser, parent, false)
        //        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(
            singleItem,
            onResultClickListener
        )
    }

    override fun getItemCount() = resultList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.ivItem.setImageBitmap(
            when(resultList.get(position).state){
                RowResultState.PRE -> Painter.resultBG
                RowResultState.READY -> Painter.resultChecked
                RowResultState.POST -> Painter.resultBG //todo: change to image generator
            }
        )
        holder.view.setOnClickListener{
            onResultClickListener.onResultClick(position)
        }

    }
}
