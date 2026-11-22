package com.example.clinetesushigo.ui.screens.register

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clinetesushigo.R

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var codigoVerificacion by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var step by remember { mutableStateOf(1) }

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // ESCUCHAR CAMBIOS DE ESTADO DEL BACKEND
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is RegisterUiState.CodeSent -> {
                Toast.makeText(
                    context,
                    state.mensaje,
                    Toast.LENGTH_LONG
                ).show()

                step = 2
                viewModel.limpiarEstado()
            }

            is RegisterUiState.Success -> {
                Toast.makeText(
                    context,
                    state.mensaje,
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.limpiarEstado()
                onRegister()
            }

            is RegisterUiState.Error -> {
                Toast.makeText(
                    context,
                    state.mensaje,
                    Toast.LENGTH_LONG
                ).show()

                viewModel.limpiarEstado()
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(15.dp))

            // LOGOTIPO
            Box(
                modifier = Modifier
                    .size(105.dp)
                    .background(Beige, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.sushigo_logo
                    ),
                    contentDescription = "Logo de SushiGo",
                    modifier = Modifier.size(95.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // NOMBRE
            Text(
                text = "SushiGo",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = WineRed
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "EL SABOR DE JAPÓN EN TUS MANOS",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = SoftBrown,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = if (step == 1)
                    "Crea tu cuenta"
                else
                    "Verifica tu correo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = if (step == 1)
                    "Regístrate para disfrutar nuestros platillos."
                else
                    "Estamos casi listos. Confirma tu correo electrónico.",
                fontSize = 13.sp,
                color = SoftBrown,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(22.dp))

            // TARJETA DEL FORMULARIO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Crossfade(
                        targetState = step,
                        label = "StepAnimation"
                    ) { currentStep ->

                        if (currentStep == 1) {

                            Column {

                                // NOMBRE
                                OutlinedTextField(
                                    value = nombre,
                                    onValueChange = { nombre = it },
                                    label = { Text("Nombre") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Person,
                                            contentDescription = null,
                                            tint = WineRed
                                        )
                                    },
                                    singleLine = true,
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = registerFieldColors()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // APELLIDO
                                OutlinedTextField(
                                    value = apellido,
                                    onValueChange = { apellido = it },
                                    label = { Text("Apellido") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Person,
                                            contentDescription = null,
                                            tint = WineRed
                                        )
                                    },
                                    singleLine = true,
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = registerFieldColors()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // TELÉFONO
                                OutlinedTextField(
                                    value = telefono,
                                    onValueChange = { telefono = it },
                                    label = { Text("Teléfono") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Phone,
                                            contentDescription = null,
                                            tint = WineRed
                                        )
                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Phone
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = registerFieldColors()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // CORREO
                                OutlinedTextField(
                                    value = correo,
                                    onValueChange = { correo = it },
                                    label = { Text("Correo electrónico") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Email,
                                            contentDescription = null,
                                            tint = WineRed
                                        )
                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = registerFieldColors()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                // CONTRASEÑA
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("Contraseña") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = WineRed
                                        )
                                    },
                                    trailingIcon = {
                                        IconButton(
                                            onClick = {
                                                passwordVisible = !passwordVisible
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (passwordVisible)
                                                    Icons.Default.Visibility
                                                else
                                                    Icons.Default.VisibilityOff,
                                                contentDescription = "Mostrar contraseña",
                                                tint = SoftBrown
                                            )
                                        }
                                    },
                                    singleLine = true,
                                    visualTransformation = if (passwordVisible)
                                        VisualTransformation.None
                                    else
                                        PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = registerFieldColors()
                                )

                                Spacer(modifier = Modifier.height(22.dp))

                                // BOTÓN OBTENER CÓDIGO
                                Button(
                                    onClick = {
                                        viewModel.registrarCliente(
                                            nombre,
                                            apellido,
                                            telefono,
                                            correo,
                                            password
                                        )
                                    },
                                    enabled = uiState !is RegisterUiState.Loading,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = WineRed,
                                        disabledContainerColor = WineRed.copy(
                                            alpha = 0.5f
                                        )
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(54.dp)
                                ) {
                                    if (uiState is RegisterUiState.Loading) {
                                        CircularProgressIndicator(
                                            color = Color.White,
                                            modifier = Modifier.size(24.dp),
                                            strokeWidth = 2.5.dp
                                        )
                                    } else {
                                        Text(
                                            "OBTENER CÓDIGO",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                }
                            }

                        } else {

                            // PASO 2: VERIFICACIÓN
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Icon(
                                    imageVector = Icons.Default.MarkEmailRead,
                                    contentDescription = null,
                                    tint = WineRed,
                                    modifier = Modifier.size(55.dp)
                                )

                                Spacer(modifier = Modifier.height(15.dp))

                                Text(
                                    text = "Ingresa el código enviado a:",
                                    textAlign = TextAlign.Center,
                                    color = DarkText,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                Spacer(modifier = Modifier.height(5.dp))

                                Text(
                                    text = correo,
                                    textAlign = TextAlign.Center,
                                    color = WineRed,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(22.dp))

                                OutlinedTextField(
                                    value = codigoVerificacion,
                                    onValueChange = {
                                        if (it.length <= 6) {
                                            codigoVerificacion = it
                                        }
                                    },
                                    label = {
                                        Text("Código de 6 dígitos")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Key,
                                            contentDescription = null,
                                            tint = WineRed
                                        )
                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = registerFieldColors()
                                )

                                Spacer(modifier = Modifier.height(22.dp))

                                Button(
                                    onClick = {
                                        viewModel.verificarCodigo(
                                            correo,
                                            codigoVerificacion
                                        )
                                    },
                                    enabled = uiState !is RegisterUiState.Loading,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = WineRed,
                                        disabledContainerColor = WineRed.copy(
                                            alpha = 0.5f
                                        )
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(54.dp)
                                ) {
                                    if (uiState is RegisterUiState.Loading) {
                                        CircularProgressIndicator(
                                            color = Color.White,
                                            modifier = Modifier.size(24.dp),
                                            strokeWidth = 2.5.dp
                                        )
                                    } else {
                                        Text(
                                            "CONFIRMAR Y CREAR",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                TextButton(
                                    onClick = { step = 1 }
                                ) {
                                    Text(
                                        "Volver a modificar datos",
                                        color = WineRed
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // REGRESAR AL LOGIN
            TextButton(onClick = onLogin) {
                Text(
                    "¿Ya tienes una cuenta? Inicia sesión",
                    color = WineRed,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

// COLORES COMPARTIDOS DE LOS CAMPOS
@Composable
private fun registerFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = WineRed,
        unfocusedBorderColor = Beige,
        focusedLabelColor = WineRed,
        unfocusedLabelColor = SoftBrown,
        cursorColor = WineRed,
        focusedTextColor = DarkText,
        unfocusedTextColor = DarkText,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
    )