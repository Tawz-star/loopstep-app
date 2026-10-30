package com.example.loopstep.data

import com.example.loopstep.R

object MockData {

    val soleEditions = listOf(
        SoleEdition(
            id = "standard",
            name = "Standard Edition",
            subtitle = "Everyday Comfort & City Pavement",
            price = 200,
            mfgCost = 70,
            profit = 130,
            thickness = "8 mm",
            durometer = "50-55 Shore A",
            weight = "145g",
            intendedUse = "Daily city walking, casual wear, office commutes",
            treadPattern = "Wave Flex Grooves",
            cushioning = "Compression-molded EVA Foam with natural rubber contact pads",
            materials = listOf("Recycled EVA Foam Core", "Natural Rubber Tread Outsole", "PA66-GF30 Twist-Lock Ring"),
            imageRes = R.drawable.sole_standard,
            colors = listOf("#2C312E", "#F7F5F2", "#C6F135", "#3A3A3A"),
            defaultColor = "#2C312E",
            features = listOf(
                "Ultra-light 8mm profile for relaxed natural foot motion",
                "Wave flex channel grooves for smooth heel-to-toe roll",
                "Dual bayonet retention lugs for zero slippage",
                "Ships in 100% recycled cardboard packaging"
            ),
            rating = 4.8,
            reviewsCount = 142
        ),
        SoleEdition(
            id = "sports",
            name = "Sports Edition",
            subtitle = "Active Performance & Kinetic Rebound",
            price = 500,
            mfgCost = 170,
            profit = 330,
            thickness = "12 mm",
            durometer = "60 Shore A",
            weight = "190g",
            intendedUse = "Running, training, gym workouts, lateral athletic motion",
            treadPattern = "Dynamic Hex & Chevron Lug System",
            cushioning = "High-density EVA + responsive TPU kinetic rebound insert",
            materials = listOf("High-Density EVA", "TPU Rebound Cushion Layer", "Carbon Rubber Traction Pods", "PA66-GF30 Ring"),
            imageRes = R.drawable.sole_sports,
            colors = listOf("#1A1A1A", "#C6F135", "#00F0FF", "#E7A93B"),
            defaultColor = "#1A1A1A",
            features = listOf(
                "12mm dual-density stack with embedded shock absorption pocket",
                "Carbon-infused rubber tread pods for high-abrasion durability",
                "High torsional rigidity preventing midsole twisting during sprint cuts",
                "Rated for 600+ km of athletic activity before sole swap"
            ),
            rating = 4.9,
            reviewsCount = 98
        ),
        SoleEdition(
            id = "trail",
            name = "Trail Edition",
            subtitle = "Rough Terrain & Rock Protection",
            price = 700,
            mfgCost = 250,
            profit = 450,
            thickness = "15 mm",
            durometer = "68 Shore A",
            weight = "240g",
            intendedUse = "Hiking, gravel trails, rocky outdoor terrain, wet muddy slopes",
            treadPattern = "Deep 5mm Geometric Lugs with Self-Cleaning Channels",
            cushioning = "Reinforced TPU chassis with embedded rock plate shield",
            materials = listOf("High-Grip Vulcanized Rubber", "Reinforced TPU Rock Plate", "Mud-Shedding Channels", "PA66-GF30 Ring"),
            imageRes = R.drawable.sole_trail,
            colors = listOf("#4A6141", "#2C312E", "#E7A93B", "#B4553B"),
            defaultColor = "#4A6141",
            features = listOf(
                "Deep 5mm multi-angle perimeter lugs with biting edges",
                "Integrated ballistic rock plate shielding against sharp stones",
                "High-traction wet-grip compound engineered for slick mud & shale",
                "Reinforced locking teeth with sealed mud-exclusion gasket"
            ),
            rating = 4.95,
            reviewsCount = 76
        )
    )

