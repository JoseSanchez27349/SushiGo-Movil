package com.example.clinetesushigo.data.api

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET

interface MenuApiService {

    @GET("api/v1/categorias")
    suspend fun obtenerCategorias(): Response<JsonElement>

    @GET("api/v1/productos")
    suspend fun obtenerProductos(): Response<JsonElement>
}