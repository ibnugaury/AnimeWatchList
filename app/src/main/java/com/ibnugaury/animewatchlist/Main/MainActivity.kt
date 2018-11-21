package com.ibnugaury.animewatchlist.Main

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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Anime Watch List"
        textTitle.addTextChangedListener(Watcher(forTitle))
        textYear.addTextChangedListener(Watcher(forYear))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getGenre())
        spinnerGenre.adapter = adapter

        btnInsert.setOnClickListener {

            val title = textTitle.text.toString()
            val year = textYear.text.toString()
            val genre = spinnerGenre.selectedItem.toString()
            val id = spinnerGenre.selectedItemId

            if (title.isEmpty()) {
                forTitle.error = "Insert Anime Title"
                return@setOnClickListener
            }

            if(year.isEmpty()) {
                forYear.error = "Insert Year"
                return@setOnClickListener
            }

            if (id < 1) {
                Toast.makeText(this, "Choose your Genre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = ModelData()
            data.title = title
            data.year = year
            data.genre = genre


            val stat = DatabaseHelper.insertData(data)

            if (stat > 0) {
                spinnerGenre.setSelection(0)
                textTitle.text.clear()
                textYear.text.clear()
                textTitle.clearFocus()
                textYear.clearFocus()

                Toast.makeText(this, "Successfully to insert data", Toast.LENGTH_SHORT).show()
            }

            else {
                Toast.makeText(this, "Abort to insert data", Toast.LENGTH_SHORT).show()
            }

        }

        btnDetail.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

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
