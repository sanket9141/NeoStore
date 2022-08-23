package com.example.neostore.addresslist

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neostore.R
import com.example.neostore.address.AddAddress
import com.example.neostore.api.RetrofitClient
import com.example.neostore.orderdetail.PlaceOrderResponse
import com.example.neostore.storage.SharedPreferenceManager
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address_list_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressListScreen : AppCompatActivity() {
    var addressList : MutableList<String> = mutableListOf()

    lateinit var addressString : String

    lateinit var addressAdapter : AddressListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list_screen)
        loadData()


        imgAddAddress.setOnClickListener(){
            val intent = Intent(this, AddAddress::class.java)
            startActivityForResult(intent,101)
        }

        btnAddToCartToolbarBack.setOnClickListener(){
            onBackPressed()
        }

        addressAdapter = AddressListAdapter(addressList)
        addressListRecyclerView.adapter = addressAdapter
        addressListRecyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)


        addressAdapter.setOnItemClick(object : AddressListAdapter.cartInterface {
            override fun onClick(position: Int, item: String) {
                addressString = item
            }

            override fun onClose(addressListAdapter: MutableList<String>) {
                addressList = addressListAdapter
                addressAdapter.notifyDataSetChanged()
                saveData()
                loadData()
            }
        })

        btnPlaceOrder.setOnClickListener(){
            placeOrder()
        }
    }

    private fun placeOrder() {

        val accessToken = SharedPreferenceManager.getInstance(this).data.access_token
        RetrofitClient.getClient.placeOrder(accessToken,addressString).enqueue(object : Callback<PlaceOrderResponse?> {
            override fun onResponse(
                call: Call<PlaceOrderResponse?>,
                response: Response<PlaceOrderResponse?>
            ) {
                if(response.code() == 200){
                    Toast.makeText(this@AddressListScreen,"${response.body()?.user_msg}",Toast.LENGTH_LONG).show()
                }else
                    Toast.makeText(this@AddressListScreen,"${response.errorBody()}",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<PlaceOrderResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101){
            val data = data?.getStringExtra("finalAddress")
            if (data != null) {
                addressList.add(data)
                saveData()
                loadData()

                addressAdapter = AddressListAdapter(addressList)
                addressListRecyclerView.adapter = addressAdapter
                addressListRecyclerView.layoutManager = LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL,false)

                addressAdapter.setOnItemClick(object : AddressListAdapter.cartInterface {
                    override fun onClick(position: Int, item: String) {
                        addressString = item

                    }

                    override fun onClose(addressListAdapter: MutableList<String>) {
                        addressList = addressListAdapter
                        addressAdapter.notifyDataSetChanged()
                        saveData()
                        loadData()
                    }

                })
            }

        }
    }

    private fun saveData() {
        val sharePref = getSharedPreferences("my_private_sharedpref" , MODE_PRIVATE)
        val edit = sharePref.edit()
        val gson = Gson()
        val json : String = gson.toJson(addressList)
        edit.putString("CityNameInSharedPref",json)
        return (edit.apply())
    }


    private fun loadData(){
        val sharePref = getSharedPreferences("my_private_sharedpref" , MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharePref.getString("CityNameInSharedPref",addressList.toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type
        addressList = gson.fromJson<ArrayList<String>>(json,type)


    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}