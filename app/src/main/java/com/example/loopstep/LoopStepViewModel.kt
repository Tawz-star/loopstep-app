package com.example.loopstep

import androidx.lifecycle.ViewModel
import com.example.loopstep.data.CartItem
import com.example.loopstep.data.MockData
import com.example.loopstep.data.ScanResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class CustomSoleState(
    val baseType: String = "Sports",
    val thicknessMm: Int = 12,
    val durometer: Int = 60,
    val treadPattern: String = "Hex Lug",
    val accentColor: String = "#C6F135",
    val size: Int = 42
) {
    val price: Int
        get() {
            val base = when (baseType) {
                "Standard" -> 200
                "Sports" -> 500
                "Trail" -> 700
                else -> 400
            }
            val thicknessExtra = (thicknessMm - 8).coerceAtLeast(0) * 15
            val treadExtra = if (treadPattern == "Geometric Trail") 60 else 0
            return base + thicknessExtra + treadExtra
        }

    val weightGrams: Int
        get() = 120 + (thicknessMm * 6) + if (baseType == "Trail") 40 else 20
}

data class OrderConfirmation(
    val orderId: String,
    val customerName: String,
    val totalAmount: Int,
    val itemsCount: Int,
    val solesDonated: Int
)

class LoopStepViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _soleReturnDiscountApplied = MutableStateFlow(false)
    val soleReturnDiscountApplied: StateFlow<Boolean> = _soleReturnDiscountApplied.asStateFlow()

    private val _customSoleState = MutableStateFlow(CustomSoleState())
    val customSoleState: StateFlow<CustomSoleState> = _customSoleState.asStateFlow()

    private val _scanProgress = MutableStateFlow(0f)
    val scanProgress: StateFlow<Float> = _scanProgress.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _scanResult = MutableStateFlow<ScanResult?>(null)
    val scanResult: StateFlow<ScanResult?> = _scanResult.asStateFlow()

    private val _twistAngle = MutableStateFlow(0f)
    val twistAngle: StateFlow<Float> = _twistAngle.asStateFlow()

    private val _impactYears = MutableStateFlow(3)
    val impactYears: StateFlow<Int> = _impactYears.asStateFlow()

    private val _lastOrderConfirmation = MutableStateFlow<OrderConfirmation?>(null)
    val lastOrderConfirmation: StateFlow<OrderConfirmation?> = _lastOrderConfirmation.asStateFlow()

    init {
        // Pre-populate cart with 1 initial sample item so user immediately sees rich cart experience
        addToCart(
            CartItem(
                id = "init-sports-sole",
                title = "Sports Edition Replacement Sole",
                subtitle = "Size: 42 | Black / Lime",
                price = 500,
                quantity = 1,
                imageRes = MockData.soleEditions[1].imageRes,
                size = "42",
                color = "#C6F135",
                itemType = "sole"
            )
        )
    }

    fun addToCart(item: CartItem) {
        _cartItems.update { current ->
            val existingIndex = current.indexOfFirst { it.id == item.id && it.size == item.size && it.color == item.color }
            if (existingIndex >= 0) {
                current.mapIndexed { index, cartItem ->
                    if (index == existingIndex) cartItem.copy(quantity = cartItem.quantity + item.quantity)
                    else cartItem
                }
            } else {
                current + item
            }
        }
    }

    fun updateCartQuantity(index: Int, change: Int) {
        _cartItems.update { current ->
            if (index in current.indices) {
                val newQty = current[index].quantity + change
                if (newQty <= 0) {
                    current.filterIndexed { i, _ -> i != index }
                } else {
                    current.mapIndexed { i, item ->
                        if (i == index) item.copy(quantity = newQty) else item
                    }
                }
            } else current
        }
    }

    fun removeCartItem(index: Int) {
        _cartItems.update { current ->
            current.filterIndexed { i, _ -> i != index }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun toggleSoleReturnDiscount() {
        _soleReturnDiscountApplied.update { !it }
    }

    fun updateCustomSole(transform: (CustomSoleState) -> CustomSoleState) {
        _customSoleState.update(transform)
    }

    fun setTwistAngle(angle: Float) {
        _twistAngle.value = angle.coerceIn(0f, 45f)
    }

    fun setImpactYears(years: Int) {
        _impactYears.value = years.coerceIn(1, 10)
    }

    fun startFootScan(onComplete: () -> Unit = {}) {
        _isScanning.value = true
        _scanProgress.value = 0f
        _scanResult.value = null
    }

    fun updateScanStep(progress: Float) {
        _scanProgress.value = progress
        if (progress >= 1.0f) {
            _isScanning.value = false
            _scanResult.value = ScanResult(
                footLengthMm = 268,
                footWidthMm = 98,
                archType = "Medium Longitudinal Arch",
                recommendedSize = 42,
                recommendedSole = "Sports Edition (12mm, 60 Shore A)",
                durometer = "60 Shore A"
            )
        }
    }

    fun placeOrder(name: String, address: String): OrderConfirmation {
        val rawTotal = _cartItems.value.sumOf { it.price * it.quantity }
        val discount = if (_soleReturnDiscountApplied.value) (rawTotal * 0.10).toInt() else 0
        val finalTotal = rawTotal - discount
        val solesCount = _cartItems.value.filter { it.itemType.contains("sole") }.sumOf { it.quantity }
        val shoesCount = _cartItems.value.filter { it.itemType == "shoe" }.sumOf { it.quantity }
        val donationCount = shoesCount.coerceAtLeast(1)

        val conf = OrderConfirmation(
            orderId = "LS-" + Random.nextInt(100000, 999999),
            customerName = name.ifBlank { "Valued Member" },
            totalAmount = finalTotal,
            itemsCount = _cartItems.value.sumOf { it.quantity },
            solesDonated = donationCount
        )
        _lastOrderConfirmation.value = conf
        clearCart()
        return conf
    }

    fun dismissOrderConfirmation() {
        _lastOrderConfirmation.value = null
    }
}
