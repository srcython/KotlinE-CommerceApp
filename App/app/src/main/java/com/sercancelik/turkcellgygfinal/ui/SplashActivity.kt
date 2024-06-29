package com.sercancelik.turkcellgygfinal.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sercancelik.turkcellgygfinal.ui.dashboard.DashboardActivity
import com.sercancelik.turkcellgygfinal.R
import com.sercancelik.turkcellgygfinal.repositories.AuthRepository
import com.sercancelik.turkcellgygfinal.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            checkTokenAndNavigate()
        }, 3000)
    }

    private fun checkTokenAndNavigate() {
        val token = authRepository.getToken()

        if (token.isNullOrEmpty()) {
           startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, DashboardActivity::class.java))

        }
        finish()
    }
}
