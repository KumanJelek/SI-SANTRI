package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.data.*
import com.example.ui.components.SiSantriTopBar
import com.example.ui.components.TanyaSiSantriModal
import com.example.ui.theme.*

@Composable
fun PengurusDashboardScreen(
    pengurus: PengurusProfile = SampleData.defaultPengurus,
    izinList: List<IzinQueueItem> = SampleData.izinQueue,
    presensiLiveList: List<PresensiLiveItem> = SampleData.presensiLive,
    onApproveIzin: (String) -> Unit = {},
    onRejectIzin: (String) -> Unit = {},
    onNavigateToJadwal: () -> Unit = {},
    onNavigateToKurasiUmkm: () -> Unit = {},
    onNavigateToRegisterSantri: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var showSupportModal by remember { mutableStateOf(false) }
    var showCanvaModal by remember { mutableStateOf(false) }
    var showScanModal by remember { mutableStateOf(false) }
    var selectedFilterGroup by remember { mutableStateOf("Semua") }
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
                subtitle = "Dashboard Pengurus",
                roleLabel = "PENGURUS PUSAT",
                userInitials = "M",
                userName = "Ust. Marzuki",
                userRoom = "Pusat",
                onProfileClick = {
                    toastMessage = "Profil: ${pengurus.name} (${pengurus.nip})"
                }
            )

            // GREETING BANNER
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                color = SurfaceCard,
                border = BorderStroke(1.dp, BorderSubtle)
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
                            text = pengurus.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = EmeraldPrimaryContainer
                            )
                        )
                        Text(
                            text = "${pengurus.authority} • 19 Sep 2026",
                            style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
                        )
                    }

                    OutlinedButton(
                        onClick = onLogout,
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null, tint = StatusDanger, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Keluar", color = StatusDanger, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // 4 KEY METRICS STATS CARDS
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        title = "Hadir Pusat",
                        value = "1.248",
                        subtitle = "dari 1.350 santri (92.4%)",
                        icon = Icons.Default.People,
                        color = StatusSuccess,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "Izin & Sakit",
                        value = "42",
                        subtitle = "${izinList.count { it.status == "Menunggu" }} perlu ACC pengurus",
                        icon = Icons.Default.Sick,
                        color = StatusWarning,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        title = "Alfa / Belum Absen",
                        value = "15",
                        subtitle = "Notifikasi wali santri dikirim",
                        icon = Icons.Default.Warning,
                        color = StatusDanger,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "Agenda Hari Ini",
                        value = "Kajian Akbar",
                        subtitle = "18:30 WIB • Masjid Jami'",
                        icon = Icons.Default.Event,
                        color = EmeraldPrimaryContainer,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // OPERASIONAL MENU GRID
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Menu Operasional Pengurus",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OperasionalItem(
                        icon = Icons.Default.QrCodeScanner,
                        title = "Pindai KTS",
                        color = EmeraldPrimaryContainer,
                        modifier = Modifier.weight(1f),
                        onClick = { showScanModal = true }
                    )
                    OperasionalItem(
                        icon = Icons.Default.FactCheck,
                        title = "Verif Izin",
                        badge = "${izinList.count { it.status == "Menunggu" }}",
                        color = StatusWarning,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            toastMessage = "Memfokuskan ke antrean izin santri..."
                        }
                    )
                    OperasionalItem(
                        icon = Icons.Default.QuestionAnswer,
                        title = "Jawab Santri",
                        badge = "3",
                        color = TealSecondary,
                        modifier = Modifier.weight(1f),
                        onClick = { showSupportModal = true }
                    )
                    OperasionalItem(
                        icon = Icons.Default.Storefront,
                        title = "Kelola UMKM",
                        color = CanvaPurple,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToKurasiUmkm
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OperasionalItem(
                        icon = Icons.Default.CalendarMonth,
                        title = "Kelola Jadwal",
                        color = EmeraldSecondary,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToJadwal
                    )
                    OperasionalItem(
                        icon = Icons.Default.CreditCard,
                        title = "Canva KTS",
                        color = Color(0xFF0077B6),
                        modifier = Modifier.weight(1f),
                        onClick = { showCanvaModal = true }
                    )
                    OperasionalItem(
                        icon = Icons.Default.Campaign,
                        title = "Maklumat",
                        color = StatusDanger,
                        modifier = Modifier.weight(1f),
                        onClick = { toastMessage = "Membuka form publikasi edaran resmi..." }
                    )
                    OperasionalItem(
                        icon = Icons.Default.PersonAdd,
                        title = "Akun Santri",
                        color = EmeraldPrimaryContainer,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToRegisterSantri
                    )
                }
            }

            // VERIFIKASI IZIN (QUEUE ANTREAN SANTRI)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Verifikasi Izin Santri",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    )
                    Surface(
                        color = StatusWarning.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "${izinList.count { it.status == "Menunggu" }} Perlu Persetujuan",
                            color = StatusWarning,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.5.sp),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                izinList.forEach { item ->
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = SurfaceCard,
                        border = BorderStroke(1.dp, BorderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, BorderSubtle, CircleShape)
                                ) {
                                    AsyncImage(
                                        model = item.photoUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text(text = item.santriName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Surface(
                                            color = if (item.type == "Sakit") StatusWarning.copy(alpha = 0.15f) else TealSecondaryContainer.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = item.type,
                                                color = if (item.type == "Sakit") StatusWarning else TealSecondary,
                                                fontSize = 8.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                    Text(text = "${item.kamar} • ${item.submittedTime}", fontSize = 10.sp, color = TextMuted)
                                    Text(text = item.reason, fontSize = 11.5.sp, color = TextBody)
                                }
                            }

                            Surface(
                                color = SurfaceSubtle,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(Icons.Default.AttachFile, contentDescription = null, tint = EmeraldPrimaryContainer, modifier = Modifier.size(13.dp))
                                    Text(text = item.attachmentName, fontSize = 9.5.sp, color = TextMuted)
                                }
                            }

                            if (item.status == "Menunggu") {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            onRejectIzin(item.id)
                                            toastMessage = "Izin ${item.santriName} ditolak."
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Tolak", color = StatusDanger, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = {
                                            onApproveIzin(item.id)
                                            toastMessage = "Izin ${item.santriName} disetujui Pengurus Pusat."
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1.5f)
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Setujui Izin", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            } else {
                                Surface(
                                    color = if (item.status == "Disetujui") StatusSuccess.copy(alpha = 0.15f) else StatusDanger.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Status: ${item.status}",
                                        color = if (item.status == "Disetujui") StatusSuccess else StatusDanger,
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
            }

            // AKTIVITAS PRESENSI TERKINI ASRAMA (LIVE FEED)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Aktivitas Presensi Terkini",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                )

                // Room Filter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Semua", "B-01 s/d B-06", "B-07 s/d B-12").forEach { grp ->
                        val isSel = selectedFilterGroup == grp
                        FilterChip(
                            selected = isSel,
                            onClick = { selectedFilterGroup = grp },
                            label = { Text(grp, fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EmeraldPrimaryContainer,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                val filteredStreams = presensiLiveList.filter {
                    when (selectedFilterGroup) {
                        "B-01 s/d B-06" -> it.group == "b01-b06"
                        "B-07 s/d B-12" -> it.group == "b07-b12"
                        else -> true
                    }
                }

                filteredStreams.forEach { stream ->
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = SurfaceCard,
                        border = BorderStroke(1.dp, BorderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldPrimaryContainer.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stream.initials,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldPrimaryContainer,
                                    fontSize = 12.sp
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(text = stream.name, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Text(text = "Kamar ${stream.kamar}", fontSize = 10.sp, color = TextMuted)
                                }
                                Text(text = stream.activity, fontSize = 11.sp, color = TextBody)
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Surface(
                                    color = EmeraldPrimaryContainer.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = stream.method,
                                        color = EmeraldPrimaryContainer,
                                        fontSize = 8.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                    )
                                }
                                Text(text = stream.timeAgo, fontSize = 9.sp, color = TextMuted)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(72.dp))
            }
        }

        // Tanya Si Santri Modal (Answer santri questions)
        if (showSupportModal) {
            TanyaSiSantriModal(onDismiss = { showSupportModal = false })
        }

        // Pindai Barcode Scanner Modal
        if (showScanModal) {
            Dialog(onDismissRequest = { showScanModal = false }) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SurfaceCard,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text("Pindai Barcode KTS Santri", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Box(
                            modifier = Modifier
                                .size(220.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = EmeraldPrimaryFixed, modifier = Modifier.size(80.dp))
                        }
                        Text("Arahkan kamera ke barcode KTS digital atau kartu fisik santri", fontSize = 11.sp, color = TextMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        Button(
                            onClick = {
                                showScanModal = false
                                toastMessage = "Santri Ahmad Fauzi (202609012) diverifikasi Hadir!"
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Simulasikan Scan Sukses")
                        }
                    }
                }
            }
        }

        // Canva KTS Designer Modal
        if (showCanvaModal) {
            Dialog(onDismissRequest = { showCanvaModal = false }) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SurfaceCard,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Canva KTS Card Designer", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            IconButton(onClick = { showCanvaModal = false }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }
                        Text("Desain kartu identitas CR80 resmi dengan template standar Kemenag & Pesantren Salaf.", fontSize = 11.sp, color = TextMuted)
                        Button(
                            onClick = {
                                showCanvaModal = false
                                toastMessage = "Membuka template resmi di Canva Editor..."
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CanvaPurple),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Buka di Canva Designer")
                        }
                    }
                }
            }
        }

        // Toast Feedback
        AnimatedVisibility(
            visible = toastMessage != null,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {
            Surface(
                color = OnSurface,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldPrimaryFixed)
                    Text(text = toastMessage ?: "", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = SurfaceCard,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 11.sp, color = TextMuted, fontWeight = FontWeight.SemiBold)
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
                }
            }
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = OnSurface)
            Text(text = subtitle, fontSize = 9.5.sp, color = TextMuted, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun OperasionalItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    color: Color,
    badge: String? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = SurfaceCard,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = modifier
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
                }
                Text(
                    text = title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    maxLines = 1
                )
            }

            if (badge != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 2.dp, y = (-2).dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(StatusDanger),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = badge, color = Color.White, fontSize = 8.5.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
