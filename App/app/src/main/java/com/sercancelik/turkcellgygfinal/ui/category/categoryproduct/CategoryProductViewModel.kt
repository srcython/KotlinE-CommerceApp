package com.sercancelik.turkcellgygfinal.ui.category.categoryproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercancelik.turkcellgygfinal.models.Product
import com.sercancelik.turkcellgygfinal.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryProductViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun fetchProductsByCategory(categorySlug: String) {
        viewModelScope.launch {
            try {
                val response = categoryRepository.getProductsByCategorySlug(categorySlug)
                if (response.isSuccessful) {
                    _products.postValue(response.body()?.products)
                } else {
                    _products.postValue(emptyList())
                }
            } catch (e: Exception) {
                _products.postValue(emptyList())
            }
        }
    }
}
