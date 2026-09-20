package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.UmkmProduct
import com.example.ui.theme.*

@Composable
fun UmkmKurasiScreen(
    pendingProducts: List<UmkmProduct> = emptyList(),
    onCurateProduct: (String, Boolean) -> Unit = { _, _ -> },
    onBackClick: () -> Unit
) {
    val initialSample = listOf(
        UmkmProduct(
            id = "k_1",
            name = "Minyak Kemiri Bakar Murni Santri 60ml",
            price = 22000,
            originalPrice = 25000,
            category = "jasa",
            sellerName = "Lukman Hakim",
            sellerRoom = "Asrama Al-Farabi B-08",
            sellerPhone = "6285678901234",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDYQ4fSEGviituQWmi7bPrfDUiEe6J6jVKVMRQD2hy6eNqjaHqDjmnn_kodKeVzt59F_39SPlaYSXAcdf0OiwAkZuDPrW0wxcJ3O-753ipcdZSrqph5fQP69ACRE_L0HI88WFMKj2Jkwv8UpY3XPolSXV25wDHQbIaa0ZfDueO2YJ5qZvlCuvQcb6RYkKj52ICbbxEd5kl_nW8OYzd_WqVi-PwJco40uVmuXmqDq47f11gknEklIBuQqA",
            badge = "Menunggu Kurasi",
            rating = 5.0,
            soldCount = 0,
            stock = 25,
            description = "Olahan kemiri sangrai murni karya santri tanpa bahan kimia sintetis. Baik untuk kesehatan rambut dan jenggot santri.",
            status = "Menunggu Kurasi"
        ),
        UmkmProduct(
            id = "k_2",
            name = "Kopi Robusta Pesantren Petik Merah 150g",
            price = 28000,
            originalPrice = 30000,
            category = "makanan",
            sellerName = "Zainal Muttaqin",
            sellerRoom = "Asrama Ibnu Rusyd B-02",
            sellerPhone = "6287890123456",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDYQ4fSEGviituQWmi7bPrfDUiEe6J6jVKVMRQD2hy6eNqjaHqDjmnn_kodKeVzt59F_39SPlaYSXAcdf0OiwAkZuDPrW0wxcJ3O-753ipcdZSrqph5fQP69ACRE_L0HI88WFMKj2Jkwv8UpY3XPolSXV25wDHQbIaa0ZfDueO2YJ5qZvlCuvQcb6RYkKj52ICbbxEd5kl_nW8OYzd_WqVi-PwJco40uVmuXmqDq47f11gknEklIBuQqA",
            badge = "Menunggu Kurasi",
            rating = 5.0,
            soldCount = 0,
            stock = 40,
            description = "Kopi bubuk asli hasil kebun pesantren binaan, disangrai manual oleh santri takhasus.",
            status = "Menunggu Kurasi"
        )
    )
    val kurasiList = if (pendingProducts.isNotEmpty()) pendingProducts else initialSample

    var toastMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "Kurasi Produk UMKM",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Dewan Pengurus & Pengawas Pesantren",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // STANDARDS BANNER
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = SurfaceCard,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Standar Kurasi Syariah & Mutu", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Surface(color = GoldLight.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp)) {
                            Text(text = "SOP Pesantren", color = GoldTertiary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldSecondary, modifier = Modifier.size(13.dp))
                            Text(text = "Bahan 100% Halal, Thayyib & Higienis", fontSize = 11.sp, color = TextBody)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldSecondary, modifier = Modifier.size(13.dp))
                            Text(text = "Bukan barang terlarang / menyalahi tata tertib pondok", fontSize = 11.sp, color = TextBody)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldSecondary, modifier = Modifier.size(13.dp))
                            Text(text = "Harga wajar santri & potongan infaq 2.5% disetujui", fontSize = 11.sp, color = TextBody)
                        }
                    }
                }
            }

            Text(
                text = "Antrean Produk Diajukan Santri (${kurasiList.count { it.status == "Menunggu Kurasi" }})",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            kurasiList.forEach { prod ->
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = SurfaceCard,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                AsyncImage(
                                    model = prod.photoUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = prod.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(text = "Rp ${String.format("%,d", prod.price).replace(',', '.')}", color = EmeraldPrimaryContainer, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text(text = "Penjual: ${prod.sellerName}", fontSize = 10.5.sp, color = TextBody)
                                Text(text = prod.sellerRoom, fontSize = 9.5.sp, color = TextMuted)
                            }
                        }

                        Text(text = prod.description, fontSize = 11.5.sp, color = TextBody, lineHeight = 16.sp)

                        if (prod.status == "Menunggu Kurasi") {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        onCurateProduct(prod.id, false)
                                        toastMessage = "Produk ${prod.name} ditolak / diminta perbaikan data."
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Tolak", color = StatusDanger, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = {
                                        onCurateProduct(prod.id, true)
                                        toastMessage = "Alhamdulillah! Produk ${prod.name} disetujui & tayang di katalog publik."
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1.5f)
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("ACC & Terbitkan", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else {
                            Surface(
                                color = if (prod.status == "Disetujui") StatusSuccess.copy(alpha = 0.15f) else StatusDanger.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Status Kurasi: ${prod.status}",
                                    color = if (prod.status == "Disetujui") StatusSuccess else StatusDanger,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
