package com.sercancelik.turkcellgygfinal.ui.dashboard

import androidx.lifecycle.ViewModel
import com.sercancelik.turkcellgygfinal.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun logout() {
        authRepository.clearToken()
    }
}
