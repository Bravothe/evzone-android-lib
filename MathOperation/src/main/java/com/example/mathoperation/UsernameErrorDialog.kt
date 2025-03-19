package com.example.mathoperation.dialogs

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mathoperation.R

object UsernameErrorDialog {
    fun showUsernameErrorDialog(context: Context, onSubmit: (String) -> Unit) {
        val dialog = Dialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_username_error, null)
        dialog.setContentView(view)
        dialog.setCancelable(false)

        // Find the EditText fields, Forget Password link, and Submit button
        val editTextUsername = view.findViewById<EditText>(R.id.edit_text_username)
        val editTextPassword = view.findViewById<EditText>(R.id.edit_text_password)
        val textForgetPassword = view.findViewById<TextView>(R.id.text_forget_password)
        val buttonSubmit = view.findViewById<Button>(R.id.button_submit)

        // Handle Forget Password click
        textForgetPassword.setOnClickListener {
            Toast.makeText(context, "Forget Password clicked (implement functionality)", Toast.LENGTH_SHORT).show()
            // Add your forget password logic here
        }

        // Handle Submit button click
        buttonSubmit.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (username.isEmpty()) {
                editTextUsername.error = "Username cannot be empty"
            } else {
                dialog.dismiss()
                onSubmit.invoke(username)
            }
            // Note: Password is optional in this case. If you want to validate the password,
            // you can add logic here and pass it via the callback if needed.
        }

        // Show the dialog
        dialog.show()

        // Optional: Set dialog window attributes for better appearance
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}