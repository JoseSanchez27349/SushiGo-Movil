package com.example.clinetesushigo.ui.screens.branches

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color.White

data class SushiBranch(
    val name: String,
    val city: String,
    val address: String,
    val mapQuery: String
)

private val branches = listOf(

    SushiBranch(
        "SushiGo Centro Magno",
        "Guadalajara",
        "Av. Ignacio L. Vallarta 2425, Arcos Vallarta.",
        "Centro Magno Guadalajara Jalisco"
    ),

    SushiBranch(
        "SushiGo Gran Terraza Oblatos",
        "Guadalajara",
        "Av. Circunvalación 2700, Col. Oblatos.",
        "Gran Terraza Oblatos Guadalajara Jalisco"
    ),

    SushiBranch(
        "SushiGo Plaza del Sol",
        "Zapopan",
        "Av. López Mateos Sur 2375, Ciudad del Sol.",
        "Plaza del Sol Zapopan Jalisco"
    ),

    SushiBranch(
        "SushiGo Galerías Guadalajara",
        "Zapopan",
        "Av. Rafael Sanzio 150, La Estancia.",
        "Galerias Guadalajara Zapopan Jalisco"
    ),

    SushiBranch(
        "SushiGo La Perla",
        "Zapopan",
        "Av. Mariano Otero 3000, Jardines del Sol.",
        "Centro Comercial La Perla Zapopan Jalisco"
    ),

    SushiBranch(
        "SushiGo Forum Tlaquepaque",
        "Tlaquepaque",
        "Blvd. General Marcelino García Barragán 2077.",
        "Forum Tlaquepaque Jalisco"
    ),

    SushiBranch(
        "SushiGo Centro Sur",
        "Tlaquepaque",
        "Periférico Sur 7835, Santa María Tequepexpan.",
        "Plaza Centro Sur Tlaquepaque Jalisco"
    )
)

@Composable
fun BranchesScreen() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(22.dp)
    ) {

        // ENCABEZADO
        Text(
            text = "Nuestras sucursales",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Encuentra tu SushiGo más cercano y disfruta de nuestros platillos favoritos.",
            fontSize = 14.sp,
            color = SoftBrown,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        // CONTADOR DE SUCURSALES
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = WineRed
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "🍣",
                    fontSize = 35.sp
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column {

                    Text(
                        text = "7 sucursales",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )

                    Text(
                        text = "Zona Metropolitana de Guadalajara",
                        fontSize = 12.sp,
                        color = White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // LISTADO DE SUCURSALES
        branches.forEachIndexed { index, branch ->

            BranchCard(
                branch = branch,
                number = index + 1,
                onLocationClick = {

                    val url = Uri.parse(
                        "https://www.google.com/maps/search/?api=1&query=" +
                                Uri.encode(branch.mapQuery)
                    )

                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        url
                    )

                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        // MENSAJE FINAL
        Text(
            text = "¡Te esperamos en SushiGo!",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = WineRed
        )

        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun BranchCard(
    branch: SushiBranch,
    number: Int,
    onLocationClick: () -> Unit
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

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            // NOMBRE Y NÚMERO
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF2E8E4)
                ) {

                    Text(
                        text = "📍",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 23.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Sucursal $number",
                        fontSize = 11.sp,
                        color = SoftBrown
                    )

                    Text(
                        text = branch.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = WineRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(17.dp))

            HorizontalDivider(color = Beige)

            Spacer(modifier = Modifier.height(15.dp))

            // MUNICIPIO
            BranchInformation(
                emoji = "🏙️",
                title = "Municipio",
                information = branch.city
            )

            Spacer(modifier = Modifier.height(13.dp))

            // DIRECCIÓN
            BranchInformation(
                emoji = "🏠",
                title = "Dirección",
                information = branch.address
            )

            Spacer(modifier = Modifier.height(13.dp))

            // HORARIO
            BranchInformation(
                emoji = "🕒",
                title = "Horario de atención",
                information = "Horario por confirmar"
            )

            Spacer(modifier = Modifier.height(17.dp))

            // BOTÓN GOOGLE MAPS
            Button(
                onClick = onLocationClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(47.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WineRed
                )
            ) {

                Text(
                    text = "Ver ubicación en Google Maps",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = White
                )
            }
        }
    }
}

@Composable
fun BranchInformation(
    emoji: String,
    title: String,
    information: String
) {

    Row(
        verticalAlignment = Alignment.Top
    ) {

        Text(
            text = emoji,
            fontSize = 19.sp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {

            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = information,
                fontSize = 12.sp,
                color = SoftBrown,
                lineHeight = 18.sp
            )
        }
    }
}
