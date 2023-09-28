package com.example.graphqldataapplication.fragment.teas.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqldataapplication.R
import com.example.graphqldataapplication.databinding.ItemsDataTeasBinding
import com.example.graphqldataapplication.interfaceclass.RecyclerViewItemClick
import com.example.graphqldatapplication.TeasQuery
import java.util.ArrayList


class TeasAdapter(
    private var teasList: ArrayList<TeasQuery.Tea?>,
    private val listener: RecyclerViewItemClick

) :
    RecyclerView.Adapter<TeasAdapter.TeasAdapterViewHolder>() {

    private var lastPosition = -1



    private lateinit var binding:ItemsDataTeasBinding

    inner class TeasAdapterViewHolder(binding: ItemsDataTeasBinding) :
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
    ): TeasAdapterViewHolder {
        binding =
            ItemsDataTeasBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TeasAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeasAdapterViewHolder, position: Int) {
        val tea = teasList.getOrNull(position)
        if (tea != null) {
            binding.tvId.text = tea.id
            binding.tvName.text = tea.name
            binding.tvPrice.text = tea.price.toString()
            binding.tvDesc.text = tea.description

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

            binding.ivEdit.setOnClickListener {
                listener.itemTeasEditClick(position)
            }
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





