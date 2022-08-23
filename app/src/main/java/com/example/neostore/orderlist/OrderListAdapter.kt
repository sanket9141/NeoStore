package com.example.neostore.orderlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.R
import com.example.neostore.swipe.ConvertorClass
import kotlinx.android.synthetic.main.my_orders_list_item.view.*

class OrderListAdapter(val orderList: MutableList<OrderListData>) :RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    lateinit var context:Context
    lateinit var mlisterner: OnItemClickListerner
    interface OnItemClickListerner{
        fun onClickListerner(position: Number)
    }

    fun onSetClickListerner(listener: OnItemClickListerner){
        mlisterner = listener
    }

    inner class ViewHolder(itemView: View,listener: OnItemClickListerner):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener(){
                listener.onClickListerner(orderList[adapterPosition].id)
            }
        }
        val itemId : TextView = itemView.txtOrderId
        val itemCreated : TextView = itemView.txtCreated
        val itemPrice : TextView = itemView.txtPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_orders_list_item,parent,false)
        context = parent.context
        return ViewHolder(itemView,mlisterner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = orderList[position]
        holder.itemId.text = "Order Id: " + currentItem.id.toString()
        holder.itemCreated.text = "Ordered date:"+ currentItem.created.toString()
        val cost = ConvertorClass().convertorfunction(currentItem.cost)
        holder.itemPrice.text = cost
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}