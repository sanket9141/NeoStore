package com.example.neostore.mycart

import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.R
import com.example.neostore.addresslist.AddressListScreen
import com.example.neostore.swipe.ConvertorClass
import com.example.neostore.api.RetrofitClient
import com.example.neostore.storage.SharedPreferenceManager
import com.example.neostore.swipe.SwipeGesture
import kotlinx.android.synthetic.main.activity_my_cart_screen.*
import kotlinx.android.synthetic.main.activity_product_detail_screen.*
import kotlinx.android.synthetic.main.activity_product_listing.*
import kotlinx.android.synthetic.main.add_to_cart_item_list.*
import kotlinx.android.synthetic.main.add_to_cart_item_list.view.*
import kotlinx.android.synthetic.main.product_detail_images_item_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyCartScreen : AppCompatActivity()  {
    lateinit var list : MutableList<CartData>
    lateinit var temporaryList: MutableList<CartData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart_screen)
        setSupportActionBar(addToCartToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        list = mutableListOf()
        temporaryList = mutableListOf()
        updateCartData()

        btnOrderNow.setOnClickListener(){
            val intent = Intent(this, AddressListScreen::class.java)
            startActivity(intent)
        }

        btnAddToCartToolbarBack.setOnClickListener(){
            onBackPressed()
        }

    }

    private fun updateCartData() {
        val accessToken = SharedPreferenceManager.getInstance(this).data.access_token
        RetrofitClient.getClient.cartList(accessToken).enqueue(object : Callback<CartListResponse?> {
            override fun onResponse(
                call: Call<CartListResponse?>,
                response: Response<CartListResponse?>
            ) {
                if(response.body()?.data!=null){

                   val cost = ConvertorClass().convertorfunction(response.body()!!.total)
                    price.text = cost
                    list = mutableListOf<CartData>()
                    for (item in response.body()?.data!!)
                    {
                        list.add(item)
                    }
                    temporaryList.addAll(list)

                    val cartAdapter = CartListAdapter(list)
                    myCartRecyclerview.adapter = cartAdapter
                    myCartRecyclerview.layoutManager = LinearLayoutManager(this@MyCartScreen,
                        LinearLayoutManager.VERTICAL,false)


                    val swipeGesture = object : SwipeGesture(this@MyCartScreen){
                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                            when(direction){
                                ItemTouchHelper.LEFT->{
                                    val productItemId = response.body()!!.data[viewHolder.adapterPosition].product_id
                                    deleteItemCall(accessToken,productItemId)
                                    cartAdapter.deletItem(viewHolder.adapterPosition)
                                }
                            }

                        }
                    }

                    val touchHelper = ItemTouchHelper(swipeGesture)
                    touchHelper.attachToRecyclerView(myCartRecyclerview)
                    
                    cartAdapter.setOnItemClick(object : CartListAdapter.cartInterface {
                        override fun onClick(position: Int, adapterPosition: Int) {
                            val productId = response.body()!!.data[adapterPosition].product_id
                            val qty = position
                            editItemData(accessToken,productId,qty)
                        }
                    })

                }else{
                    txtTotal.visibility = View.GONE
                    btnOrderNow.visibility = View.GONE
                    price.visibility = View.GONE
                    Toast.makeText(this@MyCartScreen,"Cart empty",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CartListResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun editItemData(accessToken: String, productId: Int, qty: Int) {
        RetrofitClient.getClient.editCartItem(accessToken,productId,qty).enqueue(object : Callback<CartEditResponse?> {
            override fun onResponse(
                call: Call<CartEditResponse?>,
                response: Response<CartEditResponse?>
            ) {
                if(response.body()!=null){
                    Toast.makeText(this@MyCartScreen,"${response.body()!!.user_msg}",Toast.LENGTH_LONG).show()
                    updateCartData()
                }else
                    Toast.makeText(this@MyCartScreen,"${response.errorBody()}",Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<CartEditResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })

    }

    private fun deleteItemCall(accessToken: String, productItemId: Int) {
        RetrofitClient.getClient.deleteCartItem(accessToken,productItemId).enqueue(object : Callback<CartDeleteResponse?> {
            override fun onResponse(
                call: Call<CartDeleteResponse?>,
                response: Response<CartDeleteResponse?>
            ) {
                if(response.body()?.status == 200){
                    Toast.makeText(this@MyCartScreen,"${response.body()!!.user_msg}",Toast.LENGTH_LONG).show()
                    val sharedPreferences = this@MyCartScreen.getSharedPreferences("my_private_sharedpref",
                        Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    val totalCartsVal = response.body()!!.total_carts
                    editor.putInt("total_carts",totalCartsVal)
                    editor.apply()
                    updateCartData()
                }else
                    Log.d(TAG, "onResponse: ${response.errorBody()}")
            }

            override fun onFailure(call: Call<CartDeleteResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu , menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchitem = menu?.findItem(R.id.action_search)
        val searchView = searchitem?.actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.queryHint = Html.fromHtml("<font color = #ffffff>" + "Search ..")


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MyCartScreen,"No Data Found",Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                temporaryList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    list.forEach {
                        if(it.product.name.toLowerCase(Locale.getDefault()).contains(searchText)){
                            temporaryList.add(it)
                        }
                    }
                    myCartRecyclerview.adapter!!.notifyDataSetChanged()
                }else{
                    temporaryList.clear()
                    temporaryList.addAll(list)
                    myCartRecyclerview.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}