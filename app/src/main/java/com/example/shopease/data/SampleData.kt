package com.example.shopease.data

import androidx.compose.ui.graphics.Color
import com.example.shopease.models.*
import com.example.shopease.theme.*

object SampleData {

    val categories = listOf(
        Category(1, "Electronics", "💻", CategoryElectronics, 142),
        Category(2, "Fashion", "👗", CategoryFashion, 235),
        Category(3, "Beauty", "💅", CategoryBeauty, 89),
        Category(4, "Sports", "👟", CategorySports, 112),
        Category(5, "Home", "🏠", CategoryHome, 168),
        Category(6, "Books", "📚", CategoryBooks, 310)
    )

    val banners = listOf(
        BannerItem(
            id = 1,
            title = "Grand Festive Sale",
            subtitle = "Top Electronics",
            discount = "Up to 50% OFF",
            backgroundColor = Color(0xFF1E3A8A),
            accentColor = Color(0xFF3B82F6),
            imageUrl = "https://images.unsplash.com/photo-1468495244123-6c6c332eeece?w=800&auto=format&fit=crop"
        ),
        BannerItem(
            id = 2,
            title = "Ethnic Wear Collection",
            subtitle = "Festive Season Specials",
            discount = "Flat 40% OFF",
            backgroundColor = Color(0xFF831843),
            accentColor = Color(0xFFEC4899),
            imageUrl = "https://images.unsplash.com/photo-1483985988355-763728e1935b?w=800&auto=format&fit=crop"
        ),
        BannerItem(
            id = 3,
            title = "Fitness Deals",
            subtitle = "Equip Your Home Gym",
            discount = "Starting ₹499",
            backgroundColor = Color(0xFF064E3B),
            accentColor = Color(0xFF10B981),
            imageUrl = "https://images.unsplash.com/photo-1517838277536-f5f99be501cd?w=800&auto=format&fit=crop"
        )
    )

