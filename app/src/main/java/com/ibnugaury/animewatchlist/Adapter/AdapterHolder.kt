package com.ibnugaury.animewatchlist.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import com.ibnugaury.animewatchlist.Model.ModelData
import kotlinx.android.synthetic.main.list_anime.view.*

class AdapterHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun bind(data: ModelData, listener: ItemClickListener, position: Int) = with(itemView) {

        rowList.text = data.title.substring(0, 1).capitalize()
        rowTitle.text = data.title
        rowYear.text = data.year

        setOnClickListener { listener.onClick(data, position) }


    }
}