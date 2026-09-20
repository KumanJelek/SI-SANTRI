package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.SantriProfile

@Entity(tableName = "santri")
data class SantriEntity(
    @PrimaryKey val nis: String,
    val name: String,
    val password: String = "santri123",
    val asrama: String,
    val kamar: String,
    val jurusan: String,
    val universitas: String = "UIN Sunan Kalijaga / Ma'had Aly",
    val programStudi: String = "Dirasah Islamiyah",
    val photoUrl: String,
    val gender: String,
    val whatsapp: String,
    val address: String,
    val fatherName: String,
    val motherName: String,
    val parentWhatsapp: String,
    val qrToken: String,
    val presenceRate: Int = 98
) {
    fun toSantriProfile(): SantriProfile {
        return SantriProfile(
            name = name,
            nis = nis,
            asrama = asrama,
            kamar = kamar,
            jurusan = jurusan,
            universitas = universitas,
            programStudi = programStudi,
            photoUrl = photoUrl,
            gender = gender,
            whatsapp = whatsapp,
            address = address,
            fatherName = fatherName,
            motherName = motherName,
            parentWhatsapp = parentWhatsapp,
            qrToken = qrToken,
            presenceRate = presenceRate
        )
    }

    companion object {
        fun fromSantriProfile(profile: SantriProfile, password: String = "santri123"): SantriEntity {
            return SantriEntity(
                nis = profile.nis,
                name = profile.name,
                password = password,
                asrama = profile.asrama,
                kamar = profile.kamar,
                jurusan = profile.jurusan,
                universitas = profile.universitas,
                programStudi = profile.programStudi,
                photoUrl = profile.photoUrl,
                gender = profile.gender,
                whatsapp = profile.whatsapp,
                address = profile.address,
                fatherName = profile.fatherName,
                motherName = profile.motherName,
                parentWhatsapp = profile.parentWhatsapp,
                qrToken = profile.qrToken,
                presenceRate = profile.presenceRate
            )
        }
    }
}
