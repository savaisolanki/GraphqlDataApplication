package com.example.graphqldataapplication.fragment.producer.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqldataapplication.R
import com.example.graphqldataapplication.databinding.ItemsDataBinding
import com.example.graphqldataapplication.interfaceclass.RecyclerViewItemClick
import com.example.graphqldatapplication.ProductListQuery
import java.util.ArrayList


class ProducerAdapter(
    private var producerList: ArrayList<ProductListQuery.Producer?>,
    private val listener: RecyclerViewItemClick

) :
    RecyclerView.Adapter<ProducerAdapter.ProducerAdapterViewHolder>() {

    private lateinit var binding:ItemsDataBinding
    private var lastPosition = -1


    inner class ProducerAdapterViewHolder(binding: ItemsDataBinding) :
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
    ): ProducerAdapterViewHolder {
        binding =
            ItemsDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ProducerAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProducerAdapterViewHolder, position: Int) {
        binding.tvId.text= producerList[position]?.id
        binding.tvName.text = producerList[position]?.name
        binding.tvLocation.text = producerList[position]?.location


        val scrollDirection = position - lastPosition
        if (scrollDirection > 0) {
            val rightSlideAnimation = AnimationUtils.loadAnimation(
                binding.root.context,
                R.anim.slide_right
            )
            holder.itemView.startAnimation(rightSlideAnimation)
        } else if (scrollDirection < 0) {
            val leftSlideAnimation = AnimationUtils.loadAnimation(
                binding.root.context,
                R.anim.slide_left
            )
            holder.itemView.startAnimation(leftSlideAnimation)
        }

        lastPosition = position




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





