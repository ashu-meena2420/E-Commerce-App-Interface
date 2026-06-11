package com.example.shopease

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.shopease.data.AppViewModel
import com.example.shopease.screens.*

@Composable
fun MainNavigation() {
    val backStack = rememberNavBackStack(Splash)
    val appViewModel: AppViewModel = viewModel()

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {

            // Splash Screen
            entry<Splash> {
                SplashScreen(
                    onSplashComplete = {
                        backStack.clear()
                        if (appViewModel.isLoggedIn) {
                            backStack.add(Home)
                        } else {
                            backStack.add(Login)
                        }
                    }
                )
            }

            // Login Screen
            entry<Login> {
                LoginScreen(
                    onLoginSuccess = { email ->
                        appViewModel.login(email)
                        backStack.clear()
                        backStack.add(Home)
                    },
                    onNavigateToSignup = {
                        backStack.add(Signup)
                    }
                )
            }

            // Signup Screen
            entry<Signup> {
                SignupScreen(
                    onSignupSuccess = { name, email ->
                        appViewModel.login(email, name)
                        backStack.clear()
                        backStack.add(Home)
                    },
                    onNavigateToLogin = {
                        backStack.removeLastOrNull()
                    }
                )
            }

            // Home Screen
            entry<Home> {
                HomeScreen(
                    viewModel = appViewModel,
                    onProductClick = { productId ->
                        backStack.add(ProductDetail(productId))
                    },
                    onCartClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Cart)
                    },
                    onWishlistClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Wishlist)
                    },
                    onProfileClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Profile)
                    }
                )
            }

            // Product Detail Screen
            entry<ProductDetail> { key ->
                ProductDetailScreen(
                    productId = key.productId,
                    viewModel = appViewModel,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onCartClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Cart)
                    }
                )
            }

            // Cart Screen
            entry<Cart> {
                CartScreen(
                    viewModel = appViewModel,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onCheckout = { address, payment ->
                        appViewModel.placeOrder(address, payment)
                        backStack.removeLastOrNull()
                        backStack.add(OrderSuccess)
                    },
                    onProductClick = { productId ->
                        backStack.add(ProductDetail(productId))
                    },
                    onHomeClick = {
                        backStack.clear()
                        backStack.add(Home)
                    },
                    onWishlistClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Wishlist)
                    },
                    onProfileClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Profile)
                    }
                )
            }

            // Wishlist Screen
            entry<Wishlist> {
                WishlistScreen(
                    viewModel = appViewModel,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onProductClick = { productId ->
                        backStack.add(ProductDetail(productId))
                    },
                    onHomeClick = {
                        backStack.clear()
                        backStack.add(Home)
                    },
                    onCartClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Cart)
                    },
                    onProfileClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Profile)
                    }
                )
            }

            // Profile Screen
            entry<Profile> {
                ProfileScreen(
                    viewModel = appViewModel,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onLogout = {
                        appViewModel.logout()
                        backStack.clear()
                        backStack.add(Login)
                    },
                    onOrderHistoryClick = {},
                    onHomeClick = {
                        backStack.clear()
                        backStack.add(Home)
                    },
                    onWishlistClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Wishlist)
                    },
                    onCartClick = {
                        backStack.clear()
                        backStack.add(Home)
                        backStack.add(Cart)
                    }
                )
            }

            // Order Success Screen
            entry<OrderSuccess> {
                OrderSuccessScreen(
                    viewModel = appViewModel,
                    onContinueShopping = {
                        backStack.clear()
                        backStack.add(Home)
                    }
                )
            }
        }
    )
}