    val products = listOf(
        Product(
            id = 1,
            name = "OnePlus Nord Buds 3 Wireless Earbuds",
            price = 2299.00,
            originalPrice = 3299.00,
            rating = 4.7f,
            reviewCount = 12847,
            description = "Experience rich, clear sound with OnePlus Nord Buds 3. Features 12.4mm dynamic drivers, active noise cancellation (ANC), 43-hour battery life with flash charge, and dual connection support. Sweat and water-resistant (IP55).",
            category = "Electronics",
            imageUrl = "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=500&auto=format&fit=crop",
            badge = "Best Seller",
            discount = 30,
            brand = "OnePlus",
            colors = listOf("#111827", "#FFFFFF", "#3B82F6"),
            sizes = emptyList()
        ),
        Product(
            id = 2,
            name = "Fire-Boltt Gladiator Bluetooth Smartwatch",
            price = 1999.00,
            originalPrice = 9999.00,
            rating = 4.4f,
            reviewCount = 28523,
            description = "Track your fitness in style with Fire-Boltt Gladiator. Features a massive 1.96-inch HD display, Bluetooth calling with premium dialer pad, 123 sports modes, heart rate and SpO2 tracking, and voice assistant support. IP67 waterproof.",
            category = "Electronics",
            imageUrl = "https://images.unsplash.com/photo-1542496658-e33a6d0d50f6?w=500&auto=format&fit=crop",
            badge = "70% OFF",
            discount = 80,
            brand = "Fire-Boltt",
            colors = listOf("#111827", "#D1D5DB", "#F59E0B"),
            sizes = emptyList()
        ),
        Product(
            id = 3,
            name = "Woodland Premium Leather Jacket",
            price = 6499.00,
            originalPrice = 8999.00,
            rating = 4.6f,
            reviewCount = 1432,
            description = "Premium genuine leather jacket from Woodland. Built for comfort and rugged usage with soft satin lining, heavy-duty metallic zippers, and three utility pockets. Perfect for bikers and winter wear.",
            category = "Fashion",
            imageUrl = "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=500&auto=format&fit=crop",
            badge = "Hot",
            discount = 27,
            brand = "Woodland",
            colors = listOf("#1F2937", "#4B5320"),
            sizes = listOf("S", "M", "L", "XL", "XXL")
        ),
        Product(
            id = 4,
            name = "Keychron K2 Mechanical Keyboard (V2)",
            price = 7499.00,
            originalPrice = 8999.00,
            rating = 4.9f,
            reviewCount = 953,
            description = "Keychron K2 is a wireless mechanical keyboard with 84 keys. Features hot-swappable Gateron switches, RGB backlighting, dual macOS and Windows compatibility, and a massive 4000mAh battery for up to 240 hours of typing.",
            category = "Electronics",
            imageUrl = "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500&auto=format&fit=crop",
            badge = "Top Rated",
            discount = 16,
            brand = "Keychron",
            colors = listOf("#1F2937", "#D1D5DB"),
            sizes = emptyList()
        ),
        Product(
            id = 5,
            name = "Forest Essentials Ayurvedic Skincare Kit",
            price = 3250.00,
            originalPrice = 4500.00,
            rating = 4.8f,
            reviewCount = 879,
            description = "Experience premium Ayurvedic care with this luxurious skincare kit. Contains delicate facial cleanser, steam-distilled pure rose water toner, hydrating day lotion, and deep nourishing night cream. 100% natural and cruelty-free.",
            category = "Beauty",
            imageUrl = "https://images.unsplash.com/photo-1608248597481-496100c8c836?w=500&auto=format&fit=crop",
            badge = "Luxury",
            discount = 27,
            brand = "Forest Essentials",
            colors = emptyList(),
            sizes = emptyList()
        ),
        Product(
            id = 6,
            name = "Red Tape Sports Running Shoes",
            price = 1899.00,
            originalPrice = 5499.00,
            rating = 4.5f,
            reviewCount = 19421,
            description = "Lightweight sports shoes from Red Tape. Designed with memory foam insole cushioning, slip-resistant EVA outsole, and breathable mesh upper for runners and athletes. Perfect for daily jogging.",
            category = "Sports",
            imageUrl = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500&auto=format&fit=crop",
            badge = "Best Price",
            discount = 65,
            brand = "Red Tape",
            colors = listOf("#EF4444", "#3B82F6", "#10B981", "#111827"),
            sizes = listOf("6", "7", "8", "9", "10", "11")
        ),
        Product(
            id = 7,
            name = "Philips LED EyeComfort Desk Lamp",
            price = 1299.00,
            originalPrice = 1999.00,
            rating = 4.3f,
            reviewCount = 3840,
            description = "Philips desk lamp featuring EyeComfort technology, reducing glare and eye fatigue. Has 4-level dimming options with feather touch interface, flexible neck adjustment, and built-in USB charger for smartphones.",
            category = "Home",
            imageUrl = "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=500&auto=format&fit=crop",
            badge = null,
            discount = 35,
            brand = "Philips",
            colors = listOf("#FFFFFF", "#111827"),
            sizes = emptyList()
        ),
        Product(
            id = 8,
            name = "Noise Play 4K Action Camera",
            price = 4999.00,
            originalPrice = 7999.00,
            rating = 4.2f,
            reviewCount = 2109,
            description = "Capture your adventures in 4K Ultra HD. Features a 16MP lens with 170-degree wide-angle view, EIS stabilization, 2-inch touch screen, built-in Wi-Fi, and 30m waterproof casing. Includes multiple mounting brackets.",
            category = "Electronics",
            imageUrl = "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=500&auto=format&fit=crop",
            badge = "Sale",
            discount = 37,
            brand = "Noise",
            colors = listOf("#111827"),
            sizes = emptyList()
        ),
        Product(
            id = 9,
            name = "Decathlon NBR Non-Slip Yoga Mat",
            price = 899.00,
            originalPrice = 1499.00,
            rating = 4.7f,
            reviewCount = 5482,
            description = "8mm thick, high-density NBR yoga mat from Decathlon. Sweat-resistant, anti-slip textured design for optimal grip, and lightweight with an included carrying harness. Perfect for home workouts and yoga.",
            category = "Sports",
            imageUrl = "https://images.unsplash.com/photo-1592432678016-e910b452f9a2?w=500&auto=format&fit=crop",
            badge = "Popular",
            discount = 40,
            brand = "Decathlon",
            colors = listOf("#3B82F6", "#EC4899", "#8B5CF6"),
            sizes = emptyList()
        ),
        Product(
            id = 10,
            name = "Xiaomi 10W Wireless Charger Pad",
            price = 999.00,
            originalPrice = 1499.00,
            rating = 4.4f,
            reviewCount = 9245,
            description = "Fast 10W Qi-certified wireless charging pad with slip-resistant silicone surface. Features multi-protection technology for temperature control, overvoltage protection, and foreign object detection.",
            category = "Electronics",
            imageUrl = "https://images.unsplash.com/photo-1622445262465-2481c4574875?w=500&auto=format&fit=crop",
            badge = null,
            discount = 33,
            brand = "Xiaomi",
            colors = listOf("#111827"),
            sizes = emptyList()
        ),
        Product(
            id = 11,
            name = "Prestige Drip Coffee Maker",
            price = 2499.00,
            originalPrice = 3499.00,
            rating = 4.5f,
            reviewCount = 1845,
            description = "Make authentic drip coffee with the Prestige Drip Coffee Maker. Features a 1.2-liter borosilicate glass carafe (up to 10 cups), mesh filter, anti-drip valve, and hot plate to keep coffee fresh for up to 40 minutes.",
            category = "Home",
            imageUrl = "https://images.unsplash.com/photo-1517701604599-bb29b565090c?w=500&auto=format&fit=crop",
            badge = "Top Brand",
            discount = 28,
            brand = "Prestige",
            colors = listOf("#111827", "#D1D5DB"),
            sizes = emptyList()
        ),
        Product(
            id = 12,
            name = "Kanjeevaram Silk Saree (With Blouse Piece)",
            price = 3999.00,
            originalPrice = 7999.00,
            rating = 4.8f,
            reviewCount = 687,
            description = "Exquisite Kanjeevaram silk saree adorned with classic zari borders and traditional motifs. Perfect for weddings, festivals, and special occasions. Includes an unstitched 80cm blouse piece.",
            category = "Fashion",
            imageUrl = "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=500&auto=format&fit=crop",
            badge = "Ethnic Luxe",
            discount = 50,
            brand = "Varanasi Weaves",
            colors = listOf("#EF4444", "#8B5CF6", "#F59E0B"),
            sizes = emptyList()
        )
    )

    val sampleOrders = listOf(
        OrderItem(
            id = "ORD-2024-8172",
            date = "Jun 02, 2024",
            status = "Delivered",
            items = listOf(
                CartItem(products[0], 1),
                CartItem(products[6], 1)
            ),
            total = 3598.00,
            deliveryAddress = "102, Shanti Vihar, HSR Layout, Bengaluru, Karnataka - 560102",
            paymentMethod = "UPI (Paytm)"
        ),
        OrderItem(
            id = "ORD-2024-5109",
            date = "May 20, 2024",
            status = "Delivered",
            items = listOf(
                CartItem(products[8], 1)
            ),
            total = 899.00,
            deliveryAddress = "102, Shanti Vihar, HSR Layout, Bengaluru, Karnataka - 560102",
            paymentMethod = "Cash on Delivery"
        )
    )

    fun getProductById(id: Int): Product? = products.find { it.id == id }

    fun getProductsByCategory(category: String): List<Product> =
        products.filter { it.category.equals(category, ignoreCase = true) }

    fun getFeaturedProducts(): List<Product> =
        products.filter { it.badge != null }.take(6)

    fun getDiscountedProducts(): List<Product> =
        products.sortedByDescending { it.discount }.take(8)
}
