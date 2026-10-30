package com.example.loopstep.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loopstep.LoopStepViewModel
import com.example.loopstep.R
import com.example.loopstep.data.CartItem
import com.example.loopstep.data.MockData
import com.example.loopstep.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ScannerScreen(
    viewModel: LoopStepViewModel,
    onNavigateToStudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isScanning by viewModel.isScanning.collectAsState()
    val scanProgress by viewModel.scanProgress.collectAsState()
    val scanResult by viewModel.scanResult.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // Animated laser scan line
    val infiniteTransition = rememberInfiniteTransition(label = "laser_transition")
    val laserY by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_y"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
            .testTag("scanner_screen_column"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp)
    ) {
        item {
            Text(
                text = "BIOMETRIC FITMENT",
                color = PrimaryLime,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "3D Foot & Shoe Scanner",
                color = TextMain,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Align your foot within the measurement frame to calculate arch profile, optimal durometer, and exact EU sizing.",
                color = TextMuted,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Scanner Viewport Box
            Card(
                colors = CardDefaults.cardColors(containerColor = BgCard),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, if (isScanning) PrimaryLime else BorderDim),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Foot outline & grid
                    ScannerGridCanvas(isScanning = isScanning, laserY = if (isScanning) laserY else 0.5f)

                    // Scanner HUD overlay
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = BgDark.copy(alpha = 0.85f),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, if (isScanning) SignalGreen else BorderDim)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .clip(CircleShape)
                                            .background(if (isScanning) SignalGreen else SignalAmber)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isScanning) "SCANNING ACTIVE..." else if (scanResult != null) "ANALYSIS COMPLETE" else "READY FOR SCAN",
                                        color = if (isScanning) SignalGreen else TextMain,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Surface(
                                color = BgDark.copy(alpha = 0.85f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "3D MESH CALIPER v2.4",
                                    color = TextDim,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        if (isScanning) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(BgDark.copy(alpha = 0.85f))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = when {
                                        scanProgress < 0.35f -> "Calibrating baseline heel-to-toe markers..."
                                        scanProgress < 0.75f -> "Calculating longitudinal medial arch index..."
                                        else -> "Synthesizing dynamic pressure distribution..."
                                    },
                                    color = PrimaryLime,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                LinearProgressIndicator(
                                    progress = { scanProgress },
                                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                                    color = PrimaryLime,
                                    trackColor = BorderDim
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons to Start Scan
            if (!isScanning) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.startFootScan()
                                for (p in 1..10) {
                                    delay(200)
                                    viewModel.updateScanStep(p / 10f)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryLime, contentColor = OnPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(46.dp).testTag("start_scan_button")
                    ) {
                        Icon(Icons.Filled.CameraAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (scanResult == null) "Start 3D Foot Scan" else "Retake Scan", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            // Quick preset sample scan
                            viewModel.updateScanStep(1.0f)
                        },
                        border = BorderStroke(1.dp, BorderDim),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextMain),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(46.dp)
                    ) {
                        Text("Simulate")
                    }
                }
            }

            // Results Card
            if (scanResult != null) {
                val res = scanResult!!
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = BgSurface),
                    border = BorderStroke(1.dp, SignalGreen.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth().testTag("scan_results_card")
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = SignalGreen, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Biometric Assessment Complete", color = TextMain, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            MetricItem("Foot Length", "${res.footLengthMm} mm")
                            MetricItem("Forefoot Width", "${res.footWidthMm} mm")
                            MetricItem("Arch Type", "Medium")
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = BorderDim)
                        Spacer(modifier = Modifier.height(14.dp))

                        Text("RECOMMENDED LOOPSTEP PAIRING:", color = PrimaryLime, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("EU Size ${res.recommendedSize} • ${res.recommendedSole}", color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text("Recommended for natural heel-to-toe roll with shock attenuation.", color = TextMuted, fontSize = 12.sp)

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    viewModel.addToCart(
                                        CartItem(
                                            id = "recommended-drift-shoe",
                                            title = "LoopStep Drift (Biometric Fit)",
                                            subtitle = "Size: ${res.recommendedSize} | Sports Sole Fitted",
                                            price = MockData.completeShoes[1].price,
                                            quantity = 1,
                                            imageRes = MockData.completeShoes[1].imageRes,
                                            size = res.recommendedSize.toString(),
                                            color = MockData.completeShoes[1].colors.first(),
                                            itemType = "shoe"
                                        )
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryLime, contentColor = OnPrimary),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f).height(42.dp)
                            ) {
                                Text("Add Shoe (EU ${res.recommendedSize})", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = onNavigateToStudio,
                                colors = ButtonDefaults.buttonColors(containerColor = BgCard, contentColor = SignalCyan),
                                border = BorderStroke(1.dp, SignalCyan.copy(alpha = 0.5f)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.height(42.dp)
                            ) {
                                Text("Tune in Studio", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricItem(label: String, value: String) {
    Column {
        Text(label, color = TextDim, fontSize = 11.sp)
        Text(value, color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ScannerGridCanvas(isScanning: Boolean, laserY: Float) {
    Canvas(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        val w = size.width
        val h = size.height

        // Subtle background grid
        val step = 24.dp.toPx()
        var x = 0f
        while (x < w) {
            drawLine(color = Color(0x15FFFFFF), start = Offset(x, 0f), end = Offset(x, h), strokeWidth = 1f)
            x += step
        }
        var y = 0f
        while (y < h) {
            drawLine(color = Color(0x15FFFFFF), start = Offset(0f, y), end = Offset(w, y), strokeWidth = 1f)
            y += step
        }

        // Foot outline path
        val foot = Path().apply {
            moveTo(w * 0.5f, h * 0.12f)
            cubicTo(w * 0.65f, h * 0.12f, w * 0.72f, h * 0.28f, w * 0.68f, h * 0.45f)
            cubicTo(w * 0.66f, h * 0.55f, w * 0.62f, h * 0.65f, w * 0.65f, h * 0.85f)
            cubicTo(w * 0.62f, h * 0.92f, w * 0.38f, h * 0.92f, w * 0.35f, h * 0.85f)
            cubicTo(w * 0.38f, h * 0.65f, w * 0.32f, h * 0.55f, w * 0.30f, h * 0.35f)
            cubicTo(w * 0.28f, h * 0.22f, w * 0.38f, h * 0.12f, w * 0.5f, h * 0.12f)
            close()
        }

        drawPath(
            path = foot,
            color = if (isScanning) PrimaryLime.copy(alpha = 0.12f) else Color(0x0AFFFFFF)
        )
        drawPath(
            path = foot,
            color = if (isScanning) PrimaryLime else BorderDim,
            style = Stroke(width = 2.dp.toPx())
        )

        // Center crosshairs
        drawLine(color = BorderDim, start = Offset(w * 0.5f, h * 0.05f), end = Offset(w * 0.5f, h * 0.95f), strokeWidth = 1f)
        drawLine(color = BorderDim, start = Offset(w * 0.1f, h * 0.5f), end = Offset(w * 0.9f, h * 0.5f), strokeWidth = 1f)

        // Laser scan line
        if (isScanning) {
            val laserYPx = h * laserY
            drawLine(
                color = SignalGreen,
                start = Offset(w * 0.1f, laserYPx),
                end = Offset(w * 0.9f, laserYPx),
                strokeWidth = 3.dp.toPx()
            )
        }
    }
}
