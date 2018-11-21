package com.ibnugaury.animewatchlist.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ModelData (var id : Int, var title : String, var year : String, var genre : String) : Parcelable {
        constructor() : this(0, "", "", "")
}