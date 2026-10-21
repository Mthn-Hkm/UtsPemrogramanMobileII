package com.example.utspemrogramanmobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.utspemrogramanmobile.R
import com.example.utspemrogramanmobile.model.Review
import java.util.Locale

class ReviewAdapter(
    private val reviews: List<Review>
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_reviewer_name)
        val tvRating: TextView = itemView.findViewById(R.id.tv_reviewer_rating)
        val tvComment: TextView = itemView.findViewById(R.id.tv_reviewer_comment)
        val tvDate: TextView = itemView.findViewById(R.id.tv_reviewer_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        holder.tvName.text = review.reviewerName
        holder.tvRating.text = "⭐".repeat(review.rating)
        holder.tvComment.text = "\"${review.comment}\""
        holder.tvDate.text = formatDate(review.date)
    }

    override fun getItemCount(): Int = reviews.size

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val outputFormat = java.text.SimpleDateFormat("dd MMM yyyy", Locale.US)
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString
        }
    }
}
