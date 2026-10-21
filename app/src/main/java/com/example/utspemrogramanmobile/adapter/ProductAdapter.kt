package com.example.utspemrogramanmobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.utspemrogramanmobile.R
import com.example.utspemrogramanmobile.model.Product
import java.util.Locale

class ProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivThumbnail: ImageView = itemView.findViewById(R.id.iv_product_thumbnail)
        val tvName: TextView = itemView.findViewById(R.id.tv_product_name)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_product_price)
        val tvRating: TextView = itemView.findViewById(R.id.tv_product_rating)
        val tvStock: TextView = itemView.findViewById(R.id.tv_product_stock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.tvName.text = product.title
        holder.tvPrice.text = String.format(Locale.US, "$%.2f", product.price)
        holder.tvRating.text = String.format(Locale.US, "⭐ %.1f", product.rating)
        holder.tvStock.text = "Stok: ${product.stock}"

        Glide.with(holder.itemView.context)
            .load(product.thumbnail)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .centerCrop()
            .into(holder.ivThumbnail)

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = products.size
}
