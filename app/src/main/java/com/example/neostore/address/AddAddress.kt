package com.example.neostore.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.neostore.R
import kotlinx.android.synthetic.main.activity_add_address.*


class AddAddress : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)


        btnAddToCartToolbarBack.setOnClickListener(){
            onBackPressed()
        }

        edittxtLandMark.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val landMark = edittxtLandMark.text.toString()
                val city = edittxtCityName.text.toString()
                val state = edittxtstate.text.toString()
                val country = edittxtCountry.text.toString()
                val zipcode = edittxtZipCode.text.toString()
                edittxtAddress.text = "$landMark, $city,$zipcode, $state,$country"
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        edittxtCityName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val landMark = edittxtLandMark.text.toString()
                val city = edittxtCityName.text.toString()
                val state = edittxtstate.text.toString()
                val country = edittxtCountry.text.toString()
                val zipcode = edittxtZipCode.text.toString()
                edittxtAddress.text = "$landMark, $city,$zipcode, $state,$country"
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        edittxtstate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val landMark = edittxtLandMark.text.toString()
                val city = edittxtCityName.text.toString()
                val state = edittxtstate.text.toString()
                val country = edittxtCountry.text.toString()
                val zipcode = edittxtZipCode.text.toString()
                edittxtAddress.text = "$landMark, $city,$zipcode, $state,$country"
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        edittxtCountry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val landMark = edittxtLandMark.text.toString()
                val city = edittxtCityName.text.toString()
                val state = edittxtstate.text.toString()
                val country = edittxtCountry.text.toString()
                val zipcode = edittxtZipCode.text.toString()
                edittxtAddress.text = "$landMark, $city,$zipcode, $state,$country"
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        edittxtZipCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val landMark = edittxtLandMark.text.toString()
                val city = edittxtCityName.text.toString()
                val state = edittxtstate.text.toString()
                val country = edittxtCountry.text.toString()
                val zipcode = edittxtZipCode.text.toString()
                edittxtAddress.text = "$landMark, $city,$zipcode, $state,$country"
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        btnSaveAddress.setOnClickListener(){

            if(!landMarkEmpty() or !cityNameEmpty() or !stateNameEmpty() or !countryNameEmpty() or !zipcodeEmpty()){
                return@setOnClickListener
            }else{
                val finalAdd = edittxtAddress.text.toString().trim()
                val intent = Intent()
                intent.putExtra("finalAddress",finalAdd)
                setResult(101 ,intent)
                finish()
            }
        }

    }
    private fun zipcodeEmpty(): Boolean {
        if(edittxtZipCode.text.isEmpty()){
            edittxtZipCode.setBackgroundResource(R.drawable.image_border_red)
            return false
        }
        else if(edittxtZipCode.text.length == 6){
            edittxtZipCode.setBackgroundResource(R.drawable.border_white)
            return true
        }
        else
            Toast.makeText(this,"Please provide valid zipcode",Toast.LENGTH_LONG).show()
            return false
    }
    private fun countryNameEmpty(): Boolean {
        if(edittxtCountry.text.isEmpty()){
            edittxtCountry.setBackgroundResource(R.drawable.image_border_red)
            return false
        }else
            edittxtCountry.setBackgroundResource(R.drawable.border_white)
            return true
    }

    private fun stateNameEmpty(): Boolean {
        if(edittxtstate.text.isEmpty()){
            edittxtstate.setBackgroundResource(R.drawable.image_border_red)
            return false
        }else
            edittxtstate.setBackgroundResource(R.drawable.border_white)
            return true
    }

    private fun cityNameEmpty(): Boolean {
        if(edittxtCityName.text.isEmpty()){
            edittxtCityName.setBackgroundResource(R.drawable.image_border_red)
            return false
        }else
            edittxtCityName.setBackgroundResource(R.drawable.border_white)
            return true
    }

    private fun landMarkEmpty(): Boolean {
        if(edittxtLandMark.text.isEmpty()){
            edittxtLandMark.setBackgroundResource(R.drawable.image_border_red)
            return false
        }else{
            edittxtLandMark.setBackgroundResource(R.drawable.border_white)
            return true
        }
    }
}