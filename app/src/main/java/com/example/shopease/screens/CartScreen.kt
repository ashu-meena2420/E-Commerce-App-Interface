package com.example.shopease.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopease.data.AppViewModel
import com.example.shopease.models.CartItem
import com.example.shopease.theme.*
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onCheckout: (String, String) -> Unit,
    onProductClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val cartItems = viewModel.cartItems
    val cartTotal = viewModel.cartTotal
    val haptic = LocalHapticFeedback.current
    
    val deliveryFee = if (cartTotal >= 999) 0.0 else 99.0
    val tax = cartTotal * 0.18
    val grandTotal = cartTotal + deliveryFee + tax

    var showCheckoutDialog by remember { mutableStateOf(false) }
    var selectedAddressIndex by remember { mutableStateOf(viewModel.currentUser.selectedAddressIndex) }
    var selectedPaymentMethod by remember { mutableStateOf("UPI (GPay/PhonePe)") }
    var upiId by remember { mutableStateOf("") }
    var upiError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Shopping Cart",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                actions = {
                    if (cartItems.isNotEmpty()) {
                        TextButton(onClick = {
                            try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                            viewModel.clearCart()
                        }) {
                            Text("Clear All", color = Secondary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            ShopBottomNav(
                currentRoute = "cart",
                cartCount = viewModel.cartItemCount,
                onHomeClick = onHomeClick,
                onCartClick = {},
                onWishlistClick = onWishlistClick,
                onProfileClick = onProfileClick
            )
        },
        containerColor = Background
    ) { padding ->
        if (cartItems.isEmpty()) {
            EmptyCartView(onBack = onBack, modifier = Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Scrollable cart items list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 12.dp)
                ) {
                    // Top Card: ShopEase Order Subtotal and Checkout Action Box
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Subtotal (${cartItems.sumOf { it.quantity }} items): ",
                                        fontSize = 16.sp,
                                        color = TextPrimary
                                    )
                                    Text(
                                        "₹${grandTotal.toInt()}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }

                                // Free delivery checker
                                if (cartTotal >= 999) {
                                    Row(
                                        modifier = Modifier.padding(top = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Success, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Your order qualifies for FREE Delivery", fontSize = 12.sp, color = Success)
                                    }
                                } else {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    FreeShippingBanner(cartTotal = cartTotal)
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Proceed to Buy Button (Premium Coral styled action)
                                Button(
                                    onClick = {
                                        try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                        showCheckoutDialog = true
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(44.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                    shape = RoundedCornerShape(22.dp)
                                ) {
                                    Text(
                                        "Proceed to Buy (${cartItems.sumOf { it.quantity }} items)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnPrimary
                                    )
                                }
                            }
                        }
                    }

                    // Cart Items List
                    items(cartItems) { cartItem ->
                        CartItemCard(
                            cartItem = cartItem,
                            onQuantityChange = { newQty ->
                                viewModel.updateCartQuantity(cartItem.product.id, newQty)
                            },
                            onRemove = { viewModel.removeFromCart(cartItem.product.id) },
                            onProductClick = { onProductClick(cartItem.product.id) }
                        )
                    }
                }
            }
        }
    }

    // Checkout Details Dialog (Bottom sheet style overlay)
    if (showCheckoutDialog) {
        AlertDialog(
            onDismissRequest = { showCheckoutDialog = false },
            title = {
                Text("Select Delivery & Payment", fontWeight = FontWeight.Bold, color = TextPrimary)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Delivery Address
                    Text("Delivery Address", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    viewModel.currentUser.addresses.forEachIndexed { index, address ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                    selectedAddressIndex = index
                                }
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedAddressIndex == index,
                                onClick = { selectedAddressIndex = index },
                                colors = RadioButtonDefaults.colors(selectedColor = Secondary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(address, fontSize = 12.sp, color = TextPrimary, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE5E7EB))

                    // Payment Method
                    Text("Payment Method", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    val paymentMethods = listOf("UPI (GPay/PhonePe)", "Credit/Debit Card", "Net Banking", "Cash on Delivery (COD)")
                    paymentMethods.forEach { method ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                    selectedPaymentMethod = method
                                }
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedPaymentMethod == method,
                                onClick = { selectedPaymentMethod = method },
                                colors = RadioButtonDefaults.colors(selectedColor = Secondary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(method, fontSize = 13.sp, color = TextPrimary)
                        }
                    }

                    if (selectedPaymentMethod == "UPI (GPay/PhonePe)") {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = upiId,
                            onValueChange = { upiId = it; upiError = "" },
                            placeholder = { Text("Enter UPI ID (e.g. name@okhdfcbank)", fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = upiError.isNotEmpty(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Secondary,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        if (upiError.isNotEmpty()) {
                            Text(upiError, color = Error, fontSize = 11.sp, modifier = Modifier.padding(start = 4.dp, top = 4.dp))
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                        if (selectedPaymentMethod == "UPI (GPay/PhonePe)" && (!upiId.contains("@") || upiId.length < 5)) {
                            upiError = "Please enter a valid UPI ID"
                        } else {
                            showCheckoutDialog = false
                            val paymentText = if (selectedPaymentMethod == "UPI (GPay/PhonePe)") "UPI ($upiId)" else selectedPaymentMethod
                            onCheckout(viewModel.currentUser.addresses[selectedAddressIndex], paymentText)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFFF8F00))
                ) {
                    Text("Place Order", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCheckoutDialog = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    onProductClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Product image
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onProductClick)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(cartItem.product.imageUrl)
                        .crossfade(400)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    cartItem.product.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp,
                    modifier = Modifier.clickable(onClick = onProductClick)
                )

                if (cartItem.selectedSize.isNotEmpty() || cartItem.selectedColor.isNotEmpty()) {
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (cartItem.selectedSize.isNotEmpty()) {
                            Text("Size: ${cartItem.selectedSize}", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                }

                Row(
                    modifier = Modifier.padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "₹${cartItem.product.price.toInt()}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "In Stock",
                        fontSize = 11.sp,
                        color = Success,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Qty selector drop-down style
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceVariant)
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
                            .padding(horizontal = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                onQuantityChange(cartItem.quantity - 1)
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(12.dp))
                        }
                        Text(
                            cartItem.quantity.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(
                            onClick = {
                                try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                onQuantityChange(cartItem.quantity + 1)
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(12.dp))
                        }
                    }

                    // Delete text button (Premium style)
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
                            .background(SurfaceVariant, RoundedCornerShape(8.dp))
                            .clickable(onClick = {
                                try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                onRemove()
                            })
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            "Delete",
                            fontSize = 12.sp,
                            color = Error,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FreeShippingBanner(cartTotal: Double) {
    val remaining = 999.0 - cartTotal
    val progress = (cartTotal / 999.0).toFloat().coerceIn(0f, 1f)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Add ₹${remaining.toInt()} more for FREE delivery!",
            fontSize = 12.sp,
            color = Color(0xFFCC0C39),
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(2.5.dp)),
            color = Primary,
            trackColor = Color(0xFFE5E7EB)
        )
    }
}

@Composable
fun EmptyCartView(onBack: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
            Text("🛒", fontSize = 80.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Your ShopEase Cart is empty", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text("Shop today's deals or continue shopping your favorites.", fontSize = 13.sp, color = TextSecondary, modifier = Modifier.padding(top = 4.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                shape = RoundedCornerShape(23.dp),
                modifier = Modifier.fillMaxWidth().height(46.dp)
            ) {
                Text("Start Shopping", fontWeight = FontWeight.Bold, color = OnSecondary)
            }
        }
    }
}
