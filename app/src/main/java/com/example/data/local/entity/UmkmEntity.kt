package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.UmkmOrderItem
import com.example.data.UmkmProduct

@Entity(tableName = "umkm_products")
data class UmkmProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val price: Int,
    val originalPrice: Int,
    val category: String, // "makanan", "kitab", "jasa", "pakaian", "seni"
    val sellerName: String,
    val sellerRoom: String,
    val sellerPhone: String,
    val photoUrl: String,
    val badge: String,
    val rating: Double,
    val soldCount: Int,
    val stock: Int,
    val description: String,
    val ingredientsCsv: String, // comma-separated or converter
    val variantsCsv: String, // comma-separated
    val status: String = "Aktif" // "Aktif", "Menunggu Kurasi", "Revisi"
) {
    fun toUmkmProduct(): UmkmProduct {
        return UmkmProduct(
            id = id,
            name = name,
            price = price,
            originalPrice = originalPrice,
            category = category,
            sellerName = sellerName,
            sellerRoom = sellerRoom,
            sellerPhone = sellerPhone,
            photoUrl = photoUrl,
            badge = badge,
            rating = rating,
            soldCount = soldCount,
            stock = stock,
            description = description,
            ingredients = if (ingredientsCsv.isBlank()) emptyList() else ingredientsCsv.split("||"),
            variants = if (variantsCsv.isBlank()) emptyList() else variantsCsv.split("||"),
            status = status
        )
    }

    companion object {
        fun fromUmkmProduct(p: UmkmProduct): UmkmProductEntity {
            return UmkmProductEntity(
                id = p.id,
                name = p.name,
                price = p.price,
                originalPrice = p.originalPrice,
                category = p.category,
                sellerName = p.sellerName,
                sellerRoom = p.sellerRoom,
                sellerPhone = p.sellerPhone,
                photoUrl = p.photoUrl,
                badge = p.badge,
                rating = p.rating,
                soldCount = p.soldCount,
                stock = p.stock,
                description = p.description,
                ingredientsCsv = p.ingredients.joinToString("||"),
                variantsCsv = p.variants.joinToString("||"),
                status = p.status
            )
        }
    }
}

@Entity(tableName = "umkm_orders")
data class UmkmOrderEntity(
    @PrimaryKey val id: String,
    val buyerName: String,
    val buyerRole: String,
    val buyerPhone: String = "6281234567890",
    val timeAgo: String,
    val productName: String,
    val qty: Int,
    val totalPrice: Int,
    val variant: String,
    val pickupMethod: String,
    val status: String = "Menunggu Siap",
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toUmkmOrderItem(): UmkmOrderItem {
        return UmkmOrderItem(
            id = id,
            buyerName = buyerName,
            buyerRole = buyerRole,
            buyerPhone = buyerPhone,
            timeAgo = timeAgo,
            productName = productName,
            qty = qty,
            totalPrice = totalPrice,
            variant = variant,
            pickupMethod = pickupMethod,
            status = status
        )
    }

    companion object {
        fun fromUmkmOrderItem(o: UmkmOrderItem): UmkmOrderEntity {
            return UmkmOrderEntity(
                id = o.id,
                buyerName = o.buyerName,
                buyerRole = o.buyerRole,
                buyerPhone = o.buyerPhone,
                timeAgo = o.timeAgo,
                productName = o.productName,
                qty = o.qty,
                totalPrice = o.totalPrice,
                variant = o.variant,
                pickupMethod = o.pickupMethod,
                status = o.status
            )
        }
    }
}
