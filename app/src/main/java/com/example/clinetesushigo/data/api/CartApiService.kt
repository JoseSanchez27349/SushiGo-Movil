package com.example.clinetesushigo.data.api

import com.example.clinetesushigo.data.model.AddCartItemRequest
import com.example.clinetesushigo.data.model.CheckoutRequest
import com.example.clinetesushigo.data.model.UpdateCartItemRequest
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {

    @GET("api/v1/carrito")
    suspend fun obtenerCarrito(): Response<JsonElement>

    @POST("api/v1/carrito/items")
    suspend fun agregarItem(
        @Body request: AddCartItemRequest
    ): Response<JsonElement>

    @PATCH("api/v1/carrito/items/{itemId}")
    suspend fun actualizarItem(
        @Path("itemId") itemId: String,
        @Body request: UpdateCartItemRequest
    ): Response<JsonElement>

    @DELETE("api/v1/carrito/items/{itemId}")
    suspend fun eliminarItem(
        @Path("itemId") itemId: String
    ): Response<JsonElement>

    @DELETE("api/v1/carrito")
    suspend fun vaciarCarrito(): Response<JsonElement>

    @POST("api/v1/carrito/checkout")
    suspend fun checkout(
        @Body request: CheckoutRequest
    ): Response<JsonElement>
}