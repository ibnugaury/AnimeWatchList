package com.ibnugaury.animewatchlist.Main

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ibnugaury.animewatchlist.Database.DatabaseHelper
import com.ibnugaury.animewatchlist.Model.ModelData
import com.ibnugaury.animewatchlist.R
import kotlinx.android.synthetic.main.detail_anime.*

class DetailAnimeDialog : BottomSheetDialogFragment() {

    private var dataAnime = ModelData()

    companion object {
        lateinit private var listeners: OnDialogItemClick

        fun newInstance(data: ModelData, listener: OnDialogItemClick): DetailAnimeDialog {

            listeners = listener
            val detail = DetailAnimeDialog()

            val bind = Bundle()
            bind.putParcelable("DATA", data)

            detail.arguments = bind
            return detail

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments

        if (args != null)
            dataAnime = args.getParcelable("DATA")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_anime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogTitle.text = dataAnime.title.toUpperCase()
        dialogYear.text = dataAnime.year.toString()
        dialogGenre.text = dataAnime.genre

        toolbarDialog.inflateMenu(R.menu.detail_menu)
        toolbarDialog.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.dialogEdit -> {
                    listeners.dialogEditCallback(dataAnime)
                    dialog.dismiss()
                }
                R.id.dialogHapus -> {
                    val build = context?.let { it1 -> AlertDialog.Builder(it1) }
                    build?.setTitle("Hapus Data")
                    build?.setMessage("Apakah Kamu Ingin Menghapus Data ${dataAnime.title.toUpperCase()}")
                    build?.setPositiveButton("HAPUS", { _, _ ->

                        val stas = DatabaseHelper.deleteData(dataAnime.id)

                        if (stas != 0) {
                            dialog.dismiss()
                            listeners.dialogDeleteCallback(dataAnime)

                            Toast.makeText(activity, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show()
                        }

                    })
                    build?.setNegativeButton("BATAL", null)
                    build?.create()?.show()
                }
            }
            true
        }
    }

    interface OnDialogItemClick {
        fun dialogEditCallback(data: ModelData)
        fun dialogDeleteCallback(data: ModelData)
    }

    override fun onDestroy() {
        super.onDestroy()
        DatabaseHelper.closeDatabase()
    }




}