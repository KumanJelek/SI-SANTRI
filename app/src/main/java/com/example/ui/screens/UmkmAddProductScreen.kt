package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.UmkmProduct
import com.example.ui.theme.*

@Composable
fun UmkmAddProductScreen(
    onProductSubmitted: (UmkmProduct) -> Unit,
    onBackClick: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("makanan") }
    var priceText by remember { mutableStateOf("") }
    var stockCount by remember { mutableStateOf(10) }
    var description by remember { mutableStateOf("") }
    var isPreOrder by remember { mutableStateOf(false) }
    var sellerName by remember { mutableStateOf("Ahmad Fauzi") }
    var sellerRoom by remember { mutableStateOf("Asrama Al-Farabi B-04") }
    var whatsappNumber by remember { mutableStateOf("6281234567890") }
    var agreeSharia by remember { mutableStateOf(true) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    val defaultPhoto = "https://lh3.googleusercontent.com/aida-public/AB6AXuDYQ4fSEGviituQWmi7bPrfDUiEe6J6jVKVMRQD2hy6eNqjaHqDjmnn_kodKeVzt59F_39SPlaYSXAcdf0OiwAkZuDPrW0wxcJ3O-753ipcdZSrqph5fQP69ACRE_L0HI88WFMKj2Jkwv8UpY3XPolSXV25wDHQbIaa0ZfDueO2YJ5qZvlCuvQcb6RYkKj52ICbbxEd5kl_nW8OYzd_WqVi-PwJco40uVmuXmqDq47f11gknEklIBuQqA"

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
                            text = "Buka Lapak Santri",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Tambah Produk UMKM Baru",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                        )
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = SurfaceCard,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            if (productName.isBlank() || priceText.isBlank()) {
                                toastMessage = "Mohon lengkapi nama produk dan harga"
                                return@Button
                            }
                            if (!agreeSharia) {
                                toastMessage = "Mohon setujui Ikrar Keberkahan Muamalah"
                                return@Button
                            }

                            val newProd = UmkmProduct(
                                id = "prod_${System.currentTimeMillis()}",
                                name = productName.trim(),
                                price = priceText.toIntOrNull() ?: 15000,
                                originalPrice = priceText.toIntOrNull() ?: 15000,
                                category = selectedCategory,
                                sellerName = sellerName,
                                sellerRoom = sellerRoom,
                                sellerPhone = whatsappNumber,
                                photoUrl = defaultPhoto,
                                badge = "Produk Baru",
                                rating = 5.0,
                                soldCount = 0,
                                stock = stockCount,
                                description = description.ifBlank { "Produk olahan & karya mandiri santri pondok pesantren." },
                                status = "Menunggu Kurasi"
                            )
                            onProductSubmitted(newProd)
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Icon(Icons.Default.Upload, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ajukan Produk untuk Kurasi", fontWeight = FontWeight.Bold, fontSize = 13.sp)
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
            // Notice
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = EmeraldPrimaryContainer.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, EmeraldPrimaryContainer.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = EmeraldPrimaryContainer)
                    Text(
                        text = "Setiap produk baru akan dikurasi oleh Pengurus dalam 1x24 jam kerja sebelum tayang di katalog publik.",
                        fontSize = 11.sp,
                        color = TextBody
                    )
                }
            }

            // Photo Upload Container
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SurfaceCard,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceSubtle)
                            .border(1.5.dp, BorderSubtle, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = defaultPhoto,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(6.dp)
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }
                    Text("Foto Produk Utama (Format 1:1, Rasio Persegi)", fontSize = 11.sp, color = TextMuted)
                }
            }

            // Form Inputs
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SurfaceCard,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Informasi Produk", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Nama Produk", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        OutlinedTextField(
                            value = productName,
                            onValueChange = { productName = it },
                            placeholder = { Text("Contoh: Minyak Angin Aromaterapi Barakah", fontSize = 11.sp, color = TextMuted) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Kategori Produk", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                Pair("makanan", "Makanan/Minuman"),
                                Pair("kitab", "Kitab & ATK"),
                                Pair("jasa", "Jasa & Servis"),
                                Pair("pakaian", "Pakaian & Sarung"),
                                Pair("seni", "Karya Kaligrafi")
                            ).forEach { (catKey, catLabel) ->
                                val isSel = selectedCategory == catKey
                                FilterChip(
                                    selected = isSel,
                                    onClick = { selectedCategory = catKey },
                                    label = { Text(catLabel, fontSize = 10.sp) },
                                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = EmeraldPrimaryContainer, selectedLabelColor = Color.White)
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Harga Jual (Rp)", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            OutlinedTextField(
                                value = priceText,
                                onValueChange = { priceText = it },
                                placeholder = { Text("Contoh: 18000", fontSize = 11.sp, color = TextMuted) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Jumlah Stok", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .background(SurfaceSubtle, RoundedCornerShape(12.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                IconButton(onClick = { if (stockCount > 1) stockCount -= 1 }) {
                                    Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(16.dp))
                                }
                                Text("$stockCount", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                IconButton(onClick = { stockCount += 1 }) {
                                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Deskripsi & Manfaat Produk", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            placeholder = { Text("Jelaskan bahan alami, khasiat, keunikan karya santri...", fontSize = 11.sp, color = TextMuted) },
                            minLines = 3,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Sharia Muamalah Agreement
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SurfaceCard,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { agreeSharia = !agreeSharia }
                        .padding(14.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Checkbox(
                        checked = agreeSharia,
                        onCheckedChange = { agreeSharia = it },
                        colors = CheckboxDefaults.colors(checkedColor = EmeraldPrimaryContainer)
                    )
                    Column {
                        Text(
                            text = "Ikrar Keberkahan Muamalah Santri",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = EmeraldPrimaryContainer
                        )
                        Text(
                            text = "Saya menjamin bahwa produk ini 100% halal, thoyyib, karya/usaha mandiri santri, jujur dalam timbangan/kualitas, dan menyisihkan 2.5% keuntungan untuk infaq pondok.",
                            fontSize = 10.sp,
                            color = TextBody,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
