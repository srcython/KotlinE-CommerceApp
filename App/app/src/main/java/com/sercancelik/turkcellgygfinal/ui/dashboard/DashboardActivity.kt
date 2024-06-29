package com.sercancelik.turkcellgygfinal.ui.dashboard

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.sercancelik.turkcellgygfinal.R
import com.sercancelik.turkcellgygfinal.databinding.ActivityDashboardBinding
import com.sercancelik.turkcellgygfinal.extensions.loadImageWithGlide
import com.sercancelik.turkcellgygfinal.ui.category.CategoryFragment
import com.sercancelik.turkcellgygfinal.ui.favorites.FavoritesFragment
import com.sercancelik.turkcellgygfinal.ui.home.HomeFragment
import com.sercancelik.turkcellgygfinal.ui.login.LoginActivity
import com.sercancelik.turkcellgygfinal.ui.orders.OrdersFragment
import com.sercancelik.turkcellgygfinal.ui.profile.ProfileFragment
import com.sercancelik.turkcellgygfinal.ui.search.SearchFragment
import com.sercancelik.turkcellgygfinal.util.JwtUtils
import com.sercancelik.turkcellgygfinal.util.KeystoreManager
import com.sercancelik.turkcellgygfinal.util.ThemeUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var tvDashboard: TextView
    private var currentFragment: Fragment? = null

    private val dashboardViewModel: DashboardViewModel by viewModels()

    @Inject
    lateinit var jwtUtils: JwtUtils

    @Inject
    lateinit var keystoreManager: KeystoreManager

    @Inject
    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeRemoteConfig()

        drawerLayout = binding.drawerLayout
        tvDashboard = findViewById(R.id.tv_dashboard)
        val navView: NavigationView = binding.navigationView

        val headerView = navView.getHeaderView(0)
        val tvNavHeaderTitle: TextView = headerView.findViewById(R.id.textViewHeader)

        val token = keystoreManager.getToken()
        if (token != null) {
            val username = jwtUtils.getUsernameFromToken(token)
            tvNavHeaderTitle.text = "Hoşgeldin, $username"
        }
        binding.navigationView.getHeaderView(1)
        val imageViewHeader: ImageView = headerView.findViewById(R.id.imageViewHeader)
        if (token != null) {
            val userPhoto = jwtUtils.getUserPhotoFromToken(token)
            imageViewHeader.loadImageWithGlide(userPhoto)
        }
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
            tvDashboard.text = "Ürünler"
        }

        findViewById<ImageView>(R.id.iv_menu).setOnClickListener {
            hideKeyboard()
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            hideKeyboard()
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    tvDashboard.text = "Ürünler"
                }

                R.id.nav_categories -> {
                    loadFragment(CategoryFragment())
                    tvDashboard.text = menuItem.title
                }

                R.id.nav_logout -> {
                    performLogout()
                }

                R.id.nav_favorites -> {
                    loadFragment(FavoritesFragment())
                    tvDashboard.text = menuItem.title
                }

                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    tvDashboard.text = menuItem.title
                }

                R.id.nav_orders -> {
                    loadFragment(OrdersFragment())
                    tvDashboard.text = menuItem.title
                }

                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    tvDashboard.text = menuItem.title
                }

                else -> {
                }
            }
            true
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view is EditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    view.clearFocus()
                    hideKeyboard()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun initializeRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("RemoteConfig", "Config params updated: $updated")
                    applyFetchedBackgroundColor()
                } else {
                    Log.d("RemoteConfig", "Fetch başarısız ")
                }
            }

        firebaseRemoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (configUpdate.updatedKeys.contains("backgroundColor")) {
                    firebaseRemoteConfig.activate().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            applyFetchedBackgroundColor()
                        }
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.e("RemoteConfig", "Config update error: ${error.message}")
            }
        })
    }

    private fun applyFetchedBackgroundColor() {
        val colorCode = firebaseRemoteConfig.getString("backgroundColor")
        Log.d("Fetched color", colorCode)
        if (colorCode.isEmpty()) {
            Log.d("RemoteConfig", "Background color is null or empty")
            return
        }
        try {
            Color.parseColor(colorCode)
            ThemeUtils.applyBackgroundColor(binding.root, firebaseRemoteConfig)
        } catch (e: IllegalArgumentException) {
            Log.e("RemoteConfig", "Invalid color code: $colorCode", e)
        } catch (e: StringIndexOutOfBoundsException) {
            Log.e("RemoteConfig", "Color code is empty or invalid", e)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        if (fragment !== currentFragment) {
            currentFragment = fragment
            supportFragmentManager.commit {
                replace(R.id.nav_host_fragment, fragment)
                addToBackStack(null)
            }
        }
    }

    private fun performLogout() {
        dashboardViewModel.logout()
        Toast.makeText(this, "Çıkış yapıldı", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
