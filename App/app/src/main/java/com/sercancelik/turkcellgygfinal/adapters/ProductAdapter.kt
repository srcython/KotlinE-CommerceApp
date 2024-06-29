package com.sercancelik.turkcellgygfinal.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sercancelik.turkcellgygfinal.databinding.RvItemDashboardBinding
import com.sercancelik.turkcellgygfinal.extensions.loadImageWithGlide
import com.sercancelik.turkcellgygfinal.models.Product
import java.text.DecimalFormat

class ProductAdapter(
    private val onItemClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            RvItemDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class ProductViewHolder(private val binding: RvItemDashboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            val decimalFormat = DecimalFormat("#,##0.00")

            binding.tvHomeTitle.text = product.title
            binding.smallRating.rating = product.rating.toFloat()

            val basePriceValue = product.price
            val discountValue = product.discountPercentage
            val discountedPrice = basePriceValue * (1 - discountValue / 100)

            binding.tvPriceHomeBase.text = "${decimalFormat.format(basePriceValue)}₺"
            if (discountValue > 0) {
                binding.tvPriceHomeBase.paintFlags =
                    binding.tvPriceHomeBase.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvPriceHome.text = "${decimalFormat.format(discountedPrice)}₺"
                binding.tvPercantageHome.text = "${discountValue.toDouble()}%"
            } else {
                binding.shape.visibility = View.GONE
                binding.tvPriceHomeBase.paintFlags = 0
                binding.tvPriceHome.text = ""
                binding.tvPercantageHome.text = ""
            }

            binding.imageViewHome.loadImageWithGlide(product.thumbnail)

            binding.itemContainer.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
