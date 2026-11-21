package com.example.clinetesushigo.data.network

import android.content.Context
import android.util.Log
import com.example.clinetesushigo.data.api.AuthApiService
import com.example.clinetesushigo.data.api.CartApiService
import com.example.clinetesushigo.data.api.MenuApiService
import com.example.clinetesushigo.data.api.OrderApiService
import com.example.clinetesushigo.data.local.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    const val BASE_URL = "http://192.168.100.28:3000/"

    private var tokenManager: TokenManager? = null

    fun init(context: Context) {
        if (tokenManager == null) {
            tokenManager = TokenManager(context.applicationContext)
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val token = tokenManager?.obtenerToken()

            Log.d("API_AUTH", "URL: ${chain.request().url} | Token presente: ${!token.isNullOrBlank()}")

            if (!token.isNullOrBlank()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            chain.proceed(requestBuilder.build())
        }
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val menuApiService: MenuApiService by lazy {
        retrofit.create(MenuApiService::class.java)
    }

    val cartApiService: CartApiService by lazy {
        retrofit.create(CartApiService::class.java)
    }

    val orderApiService: com.example.clinetesushigo.data.api.OrderApiService by lazy {
        retrofit.create(com.example.clinetesushigo.data.api.OrderApiService::class.java)
    }

    fun resolveImageUrl(path: String?): String? {
        if (path.isNullOrBlank() || path == "null") return null
        if (path.startsWith("http://") || path.startsWith("https://")) return path
        val cleanBase = BASE_URL.trimEnd('/')
        val cleanPath = if (path.startsWith("/")) path else "/$path"
        return "$cleanBase$cleanPath"
    }
}