package com.example.shopease.data

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.shopease.models.*

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("shopease_prefs", Context.MODE_PRIVATE)

    var isLoggedIn by mutableStateOf(sharedPreferences.getBoolean("is_logged_in", false))
    var currentUser by mutableStateOf(
        User(
            name = sharedPreferences.getString("user_name", "Arjun Sharma") ?: "Arjun Sharma",
            email = sharedPreferences.getString("user_email", "arjun.sharma@email.com") ?: "arjun.sharma@email.com",
            phone = "+91 98765 43210",
            avatarText = sharedPreferences.getString("user_avatar", "AS") ?: "AS"
        )
    )

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    private val _wishlistItems = mutableStateListOf<Product>()
    val wishlistItems: List<Product> get() = _wishlistItems

    private val _orders = mutableStateListOf<OrderItem>().apply {
        addAll(SampleData.sampleOrders)
    }
    val orders: List<OrderItem> get() = _orders

    var lastOrderId by mutableStateOf("")
    var lastOrderTotal by mutableStateOf(0.0)
    var lastOrderAddress by mutableStateOf("")
    var lastOrderPayment by mutableStateOf("")

    val cartTotal: Double get() = _cartItems.sumOf { it.product.price * it.quantity }
    val cartItemCount: Int get() = _cartItems.sumOf { it.quantity }

    fun login(email: String = "arjun.sharma@email.com", name: String? = null) {
        val calculatedName = name ?: if (email.contains("@")) email.substringBefore("@").replace(".", " ").split(" ")
            .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } } else "Arjun Sharma"
        val avatar = if (email.contains("@")) email.substring(0, 2).uppercase() else "AS"

        isLoggedIn = true
        currentUser = User(
            name = calculatedName,
            email = email,
            phone = "+91 98765 43210",
            avatarText = avatar
        )

        sharedPreferences.edit()
            .putBoolean("is_logged_in", true)
            .putString("user_name", calculatedName)
            .putString("user_email", email)
            .putString("user_avatar", avatar)
            .apply()
    }

    fun logout() {
        isLoggedIn = false
        _cartItems.clear()

        sharedPreferences.edit()
            .putBoolean("is_logged_in", false)
            .remove("user_name")
            .remove("user_email")
            .remove("user_avatar")
            .apply()
    }

    fun addToCart(product: Product, quantity: Int = 1, color: String = "", size: String = "") {
        val existingIndex = _cartItems.indexOfFirst {
            it.product.id == product.id && it.selectedColor == color && it.selectedSize == size
        }
        if (existingIndex >= 0) {
            val existing = _cartItems[existingIndex]
            _cartItems[existingIndex] = existing.copy(quantity = existing.quantity + quantity)
        } else {
            _cartItems.add(CartItem(product, quantity, color, size))
        }
    }

    fun removeFromCart(productId: Int) {
        _cartItems.removeAll { it.product.id == productId }
    }

    fun updateCartQuantity(productId: Int, quantity: Int) {
        val index = _cartItems.indexOfFirst { it.product.id == productId }
        if (index >= 0) {
            if (quantity <= 0) {
                _cartItems.removeAt(index)
            } else {
                _cartItems[index] = _cartItems[index].copy(quantity = quantity)
            }
        }
    }

    fun toggleWishlist(product: Product) {
        val existing = _wishlistItems.find { it.id == product.id }
        if (existing != null) {
            _wishlistItems.remove(existing)
        } else {
            _wishlistItems.add(product)
        }
    }

    fun isWishlisted(productId: Int): Boolean = _wishlistItems.any { it.id == productId }

    fun placeOrder(address: String, paymentMethod: String): String {
        val orderId = "ORD-2026-${(1000..9999).random()}"
        lastOrderId = orderId
        lastOrderTotal = cartTotal
        lastOrderAddress = address
        lastOrderPayment = paymentMethod

        val newOrder = OrderItem(
            id = orderId,
            date = "Jun 12, 2026",
            status = "Confirmed",
            items = _cartItems.toList(),
            total = cartTotal,
            deliveryAddress = address,
            paymentMethod = paymentMethod
        )
        _orders.add(0, newOrder)
        _cartItems.clear()
        return orderId
    }

    fun clearCart() {
        _cartItems.clear()
    }
}
