package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.SampleData
import com.example.data.local.dao.*
import com.example.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        SantriEntity::class,
        PresensiEntity::class,
        IzinEntity::class,
        JadwalEntity::class,
        UmkmProductEntity::class,
        UmkmOrderEntity::class,
        AppConfigEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SiSantriDatabase : RoomDatabase() {
    abstract fun santriDao(): SantriDao
    abstract fun presensiDao(): PresensiDao
    abstract fun izinDao(): IzinDao
    abstract fun jadwalDao(): JadwalDao
    abstract fun umkmDao(): UmkmDao
    abstract fun configDao(): ConfigDao

    companion object {
        @Volatile
        private var INSTANCE: SiSantriDatabase? = null

        fun getDatabase(context: Context): SiSantriDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SiSantriDatabase::class.java,
                    "si_santri_database.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Seed default dataset from SampleData
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    populateInitialData(database)
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                // Also ensure initial seed happens if tables are empty
                CoroutineScope(Dispatchers.IO).launch {
                    populateInitialDataIfEmpty(instance)
                }

                instance
            }
        }

        private suspend fun populateInitialDataIfEmpty(database: SiSantriDatabase) {
            val existingSantri = database.santriDao().getSantriByNisSync(SampleData.defaultSantri.nis)
            if (existingSantri == null) {
                populateInitialData(database)
            }
        }

        private suspend fun populateInitialData(database: SiSantriDatabase) {
            // Seed Santri
            database.santriDao().insertSantri(
                SantriEntity.fromSantriProfile(SampleData.defaultSantri, "santri123")
            )

            // Seed Jadwal
            val jadwalEntities = SampleData.jadwals.map { JadwalEntity.fromJadwalItem(it) }
            database.jadwalDao().insertAll(jadwalEntities)

            // Seed Presensi Live
            val presensiEntities = SampleData.presensiLive.mapIndexed { index, item ->
                PresensiEntity(
                    santriNis = if (index == 0) SampleData.defaultSantri.nis else "20260901${index + 1}",
                    santriName = item.name,
                    initials = item.initials,
                    kamar = item.kamar,
                    groupName = item.group,
                    activity = item.activity,
                    method = item.method,
                    timeAgoString = item.timeAgo,
                    timestamp = System.currentTimeMillis() - (index * 6 * 60 * 1000)
                )
            }
            database.presensiDao().insertAll(presensiEntities)

            // Seed Izin
            val izinEntities = SampleData.izinQueue.map { IzinEntity.fromIzinQueueItem(it) }
            database.izinDao().insertAll(izinEntities)

            // Seed UMKM Products
            val productEntities = SampleData.umkmProducts.map { UmkmProductEntity.fromUmkmProduct(it) }
            database.umkmDao().insertAllProducts(productEntities)

            // Seed UMKM Orders
            val orderEntities = SampleData.lapakOrders.map { UmkmOrderEntity.fromUmkmOrderItem(it) }
            database.umkmDao().insertAllOrders(orderEntities)

            // Seed App Config
            database.configDao().setConfig(AppConfigEntity("is_umkm_enabled", "true"))
            database.configDao().setConfig(AppConfigEntity("geofence_center_lat", "-6.9175"))
            database.configDao().setConfig(AppConfigEntity("geofence_center_lng", "107.6191"))
            database.configDao().setConfig(AppConfigEntity("geofence_default_radius", "50"))
        }
    }
}
