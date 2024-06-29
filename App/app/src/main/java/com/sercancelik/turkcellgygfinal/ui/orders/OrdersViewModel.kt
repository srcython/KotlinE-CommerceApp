package com.sercancelik.turkcellgygfinal.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sercancelik.turkcellgygfinal.models.Cart
import com.sercancelik.turkcellgygfinal.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    fun getUserCarts(userId: Long): LiveData<List<Cart>> = liveData {
        val carts = cartRepository.getUserCarts(userId)
        emit(carts)
    }
}
