package com.sercancelik.turkcellgygfinal.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercancelik.turkcellgygfinal.models.Product
import com.sercancelik.turkcellgygfinal.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun searchProducts(query: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = productRepository.searchProducts(query)
                if (response.isSuccessful) {
                    _products.value = response.body()?.products ?: emptyList()
                } else {
                    _products.value = emptyList()
                }
            } catch (e: Exception) {
                _products.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
}
