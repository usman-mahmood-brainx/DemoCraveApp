package com.example.demo_instaforfood

import android.content.SharedPreferences
import okhttp3.Headers

class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val ACCESS_TOKEN = "access-token"
        private const val UID = "uid"
        private const val CLIENT = "client"
        private const val LOGIN_STATUS = "loginStatus"
    }

    fun saveHeaders(headers: Headers) {
        val editor = sharedPreferences.edit()

        editor.apply {
            putString(ACCESS_TOKEN, headers[ACCESS_TOKEN])
            putString(CLIENT, headers[CLIENT])
            putString(UID, headers[UID])
            apply()
        }

    }
    fun setloginStatus(loginStatus:Boolean){
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean(LOGIN_STATUS,loginStatus)
            apply()
        }

    }

    fun getLoginStatus():Boolean{
       return sharedPreferences.getBoolean(LOGIN_STATUS, false)
    }
    fun getAccessToken():String?{
        return sharedPreferences.getString(ACCESS_TOKEN,null)
    }
    fun getClient():String?{
        return sharedPreferences.getString(CLIENT,null)
    }
    fun getUid():String?{
        return sharedPreferences.getString(UID,null)
    }

    

}
