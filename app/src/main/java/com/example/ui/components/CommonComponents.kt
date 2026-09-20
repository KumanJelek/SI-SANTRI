package com.example.ui.components

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.SantriProfile
import com.example.data.SupportMessage
import com.example.ui.theme.*

@Composable
fun SiSantriTopBar(
    title: String = "SI-SANTRI",
    subtitle: String = "Beranda",
    roleLabel: String? = null,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    userInitials: String = "A",
    userName: String = "Ahmad F.",
    userRoom: String = "Al-Farabi",
    onProfileClick: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f, fill = false)
            ) {
                if (showBackButton) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // Pesantren Logo
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(EmeraldPrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = SampleData.PESANTREN_LOGO_URL,
                        contentDescription = "Logo Pesantren",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(modifier = Modifier.padding(start = 2.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = EmeraldPrimaryContainer
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (roleLabel != null) {
                            Surface(
                                color = EmeraldPrimaryContainer,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = roleLabel,
                                    color = GoldLight,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall.copy(color = TextMuted),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Right Actions: Notification and Profile Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box {
                    IconButton(
                        onClick = onNotificationClick,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifikasi",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 7.dp, end = 7.dp)
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(StatusDanger)
                    )
                }

                Surface(
                    onClick = onProfileClick,
                    shape = RoundedCornerShape(20.dp),
                    color = SurfaceSubtle,
                    border = BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.height(34.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp, end = 4.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Text(
                                text = userName,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = EmeraldPrimaryContainer
                                )
                            )
                            Text(
                                text = userRoom,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 8.5.sp,
                                    color = TextMuted
                                )
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userInitials,
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Standard CR80 Digital Student Card (KTS) Modal
 */
@Composable
fun KtsDigitalModal(
    santri: SantriProfile = SampleData.defaultSantri,
    onDismiss: () -> Unit
) {
    var isFrontSide by remember { mutableStateOf(true) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

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
                    .widthIn(max = 420.dp)
                    .clickable(enabled = false) {}
                    .shadow(16.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                color = SurfaceCard
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = Icons.Default.Badge,
                                contentDescription = null,
                                tint = EmeraldPrimaryContainer
                            )
                            Column {
                                Text(
                                    text = "Kartu Tanda Santri (KTS)",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "Standar CR80 ID Card (85.6mm x 53.98mm)",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 10.sp)
                                )
                            }
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Tutup")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Flip selector tabs
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceSubtle, RoundedCornerShape(12.dp))
                            .padding(4.dp)
                    ) {
                        Surface(
                            onClick = { isFrontSide = true },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isFrontSide) EmeraldPrimaryContainer else Color.Transparent,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Tampak Depan",
                                color = if (isFrontSide) Color.White else TextMuted,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        Surface(
                            onClick = { isFrontSide = false },
                            shape = RoundedCornerShape(8.dp),
                            color = if (!isFrontSide) EmeraldPrimaryContainer else Color.Transparent,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Tampak Belakang",
                                color = if (!isFrontSide) Color.White else TextMuted,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // CR80 Card Preview Container
                    AnimatedContent(targetState = isFrontSide, label = "KTSCardFlip") { showFront ->
                        if (showFront) {
                            // FRONT SIDE (Emerald and Gold gradient)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(85.6f / 53.98f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                EmeraldPrimaryContainer,
                                                EmeraldPrimary,
                                                Color(0xFF042B1A)
                                            )
                                        )
                                    )
                                    .border(1.dp, GoldLight.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                                    .padding(14.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    // Top Row
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(28.dp)
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(Color.White.copy(alpha = 0.2f)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AsyncImage(
                                                    model = SampleData.PESANTREN_LOGO_URL,
                                                    contentDescription = "Logo",
                                                    modifier = Modifier.fillMaxSize()
                                                )
                                            }
                                            Column {
                                                Text(
                                                    text = "PONDOK PESANTREN SALAF MODERN",
                                                    color = GoldLight,
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontSize = 7.5.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        letterSpacing = 0.5.sp
                                                    )
                                                )
                                                Text(
                                                    text = "KARTU TANDA SANTRI",
                                                    color = Color.White,
                                                    style = MaterialTheme.typography.labelMedium.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        letterSpacing = 0.5.sp
                                                    )
                                                )
                                            }
                                        }
                                        Surface(
                                            color = GoldLight.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Text(
                                                text = "CR80",
                                                color = GoldLight,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontSize = 8.sp,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    // Middle Row: Photo, Info, QR
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        // Student Photo
                                        Box(
                                            modifier = Modifier
                                                .width(54.dp)
                                                .height(72.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .border(1.5.dp, GoldLight, RoundedCornerShape(8.dp))
                                        ) {
                                            AsyncImage(
                                                model = santri.photoUrl,
                                                contentDescription = "Foto Santri",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Nama Santri",
                                                color = GoldLight,
                                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.5.sp)
                                            )
                                            Text(
                                                text = santri.name,
                                                color = Color.White,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(
                                                text = "NIS: ${santri.nis}",
                                                color = EmeraldPrimaryFixedDim,
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
                                            )
                                            Text(
                                                text = "Kamar: ${santri.kamar}",
                                                color = Color.White.copy(alpha = 0.9f),
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.5.sp)
                                            )
                                            Text(
                                                text = santri.jurusan,
                                                color = GoldLight.copy(alpha = 0.85f),
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 7.5.sp),
                                                maxLines = 1
                                            )
                                        }

                                        // QR Mini Seal
                                        Surface(
                                            color = Color.White,
                                            shape = RoundedCornerShape(6.dp),
                                            modifier = Modifier.size(46.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    imageVector = Icons.Default.QrCode2,
                                                    contentDescription = null,
                                                    tint = OnSurface,
                                                    modifier = Modifier.fillMaxSize().padding(2.dp)
                                                )
                                            }
                                        }
                                    }

                                    // Bottom Row: Expiry
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                BorderStroke(0.5.dp, Color.White.copy(alpha = 0.2f))
                                            )
                                            .padding(top = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Masa Berlaku: s/d TA 2027/2028",
                                            color = GoldLight.copy(alpha = 0.9f),
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.sp)
                                        )
                                        Text(
                                            text = "Markaz Tarbiyah Islamiyah",
                                            color = Color.White.copy(alpha = 0.8f),
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.sp)
                                        )
                                    }
                                }
                            }
                        } else {
                            // BACK SIDE (White card with rules & signature)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(85.6f / 53.98f)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
                                    .padding(14.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "TATA TERTIB KTS",
                                            color = EmeraldPrimaryContainer,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        )
                                        Text(
                                            text = "KTS-V3-2026",
                                            color = TextMuted,
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp)
                                        )
                                    }

                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(text = "1. Kartu identitas resmi santri Ponpes SI-SANTRI.", fontSize = 8.sp, color = TextBody)
                                        Text(text = "2. Wajib dibawa saat izin keluar, perizinan, & presensi.", fontSize = 8.sp, color = TextBody)
                                        Text(text = "3. Ta'zir dan sanksi berlaku jika kartu disalahgunakan.", fontSize = 8.sp, color = TextBody)
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        Column {
                                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                                repeat(8) {
                                                    Box(
                                                        modifier = Modifier
                                                            .width(if (it % 2 == 0) 2.dp else 4.dp)
                                                            .height(14.dp)
                                                            .background(Color.Black)
                                                    )
                                                }
                                            }
                                            Text(text = "*${santri.nis}*", fontSize = 7.sp, color = TextMuted)
                                        }

                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "Pengasuh Pesantren,", fontSize = 7.5.sp, color = TextMuted)
                                            Text(
                                                text = "KH. M. Syukron Ma'mun",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = EmeraldPrimaryContainer
                                            )
                                            Text(text = "NIP. 19740812.2001.01", fontSize = 7.sp, color = TextMuted)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons: Download PDF & Share QR
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                toastMessage = "Mengunduh KTS ${santri.name} format PDF resolusi cetak..."
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Unduh PDF KTS", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                toastMessage = "Link verifikasi QR KTS disalin!"
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Bagikan QR", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (toastMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = toastMessage ?: "",
                            style = MaterialTheme.typography.bodySmall.copy(color = StatusSuccess),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

