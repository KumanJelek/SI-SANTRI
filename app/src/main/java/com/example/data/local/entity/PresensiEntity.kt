package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.PresensiLiveItem

@Entity(tableName = "presensi")
data class PresensiEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val santriNis: String,
    val santriName: String,
    val initials: String,
    val kamar: String,
    val groupName: String, // e.g. "b01-b06"
    val activity: String, // e.g. "Hadir • Sholat Subuh Berjamaah"
    val method: String, // "Swafoto", "KTS", "GPS", "Posko"
    val timestamp: Long = System.currentTimeMillis(),
    val timeAgoString: String = "Baru saja",
    val latitude: Double = -6.9175,
    val longitude: Double = 107.6191,
    val distanceMeters: Double = 12.0,
    val isWithinGeofence: Boolean = true,
    val status: String = "Hadir", // "Hadir", "Izin", "Terlambat", "Alfa"
    val photoUri: String = ""
) {
    fun toPresensiLiveItem(): PresensiLiveItem {
        return PresensiLiveItem(
            id = id.toString(),
            name = santriName,
            initials = initials,
            kamar = kamar,
            group = groupName,
            activity = activity,
            timeAgo = timeAgoString,
            method = method
        )
    }
}
