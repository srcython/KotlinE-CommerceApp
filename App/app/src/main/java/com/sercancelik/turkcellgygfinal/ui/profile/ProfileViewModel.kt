package com.sercancelik.turkcellgygfinal.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercancelik.turkcellgygfinal.models.response.User
import com.sercancelik.turkcellgygfinal.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user
    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> get() = _updateStatus

    fun getUser(id: Long) {
        viewModelScope.launch {
            val result = repository.getUser(id)
            _user.value = result
        }
    }

    fun updateUser(id: Long, updatedUser: User) {
        viewModelScope.launch {
            val result = repository.updateUser(id, updatedUser)
            _user.value = result
            _updateStatus.value = result != null
        }
    }
}
