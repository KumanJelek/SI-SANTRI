package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.PresensiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PresensiDao {
    @Query("SELECT * FROM presensi ORDER BY timestamp DESC")
    fun getAllPresensi(): Flow<List<PresensiEntity>>

    @Query("SELECT * FROM presensi WHERE santriNis = :nis ORDER BY timestamp DESC")
    fun getPresensiBySantri(nis: String): Flow<List<PresensiEntity>>

    @Query("SELECT * FROM presensi WHERE groupName = :group ORDER BY timestamp DESC")
    fun getPresensiByGroup(group: String): Flow<List<PresensiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPresensi(presensi: PresensiEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<PresensiEntity>)

    @Query("SELECT COUNT(*) FROM presensi")
    fun countTotalPresensi(): Flow<Int>
}
