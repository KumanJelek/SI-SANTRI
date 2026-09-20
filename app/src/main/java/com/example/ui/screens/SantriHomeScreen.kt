package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.SantriProfile
import com.example.ui.components.KtsDigitalModal
import com.example.ui.components.SiSantriTopBar
import com.example.ui.components.TanyaSiSantriModal
import com.example.ui.theme.*

@Composable
fun SantriHomeScreen(
    santri: SantriProfile = SampleData.defaultSantri,
    onNavigateToPresensi: (initialTab: Int) -> Unit = {},
    onNavigateToJadwal: () -> Unit = {},
    onNavigateToUmkm: () -> Unit = {},
    onNavigateToRegistrasi: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var showKtsModal by remember { mutableStateOf(false) }
    var showSupportModal by remember { mutableStateOf(false) }
    var showProfileDropdown by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceCanvas)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            SiSantriTopBar(
                title = "SI-SANTRI",
                subtitle = "Beranda Santri",
                userInitials = santri.name.take(1),
                userName = santri.name.split(" ").firstOrNull() ?: "Santri",
                userRoom = santri.asrama.replace("Asrama ", ""),
                onNotificationClick = {
                    toastMessage = "Pengumuman: Jadwal Libur Semester Gasal telah diterbitkan."
                },
                onProfileClick = { showProfileDropdown = !showProfileDropdown }
            )

            // Profile Dropdown Box if open
            AnimatedVisibility(visible = showProfileDropdown) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    color = SurfaceCard,
                    border = BorderStroke(1.dp, BorderSubtle)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, GoldLight, CircleShape)
                            ) {
                                AsyncImage(
                                    model = santri.photoUrl,
                                    contentDescription = "Foto",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = santri.name,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "NIS: ${santri.nis} • ${santri.kamar}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    showProfileDropdown = false
                                    showKtsModal = true
                                },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("KTS Card", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimaryContainer)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = BorderSubtle)

                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            DropdownItem(
                                icon = Icons.Default.Edit,
                                text = "Lengkapi / Edit Biodata Santri",
                                onClick = {
                                    showProfileDropdown = false
                                    onNavigateToRegistrasi()
                                }
                            )
                            DropdownItem(
                                icon = Icons.Default.QrCodeScanner,
                                text = "Buka Presensi & Swafoto",
                                onClick = {
                                    showProfileDropdown = false
                                    onNavigateToPresensi(0)
                                }
                            )
                            DropdownItem(
                                icon = Icons.Default.SupportAgent,
                                text = "Pusat Bantuan Si Santri (Online)",
                                onClick = {
                                    showProfileDropdown = false
                                    showSupportModal = true
                                }
                            )
                            DropdownItem(
                                icon = Icons.Default.ExitToApp,
                                text = "Keluar Aplikasi",
                                textColor = StatusDanger,
                                onClick = {
                                    showProfileDropdown = false
                                    onLogout()
                                }
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // MINIMALIST GREETING
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SurfaceCard,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(StatusSuccess)
                                )
                                Text(
                                    text = "Assalamu'alaikum,",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )
                            }
                            Text(
                                text = santri.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = EmeraldPrimaryContainer
                                )
                            )
                            Text(
                                text = "${santri.asrama} • ${santri.kamar}",
                                style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 11.sp)
                            )
                        }

                        Button(
                            onClick = { showKtsModal = true },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(16.dp), tint = GoldLight)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("KTS Digital", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // COMPACT STANDARD CR80 MINI CARD PREVIEW
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    EmeraldPrimaryContainer,
                                    EmeraldPrimary,
                                    Color(0xFF032615)
                                )
                            )
                        )
                        .border(1.dp, GoldLight.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
                        .clickable { showKtsModal = true }
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Student Photo with Gold Border
                        Box(
                            modifier = Modifier
                                .width(48.dp)
                                .height(64.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.5.dp, GoldLight, RoundedCornerShape(8.dp))
                        ) {
                            AsyncImage(
                                model = santri.photoUrl,
                                contentDescription = "Foto",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    color = GoldLight.copy(alpha = 0.25f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "KTS CR80 AKTIF",
                                        color = GoldLight,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 8.5.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                                Text(
                                    text = "1445/1446 H",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 9.sp
                                )
                            }
                            Text(
                                text = santri.name,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "NIS: ${santri.nis} • Takhasus Kitab",
                                color = EmeraldPrimaryFixedDim,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.QrCode,
                                        contentDescription = null,
                                        tint = EmeraldPrimaryContainer,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Buka ->",
                                color = GoldLight,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // LAYANAN UTAMA (QUICK SERVICES 4 ICONS)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Layanan Utama Santri",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = OnSurface
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ServiceCard(
                            title = "Presensi",
                            badge = "Wajah Siap",
                            icon = Icons.Default.Face,
                            iconColor = EmeraldPrimaryContainer,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigateToPresensi(0) }
                        )
                        ServiceCard(
                            title = "Jadwal",
                            badge = "4 Sesi",
                            icon = Icons.Default.CalendarToday,
                            iconColor = TealSecondary,
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToJadwal
                        )
                        ServiceCard(
                            title = "Perizinan",
                            badge = "Dispensasi",
                            icon = Icons.Default.AssignmentLate,
                            iconColor = StatusWarning,
                            modifier = Modifier.weight(1f),
                            onClick = { onNavigateToPresensi(2) }
                        )
                        ServiceCard(
                            title = "Koperasi",
                            badge = "Buka",
                            icon = Icons.Default.ShoppingBag,
                            iconColor = CanvaPurple,
                            modifier = Modifier.weight(1f),
                            onClick = onNavigateToUmkm
                        )
                    }
                }

                // AGENDA & KEHADIRAN HARI INI
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SurfaceCard,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Agenda & Kajian Hari Ini",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            )
                            Surface(
                                color = StatusSuccess.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Sedang Berlangsung",
                                    color = StatusSuccess,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Ongoing Kajian Card
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = SurfaceSubtle,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(EmeraldPrimaryContainer.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = EmeraldPrimaryContainer
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Kajian Kitab Bulughul Maram",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "18:30 WIB • Ba'da Maghrib",
                                        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
                                    )
                                    Text(
                                        text = "KH. Abdullah • Masjid Utama Lt. 1",
                                        style = MaterialTheme.typography.labelSmall.copy(color = EmeraldSecondary, fontSize = 10.sp)
                                    )
                                }
                                Button(
                                    onClick = { onNavigateToPresensi(0) },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text("Presensi", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        // Progress Bar Kehadiran Kitab Pekan Ini
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Kehadiran Kitab Pekan Ini",
                                    style = MaterialTheme.typography.labelSmall.copy(color = TextBody, fontWeight = FontWeight.SemiBold)
                                )
                                Text(
                                    text = "${santri.presenceRate}% (14/14 Sesi)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = StatusSuccess,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            LinearProgressIndicator(
                                progress = { santri.presenceRate / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = StatusSuccess,
                                trackColor = BorderSubtle
                            )
                        }
                    }
                }

                // PENGUMUMAN RESMI PONDOK
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Pengumuman Resmi Pondok",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    )

                    SampleData.pengumumanList.forEach { pengumuman ->
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = SurfaceCard,
                            border = BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = StatusDanger.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = pengumuman.authorTag,
                                            color = StatusDanger,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 8.5.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = pengumuman.date,
                                        style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 9.sp)
                                    )
                                }

                                Text(
                                    text = pengumuman.title,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.5.sp),
                                    lineHeight = 18.sp
                                )

                                Text(
                                    text = pengumuman.desc,
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextBody, fontSize = 11.sp, lineHeight = 16.sp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Dibaca oleh ${pengumuman.readCount} santri",
                                        style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 9.5.sp)
                                    )
                                    Text(
                                        text = "Baca Edaran ->",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = EmeraldPrimaryContainer,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        ),
                                        modifier = Modifier.clickable {
                                            toastMessage = "Membuka dokumen edaran resmi pengurus..."
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // ARTIKEL & MAU'IZHAH PENGURUS
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Artikel & Mau'izhah Pengurus",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    )

                    SampleData.artikelList.forEach { artikel ->
                        Surface(
                            shape = RoundedCornerShape(18.dp),
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
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(TealSecondaryContainer.copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoStories,
                                        contentDescription = null,
                                        tint = TealSecondary
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Surface(
                                        color = EmeraldPrimaryContainer.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = artikel.category,
                                            color = EmeraldPrimaryContainer,
                                            fontSize = 8.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = artikel.title,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Oleh ${artikel.author} • ${artikel.readTime}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 10.sp)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = TextMuted
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(72.dp))
            }
        }

        // FLOATING ACTION BUTTON: "Tanya Si Santri - Layanan 24 Jam"
        Surface(
            onClick = { showSupportModal = true },
            shape = RoundedCornerShape(28.dp),
            color = EmeraldPrimaryContainer,
            shadowElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(GoldLight.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SupportAgent,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "Tanya Si Santri",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        // KTS Digital Modal
        if (showKtsModal) {
            KtsDigitalModal(santri = santri, onDismiss = { showKtsModal = false })
        }

        // Tanya Si Santri Modal
        if (showSupportModal) {
            TanyaSiSantriModal(onDismiss = { showSupportModal = false })
        }
    }
}

@Composable
private fun ServiceCard(
    title: String,
    badge: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = SurfaceCard,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                color = OnSurface,
                maxLines = 1
            )
            Surface(
                color = iconColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = badge,
                    color = iconColor,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.5.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                )
            }
        }
    }
}

@Composable
private fun DropdownItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    textColor: Color = OnSurface,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = textColor, modifier = Modifier.size(18.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall.copy(color = textColor, fontSize = 12.sp))
    }
}
