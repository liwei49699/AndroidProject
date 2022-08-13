package com.liwei.composelib.ui.mine.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.liwei.composelib.api.Api
import com.liwei.composelib.api.apiCall
import com.liwei.composelib.auth.AuthManager

class MineViewModel : ViewModel() {
    var user by mutableStateOf(AuthManager.user.value)
    var showLoading by mutableStateOf(false)
    var showDialog by mutableStateOf(false)

    init {
        AuthManager.user.observeForever {
            user = it?.copy()
        }
    }

    fun logout() {
        viewModelScope.launch {
            showLoading = true
            apiCall { Api.get().logout() }
            showLoading = false
            AuthManager.onLogout()
        }
    }
}