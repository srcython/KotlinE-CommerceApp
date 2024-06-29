package com.sercancelik.turkcellgygfinal.adapters.orderadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sercancelik.turkcellgygfinal.databinding.RvItemOrderCartBinding
import com.sercancelik.turkcellgygfinal.models.Cart

class OrderAdapter(
    private val onItemClick: (View, Int) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var carts: List<Cart> = emptyList()

    fun submitList(cartList: List<Cart>) {
        carts = cartList
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Cart {
        return carts[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = RvItemOrderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(carts[position], onItemClick)
    }

    override fun getItemCount(): Int = carts.size

    class OrderViewHolder(private val binding: RvItemOrderCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart, onItemClick: (View, Int) -> Unit) {
            binding.totalPrice.text = "${cart.total}₺"
            binding.productCount.text = "${cart.totalProducts} ürün"
            binding.root.setOnClickListener { onItemClick(binding.root, adapterPosition) }
        }
    }
}
