package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.UmkmProduct
import com.example.ui.theme.*

@Composable
fun UmkmDetailScreen(
    product: UmkmProduct,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var selectedVariant by remember { mutableStateOf(product.variants.firstOrNull() ?: "Original") }
    var selectedPickupMethod by remember { mutableStateOf("COD Depan Masjid Jami (Usai Isya Berjamaah)") }
    var quantity by remember { mutableStateOf(1) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    val pickupOptions = listOf(
        "COD Depan Masjid Jami (Usai Isya Berjamaah)",
        "Titip Meja Kasir Koperasi OSPAI",
        "Kirim Ekspedisi ke Luar Pondok"
    )

    val totalPrice = product.price * quantity

    val waMessage = """
        Assalamu'alaikum Warahmatullah Akhi/Ukhti ${product.sellerName},
        
        Saya ingin membeli produk santri berikut:
        • Produk: ${product.name}
        • Varian: $selectedVariant
        • Jumlah: $quantity pcs
        • Total Harga: Rp ${String.format("%,d", totalPrice).replace(',', '.')}
        • Metode Pengambilan: $selectedPickupMethod
        
        Apakah pesanan ini siap diproses? Syukron jazakumullah khairan katsiran.
    """.trimIndent()

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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                    Text(
                        text = "Detail Produk Santri",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    IconButton(onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Beli ${product.name} di SI-SANTRI UMKM: https://wa.me/${product.sellerPhone}")
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Bagikan Produk"))
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Bagikan")
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = SurfaceCard,
                shadowElevation = 12.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Stepper (- 1 +)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceSubtle,
                        border = BorderStroke(1.dp, BorderSubtle)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity -= 1 },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Kurang", modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = "$quantity",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(
                                onClick = { if (quantity < product.stock) quantity += 1 },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Tambah", modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    // Direct Buy via WhatsApp CTA
                    Button(
                        onClick = {
                            val encodedMsg = Uri.encode(waMessage)
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${product.sellerPhone}?text=$encodedMsg"))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Beli via WA (Rp ${String.format("%,d", totalPrice).replace(',', '.')})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.5.sp,
                            color = Color.White
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
        ) {
            // Main Photo Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                AsyncImage(
                    model = product.photoUrl,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Surface(
                    color = EmeraldPrimaryContainer,
                    shape = RoundedCornerShape(bottomEnd = 12.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = product.badge,
                        color = GoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    color = Color.Black.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                        Text(text = "1 / 4 Foto", color = Color.White, fontSize = 10.sp)
                    }
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title and Price Section
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Rp ${String.format("%,d", product.price).replace(',', '.')}",
                            color = EmeraldPrimaryContainer,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        )

                        if (product.originalPrice > product.price) {
                            Text(
                                text = "Rp ${String.format("%,d", product.originalPrice).replace(',', '.')}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = TextMuted,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            )
                            Surface(
                                color = StatusDanger.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "-10% Berkah Santri",
                                    color = StatusDanger,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = GoldLight, modifier = Modifier.size(14.dp))
                            Text(text = "${product.rating}", fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                        }
                        Text(text = "• ${product.soldCount}+ Terjual", fontSize = 11.5.sp, color = TextMuted)
                        Text(text = "• Stok: ${product.stock} pcs", fontSize = 11.5.sp, color = EmeraldSecondary, fontWeight = FontWeight.SemiBold)
                    }
                }

                // SANTRI SELLER CARD
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SurfaceCard,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimaryContainer.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Store, contentDescription = null, tint = EmeraldPrimaryContainer)
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = product.sellerName,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                            )
                            Text(
                                text = "${product.sellerRoom} • Santri Aktif",
                                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${product.sellerPhone}?text=Assalamu'alaikum%20saya%20tertarik%20dengan%20produk%20lapak%20Anda"))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text("Chat WA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimaryContainer)
                        }
                    }
                }

                // 4 PILLARS OF TRUST
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Jaminan Berkah & Kualitas Santri",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TrustPillar(icon = Icons.Default.Verified, title = "100% Halal", desc = "Bahan Thayyib", modifier = Modifier.weight(1f))
                        TrustPillar(icon = Icons.Default.Handshake, title = "Karya Mandiri", desc = "Santri Mukim", modifier = Modifier.weight(1f))
                        TrustPillar(icon = Icons.Default.Spa, title = "Bidara & Zaitun", desc = "Herbal Alami", modifier = Modifier.weight(1f))
                        TrustPillar(icon = Icons.Default.VolunteerActivism, title = "Infaq 2.5%", desc = "SPP Mandiri", modifier = Modifier.weight(1f))
                    }
                }

                // VARIANT PICKER
                if (product.variants.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Pilihan Varian Aroma",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            product.variants.forEach { variant ->
                                val isSel = selectedVariant == variant
                                Surface(
                                    onClick = { selectedVariant = variant },
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isSel) EmeraldPrimaryContainer else SurfaceCard,
                                    border = BorderStroke(1.dp, if (isSel) EmeraldPrimaryContainer else BorderSubtle)
                                ) {
                                    Text(
                                        text = variant,
                                        color = if (isSel) Color.White else OnSurface,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // KHASIAT & KOMPOSISI
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SurfaceCard,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "Khasiat & Komposisi Alami", fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                        Text(text = product.description, fontSize = 12.sp, color = TextBody, lineHeight = 18.sp)

                        if (product.ingredients.isNotEmpty()) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                product.ingredients.forEach { ing ->
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldSecondary, modifier = Modifier.size(13.dp))
                                        Text(text = ing, fontSize = 11.5.sp, color = TextMuted)
                                    }
                                }
                            }
                        }
                    }
                }

                // METODE PENGAMBILAN
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Metode Pengambilan Pesanan",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
                    )

                    pickupOptions.forEach { opt ->
                        val isSel = selectedPickupMethod == opt
                        Surface(
                            onClick = { selectedPickupMethod = opt },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSel) EmeraldPrimaryContainer.copy(alpha = 0.08f) else SurfaceCard,
                            border = BorderStroke(1.dp, if (isSel) EmeraldPrimaryContainer else BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RadioButton(
                                    selected = isSel,
                                    onClick = { selectedPickupMethod = opt },
                                    colors = RadioButtonDefaults.colors(selectedColor = EmeraldPrimaryContainer)
                                )
                                Text(
                                    text = opt,
                                    fontSize = 11.5.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) EmeraldPrimaryContainer else TextBody
                                )
                            }
                        }
                    }
                }

                // DYNAMIC LIVE WHATSAPP CHAT GENERATOR PREVIEW
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SurfaceSubtle,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.Chat, contentDescription = null, tint = WhatsappGreen, modifier = Modifier.size(16.dp))
                                Text(text = "Preview Format Pesan WhatsApp", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            TextButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("Pesan WA", waMessage))
                                    toastMessage = "Format pesan disalin ke clipboard!"
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Salin Teks", fontSize = 11.sp, color = EmeraldPrimaryContainer, fontWeight = FontWeight.Bold)
                            }
                        }
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SurfaceCard,
                            border = BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = waMessage,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.5.sp, color = TextBody, lineHeight = 15.sp),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun TrustPillar(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = SurfaceCard,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(icon, contentDescription = null, tint = EmeraldPrimaryContainer, modifier = Modifier.size(18.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 9.5.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Text(text = desc, fontSize = 8.5.sp, color = TextMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}
