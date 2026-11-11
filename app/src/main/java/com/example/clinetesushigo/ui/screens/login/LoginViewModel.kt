package com.example.clinetesushigo.ui.screens.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinetesushigo.data.network.RetrofitClient
import com.example.clinetesushigo.data.local.TokenManager
import com.example.clinetesushigo.data.model.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Success(val token: String, val nombreUsuario: String) : LoginUiState
    data class Error(val mensaje: String) : LoginUiState
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, contrasena: String) {
        if (email.isBlank() || contrasena.isBlank()) {
            _uiState.value = LoginUiState.Error("Por favor completa todos los campos.")
            return;
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            try {
                val response = RetrofitClient.authApiService.loginCliente(
                    LoginRequest(correoElectronico = email.trim(), contrasena = contrasena)
                )

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    val token = body.token

                    if (!token.isNullOrEmpty()) {
                        tokenManager.guardarToken(token)
                        val nombre = body.usuario?.nombre ?: "Cliente"
                        _uiState.value = LoginUiState.Success(token, nombre)
                    } else {
                        _uiState.value = LoginUiState.Error("Respuesta inválida del servidor")
                    }
                } else {
                    // Extraer mensaje de error del backend JSON {"message": "..."}
                    val errorBody = response.errorBody()?.string()
                    val mensajeError = try {
                        JSONObject(errorBody ?: "").getString("message")
                    } catch (e: Exception) {
                        "Credenciales incorrectas o error en el servidor"
                    }
                    _uiState.value = LoginUiState.Error(mensajeError)
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("Error de conexión: Verifica tu red o servidor")
            }
        }
    }

    fun limpiarEstadoError() {
        if (_uiState.value is LoginUiState.Error) {
            _uiState.value = LoginUiState.Idle
        }
    }
}