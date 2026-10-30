package com.example.loopstep.data

import com.example.loopstep.R

data class SoleEdition(
    val id: String,
    val name: String,
    val subtitle: String,
    val price: Int,
    val mfgCost: Int,
    val profit: Int,
    val thickness: String,
    val durometer: String,
    val weight: String,
    val intendedUse: String,
    val treadPattern: String,
    val cushioning: String,
    val materials: List<String>,
    val imageRes: Int,
    val colors: List<String>,
    val defaultColor: String,
    val features: List<String>,
    val rating: Double,
    val reviewsCount: Int
)

data class CompleteShoe(
    val id: String,
    val name: String,
    val tagline: String,
    val price: Int,
    val originalPrice: Int,
    val category: String,
    val description: String,
    val imageRes: Int,
    val includedSole: String,
    val upperMaterial: String,
    val soleStandard: String,
    val lifespan: String,
    val sizes: List<Int>,
    val colors: List<String>,
    val weight: String,
    val closure: String,
    val drop: String,
    val breathability: String
)

data class ReFitAdapter(
    val id: String,
    val name: String,
    val subtitle: String,
    val price: Int,
    val mfgCost: Int,
    val profit: Int,
    val description: String,
    val imageRes: Int,
    val contents: List<String>,
    val material: String,
    val thickness: String,
    val bondStrength: String,
    val curingTime: String
)

data class CartItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: Int,
    var quantity: Int,
    val imageRes: Int,
    val size: String? = null,
    val color: String? = null,
    val itemType: String // "shoe", "sole", "custom_sole", "refit"
)

data class BrandCompatibility(
    val brand: String,
    val models: List<String>,
    val status: String,
    val adapterNeeded: String
)

data class SDGFocus(
    val code: String,
    val title: String,
    val desc: String
)

data class FAQItem(
    val question: String,
    val answer: String
)

data class ScanResult(
    val footLengthMm: Int,
    val footWidthMm: Int,
    val archType: String,
    val recommendedSize: Int,
    val recommendedSole: String,
    val durometer: String
)

data class MaterialInfo(
    val name: String,
    val purpose: String
)
