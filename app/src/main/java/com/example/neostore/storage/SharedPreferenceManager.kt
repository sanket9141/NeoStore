package com.example.neostore.storage

import android.content.Context
import com.example.neostore.myaccount.Data

class SharedPreferenceManager private constructor(private val mcontext : Context) {

    val loggedIn:Boolean
         get() {
            val sharedPreferences = mcontext.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
             return sharedPreferences.getInt("id",-1) != -1
        }

    val data: Data
        get()
        {
            val sharedPreferences = mcontext.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
            return Data(
                sharedPreferences.getString("access_token",null)!!,
                sharedPreferences.getInt("country_id",-1),
                sharedPreferences.getString("created","")!!,
                sharedPreferences.getString("dob","")!!,
                sharedPreferences.getString("email",null)!!,
                sharedPreferences.getString("first_name",null)!!,
                sharedPreferences.getString("gender",null)!!,
                sharedPreferences.getInt("id" , -1),
                sharedPreferences.getBoolean("is_active", null == true),
                sharedPreferences.getString("last_name",null)!!,
                sharedPreferences.getString("modified","")!!,
                sharedPreferences.getLong("phone_no", -1),
                sharedPreferences.getString("profile_pic","")!!,
                sharedPreferences.getInt("role_id",-1),
                sharedPreferences.getString("username","")!!
            )
        }

    fun saveUser(data: Data){
        val sharedPreferences = mcontext.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("access_token",data.access_token)
        editor.putString("created",data.created)
        editor.putString("dob",data.dob)
        editor.putString("email",data.email)
        editor.putString("first_name",data.first_name)
        editor.putString("gender",data.gender)
        editor.putInt("id",data.id)
        editor.putString("last_name",data.last_name)
        editor.putString("modified",data.modified)
        editor.putLong("phone_no",data.phone_no)
        editor.putString("profile_pic",data.profile_pic)
        editor.putString("username",data.username)

        editor.apply()
    }

    fun clear() {
        val sharedPreferences = mcontext.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object{
        private  val SHARED_PREFERENCE_NAME = "my_private_sharedpref"
        private var mInstance:SharedPreferenceManager?= null

        @Synchronized
        fun getInstance(mcontext: Context):SharedPreferenceManager{
            if(mInstance == null){
                mInstance = SharedPreferenceManager(mcontext)
            }
            return mInstance as SharedPreferenceManager
        }
    }
}
