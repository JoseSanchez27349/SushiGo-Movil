package com.example.clinetesushigo.ui.screens.forgotpassword

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private val SushiRed = Color(0xFFD32F2F)
private val DarkBackground = Color(0xFF121212)

@Composable
fun ForgotPasswordScreen(
    onPasswordResetSuccess: () -> Unit,
    onBackToLogin: () -> Unit,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    var correo by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var nuevaContrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var passVisible by remember { mutableStateOf(false) }

    var step by remember { mutableStateOf(1) } // 1: Pedir Correo, 2: Ingresar Token + Nueva Clave

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is ForgotPasswordUiState.EmailSent -> {
                Toast.makeText(context, state.mensaje, Toast.LENGTH_LONG).show()
                step = 2
                viewModel.limpiarEstado()
            }
            is ForgotPasswordUiState.Success -> {
                Toast.makeText(context, state.mensaje, Toast.LENGTH_LONG).show()
                viewModel.limpiarEstado()
                onPasswordResetSuccess()
            }
            is ForgotPasswordUiState.Error -> {
                Toast.makeText(context, state.mensaje, Toast.LENGTH_LONG).show()
                viewModel.limpiarEstado()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.LockReset,
                contentDescription = null,
                tint = SushiRed,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Recuperar Contraseña",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = if (step == 1) "Ingresa tu correo registrado" else "Crea tu nueva contraseña",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Crossfade(targetState = step, label = "ResetStepAnimation") { currentStep ->
                        if (currentStep == 1) {
                            // Paso 1: Ingresar correo
                            Column {
                                OutlinedTextField(
                                    value = correo,
                                    onValueChange = { correo = it },
                                    label = { Text("Correo Electrónico") },
                                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = SushiRed) },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Button(
                                    onClick = { viewModel.solicitarRecuperacion(correo) },
                                    enabled = uiState !is ForgotPasswordUiState.Loading,
                                    colors = ButtonDefaults.buttonColors(containerColor = SushiRed),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                ) {
                                    if (uiState is ForgotPasswordUiState.Loading) {
                                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                                    } else {
                                        Text("ENVIAR CÓDIGO", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    }
                                }
                            }
                        } else {
                            // Paso 2: Ingresar Token + Nueva Contraseña
                            Column {
                                Text(
                                    text = "Ingresa el token enviado a:\n$correo",
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = token,
                                    onValueChange = { if (it.length <= 6) token = it },
                                    label = { Text("Código de 6 dígitos") },
                                    leadingIcon = { Icon(Icons.Default.VpnKey, contentDescription = null, tint = SushiRed) },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // 👈 Teclado exclusivamente numérico
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = nuevaContrasena,
                                    onValueChange = { nuevaContrasena = it },
                                    label = { Text("Nueva Contraseña") },
                                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = SushiRed) },
                                    trailingIcon = {
                                        IconButton(onClick = { passVisible = !passVisible }) {
                                            Icon(
                                                imageVector = if (passVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    singleLine = true,
                                    visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = confirmarContrasena,
                                    onValueChange = { confirmarContrasena = it },
                                    label = { Text("Confirmar Nueva Contraseña") },
                                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = SushiRed) },
                                    singleLine = true,
                                    visualTransformation = PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Button(
                                    onClick = {
                                        viewModel.restablecerContrasena(token, nuevaContrasena, confirmarContrasena)
                                    },
                                    enabled = uiState !is ForgotPasswordUiState.Loading,
                                    colors = ButtonDefaults.buttonColors(containerColor = SushiRed),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                ) {
                                    if (uiState is ForgotPasswordUiState.Loading) {
                                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                                    } else {
                                        Text("CAMBIAR CONTRASEÑA", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                TextButton(
                                    onClick = { step = 1 },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Reenviar correo", color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackToLogin) {
                Text("Volver al Inicio de Sesión", color = Color.White)
            }
        }
    }
}