package com.example.loopstep.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loopstep.CustomSoleState
import com.example.loopstep.LoopStepViewModel
import com.example.loopstep.R
import com.example.loopstep.data.CartItem
import com.example.loopstep.ui.theme.*

@Composable
fun SoleStudioScreen(
    viewModel: LoopStepViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.customSoleState.collectAsState()
    val availableColors = listOf("#C6F135", "#00F0FF", "#E7A93B", "#F43F5E", "#F8FAFC", "#2C312E")
    val treadPatterns = listOf("Wave Flex", "Hex Lug", "Geometric Trail")
    val sizes = (38..46).toList()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
            .testTag("sole_studio_screen_column"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp)
    ) {
        item {
            Text(
                text = "ENGINEERING LAB",
                color = PrimaryLime,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "Custom Sole Studio",
                color = TextMain,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Calibrate outsole thickness, durometer hardness, and tread topology for your specific terrain.",
                color = TextMuted,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Interactive Sole Topology Canvas Preview
            Card(
                colors = CardDefaults.cardColors(containerColor = BgCard),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, PrimaryLime.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    SoleCanvas(state = state, modifier = Modifier.fillMaxSize().padding(16.dp))

                    Surface(
                        color = BgDark.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "${state.baseType} Topology • ${state.thicknessMm}mm • ${state.durometer}A",
                            color = PrimaryLime,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        color = BgDark.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "PA66-GF30 Ring 45°",
                            color = SignalCyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Base Edition Selector
            Text("1. Base Sole Geometry:", color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                listOf("Standard", "Sports", "Trail").forEach { type ->
                    val isSel = state.baseType == type
                    Surface(
                        color = if (isSel) PrimaryLime else BgSurface,
                        border = BorderStroke(1.dp, if (isSel) PrimaryLime else BorderDim),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.updateCustomSole { it.copy(baseType = type) } }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = type,
                                color = if (isSel) OnPrimary else TextMain,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = when (type) {
                                    "Standard" -> "City / Flat"
                                    "Sports" -> "Dynamic Cut"
                                    else -> "Rugged Rock"
                                },
                                color = if (isSel) OnPrimary.copy(alpha = 0.8f) else TextDim,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Thickness Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("2. Sole Stack Thickness:", color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("${state.thicknessMm} mm", color = PrimaryLime, fontSize = 15.sp, fontWeight = FontWeight.Black)
            }
            Slider(
                value = state.thicknessMm.toFloat(),
                onValueChange = { viewModel.updateCustomSole { s -> s.copy(thicknessMm = it.toInt()) } },
                valueRange = 8f..18f,
                steps = 9,
                colors = SliderDefaults.colors(thumbColor = PrimaryLime, activeTrackColor = PrimaryLime, inactiveTrackColor = BorderDim)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("8mm (Ultralight)", color = TextDim, fontSize = 10.sp)
                Text("13mm (Balanced)", color = TextDim, fontSize = 10.sp)
                Text("18mm (Max Cushion)", color = TextDim, fontSize = 10.sp)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Durometer Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("3. Shore A Hardness:", color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("${state.durometer} Shore A", color = SignalCyan, fontSize = 15.sp, fontWeight = FontWeight.Black)
            }
            Slider(
                value = state.durometer.toFloat(),
                onValueChange = { viewModel.updateCustomSole { s -> s.copy(durometer = it.toInt()) } },
                valueRange = 45f..75f,
                steps = 5,
                colors = SliderDefaults.colors(thumbColor = SignalCyan, activeTrackColor = SignalCyan, inactiveTrackColor = BorderDim)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("45A (Plush & Soft)", color = TextDim, fontSize = 10.sp)
                Text("60A (Responsive)", color = TextDim, fontSize = 10.sp)
                Text("75A (Rigid & Resilient)", color = TextDim, fontSize = 10.sp)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Tread Pattern Selector
            Text("4. Tread Topology Pattern:", color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                treadPatterns.forEach { pattern ->
                    val isSel = state.treadPattern == pattern
                    Surface(
                        color = if (isSel) SignalCyan.copy(alpha = 0.2f) else BgSurface,
                        border = BorderStroke(1.dp, if (isSel) SignalCyan else BorderDim),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { viewModel.updateCustomSole { it.copy(treadPattern = pattern) } }
                    ) {
                        Text(
                            text = pattern,
                            color = if (isSel) SignalCyan else TextMain,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Traction Pod Accent Color
            Text("5. Traction Pod Accent Color:", color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                availableColors.forEach { hex ->
                    val colorObj = try {
                        Color(android.graphics.Color.parseColor(hex))
                    } catch (e: Exception) {
                        PrimaryLime
                    }
                    val isSel = state.accentColor.equals(hex, ignoreCase = true)
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(colorObj)
                            .border(3.dp, if (isSel) Color.White else BorderDim, CircleShape)
                            .clickable { viewModel.updateCustomSole { it.copy(accentColor = hex) } },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSel) {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = if (hex == "#F8FAFC" || hex == "#C6F135") OnPrimary else Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Size Selector
            Text("6. Shoe Size (EU):", color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(sizes) { sz ->
                    val isSel = state.size == sz
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSel) PrimaryLime else BgSurface)
                            .border(1.dp, if (isSel) PrimaryLime else BorderDim, RoundedCornerShape(8.dp))
                            .clickable { viewModel.updateCustomSole { it.copy(size = sz) } },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            sz.toString(),
                            color = if (isSel) OnPrimary else TextMain,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Live Price & Weight Bar with Add to Cart Button
            Card(
                colors = CardDefaults.cardColors(containerColor = BgSurface),
                border = BorderStroke(1.dp, BorderDim),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("CUSTOM ENGINEERED OUTSOLE", color = TextDim, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text("Size ${state.size} • ~${state.weightGrams}g Weight", color = TextMain, fontSize = 13.sp)
                        }
                        Text("₹${state.price}", color = PrimaryLime, fontSize = 24.sp, fontWeight = FontWeight.Black)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            viewModel.addToCart(
                                CartItem(
                                    id = "custom-${System.currentTimeMillis()}",
                                    title = "Custom ${state.baseType} Sole (EU ${state.size})",
                                    subtitle = "${state.thicknessMm}mm • ${state.durometer}A • ${state.treadPattern}",
                                    price = state.price,
                                    quantity = 1,
                                    imageRes = when (state.baseType) {
                                        "Standard" -> R.drawable.sole_standard
                                        "Trail" -> R.drawable.sole_trail
                                        else -> R.drawable.sole_sports
                                    },
                                    size = state.size.toString(),
                                    color = state.accentColor,
                                    itemType = "custom_sole"
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryLime, contentColor = OnPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("add_custom_sole_button")
                    ) {
                        Icon(Icons.Filled.AddShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add Custom Sole to Cart — ₹${state.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun SoleCanvas(state: CustomSoleState, modifier: Modifier = Modifier) {
    val accentColor = remember(state.accentColor) {
        try {
            Color(android.graphics.Color.parseColor(state.accentColor))
        } catch (e: Exception) {
            PrimaryLime
        }
    }

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Outer sole silhouette
        val solePath = Path().apply {
            moveTo(w * 0.2f, h * 0.15f)
            quadraticTo(w * 0.5f, h * 0.08f, w * 0.8f, h * 0.15f)
            quadraticTo(w * 0.95f, h * 0.35f, w * 0.88f, h * 0.55f)
            quadraticTo(w * 0.78f, h * 0.65f, w * 0.82f, h * 0.85f)
            quadraticTo(w * 0.5f, h * 0.95f, w * 0.18f, h * 0.85f)
            quadraticTo(w * 0.22f, h * 0.65f, w * 0.12f, h * 0.55f)
            quadraticTo(w * 0.05f, h * 0.35f, w * 0.2f, h * 0.15f)
            close()
        }

        // Draw sole base fill
        drawPath(
            path = solePath,
            color = Color(0xFF141A22)
        )
        drawPath(
            path = solePath,
            color = BorderDim,
            style = Stroke(width = 3.dp.toPx())
        )

        // Draw Tread pattern based on selected pattern
        when (state.treadPattern) {
            "Wave Flex" -> {
                for (i in 1..5) {
                    val y = h * (0.2f + i * 0.12f)
                    val wavePath = Path().apply {
                        moveTo(w * 0.25f, y)
                        quadraticTo(w * 0.4f, y - 10f, w * 0.5f, y)
                        quadraticTo(w * 0.6f, y + 10f, w * 0.75f, y)
                    }
                    drawPath(wavePath, color = accentColor.copy(alpha = 0.7f), style = Stroke(width = 4.dp.toPx()))
                }
            }
            "Hex Lug" -> {
                val hexCols = 3
                val hexRows = 4
                for (r in 0 until hexRows) {
                    for (c in 0 until hexCols) {
                        val cx = w * (0.35f + c * 0.15f)
                        val cy = h * (0.22f + r * 0.16f)
                        drawCircle(
                            color = accentColor.copy(alpha = 0.8f),
                            radius = 12f,
                            center = Offset(cx, cy),
                            style = Stroke(width = 3.dp.toPx())
                        )
                    }
                }
            }
            else -> { // Geometric Trail
                for (i in 0..4) {
                    val y = h * (0.2f + i * 0.14f)
                    drawRoundRect(
                        color = accentColor.copy(alpha = 0.85f),
                        topLeft = Offset(w * 0.28f, y),
                        size = Size(w * 0.44f, 16f),
                        cornerRadius = CornerRadius(4f, 4f)
                    )
                }
            }
        }

        // Center PA66-GF30 Twist-Lock Mechanism Ring
        val ringCenter = Offset(w * 0.5f, h * 0.55f)
        drawCircle(
            color = Color(0xFF0C1014),
            radius = 38f,
            center = ringCenter
        )
        drawCircle(
            color = PrimaryLime,
            radius = 38f,
            center = ringCenter,
            style = Stroke(width = 4.dp.toPx())
        )
        // Locking retention teeth lugs
        for (a in 0 until 4) {
            val angleRad = (a * 90.0) * (Math.PI / 180.0)
            val lx = ringCenter.x + (Math.cos(angleRad) * 44f).toFloat()
            val ly = ringCenter.y + (Math.sin(angleRad) * 44f).toFloat()
            drawCircle(color = SignalCyan, radius = 6f, center = Offset(lx, ly))
        }
        drawCircle(
            color = Color.White,
            radius = 12f,
            center = ringCenter
        )
    }
}
