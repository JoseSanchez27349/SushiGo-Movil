package com.example.clinetesushigo.ui.screens.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinetesushigo.data.model.ForgotPasswordRequest
import com.example.clinetesushigo.data.model.ResetPasswordRequest
import com.example.clinetesushigo.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

sealed class ForgotPasswordUiState {
    object Idle : ForgotPasswordUiState()
    object Loading : ForgotPasswordUiState()
    data class EmailSent(val mensaje: String) : ForgotPasswordUiState()
    data class Success(val mensaje: String) : ForgotPasswordUiState()
    data class Error(val mensaje: String) : ForgotPasswordUiState()
}

class ForgotPasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState

    // 1. Enviar correo para recibir el token
    fun solicitarRecuperacion(correo: String) {
        if (correo.isBlank()) {
            _uiState.value = ForgotPasswordUiState.Error("Por favor ingresa tu correo electrónico.")
            return
        }

        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            try {
                val request = ForgotPasswordRequest(correoElectronico = correo.trim())
                val response = RetrofitClient.authApiService.solicitarRecuperacion(request)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = ForgotPasswordUiState.EmailSent(
                        response.body()?.message ?: "Instrucciones enviadas al correo."
                    )
                } else {
                    val rawError = response.errorBody()?.string()
                    val errorMsg = try {
                        JSONObject(rawError ?: "").getString("message")
                    } catch (e: Exception) {
                        "Error al solicitar la recuperación."
                    }
                    _uiState.value = ForgotPasswordUiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                _uiState.value = ForgotPasswordUiState.Error("Error de red: ${e.localizedMessage}")
            }
        }
    }

    // 2. Restablecer la contraseña con el token recibido
    fun restablecerContrasena(token: String, nuevaContrasena: String, confirmarContrasena: String) {
        if (token.isBlank() || nuevaContrasena.isBlank()) {
            _uiState.value = ForgotPasswordUiState.Error("Ingresa el token y la nueva contraseña.")
            return
        }
        if (nuevaContrasena != confirmarContrasena) {
            _uiState.value = ForgotPasswordUiState.Error("Las contraseñas no coinciden.")
            return
        }

        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            try {
                val request = ResetPasswordRequest(
                    token = token.trim(),
                    nuevaContrasena = nuevaContrasena
                )
                val response = RetrofitClient.authApiService.restablecerContrasena(request)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = ForgotPasswordUiState.Success(
                        response.body()?.message ?: "Contraseña actualizada con éxito."
                    )
                } else {
                    val rawError = response.errorBody()?.string()
                    val errorMsg = try {
                        JSONObject(rawError ?: "").getString("message")
                    } catch (e: Exception) {
                        "El token es inválido o ha expirado."
                    }
                    _uiState.value = ForgotPasswordUiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                _uiState.value = ForgotPasswordUiState.Error("Error de red: ${e.localizedMessage}")
            }
        }
    }

    fun limpiarEstado() {
        _uiState.value = ForgotPasswordUiState.Idle
    }
}