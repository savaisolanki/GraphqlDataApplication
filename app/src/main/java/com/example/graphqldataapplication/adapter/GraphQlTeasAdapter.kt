package com.example.graphqldataapplication.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqldataapplication.databinding.ItemsDataTeasBinding
import com.example.graphqldataapplication.interfaceclass.RecyclerViewItemClick
import com.example.graphqldatapplication.TeasQuery
import java.util.ArrayList


class GraphQlTeasAdapter(
    private var teasList: ArrayList<TeasQuery.Tea>,
    private val listener: RecyclerViewItemClick

) :
    RecyclerView.Adapter<GraphQlTeasAdapter.GraphQlTeasAdapterViewHolder>() {

    private lateinit var binding:ItemsDataTeasBinding


    inner class GraphQlTeasAdapterViewHolder(binding: ItemsDataTeasBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }



    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GraphQlTeasAdapterViewHolder {
        binding =
            ItemsDataTeasBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GraphQlTeasAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GraphQlTeasAdapterViewHolder, position: Int) {
        binding.tvId.text= teasList[position].id
        binding.tvName.text = teasList[position].name
        binding.tvPrice.text = teasList[position].price.toString()
        binding.tvDesc.text = teasList[position].description

        binding.root.setOnClickListener {
            listener.itemClick(position)
        }

        binding.ivEdit.setOnClickListener {
            listener.itemTeasEditClick(position)
        }
    }

    override fun getItemCount(): Int {
        return teasList.size
    }

    fun removeItem(position: Int) {
        if (position in 0 until teasList.size) {
            teasList.removeAt(position)
            notifyItemRemoved(position)
        }
    }



}





