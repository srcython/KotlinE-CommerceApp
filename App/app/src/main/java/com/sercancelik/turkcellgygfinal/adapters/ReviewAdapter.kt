package com.sercancelik.turkcellgygfinal.ui.home.productdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sercancelik.turkcellgygfinal.R
import com.sercancelik.turkcellgygfinal.models.Review

class ReviewAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.reviewerName.text = review.reviewerName
        holder.comment.text = review.comment
        holder.rating.text = review.rating.toString()
        // You can set the rating star image based on the rating value if needed
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reviewerName: TextView = itemView.findViewById(R.id.textViewReviewerName)
        val comment: TextView = itemView.findViewById(R.id.textViewComment)
        val rating: TextView = itemView.findViewById(R.id.textViewRating)
    }
}
