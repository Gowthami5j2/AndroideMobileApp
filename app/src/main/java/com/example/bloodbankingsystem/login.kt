package com.example.bloodbankingsystem
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast



class login : AppCompatActivity() {
    // two buttons
    var login: Button? = null
    lateinit var dbHelper1: userdata
     lateinit var db: SQLiteDatabase

    // four text fields
    var username: EditText? = null
    var password: EditText? = null

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    var isAllFieldsChecked = false
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // register buttons with their proper IDs.
         login = findViewById(R.id.sign)
         val button = login as Button
// Now you can safely use button assuming login is indeed a Button
         dbHelper1 = userdata(this)
         db = dbHelper1.readableDatabase

         // register all the EditText fields with their IDs.
        username = findViewById(R.id.txt2)
        password = findViewById(R.id.editTextTextPassword3)

        // handle the PROCEED button
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields()

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    val enteredUsername = username?.text.toString()
                    val enteredPassword = password?.text.toString()
                    if (enteredUsername.isNotEmpty() && enteredPassword.isNotEmpty()) {
                        if (validateCredentials(enteredUsername, enteredPassword)) {
                            Toast.makeText(this@login, "User login is successful!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@login, home::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@login, "Invalid username or password", Toast.LENGTH_SHORT).show()
                        }
                    }

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
            }

            // after all validation return true.
            return true
        }
    private fun validateCredentials(username: String, password: String): Boolean {
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${userdata.TABLE_USERS} WHERE ${userdata.COLUMN_USERNAME} = ? AND ${userdata.COLUMN_PASSWORD} = ?",
            arrayOf(username, password)
        )

        val result = cursor.count > 0
        cursor.close()
        return result
    }

}
