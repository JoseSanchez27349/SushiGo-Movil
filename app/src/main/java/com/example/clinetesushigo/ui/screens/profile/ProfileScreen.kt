package com.example.clinetesushigo.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color(0xFFFFFFFF)

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onViewOrders: () -> Unit,
    onLogout: () -> Unit
) {

    var name by remember { mutableStateOf("Cliente SushiGo") }
    var email by remember { mutableStateOf("cliente@email.com") }
    var phone by remember { mutableStateOf("") }
    var editing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(22.dp)
    ) {

        // ENCABEZADO
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text(
                    text = "‹",
                    fontSize = 32.sp,
                    color = DarkText
                )
            }

            Text(
                text = "Mi perfil",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // AVATAR
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = WineRed
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "👤",
                        fontSize = 48.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = name,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "Cliente SushiGo",
                fontSize = 13.sp,
                color = SoftBrown
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // INFORMACIÓN PERSONAL
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
                Text(
                    text = "INFORMACIÓN PERSONAL",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftBrown,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nombre") },
                    enabled = editing,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Correo electrónico") },
                    enabled = editing,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Teléfono") },
                    enabled = editing,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        editing = !editing
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WineRed
                    )
                ) {
                    Text(
                        text = if (editing) "GUARDAR CAMBIOS" else "EDITAR PERFIL",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // MIS PEDIDOS
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            onClick = onViewOrders
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Mis pedidos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Text(
                        text = "Consulta tus compras anteriores",
                        fontSize = 12.sp,
                        color = SoftBrown
                    )
                }

                Text(
                    text = "›",
                    fontSize = 27.sp,
                    color = WineRed
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // CERRAR SESIÓN
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = WineRed
            )
        ) {
            Text(
                text = "CERRAR SESIÓN",
                fontWeight = FontWeight.Bold
            )
        }
    }
}