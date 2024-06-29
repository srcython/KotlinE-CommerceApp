package com.sercancelik.turkcellgygfinal.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sercancelik.turkcellgygfinal.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authState by loginViewModel.authState.collectAsState()

            LoginScreen(
                onLoginSuccess = {
                    navigateToHome()
                },
                authState = authState,
                onLoginClick = { username, password ->
                    loginViewModel.login(username, password)
                }
            )
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}