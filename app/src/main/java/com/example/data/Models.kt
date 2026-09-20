package com.example.data

enum class UserRole {
    SANTRI,
    PENGURUS_PUSAT,
    PENGURUS_WILAYAH
}

data class SantriProfile(
    val name: String = "Ahmad Fauzi",
    val nis: String = "202609012",
    val asrama: String = "Asrama Al-Farabi",
    val kamar: String = "Kamar B-04",
    val jurusan: String = "Takhasus Kitab Kuning & Tahfidz",
    val universitas: String = "UIN Sunan Kalijaga / Ma'had Aly",
    val programStudi: String = "Dirasah Islamiyah (Pendidikan Kitab)",
    val photoUrl: String = "https://lh3.googleusercontent.com/aida-public/AB6AXuA28xct3Mp6fMkgFsTDfsJfZi4_dijsUdleGRnekoXl1PNHS5gke1ZfeGmP_AiUjEfsBvnY0DauBm8q2M7g5ovb423FIq5zN4JuoEA2ebbtQpmVueftZ_6VFq-TayF1xSbB-T05rnhhbYbFE3Typ-Z31XyTQsue0UiYMlQTLa2vjjq47hr4kwxpqZa2iOKllb7q6vxUDWjJm3IoUWlYsOqZZL2lmrdJiVIWtJRywQ5T22dtMYQvRpbzmw",
    val gender: String = "Laki-laki (Ikhwan)",
    val whatsapp: String = "812-3456-7890",
    val address: String = "Jl. Kyai Haji Mansyur No. 12, Kelurahan, Kecamatan, Kabupaten/Kota, Jawa Timur",
    val fatherName: String = "H. Ahmad Dahlan",
    val motherName: String = "Hj. Siti Aminah",
    val parentWhatsapp: String = "811-2345-6789",
    val qrToken: String = "SI-SANTRI-202609012-CR80-VALID",
    val presenceRate: Int = 98
)

data class PengurusProfile(
    val name: String = "Ust. Marzuki, M.Pd",
    val nip: String = "NIP-1988051201",
    val title: String = "Ketua Dewan Pengurus Pusat",
    val authority: String = "Dewan Pengurus Pusat"
)

data class JadwalItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val ustadz: String,
    val location: String,
    val timeRange: String,
    val date: String,
    val category: String, // "kitab", "tahfidz", "asrama", "ujian"
    val isLive: Boolean = false,
    val presenceWindow: String = "18:15 - 18:45 WIB",
    val isGCalendarSynced: Boolean = true
)

data class PengumumanItem(
    val id: String,
    val title: String,
    val desc: String,
    val date: String,
    val authorTag: String = "PENTING • PENGURUS PUSAT",
    val readCount: Int = 420
)

data class ArtikelItem(
    val id: String,
    val title: String,
    val author: String,
    val date: String,
    val readTime: String,
    val category: String
)

data class PresensiLiveItem(
    val id: String,
    val name: String,
    val initials: String,
    val kamar: String,
    val group: String, // "b01-b06", "b07-b12"
    val activity: String,
    val timeAgo: String,
    val method: String // "KTS", "GPS", "Posko"
)

data class IzinQueueItem(
    val id: String,
    val santriName: String,
    val kamar: String,
    val photoUrl: String,
    val type: String, // "Sakit", "Izin Pulang"
    val reason: String,
    val attachmentName: String,
    val submittedTime: String,
    var status: String = "Menunggu" // "Menunggu", "Disetujui", "Ditolak"
)

data class UmkmProduct(
    val id: String,
    val name: String,
    val price: Int,
    val originalPrice: Int = price,
    val category: String, // "makanan", "kitab", "jasa", "pakaian", "seni"
    val sellerName: String,
    val sellerRoom: String,
    val sellerPhone: String,
    val photoUrl: String,
    val badge: String,
    val rating: Double,
    val soldCount: Int,
    val stock: Int,
    val description: String,
    val ingredients: List<String> = emptyList(),
    val variants: List<String> = listOf("Original Mint", "Lavender Sidr", "Cengkeh Hangat"),
    var status: String = "Aktif" // "Aktif", "Menunggu Kurasi", "Revisi"
)

data class UmkmOrderItem(
    val id: String,
    val buyerName: String,
    val buyerRole: String,
    val buyerPhone: String = "6281234567890",
    val timeAgo: String,
    val productName: String,
    val qty: Int,
    val totalPrice: Int,
    val variant: String,
    val pickupMethod: String,
    var status: String = "Menunggu Siap" // "Menunggu Siap", "Siap Diambil", "Selesai"
)

data class SupportMessage(
    val id: String,
    val sender: String, // "Santri", "Bot", "Pengurus"
    val senderName: String,
    val message: String,
    val time: String,
    val isBot: Boolean = false
)
