package hu.idkfa.mastermind.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.idkfa.mastermind.R
import hu.idkfa.mastermind.repositories.PinStore
import kotlinx.android.synthetic.main.item_chooser.view.*

enum class FunctionMode{
    MAIN_TABLE,
    CHOOSER,
    TOGUESS
}

class PinHolderAdapter(private var linearDataSet: List<Int>,
                       private val onPinClickListener: OnPinClickListener,
                       private val actAs: FunctionMode
):
    RecyclerView.Adapter<PinHolderAdapter.MyViewHolder>()
{
    class MyViewHolder(val view: View, val onPinListener: OnPinClickListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        init{
            view.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
        }
    }
    interface OnPinClickListener{
        fun onTablePinClick(position: Int, mode: FunctionMode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val singleItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chooser, parent, false)
        //        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(
            singleItem,
            onPinClickListener
        )
    }

    override fun getItemCount() = linearDataSet.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.ivItem.setImageBitmap(
            when(actAs){
                FunctionMode.MAIN_TABLE -> PinStore.getTableIconById(linearDataSet.get(position))
                FunctionMode.CHOOSER -> PinStore.getChooserIconById(linearDataSet.get(position))
                FunctionMode.TOGUESS -> PinStore.guessIcon
            }
        )
        holder.view.setOnClickListener{
            onPinClickListener.onTablePinClick(position, actAs)
        }

    }
}