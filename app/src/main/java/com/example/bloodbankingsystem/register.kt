package com.example.bloodbankingsystem


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class register : AppCompatActivity() {

    var registerButton: Button? = null
    var name: EditText? = null
    var bloodGroup: EditText? = null
    var contact: EditText? = null
    var address: EditText? = null
    var age: EditText? = null
    var datel: EditText? = null
    lateinit var dbHelper: donord
    lateinit var dbd: SQLiteDatabase
    var fieldsChecked = false
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        dbHelper = donord(this)
        dbd = dbHelper.writableDatabase

        registerButton = findViewById(R.id.button3)
        name = findViewById(R.id.editTextText2)
        bloodGroup = findViewById(R.id.bg)
        contact = findViewById(R.id.editTextPhone2)
        age = findViewById(R.id.editTextNumberSigned)
        datel=findViewById(R.id.editTextDate)
        address = findViewById(R.id.editTextTextPostalAddress2)

        val reb=registerButton as Button
        reb.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                fieldsChecked=checkAllFields()
                if (fieldsChecked) {
                    insertDonor()

                }
            }

        })
    }

    fun checkAllFields(): Boolean {
        if (name!!.text.isBlank()) {
            name!!.error = "This field is required"
            return false
        }

        if (bloodGroup!!.text.isBlank()) {
            bloodGroup!!.error = "Blood Group is required"
            return false
        } else if (bloodGroup!!.text.length > 3) {
            bloodGroup!!.error = "Invalid blood group"
            return false
        }

        if (address!!.text.isBlank()) {
            address!!.error = "Address is required"
            return false
        }

        if (contact!!.text.isBlank()) {
            contact!!.error = "Contact number is required"
            return false
        } else if (contact!!.text.length != 10) {
            contact!!.error = "Contact number is invalid"
            return false
        }

        if (age!!.text.isBlank()) {
            age!!.error = "Age is required"
            return false
        }

        val ageInt = age!!.text.toString().toInt()
        if (ageInt !in 18..60) {
            age!!.error = "Not eligible for donating blood"
            return false
        }

        return true
    }

    fun insertDonor() {
        val data = ContentValues()
            data.put(donord.COLUMN_NAME, name?.text.toString())
            data.put(donord.COLUMN_LOCATION, address?.text.toString())
            data.put(donord.COLUMN_CONTACT, contact?.text.toString())
            data.put(donord.COLUMN_BLOOD_TYPE, bloodGroup?.text.toString())

        val newRow = dbd.insert(donord.TABLE_DONOR2, null, data)

        if (newRow == -1L) {
            Toast.makeText(this, "Error inserting donor", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Donor registered successfully", Toast.LENGTH_SHORT).show()
            // You can add an intent here to navigate to another activity if needed

        }
        /*val intt=Intent(this@register,register::class.java)
        startActivity(intt)*/
    }
}