package com.example.shopease

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable data object Main : NavKey         // alias kept for legacy
@Serializable data object Splash : NavKey
@Serializable data object Login : NavKey
@Serializable data object Signup : NavKey
@Serializable data object Home : NavKey
@Serializable data class ProductDetail(val productId: Int) : NavKey
@Serializable data object Cart : NavKey
@Serializable data object Wishlist : NavKey
@Serializable data object Profile : NavKey
@Serializable data object OrderSuccess : NavKey
