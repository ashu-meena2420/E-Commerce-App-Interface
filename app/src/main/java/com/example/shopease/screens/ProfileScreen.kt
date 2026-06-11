package com.example.shopease.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopease.data.AppViewModel
import com.example.shopease.models.OrderItem
import com.example.shopease.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onOrderHistoryClick: () -> Unit,
    onHomeClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onCartClick: () -> Unit
) {
    val user = viewModel.currentUser
    val orders = viewModel.orders

    var showOrdersDialog by remember { mutableStateOf(false) }
    var selectedOrderForTracking by remember { mutableStateOf<OrderItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", fontWeight = FontWeight.Bold, color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = Secondary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            ShopBottomNav(
                currentRoute = "profile",
                cartCount = viewModel.cartItemCount,
                onHomeClick = onHomeClick,
                onCartClick = onCartClick,
                onWishlistClick = onWishlistClick,
                onProfileClick = {}
            )
        },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFE0F2F1), Background)
                        )
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(
                                Brush.linearGradient(listOf(Primary, Secondary)),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            user.avatarText,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(user.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text(user.email, fontSize = 14.sp, color = TextSecondary, modifier = Modifier.padding(top = 4.dp))
                    Text(user.phone, fontSize = 14.sp, color = TextHint, modifier = Modifier.padding(top = 2.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stats row
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        ProfileStat("${orders.size}", "Orders", Modifier.weight(1f))
                        VerticalDivider(modifier = Modifier.height(36.dp), color = Color(0xFFE2E8F0))
                        ProfileStat("${viewModel.wishlistItems.size}", "Wishlist", Modifier.weight(1f))
                        VerticalDivider(modifier = Modifier.height(36.dp), color = Color(0xFFE2E8F0))
                        ProfileStat("${user.memberSince}", "Member Since", Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Menu sections
            ProfileSection(title = "Account Details") {
                ProfileMenuItem(icon = Icons.Default.Person, label = "Personal Information", onClick = {})
                ProfileMenuItem(icon = Icons.Default.LocationOn, label = "Manage Addresses (${user.addresses.size})", onClick = {})
                ProfileMenuItem(icon = Icons.Default.Payment, label = "Saved Payment Methods", onClick = {})
            }

            Spacer(modifier = Modifier.height(8.dp))

            ProfileSection(title = "My Orders") {
                ProfileMenuItem(
                    icon = Icons.Default.ShoppingBag,
                    label = "Order History",
                    badge = "${orders.size}",
                    onClick = { showOrdersDialog = true }
                )
                ProfileMenuItem(icon = Icons.Default.LocalShipping, label = "Track Active Orders", onClick = {
                    if (orders.isNotEmpty()) {
                        selectedOrderForTracking = orders.first()
                    } else {
                        showOrdersDialog = true
                    }
                })
                ProfileMenuItem(icon = Icons.AutoMirrored.Filled.Undo, label = "Returns & Refunds", onClick = {})
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Recent Orders List (directly visible on profile)
            if (orders.isNotEmpty()) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("Recent Orders", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.padding(bottom = 12.dp))

                    orders.take(2).forEach { order ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedOrderForTracking = order }
                                .padding(bottom = 10.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(Success.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Success, modifier = Modifier.size(22.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(order.id, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                    Text("${order.date} • ${order.items.size} item(s)", fontSize = 12.sp, color = TextSecondary)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("₹${order.total.toInt()}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Primary)
                                    Box(
                                        modifier = Modifier
                                            .background(Success.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(order.status, fontSize = 10.sp, color = Success, fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            ProfileSection(title = "Customer Support") {
                ProfileMenuItem(icon = Icons.AutoMirrored.Filled.Help, label = "Help Center & FAQs", onClick = {})
                ProfileMenuItem(icon = Icons.AutoMirrored.Filled.Chat, label = "Live Chat Support", onClick = {})
                ProfileMenuItem(icon = Icons.Default.Star, label = "Rate ShopEase on Play Store", onClick = {})
            }

            Spacer(modifier = Modifier.height(8.dp))

            ProfileSection(title = "App Settings") {
                ProfileMenuItem(icon = Icons.Default.Notifications, label = "Push Notifications", onClick = {})
                ProfileMenuItem(icon = Icons.Default.Language, label = "Language", subtitle = "English (IN)", onClick = {})
                ProfileMenuItem(icon = Icons.Default.Brightness4, label = "Dark Theme", onClick = {})
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logout button
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.5.dp, Error.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Error)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = Error, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Error)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "ShopEase India v1.0.1 • Member since ${user.memberSince}",
                fontSize = 12.sp,
                color = TextHint,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }

    // Order History Dialog
    if (showOrdersDialog) {
        AlertDialog(
            onDismissRequest = { showOrdersDialog = false },
            title = { Text("Order History", fontWeight = FontWeight.Bold, color = TextPrimary) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    if (orders.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No orders placed yet.", color = TextSecondary)
                        }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(orders) { order ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedOrderForTracking = order
                                            showOrdersDialog = false
                                        },
                                    colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(order.id, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                                            Text("₹${order.total.toInt()}", fontWeight = FontWeight.Bold, color = Primary, fontSize = 13.sp)
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text("Date: ${order.date}", fontSize = 11.sp, color = TextSecondary)
                                        Text("Status: ${order.status}", fontSize = 11.sp, color = Success, fontWeight = FontWeight.SemiBold)
                                        Text("Items: ${order.items.joinToString { it.product.name.take(15) + "..." }}", fontSize = 11.sp, color = TextHint, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showOrdersDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Close", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showOrdersDialog = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            },
            containerColor = Color.White
        )
    }

    // Order Tracking & Detailed Receipt Dialog
    if (selectedOrderForTracking != null) {
        val trackingOrder = selectedOrderForTracking!!
        val steps = listOf("Order Placed", "Confirmed", "Shipped", "Delivered")
        val currentStep = when (trackingOrder.status) {
            "Delivered" -> 3
            "Shipped" -> 2
            "Confirmed", "Processing" -> 1
            else -> 0
        }

        AlertDialog(
            onDismissRequest = { selectedOrderForTracking = null },
            title = { Text("Order Tracker & Receipt", fontWeight = FontWeight.Bold, color = TextPrimary) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("Order ID: ${trackingOrder.id}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                    Text("Placed on: ${trackingOrder.date}", fontSize = 12.sp, color = TextSecondary)
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    // Step Tracker
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        steps.forEachIndexed { index, step ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(
                                            if (index <= currentStep) Success else SurfaceHighlight,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (index <= currentStep) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                    } else {
                                        Text("${index + 1}", fontSize = 10.sp, color = TextHint, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Text(step, fontSize = 8.sp, color = if (index <= currentStep) Success else TextHint, modifier = Modifier.padding(top = 3.dp))
                            }
                            if (index < steps.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(2.dp)
                                        .background(if (index < currentStep) Success else SurfaceHighlight)
                                )
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = SurfaceHighlight)

                    // Shipping Details
                    Text("Delivery Address", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = TextPrimary)
                    Text(trackingOrder.deliveryAddress.ifEmpty { "102, Shanti Vihar, HSR Layout, Bengaluru" }, fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(top = 2.dp))
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text("Payment Method", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = TextPrimary)
                    Text(trackingOrder.paymentMethod.ifEmpty { "UPI" }, fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(top = 2.dp))

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = SurfaceHighlight)

                    // Items list
                    Text("Items Ordered", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = TextPrimary)
                    Spacer(modifier = Modifier.height(8.dp))
                    trackingOrder.items.forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${item.product.name} (x${item.quantity})", fontSize = 12.sp, color = TextSecondary, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text("₹${(item.product.price * item.quantity).toInt()}", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = SurfaceHighlight)
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total Paid", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                        Text("₹${trackingOrder.total.toInt()}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Primary)
                    }
                }
            },
            confirmButton = {
                Button(onClick = { selectedOrderForTracking = null }, colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                    Text("Close")
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
fun ProfileStat(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Secondary)
        Text(label, fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(top = 2.dp))
    }
}

@Composable
fun ProfileSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary, modifier = Modifier.padding(bottom = 8.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column { content() }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    subtitle: String = "",
    badge: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Secondary.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Secondary, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, fontSize = 14.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
            if (subtitle.isNotEmpty()) {
                Text(subtitle, fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(top = 1.dp))
            }
        }
        if (badge.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .background(Primary, RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(badge, fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextHint, modifier = Modifier.size(18.dp))
    }
}
