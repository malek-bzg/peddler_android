package com.example.peddler.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.peddler.R
import com.example.peddler.Session
import com.example.peddler.api.ApiInterface
import com.example.peddler.api.models.LoginResponse
import com.example.peddler.api.models.RegisterResponse
import com.example.peddler.api.models.User
import com.example.peddler.hideKeyboard
import com.example.peddler.showToast
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        buttonRegister.setOnClickListener {
            hideKeyboard()
            if (validateForm()) {
                register()
                println("erre")
            }
        }
    }
    private fun register(){
        hideKeyboard()
        val apiInterface = ApiInterface.api
        apiInterface.register(User(firstName = firstName.text.toString(), lastName =  lastName.text.toString(), email = email.text.toString(), address =  address.text.toString(), cin =  CIN.text.toString(), password = Password.text.toString(), ConfirmPassword =  ComfirmPassword.text.toString())).enqueue(
            object :Callback<RegisterResponse>{
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.code() == 403) {
                        showToast("Utilisateur existe déjà !")
                    } else {
                        val session = Session.getInstance(this@RegisterActivity)
                        session.firstName = response.body()?.user?.firstName
                        session.lastName = response.body()?.user?.lastName
                        session.email = response.body()?.user?.email
                        session.address = response.body()?.user?.address
                        session.cin = response.body()?.user?.cin
                        session.address = response.body()?.user?.address
                        val intent = Intent(this@RegisterActivity, MainActivity2::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    showToast("Une erreur est survenue !")
                }
            }
        )
    }
    private fun validateForm(): Boolean {
        if (firstName.text.toString().equals("")) {
            nomRequired.visibility = View.VISIBLE
            return false
        } else {
            firstName.visibility = View.INVISIBLE
        }
        /*if (!Patterns.EMAIL_ADDRESS.matcher(lastName.text.toString()!!).matches()) {
            emailRequired.visibility = View.VISIBLE
            return false
        } else {
            emailRequired.visibility = View.INVISIBLE
        }*/

        if (lastName.text.toString() == "") {
            nomRequired.visibility = View.VISIBLE
            return false
        } else {
            nomRequired.visibility = View.INVISIBLE
        }
        if (email.text.toString() == "") {
            addresRequired.visibility = View.VISIBLE
            return false
        } else {
            addresRequired.visibility = View.INVISIBLE
        }
        if (address.text.toString() == "") {
            taxRequired.visibility = View.VISIBLE
            return false
        } else {
            taxRequired.visibility = View.INVISIBLE
        }
        if (CIN.text.toString() == "") {
            CINRequired.visibility = View.VISIBLE
            return false
        } else {
            CINRequired.visibility = View.INVISIBLE
        }
        if (Password.text.toString() == "") {
            PasswordRequired.visibility = View.VISIBLE
            return false
        } else {
            PasswordRequired.visibility = View.INVISIBLE
        }


        if (ComfirmPassword.text.toString() == "") {
            ComfirmPasswordRequired.visibility = View.VISIBLE
            return false
        } else {
            ComfirmPasswordRequired.visibility = View.INVISIBLE
        }

        if (ComfirmPassword.text.toString() != Password.text.toString()) {
            ComfirmPasswordRequired.visibility = View.VISIBLE
            return false
        } else {
            ComfirmPasswordRequired.visibility = View.INVISIBLE

        }

        return true

    }

}