package com.example.loopstep.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loopstep.LoopStepViewModel
import com.example.loopstep.R
import com.example.loopstep.data.CartItem
import com.example.loopstep.data.MockData
import com.example.loopstep.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: LoopStepViewModel,
    onNavigateToTab: (Int) -> Unit
) {
    var quickAngle by remember { mutableFloatStateOf(0f) }
    val animatedAngle by animateFloatAsState(targetValue = quickAngle, label = "twist_quick")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
            .testTag("home_screen_column"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Hero Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hero_mechanism),
                    contentDescription = "LoopStep Twist-Lock Sole Mechanism",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    BgDark.copy(alpha = 0.5f),
                                    BgDark
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {
                    Surface(
                        color = PrimaryLime.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, PrimaryLime),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(SignalGreen)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "MODULAR FOOTWEAR INNOVATION",
                                color = PrimaryLime,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Text(
                        text = "ONE SHOE.\nEVERY TERRAIN.\nLESS WASTE.",
                        color = TextMain,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        lineHeight = 32.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Decouple upper wear from outsole wear. Twist off the worn sole and lock in a new one in 10 seconds. Soles from ₹200.",
                        color = TextMuted,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { onNavigateToTab(1) }, // Shop
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryLime,
                                contentColor = OnPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("home_shop_button")
                        ) {
                            Icon(Icons.Filled.ShoppingBag, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Shop Soles & Shoes", fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { onNavigateToTab(2) }, // Studio
                            border = BorderStroke(1.dp, BorderDim),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextMain),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Filled.Tune, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("3D Studio")
                        }
                    }
                }
            }
        }

        // Quick Highlights Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(BgSurface)
                    .border(1.dp, BorderDim, RoundedCornerShape(16.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                HighlightMetric(title = "< 10 sec", label = "Sole Swap", icon = Icons.Filled.Refresh)
                HighlightMetric(title = "7,000 N", label = "Load Tested", icon = Icons.Filled.Shield)
                HighlightMetric(title = "-70%", label = "Waste Cut", icon = Icons.Filled.Eco)
                HighlightMetric(title = "₹200", label = "Soles Start", icon = Icons.Filled.CurrencyRupee)
            }
        }

        // Interactive Twist-Lock Teaser Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = BgCard),
                border = BorderStroke(1.dp, if (quickAngle >= 40f) PrimaryLime else BorderDim),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "PATENTED 45° TWIST-LOCK",
                                color = PrimaryLime,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = if (quickAngle >= 40f) "Status: LOCKED (Mechanical Detent Engaged)" else "Status: UNLOCKED / ROTATING",
                                color = if (quickAngle >= 40f) SignalGreen else SignalAmber,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(BgSurface)
                                .border(2.dp, if (quickAngle >= 40f) SignalGreen else PrimaryLime, CircleShape)
                                .clickable {
                                    quickAngle = if (quickAngle >= 40f) 0f else 45f
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.RotateRight,
                                contentDescription = "Twist mechanism",
                                tint = if (quickAngle >= 40f) SignalGreen else PrimaryLime,
                                modifier = Modifier
                                    .size(36.dp)
                                    .rotate(animatedAngle)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Touch the dial to test the 45° radial bayonet rotation. PA66-GF30 reinforced teeth withstand extreme athletic shear force.",
                        color = TextMuted,
                        fontSize = 12.sp
                    )

                    Slider(
                        value = quickAngle,
                        onValueChange = { quickAngle = it },
                        valueRange = 0f..45f,
                        colors = SliderDefaults.colors(
                            thumbColor = PrimaryLime,
                            activeTrackColor = PrimaryLime,
                            inactiveTrackColor = BorderDim
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("0° Detached", color = TextDim, fontSize = 11.sp)
                        Text("${quickAngle.toInt()}° Rotation", color = PrimaryLime, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("45° Locked Click", color = SignalGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Complete Shoes Section
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("COMPLETE NATIVE SHOES", color = TextMain, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Pre-fitted with twist-lock ring & premium soles", color = TextMuted, fontSize = 12.sp)
                    }
                    TextButton(onClick = { onNavigateToTab(1) }) {
                        Text("View All", color = PrimaryLime, fontWeight = FontWeight.Bold)
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(MockData.completeShoes) { shoe ->
                        ShoeMiniCard(
                            shoe = shoe,
                            onAddToCart = {
                                viewModel.addToCart(
                                    CartItem(
                                        id = shoe.id,
                                        title = shoe.name,
                                        subtitle = "${shoe.tagline} | Size: 42",
                                        price = shoe.price,
                                        quantity = 1,
                                        imageRes = shoe.imageRes,
                                        size = "42",
                                        color = shoe.colors.firstOrNull(),
                                        itemType = "shoe"
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }

        // Modular Sole Editions Section
        item {
            Column(modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp)) {
                Text("REPLACEABLE SOLE EDITIONS", color = TextMain, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Keep the upper. Swap the sole according to your terrain.", color = TextMuted, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(12.dp))

                MockData.soleEditions.forEach { sole ->
                    SoleRowCard(
                        sole = sole,
                        onAddToCart = {
                            viewModel.addToCart(
                                CartItem(
                                    id = sole.id,
                                    title = sole.name,
                                    subtitle = "${sole.thickness} • ${sole.durometer}",
                                    price = sole.price,
                                    quantity = 1,
                                    imageRes = sole.imageRes,
                                    size = "42",
                                    color = sole.defaultColor,
                                    itemType = "sole"
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        // ReFit Universal Kit Feature
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = BgCard),
                border = BorderStroke(1.dp, BorderDim),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = SignalCyan.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "UNIVERSAL RETROFIT",
                                color = SignalCyan,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ReFit™ Adapter Kit", color = TextMain, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Turn your favorite Nike, Adidas, Puma, or Converse into a LoopStep twist-lock receiver. Includes calibrated PA66 plates and high-bond PU adhesive.",
                        color = TextMuted,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Only ₹50 / pair", color = PrimaryLime, fontSize = 18.sp, fontWeight = FontWeight.Black)
                        Button(
                            onClick = {
                                viewModel.addToCart(
                                    CartItem(
                                        id = MockData.refitAdapter.id,
                                        title = MockData.refitAdapter.name,
                                        subtitle = "Includes plates + adhesive",
                                        price = MockData.refitAdapter.price,
                                        quantity = 1,
                                        imageRes = MockData.refitAdapter.imageRes,
                                        itemType = "refit"
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SignalCyan, contentColor = OnPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Add ReFit Kit", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 1:1 Social Impact Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF142416),
                                BgSurface
                            )
                        )
                    )
                    .border(1.dp, SignalGreen.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(SignalGreen.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.VolunteerActivism, contentDescription = null, tint = SignalGreen)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text("1:1 Social Impact Model", color = SignalGreen, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "For every pair of LoopStep shoes purchased, we donate 1 spare replacement sole to local cobblers and repair micro-enterprises in underserved communities.",
                            color = TextMuted,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HighlightMetric(title: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = PrimaryLime, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Text(text = label, color = TextDim, fontSize = 10.sp)
    }
}

@Composable
private fun ShoeMiniCard(
    shoe: com.example.loopstep.data.CompleteShoe,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .testTag("shoe_card_${shoe.id}"),
        colors = CardDefaults.cardColors(containerColor = BgCard),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BorderDim)
    ) {
        Column {
            Box(modifier = Modifier.height(130.dp).fillMaxWidth()) {
                Image(
                    painter = painterResource(id = shoe.imageRes),
                    contentDescription = shoe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    color = BgDark.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(bottomStart = 8.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = "Save ₹${shoe.originalPrice - shoe.price}",
                        color = SignalGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(shoe.name, color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(shoe.category, color = TextDim, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("₹${shoe.price}", color = PrimaryLime, fontSize = 16.sp, fontWeight = FontWeight.Black)
                    Text("₹${shoe.originalPrice}", color = TextDim, fontSize = 12.sp, style = MaterialTheme.typography.bodySmall.copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onAddToCart,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLime, contentColor = OnPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(36.dp)
                ) {
                    Text("Add to Cart", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SoleRowCard(
    sole: com.example.loopstep.data.SoleEdition,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("sole_card_${sole.id}"),
        colors = CardDefaults.cardColors(containerColor = BgCard),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, BorderDim)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = sole.imageRes),
                contentDescription = sole.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(sole.name, color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text("${sole.thickness} • ${sole.durometer}", color = TextMuted, fontSize = 12.sp)
                Text(sole.intendedUse, color = TextDim, fontSize = 11.sp, maxLines = 1)
                Spacer(modifier = Modifier.height(4.dp))
                Text("₹${sole.price}", color = PrimaryLime, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onAddToCart,
                modifier = Modifier
                    .size(38.dp)
                    .background(PrimaryLime, CircleShape)
            ) {
                Icon(Icons.Filled.AddShoppingCart, contentDescription = "Add sole", tint = OnPrimary, modifier = Modifier.size(18.dp))
            }
        }
    }
}
