package com.sercancelik.turkcellgygfinal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercancelik.turkcellgygfinal.models.Product
import com.sercancelik.turkcellgygfinal.network.ProductApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private var skip = 0
    private var isLastPage = false
    private var isFetching = false

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        if (isLastPage || isFetching) return

        isFetching = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val productResponse = productApi.getProducts(page = skip, limit = 10)
                if (productResponse.products.isEmpty() || skip >= 30) {
                    isLastPage = true
                } else {
                    val currentList = _products.value.orEmpty().toMutableList()
                    currentList.addAll(productResponse.products)
                    _products.postValue(currentList)
                    skip += 10
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isFetching = false
            }
        }
    }
}
