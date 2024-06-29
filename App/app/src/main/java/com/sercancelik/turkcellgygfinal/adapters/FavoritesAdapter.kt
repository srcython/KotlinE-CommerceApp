package com.sercancelik.turkcellgygfinal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sercancelik.turkcellgygfinal.databinding.RvItemFavoritesBinding
import com.sercancelik.turkcellgygfinal.extensions.loadImageWithGlide
import com.sercancelik.turkcellgygfinal.models.FavoriteProduct

class FavoritesAdapter(
    private val onItemClick: (FavoriteProduct) -> Unit,
    private val onDeleteClick: (FavoriteProduct) -> Unit
) : ListAdapter<FavoriteProduct, FavoritesAdapter.FavoriteViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            RvItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick, onDeleteClick)
    }

    class FavoriteViewHolder(private val binding: RvItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            favoriteProduct: FavoriteProduct,
            onItemClick: (FavoriteProduct) -> Unit,
            onDeleteClick: (FavoriteProduct) -> Unit
        ) {
            binding.tvHomeTitle.text = favoriteProduct.title
            binding.imageViewHome.loadImageWithGlide(favoriteProduct.thumbnail)

            binding.root.setOnClickListener {
                onItemClick(favoriteProduct)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(favoriteProduct)
            }
        }
    }

    class FavoriteDiffCallback : DiffUtil.ItemCallback<FavoriteProduct>() {
        override fun areItemsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(
            oldItem: FavoriteProduct,
            newItem: FavoriteProduct
        ): Boolean {
            return oldItem == newItem
        }
    }
}
