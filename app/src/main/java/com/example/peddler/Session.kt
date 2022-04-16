package com.example.peddler

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class Session private constructor(cntx: Context) {
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(cntx)

    fun clearSession() {
        prefs.edit().clear().commit()
    }

    var firstName: String?
        get() = prefs.getString("firstName", "")
        set(firstName) {
            prefs.edit().putString("firstName", firstName).commit()
        }
    var lastName: String?
        get() = prefs.getString("lastName", "")
        set(lastName) {
            prefs.edit().putString("lastName", lastName).commit()
        }
    var email: String?
        get() = prefs.getString("email", "")
        set(email) {
            prefs.edit().putString("email", email).commit()
        }
    var address: String?
        get() = prefs.getString("address", "")
        set(address) {
            prefs.edit().putString("address", address).commit()
        }
    var cin: String?
        get() = prefs.getString("cin", "")
        set(cin) {
            prefs.edit().putString("cin", cin).commit()
        }

    var token: String?
        get() = prefs.getString("token", "null")
        set(token) {
            prefs.edit().putString("token", token).commit()
        }

    companion object {
        private lateinit var instance: Session

        fun getInstance(context: Context): Session {
            if (!::instance.isInitialized) {
                instance = Session(context)
            }
            return instance
        }
    }

}