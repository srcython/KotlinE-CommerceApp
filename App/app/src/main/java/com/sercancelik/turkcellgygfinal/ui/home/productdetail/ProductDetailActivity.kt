package com.sercancelik.turkcellgygfinal.ui.home.productdetail

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.sercancelik.turkcellgygfinal.R
import com.sercancelik.turkcellgygfinal.adapters.ImagePagerAdapter
import com.sercancelik.turkcellgygfinal.databinding.ActivityProductDetailBinding
import com.sercancelik.turkcellgygfinal.models.Product
import com.sercancelik.turkcellgygfinal.models.Review
import com.sercancelik.turkcellgygfinal.util.JwtUtils
import com.sercancelik.turkcellgygfinal.util.KeystoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()
    private var quantity = 1
    private var product: Product? = null

    @Inject
    lateinit var keystoreManager: KeystoreManager

    @Inject
    lateinit var jwtUtils: JwtUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras
        product = bundle?.getParcelable("product")
        product?.let {
            binding.productName.text = it.title
            setupViewPager(it.images)
            observeFavoriteState(it.id)
            setupProductDetails(it)
            setupReviews(it.reviews)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.btnIncrement.setOnClickListener {
            quantity++
            binding.tvQuantity.text = quantity.toString()
        }

        binding.btnDecrement.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
            }
        }

        binding.btnAddToCart.setOnClickListener {
            val token = keystoreManager.getToken()
            if (token != null) {
                val userId = jwtUtils.getUserIdFromToken(token)
                product?.let {
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.addToCart(userId, it, quantity)
                }
            }
        }

        viewModel.cartAddStatus.observe(this) { status ->
            val (success, cartResponse) = status
            lifecycleScope.launch {
                delay(1000)
                if (success) {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "Sepete eklendi: ${cartResponse?.totalQuantity} adet",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "Sepete eklenemedi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupViewPager(images: List<String>) {
        val adapter = ImagePagerAdapter(images)
        binding.viewPager.adapter = adapter

        binding.tvImagePosition.text = "1/${images.size}"
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvImagePosition.text = "${position + 1}/${images.size}"
            }
        })
    }

    private fun setupProductDetails(product: Product) {
        val decimalFormat = DecimalFormat("#,##0.00")

        binding.smallRating.rating = product.rating.toFloat()

        val basePrice = product.price
        val discountPercentage = product.discountPercentage
        val discountedPrice = basePrice * (1 - discountPercentage / 100)

        binding.tvPriceHomeBase.text = "${decimalFormat.format(basePrice)}₺"
        binding.tvPriceHomeBase.paintFlags =
            binding.tvPriceHomeBase.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        binding.tvPriceHome.text = "${decimalFormat.format(discountedPrice)}₺"
        binding.tvPercantageHome.text = "${discountPercentage.toInt()}%"

        if (discountPercentage.toInt() == 0) {
            binding.shape.visibility = View.GONE
            binding.tvPriceHomeBase.paintFlags = 0
            binding.tvPriceHomeBase.text = "${decimalFormat.format(basePrice)}₺"
        }
    }

    private fun setupReviews(reviews: List<Review>) {
        val reviewAdapter = ReviewAdapter(reviews)
        binding.recyclerViewReviews.adapter = reviewAdapter
        binding.recyclerViewReviews.layoutManager = LinearLayoutManager(this)
    }

    private fun observeFavoriteState(productId: Long) {
        viewModel.getFavoriteProduct(productId).observe(this) { favoriteProduct ->
            val isFavorite = favoriteProduct != null
            binding.btnAddToFav.setImageResource(
                if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart
            )
            binding.btnAddToFav.setOnClickListener {
                if (isFavorite) {
                    viewModel.removeFavoriteProduct(productId)
                    Toast.makeText(this, "Favorilerden silindi.", Toast.LENGTH_SHORT).show()
                } else {
                    product?.let { viewModel.addFavoriteProduct(it) }
                    Toast.makeText(this, "Favorilere eklendi.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
