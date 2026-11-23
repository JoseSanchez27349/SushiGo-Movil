package com.example.clinetesushigo.data.network

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

object SocketManager {

    private const val TAG = "SUSHIGO_SOCKET"

    private var socket: Socket? = null

    var onOrderStatusChanged: ((
        String,
        String,
        Boolean
    ) -> Unit)? = null

    fun conectar() {
        try {
            if (socket?.connected() == true) return

            val opciones = IO.Options().apply {
                reconnection = true
                reconnectionAttempts = Int.MAX_VALUE
                reconnectionDelay = 2000
                timeout = 10000
            }

            socket = IO.socket(
                RetrofitClient.BASE_URL.trimEnd('/'),
                opciones
            )

            socket?.on(Socket.EVENT_CONNECT) {
                Log.d(TAG, "Conectado al servidor SushiGo")
            }

            socket?.on(Socket.EVENT_DISCONNECT) {
                Log.d(TAG, "Desconectado del servidor")
            }

            socket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                Log.e(
                    TAG,
                    "Error de conexión: ${args.firstOrNull()}"
                )
            }

            socket?.on("pedido:estado_actualizado") { args ->

                val datos = args.firstOrNull() as? JSONObject

                if (datos != null) {

                    val ordenId = datos.optString("ordenId")
                    val estado = datos.optString("estado")
                    val listo = datos.optBoolean("listo", false)

                    Log.d(
                        TAG,
                        "Pedido: $ordenId | Estado: $estado | Listo: $listo"
                    )

                    onOrderStatusChanged?.invoke(
                        ordenId,
                        estado,
                        listo
                    )
                }
            }

            socket?.connect()

        } catch (e: Exception) {
            Log.e(TAG, "No se pudo conectar: ${e.message}")
        }
    }

    fun desconectar() {
        socket?.disconnect()
        socket?.off()
        socket = null
    }

    fun estaConectado(): Boolean {
        return socket?.connected() == true
    }
}