package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.SantriEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SantriDao {
    @Query("SELECT * FROM santri WHERE nis = :nis LIMIT 1")
    fun getSantriByNis(nis: String): Flow<SantriEntity?>

    @Query("SELECT * FROM santri WHERE nis = :nis LIMIT 1")
    suspend fun getSantriByNisSync(nis: String): SantriEntity?

    @Query("SELECT * FROM santri ORDER BY name ASC")
    fun getAllSantri(): Flow<List<SantriEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSantri(santri: SantriEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<SantriEntity>)

    @Update
    suspend fun updateSantri(santri: SantriEntity)

    @Query("DELETE FROM santri WHERE nis = :nis")
    suspend fun deleteSantriByNis(nis: String)

    @Query("SELECT * FROM santri WHERE nis = :nis AND password = :password LIMIT 1")
    suspend fun authenticate(nis: String, password: String): SantriEntity?
}