    val completeShoes = listOf(
        CompleteShoe(
            id = "shoe-terra",
            name = "LoopStep Terra",
            tagline = "The Everyday Keeper",
            price = 2499,
            originalPrice = 3000,
            category = "Lifestyle & Urban",
            description = "Engineered for seamless urban living. Crafted with a 100% recycled ocean-plastic knit upper that conforms to your foot like a second skin, married to a natural rubber cage and our proprietary moulded twist-lock ring.",
            imageRes = R.drawable.shoe_terra,
            includedSole = "Standard Edition (8 mm fitted)",
            upperMaterial = "Recycled Ocean-Plastic Knit + Natural Rubber Cage",
            soleStandard = "Standard Twist-Lock Ring (PA66-GF30)",
            lifespan = "Upper rated for 3+ years of continuous wear",
            sizes = listOf(38, 39, 40, 41, 42, 43, 44, 45, 46),
            colors = listOf("Ocean Sand & Charcoal", "Graphite Black", "Chalk White"),
            weight = "280g (Size 42 with Standard Sole)",
            closure = "Recycled Polyester Lacing with quick-lock eyelets",
            drop = "6 mm",
            breathability = "High airflow zoned knit"
        ),
        CompleteShoe(
            id = "shoe-drift",
            name = "LoopStep Drift",
            tagline = "Built to Run, Made to Last",
            price = 2899,
            originalPrice = 3499,
            category = "Performance Running",
            description = "Lightweight performance runner designed for daily road training and gym sessions. High-tensile recycled mesh with lateral TPU frame holding your heel locked in place through every sprint.",
            imageRes = R.drawable.shoe_drift,
            includedSole = "Sports Edition (12 mm fitted)",
            upperMaterial = "Engineered Performance Weave + Lateral TPU Counter",
            soleStandard = "Standard Twist-Lock Ring (PA66-GF30)",
            lifespan = "Upper rated for 3,000+ running km",
            sizes = listOf(38, 39, 40, 41, 42, 43, 44, 45, 46),
            colors = listOf("Deep Graphite / Signal Lime", "Midnight Carbon", "Solar Ember"),
            weight = "310g (Size 42 with Sports Sole)",
            closure = "Dynamic lacing cage with padded tongue gusset",
            drop = "8 mm",
            breathability = "Active moisture-wicking weave"
        ),
        CompleteShoe(
            id = "shoe-ridge",
            name = "LoopStep Ridge",
            tagline = "Trail-Ready, Sole-Swappable",
            price = 2999,
            originalPrice = 3699,
            category = "Outdoor & Hiking",
            description = "Heavy-duty trail shoe with an abrasion-resistant ripstop upper, mudguard rubber wrap, and water-shedding gusset. Ready for jagged gravel, wet roots, and rugged backcountry paths.",
            imageRes = R.drawable.shoe_ridge,
            includedSole = "Trail Edition (15 mm fitted)",
            upperMaterial = "Recycled Ripstop Textile + TPU Abrasion Overlays",
            soleStandard = "Standard Twist-Lock Ring (PA66-GF30)",
            lifespan = "Upper rated for 4+ years of outdoor expeditions",
            sizes = listOf(38, 39, 40, 41, 42, 43, 44, 45, 46),
            colors = listOf("Deep Moss / Bark Brown", "Blackout Stealth", "Alpine Rust"),
            weight = "360g (Size 42 with Trail Sole)",
            closure = "Heavy-duty Kevlar-reinforced eyelet lace harness",
            drop = "10 mm",
            breathability = "Durable water-repellent (DWR) ripstop"
        )
    )

    val refitAdapter = ReFitAdapter(
        id = "refit-adapter-kit",
        name = "LoopStep ReFit™ Universal Adapter Kit",
        subtitle = "Convert ANY brand shoe into a twist-lock modular shoe",
        price = 50,
        mfgCost = 35,
        profit = 15,
        description = "The right to repair applies to all footwear. Our ReFit kit contains calibrated 2mm PA66-GF30 twist-lock adapter plates, specialized industrial polyurethane bonding adhesive, sandpaper preparation pad, and a foolproof alignment stencil. Turn your favorite worn Nike, Adidas, Puma, or Converse into a LoopStep sole receiver in 15 minutes.",
        imageRes = R.drawable.refit_adapter,
        contents = listOf(
            "2x PA66-GF30 Reinforced Twist-Lock Adapter Plates (Left & Right)",
            "1x Industrial Polyurethane Contact Adhesive Tube (50ml)",
            "2x Abrasive surface prep pads",
            "1x Universal alignment and sizing template",
            "1x Step-by-step illustrated manual & QR video guide"
        ),
        material = "PA66 Glass Fiber Reinforced (30%)",
        thickness = "2.2 mm ultra-low profile",
        bondStrength = "Exceeds 4.5 MPa shear resistance",
        curingTime = "Initial hold in 10 mins, full cure in 12 hours"
    )

