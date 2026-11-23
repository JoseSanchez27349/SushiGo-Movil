package com.example.clinetesushigo.data.api

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body

data class CancelOrderRequest(
    val motivo: String
)

interface OrderApiService {

    @GET("api/v1/ordenes/mis-ordenes")
    suspend fun obtenerMisOrdenes(): Response<JsonElement>

    @PUT("api/v1/ordenes/{id}/cancelar")
    suspend fun cancelarOrden(
        @Path("id") id: String,
        @Body request: CancelOrderRequest
    ): Response<JsonElement>
}