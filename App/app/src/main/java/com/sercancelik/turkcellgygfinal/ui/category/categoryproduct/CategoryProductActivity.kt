package com.sercancelik.turkcellgygfinal.ui.category.categoryproduct

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.sercancelik.turkcellgygfinal.R
import com.sercancelik.turkcellgygfinal.adapters.ProductAdapter
import com.sercancelik.turkcellgygfinal.databinding.ActivityCategoryProductBinding
import com.sercancelik.turkcellgygfinal.models.Product
import com.sercancelik.turkcellgygfinal.ui.home.productdetail.ProductDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryProductActivity : AppCompatActivity() {

    private val categoryProductViewModel: CategoryProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var binding: ActivityCategoryProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        val categoryName = intent.getStringExtra("categoryName")
        binding.categoryTitle.text = categoryName

        binding.rvCategoryProducts.layoutManager = GridLayoutManager(this, 2)

        productAdapter = ProductAdapter { product ->
            navigateToProductDetail(product)
        }

        binding.rvCategoryProducts.adapter = productAdapter

        val categorySlug = intent.getStringExtra("categorySlug")
        if (categorySlug != null) {
            categoryProductViewModel.fetchProductsByCategory(categorySlug)
        }

        categoryProductViewModel.products.observe(this, Observer { products ->
            productAdapter.submitList(products)
        })
    }

    private fun navigateToProductDetail(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java).apply {
            putExtra("product", product)
        }
        startActivity(intent)
    }
}
