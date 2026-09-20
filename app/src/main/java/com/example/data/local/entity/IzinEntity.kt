package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.IzinQueueItem

@Entity(tableName = "izin")
data class IzinEntity(
    @PrimaryKey val id: String,
    val santriNis: String,
    val santriName: String,
    val kamar: String,
    val photoUrl: String,
    val type: String, // "Sakit", "Izin Pulang/Acara Keluarga", "Tugas Pesantren"
    val reason: String,
    val attachmentName: String,
    val submittedTime: String,
    val status: String = "Menunggu", // "Menunggu", "Disetujui", "Ditolak"
    val reviewedBy: String? = null,
    val reviewNotes: String? = null,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toIzinQueueItem(): IzinQueueItem {
        return IzinQueueItem(
            id = id,
            santriName = santriName,
            kamar = kamar,
            photoUrl = photoUrl,
            type = type,
            reason = reason,
            attachmentName = attachmentName,
            submittedTime = submittedTime,
            status = status
        )
    }

    companion object {
        fun fromIzinQueueItem(item: IzinQueueItem, nis: String = "202609012"): IzinEntity {
            return IzinEntity(
                id = item.id,
                santriNis = nis,
                santriName = item.santriName,
                kamar = item.kamar,
                photoUrl = item.photoUrl,
                type = item.type,
                reason = item.reason,
                attachmentName = item.attachmentName,
                submittedTime = item.submittedTime,
                status = item.status
            )
        }
    }
}
