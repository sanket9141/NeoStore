package com.example.neostore.myaccount

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.neostore.R
import com.example.neostore.editprofile.EditProfileFragment
import com.example.neostore.fragment.ResetPasswordFragment
import com.example.neostore.myaccount.MyAccount
import com.example.neostore.storage.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_my_account.*
import kotlinx.android.synthetic.main.fragment_my_account.view.*


class MyAccountFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myAccount = activity as MyAccount
        val view =  inflater.inflate(R.layout.fragment_my_account, container, false)

        val AccFirstName = SharedPreferenceManager.getInstance(myAccount).data.first_name
        val AccLastName = SharedPreferenceManager.getInstance(myAccount).data.last_name
        val AccEmail = SharedPreferenceManager.getInstance(myAccount).data.email
        val AccPhoneNumber = SharedPreferenceManager.getInstance(myAccount).data.phone_no
        val AccDob = SharedPreferenceManager.getInstance(myAccount).data.dob
        val AccProfilePic = SharedPreferenceManager.getInstance(myAccount).data.profile_pic

        view.txtAccFirstName.text = AccFirstName
        view.txtAccLastName.text = AccLastName
        view.txtAccEmail.text = AccEmail
        view.txtAccPhonenumber.text = AccPhoneNumber.toString()
        view.txtAccDOB.text = AccDob
        Log.d(TAG, "onCreateView: pic $AccProfilePic")
        Glide.with(activity as MyAccount).load(AccProfilePic).placeholder(R.drawable.profileavtar).into(view.profileImage)


        view.btnEditProfile.setOnClickListener(){
            val editProfileFragemnt :Fragment = EditProfileFragment()
            myAccount.supportFragmentManager.beginTransaction().replace(R.id.frameLayout,editProfileFragemnt,"myAccoutnfrgament")
                .addToBackStack("Edit_Profile")
                .commit()
        }

        view.myAccountToolbarBack.setOnClickListener(){
            (activity as MyAccount).onBackPressed()
        }

        view.btnResetPassword.setOnClickListener(){
            val resetPassword :Fragment = ResetPasswordFragment()
            //get an instance of FragmentTransaction from the FragmentManager by calling beginTransaction()
            //Use replace() to replace an existing fragment in a container with an instance of a new fragment class that you provide
            myAccount.supportFragmentManager.beginTransaction().replace(R.id.frameLayout,resetPassword,"resetPasswordFragment")
                .addToBackStack("Reset_Password")
                .commit()
        }
        return view
    }
}