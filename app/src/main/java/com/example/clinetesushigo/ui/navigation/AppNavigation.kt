package com.example.clinetesushigo.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*

import com.example.clinetesushigo.data.model.Order
import com.example.clinetesushigo.data.repository.OrderManager
import com.example.clinetesushigo.data.repository.CartManager

import com.example.clinetesushigo.ui.components.SushiGoNavigationDrawer

import com.example.clinetesushigo.ui.screens.MyOrdersScreen
import com.example.clinetesushigo.ui.screens.splash.SplashScreen
import com.example.clinetesushigo.ui.screens.login.LoginScreen
import com.example.clinetesushigo.ui.screens.register.RegisterScreen
import com.example.clinetesushigo.ui.screens.home.HomeScreen
import com.example.clinetesushigo.ui.screens.cart.CartScreen
import com.example.clinetesushigo.ui.screens.checkout.CheckoutScreen
import com.example.clinetesushigo.ui.screens.confirmation.ConfirmationScreen
import com.example.clinetesushigo.ui.screens.profile.ProfileScreen
import com.example.clinetesushigo.ui.screens.promotions.PromotionsScreen
import com.example.clinetesushigo.ui.screens.forgotpassword.ForgotPasswordScreen
import com.example.clinetesushigo.ui.screens.contact.ContactScreen
import com.example.clinetesushigo.ui.screens.productdetail.ProductDetailScreen

import kotlinx.coroutines.launch


@Composable
fun AppNavigation() {

    val scope = rememberCoroutineScope()

    var currentScreen by remember { mutableStateOf("splash") }
    var currentOrder by remember { mutableStateOf<Order?>(null) }

    // INFORMACIÓN DEL PRODUCTO SELECCIONADO
    var selectedProductId by remember { mutableStateOf("") }
    var selectedProductName by remember { mutableStateOf("") }
    var selectedProductDescription by remember { mutableStateOf("") }
    var selectedProductPrice by remember { mutableIntStateOf(0) }
    var selectedProductEmoji by remember { mutableStateOf("") }
    var selectedProductCategory by remember { mutableStateOf("") }
    var selectedProductProtein by remember { mutableStateOf("") }
    var selectedProductVegetarian by remember { mutableStateOf(false) }
    var selectedProductSpicy by remember { mutableStateOf(false) }
    var selectedProductPreparation by remember { mutableStateOf("") }
    var selectedProductImage by remember { mutableStateOf("") }


    // BOTÓN ATRÁS DEL CELULAR
    BackHandler(enabled = currentScreen != "splash") {

        currentScreen = when (currentScreen) {

            "login" -> "splash"
            "register" -> "login"
            "forgot" -> "login"

            "home" -> "login"

            "productdetail" -> "home"
            "branches" -> "home"
            "contact" -> "home"
            "promotions" -> "home"

            "cart" -> "home"
            "checkout" -> "cart"

            "confirmation" -> "home"

            "profile" -> "home"

            // MIS PEDIDOS REGRESA A INICIO
            "orders" -> "home"

            else -> "home"
        }
    }


    // CONTENIDO DE LAS PANTALLAS
    val screenContent: @Composable () -> Unit = {

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
                    scope.launch {
                        CartManager.syncCart()
                    }

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
                    // Los productos se muestran directamente en Inicio.
                },
                onOpenProfile = {
                    currentScreen = "profile"
                },
                onOpenPromotions = {
                    currentScreen = "promotions"
                },
                onOpenProduct = {
                        id, name, description, price, emoji,
                        category, protein, vegetarian,
                        spicy, preparation, imageUrl ->

                    selectedProductId = id
                    selectedProductName = name
                    selectedProductDescription = description
                    selectedProductPrice = price
                    selectedProductEmoji = emoji
                    selectedProductCategory = category
                    selectedProductProtein = protein
                    selectedProductVegetarian = vegetarian
                    selectedProductSpicy = spicy
                    selectedProductPreparation = preparation
                    selectedProductImage = imageUrl

                    currentScreen = "productdetail"
                }
            )


            // DETALLES DEL PRODUCTO
            "productdetail" -> ProductDetailScreen(
                name = selectedProductName,
                description = selectedProductDescription,
                price = selectedProductPrice,
                emoji = selectedProductEmoji,
                category = selectedProductCategory,
                protein = selectedProductProtein,
                vegetarian = selectedProductVegetarian,
                spicy = selectedProductSpicy,
                preparation = selectedProductPreparation,
                imageUrl = selectedProductImage,

                onBack = {
                    currentScreen = "home"
                },

                onAddToCart = { quantity ->

                    CartManager.addProduct(
                        productoId = selectedProductId,
                        name = selectedProductName,
                        description = selectedProductDescription,
                        price = selectedProductPrice,
                        emoji = selectedProductEmoji,
                        imageUrl = selectedProductImage.ifBlank { null },
                        quantity = quantity
                    )

                    currentScreen = "cart"
                }
            )


            // CONTACTO
            "contact" -> ContactScreen()


            // PROMOCIONES
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
                    },
                    onTrackOrder = {
                        // Abrir el apartado único de Mis pedidos
                        currentScreen = "orders"
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
                    CartManager.cartItems.clear()
                    OrderManager.orders.clear()
                    currentScreen = "login"
                }
            )


            // MIS PEDIDOS: ACTIVOS E HISTORIAL
            "orders" -> MyOrdersScreen(
                onBack = {
                    currentScreen = "home"
                },
                onOpenCart = {
                    currentScreen = "cart"
                }
            )


            // RECUPERAR CONTRASEÑA
            "forgot" -> ForgotPasswordScreen(
                onPasswordResetSuccess = {
                    currentScreen = "login"
                },
                onBackToLogin = {
                    currentScreen = "login"
                }
            )
        }
    }


    // PANTALLAS QUE TENDRÁN EL MENÚ LATERAL
    val drawerScreens = listOf(
        "home",
        "cart",
        "contact",
        "promotions",
        "profile",
        "orders"
    )


    if (currentScreen in drawerScreens) {

        SushiGoNavigationDrawer(
            currentScreen = currentScreen,
            onNavigate = { destination ->
                currentScreen = destination
            }
        ) {
            screenContent()
        }

    } else {

        screenContent()
    }
}