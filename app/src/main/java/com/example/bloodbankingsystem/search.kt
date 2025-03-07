package com.example.bloodbankingsystem
/*
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class search : AppCompatActivity() {

    private lateinit var linearLayout: LinearLayout
    lateinit var bloodGroupSpinner:Spinner

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        val bloodGroupOptions = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
         bloodGroupSpinner = findViewById<Spinner>(R.id.spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroupOptions)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        // Apply the adapter to the spinner
        bloodGroupSpinner.adapter = adapter
        linearLayout = findViewById(R.id.linearLayout)
        val buttonSearch = findViewById<Button>(R.id.button4)
        buttonSearch.setOnClickListener {
            searchDonor()
        }
    }

    @SuppressLint("Range")
    private fun searchDonor() {

        val locationEditText = findViewById<EditText>(R.id.editTextText)

        val bloodGroup = bloodGroupSpinner.selectedItem.toString()
        val location = locationEditText.text.toString()

        // Open or create your SQLite database
        val dbHelper = donord(this)
        val db = dbHelper.readableDatabase

        // Execute the query
        val cursor = db.rawQuery(
            "SELECT * FROM YourTable WHERE bloodType = ? AND location = ?",
            arrayOf(bloodGroup, location)
        )

        // Clear previous results
        linearLayout.removeAllViews()

        // Populate LinearLayout with TextViews dynamically
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val contact = cursor.getString(cursor.getColumnIndex("contact"))

                val textView = TextView(this)
                textView.text = "Name: $name\nContact: $contact"
                linearLayout.addView(textView)
            } while (cursor.moveToNext())
        } else {
            // If no results found, display a message
            val textView = TextView(this)
            textView.text = "No donors found for the specified criteria."
            linearLayout.addView(textView)
        }

        cursor.close()
        db.close()
    }
}*/
import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bloodbankingsystem.donord

class search : AppCompatActivity() {

    private lateinit var linearLayout: LinearLayout
    private lateinit var bloodGroupSpinner: Spinner

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val bloodGroupOptions = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
        bloodGroupSpinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroupOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bloodGroupSpinner.adapter = adapter

        linearLayout = findViewById(R.id.linear)

        val buttonSearch = findViewById<Button>(R.id.button)
        buttonSearch.setOnClickListener {
            searchDonor()
        }
    }

    @SuppressLint("Range")
    private fun searchDonor() {
        val locationEditText = findViewById<EditText>(R.id.editTextText)
        val bloodGroup = bloodGroupSpinner.selectedItem.toString()
        val location = locationEditText.text.toString()

        val dbHelper = donord(this)
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${donord.TABLE_DONOR2} WHERE ${donord.COLUMN_BLOOD_TYPE} = ? AND ${donord.COLUMN_LOCATION} = ?",
            arrayOf(bloodGroup, location)
        )

        linearLayout.removeAllViews()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(donord.COLUMN_NAME))
                val contact = cursor.getString(cursor.getColumnIndex(donord.COLUMN_CONTACT))
                val textView = TextView(this)
                textView.text = "Name: $name\nContact: $contact"
                linearLayout.addView(textView)
            } while (cursor.moveToNext())
        } else {
            val textView = TextView(this)
            textView.text = "No donors found for the specified criteria."
            linearLayout.addView(textView)
        }

        cursor.close()
        db.close()
    }
}
