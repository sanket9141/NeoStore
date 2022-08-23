package com.example.neostore.forgotpassword

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.neostore.R
import com.example.neostore.login.MainActivity
import com.example.neostore.api.RetrofitClient
import kotlinx.android.synthetic.main.activity_forgot_password_screen.*
import kotlinx.android.synthetic.main.activity_registration_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_screen)

        btnForgotEmailSubmit.setOnClickListener(){
            validateForm()
        }
    }

    private fun validateForm() {
        if(!forgotEmailValidate()){
            return
        }else{
            submitEmail()
        }
    }

    private fun submitEmail() {
        val emailAddress = txtInputForgotEmail.editText?.text.toString()

        RetrofitClient.getClient.forgotPassword(emailAddress).enqueue(object : Callback<ForgotPasswordResponse?> {
            override fun onResponse(
                call: Call<ForgotPasswordResponse?>,
                response: Response<ForgotPasswordResponse?>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@ForgotPasswordScreen,"${response.body()?.user_msg}",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@ForgotPasswordScreen, MainActivity::class.java))
                }else{
                    Toast.makeText(this@ForgotPasswordScreen,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ForgotPasswordResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun forgotEmailValidate(): Boolean {
        val email = txtForgotEmail.text.toString()
        if(email.isEmpty()){
            txtInputForgotEmail.apply {
                error = "Field cannot be blank"
                isExpandedHintEnabled = false
                requestFocus()
            }
            return false
        }
        else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){ //This function returns a boolean indicating whether the input string completely matches the pattern or not.
            txtInputForgotEmail.apply {
                error = null
                isExpandedHintEnabled = true
            }
            txtInputForgotEmail.requestFocus()
            return true
        }
        else{
            txtInputForgotEmail.apply {
                error="please insert valid Email address"
                isExpandedHintEnabled = false
            }
            txtInputForgotEmail.requestFocus()
            return false
        }

    }
}