    val brandCompatibilityList = listOf(
        BrandCompatibility("Nike", listOf("Air Force 1", "Pegasus 39/40", "Court Vision", "Revolution 6"), "Verified Match", "Standard ReFit 40-44"),
        BrandCompatibility("Adidas", listOf("Stan Smith", "Ultraboost 1.0-5.0", "Superstar", "Duramo"), "Verified Match", "Standard ReFit 39-45"),
        BrandCompatibility("Puma", listOf("Suede Classic", "Smash v2", "Velocity Nitro"), "Verified Match", "Standard ReFit 38-44"),
        BrandCompatibility("Converse", listOf("Chuck Taylor All Star", "Chuck 70"), "Verified Match", "Flat Base ReFit 38-46"),
        BrandCompatibility("Vans", listOf("Old Skool", "Authentic", "Era"), "Verified Match", "Flat Base ReFit 38-45"),
        BrandCompatibility("Asics", listOf("Gel-Kayano", "Gel-Nimbus", "GT-2000"), "Potential Match", "Requires Arch Inspection"),
        BrandCompatibility("New Balance", listOf("574", "327", "990v5"), "Verified Match", "Standard ReFit 39-45")
    )

    val sdgList = listOf(
        SDGFocus(
            code = "SDG 12",
            title = "Responsible Consumption & Production",
            desc = "Decoupling upper wear from sole wear extends footwear lifespan by 3x–5x and cuts shoe landfill waste by up to 70%."
        ),
        SDGFocus(
            code = "SDG 13",
            title = "Climate Action",
            desc = "Manufacturing a replacement sole produces 82% less carbon emissions compared to manufacturing an entire new pair of sneakers."
        ),
        SDGFocus(
            code = "SDG 8",
            title = "Decent Work & Economic Growth",
            desc = "Our 1:1 Social Impact Model donates one spare sole for every complete pair sold to local shoe-repair micro-enterprises in underserved communities."
        )
    )

    val materials = listOf(
        MaterialInfo("PA66-GF30 Twist-Lock Ring", "Polyamide 66 reinforced with 30% glass fiber, capable of withstanding 7,000 N loads."),
        MaterialInfo("Recycled Ocean-Plastic Mesh", "Breathable, high-tenacity upper reducing virgin petroleum polymers."),
        MaterialInfo("Natural Rubber Traction Outsole", "Sustainably harvested natural rubber contact pads with wave and hex tread channels."),
        MaterialInfo("Compression-Molded EVA", "Lightweight responsive cushioning absorbing continuous foot impact."),
        MaterialInfo("PU Memory Foam Insole", "Contoured anatomical footbed offering customized arch and metatarsal comfort."),
        MaterialInfo("Reinforced TPU Rock Plate", "Ballistic penetration shield safeguarding against jagged shale and rocks.")
    )

    val faqList = listOf(
        FAQItem(
            "What makes LoopStep different from conventional shoes?",
            "In conventional footwear, 90% of discarded shoes still have a perfectly serviceable upper, but are thrown away because only the rubber outsole wore out. LoopStep introduces a reinforced mechanical twist-lock ring that lets you detach a worn sole in 10 seconds and click in a new one starting at just ₹200."
        ),
        FAQItem(
            "How does the mechanical twist-lock system work?",
            "The shoe midsole houses a high-strength PA66-GF30 polymer ring with precision radial bayonet teeth. The outsole has matching interlocking lugs. Align the index markers, rotate 45 degrees, and an audible mechanical detent clicks to lock it solidly into place. No batteries or magnets — pure mechanical engineering."
        ),
        FAQItem(
            "Can I replace the sole myself without any tools?",
            "Yes! The twist-lock system requires zero tools, heat, or adhesives for LoopStep native shoes. You simply twist counter-clockwise by hand, pull off the old sole, align the replacement, and twist clockwise until the click confirms positive engagement."
        ),
        FAQItem(
            "Can I convert my existing Nike, Adidas, or Converse sneakers with ReFit?",
            "Yes. Our universal ReFit Adapter Kit (₹50) includes a low-profile twist-lock adapter plate and industrial polyurethane adhesive. Follow our 4-step home guide to bond the plate to the flat base of your existing shoe, and it can take LoopStep modular soles forever."
        ),
        FAQItem(
            "How strong is the twist-lock connection? Will it detach while running?",
            "The locking mechanism has been tested up to 7,000 N of vertical and shear force (exceeding 700 kg of load, far greater than human athletic impact). A dual retention detent prevents unintended rotation under forward or lateral athletic cuts."
        ),
        FAQItem(
            "What is the One-for-One sole donation program?",
            "For every complete pair of LoopStep shoes sold, we fund and donate one spare replacement sole directly to cobblers and shoe-repair micro-enterprises in underserved communities. This creates circular employment and supports local repair livelihoods."
        ),
        FAQItem(
            "What is the Sole Exchange program?",
            "When your LoopStep sole finally wears down, send it back to receive 10% off your next replacement sole. We shred the retired soles and blend the vulcanized rubber into new tread compounds."
        )
    )
}
