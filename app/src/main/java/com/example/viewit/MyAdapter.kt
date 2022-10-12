package com.example.viewit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.viewit.data.WordsForModel

class MyAdapter(val TheDatas:ArrayList<WordsForModel>):RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    private lateinit var mListner:onItemClickListener
    interface onItemClickListener{
        fun onItemClicked(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener)
    {
        mListner=listener
    }
    inner class MyViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView)
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
        return MyViewHolder(view,mListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            val current=TheDatas[position]
            holder.titleView.text=current.words
        }
    }

    override fun getItemCount(): Int {
        return TheDatas.size
    }
}