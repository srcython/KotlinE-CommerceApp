package com.sercancelik.turkcellgygfinal.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercancelik.turkcellgygfinal.models.response.Category
import com.sercancelik.turkcellgygfinal.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categoryList = repository.getCategories()
                _categories.postValue(categoryList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
