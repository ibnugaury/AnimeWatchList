package com.ibnugaury.animewatchlist.Adapter

import com.ibnugaury.animewatchlist.Model.ModelData

interface ItemClickListener {
    fun onClick(data: ModelData, position: Int)
}