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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.JadwalItem
import com.example.data.SampleData
import com.example.ui.components.SiSantriTopBar
import com.example.ui.theme.*

@Composable
fun JadwalScreen(
    jadwalList: List<JadwalItem> = SampleData.jadwals,
    onAddJadwal: (JadwalItem, Int) -> Unit = { _, _ -> },
    isPengurusMode: Boolean = false,
    onNavigateToPresensi: () -> Unit = {},
    onNavigateToIzin: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var selectedDayIndex by remember { mutableStateOf(4) } // Friday active
    var showCreateScheduleDialog by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    val days = listOf(
        Pair("Sen", "15"),
        Pair("Sel", "16"),
        Pair("Rab", "17"),
        Pair("Kam", "18"),
        Pair("Jum", "19"),
        Pair("Sab", "20"),
        Pair("Ahd", "21")
    )

    val categories = listOf("Semua", "Kajian Kitab", "Halaqah Tahfidz", "Kegiatan Pondok", "Jadwal Ujian")

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
                subtitle = if (isPengurusMode) "Kelola Jadwal Pengajian" else "Jadwal Taklim & Kegiatan",
                showBackButton = true,
                onBackClick = onBackClick,
                roleLabel = if (isPengurusMode) "PENGURUS" else null
            )

            // CALENDAR SYNC BANNER & ATTENDANCE
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(18.dp),
                color = SurfaceCard,
                border = BorderStroke(1.dp, BorderSubtle)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.EventAvailable, contentDescription = null, tint = EmeraldPrimaryContainer, modifier = Modifier.size(16.dp))
                            Text(
                                text = "Terhubung otomatis ke Google Calendar (2-Arah)",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = EmeraldPrimaryContainer, fontSize = 10.sp)
                            )
                        }
                        Surface(
                            color = StatusSuccess.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Tersinkron",
                                color = StatusSuccess,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Kehadiran Kajian Anda Pekan Ini", style = MaterialTheme.typography.bodySmall.copy(color = TextMuted))
                        Text(text = "98% (14 Sesi)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = StatusSuccess))
                    }
                    LinearProgressIndicator(
                        progress = { 0.98f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = StatusSuccess,
                        trackColor = BorderSubtle
                    )
                }
            }

            // WEEKLY CALENDAR STRIP
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "September 2026",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    )
                    Text(
                        text = "7 Rabi'ul Awwal 1448 H",
                        style = MaterialTheme.typography.labelSmall.copy(color = GoldTertiary, fontWeight = FontWeight.Bold)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    days.forEachIndexed { index, (dayName, dateNum) ->
                        val isSelected = selectedDayIndex == index
                        Surface(
                            onClick = { selectedDayIndex = index },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) EmeraldPrimaryContainer else SurfaceCard,
                            border = if (isSelected) null else BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = dayName,
                                    color = if (isSelected) GoldLight else TextMuted,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = dateNum,
                                    color = if (isSelected) Color.White else OnSurface,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                )
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(4.dp)
                                            .clip(CircleShape)
                                            .background(GoldLight)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // CATEGORY FILTER CHIPS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = EmeraldPrimaryContainer,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // SCHEDULE CARDS LIST
            val filteredList = jadwalList.filter { item ->
                val matchesCategory = when (selectedCategory) {
                    "Kajian Kitab" -> item.category == "kitab"
                    "Halaqah Tahfidz" -> item.category == "tahfidz"
                    "Kegiatan Pondok" -> item.category == "asrama"
                    "Jadwal Ujian" -> item.category == "ujian"
                    else -> true
                }
                val matchesSearch = searchQuery.isBlank() ||
                    item.title.contains(searchQuery, ignoreCase = true) ||
                    item.subtitle.contains(searchQuery, ignoreCase = true) ||
                    item.ustadz.contains(searchQuery, ignoreCase = true)
                matchesCategory && matchesSearch
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (filteredList.isEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = SurfaceCard,
                        border = BorderStroke(1.dp, BorderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tidak ada jadwal kegiatan untuk kategori atau pencarian ini.",
                            fontSize = 12.sp,
                            color = TextMuted,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }

                filteredList.forEach { item ->
                    ScheduleCard(
                        item = item,
                        onPresensiClick = onNavigateToPresensi,
                        onSyncCalendarClick = {
                            toastMessage = "Jadwal '${item.title}' ditambahkan ke Google Calendar!"
                        }
                    )
                }

                // BOTTOM CONFLICT / DISPENSATION WIDGET
                Surface(
                    onClick = onNavigateToIzin,
                    shape = RoundedCornerShape(16.dp),
                    color = SurfaceSubtle,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(Icons.Default.HelpOutline, contentDescription = null, tint = EmeraldPrimaryContainer)
                            Column {
                                Text(text = "Bentrok Jadwal / Berhalangan?", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text(text = "Ajukan perizinan sebelum sesi dimulai", fontSize = 10.sp, color = TextMuted)
                            }
                        }
                        Text(
                            text = "Ajukan Izin ->",
                            color = EmeraldPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(72.dp))
            }
        }

        // IF PENGURUS: FLOATING ACTION BUTTON "+ Buat Jadwal Baru"
        if (isPengurusMode) {
            Surface(
                onClick = { showCreateScheduleDialog = true },
                shape = RoundedCornerShape(24.dp),
                color = EmeraldPrimaryContainer,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                    Text("Buat Jadwal Baru", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        // CREATE SCHEDULE DIALOG FOR PENGURUS
        if (showCreateScheduleDialog) {
            CreateScheduleDialog(
                onDismiss = { showCreateScheduleDialog = false },
                onScheduleCreated = { newSchedule, radius ->
                    onAddJadwal(newSchedule, radius)
                    showCreateScheduleDialog = false
                    toastMessage = "Jadwal '${newSchedule.title}' berhasil diterbitkan dan disinkronkan ke kalender santri!"
                }
            )
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
private fun ScheduleCard(
    item: JadwalItem,
    onPresensiClick: () -> Unit,
    onSyncCalendarClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = SurfaceCard,
        border = BorderStroke(1.dp, if (item.isLive) EmeraldPrimaryContainer.copy(alpha = 0.6f) else BorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Header: Category & Live status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        color = when (item.category) {
                            "kitab" -> EmeraldPrimaryContainer.copy(alpha = 0.12f)
                            "tahfidz" -> TealSecondaryContainer.copy(alpha = 0.5f)
                            else -> SurfaceSubtle
                        },
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = when (item.category) {
                                "kitab" -> "Kajian Kitab"
                                "tahfidz" -> "Halaqah Tahfidz"
                                else -> "Kegiatan Asrama"
                            },
                            color = EmeraldPrimaryContainer,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    if (item.isLive) {
                        Surface(
                            color = StatusSuccess.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(StatusSuccess)
                                )
                                Text(
                                    text = "Sedang Berlangsung",
                                    color = StatusSuccess,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                )
                            }
                        }
                    }
                }

                Text(
                    text = item.timeRange,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = TextBody)
                )
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.5.sp)
            )

            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall.copy(color = TextBody, fontSize = 11.5.sp)
            )

            // Info rows: Ustadz and Location
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = EmeraldSecondary, modifier = Modifier.size(14.dp))
                    Text(text = item.ustadz, fontSize = 11.sp, color = TextMuted)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Default.Place, contentDescription = null, tint = StatusWarning, modifier = Modifier.size(14.dp))
                    Text(text = item.location, fontSize = 11.sp, color = TextMuted)
                }
            }

            HorizontalDivider(color = BorderSubtle)

            // Bottom action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onSyncCalendarClick, modifier = Modifier.size(34.dp)) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Sync", tint = EmeraldPrimaryContainer, modifier = Modifier.size(16.dp))
                }

                if (item.isLive) {
                    Button(
                        onClick = onPresensiClick,
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.QrCodeScanner, contentDescription = null, modifier = Modifier.size(15.dp), tint = GoldLight)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Presensi Sekarang", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    OutlinedButton(
                        onClick = onSyncCalendarClick,
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Set Pengingat", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimaryContainer)
                    }
                }
            }
        }
    }
}

