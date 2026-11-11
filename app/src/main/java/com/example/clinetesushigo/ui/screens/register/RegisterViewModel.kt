package com.example.clinetesushigo.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clinetesushigo.data.api.AuthApiService
import com.example.clinetesushigo.data.model.RegisterRequest
import com.example.clinetesushigo.data.model.Verify2FARequest
import com.example.clinetesushigo.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    data class CodeSent(val mensaje: String) : RegisterUiState()
    data class Success(val mensaje: String) : RegisterUiState()
    data class Error(val mensaje: String) : RegisterUiState()
}

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    // Paso 1: Crear la cuenta y solicitar el código de verificación por correo
    fun registrarCliente(
        nombre: String,
        apellido: String,
        telefono: String,
        correo: String,
        password: String
    ) {
        if (nombre.isBlank() || apellido.isBlank() || telefono.isBlank() || correo.isBlank() || password.isBlank()) {
            _uiState.value = RegisterUiState.Error("Por favor completa todos los campos obligatorios.")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            try {
                val request = RegisterRequest(
                    nombre = nombre.trim(),
                    apellido = apellido.trim(),
                    telefono = telefono.trim(),
                    correoElectronico = correo.trim(),
                    contrasena = password,
                    activar2FA = true
                )

                val response = RetrofitClient.authApiService.registrarCliente(request)

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    _uiState.value = RegisterUiState.CodeSent(body.message)
                } else {
                    val rawError = response.errorBody()?.string()
                    val errorMsg = try {
                        JSONObject(rawError ?: "").getString("message")
                    } catch (e: Exception) {
                        "Error al registrar la cuenta"
                    }
                    _uiState.value = RegisterUiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                _uiState.value = RegisterUiState.Error("Error de red: ${e.localizedMessage}")
            }
        }
    }

    // Paso 2: Validar el código de 6 dígitos ingresado por el cliente
    fun verificarCodigo(correo: String, codigo: String) {
        if (codigo.trim().length < 6) {
            _uiState.value = RegisterUiState.Error("Ingresa el código completo de 6 dígitos.")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            try {
                val request = Verify2FARequest(
                    correoElectronico = correo.trim(),
                    codigo = codigo.trim()
                )

                val response = RetrofitClient.authApiService.verificarCodigo2FA(request)

                if (response.isSuccessful) {
                    _uiState.value = RegisterUiState.Success("¡Cuenta verificada e inicio de sesión exitoso!")
                } else {
                    val rawError = response.errorBody()?.string()
                    val errorMsg = try {
                        JSONObject(rawError ?: "").getString("message")
                    } catch (e: Exception) {
                        "Código inválido o expirado"
                    }
                    _uiState.value = RegisterUiState.Error(errorMsg)
                }
            } catch (e: Exception) {
                _uiState.value = RegisterUiState.Error("Error de red: ${e.localizedMessage}")
            }
        }
    }

    fun limpiarEstado() {
        _uiState.value = RegisterUiState.Idle
    }
}