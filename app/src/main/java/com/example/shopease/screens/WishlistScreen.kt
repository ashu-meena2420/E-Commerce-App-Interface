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
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import coil.request.ImageRequest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopease.data.AppViewModel
import com.example.shopease.models.Product
import com.example.shopease.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onProductClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val wishlistItems = viewModel.wishlistItems
    val haptic = LocalHapticFeedback.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Wishlist", fontWeight = FontWeight.Bold, color = TextPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                actions = {
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
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            ShopBottomNav(
                currentRoute = "wishlist",
                cartCount = viewModel.cartItemCount,
                onHomeClick = onHomeClick,
                onCartClick = onCartClick,
                onWishlistClick = {},
                onProfileClick = onProfileClick
            )
        },
        containerColor = Background
    ) { padding ->
        if (wishlistItems.isEmpty()) {
            EmptyWishlistView(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                item {
                    Text(
                        "${wishlistItems.size} saved item${if (wishlistItems.size != 1) "s" else ""}",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }

                items(wishlistItems) { product ->
                    WishlistItemCard(
                        product = product,
                        onRemove = { viewModel.toggleWishlist(product) },
                        onAddToCart = { viewModel.addToCart(product) },
                        onProductClick = { onProductClick(product.id) }
                    )
                }

                // Move all to cart button (Premium custom Teal action)
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = {
                            try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                            wishlistItems.forEach { viewModel.addToCart(it) }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = OnSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Add All to Cart", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSecondary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WishlistItemCard(
    product: Product,
    onRemove: () -> Unit,
    onAddToCart: () -> Unit,
    onProductClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                onProductClick()
            }),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product image
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageUrl)
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
                    product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    RatingStars(rating = product.rating)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("(${formatCount(product.reviewCount)})", fontSize = 11.sp, color = TextSecondary)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("₹${product.price.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)

                    // Add to cart button (Premium Coral pill button)
                    Button(
                        onClick = {
                            try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                            onAddToCart()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Add to Cart", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OnPrimary)
                    }
                }
            }

            // Remove button
            IconButton(
                onClick = {
                    try { haptic.performHapticFeedback(HapticFeedbackType.LongPress) } catch (_: Exception) {}
                    onRemove()
                },
                modifier = Modifier
                    .align(Alignment.Top)
                    .size(32.dp)
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Remove from wishlist",
                    tint = Error,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyWishlistView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
            Text("💝", fontSize = 80.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Your Wishlist is empty", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text("Save products you want to buy later here.", fontSize = 13.sp, color = TextSecondary, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
