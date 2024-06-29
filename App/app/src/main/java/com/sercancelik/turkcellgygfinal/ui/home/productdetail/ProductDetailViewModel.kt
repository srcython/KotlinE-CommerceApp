package com.sercancelik.turkcellgygfinal.ui.home.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercancelik.turkcellgygfinal.data.ProductDao
import com.sercancelik.turkcellgygfinal.models.FavoriteProduct
import com.sercancelik.turkcellgygfinal.models.Product
import com.sercancelik.turkcellgygfinal.models.Cart
import com.sercancelik.turkcellgygfinal.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Date
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDao: ProductDao,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartAddStatus = MutableLiveData<Pair<Boolean, Cart?>>()
    val cartAddStatus: LiveData<Pair<Boolean, Cart?>> get() = _cartAddStatus

    fun getFavoriteProduct(productId: Long): LiveData<FavoriteProduct?> {
        return productDao.getFavoriteProduct(productId)
    }

    fun addFavoriteProduct(product: Product) {
        val favoriteProduct = FavoriteProduct(
            productId = product.id,
            title = product.title,
            thumbnail = product.thumbnail,
            createdAt = System.currentTimeMillis()
        )
        viewModelScope.launch {
            productDao.addFavoriteProduct(favoriteProduct)
        }
    }

    fun removeFavoriteProduct(productId: Long) {
        viewModelScope.launch {
            productDao.removeFavoriteProductById(productId)
        }
    }

    fun addToCart(userId: Long, product: Product, quantity: Int) {
        viewModelScope.launch {
            try {
                val response =
                    cartRepository.addToCart(userId, listOf(product.copy(quantity = quantity)))
                if (response.isSuccessful) {
                    _cartAddStatus.postValue(Pair(true, response.body()))
                } else {
                    _cartAddStatus.postValue(Pair(false, null))
                }
            } catch (e: Exception) {
                _cartAddStatus.postValue(Pair(false, null))
            }
        }
    }
}
