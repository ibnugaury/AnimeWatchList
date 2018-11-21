package com.ibnugaury.animewatchlist.Main

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ListAdapter
import com.ibnugaury.animewatchlist.Adapter.ItemClickListener
import com.ibnugaury.animewatchlist.Adapter.RecyclerAdapter
import com.ibnugaury.animewatchlist.Database.DatabaseHelper
import com.ibnugaury.animewatchlist.Model.ModelData
import com.ibnugaury.animewatchlist.R
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_main.*

class ListActivity : AppCompatActivity(), ItemClickListener, DetailAnimeDialog.OnDialogItemClick {

    private var dataAnimeList: MutableList<ModelData> = ArrayList()
    private var positionStatements = 0
    lateinit private var adapterAnimeList : RecyclerAdapter

    override fun dialogDeleteCallback(data: ModelData) {
        this.dataAnimeList.remove(data)
        adapterAnimeList.notifyDataSetChanged()

        if (this.dataAnimeList.size > 0) {
            textEmpty.visibility = View.GONE
        } else {
            textEmpty.visibility = View.VISIBLE
        }

    }

    override fun dialogEditCallback(data: ModelData) {

        val bind = Bundle()
        bind.putParcelable("DATA", data)

        val edit = Intent(this, UpdateActivity::class.java)
        edit.putExtras(bind)
        startActivityForResult(edit, 1)
    }


    override fun onClick(data: ModelData, position: Int) {
        DetailAnimeDialog.newInstance(data, this).show(supportFragmentManager, "DETAIL")
        positionStatements = position
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        dataAnimeList = DatabaseHelper.getAllData()
        adapterAnimeList = RecyclerAdapter(dataAnimeList, this)

        RecyclerList.setHasFixedSize(true)
        RecyclerList.layoutManager = LinearLayoutManager(this)
        RecyclerList.adapter = adapterAnimeList

        toolbars.title = "Anime List"

        if (dataAnimeList.size > 0) {
            textEmpty.visibility = View.GONE
        } else {
            textEmpty.visibility = View.VISIBLE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                val dataAnime: ModelData = data.extras.getParcelable("DATA")
                dataAnimeList[positionStatements] = dataAnime
                adapterAnimeList.notifyDataSetChanged()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        DatabaseHelper.closeDatabase()
    }



}
