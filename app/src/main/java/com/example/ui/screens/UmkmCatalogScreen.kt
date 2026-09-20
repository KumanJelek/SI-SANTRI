package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.UmkmProduct
import com.example.ui.components.SiSantriTopBar
import com.example.ui.theme.*

@Composable
fun UmkmCatalogScreen(
    productList: List<UmkmProduct> = SampleData.umkmProducts,
    isMarketplaceActive: Boolean = true,
    onToggleMarketplace: (Boolean) -> Unit = {},
    onProductClick: (UmkmProduct) -> Unit,
    onNavigateToAddProduct: () -> Unit,
    onNavigateToLapakSaya: () -> Unit,
    onNavigateToKurasi: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    val categories = listOf(
        "Semua",
        "Makanan & Minuman",
        "Kitab & Alat Tulis",
        "Jasa & Servis",
        "Pakaian & Sarung",
        "Karya Kaligrafi"
    )

    val products = productList.filter { product ->
        (selectedCategory == "Semua" || when (selectedCategory) {
            "Makanan & Minuman" -> product.category == "makanan"
            "Kitab & Alat Tulis" -> product.category == "kitab"
            "Jasa & Servis" -> product.category == "jasa"
            "Pakaian & Sarung" -> product.category == "pakaian"
            "Karya Kaligrafi" -> product.category == "seni"
            else -> true
        }) && (searchQuery.isBlank() || product.name.contains(searchQuery, ignoreCase = true) || product.sellerName.contains(searchQuery, ignoreCase = true))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCanvas)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            SiSantriTopBar(
                title = "SI-SANTRI",
                subtitle = "Katalog UMKM Santri",
                showBackButton = true,
                onBackClick = onBackClick
            )

            // Search Bar & Filter Header
            Surface(
                color = SurfaceCard,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Cari", tint = TextMuted)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Hapus")
                                }
                            }
                        },
                        placeholder = { Text("Cari produk karya santri pondok...", fontSize = 12.sp, color = TextMuted) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceSubtle,
                            unfocusedContainerColor = SurfaceSubtle,
                            focusedBorderColor = EmeraldPrimaryContainer,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Categories Horizontal Strip
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSel = selectedCategory == cat
                            FilterChip(
                                selected = isSel,
                                onClick = { selectedCategory = cat },
                                label = { Text(cat, fontSize = 11.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = EmeraldPrimaryContainer,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Product Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header Banner: Ekonomi Berkah Santri
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
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
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(EmeraldPrimaryContainer.copy(alpha = 0.12f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.Storefront, contentDescription = null, tint = EmeraldPrimaryContainer, modifier = Modifier.size(18.dp))
                                    }
                                    Column {
                                        Text(text = "Ekonomi Berkah Santri", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(text = "100% Karya & Usaha Mandiri Santri", fontSize = 10.sp, color = TextMuted)
                                    }
                                }

                                OutlinedButton(
                                    onClick = onNavigateToLapakSaya,
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text("Lapak Saya", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimaryContainer)
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Status Pasar Digital",
                                    fontSize = 11.sp,
                                    color = TextBody,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = if (isMarketplaceActive) "Buka (Online)" else "Tutup Sementara",
                                        fontSize = 10.5.sp,
                                        color = if (isMarketplaceActive) StatusSuccess else StatusDanger,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Switch(
                                        checked = isMarketplaceActive,
                                        onCheckedChange = {
                                            onToggleMarketplace(it)
                                            toastMessage = if (it) "Pasar UMKM dibuka kembali untuk santri." else "Pasar UMKM ditutup sementara."
                                        },
                                        modifier = Modifier.scale(0.7f),
                                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = EmeraldPrimaryContainer)
                                    )
                                }
                            }
                        }
                    }
                }

                // Grid of items
                items(products) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = { onProductClick(product) },
                        onBuyWaClick = {
                            val msg = "Assalamu'alaikum%20akhi%2C%20saya%20ingin%20memesan%20${product.name}%20(Rp%20${product.price})%20apakah%20stok%20masih%20tersedia%3F"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${product.sellerPhone}?text=$msg"))
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }

        // Bottom Actions: + Buka Lapak & Kurasi Button
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(14.dp),
            shape = RoundedCornerShape(20.dp),
            color = SurfaceCard,
            shadowElevation = 8.dp,
            border = BorderStroke(1.dp, BorderSubtle)
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateToKurasi,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = GoldTertiary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Kurasi Pengurus", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldTertiary)
                }

                Button(
                    onClick = onNavigateToAddProduct,
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1.3f)
                ) {
                    Icon(Icons.Default.AddBusiness, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("+ Buka Lapak Baru", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: UmkmProduct,
    onProductClick: () -> Unit,
    onBuyWaClick: () -> Unit
) {
    Surface(
        onClick = onProductClick,
        shape = RoundedCornerShape(16.dp),
        color = SurfaceCard,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Photo with Badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                AsyncImage(
                    model = product.photoUrl,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Badge top left
                Surface(
                    color = EmeraldPrimaryContainer,
                    shape = RoundedCornerShape(bottomEnd = 10.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = product.badge,
                        color = GoldLight,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 15.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rp ${String.format("%,d", product.price).replace(',', '.')}",
                        color = EmeraldPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )

                    if (product.originalPrice > product.price) {
                        Text(
                            text = "Rp ${String.format("%,d", product.originalPrice).replace(',', '.')}",
                            color = TextMuted,
                            fontSize = 9.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = GoldLight, modifier = Modifier.size(11.dp))
                    Text(text = "${product.rating}", fontSize = 9.5.sp, fontWeight = FontWeight.Bold, color = TextBody)
                    Text(text = "• ${product.soldCount} terjual", fontSize = 9.sp, color = TextMuted)
                }

                Text(
                    text = product.sellerName,
                    fontSize = 9.5.sp,
                    color = TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Button(
                    onClick = onBuyWaClick,
                    colors = ButtonDefaults.buttonColors(containerColor = WhatsappGreen),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(13.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Beli via WA", fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

private fun Modifier.scale(scale: Float): Modifier = this.then(Modifier.size((50 * scale).dp, (30 * scale).dp))
