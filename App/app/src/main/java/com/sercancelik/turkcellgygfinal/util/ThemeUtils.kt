package com.sercancelik.turkcellgygfinal.util

import android.content.Context
import android.graphics.Color
import android.view.View
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

object ThemeUtils {
    fun applyBackgroundColor(view: View, remoteConfig: FirebaseRemoteConfig) {
        val colorCode = remoteConfig.getString("backgroundColor")
        view.setBackgroundColor(Color.parseColor(colorCode))
    }
}
