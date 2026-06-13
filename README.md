# ShopEase – Modern E-Commerce Android App Interface

ShopEase is an Android app I built during my internship at CODTECH

## Intern Project Details
* **INTERN ID:** CITS2358
* **FULL NAME:** Ashu meena
* **NO. OF WEEKS:** 6 Weeks
* **PROJECT NAME:** ShopEase – Modern E-Commerce Android App Interface
* **PROJECT SCOPE:** A modern Android e-commerce application displaying modern layout paradigms, persistent bottom navigation across all core screens, premium light theme styling with Coral and Teal accents, and local session persistence via SharedPreferences to keep users signed in across app launches.

## Project Description
ShopEase is a premium mobile e-commerce portal built to simplify online shopping. It supports standard user authentication and provides user-friendly, responsive screens for different parts of the customer journey:
* **Splash & Auth Screens:** An animated logo transition displaying the custom branding. Signup and Login screens gather user details (name and email) to initialize their shopping profiles.
* **Home Screen:** A landing page featuring a custom search bar, automated banner carousel, circular categories chip row, flash deals, and a grid showing products with pricing, ratings, and instant add-to-cart actions.
* **Product Detail Screen:** A details page showcasing multiple product image variants, interactive color and size selectors, quantity counters, star ratings, and primary Coral / Teal gradient buttons.
* **Shopping Cart & Wishlist:** Allows users to manage active items, adjust item quantities, delete unwanted items, save products to a wishlist, and perform bulk add-to-cart operations.
* **Checkout & Order Success:** A simulated checkout dialog selection for delivery addresses and payment modes (UPI inputs included), followed by a spring-animated order success page with receipt summaries and order tracking progress.
* **Profile Screen:** Displays user statistics (completed orders, wishlist counts), saved addresses, support tickets, and app settings, alongside session logout functions.

## Technology Stack
* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (using a light theme design with Coral `#FF6B6B` and Teal `#0D9488` accents, rounded corners `12.dp`, and soft shadow elevations)
* **Architecture:** MVVM (Model-View-ViewModel) Pattern
* **Navigation:** AndroidX Navigation3 (handles backstack and route transitions)
* **Local Persistence:** SharedPreferences (stores active login session, user profile name, email, and avatar metadata)
* **Image Loading:** Coil (handles asynchronous remote image rendering)

## Folder Directory Structure
app/src/main/java/com/example/shopease/
* **data/**:
  * `AppViewModel.kt`: Central state management, cart operations, order placing, and persistent login session handling.
  * `SampleData.kt`: In-memory datasets containing catalog products and categories.
  * `DataRepository.kt`: Access layer definition.
* **models/**:
  * `Models.kt`: Serialization data classes representing Product, User, CartItem, OrderItem, and Category.
* **theme/**:
  * `Color.kt`: Brand color systems (Primary Coral, Secondary Teal, Success Green, Error Red).
  * `Type.kt`: Typography weights and font size scales.
  * `Theme.kt`: Material3 LightTheme and DarkTheme configuration.
* **screens/**:
  * `AuthScreens.kt`: Form screens for user Sign In and Signup, plus custom ShopEase logos.
  * `HomeScreen.kt`: Home dashboard showing headers, carousels, category rows, product grids, and the persistent bottom nav bar.
  * `ProductDetailScreen.kt`: Detailed catalog specs, color/size selection, and add-to-cart triggers.
  * `CartScreen.kt`: Shopping cart manager, quantity updates, and delivery/payment checkout dialogs.
  * `WishlistScreen.kt`: Saved item cards and bulk cart actions.
  * `ProfileScreen.kt`: User statistics tracker, address overview, support sections, and logout functionality.
  * `SplashScreen.kt`: Delayed entry splash screen.
  * `OrderSuccessScreen.kt`: Spring-animated checkmark and receipt summary.
* **MainActivity.kt**: Main launcher configuration.
* **Navigation.kt**: NavDisplay backstack routing configurations.
* **NavigationKeys.kt**: Type-safe navigation keys for Navigation3.

## How to Set Up and Run the Project
### Prerequisites
* Android Studio (Hedgehog or higher)
* Java Development Kit (JDK) 17
* An Android Emulator or physical Android device running API level 24+

### 1. Open the Project
* Launch Android Studio.
* Select **File -> Open**, navigate to the project directory, and click **OK**.
* Let Gradle sync and download all dependencies.

### 2. Build & Install
* Connect your Android device or start an emulator.
* Click the **Run** button in Android Studio, or execute the following command in the terminal to compile and run:
  ```powershell
  .\gradlew installDebug
  ```

## Testing and Verification
* **Persistent Tab Navigation:** Toggle between Home, Wishlist, Cart, and Profile. Verify that the bottom navigation options do not disappear when moving between these core pages.
* **Premium Custom Colors & Design:** Inspect cards, buttons, and headers. Ensure they use rounded shapes (`12.dp`), soft shadows, and clean Coral/Teal highlights rather than raw Amazon copies.
* **Session Persistence:** Log in or sign up, then close the app. Relaunch the app and confirm the splash screen routes directly to the Home dashboard without asking for login credentials again.
* **Order Flow Simulation:** Add products to the cart, select a delivery address and a payment method (e.g. UPI), and place the order. Verify that the order success animations play and the receipt is displayed on both the success screen and the Profile history.

## Project Deliverables
* Compiling project source code.
* SharedPreferences-based user session persistence system.
* Comprehensive README.md file documenting the project stack, setup steps, folder structure, and security/state logic.
