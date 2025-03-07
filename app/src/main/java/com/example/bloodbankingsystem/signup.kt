package com.example.bloodbankingsystem

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast

class signup : AppCompatActivity() {
    // two buttons
    var sign: Button? = null
    var username: EditText? = null
    var password: EditText? = null
    var confirmpassword: EditText? = null
    lateinit var dbHelper2: userdata
    lateinit var db: SQLiteDatabase
    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    var isAllFieldsChecked = false
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        dbHelper2 = userdata(this)
        db = dbHelper2.writableDatabase

        // register buttons with their proper IDs.
        sign = findViewById(R.id.sign)
        val bu = sign as Button
// Now you can safely use button assuming login is indeed a Button


        // register all the EditText fields with their IDs.
        username = findViewById(R.id.txt2)
        password = findViewById(R.id.editTextTextPassword3)
        confirmpassword = findViewById(R.id.editTextTextPassword2)

        // handle the PROCEED button
        bu.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields()

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    insertUser()
                    val i = Intent(this@signup, home::class.java)
                    startActivity(i)
                }
            }
        })

        /* // if user presses the cancel button then close the
        // application or the particular activity.
        bCancel.setOnClickListener(object : View.OnClickListener() {
            fun onClick(v: View?) {
                finish()
                System.exit(0)
            }
        })*/
    }

    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    fun CheckAllFields(): Boolean {
        if (username!!.length() == 0) {
            username!!.error = "This field is required"
            return false
        }

        if (password!!.length() == 0) {
            password!!.error = "Password is required"
            return false
        } else if (password!!.length() < 6) {
            password!!.error = "Password must be minimum 8 characters"
            return false
        }

        if (confirmpassword!!.length() == 0) {
            confirmpassword!!.error = "Confirm Password is required"
            return false
        }else if (!password!!.text.toString().equals(confirmpassword!!.text.toString())){
            confirmpassword!!.error = "Password and confirm password must be same"
            return false
        }
        // after all validation return true.
        return true
    }
    private fun insertUser() {
        val values = ContentValues().apply {
            put(userdata.COLUMN_USERNAME, username?.text.toString())
            put(userdata.COLUMN_PASSWORD, password?.text.toString())
        }

        val newRowId = db.insert(userdata.TABLE_USERS, null, values)

        if (newRowId == -1L) {
            Toast.makeText(this, "Error inserting user", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "User inserted successfully", Toast.LENGTH_SHORT).show()
        }
    }


}
