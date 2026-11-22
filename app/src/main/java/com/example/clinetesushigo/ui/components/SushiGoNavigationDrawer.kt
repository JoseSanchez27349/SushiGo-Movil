package com.example.clinetesushigo.ui.components

import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.clinetesushigo.R

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color(0xFFFFFFFF)

private data class DrawerOption(
    val title: String,
    val route: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SushiGoNavigationDrawer(
    currentScreen: String,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    val options = listOf(
        DrawerOption("Inicio", "home", Icons.Default.Home),
        DrawerOption("Carrito", "cart", Icons.Default.ShoppingCart),
        DrawerOption("Mis pedidos", "orders", Icons.Default.List),
        DrawerOption("Contacto", "contact", Icons.Default.Email),
        DrawerOption("Mi perfil", "profile", Icons.Default.Person)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Cream,
                drawerShape = RoundedCornerShape(
                    topEnd = 22.dp,
                    bottomEnd = 22.dp
                ),
                modifier = Modifier.width(300.dp)
            ) {
                Spacer(modifier = Modifier.height(35.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sushigo_logo),
                        contentDescription = "Logo de SushiGo",
                        modifier = Modifier.size(110.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "SushiGo",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = WineRed
                    )

                    Text(
                        text = "Una experiencia de sabor japonés",
                        fontSize = 11.sp,
                        color = SoftBrown
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = Beige
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "NAVEGACIÓN",
                    modifier = Modifier.padding(
                        start = 25.dp,
                        bottom = 12.dp
                    ),
                    color = SoftBrown,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                options.forEach { option ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = option.title,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        selected = currentScreen == option.route,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            onNavigate(option.route)
                        },
                        icon = {
                            Icon(
                                imageVector = option.icon,
                                contentDescription = option.title
                            )
                        },
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 4.dp
                        ),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = WineRed,
                            selectedIconColor = White,
                            selectedTextColor = White,
                            unselectedContainerColor = Cream,
                            unselectedIconColor = SoftBrown,
                            unselectedTextColor = DarkText
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = Beige
                )

                Text(
                    text = "SUSHIGO • SABOR Y TRADICIÓN",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    color = SoftBrown,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        }
    ) {
        Scaffold(
            containerColor = Cream,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "SushiGo",
                            color = WineRed,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = WineRed
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Cream
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Cream)
            ) {
                content()
            }
        }
    }
}