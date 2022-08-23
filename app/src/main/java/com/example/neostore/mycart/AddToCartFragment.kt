package com.example.neostore.mycart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.neostore.R
import com.example.neostore.productdetail.ProductDetailScreen
import com.example.neostore.api.RetrofitClient
import com.example.neostore.storage.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_add_to_cart.*
import kotlinx.android.synthetic.main.fragment_add_to_cart.view.*
import kotlinx.android.synthetic.main.fragment_rating_pop_up_screen.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddToCartFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_to_cart, container, false)

        val imageUrl = arguments?.getString("ImageURl")
        val productName = arguments?.getString("productName")
        Glide.with(this).load(imageUrl).into(view.imgQtyItemImage)
        view.txtQtyItemName.text = productName
        view.btnQtySubmitButton.setOnClickListener(){
            if(!validateEnterQuantity())
                return@setOnClickListener
            else
                addToCart()
        }
        view.txtQtyEnterQuantity.setOnClickListener(){
            txtQtyEnterQuantity.setBackgroundResource(R.drawable.quantity_border_green)
        }
        return view
    }

    private fun addToCart() {
        val productId =  arguments?.getString("productId")?.toInt()
        val quantity = txtQtyEnterQuantity.text.toString().toInt()
        val activity = activity as ProductDetailScreen
        val accessToken = SharedPreferenceManager.getInstance(activity).data.access_token

        RetrofitClient.getClient.addToCart(accessToken,productId,quantity).enqueue(object : Callback<AddToCartResponse?> {
            override fun onResponse(
                call: Call<AddToCartResponse?>,
                response: Response<AddToCartResponse?>
            ) {
                if(response.body()?.status == 200){
                    val totalCarts = response.body()!!.total_carts
                    val sharedPreferences = activity.getSharedPreferences("my_private_sharedpref",Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putInt("total_carts",totalCarts)
                    editor.apply()
                    Toast.makeText(activity, response.body()!!.user_msg,Toast.LENGTH_LONG).show()
                    dismiss()
                }else
                    Toast.makeText(activity,"${response.errorBody()}",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<AddToCartResponse?>, t: Throwable) {
                Toast.makeText(activity,"${t.message}",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validateEnterQuantity(): Boolean {
        val enterQuantity = txtQtyEnterQuantity.text.toString()
        if(enterQuantity.isEmpty()){
            txtQtyEnterQuantity.setBackgroundResource(R.drawable.image_border_red)
            return false
        }
        else{
            txtQtyEnterQuantity.setBackgroundResource(R.drawable.quantity_border_green)
            return true
        }

    }
}