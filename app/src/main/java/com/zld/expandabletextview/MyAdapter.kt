package com.zld.expandabletextview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zld.expandlayout.ExpandLayout

/**
 *  Created by lingdong on 2018/5/6.
 *
 *
 **/
class MyAdapter(private var context: Context, private var list: List<String>) : RecyclerView.Adapter<MyAdapter.Companion.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text, null))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text = list[position]
        holder.expandLayout.setContent(text)
    }

    companion object {
        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var expandLayout = view.findViewById<ExpandLayout>(R.id.expand_layout)
        }
    }
}