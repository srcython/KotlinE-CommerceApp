package com.sercancelik.turkcellgygfinal.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.sercancelik.turkcellgygfinal.adapters.CategoryAdapter
import com.sercancelik.turkcellgygfinal.databinding.FragmentCategoryBinding
import com.sercancelik.turkcellgygfinal.ui.category.categoryproduct.CategoryProductActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCategory.layoutManager = GridLayoutManager(context, 2)

        categoryAdapter = CategoryAdapter { category ->
            val intent = Intent(requireContext(), CategoryProductActivity::class.java).apply {
                putExtra("categorySlug", category.slug)
                putExtra("categoryName", category.name)
            }
            startActivity(intent)
        }
        binding.rvCategory.adapter = categoryAdapter

        categoryViewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.submitList(categories)
        })

        categoryViewModel.getCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
