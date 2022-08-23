package com.example.neostore.mycart

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore.R
import com.example.neostore.swipe.ConvertorClass
import kotlinx.android.synthetic.main.add_to_cart_item_list.view.*

class CartListAdapter(val cartList:MutableList<CartData>) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    lateinit var context:Context

    lateinit var mlisterner : cartInterface

    interface cartInterface
    {
        fun onClick(position: Int, adapterPosition: Int)
    }
    fun setOnItemClick(listerner: cartInterface){
        mlisterner = listerner
    }

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

        val itemImage:ImageView = itemView.cartItemImageView
        val itemName:TextView = itemView.textAddToCartName
        val itemCategory:TextView = itemView.textAddToCartCategory
        val price : TextView = itemView.txtPrice
        val quantity  = itemView.dropdown

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.add_to_cart_item_list,parent,false)
        context = parent.context
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPos = cartList[position]
        holder.itemName.text = itemPos.product.name
        holder.itemCategory.text = itemPos.product.product_category


        val url = itemPos.product.product_images
        Glide.with(context).load(url).into(holder.itemImage)

        val listOfNumber = context.resources.getStringArray(R.array.quantity)
        val arrayAdapter = ArrayAdapter(context,R.layout.drop_down_item,listOfNumber)

        holder.quantity.setOnItemClickListener (object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                //this is the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemClick(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val pos = p2+1
                val adapterPosition = holder.adapterPosition
                Log.d(TAG, "onItemClick: $pos and $adapterPosition")
                mlisterner.onClick(pos,adapterPosition)
            }
        })

        holder.quantity.setAdapter(arrayAdapter)

        holder.quantity.setText("${itemPos.quantity}",false)


        val cost = ConvertorClass().convertorfunction(itemPos.product.cost)
        holder.price.text = cost

    }
    override fun getItemCount(): Int {
        return cartList.size
    }

    fun deletItem(i:Int){
        cartList.removeAt(i)
        notifyDataSetChanged()
    }
}