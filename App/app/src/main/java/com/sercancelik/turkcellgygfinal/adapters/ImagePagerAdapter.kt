package com.sercancelik.turkcellgygfinal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sercancelik.turkcellgygfinal.databinding.RvItemImagePagerBinding
import com.sercancelik.turkcellgygfinal.extensions.loadImageWithGlide

class ImagePagerAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = RvItemImagePagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    class ImageViewHolder(private val binding: RvItemImagePagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            binding.imageView.loadImageWithGlide(imageUrl)
        }
    }
}
