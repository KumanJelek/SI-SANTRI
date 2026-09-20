package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.AppConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigDao {
    @Query("SELECT value FROM app_config WHERE `key` = :key LIMIT 1")
    fun getConfig(key: String): Flow<String?>

    @Query("SELECT value FROM app_config WHERE `key` = :key LIMIT 1")
    suspend fun getConfigSync(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setConfig(config: AppConfigEntity)
}
