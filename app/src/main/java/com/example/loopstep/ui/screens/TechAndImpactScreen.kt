package com.example.loopstep.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loopstep.LoopStepViewModel
import com.example.loopstep.R
import com.example.loopstep.data.MockData
import com.example.loopstep.ui.theme.*

@Composable
fun TechAndImpactScreen(
    viewModel: LoopStepViewModel,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf("Mechanism") }
    val sections = listOf("Mechanism", "Sustainability", "Materials", "ReFit Guide", "Economics", "FAQ")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
            .testTag("tech_impact_screen_column"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp)
    ) {
        item {
            Text(
                text = "TECHNOLOGY & CIRCULAR IMPACT",
                color = PrimaryLime,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "Engineering & Science",
                color = TextMain,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Sub-navigation tabs
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(sections) { sec ->
                    val isSel = selectedSection == sec
                    FilterChip(
                        selected = isSel,
                        onClick = { selectedSection = sec },
                        label = { Text(sec, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryLime,
                            selectedLabelColor = OnPrimary,
                            containerColor = BgSurface,
                            labelColor = TextMuted
                        ),
                        border = BorderStroke(1.dp, if (isSel) PrimaryLime else BorderDim),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        when (selectedSection) {
            "Mechanism" -> {
                item { TwistLockMechanismSection(viewModel) }
            }
            "Sustainability" -> {
                item { SustainabilitySection(viewModel) }
            }
            "Materials" -> {
                item { MaterialsSection() }
            }
            "ReFit Guide" -> {
                item { ReFitGuideSection() }
            }
            "Economics" -> {
                item { EconomicsSection() }
            }
            "FAQ" -> {
                item { FAQSection() }
            }
        }
    }
}

@Composable
private fun TwistLockMechanismSection(viewModel: LoopStepViewModel) {
    var angle by remember { mutableFloatStateOf(0f) }
    val animatedAngle by animateFloatAsState(targetValue = angle, label = "mechanism_twist")
    val isLocked = angle >= 42f

    Column {
        Card(
            colors = CardDefaults.cardColors(containerColor = BgCard),
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, if (isLocked) SignalGreen else BorderDim),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("45° ROTATION BAYONET LOCK", color = PrimaryLime, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = if (isLocked) "POSITIVELY LOCKED (Spring Detent)" else "ROTATING / DISENGAGED",
                            color = if (isLocked) SignalGreen else SignalAmber,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(BgSurface)
                            .border(2.dp, if (isLocked) SignalGreen else PrimaryLime, CircleShape)
                            .clickable { angle = if (isLocked) 0f else 45f },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Autorenew,
                            contentDescription = "Twist mechanism",
                            tint = if (isLocked) SignalGreen else PrimaryLime,
                            modifier = Modifier.size(38.dp).rotate(animatedAngle)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "A quarter of an eighth rotation (45°) aligns the four precision bayonet lugs into the PA66-GF30 midsole ring. A dual tactile sprung detent secures the lock, preventing back-rotation during sprint stops or lateral cuts.",
                    color = TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(10.dp))
                Slider(
                    value = angle,
                    onValueChange = { angle = it },
                    valueRange = 0f..45f,
                    colors = SliderDefaults.colors(thumbColor = PrimaryLime, activeTrackColor = PrimaryLime, inactiveTrackColor = BorderDim)
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("0° Detached", color = TextDim, fontSize = 11.sp)
                    Text("${angle.toInt()}° Rotation", color = PrimaryLime, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("45° Locked Click", color = SignalGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Engineering Bench Data Table
        Text("ENGINEERING SPECIFICATIONS (PA66-GF30)", color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = BgSurface),
            border = BorderStroke(1.dp, BorderDim),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                TechSpecRow("Tensile Strength", "170 – 200 MPa")
                TechSpecRow("Compressive Strength", "180 – 250 MPa")
                TechSpecRow("Flexural Strength", "220 – 280 MPa")
                TechSpecRow("Tested Safe Load", "~7,000 N (700+ kg force)")
                TechSpecRow("Operating Temperature", "-40°C to 120°C")
                TechSpecRow("Swap Operation Time", "Under 10 seconds")
                TechSpecRow("Mechanical Cycle Life", "10,000+ lock/unlock cycles")
            }
        }
    }
}

@Composable
private fun SustainabilitySection(viewModel: LoopStepViewModel) {
    val impactYears by viewModel.impactYears.collectAsState()

    val shoesAvoided = impactYears * 2
    val co2SavedKg = (shoesAvoided * (14.5 - 2.6)).toInt()
    val wastePreventedKg = (shoesAvoided * (0.85 - 0.22)).toInt()
    val waterSavedLitres = shoesAvoided * 1200
    val moneySavedInr = shoesAvoided * 2300

    Column {
        Card(
            colors = CardDefaults.cardColors(containerColor = BgCard),
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.dp, SignalGreen.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("CIRCULAR IMPACT CALCULATOR", color = SignalGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("$impactYears Years of LoopStep Wear", color = TextMain, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Text("$shoesAvoided Pairs Saved", color = PrimaryLime, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Slider(
                    value = impactYears.toFloat(),
                    onValueChange = { viewModel.setImpactYears(it.toInt()) },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(thumbColor = SignalGreen, activeTrackColor = SignalGreen, inactiveTrackColor = BorderDim),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    ImpactBox("CO₂ Avoided", "$co2SavedKg kg", SignalCyan)
                    ImpactBox("Landfill Saved", "$wastePreventedKg kg", SignalGreen)
                    ImpactBox("Water Saved", "$waterSavedLitres L", PrimaryLime)
                    ImpactBox("Money Saved", "₹$moneySavedInr", SignalAmber)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // UN Sustainable Development Goals Focus
        Text("UN SUSTAINABLE DEVELOPMENT GOALS", color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        MockData.sdgList.forEach { sdg ->
            Card(
                colors = CardDefaults.cardColors(containerColor = BgSurface),
                border = BorderStroke(1.dp, BorderDim),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(sdg.code, color = PrimaryLime, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(sdg.title, color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(sdg.desc, color = TextMuted, fontSize = 12.sp, lineHeight = 16.sp)
                }
            }
        }
    }
}

@Composable
private fun MaterialsSection() {
    Column {
        Text("RESPONSIBLE MATERIALS ENGINEERING", color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Text("Every component is chosen for durability, circularity, and performance.", color = TextDim, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(10.dp))

        MockData.materials.forEach { mat ->
            Card(
                colors = CardDefaults.cardColors(containerColor = BgSurface),
                border = BorderStroke(1.dp, BorderDim),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(mat.name, color = PrimaryLime, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(mat.purpose, color = TextMuted, fontSize = 12.sp, lineHeight = 16.sp)
                }
            }
        }
    }
}

@Composable
private fun ReFitGuideSection() {
    var searchQuery by remember { mutableStateOf("") }
    val filteredBrands = remember(searchQuery) {
        MockData.brandCompatibilityList.filter {
            it.brand.contains(searchQuery, ignoreCase = true) || it.models.any { m -> m.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column {
        Text("ReFit™ 4-STEP DIY CONVERSION", color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        val steps = listOf(
            "1. Sand & Clean" to "Buff the worn sole contact area with included abrasive pad to remove surface oxidation.",
            "2. Apply PU Adhesive" to "Evenly coat the PA66 adapter plate and shoe base with industrial polyurethane adhesive.",
            "3. Align & Clamp" to "Seat the universal alignment stencil firmly into the heel-toe axis for 10 minutes.",
            "4. Full 12hr Cure" to "Allow full 12-hour chemical cross-linking. After cure, your shoe accepts any LoopStep sole."
        )

        steps.forEach { (title, desc) ->
            Card(
                colors = CardDefaults.cardColors(containerColor = BgSurface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderDim),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(title, color = SignalCyan, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(desc, color = TextMuted, fontSize = 11.sp, lineHeight = 15.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("BRAND COMPATIBILITY CHECKER", color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search brand (e.g. Nike, Adidas, Puma)...", color = TextDim, fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = PrimaryLime) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryLime,
                unfocusedBorderColor = BorderDim,
                focusedTextColor = TextMain,
                unfocusedTextColor = TextMain
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        filteredBrands.forEach { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = BgCard),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderDim),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.brand, color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Surface(
                            color = if (item.status.contains("Verified")) SignalGreen.copy(alpha = 0.15f) else SignalAmber.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                item.status,
                                color = if (item.status.contains("Verified")) SignalGreen else SignalAmber,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text("Verified models: ${item.models.joinToString(", ")}", color = TextMuted, fontSize = 11.sp)
                    Text("Adapter: ${item.adapterNeeded}", color = SignalCyan, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun EconomicsSection() {
    Column {
        Text("UNIT ECONOMICS & BUSINESS MODEL", color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Text("Healthy 25.3% net margin on complete shoes, and 64-66% margin on sole refills.", color = TextDim, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = BgCard),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderDim),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Complete Shoe Unit Economics", color = PrimaryLime, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                TechSpecRow("Retail Selling Price", "₹3,000")
                TechSpecRow("Manufacturing Cost (BOM)", "₹1,740 (58%)")
                TechSpecRow("Operating Expenses (Logistics & R&D)", "₹500 (16.7%)")
                TechSpecRow("Total Cost", "₹2,240")
                TechSpecRow("Net Profit Per Pair", "₹760 (25.3% Net Margin)")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = BgSurface),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderDim),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Replacement Sole Margins", color = SignalCyan, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                TechSpecRow("Standard Sole (₹200)", "Mfg: ₹70 • Profit: ₹130 (65%)")
                TechSpecRow("Sports Sole (₹500)", "Mfg: ₹170 • Profit: ₹330 (66%)")
                TechSpecRow("Trail Sole (₹700)", "Mfg: ₹250 • Profit: ₹450 (64%)")
                TechSpecRow("ReFit Adapter Kit (₹50)", "Mfg: ₹35 • Profit: ₹15 (30%)")
            }
        }
    }
}

@Composable
private fun FAQSection() {
    Column {
        Text("FREQUENTLY ASKED QUESTIONS", color = TextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        MockData.faqList.forEach { faq ->
            var expanded by remember { mutableStateOf(false) }
            Card(
                colors = CardDefaults.cardColors(containerColor = BgSurface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, if (expanded) PrimaryLime.copy(alpha = 0.5f) else BorderDim),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { expanded = !expanded }
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(faq.question, color = TextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = PrimaryLime,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    AnimatedVisibility(visible = expanded) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(faq.answer, color = TextMuted, fontSize = 12.sp, lineHeight = 17.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TechSpecRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextDim, fontSize = 12.sp)
        Text(value, color = TextMain, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ImpactBox(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontSize = 15.sp, fontWeight = FontWeight.Black)
        Text(label, color = TextDim, fontSize = 10.sp)
    }
}
