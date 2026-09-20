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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.SantriProfile
import com.example.ui.components.SiSantriTopBar
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SantriPresensiScreen(
    santri: SantriProfile = SampleData.defaultSantri,
    initialTab: Int = 0,
    onPresensiSuccess: (activity: String, method: String) -> Unit = { _, _ -> },
    onIzinSubmitted: (type: String, reason: String, attachment: String) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(initialTab) }
    var isFlashOn by remember { mutableStateOf(false) }
    var isFrontCamera by remember { mutableStateOf(true) }
    var isSubmittingPresence by remember { mutableStateOf(false) }
    var presenceSuccess by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    // QR Countdown timer
    var qrSecondsLeft by remember { mutableStateOf(45) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            if (qrSecondsLeft > 1) {
                qrSecondsLeft -= 1
            } else {
                qrSecondsLeft = 45
            }
        }
    }

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
                subtitle = "Presensi & Absensi Santri",
                showBackButton = true,
                onBackClick = onBackClick,
                userInitials = santri.name.take(1),
                userName = santri.name.split(" ").firstOrNull() ?: "Santri",
                userRoom = santri.kamar
            )

            // Tabs Selector
            Surface(
                color = SurfaceCard,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .background(SurfaceSubtle, RoundedCornerShape(12.dp))
                        .padding(4.dp)
                ) {
                    TabPill(
                        title = "Swafoto & GPS",
                        icon = Icons.Default.CameraAlt,
                        isSelected = selectedTab == 0,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedTab = 0 }
                    )
                    TabPill(
                        title = "QR Code KTS",
                        icon = Icons.Default.QrCode2,
                        isSelected = selectedTab == 1,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedTab = 1 }
                    )
                    TabPill(
                        title = "Izin",
                        icon = Icons.Default.AssignmentLate,
                        isSelected = selectedTab == 2,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedTab = 2 }
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (selectedTab) {
                    0 -> {
                        // TAB 1: SWAFOTO & GPS
                        // Active Schedule Context Card
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = SurfaceCard,
                            border = BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = EmeraldPrimaryContainer.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "Jadwal Presensi Dibuka",
                                            color = EmeraldPrimaryContainer,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Surface(
                                        color = StatusDanger.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(3.dp),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Icon(Icons.Default.Timer, contentDescription = null, tint = StatusDanger, modifier = Modifier.size(12.dp))
                                            Text(
                                                text = "Sisa 12 Menit",
                                                color = StatusDanger,
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = "Kajian Kitab Aqidatul Awam (Ba'da Maghrib)",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "KH. Abdullah • Masjid Utama Pesantren",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )
                                Text(
                                    text = "Waktu Presensi: 18.15 - 18.45 WIB",
                                    style = MaterialTheme.typography.labelSmall.copy(color = EmeraldSecondary, fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }

                        // CAMERA VIEWFINDER WITH FACE OVAL
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(380.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(Color(0xFF0F172A))
                        ) {
                            // Simulated Camera Stream
                            AsyncImage(
                                model = santri.photoUrl,
                                contentDescription = "Live Camera Preview",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .alpha(0.85f)
                            )

                            // Oval Face Guide
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .width(200.dp)
                                    .height(260.dp)
                                    .border(
                                        width = 3.dp,
                                        brush = Brush.verticalGradient(listOf(EmeraldPrimaryFixed, EmeraldSecondary)),
                                        shape = RoundedCornerShape(100.dp)
                                    )
                            )

                            // Overlay Guides
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Top controls: Flash and Camera Flip
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Surface(
                                        onClick = { isFlashOn = !isFlashOn },
                                        color = Color.Black.copy(alpha = 0.5f),
                                        shape = CircleShape,
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                                                contentDescription = "Flash",
                                                tint = if (isFlashOn) GoldLight else Color.White
                                            )
                                        }
                                    }

                                    Surface(
                                        color = Color.Black.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = "Wajah Terdeteksi (99%)",
                                            color = EmeraldPrimaryFixed,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }

                                    Surface(
                                        onClick = { isFrontCamera = !isFrontCamera },
                                        color = Color.Black.copy(alpha = 0.5f),
                                        shape = CircleShape,
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.FlipCameraAndroid,
                                                contentDescription = "Flip",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                }

                                // Bottom instruction & Geofence GPS Pill
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        color = Color.Black.copy(alpha = 0.65f),
                                        shape = RoundedCornerShape(14.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Icon(Icons.Default.GpsFixed, contentDescription = null, tint = StatusSuccess, modifier = Modifier.size(14.dp))
                                            Text(
                                                text = "Dalam Radius Lokasi 12m / Maks 50m (Akurasi 3m)",
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }

                                    Text(
                                        text = "Posisikan wajah Anda tegak di dalam garis panduan hijau",
                                        color = Color.White.copy(alpha = 0.9f),
                                        fontSize = 11.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        // Action: Ambil Swafoto & Hadir
                        Button(
                            onClick = {
                                isSubmittingPresence = true
                                presenceSuccess = true
                                onPresensiSuccess("Hadir • Pengajian Kitab Aqidatul Awam", "Swafoto")
                                toastMessage = "Alhamdulillah! Presensi kajian Aqidatul Awam berhasil dicatat."
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            if (isSubmittingPresence) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Memverifikasi GPS & Wajah...", fontWeight = FontWeight.Bold)
                            } else {
                                Icon(Icons.Default.Camera, contentDescription = null, modifier = Modifier.size(20.dp), tint = GoldLight)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Ambil Swafoto & Hadir", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                            }
                        }

                        // Teaser Links
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                onClick = { selectedTab = 1 },
                                shape = RoundedCornerShape(14.dp),
                                color = SurfaceCard,
                                border = BorderStroke(1.dp, BorderSubtle),
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(Icons.Default.QrCode2, contentDescription = null, tint = EmeraldPrimaryContainer)
                                    Column {
                                        Text(text = "QR Code KTS", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                        Text(text = "Scan oleh Pengurus", fontSize = 9.5.sp, color = TextMuted)
                                    }
                                }
                            }

                            Surface(
                                onClick = { selectedTab = 2 },
                                shape = RoundedCornerShape(14.dp),
                                color = SurfaceCard,
                                border = BorderStroke(1.dp, BorderSubtle),
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(Icons.Default.EventBusy, contentDescription = null, tint = StatusWarning)
                                    Column {
                                        Text(text = "Berhalangan?", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                        Text(text = "Buka Form Izin", fontSize = 9.5.sp, color = TextMuted)
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // TAB 2: QR CODE KTS
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = SurfaceCard,
                            border = BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Text(
                                    text = "QR Presensi KTS Dinamis",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )

                                Text(
                                    text = "Tunjukkan barcode ini kepada Pengurus Piket / Ustadz pengampu kajian untuk diverifikasi.",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted),
                                    textAlign = TextAlign.Center
                                )

                                // Large QR Card
                                Box(
                                    modifier = Modifier
                                        .size(240.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.White)
                                        .border(2.dp, EmeraldPrimaryContainer, RoundedCornerShape(20.dp))
                                        .padding(14.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.QrCode2,
                                        contentDescription = "QR Code",
                                        tint = OnSurface,
                                        modifier = Modifier.fillMaxSize()
                                    )

                                    // Center Logo Stamp
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color.White)
                                            .border(1.5.dp, GoldLight, RoundedCornerShape(10.dp))
                                            .padding(4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        AsyncImage(
                                            model = SampleData.PESANTREN_LOGO_URL,
                                            contentDescription = "Logo",
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }

                                // Countdown Timer Pill
                                Surface(
                                    color = EmeraldPrimaryContainer.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(Icons.Default.Sync, contentDescription = null, tint = EmeraldPrimaryContainer, modifier = Modifier.size(16.dp))
                                        Text(
                                            text = "Kode diperbarui dalam: $qrSecondsLeft detik",
                                            color = EmeraldPrimaryContainer,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                        )
                                    }
                                }

                                Text(
                                    text = "Ahmad Fauzi • NIS: ${santri.nis}\n${santri.asrama} - ${santri.kamar}",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = TextBody)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.BrightnessHigh, contentDescription = null, tint = GoldTertiary, modifier = Modifier.size(16.dp))
                                    Text(
                                        text = "Tingkatkan kecerahan layar ponsel Anda agar terbaca maksimal oleh barcode scanner pengurus.",
                                        fontSize = 10.sp,
                                        color = TextMuted
                                    )
                                }
                            }
                        }
                    }

                    2 -> {
                        // TAB 3: FORM PERIZINAN SANTRI
                        var jenisIzin by remember { mutableStateOf("Sakit (Klinik)") }
                        var alasanText by remember { mutableStateOf("") }
                        var tanggalMulai by remember { mutableStateOf("19 Sep 2026") }
                        var durasiHari by remember { mutableStateOf("1 Hari") }

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
                                Text(
                                    text = "Pengajuan Perizinan / Dispensasi",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )

                                Text(
                                    text = "Santri yang berhalangan hadir wajib mengajukan perizinan sebelum sesi kajian/kegiatan dimulai.",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )

                                // Jenis Izin Selector
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(text = "Jenis Izin", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(SurfaceSubtle, RoundedCornerShape(12.dp))
                                            .padding(4.dp)
                                    ) {
                                        listOf("Sakit (Klinik)", "Izin Pulang", "Tugas Pesantren").forEach { opt ->
                                            val isSel = jenisIzin == opt
                                            Surface(
                                                onClick = { jenisIzin = opt },
                                                shape = RoundedCornerShape(10.dp),
                                                color = if (isSel) EmeraldPrimaryContainer else Color.Transparent,
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text(
                                                    text = opt,
                                                    color = if (isSel) Color.White else TextMuted,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier.padding(vertical = 8.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                // Alasan
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(text = "Keterangan / Alasan Izin", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                    OutlinedTextField(
                                        value = alasanText,
                                        onValueChange = { alasanText = it },
                                        placeholder = { Text("Contoh: Demam tinggi, rawat inap di klinik pesantren...", color = TextMuted, fontSize = 12.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        minLines = 3,
                                        shape = RoundedCornerShape(12.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = SurfaceSubtle,
                                            unfocusedContainerColor = SurfaceSubtle
                                        )
                                    )
                                }

                                // Upload Lampiran
                                Surface(
                                    onClick = { toastMessage = "Memilih lampiran surat dokter / persetujuan wali kamar..." },
                                    shape = RoundedCornerShape(12.dp),
                                    color = SurfaceSubtle,
                                    border = BorderStroke(1.dp, BorderSubtle),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Icon(Icons.Default.AttachFile, contentDescription = null, tint = EmeraldPrimaryContainer)
                                        Column {
                                            Text(text = "Unggah Surat Dokter / Bukti (Opsional)", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                            Text(text = "Format PDF, JPG, PNG maks 5MB", fontSize = 9.5.sp, color = TextMuted)
                                        }
                                    }
                                }

                                Button(
                                    onClick = {
                                        onIzinSubmitted(jenisIzin, alasanText, "Surat_Dokter_Klinik.pdf")
                                        toastMessage = "Pengajuan izin berhasil dikirim ke Dewan Pengurus Asrama."
                                        selectedTab = 0
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                ) {
                                    Text("Kirim Pengajuan Izin", fontWeight = FontWeight.Bold)
                                }
                            }
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
private fun TabPill(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) EmeraldPrimaryContainer else Color.Transparent,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else TextMuted,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                color = if (isSelected) Color.White else TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
