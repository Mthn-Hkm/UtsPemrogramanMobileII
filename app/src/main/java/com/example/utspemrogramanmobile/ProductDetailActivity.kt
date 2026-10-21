package com.example.utspemrogramanmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.utspemrogramanmobile.adapter.ReviewAdapter
import com.example.utspemrogramanmobile.model.Product
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Setup toolbar back button
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        // Get product data from Intent
        @Suppress("DEPRECATION")
        val product = intent.getSerializableExtra("PRODUCT_DATA") as? Product

        if (product == null) {
            finish()
            return
        }

        bindProductData(product)
    }

    private fun bindProductData(product: Product) {
        // Product Image
        val ivImage = findViewById<ImageView>(R.id.iv_product_image)
        val imageUrl = if (product.images.isNotEmpty()) product.images[0] else product.thumbnail
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .centerCrop()
            .into(ivImage)

        // Title & Category
        findViewById<TextView>(R.id.tv_product_title).text = product.title
        findViewById<TextView>(R.id.tv_product_category).text = product.category.replaceFirstChar { it.uppercase() }

        // Price & Rating
        findViewById<TextView>(R.id.tv_product_price).text =
            String.format(Locale.US, "$%.2f", product.price)
        findViewById<TextView>(R.id.tv_product_rating).text =
            String.format(Locale.US, "⭐ %.1f / 5.0", product.rating)

        // Stock & Availability
        findViewById<TextView>(R.id.tv_product_stock).text = "Stok: ${product.stock} unit"
        val tvAvailability = findViewById<TextView>(R.id.tv_product_availability)
        tvAvailability.text = product.availabilityStatus
        tvAvailability.setTextColor(
            if (product.availabilityStatus.contains("In Stock", ignoreCase = true))
                getColor(R.color.success)
            else
                getColor(R.color.error)
        )

        // Description
        findViewById<TextView>(R.id.tv_product_description).text = product.description

        // Tags
        val tagsText = product.tags.joinToString(", ") { "#$it" }
        findViewById<TextView>(R.id.tv_product_tags).text = tagsText

        // Dimensions
        val dim = product.dimensions
        findViewById<TextView>(R.id.tv_product_dimensions).text =
            String.format(Locale.US, "W: %.1f × H: %.1f × D: %.1f cm", dim.width, dim.height, dim.depth)

        // Reviews - show first review preview
        if (product.reviews.isNotEmpty()) {
            val firstReview = product.reviews[0]
            findViewById<TextView>(R.id.tv_review_name).text = firstReview.reviewerName
            findViewById<TextView>(R.id.tv_review_rating).text = "⭐".repeat(firstReview.rating)
            findViewById<TextView>(R.id.tv_review_comment).text = "\"${firstReview.comment}\""
            findViewById<TextView>(R.id.tv_review_date).text = formatDate(firstReview.date)
        } else {
            findViewById<LinearLayout>(R.id.layout_review_preview).visibility = View.GONE
        }

        // See All Reviews button
        val tvSeeAll = findViewById<TextView>(R.id.tv_see_all_reviews)
        if (product.reviews.size > 1) {
            tvSeeAll.text = "Lihat Semua Review (${product.reviews.size}) →"
            tvSeeAll.setOnClickListener {
                showReviewsDialog(product)
            }
        } else {
            tvSeeAll.visibility = View.GONE
        }
    }

    private fun showReviewsDialog(product: Product) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_reviews, null)
        val rvReviews = dialogView.findViewById<RecyclerView>(R.id.rv_reviews)

        rvReviews.layoutManager = LinearLayoutManager(this)
        rvReviews.adapter = ReviewAdapter(product.reviews)

        AlertDialog.Builder(this)
            .setTitle("${getString(R.string.semua_review)} (${product.reviews.size})")
            .setView(dialogView)
            .setPositiveButton(R.string.tutup, null)
            .show()
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString
        }
    }
}
