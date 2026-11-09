package com.example.clinetesushigo.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.material3.Text

import com.example.clinetesushigo.data.model.Order
import com.example.clinetesushigo.data.repository.OrderManager

import com.example.clinetesushigo.ui.screens.splash.SplashScreen
import com.example.clinetesushigo.ui.screens.login.LoginScreen
import com.example.clinetesushigo.ui.screens.register.RegisterScreen
import com.example.clinetesushigo.ui.screens.home.HomeScreen
import com.example.clinetesushigo.ui.screens.menu.MenuScreen
import com.example.clinetesushigo.ui.screens.cart.CartScreen
import com.example.clinetesushigo.ui.screens.checkout.CheckoutScreen
import com.example.clinetesushigo.ui.screens.confirmation.ConfirmationScreen
import com.example.clinetesushigo.ui.screens.profile.ProfileScreen
import com.example.clinetesushigo.ui.screens.orders.OrdersScreen
import com.example.clinetesushigo.ui.screens.promotions.PromotionsScreen

@Composable
fun AppNavigation() {

    var currentScreen by remember { mutableStateOf("splash") }
    var currentOrder by remember { mutableStateOf<Order?>(null) }

    // =====================================
    // BOTÓN ATRÁS DEL CELULAR
    // =====================================

    BackHandler(enabled = currentScreen != "splash") {

        currentScreen = when (currentScreen) {

            "login" -> "splash"
            "register" -> "login"
            "forgot" -> "login"

            "home" -> "login"

            "menu" -> "home"
            "promotions" -> "home"

            "cart" -> "home"
            "checkout" -> "cart"

            "confirmation" -> "home"

            "profile" -> "home"
            "orders" -> "profile"

            else -> "home"
        }
    }

    // =====================================
    // NAVEGACIÓN ENTRE PANTALLAS
    // =====================================

    when (currentScreen) {

        // BIENVENIDA
        "splash" -> SplashScreen(
            onStart = {
                currentScreen = "login"
            }
        )

        // INICIO DE SESIÓN
        "login" -> LoginScreen(
            onLogin = {
                currentScreen = "home"
            },
            onRegister = {
                currentScreen = "register"
            },
            onForgotPassword = {
                currentScreen = "forgot"
            }
        )

        // REGISTRO
        "register" -> RegisterScreen(
            onRegister = {
                currentScreen = "login"
            },
            onLogin = {
                currentScreen = "login"
            }
        )

        // INICIO
        "home" -> HomeScreen(
            onOpenCart = {
                currentScreen = "cart"
            },
            onOpenMenu = {
                currentScreen = "menu"
            },
            onOpenProfile = {
                currentScreen = "profile"
            },
            onOpenPromotions = {
                currentScreen = "promotions"
            }
        )

        // MENÚ
        "menu" -> MenuScreen(
            onBack = {
                currentScreen = "home"
            },
            onOpenCart = {
                currentScreen = "cart"
            }
        )

        // PROMOCIONES Y OFERTAS
        "promotions" -> PromotionsScreen(
            onBack = {
                currentScreen = "home"
            },
            onOpenCart = {
                currentScreen = "cart"
            }
        )

        // CARRITO
        "cart" -> CartScreen(
            onBack = {
                currentScreen = "home"
            },
            onContinueShopping = {
                currentScreen = "checkout"
            }
        )

        // CHECKOUT
        "checkout" -> CheckoutScreen(
            onBack = {
                currentScreen = "cart"
            },
            onConfirmOrder = { pickupTime, paymentMethod ->

                currentOrder = OrderManager.createOrder(
                    pickupTime = pickupTime,
                    paymentMethod = paymentMethod
                )

                currentScreen = "confirmation"
            }
        )

        // CONFIRMACIÓN
        "confirmation" -> currentOrder?.let { order ->

            ConfirmationScreen(
                order = order,
                onGoHome = {
                    currentScreen = "home"
                }
            )
        }

        // PERFIL
        "profile" -> ProfileScreen(
            onBack = {
                currentScreen = "home"
            },
            onViewOrders = {
                currentScreen = "orders"
            },
            onLogout = {
                currentScreen = "login"
            }
        )

        // HISTORIAL DE PEDIDOS
        "orders" -> OrdersScreen(
            onBack = {
                currentScreen = "profile"
            }
        )

        // RECUPERAR CONTRASEÑA
        "forgot" -> Text(
            text = "Recuperar contraseña"
        )
    }
}