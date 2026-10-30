package com.example.loopstep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loopstep.ui.components.CartModal
import com.example.loopstep.ui.components.OrderConfirmationDialog
import com.example.loopstep.ui.screens.*
import com.example.loopstep.ui.theme.*

class MainActivity : ComponentActivity() {

    private val viewModel: LoopStepViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LoopStepTheme {
                var selectedTab by remember { mutableIntStateOf(0) }
                var isCartOpen by remember { mutableStateOf(false) }

                val cartItems by viewModel.cartItems.collectAsState()
                val totalCartCount = cartItems.sumOf { it.quantity }
                val lastOrder by viewModel.lastOrderConfirmation.collectAsState()

                BackHandler(enabled = selectedTab != 0) {
                    selectedTab = 0
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(PrimaryLime)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "LOOPSTEP",
                                        color = TextMain,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = PrimaryLime.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "45° MODULAR",
                                            color = PrimaryLime,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = { isCartOpen = true },
                                    modifier = Modifier.testTag("top_bar_cart_button")
                                ) {
                                    BadgedBox(
                                        badge = {
                                            if (totalCartCount > 0) {
                                                Badge(
                                                    containerColor = PrimaryLime,
                                                    contentColor = OnPrimary
                                                ) {
                                                    Text(
                                                        "$totalCartCount",
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 10.sp
                                                    )
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.ShoppingCart,
                                            contentDescription = "Cart ($totalCartCount items)",
                                            tint = TextMain
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = BgDark,
                                titleContentColor = TextMain
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = BgBase,
                            tonalElevation = 8.dp,
                            modifier = Modifier.testTag("bottom_nav_bar")
                        ) {
                            NavigationBarItem(
                                selected = selectedTab == 0,
                                onClick = { selectedTab = 0 },
                                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                                label = { Text("Explore", fontSize = 11.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = OnPrimary,
                                    selectedTextColor = PrimaryLime,
                                    indicatorColor = PrimaryLime,
                                    unselectedIconColor = TextMuted,
                                    unselectedTextColor = TextMuted
                                ),
                                modifier = Modifier.testTag("nav_item_explore")
                            )

                            NavigationBarItem(
                                selected = selectedTab == 1,
                                onClick = { selectedTab = 1 },
                                icon = { Icon(Icons.Filled.ShoppingBag, contentDescription = "Shop") },
                                label = { Text("Shop", fontSize = 11.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = OnPrimary,
                                    selectedTextColor = PrimaryLime,
                                    indicatorColor = PrimaryLime,
                                    unselectedIconColor = TextMuted,
                                    unselectedTextColor = TextMuted
                                ),
                                modifier = Modifier.testTag("nav_item_shop")
                            )

                            NavigationBarItem(
                                selected = selectedTab == 2,
                                onClick = { selectedTab = 2 },
                                icon = { Icon(Icons.Filled.Tune, contentDescription = "Studio") },
                                label = { Text("Studio", fontSize = 11.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = OnPrimary,
                                    selectedTextColor = PrimaryLime,
                                    indicatorColor = PrimaryLime,
                                    unselectedIconColor = TextMuted,
                                    unselectedTextColor = TextMuted
                                ),
                                modifier = Modifier.testTag("nav_item_studio")
                            )

                            NavigationBarItem(
                                selected = selectedTab == 3,
                                onClick = { selectedTab = 3 },
                                icon = { Icon(Icons.Filled.DocumentScanner, contentDescription = "Scanner") },
                                label = { Text("Scanner", fontSize = 11.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = OnPrimary,
                                    selectedTextColor = PrimaryLime,
                                    indicatorColor = PrimaryLime,
                                    unselectedIconColor = TextMuted,
                                    unselectedTextColor = TextMuted
                                ),
                                modifier = Modifier.testTag("nav_item_scanner")
                            )

                            NavigationBarItem(
                                selected = selectedTab == 4,
                                onClick = { selectedTab = 4 },
                                icon = { Icon(Icons.Filled.Engineering, contentDescription = "Tech & Impact") },
                                label = { Text("Tech & SDG", fontSize = 11.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = OnPrimary,
                                    selectedTextColor = PrimaryLime,
                                    indicatorColor = PrimaryLime,
                                    unselectedIconColor = TextMuted,
                                    unselectedTextColor = TextMuted
                                ),
                                modifier = Modifier.testTag("nav_item_tech")
                            )
                        }
                    },
                    containerColor = BgDark
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        when (selectedTab) {
                            0 -> HomeScreen(
                                viewModel = viewModel,
                                onNavigateToTab = { selectedTab = it }
                            )
                            1 -> ShopScreen(
                                viewModel = viewModel
                            )
                            2 -> SoleStudioScreen(
                                viewModel = viewModel
                            )
                            3 -> ScannerScreen(
                                viewModel = viewModel,
                                onNavigateToStudio = { selectedTab = 2 }
                            )
                            4 -> TechAndImpactScreen(
                                viewModel = viewModel
                            )
                        }
                    }

                    // Cart Bottom Sheet
                    CartModal(
                        viewModel = viewModel,
                        isOpen = isCartOpen,
                        onDismiss = { isCartOpen = false }
                    )

                    // Order Confirmation Dialog
                    OrderConfirmationDialog(
                        confirmation = lastOrder,
                        onDismiss = { viewModel.dismissOrderConfirmation() }
                    )
                }
            }
        }
    }
}
