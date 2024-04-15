package au.com.agl.kotlincats.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.agl.kotlincats.R
import au.com.agl.kotlincats.databinding.HeaderLayoutBinding
import au.com.agl.kotlincats.databinding.ItemLayoutBinding

class MyAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<DataItem> = mutableListOf()

    private val HEADER_VIEW_TYPE = 0
    private val ITEM_VIEW_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> HeaderViewHolder(
                HeaderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            ITEM_VIEW_TYPE -> ItemViewHolder(
                ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is HeaderDataItem -> {
                (holder as HeaderViewHolder).bind(item)
            }

            is PetDataItem -> {
                (holder as ItemViewHolder).bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HeaderDataItem -> HEADER_VIEW_TYPE
            is PetDataItem -> ITEM_VIEW_TYPE
        }
    }

    class HeaderViewHolder(private val binding: HeaderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HeaderDataItem) {
            binding.headerTitle.text = item.headerText
        }
    }

    class ItemViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PetDataItem) {
            binding.itemTitle.text = item.petName
        }
    }

    fun setData(items: List<DataItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}
