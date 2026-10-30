package com.example.loopstep.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.loopstep.LoopStepViewModel
import com.example.loopstep.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartModal(
    viewModel: LoopStepViewModel,
    isOpen: Boolean,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    val cartItems by viewModel.cartItems.collectAsState()
    val discountApplied by viewModel.soleReturnDiscountApplied.collectAsState()
    var isCheckingOut by remember { mutableStateOf(false) }

    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var customerAddress by remember { mutableStateOf("") }
    var selectedPayment by remember { mutableStateOf("UPI (GPay / PhonePe)") }

    val rawTotal = cartItems.sumOf { it.price * it.quantity }
    val discountAmount = if (discountApplied) (rawTotal * 0.10).toInt() else 0
    val finalTotal = (rawTotal - discountAmount).coerceAtLeast(0)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BgSurface,
        scrimColor = BgDark.copy(alpha = 0.85f),
        dragHandle = { BottomSheetDefaults.DragHandle(color = BorderDim) },
        modifier = Modifier.testTag("cart_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .fillMaxHeight(0.85f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = null, tint = PrimaryLime)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (isCheckingOut) "Checkout & Delivery" else "Your Cart (${cartItems.sumOf { it.quantity }})",
                        color = TextMain,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (!isCheckingOut && cartItems.isNotEmpty()) {
                    TextButton(onClick = { viewModel.clearCart() }) {
                        Text("Clear", color = SignalRed, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.RemoveShoppingCart, contentDescription = null, tint = TextDim, modifier = Modifier.size(54.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Your LoopStep cart is empty", color = TextMuted, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text("Add shoes, replaceable soles, or a ReFit kit to begin.", color = TextDim, fontSize = 12.sp)
                    }
                }
            } else if (!isCheckingOut) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(cartItems) { index, item ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = BgCard),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, BorderDim),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = item.imageRes),
                                    contentDescription = item.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.title, color = TextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text(item.subtitle, color = TextMuted, fontSize = 11.sp)
                                    Text("₹${item.price} each", color = PrimaryLime, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = { viewModel.updateCartQuantity(index, -1) },
                                        modifier = Modifier.size(28.dp).background(BgSurface, CircleShape)
                                    ) {
                                        Icon(Icons.Filled.Remove, contentDescription = "Decrease", tint = TextMain, modifier = Modifier.size(14.dp))
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("${item.quantity}", color = TextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(
                                        onClick = { viewModel.updateCartQuantity(index, 1) },
                                        modifier = Modifier.size(28.dp).background(BgSurface, CircleShape)
                                    ) {
                                        Icon(Icons.Filled.Add, contentDescription = "Increase", tint = TextMain, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }

                    // Sole Return Exchange 10% Discount Toggle
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = if (discountApplied) Color(0xFF142416) else BgCard),
                            border = BorderStroke(1.dp, if (discountApplied) SignalGreen else BorderDim),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Filled.Recycling, contentDescription = null, tint = SignalGreen, modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Sole Exchange Program", color = TextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text("Returning your old worn sole for circular shredding? Save 10%.", color = TextMuted, fontSize = 11.sp)
                                }
                                Switch(
                                    checked = discountApplied,
                                    onCheckedChange = { viewModel.toggleSoleReturnDiscount() },
                                    colors = SwitchDefaults.colors(checkedThumbColor = SignalGreen, checkedTrackColor = SignalGreen.copy(alpha = 0.3f))
                                )
                            }
                        }
                    }
                }

                // Subtotal & Proceed Bar
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = BgCard),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, BorderDim),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal", color = TextDim, fontSize = 12.sp)
                            Text("₹$rawTotal", color = TextMain, fontSize = 12.sp)
                        }
                        if (discountApplied) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Sole Return Discount (-10%)", color = SignalGreen, fontSize = 12.sp)
                                Text("-₹$discountAmount", color = SignalGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Circular Eco-Logistics", color = TextDim, fontSize = 12.sp)
                            Text("FREE", color = SignalGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        HorizontalDivider(color = BorderDim)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Total", color = TextMain, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("₹$finalTotal", color = PrimaryLime, fontSize = 20.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { isCheckingOut = true },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLime, contentColor = OnPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(46.dp).testTag("proceed_checkout_button")
                ) {
                    Text("Proceed to Checkout (₹$finalTotal)", fontWeight = FontWeight.Bold)
                }
            } else {
                // Checkout Form
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("Full Name") },
                            placeholder = { Text("e.g. Alex Rivera") },
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryLime, unfocusedBorderColor = BorderDim, focusedTextColor = TextMain, unfocusedTextColor = TextMain),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = customerPhone,
                            onValueChange = { customerPhone = it },
                            label = { Text("Contact Phone / Email") },
                            placeholder = { Text("+91 98765 43210") },
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryLime, unfocusedBorderColor = BorderDim, focusedTextColor = TextMain, unfocusedTextColor = TextMain),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = customerAddress,
                            onValueChange = { customerAddress = it },
                            label = { Text("Shipping Address & Pincode") },
                            placeholder = { Text("Street, City, Pincode") },
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryLime, unfocusedBorderColor = BorderDim, focusedTextColor = TextMain, unfocusedTextColor = TextMain),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        Text("Select Payment Gateway:", color = TextDim, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        listOf("UPI (GPay / PhonePe)", "Credit / Debit Card", "Cash on Delivery", "Eco-Wallet Points").forEach { method ->
                            val isSel = selectedPayment == method
                            Surface(
                                color = if (isSel) PrimaryLime.copy(alpha = 0.15f) else BgCard,
                                border = BorderStroke(1.dp, if (isSel) PrimaryLime else BorderDim),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSel,
                                        onClick = { selectedPayment = method },
                                        colors = RadioButtonDefaults.colors(selectedColor = PrimaryLime)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(method, color = TextMain, fontSize = 13.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { isCheckingOut = false },
                        border = BorderStroke(1.dp, BorderDim),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextMain),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(46.dp)
                    ) {
                        Text("Back to Cart")
                    }

                    Button(
                        onClick = {
                            viewModel.placeOrder(customerName, customerAddress)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryLime, contentColor = OnPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(46.dp).testTag("confirm_order_button")
                    ) {
                        Text("Confirm Order — ₹$finalTotal", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderConfirmationDialog(
    confirmation: com.example.loopstep.OrderConfirmation?,
    onDismiss: () -> Unit
) {
    if (confirmation == null) return

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BgSurface),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, SignalGreen),
            modifier = Modifier.fillMaxWidth().padding(16.dp).testTag("order_confirmation_dialog")
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(SignalGreen.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Verified, contentDescription = null, tint = SignalGreen, modifier = Modifier.size(36.dp))
                }

                Spacer(modifier = Modifier.height(14.dp))
                Text("Order Placed Successfully!", color = TextMain, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Order Reference: ${confirmation.orderId}", color = PrimaryLime, fontSize = 13.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Thank you, ${confirmation.customerName}! Your LoopStep order is confirmed. A prepaid return sleeve is included in your parcel for the Sole Exchange program.",
                    color = TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    color = Color(0xFF142416),
                    border = BorderStroke(1.dp, SignalGreen.copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.VolunteerActivism, contentDescription = null, tint = SignalGreen, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "${confirmation.solesDonated} spare replacement sole will be donated to community cobblers under our 1:1 initiative.",
                            color = SignalGreen,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLime, contentColor = OnPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Text("Continue Exploring", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
