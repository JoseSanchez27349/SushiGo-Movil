package com.example.clinetesushigo.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit
) {

    // ESTADOS DE LOS CAMPOS
    var currentStep by remember { mutableIntStateOf(1) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(horizontal = 25.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // LOGOTIPO
        Box(
            modifier = Modifier
                .size(75.dp)
                .background(Beige, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🍣",
                fontSize = 42.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // NOMBRE
        Text(
            text = "SushiGo",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = WineRed
        )

        Text(
            text = "EL SABOR DE JAPÓN EN TUS MANOS",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = SoftBrown,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        // INDICADOR DE PASOS
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            StepIndicator(
                number = 1,
                title = "Datos personales",
                selected = currentStep == 1
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                color = Beige,
                thickness = 2.dp
            )

            StepIndicator(
                number = 2,
                title = "Seguridad",
                selected = currentStep == 2
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // TÍTULO
        Text(
            text = if (currentStep == 1)
                "¡Únete a SushiGo!"
            else
                "Protege tu cuenta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = if (currentStep == 1)
                "Cuéntanos un poco sobre ti."
            else
                "Crea una contraseña segura.",
            fontSize = 14.sp,
            color = SoftBrown,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        // =================================
        // PASO 1: DATOS PERSONALES
        // =================================

        if (currentStep == 1) {

            // NOMBRE COMPLETO
            OutlinedTextField(
                value = name,
                onValueChange = { newName ->
                    name = newName
                    errorMessage = ""
                },
                label = {
                    Text("Nombre completo")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = sushiFieldColors()
            )

            Spacer(modifier = Modifier.height(15.dp))

            // CORREO ELECTRÓNICO
            OutlinedTextField(
                value = email,
                onValueChange = { newEmail ->
                    email = newEmail
                    errorMessage = ""
                },
                label = {
                    Text("Correo electrónico")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                shape = RoundedCornerShape(14.dp),
                colors = sushiFieldColors()
            )

            Spacer(modifier = Modifier.height(15.dp))

            // TELÉFONO
            OutlinedTextField(
                value = phone,
                onValueChange = { newPhone ->
                    if (
                        newPhone.length <= 10 &&
                        newPhone.all { it.isDigit() }
                    ) {
                        phone = newPhone
                        errorMessage = ""
                    }
                },
                label = {
                    Text("Número de teléfono")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                shape = RoundedCornerShape(14.dp),
                colors = sushiFieldColors()
            )

        } else {

            // =================================
            // PASO 2: SEGURIDAD
            // =================================

            // CONTRASEÑA
            OutlinedTextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                    errorMessage = ""
                },
                label = {
                    Text("Crear contraseña")
                },
                trailingIcon = {
                    TextButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Text(
                            text = if (passwordVisible) "Ocultar" else "Ver",
                            color = WineRed
                        )
                    }
                },
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = sushiFieldColors()
            )

            Spacer(modifier = Modifier.height(15.dp))

            // CONFIRMAR CONTRASEÑA
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { newConfirmPassword ->
                    confirmPassword = newConfirmPassword
                    errorMessage = ""
                },
                label = {
                    Text("Confirmar contraseña")
                },
                trailingIcon = {
                    TextButton(
                        onClick = {
                            confirmPasswordVisible =
                                !confirmPasswordVisible
                        }
                    ) {
                        Text(
                            text = if (confirmPasswordVisible)
                                "Ocultar"
                            else
                                "Ver",
                            color = WineRed
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = sushiFieldColors()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "La contraseña debe tener al menos 6 caracteres.",
                color = SoftBrown,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // MENSAJE DE ERROR
        if (errorMessage.isNotEmpty()) {

            Text(
                text = errorMessage,
                color = WineRed,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        // =================================
        // BOTÓN PRINCIPAL
        // =================================

        Button(
            onClick = {

                if (currentStep == 1) {

                    // VALIDACIONES DEL PASO 1

                    errorMessage = when {

                        name.isBlank() ->
                            "Ingresa tu nombre completo."

                        !android.util.Patterns.EMAIL_ADDRESS
                            .matcher(email.trim()).matches() ->
                            "Ingresa un correo electrónico válido."

                        phone.length != 10 ->
                            "El teléfono debe tener 10 dígitos."

                        else -> ""
                    }

                    // AVANZAR AL PASO 2
                    if (errorMessage.isEmpty()) {
                        currentStep = 2
                    }

                } else {

                    // VALIDACIONES DEL PASO 2

                    errorMessage = when {

                        password.length < 6 ->
                            "La contraseña debe tener al menos 6 caracteres."

                        password != confirmPassword ->
                            "Las contraseñas no coinciden."

                        else -> ""
                    }

                    // FINALIZAR REGISTRO
                    if (errorMessage.isEmpty()) {
                        onRegister()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = WineRed
            ),
            shape = RoundedCornerShape(15.dp)
        ) {

            Text(
                text = if (currentStep == 1)
                    "CONTINUAR"
                else
                    "CREAR CUENTA",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = Color.White
            )
        }

        // =================================
        // REGRESAR AL PASO ANTERIOR
        // =================================

        if (currentStep == 2) {

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    currentStep = 1
                    errorMessage = ""
                }
            ) {

                Text(
                    text = "← Regresar al paso anterior",
                    color = WineRed
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // INICIAR SESIÓN
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "¿Ya tienes cuenta?",
                color = SoftBrown,
                fontSize = 13.sp
            )

            TextButton(
                onClick = onLogin
            ) {

                Text(
                    text = "Inicia sesión",
                    color = WineRed,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// =================================
// INDICADOR DE PASOS
// =================================

@Composable
private fun StepIndicator(
    number: Int,
    title: String,
    selected: Boolean
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(35.dp)
                .background(
                    color = if (selected) WineRed else Beige,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = number.toString(),
                color = if (selected) Color.White else SoftBrown,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = title,
            fontSize = 10.sp,
            color = if (selected) WineRed else SoftBrown,
            fontWeight = if (selected)
                FontWeight.Bold
            else
                FontWeight.Normal
        )
    }
}

// =================================
// ESTILO DE LOS CAMPOS
// =================================

@Composable
private fun sushiFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = WineRed,
        unfocusedBorderColor = Beige,
        focusedLabelColor = WineRed,
        cursorColor = WineRed,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
    )
