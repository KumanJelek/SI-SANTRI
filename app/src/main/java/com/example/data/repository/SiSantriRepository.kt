package com.example.data.repository

import android.content.Context
import com.example.data.*
import com.example.data.local.SiSantriDatabase
import com.example.data.local.entity.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.*

class SiSantriRepository(private val database: SiSantriDatabase) {

    private val santriDao = database.santriDao()
    private val presensiDao = database.presensiDao()
    private val izinDao = database.izinDao()
    private val jadwalDao = database.jadwalDao()
    private val umkmDao = database.umkmDao()
    private val configDao = database.configDao()

    companion object {
        @Volatile
        private var INSTANCE: SiSantriRepository? = null

        fun getInstance(context: Context): SiSantriRepository {
            return INSTANCE ?: synchronized(this) {
                val db = SiSantriDatabase.getDatabase(context)
                val instance = SiSantriRepository(db)
                INSTANCE = instance
                instance
            }
        }

        // Pesantren Default Location (e.g. Masjid Utama Pesantren)
        const val PESANTREN_DEFAULT_LAT = -6.9175
        const val PESANTREN_DEFAULT_LNG = 107.6191
        const val DEFAULT_GEOFENCE_RADIUS_METERS = 50
    }

    // ==========================================
    // SANTRI PROFILE & AUTHENTICATION
    // ==========================================

    fun getSantriProfileFlow(nis: String): Flow<SantriProfile?> {
        return santriDao.getSantriByNis(nis).map { it?.toSantriProfile() }
    }

    suspend fun getSantriProfileSync(nis: String): SantriProfile? {
        return santriDao.getSantriByNisSync(nis)?.toSantriProfile()
    }

    fun getAllSantriProfiles(): Flow<List<SantriProfile>> {
        return santriDao.getAllSantri().map { list -> list.map { it.toSantriProfile() } }
    }

    suspend fun saveOrUpdateSantri(profile: SantriProfile, password: String = "santri123") {
        santriDao.insertSantri(SantriEntity.fromSantriProfile(profile, password))
    }

    suspend fun authenticateSantri(nis: String, pass: String): SantriProfile? {
        return santriDao.authenticate(nis, pass)?.toSantriProfile()
    }

    // ==========================================
    // PRESENSI & GEOFENCING ENGINE
    // ==========================================

    val allPresensiFlow: Flow<List<PresensiLiveItem>> = presensiDao.getAllPresensi().map { list ->
        list.map { it.toPresensiLiveItem() }
    }

    suspend fun recordPresensi(
        santri: SantriProfile,
        activity: String,
        method: String, // "Swafoto", "KTS", "GPS", "Posko"
        userLat: Double = PESANTREN_DEFAULT_LAT,
        userLng: Double = PESANTREN_DEFAULT_LNG,
        targetLat: Double = PESANTREN_DEFAULT_LAT,
        targetLng: Double = PESANTREN_DEFAULT_LNG,
        geofenceRadiusMeters: Int = DEFAULT_GEOFENCE_RADIUS_METERS,
        photoUri: String = ""
    ): PresensiEntity {
        val distance = calculateDistanceMeters(userLat, userLng, targetLat, targetLng)
        val isWithin = distance <= geofenceRadiusMeters
        val initials = santri.name.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .map { it.first() }
            .joinToString("")
            .uppercase()

        val presensiEntity = PresensiEntity(
            santriNis = santri.nis,
            santriName = santri.name,
            initials = if (initials.isBlank()) "ST" else initials,
            kamar = santri.kamar.replace("Kamar ", ""),
            groupName = if (santri.kamar.contains("B-0", ignoreCase = true)) "b01-b06" else "b07-b12",
            activity = activity,
            method = method,
            timeAgoString = "Baru saja",
            timestamp = System.currentTimeMillis(),
            latitude = userLat,
            longitude = userLng,
            distanceMeters = distance,
            isWithinGeofence = isWithin,
            status = if (isWithin) "Hadir" else "Terlambat / Di luar radius",
            photoUri = photoUri
        )

        val newId = presensiDao.insertPresensi(presensiEntity)
        return presensiEntity.copy(id = newId)
    }

