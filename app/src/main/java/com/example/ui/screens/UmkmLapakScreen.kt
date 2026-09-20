package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.UmkmOrderItem
import com.example.ui.theme.*

@Composable
fun UmkmLapakScreen(
    orders: List<UmkmOrderItem> = SampleData.lapakOrders,
    onUpdateOrderStatus: (String, String) -> Unit = { _, _ -> },
    onNavigateToAddProduct: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var isShopOpen by remember { mutableStateOf(true) }
    var selectedTab by remember { mutableStateOf(0) }

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
                            text = "Lapak Saya",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Manajemen Toko & Penjualan Santri",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToAddProduct,
                containerColor = EmeraldPrimaryContainer,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Tambah Produk Baru", fontWeight = FontWeight.Bold) }
            )
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
            // SHOP HEADER PROFILE
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SurfaceCard,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimaryContainer.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Storefront, contentDescription = null, tint = EmeraldPrimaryContainer, modifier = Modifier.size(26.dp))
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Lapak Herbal Barakah Santri",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Ahmad Fauzi Dahlan • Asrama Al-Farabi B-04",
                                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                            )
                        }
                    }

                    HorizontalDivider(color = BorderSubtle)

                    // Shop status toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Status Lapak", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text(
                                text = if (isShopOpen) "Buka (Menerima Pesanan)" else "Tutup (Sedang Mengaji/Kajian)",
                                fontSize = 10.5.sp,
                                color = if (isShopOpen) StatusSuccess else StatusDanger
                            )
                        }
                        Switch(
                            checked = isShopOpen,
                            onCheckedChange = { isShopOpen = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = EmeraldPrimaryContainer)
                        )
                    }
                }
            }

            // FINANCIAL SUMMARY STATS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Total Omzet",
                    value = "Rp 6,1 Jt",
                    icon = Icons.Default.Payments,
                    color = EmeraldPrimaryContainer,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Terjual",
                    value = "340 pcs",
                    icon = Icons.Default.LocalMall,
                    color = StatusSuccess,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Infaq Santri",
                    value = "Rp 153 Rb",
                    icon = Icons.Default.VolunteerActivism,
                    color = GoldTertiary,
                    modifier = Modifier.weight(1f)
                )
            }

            // TAB SELECTOR: PRODUK SAYA & PESANAN MASUK
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceSubtle, RoundedCornerShape(12.dp))
                    .padding(4.dp)
            ) {
                Surface(
                    onClick = { selectedTab = 0 },
                    shape = RoundedCornerShape(10.dp),
                    color = if (selectedTab == 0) EmeraldPrimaryContainer else Color.Transparent,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Produk Saya (4)",
                        color = if (selectedTab == 0) Color.White else TextMuted,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.5.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Surface(
                    onClick = { selectedTab = 1 },
                    shape = RoundedCornerShape(10.dp),
                    color = if (selectedTab == 1) EmeraldPrimaryContainer else Color.Transparent,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Pesanan Masuk (${orders.size})",
                        color = if (selectedTab == 1) Color.White else TextMuted,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.5.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            // TAB 0: PRODUK SAYA
            if (selectedTab == 0) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SampleData.umkmProducts.take(3).forEach { prod ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = SurfaceCard,
                            border = BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(10.dp))
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
                                    Text(text = "Stok: ${prod.stock} pcs • ${prod.soldCount} terjual", fontSize = 10.sp, color = TextMuted)
                                }

                                Surface(
                                    color = EmeraldPrimaryContainer.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Aktif",
                                        color = EmeraldPrimaryContainer,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // TAB 1: PESANAN MASUK
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    orders.forEach { order ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
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
                                    Text(text = order.id, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = EmeraldPrimaryContainer)
                                    Surface(
                                        color = if (order.status == "Perlu Dikemas") StatusWarning.copy(alpha = 0.15f) else StatusSuccess.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = order.status,
                                            color = if (order.status == "Perlu Dikemas") StatusWarning else StatusSuccess,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Text(text = "${order.buyerName} (${order.buyerRole})", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                Text(text = "${order.productName} x ${order.qty} pcs", fontSize = 11.sp, color = TextBody)
                                Text(text = "Total: Rp ${String.format("%,d", order.totalPrice).replace(',', '.')}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = EmeraldPrimaryContainer)
                                Text(text = "Metode: ${order.pickupMethod}", fontSize = 10.sp, color = TextMuted)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${order.buyerPhone}?text=Assalamu'alaikum%20akhi%2C%20pesanan%20kamu%20${order.id}%20sudah%20kami%20siapkan"))
                                            context.startActivity(intent)
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.Chat, contentDescription = null, tint = WhatsappGreen, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Chat Pembeli", fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = {
                                            onUpdateOrderStatus(order.id, "Siap Diambil")
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1.3f)
                                    ) {
                                        Text("Tandai Siap", fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = SurfaceCard,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
            Text(text = title, fontSize = 9.sp, color = TextMuted)
            Text(text = value, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = OnSurface)
        }
    }
}
