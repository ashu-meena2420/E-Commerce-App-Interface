package com.example.shopease.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopease.data.AppViewModel
import com.example.shopease.theme.*

@Composable
fun OrderSuccessScreen(
    viewModel: AppViewModel,
    onContinueShopping: () -> Unit
) {
    val scale = remember { Animatable(0f) }
    val checkScale = remember { Animatable(0f) }
    val contentAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            1f,
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        )
        checkScale.animateTo(
            1f,
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
        )
        kotlinx.coroutines.delay(200)
        contentAlpha.animateTo(1f, tween(600))
    }

    val infiniteTransition = rememberInfiniteTransition(label = "success_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            tween(1200, easing = EaseInOutSine),
            RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            // Success checkmark circle
            Box(
                modifier = Modifier
                    .scale(scale.value * pulseScale)
                    .size(120.dp)
                    .background(
                        Success.copy(alpha = 0.15f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Success, CircleShape)
                        .scale(checkScale.value),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Success message
            androidx.compose.animation.AnimatedVisibility(
                visible = contentAlpha.value > 0.5f,
                enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.slideInVertically()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Order Placed successfully! 🎉",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        "Thank you for shopping on ShopEase.in",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Order details card
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Receipt & Dispatch Summary", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Spacer(modifier = Modifier.height(10.dp))

                            OrderDetailRow("Order ID", viewModel.lastOrderId)
                            Spacer(modifier = Modifier.height(6.dp))
                            OrderDetailRow("Total Paid", "₹${viewModel.lastOrderTotal.toInt()}")
                            Spacer(modifier = Modifier.height(6.dp))
                            OrderDetailRow("Deliver to", if (viewModel.lastOrderAddress.length > 25) viewModel.lastOrderAddress.take(25) + "..." else viewModel.lastOrderAddress)
                            Spacer(modifier = Modifier.height(6.dp))
                            OrderDetailRow("Payment Mode", viewModel.lastOrderPayment)
                            Spacer(modifier = Modifier.height(6.dp))
                            OrderDetailRow("Estimated Arrival", "Tomorrow by 9 PM")

                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(color = Color(0xFFE5E7EB))
                            Spacer(modifier = Modifier.height(16.dp))

                            // Step-by-step progress tracking bar
                            OrderStatusTracker(currentStep = 1)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Continue shopping button (Premium Coral)
                    Button(
                        onClick = onContinueShopping,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(23.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Continue Shopping", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Track Order button (Premium Teal Outlined)
                    OutlinedButton(
                        onClick = onContinueShopping,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp),
                        shape = RoundedCornerShape(23.dp),
                        border = BorderStroke(1.5.dp, Secondary),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = Secondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Track your packages", fontSize = 14.sp, color = Secondary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = TextSecondary)
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
}

@Composable
fun OrderStatusTracker(currentStep: Int) {
    val steps = listOf("Ordered", "Dispatched", "Out for delivery", "Arriving")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            if (index <= currentStep) Success else Color(0xFFE5E7EB),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (index <= currentStep) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                    } else {
                        Text("${index + 1}", fontSize = 9.sp, color = TextHint, fontWeight = FontWeight.Bold)
                    }
                }
                Text(step, fontSize = 9.sp, color = if (index <= currentStep) Success else TextHint, modifier = Modifier.padding(top = 3.dp))
            }
            if (index < steps.size - 1) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(if (index < currentStep) Success else Color(0xFFE5E7EB))
                )
            }
        }
    }
}
