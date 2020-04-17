package hu.idkfa.mastermind

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.idkfa.mastermind.repositories.PinStore
import kotlinx.android.synthetic.main.item_chooser.view.*


class GameTableAdapter(private var linearDataSet: List<Int>,
                       private val onKolorClickListener: OnKolorClickListener):
    RecyclerView.Adapter<GameTableAdapter.MyViewHolder>()
{
    class MyViewHolder(val view: View, val onKolorListener: OnKolorClickListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        init{
            view.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
        }
    }
    interface OnKolorClickListener{
        fun onTablePinClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val singleItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chooser, parent, false)
        //        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(singleItem, onKolorClickListener)
    }

    override fun getItemCount() = linearDataSet.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.ivItem.setImageBitmap(
            PinStore.getTableIconById(linearDataSet.get(position))
        )
        holder.view.setOnClickListener{
            onKolorClickListener.onTablePinClick(position)
        }
    }
}