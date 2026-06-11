package com.example.shopease.models

import androidx.compose.ui.graphics.Color

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val originalPrice: Double,
    val rating: Float,
    val reviewCount: Int,
    val description: String,
    val category: String,
    val imageUrl: String,   // URL representation for Coil loading
    val badge: String? = null,
    val isWishlisted: Boolean = false,
    val discount: Int = 0,
    val brand: String = "",
    val colors: List<String> = emptyList(),
    val sizes: List<String> = emptyList(),
    val inStock: Boolean = true
)

data class Category(
    val id: Int,
    val name: String,
    val icon: String, // Keep emoji or symbol for category icon
    val color: Color,
    val productCount: Int
)

data class CartItem(
    val product: Product,
    val quantity: Int,
    val selectedColor: String = "",
    val selectedSize: String = ""
)

data class OrderItem(
    val id: String,
    val date: String,
    val status: String,
    val items: List<CartItem>,
    val total: Double,
    val deliveryAddress: String = "",
    val paymentMethod: String = ""
)

data class User(
    val name: String = "Arjun Sharma",
    val email: String = "arjun.sharma@email.com",
    val phone: String = "+91 98765 43210",
    val avatarText: String = "AS",
    val memberSince: String = "2024",
    val addresses: List<String> = listOf(
        "102, Shanti Vihar, HSR Layout, Bengaluru, Karnataka - 560102",
        "Flat 5B, Skyline Heights, Powai, Mumbai, Maharashtra - 400076",
        "C-404, Green Meadows, Sector 62, Noida, Uttar Pradesh - 201301"
    ),
    val selectedAddressIndex: Int = 0
)

data class BannerItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val discount: String,
    val backgroundColor: Color,
    val accentColor: Color,
    val imageUrl: String // URL instead of emoji
)
