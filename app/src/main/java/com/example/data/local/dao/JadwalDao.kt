package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.JadwalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {
    @Query("SELECT * FROM jadwal ORDER BY date ASC, timeRange ASC")
    fun getAllJadwal(): Flow<List<JadwalEntity>>

    @Query("SELECT * FROM jadwal WHERE category = :category ORDER BY date ASC")
    fun getJadwalByCategory(category: String): Flow<List<JadwalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJadwal(jadwal: JadwalEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<JadwalEntity>)

    @Update
    suspend fun updateJadwal(jadwal: JadwalEntity)

    @Query("DELETE FROM jadwal WHERE id = :id")
    suspend fun deleteJadwal(id: String)
}
