package com.example.peddler.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.peddler.R
import com.example.peddler.Session
import com.example.peddler.api.ApiInterface
import com.example.peddler.api.models.LoginResponse
import com.example.peddler.hideKeyboard
import com.example.peddler.showToast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonlogin.setOnClickListener {
            hideKeyboard()
            if (validateForm()) {
                login()
            }
        }

        goToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(){
        hideKeyboard()
        val apiInterface = ApiInterface.api
        apiInterface.login(et_email.text.toString(), et_password.text.toString()).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.code() == 403) {
                        showToast("Prière de vérifier vos données de connexion !")
                    } else {
                        val session = Session.getInstance(this@LoginActivity)
                        session.firstName = response.body()?.user?.firstName
                        session.lastName = response.body()?.user?.lastName
                        session.email = response.body()?.user?.email
                        session.address = response.body()?.user?.address
                        session.cin = response.body()?.user?.cin
                        session.token = response.body()?.token
                        session.address = response.body()?.user?.address
                        val intent = Intent(this@LoginActivity, MainActivity2::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    showToast("Une erreur est survenue !")
                }
            }
        )
    }
    private fun validateForm(): Boolean {
        if (et_email.text.toString().trim().equals("")) {
            ll_emailRequired.visibility = View.VISIBLE
            return false
        } else {
            ll_emailRequired.visibility = View.INVISIBLE
        }

        if (et_password.text.trim().toString() == "") {
            passwordRequired.visibility = View.VISIBLE
            return false
        } else {
            passwordRequired.visibility = View.INVISIBLE
        }
        return true

    }
}