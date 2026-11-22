package com.example.clinetesushigo.ui.screens.contact

import android.content.Intent
import android.net.Uri

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// COLORES SUSHIGO
// ==========================================

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val OliveGreen = Color(0xFF718B52)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color.White

// COLORES PASTEL DE REDES
private val PastelPink = Color(0xFFF5E3E1)
private val PastelBlue = Color(0xFFE3EBF3)
private val PastelLilac = Color(0xFFECE5F2)

// OTROS COLORES
private val PastelCream = Color(0xFFF4EDE4)
private val IconBackground = Color(0xFFF2E8E4)


// ==========================================
// PANTALLA DE CONTACTO
// ==========================================

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

        // CONTACTO
        SectionTitle(
            title = "Contacto",
            subtitle = "Estamos a un mensaje de distancia."
        )

        Spacer(modifier = Modifier.height(14.dp))

        // TELÉFONO
        ContactInfoCard(
            icon = Icons.Default.Phone,
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
            icon = Icons.Default.Email,
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

        // ==================================
        // REDES SOCIALES
        // ==================================

        SectionTitle(
            title = "Síguenos",
            subtitle = "Encuéntranos en nuestras redes sociales."
        )

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // INSTAGRAM
            SocialInfo(
                icon = Icons.Default.CameraAlt,
                name = "Instagram",
                username = "@sushigo.mx",
                backgroundColor = PastelPink
            )

            // FACEBOOK
            SocialInfo(
                icon = Icons.Default.Groups,
                name = "Facebook",
                username = "SushiGo México",
                backgroundColor = PastelBlue
            )

            // TIKTOK
            SocialInfo(
                icon = Icons.Default.MusicNote,
                name = "TikTok",
                username = "@sushigo.oficial",
                backgroundColor = PastelLilac
            )
        }

        Spacer(modifier = Modifier.height(27.dp))

        // ==================================
        // HORARIOS
        // ==================================

        SectionTitle(
            title = "Horario de atención",
            subtitle = "¡Te esperamos!"
        )

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = PastelCream
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {

            Column(
                modifier = Modifier.padding(19.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // RECUADRO BLANCO DEL RELOJ
                    IconContainer(
                        icon = Icons.Default.AccessTime,
                        size = 55.dp,
                        backgroundColor = White
                    )

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

        // ==================================
        // SUCURSAL
        // ==================================

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

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconContainer(
                        icon = Icons.Default.LocationOn,
                        size = 62.dp
                    )

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

                // VISÍTANOS
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    color = PastelCream
                ) {

                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = WineRed,
                            modifier = Modifier.size(25.dp)
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

        Spacer(modifier = Modifier.height(27.dp))

        // ==================================
        // PREGUNTAS FRECUENTES
        // ==================================

        SectionTitle(
            title = "Preguntas frecuentes",
            subtitle = "Resolvemos tus dudas para que disfrutes SushiGo."
        )

        Spacer(modifier = Modifier.height(14.dp))

        FrequentlyAskedQuestions()

        Spacer(modifier = Modifier.height(30.dp))

        // ==================================
        // PIE DE PÁGINA
        // ==================================

        HorizontalDivider(color = Beige)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Sushi",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = WineRed
            )

            Text(
                text = "Go",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = OliveGreen
            )
        }

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
// CONTENEDOR DE ICONOS
// ==========================================

@Composable
private fun IconContainer(
    icon: ImageVector,
    size: androidx.compose.ui.unit.Dp = 50.dp,
    backgroundColor: Color = IconBackground,
    iconColor: Color = WineRed
) {

    Surface(
        modifier = Modifier.size(size),
        shape = RoundedCornerShape(17.dp),
        color = backgroundColor
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(size * 0.48f)
            )
        }
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
    icon: ImageVector,
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

                IconContainer(
                    icon = icon,
                    size = 55.dp
                )

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
// TARJETAS INDIVIDUALES DE REDES
// ==========================================

@Composable
private fun SocialInfo(
    icon: ImageVector,
    name: String,
    username: String,
    backgroundColor: Color
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LOGO BLANCO
            Surface(
                modifier = Modifier.size(58.dp),
                shape = RoundedCornerShape(17.dp),
                color = White
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = icon,
                        contentDescription = name,
                        tint = WineRed,
                        modifier = Modifier.size(27.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {

                Text(
                    text = name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = username,
                    fontSize = 13.sp,
                    color = SoftBrown
                )
            }
        }
    }
}


// ==========================================
// PREGUNTAS FRECUENTES
// ==========================================

@Composable
private fun FrequentlyAskedQuestions() {

    var expandedQuestion by remember {
        mutableStateOf<Int?>(null)
    }

    val questions = listOf(

        "¿Cómo puedo realizar un pedido?" to
                "Entra a nuestro menú, selecciona tus platillos favoritos, agrégalos al carrito y continúa con el proceso de compra.",

        "¿Puedo recoger mi pedido en el restaurante?" to
                "¡Claro! Puedes seleccionar la opción de recoger en el restaurante al realizar tu pedido.",

        "¿Qué métodos de pago aceptan?" to
                "Puedes elegir entre efectivo o tarjeta, de acuerdo con las opciones disponibles al realizar tu pedido.",

        "¿Dónde puedo consultar mis pedidos?" to
                "Entra a tu perfil y selecciona la sección Mis pedidos para consultar tu historial.",

        "¿Cómo puedo comunicarme con SushiGo?" to
                "Puedes llamarnos al +52 388 103 6841 o escribirnos a sushi@sushigo.com.",

        "¿Dónde se encuentra el restaurante?" to
                "Nuestra sucursal se encuentra en Av. Patria 191A, Villa Hermosa, 44490 Guadalajara, Jalisco."
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        questions.forEachIndexed { index, question ->

            val expanded = expandedQuestion == index

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .clickable {
                        expandedQuestion =
                            if (expanded) null else index
                    },
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = question.first,
                            modifier = Modifier.weight(1f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkText
                        )

                        Icon(
                            imageVector = if (expanded)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = if (expanded)
                                "Cerrar respuesta"
                            else
                                "Mostrar respuesta",
                            tint = WineRed
                        )
                    }

                    if (expanded) {

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        HorizontalDivider(color = Beige)

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = question.second,
                            fontSize = 12.sp,
                            color = SoftBrown,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}