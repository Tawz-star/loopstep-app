package com.example.loopstep.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loopstep.LoopStepViewModel
import com.example.loopstep.R
import com.example.loopstep.data.CartItem
import com.example.loopstep.data.CompleteShoe
import com.example.loopstep.data.MockData
import com.example.loopstep.data.SoleEdition
import com.example.loopstep.ui.theme.*

@Composable
fun ShopScreen(
    viewModel: LoopStepViewModel,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Complete Shoes", "Sole Editions", "ReFit Kit")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
            .testTag("shop_screen_column"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp)
    ) {
        item {
            Text(
                text = "LOOPSTEP CATALOG",
                color = PrimaryLime,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "Footwear & Modular Outsoles",
                color = TextMain,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Category Filter Chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryLime,
                            selectedLabelColor = OnPrimary,
                            containerColor = BgSurface,
                            labelColor = TextMuted
                        ),
                        border = BorderStroke(1.dp, if (isSelected) PrimaryLime else BorderDim),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Complete Shoes Section
        if (selectedCategory == "All" || selectedCategory == "Complete Shoes") {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.DirectionsRun, contentDescription = null, tint = PrimaryLime, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Complete Native Shoes", color = TextMain, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Text("Equipped with PA66-GF30 twist-lock ring and removable soles", color = TextDim, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(MockData.completeShoes) { shoe ->
                CompleteShoeCard(
                    shoe = shoe,
                    onAddToCart = { size, color ->
                        viewModel.addToCart(
                            CartItem(
                                id = shoe.id,
                                title = shoe.name,
                                subtitle = "Size: $size | $color",
                                price = shoe.price,
                                quantity = 1,
                                imageRes = shoe.imageRes,
                                size = size.toString(),
                                color = color,
                                itemType = "shoe"
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Sole Editions Section
        if (selectedCategory == "All" || selectedCategory == "Sole Editions") {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Layers, contentDescription = null, tint = SignalCyan, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Modular Replacement Soles", color = TextMain, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Text("Twist-off, twist-on. Fits both LoopStep shoes and ReFit retrofits.", color = TextDim, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(MockData.soleEditions) { sole ->
                SoleDetailCard(
                    sole = sole,
                    onAddToCart = { size ->
                        viewModel.addToCart(
                            CartItem(
                                id = sole.id,
                                title = sole.name,
                                subtitle = "${sole.thickness} • Shore ${sole.durometer} | Size: $size",
                                price = sole.price,
                                quantity = 1,
                                imageRes = sole.imageRes,
                                size = size.toString(),
                                color = sole.defaultColor,
                                itemType = "sole"
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // ReFit Kit Section
        if (selectedCategory == "All" || selectedCategory == "ReFit Kit") {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Build, contentDescription = null, tint = SignalAmber, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ReFit™ Universal Retrofit Kit", color = TextMain, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Text("Convert your existing sneakers into modular LoopStep shoes", color = TextDim, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(10.dp))

                ReFitKitCard(
                    refit = MockData.refitAdapter,
                    onAddToCart = {
                        viewModel.addToCart(
                            CartItem(
                                id = MockData.refitAdapter.id,
                                title = MockData.refitAdapter.name,
                                subtitle = "Universal 2mm adapter plates + PU adhesive",
                                price = MockData.refitAdapter.price,
                                quantity = 1,
                                imageRes = MockData.refitAdapter.imageRes,
                                itemType = "refit"
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun CompleteShoeCard(
    shoe: CompleteShoe,
    onAddToCart: (Int, String) -> Unit
) {
    var selectedSize by remember { mutableIntStateOf(shoe.sizes[4]) } // Default 42
    var selectedColor by remember { mutableStateOf(shoe.colors.first()) }
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = BgCard),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, BorderDim),
        modifier = Modifier.fillMaxWidth().testTag("shoe_card_${shoe.id}")
    ) {
        Column {
            Box(modifier = Modifier.height(200.dp).fillMaxWidth()) {
                Image(
                    painter = painterResource(id = shoe.imageRes),
                    contentDescription = shoe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    color = BgDark.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(bottomStart = 10.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = "Fitted with ${shoe.includedSole}",
                        color = PrimaryLime,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(shoe.name, color = TextMain, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(shoe.tagline, color = PrimaryLime, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("₹${shoe.price}", color = PrimaryLime, fontSize = 20.sp, fontWeight = FontWeight.Black)
                        Text("₹${shoe.originalPrice}", color = TextDim, fontSize = 12.sp, style = MaterialTheme.typography.bodySmall.copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(shoe.description, color = TextMuted, fontSize = 12.sp, lineHeight = 17.sp)

                Spacer(modifier = Modifier.height(12.dp))
                Text("Select Size (EU):", color = TextDim, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    items(shoe.sizes) { size ->
                        val isSel = size == selectedSize
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) PrimaryLime else BgSurface)
                                .border(1.dp, if (isSel) PrimaryLime else BorderDim, RoundedCornerShape(8.dp))
                                .clickable { selectedSize = size },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                size.toString(),
                                color = if (isSel) OnPrimary else TextMain,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("Colorways:", color = TextDim, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                    shoe.colors.forEach { colorName ->
                        val isSel = colorName == selectedColor
                        Surface(
                            color = if (isSel) BgSurface else BgDark,
                            border = BorderStroke(1.dp, if (isSel) PrimaryLime else BorderDim),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.clickable { selectedColor = colorName }
                        ) {
                            Text(
                                text = colorName,
                                color = if (isSel) PrimaryLime else TextMuted,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Expandable Technical Specs
                if (isExpanded) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = BgSurface,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            SpecRow("Weight", shoe.weight)
                            SpecRow("Heel Drop", shoe.drop)
                            SpecRow("Upper Material", shoe.upperMaterial)
                            SpecRow("Lifespan", shoe.lifespan)
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { isExpanded = !isExpanded }) {
                        Text(if (isExpanded) "Hide Specs" else "View Specs", color = TextMuted, fontSize = 12.sp)
                    }

                    Button(
                        onClick = { onAddToCart(selectedSize, selectedColor) },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryLime, contentColor = OnPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Filled.AddShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add Size $selectedSize", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SoleDetailCard(
    sole: SoleEdition,
    onAddToCart: (Int) -> Unit
) {
    var selectedSize by remember { mutableIntStateOf(42) }
    val sizes = listOf(38, 39, 40, 41, 42, 43, 44, 45, 46)

    Card(
        colors = CardDefaults.cardColors(containerColor = BgCard),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, BorderDim),
        modifier = Modifier.fillMaxWidth().testTag("sole_card_${sole.id}")
    ) {
        Column {
            Box(modifier = Modifier.height(180.dp).fillMaxWidth()) {
                Image(
                    painter = painterResource(id = sole.imageRes),
                    contentDescription = sole.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    color = BgDark.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(bottomStart = 10.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Filled.Star, contentDescription = null, tint = SignalAmber, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${sole.rating} (${sole.reviewsCount})", color = TextMain, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(sole.name, color = TextMain, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Text(sole.subtitle, color = TextDim, fontSize = 12.sp)
                    }
                    Text("₹${sole.price}", color = PrimaryLime, fontSize = 20.sp, fontWeight = FontWeight.Black)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BadgeChip(sole.thickness)
                    BadgeChip(sole.durometer)
                    BadgeChip(sole.weight)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(sole.treadPattern, color = SignalCyan, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Text(sole.cushioning, color = TextMuted, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(10.dp))
                Text("Select Size (EU):", color = TextDim, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    items(sizes) { size ->
                        val isSel = size == selectedSize
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) PrimaryLime else BgSurface)
                                .border(1.dp, if (isSel) PrimaryLime else BorderDim, RoundedCornerShape(8.dp))
                                .clickable { selectedSize = size },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                size.toString(),
                                color = if (isSel) OnPrimary else TextMain,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onAddToCart(selectedSize) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLime, contentColor = OnPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Icon(Icons.Filled.AddShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add Sole (Size $selectedSize) — ₹${sole.price}", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ReFitKitCard(
    refit: com.example.loopstep.data.ReFitAdapter,
    onAddToCart: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BgCard),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, SignalCyan.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth().testTag("refit_card")
    ) {
        Column {
            Box(modifier = Modifier.height(180.dp).fillMaxWidth()) {
                Image(
                    painter = painterResource(id = refit.imageRes),
                    contentDescription = refit.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    color = BgDark.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(bottomStart = 10.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        "Universal Retrofit",
                        color = SignalCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(refit.name, color = TextMain, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("₹${refit.price}", color = PrimaryLime, fontSize = 20.sp, fontWeight = FontWeight.Black)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(refit.description, color = TextMuted, fontSize = 12.sp, lineHeight = 16.sp)

                Spacer(modifier = Modifier.height(10.dp))
                Text("Kit Contents:", color = TextDim, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                refit.contents.forEach { item ->
                    Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(SignalCyan))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(item, color = TextMain, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onAddToCart,
                    colors = ButtonDefaults.buttonColors(containerColor = SignalCyan, contentColor = OnPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Icon(Icons.Filled.AddShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add ReFit Universal Kit — ₹${refit.price}", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun BadgeChip(text: String) {
    Surface(
        color = BgSurface,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, BorderDim)
    ) {
        Text(text, color = TextMain, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextDim, fontSize = 11.sp)
        Text(value, color = TextMain, fontSize = 11.sp, fontWeight = FontWeight.Medium)
    }
}
