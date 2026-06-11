package com.example.shopease.screens

import androidx.compose.animation.core.*
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
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import coil.request.ImageRequest
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopease.data.AppViewModel
import com.example.shopease.data.SampleData
import com.example.shopease.models.Product
import com.example.shopease.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onCartClick: () -> Unit
) {
    val product = SampleData.getProductById(productId) ?: return
    var selectedColor by remember { mutableStateOf(product.colors.firstOrNull() ?: "") }
    var selectedSize by remember { mutableStateOf(product.sizes.firstOrNull() ?: "") }
    var quantity by remember { mutableStateOf(1) }
    var currentImageIndex by remember { mutableStateOf(0) }
    val isWishlisted = viewModel.isWishlisted(product.id)
    var showAddedToCart by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    val imageVariants = listOf(product.imageUrl, product.imageUrl, product.imageUrl)

    LaunchedEffect(showAddedToCart) {
        if (showAddedToCart) {
            kotlinx.coroutines.delay(2000)
            showAddedToCart = false
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            
            // Premium Clean Top Header (White Surface with Shadow)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                }
                
                Text(
                    text = "Product Details",
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = {
                        try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                        viewModel.toggleWishlist(product)
                    }) {
                        Icon(
                            if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isWishlisted) Error else Color.Gray
                        )
                    }
                    Box {
                        IconButton(onClick = onCartClick) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = TextPrimary)
                        }
                        if (viewModel.cartItemCount > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 4.dp, end = 4.dp)
                                    .size(14.dp)
                                    .background(Primary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                  Text(viewModel.cartItemCount.toString(), fontSize = 8.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Product Image Carousel
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.White)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageVariants[currentImageIndex])
                            .crossfade(400)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize().padding(16.dp)
                    )

                    // Carousel dots
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        imageVariants.forEachIndexed { index, _ ->
                            Box(
                                modifier = Modifier
                                    .size(if (index == currentImageIndex) 8.dp else 6.dp)
                                    .clip(CircleShape)
                                    .background(if (index == currentImageIndex) Secondary else Color.Gray.copy(alpha = 0.5f))
                                    .clickable { currentImageIndex = index }
                            )
                        }
                    }

                    // Navigation arrows
                    IconButton(
                        onClick = { if (currentImageIndex > 0) currentImageIndex-- },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 8.dp)
                            .size(36.dp)
                            .background(Color.Black.copy(alpha = 0.05f), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                    }
                    IconButton(
                        onClick = { if (currentImageIndex < imageVariants.size - 1) currentImageIndex++ },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                            .size(36.dp)
                            .background(Color.Black.copy(alpha = 0.05f), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                    }
                }

                HorizontalDivider(color = Color(0xFFE2E8F0))

                Column(modifier = Modifier.padding(16.dp)) {
                    // Brand link
                    Text(
                        text = "Visit the ${product.brand} Store",
                        fontSize = 13.sp,
                        color = Secondary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { }
                            .padding(bottom = 4.dp)
                    )

                    // Product title
                    Text(
                        product.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        lineHeight = 22.sp
                    )

                    // Ratings block
                    Row(
                        modifier = Modifier.padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RatingStars(rating = product.rating)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "${product.rating} out of 5 (${formatCount(product.reviewCount)} reviews)",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE2E8F0))

                    // Price info
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "-${product.discount}%",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "₹${product.price.toInt()}",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                    }

                    if (product.originalPrice > product.price) {
                        Text(
                            text = "M.R.P.: ₹${product.originalPrice.toInt()}",
                            fontSize = 13.sp,
                            color = TextSecondary,
                            textDecoration = TextDecoration.LineThrough,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Text(
                        text = "Inclusive of all taxes",
                        fontSize = 12.sp,
                        color = TextHint,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    // Free Delivery info card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Success, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("FREE delivery tomorrow", fontSize = 13.sp, color = Success, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                "Order within 12 hrs. Details",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                modifier = Modifier.padding(start = 22.dp, top = 2.dp)
                            )
                        }
                    }

                    // Stock availability
                    Text(
                        text = if (product.inStock) "In Stock" else "Currently Unavailable",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (product.inStock) Success else Error,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Color selection
                    if (product.colors.isNotEmpty()) {
                        Text("Color", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            product.colors.forEach { colorHex ->
                                val color = try { Color(android.graphics.Color.parseColor(colorHex)) } catch (_: Exception) { Primary }
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .border(
                                            width = if (selectedColor == colorHex) 3.dp else 1.dp,
                                            color = if (selectedColor == colorHex) Secondary else Color(0xFFDDDDDD),
                                            shape = CircleShape
                                        )
                                        .clickable {
                                            try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                            selectedColor = colorHex
                                        }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    // Size selection
                    if (product.sizes.isNotEmpty()) {
                        Text("Size", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            product.sizes.forEach { size ->
                                Box(
                                    modifier = Modifier
                                        .size(width = 54.dp, height = 38.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (selectedSize == size) Secondary.copy(alpha = 0.15f) else Color.White)
                                        .border(
                                            width = if (selectedSize == size) 2.dp else 1.dp,
                                            color = if (selectedSize == size) Secondary else Color(0xFFDDDDDD),
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .clickable {
                                            try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                            selectedSize = size
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        size,
                                        fontSize = 13.sp,
                                        fontWeight = if (selectedSize == size) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedSize == size) Secondary else TextPrimary
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    // Quantity selector
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text("Qty: ", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFF1F5F9))
                                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(20.dp))
                                .padding(horizontal = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                    if (quantity > 1) quantity--
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(14.dp))
                            }
                            Text(
                                quantity.toString(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(
                                onClick = {
                                    try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                    quantity++
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(14.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Stacked Buttons (Peach Coral & Teal gradients)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Add to Cart (Coral)
                        Button(
                            onClick = {
                                try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                viewModel.addToCart(product, quantity, selectedColor, selectedSize)
                                showAddedToCart = true
                            },
                            enabled = product.inStock,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            shape = RoundedCornerShape(23.dp)
                        ) {
                            Text(
                                if (showAddedToCart) "Added to Cart! ✓" else "Add to Cart",
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Buy Now (Teal)
                        Button(
                            onClick = {
                                try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                                viewModel.addToCart(product, quantity, selectedColor, selectedSize)
                                onCartClick()
                            },
                            enabled = product.inStock,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                            shape = RoundedCornerShape(23.dp)
                        ) {
                            Text(
                                "Buy Now",
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp), color = Color(0xFFE2E8F0))

                    // Description
                    Text("Description", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        product.description,
                        fontSize = 13.sp,
                        color = TextSecondary,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
