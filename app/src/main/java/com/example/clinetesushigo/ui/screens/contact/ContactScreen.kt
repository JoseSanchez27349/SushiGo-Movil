package com.example.clinetesushigo.ui.screens.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color.White

@Composable
fun ContactScreen() {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(22.dp)
    ) {

        // ENCABEZADO
        Text(
            text = "Contáctanos",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Nos encanta escucharte. Estamos aquí para ayudarte.",
            fontSize = 14.sp,
            color = SoftBrown,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        // TARJETA DE ATENCIÓN AL CLIENTE
        ContactCard(
            emoji = "📞",
            title = "Atención al cliente",
            description = "¿Tienes alguna pregunta sobre tu pedido o nuestros productos?",
            detail = "Nuestro equipo estará disponible próximamente para atenderte."
        )

        Spacer(modifier = Modifier.height(15.dp))

        // TARJETA DE CORREO
        ContactCard(
            emoji = "✉️",
            title = "Correo electrónico",
            description = "¿Tienes alguna duda o sugerencia?",
            detail = "Nuestro correo oficial estará disponible próximamente."
        )

        Spacer(modifier = Modifier.height(15.dp))

        // TARJETA DE REDES SOCIALES
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "📱 Síguenos en redes sociales",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = WineRed
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Muy pronto podrás encontrarnos en nuestras redes sociales para conocer promociones, novedades y nuevos platillos.",
                    fontSize = 13.sp,
                    color = SoftBrown,
                    lineHeight = 21.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    SocialButton(
                        name = "Instagram",
                        modifier = Modifier.weight(1f)
                    )

                    SocialButton(
                        name = "Facebook",
                        modifier = Modifier.weight(1f)
                    )

                    SocialButton(
                        name = "TikTok",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // HORARIO DE ATENCIÓN
        ContactCard(
            emoji = "🕒",
            title = "Horario de atención",
            description = "Queremos atenderte de la mejor manera.",
            detail = "Los horarios oficiales de atención se definirán próximamente."
        )

        Spacer(modifier = Modifier.height(27.dp))

        // FORMULARIO DE CONTACTO
        Text(
            text = "Envíanos un mensaje",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Comparte tus dudas, comentarios o sugerencias con nosotros.",
            fontSize = 13.sp,
            color = SoftBrown,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                // NOMBRE
                Text(
                    text = "Nombre",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(7.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Escribe tu nombre")
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WineRed,
                        unfocusedBorderColor = Beige,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                // CORREO
                Text(
                    text = "Correo electrónico",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(7.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("ejemplo@correo.com")
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WineRed,
                        unfocusedBorderColor = Beige,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                // MENSAJE
                Text(
                    text = "Mensaje",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(7.dp))

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(125.dp),
                    placeholder = {
                        Text("Escribe aquí tu mensaje...")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WineRed,
                        unfocusedBorderColor = Beige,
                        focusedContainerColor = White,
                        unfocusedContainerColor = White
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // BOTÓN ENVIAR
                Button(
                    onClick = {

                        val validEmail = android.util.Patterns.EMAIL_ADDRESS
                            .matcher(email.trim())
                            .matches()

                        when {
                            name.isBlank() ||
                                    email.isBlank() ||
                                    message.isBlank() -> {
                                Toast.makeText(
                                    context,
                                    "Completa todos los campos.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            !validEmail -> {
                                Toast.makeText(
                                    context,
                                    "Ingresa un correo electrónico válido.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    context,
                                    "El formulario aún no está conectado al servidor.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WineRed
                    )
                ) {

                    Text(
                        text = "ENVIAR MENSAJE",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // PIE DE PANTALLA
        HorizontalDivider(color = Beige)

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "SUSHIGO",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = WineRed
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "SABOR Y TRADICIÓN",
            fontSize = 10.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold,
            color = SoftBrown
        )

        Spacer(modifier = Modifier.height(15.dp))
    }
}

// COMPONENTE DE TARJETAS DE CONTACTO
@Composable
fun ContactCard(
    emoji: String,
    title: String,
    description: String,
    detail: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.Top
        ) {

            Text(
                text = emoji,
                fontSize = 27.sp
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column {

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = WineRed
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = DarkText,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = detail,
                    fontSize = 12.sp,
                    color = SoftBrown,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

// BOTONES DE REDES SOCIALES
@Composable
fun SocialButton(
    name: String,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFF2E8E4)
    ) {

        Text(
            text = name,
            modifier = Modifier.padding(
                horizontal = 5.dp,
                vertical = 12.dp
            ),
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = WineRed,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}