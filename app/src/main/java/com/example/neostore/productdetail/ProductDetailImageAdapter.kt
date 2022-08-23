package com.example.neostore.productdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore.R
import kotlinx.android.synthetic.main.product_detail_images_item_list.view.*

class ProductDetailImageAdapter(val imageList : MutableList<ProductImage>) :
    RecyclerView.Adapter<ProductDetailImageAdapter.ViewHolder>() {
    lateinit var context:Context
    var row_index = -1
    lateinit var mlistener: onImageClickListener
    interface onImageClickListener{
        fun onClickListener(position: String)
    }

    fun setOnClickListerner(listener: onImageClickListener){
        mlistener = listener
    }
    inner class ViewHolder(itemView : View , listener: onImageClickListener):
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onClickListener(imageList[adapterPosition].image)
                row_index = adapterPosition
                notifyDataSetChanged()

            }
        }
            val productImageList:ImageView = itemView.PrdouctItemImageList
    }

        override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
            var itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_detail_images_item_list,parent,false)
            context = parent.context
            return ViewHolder(itemView,mlistener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentImage = imageList[position]
        val url = currentImage.image

        Glide.with(context).load(url).into(holder.productImageList)

        if(row_index == position){
            holder.productImageList.setBackgroundResource(R.drawable.image_border_red)
        }else{
            holder.productImageList.setBackgroundResource(R.drawable.image_border_grey)
        }
    }
    override fun getItemCount(): Int {
        return imageList.size
    }
}