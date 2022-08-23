package com.example.neostore.productlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore.R
import com.example.neostore.models.ProductData
import kotlinx.android.synthetic.main.product_list_item.view.*
import java.text.DecimalFormat
import java.text.NumberFormat

class ProductListAdapter(val dataList:MutableList<ProductData>):
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    lateinit var context:Context

    lateinit var mlistener : onItemClickListerner

    interface onItemClickListerner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListerner(listerner: onItemClickListerner){
        mlistener = listerner
    }


    inner class ViewHolder(itemView : View , listerner: onItemClickListerner):RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listerner.onItemClick(dataList[adapterPosition].id)
            }
        }



        val productImageView:ImageView = itemView.imgRecyclerItem
        val productName :TextView = itemView.txtItemName
        val productSellerName : TextView = itemView.txtItemSellerName
        val productPrice : TextView = itemView.txtItemPrice
        val ratingBar :RatingBar = itemView.ratingBar

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item,parent,false)
        context = parent.context
        return ViewHolder(itemView,mlistener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem: ProductData = dataList[position]
        holder.productName.text = currentItem.name
        holder.productSellerName.text = currentItem.producer


        val nf = NumberFormat.getCurrencyInstance()
        val pattern = (nf as DecimalFormat).toPattern()
        val newPattern = pattern.replace("\u00A4", "").trim { it <= ' ' }
        val newFormat: NumberFormat = DecimalFormat(newPattern)
        val answer = newFormat.format(currentItem.cost).trim().dropLast(3)
        //val answer = format.format(currentItem.cost).trim()
        holder.productPrice.text = "Rs. " + answer
        holder.ratingBar.rating = currentItem.rating.toFloat()
        val url = currentItem.product_images
        Glide.with(context).load(url).into(holder.productImageView)
        //holder.productPrice.text = position.toString() + "/" + dataList.size

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}