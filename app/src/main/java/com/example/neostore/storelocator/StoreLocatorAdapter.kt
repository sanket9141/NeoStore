/*package com.example.neostore.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.R
import com.example.neostore.storelocator.StoreLocatorClass
import kotlinx.android.synthetic.main.store_locator_namelist.view.*

class StoreLocatorAdapter(val storeNameList: MutableList<StoreLocatorClass>) :RecyclerView.Adapter<StoreLocatorAdapter.ViewHolder>() {
    lateinit var context:Context

    lateinit var mlisterner:onItemClickListerner
    interface onItemClickListerner{
        fun onClickListerner(position: Int,lat:Double,lon:Double)
    }
    fun onSetClickListerner(listerner:onItemClickListerner){
        mlisterner = listerner
    }
    inner class ViewHolder(itemView : View,listerner:onItemClickListerner):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener(){
                listerner.onClickListerner(adapterPosition,storeNameList[adapterPosition].lat,
                    storeNameList[adapterPosition].lon)
            }
        }

        val name  : TextView = itemView.txtStoreName
        val address : TextView = itemView.txtAddress
        val image:ImageView = itemView.locationIcon

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoreLocatorAdapter.ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.store_locator_namelist,parent,false)
        context = parent.context
        return ViewHolder(itemView,mlisterner)
    }

    override fun onBindViewHolder(holder: StoreLocatorAdapter.ViewHolder, position: Int) {
        val item = storeNameList[position]
        holder.name.text = item.name
        holder.address.text = item.address
        holder.image.setImageResource(R.drawable.ic_baseline_location_on_24)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${storeNameList.size}")
       return storeNameList.size
    }
}*/