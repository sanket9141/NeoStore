package com.example.neostore.productdetail

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.neostore.R
import com.example.neostore.api.RetrofitClient
import com.example.neostore.mycart.AddToCartFragment
import kotlinx.android.synthetic.main.activity_product_detail_screen.*
import kotlinx.android.synthetic.main.product_detail_images_item_list.*
import kotlinx.android.synthetic.main.product_detail_images_item_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.NumberFormat
import android.content.Intent
import android.net.Uri
import com.example.neostore.login.MainActivity
import com.example.neostore.storage.SharedPreferenceManager


class ProductDetailScreen : AppCompatActivity() {
    lateinit var getId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail_screen)

        
        getProductDetailData()

        btnProductDetailTollbarBack.setOnClickListener(){
            onBackPressed()
        }

    }

    private fun getProductDetailData() {
        getId = intent.getStringExtra("PRODUCT_ID").toString()
        val data: Uri? = intent?.data
        Log.d(TAG, "getProductDetailData: $data")
        if(data != null){
            getId= data.getQueryParameter("id").toString()
            Log.d(TAG, "getProductDetailData: $getId")
        }
        RetrofitClient.getClient.productDetail(getId).enqueue(object : Callback<ProductDetail?> {
            override fun onResponse(
                call: Call<ProductDetail?>,
                response: Response<ProductDetail?>
            ) {
                if(response.body() != null){
                    val productResponse = response.body()
                    productDetailtoolbarTitle.setText(productResponse?.data?.name)
                    val productName = productResponse?.data?.name
                    ProductDetailName.text = productName
                    val categoryId = productResponse?.data?.product_category_id
                    if(categoryId == 1)
                        productDetailCategory.text = "Category - Tables"
                    else if (categoryId == 2)
                        productDetailCategory.text = "Category - Chairs"
                    else if(categoryId == 3)
                        productDetailCategory.text = "Category - Sofas"
                    else if (categoryId == 4)
                        productDetailCategory.text = "Category - Cupboards"
                    else
                        productDetailCategory.text = ""

                    productDetailProducer.text = productResponse?.data?.producer
                    productDetailRatingBar.rating = productResponse?.data?.rating!!.toFloat()


                    val nf = NumberFormat.getCurrencyInstance()
                    val pattern = (nf as DecimalFormat).toPattern()
                    val newPattern = pattern.replace("\u00A4", "").trim { it <= ' ' }
                    val newFormat: NumberFormat = DecimalFormat(newPattern)
                    val answer = newFormat.format(productResponse?.data?.cost).trim().dropLast(3)
                    productDetailPrice.text = "Rs. " + answer

                    productDetailDescription.text = productResponse.data.description

                    val imageUrl = productResponse.data.product_images[0].image
                    Glide.with(this@ProductDetailScreen).load(imageUrl).into(productDetailImage)

                    val imageList = mutableListOf<ProductImage>()
                    for (image in productResponse.data.product_images){
                        imageList.add(image)
                    }
                    Log.d(TAG, "onResponse: ${productResponse.data.product_images[0].image}")
                    val prodcutDetailAdapter = ProductDetailImageAdapter(imageList)
                    productDetailRecyclerview.adapter = prodcutDetailAdapter
                    productDetailRecyclerview.layoutManager = LinearLayoutManager(this@ProductDetailScreen,LinearLayoutManager.HORIZONTAL,false)


                    ProductDetailRatingPopUp.setOnClickListener(){

                        val fragmentPopUp: DialogFragment = RatingPopUpScreen()
                        val bundle = Bundle()
                        bundle.putString("productId",getId)
                        bundle.putString("productName" , productName)
                        bundle.putString("ImageURl",imageUrl)
                        fragmentPopUp.arguments = bundle
                        fragmentPopUp.show(supportFragmentManager,"RatingPOPUpScreen")
                    }

                    btnProductDetailBuyNow.setOnClickListener(){
                        if(!SharedPreferenceManager.getInstance(this@ProductDetailScreen).loggedIn){
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }else{
                            val buyNowPopUp :DialogFragment = AddToCartFragment()
                            val bundle = Bundle()
                            bundle.putString("productId",getId)
                            bundle.putString("productName" , productName)
                            bundle.putString("ImageURl",imageUrl)
                            buyNowPopUp.arguments = bundle
                            buyNowPopUp.show(supportFragmentManager,"AddToCartScreen")
                        }


                    }
                    productDetailSharing.setOnClickListener(){
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        //http://staging.php-dev.in:8844/trainingapp/api/products/getDetail?product_id=$getId
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "www.neostore.com/product_id?id=$getId")
                        startActivity(Intent.createChooser(shareIntent, "Share link using"))
                    }
                    prodcutDetailAdapter.setOnClickListerner(object : ProductDetailImageAdapter.onImageClickListener {

//                        override fun onClickListerner(position: Int) {
//
//                            val url = productResponse.data.product_images[position].image
//                            val positionValue = position
//                            Glide.with(this@ProductDetailScreen).load(url).into(productDetailImage)
//                            val view =productDetailRecyclerview.findViewHolderForAdapterPosition(position)?.itemView
//                            view?.PrdouctItemImageList?.setBackgroundResource(R.drawable.image_border_red)
//                            }

                        override fun onClickListener(position: String) {
                            Glide.with(this@ProductDetailScreen).load(position).into(productDetailImage)
                        }

                    })

                }else{
                    Toast.makeText(this@ProductDetailScreen,"${response.errorBody()}",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductDetail?>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}