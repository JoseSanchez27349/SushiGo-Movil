package com.example.clinetesushigo.data.api

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET

interface OrderApiService {

    @GET("api/v1/ordenes/mis-ordenes")
    suspend fun obtenerMisOrdenes(): Response<JsonElement>
}