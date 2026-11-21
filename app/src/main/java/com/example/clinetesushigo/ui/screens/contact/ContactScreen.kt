package com.example.clinetesushigo.ui.screens.contact

import android.content.Intent
import android.net.Uri

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color.White

@Composable
fun ContactScreen() {

    val context = LocalContext.current

    val phone = "+523881036841"
    val email = "sushi@sushigo.com"

    val address =
        "Av. Patria 191A, Villa Hermosa, 44490 Guadalajara, Jalisco, México"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 22.dp)
    ) {

        // INTRODUCCIÓN
        Text(
            text = "¡Estamos para atenderte!",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Queremos que disfrutes cada momento con SushiGo. Contáctanos o visítanos.",
            fontSize = 13.sp,
            color = SoftBrown,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        // TÍTULO CONTACTO
        SectionTitle(
            title = "Contacto",
            subtitle = "Estamos a un mensaje de distancia."
        )

        Spacer(modifier = Modifier.height(14.dp))

        // TELÉFONO
        ContactInfoCard(
            icon = "☎",
            title = "Teléfono",
            detail = "+52 388 103 6841",
            description = "Comunícate con nuestro equipo.",
            buttonText = "LLAMAR",
            onClick = {
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:$phone")
                )
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // CORREO
        ContactInfoCard(
            icon = "✉",
            title = "Correo electrónico",
            detail = email,
            description = "Envíanos tus dudas o sugerencias.",
            buttonText = "ENVIAR CORREO",
            onClick = {
                val intent = Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse("mailto:$email")
                )
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(27.dp))

        // REDES SOCIALES
        SectionTitle(
            title = "Síguenos",
            subtitle = "Encuéntranos en nuestras redes sociales."
        )

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                // INSTAGRAM
                SocialInfo(
                    symbol = "◎",
                    name = "Instagram",
                    username = "@sushigo.mx",
                    iconColor = Color(0xFFC13584)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 14.dp),
                    color = Beige
                )

                // FACEBOOK
                SocialInfo(
                    symbol = "f",
                    name = "Facebook",
                    username = "SushiGo México",
                    iconColor = Color(0xFF1877F2)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 14.dp),
                    color = Beige
                )

                // TIKTOK
                SocialInfo(
                    symbol = "♪",
                    name = "TikTok",
                    username = "@sushigo.oficial",
                    iconColor = DarkText
                )
            }
        }

        Spacer(modifier = Modifier.height(27.dp))

        // HORARIOS
        SectionTitle(
            title = "Horario de atención",
            subtitle = "¡Te esperamos!"
        )

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {

            Column(
                modifier = Modifier.padding(19.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = RoundedCornerShape(13.dp),
                        color = Cream
                    ) {

                        Text(
                            text = "🕒",
                            modifier = Modifier.padding(12.dp),
                            fontSize = 25.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(13.dp))

                    Column {

                        Text(
                            text = "Estamos listos para recibirte",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )

                        Text(
                            text = "Lunes a domingo",
                            fontSize = 12.sp,
                            color = SoftBrown
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                HorizontalDivider(color = Beige)

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Horario",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )

                    Text(
                        text = "12:00 p.m. - 10:00 p.m.",
                        fontSize = 13.sp,
                        color = SoftBrown
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(27.dp))

        // SUCURSAL
        SectionTitle(
            title = "Nuestra sucursal",
            subtitle = "Ven a disfrutar el sabor de SushiGo."
        )

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                // ENCABEZADO
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(62.dp),
                        shape = RoundedCornerShape(17.dp),
                        color = Color(0xFFF2E8E4)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Ubicación",
                                tint = WineRed,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = "SushiGo Guadalajara",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = WineRed
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Nuestra ubicación",
                            fontSize = 12.sp,
                            color = SoftBrown
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                HorizontalDivider(color = Beige)

                Spacer(modifier = Modifier.height(20.dp))

                // DIRECCIÓN
                Text(
                    text = "DIRECCIÓN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = SoftBrown
                )

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = address,
                    fontSize = 15.sp,
                    color = DarkText,
                    lineHeight = 23.sp
                )

                Spacer(modifier = Modifier.height(19.dp))

                // INFORMACIÓN DE UBICACIÓN
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(13.dp),
                    color = Cream
                ) {

                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "📍",
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {

                            Text(
                                text = "Visítanos",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )

                            Text(
                                text = "Te esperamos en nuestra sucursal.",
                                fontSize = 11.sp,
                                color = SoftBrown
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(19.dp))

                // CÓMO LLEGAR
                Button(
                    onClick = {

                        val mapUrl =
                            "https://www.google.com/maps/search/?api=1&query=${Uri.encode(address)}"

                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(mapUrl)
                        )

                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WineRed
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = White
                    )

                    Spacer(modifier = Modifier.width(9.dp))

                    Text(
                        text = "CÓMO LLEGAR",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // PIE DE PÁGINA
        HorizontalDivider(color = Beige)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "🍣 SushiGo",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = WineRed,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "SABOR Y TRADICIÓN",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 10.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold,
            color = SoftBrown,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}


// ==========================================
// TÍTULOS DE SECCIÓN
// ==========================================

@Composable
private fun SectionTitle(
    title: String,
    subtitle: String
) {

    Column {

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = SoftBrown
        )
    }
}


// ==========================================
// TARJETAS DE CONTACTO
// ==========================================

@Composable
private fun ContactInfoCard(
    icon: String,
    title: String,
    detail: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(19.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    shape = RoundedCornerShape(13.dp),
                    color = Color(0xFFF2E8E4)
                ) {

                    Text(
                        text = icon,
                        modifier = Modifier.padding(13.dp),
                        fontSize = 23.sp,
                        color = WineRed
                    )
                }

                Spacer(modifier = Modifier.width(13.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = detail,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = WineRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                color = SoftBrown
            )

            Spacer(modifier = Modifier.height(13.dp))

            OutlinedButton(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(43.dp),
                shape = RoundedCornerShape(11.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = WineRed
                )
            ) {

                Text(
                    text = buttonText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}


// ==========================================
// REDES SOCIALES
// ==========================================

@Composable
private fun SocialInfo(
    symbol: String,
    name: String,
    username: String,
    iconColor: Color
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Surface(
            modifier = Modifier.size(43.dp),
            shape = CircleShape,
            color = iconColor.copy(alpha = 0.10f)
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = symbol,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
        }

        Spacer(modifier = Modifier.width(13.dp))

        Column {

            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = username,
                fontSize = 12.sp,
                color = SoftBrown
            )
        }
    }
}