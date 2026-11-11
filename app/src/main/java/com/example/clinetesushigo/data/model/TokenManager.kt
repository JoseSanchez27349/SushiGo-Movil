package com.example.clinetesushigo.data.local

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("sushigo_prefs", Context.MODE_PRIVATE)

    fun guardarToken(token: String) {
        prefs.edit().putString("JWT_TOKEN", token).apply()
    }

    fun obtenerToken(): String? {
        return prefs.getString("JWT_TOKEN", null)
    }

    fun cerrarSesion() {
        prefs.edit().remove("JWT_TOKEN").apply()
    }
}