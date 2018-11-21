package com.ibnugaury.animewatchlist.Main

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ibnugaury.animewatchlist.Database.DatabaseHelper
import com.ibnugaury.animewatchlist.Model.ModelData
import com.ibnugaury.animewatchlist.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {
    var dataAnime = ModelData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        bindView()
        textTitleEdit.addTextChangedListener(Watcher(forTitleEdit))
        textYearEdit.addTextChangedListener(Watcher(forYearEdit))

        btnEdit.setOnClickListener {

            val title = textTitleEdit.text.toString()
            val year = textYearEdit.text.toString()
            val genre = spinnerGenreEdit.selectedItem.toString()
            val id = spinnerGenreEdit.selectedItemId

            if (title.isEmpty()) {
                forTitleEdit.error = "Insert Anime Title"
                return@setOnClickListener
            }

            if (year.isEmpty()) {
                forYearEdit.error = "Insert Year"
                return@setOnClickListener
            }

            if (id < 1) {
                Toast.makeText(this, "Choose your Genre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

           
            dataAnime.title = title
            dataAnime.year = year
            dataAnime.genre = genre


            val stat = DatabaseHelper.updateData(dataAnime)
            if (stat > 0) {
                val bind = Bundle()
                bind.putParcelable("DATA", dataAnime)

                val intent = Intent()
                intent.putExtras(bind)

                setResult(Activity.RESULT_OK, intent)

                Toast.makeText(this, "Succesfully to Update Data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Abort to Update Data", Toast.LENGTH_SHORT).show()
            }
        }
            toolbarEdit.title = "Update Data"
    }


private fun bindView() {
    val bind = intent.extras
    dataAnime = bind.getParcelable("DATA")

    textTitleEdit.setText(dataAnime.title)
    textYearEdit.setText(dataAnime.year)

    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getGenre())
    spinnerGenreEdit.adapter = adapter
    spinnerGenreEdit.setSelection(adapter.getPosition(dataAnime.genre))

}
    private fun getGenre(): List<String> = listOf("Genre", "Adventure", "Comedy", "Horror", "Romance", "Thriller")

    private class Watcher(textinput: TextInputLayout) : TextWatcher {

    val input = textinput

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        input.isErrorEnabled = false
    }
}

override fun onDestroy() {
    super.onDestroy()

    DatabaseHelper.closeDatabase()
}
}
