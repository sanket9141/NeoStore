package com.example.neostore.orderlist

import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neostore.R
import com.example.neostore.api.RetrofitClient
import com.example.neostore.orderdetail.OrderActivtyScreen
import com.example.neostore.storage.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_my_orders_screen.*
import kotlinx.android.synthetic.main.activity_product_listing.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyOrdersScreen : AppCompatActivity() {

    lateinit var orderList : MutableList<OrderListData>
    lateinit var temporaryList : MutableList<OrderListData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders_screen)
        setSupportActionBar(myOrdersToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        orderList = mutableListOf()
        temporaryList = mutableListOf()
        updateOrderList()

        myOrdersTollbarBack.setOnClickListener(){
            onBackPressed()
        }
    }

    private fun updateOrderList() {
        val accessToken = SharedPreferenceManager.getInstance(this).data.access_token
        RetrofitClient.getClient.orderList(accessToken).enqueue(object : Callback<OrderListResponse?> {
            override fun onResponse(
                call: Call<OrderListResponse?>,
                response: Response<OrderListResponse?>
            ) {
                if(response.body() != null){
                    orderList = mutableListOf<OrderListData>()
                    for (item in response.body()!!.data){
                        orderList.add(item)
                    }
                    temporaryList.addAll(orderList)


                    val orderAdapter = OrderListAdapter(temporaryList)
                    OrdersRecyclerView.adapter = orderAdapter
                    OrdersRecyclerView.layoutManager = LinearLayoutManager(this@MyOrdersScreen,
                        LinearLayoutManager.VERTICAL,false)


                    orderAdapter.onSetClickListerner(object : OrderListAdapter.OnItemClickListerner {


                        override fun onClickListerner(position: Number) {
                            val intent = Intent(this@MyOrdersScreen, OrderActivtyScreen::class.java)
                            intent.putExtra("ORDER_ID",position.toString())
                            startActivity(intent)
                        }
                    })

                }else{
                    Toast.makeText(this@MyOrdersScreen,"${response.errorBody()}",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<OrderListResponse?>, t: Throwable) {
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
        searchView?.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.queryHint = Html.fromHtml("<font color = #ffffff>" + "Search ..")


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MyOrdersScreen,"No Data Found",Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                temporaryList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    orderList.forEach {
                        if(it.id.toString().toLowerCase().contains(searchText)){
                            temporaryList.add(it)
                        }
                    }
                    OrdersRecyclerView.adapter!!.notifyDataSetChanged()
                }else{
                    temporaryList.clear()
                    temporaryList.addAll(orderList)
                    OrdersRecyclerView.adapter!!.notifyDataSetChanged()
                }

                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


}