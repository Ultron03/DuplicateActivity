package com.example.duplicateactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecyclerViewAdapter(
    private val listofitem:List<ItemOfRecyclerView>,
    private val onItemLongClickListener: OnItemLongClickListener
):RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(private val view: View,private val onItemLongClickListener: OnItemLongClickListener):ViewHolder(view){
        val img:ImageView = view.findViewById(R.id.img)
        val title:TextView = view.findViewById(R.id.title)
        val size:TextView = view.findViewById(R.id.size)
        val card:CardView = view.findViewById(R.id.card_img)
        val tick:ImageView = view.findViewById(R.id.img_tick)
        init {
            view.setOnLongClickListener {
                onItemLongClickListener.onItemLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
            view.setOnClickListener{
                onItemLongClickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_images,parent,false)

        return RecyclerViewHolder(view,onItemLongClickListener)
    }

    override fun getItemCount(): Int {
        return listofitem.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.img.setImageResource(listofitem[position].img)
        holder.title.text = listofitem[position].title
        holder.size.text = listofitem[position].size
        listofitem[position].cardView = holder.card
        listofitem[position].tick = holder.tick

    }

    interface OnItemLongClickListener {

        fun onItemLongClick(position: Int)
        fun onItemClick(position: Int)

    }

}


