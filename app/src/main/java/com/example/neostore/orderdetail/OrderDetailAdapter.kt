package com.example.neostore.orderdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore.R
import com.example.neostore.swipe.ConvertorClass
import kotlinx.android.synthetic.main.order_detail_list.view.*

class OrderDetailAdapter(val orderDetailList: MutableList<OrderDetail>) :RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    lateinit var context:Context
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val orderimage : ImageView = itemView.orderItemImage
        val orderName : TextView = itemView.textOrderName
        val ordercategory : TextView = itemView.textOrderCategories
        val orderQty : TextView = itemView.textOrderQty
        val orderPrice : TextView = itemView.orderPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_detail_list,parent,false)
        context = parent.context
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItemPos = orderDetailList[position]
        holder.orderName.text = currentItemPos.prod_name
        holder.ordercategory.text = "("+ currentItemPos.prod_cat_name+")"
        holder.orderQty.text = "QTY: "+currentItemPos.quantity.toString()
        val cost = ConvertorClass().convertorfunction(currentItemPos.total)
        holder.orderPrice.text = cost
        val url = currentItemPos.prod_image
        Glide.with(context).load(url).into(holder.orderimage)
    }

    override fun getItemCount(): Int {
        return orderDetailList.size
    }


}