    fun calculateDistanceMeters(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371000.0 // meters
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (earthRadius * c * 10.0).roundToInt() / 10.0
    }

    // ==========================================
    // PERIZINAN & DISPENSASI
    // ==========================================

    val allIzinFlow: Flow<List<IzinQueueItem>> = izinDao.getAllIzin().map { list ->
        list.map { it.toIzinQueueItem() }
    }

    val pendingIzinFlow: Flow<List<IzinQueueItem>> = izinDao.getPendingIzin().map { list ->
        list.map { it.toIzinQueueItem() }
    }

    suspend fun submitIzin(
        santri: SantriProfile,
        type: String,
        reason: String,
        attachmentName: String
    ): IzinEntity {
        val newId = "iz_${System.currentTimeMillis()}"
        val entity = IzinEntity(
            id = newId,
            santriNis = santri.nis,
            santriName = santri.name,
            kamar = santri.kamar,
            photoUrl = santri.photoUrl,
            type = type,
            reason = reason,
            attachmentName = attachmentName,
            submittedTime = "Hari ini, baru saja",
            status = "Menunggu"
        )
        izinDao.insertIzin(entity)
        return entity
    }

    suspend fun reviewIzin(izinId: String, status: String, reviewedBy: String, notes: String? = null) {
        izinDao.updateIzinStatus(izinId, status, reviewedBy, notes)
    }

    // ==========================================
    // JADWAL & AGENDA KEGIATAN
    // ==========================================

    val allJadwalFlow: Flow<List<JadwalItem>> = jadwalDao.getAllJadwal().map { list ->
        list.map { it.toJadwalItem() }
    }

    suspend fun createJadwal(item: JadwalItem, radiusMeters: Int = 50) {
        val entity = JadwalEntity.fromJadwalItem(item).copy(geofenceRadiusMeters = radiusMeters)
        jadwalDao.insertJadwal(entity)
    }

    suspend fun deleteJadwal(id: String) {
        jadwalDao.deleteJadwal(id)
    }

    // ==========================================
    // PASAR UMKM SANTRI & KURASI
    // ==========================================

    val activeUmkmProductsFlow: Flow<List<UmkmProduct>> = umkmDao.getActiveProducts().map { list ->
        list.map { it.toUmkmProduct() }
    }

    val allUmkmProductsFlow: Flow<List<UmkmProduct>> = umkmDao.getAllProducts().map { list ->
        list.map { it.toUmkmProduct() }
    }

    val pendingCurationProductsFlow: Flow<List<UmkmProduct>> = umkmDao.getPendingCurationProducts().map { list ->
        list.map { it.toUmkmProduct() }
    }

    suspend fun submitUmkmProduct(product: UmkmProduct) {
        val entity = UmkmProductEntity.fromUmkmProduct(product)
        umkmDao.insertProduct(entity)
    }

    suspend fun updateProductStatus(productId: String, status: String) {
        umkmDao.updateProductStatus(productId, status)
    }

    val allOrdersFlow: Flow<List<UmkmOrderItem>> = umkmDao.getAllOrders().map { list ->
        list.map { it.toUmkmOrderItem() }
    }

    suspend fun submitOrder(order: UmkmOrderItem) {
        val entity = UmkmOrderEntity.fromUmkmOrderItem(order)
        umkmDao.insertOrder(entity)
    }

    suspend fun updateOrderStatus(orderId: String, status: String) {
        umkmDao.updateOrderStatus(orderId, status)
    }

    // ==========================================
    // CONFIGURATION & FEATURE TOGGLES
    // ==========================================

    val isUmkmEnabledFlow: Flow<Boolean> = configDao.getConfig("is_umkm_enabled").map { value ->
        value != "false"
    }

    suspend fun setUmkmEnabled(enabled: Boolean) {
        configDao.setConfig(AppConfigEntity("is_umkm_enabled", enabled.toString()))
    }
}
