package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.data.UserRole
import com.example.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: (UserRole) -> Unit,
    onForgotPasswordClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var selectedRole by remember { mutableStateOf(UserRole.SANTRI) }
    var identifierInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    // Prepopulate identifier when switching role for convenience
    LaunchedEffect(selectedRole) {
        identifierInput = if (selectedRole == UserRole.SANTRI) "202609012" else "NIP-1988051201"
        passwordInput = "santri123"
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
            // TOP EMERALD ISLAMIC HERO
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                EmeraldPrimaryContainer,
                                EmeraldPrimary,
                                Color(0xFF042D19)
                            )
                        )
                    )
                    .padding(horizontal = 24.dp)
                    .padding(top = 48.dp, bottom = 44.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo Container with Gold-tinted frame
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(Color.White)
                            .border(2.dp, GoldLight.copy(alpha = 0.6f), RoundedCornerShape(22.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = SampleData.PESANTREN_LOGO_URL,
                            contentDescription = "Logo Pesantren",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(14.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Academic Year Tag
                    Surface(
                        color = EmeraldOnPrimaryContainer.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = null,
                                tint = GoldLight,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "TAHUN AKADEMIK 1445 / 1446 H",
                                color = GoldLight,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "SI-SANTRI",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    )
                    Text(
                        text = "(Sistem Informasi Santri)",
                        color = EmeraldOnPrimaryContainer,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                    )
                }
            }

            // FLOATING CARD CONTAINER
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp)
                    .padding(horizontal = 18.dp)
                    .shadow(12.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                color = SurfaceCard
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header Inside Card
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Ahlan Wa Sahlan",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                ),
                                color = OnSurface
                            )
                            Text(
                                text = if (selectedRole == UserRole.SANTRI) "Silakan masukkan akun santri Anda" else "Portal login staf & pengurus pondok",
                                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceSubtle),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mosque,
                                contentDescription = null,
                                tint = EmeraldPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    // Role Switcher: Santri vs Pengurus
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceSubtle, RoundedCornerShape(16.dp))
                            .padding(4.dp)
                    ) {
                        val isSantri = selectedRole == UserRole.SANTRI
                        Surface(
                            onClick = { selectedRole = UserRole.SANTRI },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSantri) EmeraldPrimaryContainer else Color.Transparent,
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = if (isSantri) Color.White else TextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Santri",
                                    color = if (isSantri) Color.White else TextMuted,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }

                        val isPengurus = selectedRole != UserRole.SANTRI
                        Surface(
                            onClick = { selectedRole = UserRole.PENGURUS_PUSAT },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isPengurus) EmeraldPrimaryContainer else Color.Transparent,
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = if (isPengurus) Color.White else TextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Pengurus",
                                    color = if (isPengurus) Color.White else TextMuted,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }

                    // Field 1: NIS / NIP
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(
                                imageVector = Icons.Default.Badge,
                                contentDescription = null,
                                tint = EmeraldPrimaryContainer,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = if (selectedRole == UserRole.SANTRI) "Nomor Induk Santri (NIS)" else "Nomor Induk Pengurus (NIP)",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = OnSurface
                            )
                        }
                        OutlinedTextField(
                            value = identifierInput,
                            onValueChange = { identifierInput = it },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null, tint = TextMuted)
                            },
                            placeholder = {
                                Text(
                                    text = if (selectedRole == UserRole.SANTRI) "Contoh: 202609012" else "Contoh: NIP-1988051201",
                                    color = TextMuted.copy(alpha = 0.6f)
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = if (selectedRole == UserRole.SANTRI) KeyboardType.Number else KeyboardType.Text
                            ),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = SurfaceSubtle,
                                unfocusedContainerColor = SurfaceSubtle,
                                focusedBorderColor = EmeraldPrimaryContainer,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = if (selectedRole == UserRole.SANTRI) "Masukkan 9-digit NIS santri aktif pondok" else "Masukkan NIP atau ID resmi pengurus / ustadz",
                            style = MaterialTheme.typography.labelSmall.copy(color = TextMuted, fontSize = 10.sp),
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }

                    // Field 2: Password
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = EmeraldPrimaryContainer,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = "Kata Sandi",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = OnSurface
                            )
                        }
                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = { passwordInput = it },
                            leadingIcon = {
                                Icon(Icons.Default.Key, contentDescription = null, tint = TextMuted)
                            },
                            trailingIcon = {
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "Lihat Password",
                                        tint = TextMuted
                                    )
                                }
                            },
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            placeholder = { Text("••••••••", color = TextMuted.copy(alpha = 0.6f)) },
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
                    }

                    // Remember Me & Forgot Password Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { rememberMe = !rememberMe }
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(checkedColor = EmeraldPrimaryContainer)
                            )
                            Text(
                                text = "Ingat Saya",
                                style = MaterialTheme.typography.bodySmall.copy(color = TextBody)
                            )
                        }

                        Text(
                            text = "Lupa Kata Sandi?",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = EmeraldPrimaryContainer
                            ),
                            modifier = Modifier.clickable {
                                toastMessage = "Tautan reset telah dikirimkan ke kontak wali santri terdaftar."
                                onForgotPasswordClick()
                            }
                        )
                    }

                    // Submit CTA Button
                    Button(
                        onClick = {
                            if (identifierInput.isBlank()) {
                                toastMessage = "Mohon masukkan ${if (selectedRole == UserRole.SANTRI) "NIS Santri" else "NIP Pengurus"}"
                                return@Button
                            }
                            isLoading = true
                            toastMessage = "Alhamdulillah, berhasil masuk ke portal ${if (selectedRole == UserRole.SANTRI) "santri" else "pengurus"}!"
                            onLoginSuccess(selectedRole)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimaryContainer),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Memverifikasi Data...", fontWeight = FontWeight.Bold)
                        } else {
                            Text(
                                text = if (selectedRole == UserRole.SANTRI) "Masuk Sebagai Santri" else "Masuk Sebagai Pengurus",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                        }
                    }

                    // Divider: Atau Masuk Lebih Cepat
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = BorderSubtle)
                        Text(
                            text = "Atau Masuk Lebih Cepat",
                            style = MaterialTheme.typography.labelSmall.copy(color = TextMuted),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f), color = BorderSubtle)
                    }

                    // Biometric Button
                    OutlinedButton(
                        onClick = {
                            toastMessage = "Identitas biometrik terverifikasi. Selamat datang kembali!"
                            onLoginSuccess(selectedRole)
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = SurfaceSubtle),
                        border = BorderStroke(1.dp, BorderSubtle),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(EmeraldPrimaryContainer.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Fingerprint,
                                contentDescription = null,
                                tint = EmeraldPrimaryContainer,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Masuk dengan Sidik Jari / Face ID",
                            color = OnSurface,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }

            // MICRO BANNER: Integritas Santri Mandiri
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp),
                shape = RoundedCornerShape(16.dp),
                color = TealSecondaryContainer.copy(alpha = 0.35f)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = TealSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "Integritas Santri Mandiri",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TealSecondary
                            )
                        )
                        Text(
                            text = "Masuk harian mencatat rekapitulasi kehadiran otomatis.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TealOnSecondaryContainer,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // FOOTER & HELPDESK
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Belum memiliki akun santri aktif atau terkendala akses?",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted),
                    textAlign = TextAlign.Center
                )

                // WhatsApp Pill Action Button
                Surface(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/6281234567890?text=Assalamu'alaikum%20Pengurus%20Pusat%2C%20saya%20memerlukan%20bantuan%20akun%20santri"))
                        context.startActivity(intent)
                    },
                    shape = RoundedCornerShape(24.dp),
                    color = SurfaceCard,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(WhatsappGreen.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                tint = WhatsappGreen,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                        Text(
                            text = "Hubungi Pengurus Pusat",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = TextBody
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.OpenInNew,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                // Security notice
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = EmeraldPrimaryContainer,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "Koneksi Terenkripsi 256-Bit SSL Pesantren Network",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    )
                }
            }
        }

        // Animated Toast Notification
        AnimatedVisibility(
            visible = toastMessage != null,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp, start = 20.dp, end = 20.dp)
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
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = EmeraldPrimaryFixed
                    )
                    Text(
                        text = toastMessage ?: "",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                    )
                }
            }
        }
    }
}
