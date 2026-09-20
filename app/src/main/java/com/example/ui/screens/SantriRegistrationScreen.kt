package com.example.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.SantriProfile
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SantriRegistrationScreen(
    currentSantri: SantriProfile = SampleData.defaultSantri,
    onSaveSuccess: (SantriProfile) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var namaLengkap by remember { mutableStateOf(currentSantri.name) }
    var selectedAsrama by remember { mutableStateOf(currentSantri.asrama) }
    var kamar by remember { mutableStateOf(currentSantri.kamar) }
    var universitas by remember { mutableStateOf("UIN Sunan Kalijaga / Ma'had Aly") }
    var programStudi by remember { mutableStateOf(currentSantri.jurusan) }
    var jenisKelamin by remember { mutableStateOf(currentSantri.gender) }
    var whatsappSantri by remember { mutableStateOf(currentSantri.whatsapp) }
    var alamatLengkap by remember { mutableStateOf(currentSantri.address) }
    var namaAyah by remember { mutableStateOf(currentSantri.fatherName) }
    var namaIbu by remember { mutableStateOf(currentSantri.motherName) }
    var whatsappOrtu by remember { mutableStateOf(currentSantri.parentWhatsapp) }

    var asramaExpanded by remember { mutableStateOf(false) }
    val asramaOptions = listOf(
        "Asrama Al-Farabi (Putra)",
        "Asrama Ibnu Rusyd (Putra)",
        "Asrama Al-Ghazali (Putra)",
        "Kompleks Putri Khadijah (Putri)",
        "Kompleks Putri Aisyah (Putri)"
    )

    var toastMessage by remember { mutableStateOf<String?>(null) }
    var isSaving by remember { mutableStateOf(false) }

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
                            text = "SI-SANTRI • Registrasi Baru",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = EmeraldPrimaryContainer
                            )
                        )
                        Text(
                            text = "Lengkapi Biodata Santri",
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
                            if (namaLengkap.isBlank()) {
                                toastMessage = "Mohon isi Nama Lengkap Santri"
                                return@Button
                            }
                            isSaving = true
                            val updated = currentSantri.copy(
                                name = namaLengkap.trim(),
                                asrama = selectedAsrama,
                                kamar = kamar.trim(),
                                jurusan = programStudi.trim(),
                                gender = jenisKelamin,
                                whatsapp = whatsappSantri.trim(),
                                address = alamatLengkap.trim(),
                                fatherName = namaAyah.trim(),
                                motherName = namaIbu.trim(),
                                parentWhatsapp = whatsappOrtu.trim()
                            )
                            onSaveSuccess(updated)
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Menerbitkan KTS Digital...")
                        } else {
                            Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(18.dp), tint = GoldLight)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Simpan & Buat KTS Digital",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
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
            // PROGRESS STEP BANNER
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = EmeraldPrimaryContainer.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, EmeraldPrimaryContainer.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Langkah 1 dari 1: Pengisian Biodata Wajib",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = EmeraldPrimaryContainer)
                        )
                        Text(
                            text = "Tahap Akhir",
                            style = MaterialTheme.typography.labelSmall.copy(color = GoldTertiary, fontWeight = FontWeight.Bold)
                        )
                    }
                    LinearProgressIndicator(
                        progress = { 1f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = EmeraldPrimaryContainer,
                        trackColor = BorderSubtle
                    )
                }
            }

            // WELCOME MESSAGE BANNER
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SurfaceCard,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(GoldLight.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = GoldTertiary)
                    }
                    Column {
                        Text(
                            text = "Selamat Datang di SI-SANTRI",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = OnSurface)
                        )
                        Text(
                            text = "Lengkapi data diri Anda dengan akurat untuk verifikasi akun, pencatatan akademik, dan penerbitan KTS Digital standar CR80.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextBody, fontSize = 11.sp, lineHeight = 16.sp)
                        )
                    }
                }
            }

            // PAS FOTO 3X4 CONTAINER
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SurfaceCard,
                border = BorderStroke(1.dp, BorderSubtle),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Pas Foto Santri (Format 3x4)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Box(
                        modifier = Modifier
                            .width(105.dp)
                            .height(140.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, EmeraldPrimaryContainer, RoundedCornerShape(12.dp))
                            .background(SurfaceSubtle)
                    ) {
                        AsyncImage(
                            model = currentSantri.photoUrl,
                            contentDescription = "Foto Santri",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Camera Button Overlay
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(6.dp)
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimaryContainer)
                                .clickable {
                                    toastMessage = "Memilih foto santri dari galeri..."
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = "Ganti Foto", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }

                    Text(
                        text = "Gunakan foto formal bersarung & berkopiah (ikhwan) atau jilbab rapi (akhwat) berlatar belakang polos.",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 10.5.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // SECTION 1: DATA DIRI SANTRI
            CardSection(title = "1. Data Diri Santri", icon = Icons.Default.Person) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    FormField(
                        label = "Nama Lengkap Sesuai KTP / KK",
                        value = namaLengkap,
                        onValueChange = { namaLengkap = it },
                        placeholder = "Contoh: Ahmad Fauzi Al-Farabi"
                    )

                    // Asrama Dropdown
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Asrama Pondok", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        ExposedDropdownMenuBox(
                            expanded = asramaExpanded,
                            onExpandedChange = { asramaExpanded = !asramaExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedAsrama,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = asramaExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = SurfaceSubtle,
                                    unfocusedContainerColor = SurfaceSubtle
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = asramaExpanded,
                                onDismissRequest = { asramaExpanded = false }
                            ) {
                                asramaOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option, fontSize = 12.sp) },
                                        onClick = {
                                            selectedAsrama = option
                                            asramaExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    FormField(
                        label = "Kamar Asrama",
                        value = kamar,
                        onValueChange = { kamar = it },
                        placeholder = "Contoh: Kamar B-04"
                    )

                    FormField(
                        label = "Universitas / Instansi Pendidikan Formal",
                        value = universitas,
                        onValueChange = { universitas = it },
                        placeholder = "Contoh: UIN Sunan Kalijaga"
                    )

                    FormField(
                        label = "Program Studi / Takhasus Kepesantrenan",
                        value = programStudi,
                        onValueChange = { programStudi = it },
                        placeholder = "Contoh: Takhasus Kitab Kuning & Tahfidz"
                    )

                    // Gender Selector
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Jenis Kelamin", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SurfaceSubtle, RoundedCornerShape(12.dp))
                                .padding(4.dp)
                        ) {
                            val isIkhwan = jenisKelamin.contains("Laki-laki")
                            Surface(
                                onClick = { jenisKelamin = "Laki-laki (Ikhwan)" },
                                shape = RoundedCornerShape(10.dp),
                                color = if (isIkhwan) EmeraldPrimaryContainer else Color.Transparent,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Laki-laki (Ikhwan)",
                                    color = if (isIkhwan) Color.White else TextMuted,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            Surface(
                                onClick = { jenisKelamin = "Perempuan (Akhwat)" },
                                shape = RoundedCornerShape(10.dp),
                                color = if (!isIkhwan) EmeraldPrimaryContainer else Color.Transparent,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Perempuan (Akhwat)",
                                    color = if (!isIkhwan) Color.White else TextMuted,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // SECTION 2: KONTAK & DOMISILI
            CardSection(title = "2. Kontak & Domisili Asal", icon = Icons.Default.LocationOn) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    FormField(
                        label = "Nomor WhatsApp Santri (+62)",
                        value = whatsappSantri,
                        onValueChange = { whatsappSantri = it },
                        placeholder = "Contoh: 812-3456-7890",
                        keyboardType = KeyboardType.Phone
                    )

                    FormField(
                        label = "Alamat Lengkap Sesuai KTP / KK",
                        value = alamatLengkap,
                        onValueChange = { alamatLengkap = it },
                        placeholder = "Dusun, RT/RW, Desa/Kelurahan, Kecamatan, Kab/Kota, Provinsi",
                        singleLine = false,
                        maxLines = 3
                    )
                }
            }

            // SECTION 3: DATA ORANG TUA / WALI
            CardSection(title = "3. Data Orang Tua / Wali", icon = Icons.Default.FamilyRestroom) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    FormField(
                        label = "Nama Lengkap Ayah",
                        value = namaAyah,
                        onValueChange = { namaAyah = it },
                        placeholder = "Contoh: H. Ahmad Dahlan"
                    )

                    FormField(
                        label = "Nama Lengkap Ibu",
                        value = namaIbu,
                        onValueChange = { namaIbu = it },
                        placeholder = "Contoh: Hj. Siti Aminah"
                    )

                    FormField(
                        label = "Nomor WhatsApp Orang Tua / Wali (+62)",
                        value = whatsappOrtu,
                        onValueChange = { whatsappOrtu = it },
                        placeholder = "Contoh: 811-2345-6789",
                        keyboardType = KeyboardType.Phone
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CardSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = SurfaceCard,
        border = BorderStroke(1.dp, BorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(EmeraldPrimaryContainer.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = EmeraldPrimaryContainer, modifier = Modifier.size(16.dp))
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                )
            }
            content()
        }
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = TextMuted.copy(alpha = 0.6f), fontSize = 12.sp) },
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceSubtle,
                unfocusedContainerColor = SurfaceSubtle,
                focusedBorderColor = EmeraldPrimaryContainer,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
