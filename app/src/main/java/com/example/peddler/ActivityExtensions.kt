package com.example.peddler

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Activity.hideKeyboard() = WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.ime())

fun Activity.showToast (message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()