package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.UmkmOrderEntity
import com.example.data.local.entity.UmkmProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UmkmDao {
    // PRODUCTS
    @Query("SELECT * FROM umkm_products ORDER BY rating DESC, soldCount DESC")
    fun getAllProducts(): Flow<List<UmkmProductEntity>>

    @Query("SELECT * FROM umkm_products WHERE status = 'Aktif' ORDER BY rating DESC")
    fun getActiveProducts(): Flow<List<UmkmProductEntity>>

    @Query("SELECT * FROM umkm_products WHERE status = 'Menunggu Kurasi' ORDER BY id DESC")
    fun getPendingCurationProducts(): Flow<List<UmkmProductEntity>>

    @Query("SELECT * FROM umkm_products WHERE category = :category AND status = 'Aktif'")
    fun getProductsByCategory(category: String): Flow<List<UmkmProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: UmkmProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(list: List<UmkmProductEntity>)

    @Query("UPDATE umkm_products SET status = :status WHERE id = :id")
    suspend fun updateProductStatus(id: String, status: String)

    @Query("DELETE FROM umkm_products WHERE id = :id")
    suspend fun deleteProduct(id: String)

    // ORDERS
    @Query("SELECT * FROM umkm_orders ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<UmkmOrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: UmkmOrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOrders(list: List<UmkmOrderEntity>)

    @Query("UPDATE umkm_orders SET status = :status WHERE id = :id")
    suspend fun updateOrderStatus(id: String, status: String)
}
