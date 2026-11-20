package com.example.clinetesushigo.data.network

import android.content.Context

object SessionManager {
    private const val PREFS_NAME = "sushigo_prefs"
    private const val KEY_TOKEN = "jwt_token"

    var currentToken: String? = null
        private set

    fun saveToken(context: Context, token: String) {
        currentToken = token
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    fun getToken(context: Context): String? {
        if (!currentToken.isNullOrBlank()) return currentToken
        currentToken = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_TOKEN, null)
        return currentToken
    }

    fun clearSession(context: Context) {
        currentToken = null
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_TOKEN)
            .apply()
    }
}