package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.JadwalItem

@Entity(tableName = "jadwal")
data class JadwalEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val ustadz: String,
    val location: String,
    val timeRange: String,
    val date: String,
    val category: String, // "kitab", "tahfidz", "asrama", "ujian"
    val isLive: Boolean = false,
    val presenceWindow: String = "18:15 - 18:45 WIB",
    val isGCalendarSynced: Boolean = true,
    val targetLat: Double = -6.9175,
    val targetLng: Double = 107.6191,
    val geofenceRadiusMeters: Int = 50
) {
    fun toJadwalItem(): JadwalItem {
        return JadwalItem(
            id = id,
            title = title,
            subtitle = subtitle,
            ustadz = ustadz,
            location = location,
            timeRange = timeRange,
            date = date,
            category = category,
            isLive = isLive,
            presenceWindow = presenceWindow,
            isGCalendarSynced = isGCalendarSynced
        )
    }

    companion object {
        fun fromJadwalItem(item: JadwalItem): JadwalEntity {
            return JadwalEntity(
                id = item.id,
                title = item.title,
                subtitle = item.subtitle,
                ustadz = item.ustadz,
                location = item.location,
                timeRange = item.timeRange,
                date = item.date,
                category = item.category,
                isLive = item.isLive,
                presenceWindow = item.presenceWindow,
                isGCalendarSynced = item.isGCalendarSynced
            )
        }
    }
}
