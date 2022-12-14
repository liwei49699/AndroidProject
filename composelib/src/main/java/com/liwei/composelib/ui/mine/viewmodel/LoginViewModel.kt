package com.liwei.composelib.ui.mine.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.liwei.composelib.api.Api
import com.liwei.composelib.api.apiCall
import com.liwei.composelib.auth.AuthManager
import com.liwei.composelib.widget.Toaster

class LoginViewModel : ViewModel() {
    var showLoading by mutableStateOf(false)
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    fun login(navController: NavHostController) {
        viewModelScope.launch {
            if (username.isEmpty()) {
                Toaster.show("请输入用户名")
                return@launch
            }
            if (password.isEmpty()) {
                Toaster.show("请输入密码")
                return@launch
            }
            showLoading = true
            val loginRes = apiCall { Api.get().login(username, password) }
            showLoading = false
            if (loginRes.isSuccess()) {
                AuthManager.onLogin(loginRes.data!!)
                navController.popBackStack()
                Toaster.show("登录成功")
            } else {
                Toaster.show(loginRes.msg)
            }
        }
    }
}