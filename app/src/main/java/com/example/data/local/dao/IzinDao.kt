package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.IzinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IzinDao {
    @Query("SELECT * FROM izin ORDER BY timestamp DESC")
    fun getAllIzin(): Flow<List<IzinEntity>>

    @Query("SELECT * FROM izin WHERE status = 'Menunggu' ORDER BY timestamp DESC")
    fun getPendingIzin(): Flow<List<IzinEntity>>

    @Query("SELECT * FROM izin WHERE santriNis = :nis ORDER BY timestamp DESC")
    fun getIzinBySantri(nis: String): Flow<List<IzinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIzin(izin: IzinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<IzinEntity>)

    @Query("UPDATE izin SET status = :status, reviewedBy = :reviewedBy, reviewNotes = :notes WHERE id = :id")
    suspend fun updateIzinStatus(id: String, status: String, reviewedBy: String?, notes: String? = null)

    @Query("DELETE FROM izin WHERE id = :id")
    suspend fun deleteIzin(id: String)
}
