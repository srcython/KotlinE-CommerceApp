package com.sercancelik.turkcellgygfinal.adapters.orderadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sercancelik.turkcellgygfinal.databinding.RvItemOrderCartExpandedBinding
import com.sercancelik.turkcellgygfinal.extensions.loadImageWithGlide
import com.sercancelik.turkcellgygfinal.models.Product

class OrderProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<OrderProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RvItemOrderCartExpandedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(private val binding: RvItemOrderCartExpandedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvHomeTitle.text = product.title
            binding.tvPriceHomeBase.text = "${product.price}₺"
            binding.imageViewHome.loadImageWithGlide(product.thumbnail)
            binding.tvPiece.text = product.quantity.toString()
            binding.tvTotalProductPrice.text =
                "Toplam: " + (product.price * product.quantity).toString() + "₺"
        }
    }
}