/**
 * Tanya Si Santri (AI Support & Pengurus Bantuan) Modal
 */
@Composable
fun TanyaSiSantriModal(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(SampleData.supportMessages.toMutableList()) }

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
                    .heightIn(max = 620.dp)
                    .clickable(enabled = false) {}
                    .shadow(16.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                color = SurfaceCard
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Header with Emerald Gradient
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(EmeraldPrimaryContainer, EmeraldPrimary)
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(GoldLight.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SupportAgent,
                                        contentDescription = null,
                                        tint = GoldLight
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Layanan Bantuan Santri",
                                        color = Color.White,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(StatusSuccess)
                                        )
                                        Text(
                                            text = "Bot AI Aktif • Pengurus Siaga (08:00 - 21:00)",
                                            color = Color.White.copy(alpha = 0.85f),
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp)
                                        )
                                    }
                                }
                            }
                            IconButton(onClick = onDismiss) {
                                Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.White)
                            }
                        }
                    }

                    // Chat messages list
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .background(SurfaceBackground)
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Surface(
                                color = SurfaceSubtle,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Hari ini, 24 Oktober 2026",
                                    style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 9.5.sp),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                                )
                            }
                        }

                        messages.forEach { msg ->
                            val isUser = msg.sender == "Santri"
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (!isUser) {
                                        Icon(
                                            imageVector = if (msg.isBot) Icons.Default.SmartToy else Icons.Default.Security,
                                            contentDescription = null,
                                            tint = if (msg.isBot) EmeraldSecondary else TealSecondary,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }
                                    Text(
                                        text = "${msg.senderName} • ${msg.time}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 9.sp,
                                            color = if (msg.isBot) EmeraldSecondary else TextMuted
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Surface(
                                    color = if (isUser) EmeraldPrimaryContainer else SurfaceCard,
                                    shape = RoundedCornerShape(
                                        topStart = 14.dp,
                                        topEnd = 14.dp,
                                        bottomStart = if (isUser) 14.dp else 2.dp,
                                        bottomEnd = if (isUser) 2.dp else 14.dp
                                    ),
                                    border = if (isUser) null else BorderStroke(1.dp, BorderSubtle),
                                    modifier = Modifier.widthIn(max = 300.dp)
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = msg.message,
                                            color = if (isUser) Color.White else TextBody,
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp, lineHeight = 16.sp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Bottom Action & Input Field
                    Surface(
                        color = SurfaceCard,
                        shadowElevation = 4.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SurfaceSubtle, RoundedCornerShape(14.dp))
                                    .padding(horizontal = 8.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = inputText,
                                    onValueChange = { inputText = it },
                                    placeholder = {
                                        Text("Tulis pertanyaan untuk AI / Pengurus...", fontSize = 11.sp, color = TextMuted)
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent
                                    ),
                                    modifier = Modifier.weight(1f),
                                    textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                                )
                                IconButton(
                                    onClick = {
                                        if (inputText.isNotBlank()) {
                                            val query = inputText.trim()
                                            messages.add(
                                                SupportMessage(
                                                    id = "user_${System.currentTimeMillis()}",
                                                    sender = "Santri",
                                                    senderName = "Ahmad Fauzi",
                                                    message = query,
                                                    time = "Baru saja"
                                                )
                                            )
                                            inputText = ""

                                            // Auto bot guidance response
                                            messages.add(
                                                SupportMessage(
                                                    id = "bot_${System.currentTimeMillis()}",
                                                    sender = "Bot",
                                                    senderName = "Asisten Bot SI-SANTRI",
                                                    message = "Terima kasih atas pertanyaannya. Pertanyaan '$query' telah diteruskan ke Pengurus Piket Asrama Al-Farabi. Silakan tunggu respon berikutnya.",
                                                    time = "Baru saja",
                                                    isBot = true
                                                )
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(EmeraldPrimaryContainer)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = "Kirim",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Bot AI aktif 24 jam",
                                    fontSize = 9.sp,
                                    color = TextMuted
                                )
                                TextButton(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/6281234567890?text=Assalamu'alaikum%20Pengurus%20Pusat%2C%20saya%20memerlukan%20bantuan%20akun%20santri"))
                                        context.startActivity(intent)
                                    },
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Icon(Icons.Default.Chat, contentDescription = null, tint = WhatsappGreen, modifier = Modifier.size(13.dp))
                                        Text("WhatsApp Pengurus", fontSize = 10.sp, color = WhatsappGreen, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
