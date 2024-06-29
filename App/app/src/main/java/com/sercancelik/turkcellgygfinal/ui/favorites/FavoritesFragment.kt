package com.sercancelik.turkcellgygfinal.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sercancelik.turkcellgygfinal.adapters.FavoritesAdapter
import com.sercancelik.turkcellgygfinal.databinding.FragmentFavoritesBinding
import com.sercancelik.turkcellgygfinal.models.FavoriteProduct
import com.sercancelik.turkcellgygfinal.ui.home.productdetail.ProductDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoritesAdapter(
            onItemClick = { favoriteProduct ->
                viewModel.fetchSingleProduct(favoriteProduct.productId)
            },
            onDeleteClick = { favoriteProduct ->
                showDeleteConfirmationDialog(favoriteProduct)
            }
        )

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        viewModel.favoriteProducts.observe(viewLifecycleOwner) { favoriteProducts ->
            adapter.submitList(favoriteProducts)
        }

        viewModel.productDetails.observe(viewLifecycleOwner) { product ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                putExtra("product", product)
            }
            startActivity(intent)
        }
    }

    private fun showDeleteConfirmationDialog(favoriteProduct: FavoriteProduct) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Favorilerden sil")
            .setMessage("Bu ürünü silmek istediğinizden emin misiniz?")
            .setNegativeButton("Hayır") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Evet") { dialog, _ ->
                viewModel.removeFavoriteProduct(favoriteProduct.productId)
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