@Composable
private fun CreateScheduleDialog(
    onDismiss: () -> Unit,
    onScheduleCreated: (JadwalItem, Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var ustadz by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("Masjid Jami' Pesantren") }
    var timeRange by remember { mutableStateOf("18:30 - 20:00 WIB") }
    var category by remember { mutableStateOf("kitab") }
    var geofenceRadius by remember { mutableStateOf(50f) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.65f))
                .clickable { onDismiss() }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 440.dp)
                    .clickable(enabled = false) {}
                    .shadow(16.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                color = SurfaceCard
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Buat Jadwal Baru (Pengurus)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Tutup")
                        }
                    }

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Nama Pengajian / Kegiatan") },
                        placeholder = { Text("Contoh: Kajian Fiqih Muamalat") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        label = { Text("Materi / Kitab") },
                        placeholder = { Text("Contoh: Kitab Fathul Qorib Bab Jual Beli") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = ustadz,
                        onValueChange = { ustadz = it },
                        label = { Text("Pengampu / Pemateri") },
                        placeholder = { Text("Contoh: Dr. KH. Abdullah Arifin") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Lokasi Pertemuan") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = timeRange,
                        onValueChange = { timeRange = it },
                        label = { Text("Rentang Waktu & Presensi") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Geofence Radius Slider
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Radius GPS Geofence", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text("${geofenceRadius.toInt()} Meter", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimaryContainer)
                        }
                        Slider(
                            value = geofenceRadius,
                            onValueChange = { geofenceRadius = it },
                            valueRange = 25f..200f,
                            colors = SliderDefaults.colors(thumbColor = EmeraldPrimaryContainer, activeTrackColor = EmeraldPrimaryContainer)
                        )
                    }

                    Button(
                        onClick = {
                            val newJadwal = JadwalItem(
                                id = "j_${System.currentTimeMillis()}",
                                title = title.ifBlank { "Kajian Pengurus Terjadwal" },
                                subtitle = subtitle.ifBlank { "Kajian Rutin Pesantren" },
                                ustadz = ustadz.ifBlank { "Dewan Asatidz Pondok" },
                                location = location,
                                timeRange = timeRange,
                                date = "Hari Ini",
                                category = category,
                                isLive = true
                            )
                            onScheduleCreated(newJadwal, geofenceRadius.toInt())
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("Terbitkan & Sinkronkan Jadwal", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
