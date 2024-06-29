package com.sercancelik.turkcellgygfinal.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercancelik.turkcellgygfinal.models.FavoriteProduct
import com.sercancelik.turkcellgygfinal.models.Product
import com.sercancelik.turkcellgygfinal.network.ProductApi
import com.sercancelik.turkcellgygfinal.repositories.ProductDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ProductDbRepository,
    private val productApiService: ProductApi
) : ViewModel() {

    val favoriteProducts: LiveData<List<FavoriteProduct>> = repository.getAllFavoriteProducts()
    private val _productDetails = MutableLiveData<Product>()
    val productDetails: LiveData<Product> get() = _productDetails

    fun removeFavoriteProduct(productId: Long) {
        viewModelScope.launch {
            repository.removeFavoriteProduct(productId)
        }
    }

    fun fetchSingleProduct(productId: Long) {
        viewModelScope.launch {
            val response = productApiService.getSingleProduct(productId)
            _productDetails.postValue(response)
        }
    }
}
