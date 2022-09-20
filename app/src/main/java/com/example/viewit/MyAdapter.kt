package com.example.viewit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var TheDatas:List<String>):RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    private lateinit var mLister:onItemClickListener
    interface onItemClickListener{
        fun onItemClicked(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener)
    {
        mLister=listener
    }
    class MyViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView)
    {
        val titleView:TextView=itemView.findViewById(R.id.words)
        init {
            itemView.setOnClickListener{
                listener.onItemClicked(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.singlerow, parent, false)
        return MyViewHolder(view,mLister)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            val current=TheDatas[position]
            holder.titleView.text=current
        }
    }

    override fun getItemCount(): Int {
        return TheDatas.size
    }
}