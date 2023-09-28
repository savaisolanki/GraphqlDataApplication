package com.example.graphqldataapplication.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqldataapplication.databinding.ItemsDataBinding
import com.example.graphqldataapplication.interfaceclass.RecyclerViewItemClick
import com.example.graphqldatapplication.ProductListQuery
import java.util.ArrayList


class GraphQlAdapter(
    private var producerList: ArrayList<ProductListQuery.Producer?>,
    private val listener: RecyclerViewItemClick

) :
    RecyclerView.Adapter<GraphQlAdapter.GraphQlAdapterViewHolder>() {

    private lateinit var binding:ItemsDataBinding


    inner class GraphQlAdapterViewHolder(binding: ItemsDataBinding) :
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
    ): GraphQlAdapterViewHolder {
        binding =
            ItemsDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GraphQlAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GraphQlAdapterViewHolder, position: Int) {
        binding.tvId.text= producerList[position]?.id
        binding.tvName.text = producerList[position]?.name
        binding.tvLocation.text = producerList[position]?.location

        binding.root.setOnClickListener {
            listener.itemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return producerList.size
    }

    fun removeItem(position: Int) {
        if (position in 0 until producerList.size) {
            producerList.removeAt(position)
            notifyItemRemoved(position)
        }
    }



}





