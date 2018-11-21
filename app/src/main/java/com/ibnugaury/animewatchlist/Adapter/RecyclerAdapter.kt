package com.ibnugaury.animewatchlist.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ibnugaury.animewatchlist.Model.ModelData
import com.ibnugaury.animewatchlist.R
import kotlinx.android.synthetic.main.list_anime.view.*

class RecyclerAdapter (data: MutableList<ModelData>, listener: ItemClickListener) : RecyclerView.Adapter<AdapterHolder>() {

    private val datas = data
    private val listeners = listener

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        holder?.bind(datas[position], listeners, position)
    }

    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_anime, parent, false)
        return AdapterHolder(view)
    }


    }

