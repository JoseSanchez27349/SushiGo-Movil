package com.example.clinetesushigo.data.network

import com.example.clinetesushigo.data.api.AuthApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "http://192.168.100.9:3000/"

    // Configurar timeouts de OkHttp
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS) // Tiempo para establecer conexión
        .readTimeout(15, TimeUnit.SECONDS)    // Tiempo para esperar respuesta
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    val authApiService: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // 👈 Vincula el cliente con mayores timeouts
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }
}