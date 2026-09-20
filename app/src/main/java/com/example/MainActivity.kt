package com.example

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.SampleData
import com.example.data.SantriProfile
import com.example.data.UmkmProduct
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SurfaceCanvas
import com.example.ui.viewmodel.SiSantriViewModel
import com.example.ui.viewmodel.SiSantriViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SurfaceCanvas
                ) {
                    SiSantriApp()
                }
            }
        }
    }
}

@Composable
fun SiSantriApp() {
    val context = LocalContext.current
    val viewModel: SiSantriViewModel = viewModel(
        factory = SiSantriViewModelFactory(context.applicationContext as Application)
    )

    val navController = rememberNavController()
    val santriList by viewModel.santriList.collectAsStateWithLifecycle()
    val presensiLiveList by viewModel.presensiLiveList.collectAsStateWithLifecycle()
    val izinList by viewModel.izinList.collectAsStateWithLifecycle()
    val jadwalList by viewModel.jadwalList.collectAsStateWithLifecycle()
    val umkmProducts by viewModel.umkmProducts.collectAsStateWithLifecycle()
    val umkmOrders by viewModel.umkmOrders.collectAsStateWithLifecycle()
    val currentSantri by viewModel.currentSantri.collectAsStateWithLifecycle()
    val isMarketplaceActive by viewModel.isMarketplaceActive.collectAsStateWithLifecycle()

    var selectedProduct by remember { mutableStateOf<UmkmProduct?>(null) }

    val activeSantri = currentSantri ?: SampleData.defaultSantri

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { role ->
                    if (role == com.example.data.UserRole.SANTRI) {
                        navController.navigate("santri_home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("pengurus_dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("santri_home") {
            SantriHomeScreen(
                santri = activeSantri,
                onNavigateToPresensi = { _ ->
                    navController.navigate("santri_presensi")
                },
                onNavigateToJadwal = {
                    navController.navigate("jadwal")
                },
                onNavigateToUmkm = {
                    navController.navigate("umkm_catalog")
                },
                onNavigateToRegistrasi = {
                    navController.navigate("santri_register")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("santri_home") { inclusive = true }
                    }
                }
            )
        }

        composable("santri_register") {
            SantriRegistrationScreen(
                currentSantri = activeSantri,
                onSaveSuccess = { updatedSantri ->
                    viewModel.registerSantri(updatedSantri)
                    navController.navigate("santri_home") {
                        popUpTo("santri_register") { inclusive = true }
                    }
                },
                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate("login")
                    }
                }
            )
        }

        composable("santri_presensi") {
            SantriPresensiScreen(
                santri = activeSantri,
                initialTab = 0,
                onPresensiSuccess = { activity, method ->
                    viewModel.recordPresensi(activeSantri, activity, method)
                },
                onIzinSubmitted = { type, reason, attachment ->
                    viewModel.submitIzin(activeSantri, type, reason, attachment)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("santri_presensi_izin") {
            SantriPresensiScreen(
                santri = activeSantri,
                initialTab = 2,
                onPresensiSuccess = { activity, method ->
                    viewModel.recordPresensi(activeSantri, activity, method)
                },
                onIzinSubmitted = { type, reason, attachment ->
                    viewModel.submitIzin(activeSantri, type, reason, attachment)
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("jadwal") {
            JadwalScreen(
                jadwalList = jadwalList,
                isPengurusMode = false,
                onNavigateToPresensi = { navController.navigate("santri_presensi") },
                onNavigateToIzin = { navController.navigate("santri_presensi_izin") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("jadwal_pengurus") {
            JadwalScreen(
                jadwalList = jadwalList,
                onAddJadwal = { item, radius ->
                    viewModel.addJadwal(item, radius)
                },
                isPengurusMode = true,
                onNavigateToPresensi = { navController.navigate("santri_presensi") },
                onNavigateToIzin = { navController.navigate("santri_presensi_izin") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("pengurus_dashboard") {
            PengurusDashboardScreen(
                izinList = izinList,
                presensiLiveList = presensiLiveList,
                onApproveIzin = { izinId -> viewModel.approveIzin(izinId) },
                onRejectIzin = { izinId -> viewModel.rejectIzin(izinId) },
                onNavigateToJadwal = { navController.navigate("jadwal_pengurus") },
                onNavigateToKurasiUmkm = { navController.navigate("umkm_kurasi") },
                onNavigateToRegisterSantri = { navController.navigate("santri_register") },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("pengurus_dashboard") { inclusive = true }
                    }
                }
            )
        }

        composable("umkm_catalog") {
            UmkmCatalogScreen(
                productList = umkmProducts,
                isMarketplaceActive = isMarketplaceActive,
                onToggleMarketplace = { active -> viewModel.toggleMarketplace(active) },
                onProductClick = { prod ->
                    selectedProduct = prod
                    navController.navigate("umkm_detail")
                },
                onNavigateToAddProduct = { navController.navigate("umkm_add") },
                onNavigateToLapakSaya = { navController.navigate("umkm_lapak") },
                onNavigateToKurasi = { navController.navigate("umkm_kurasi") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("umkm_detail") {
            val prod = selectedProduct ?: umkmProducts.firstOrNull() ?: SampleData.umkmProducts.first()
            UmkmDetailScreen(
                product = prod,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("umkm_add") {
            UmkmAddProductScreen(
                onProductSubmitted = { newProduct ->
                    viewModel.addProduct(newProduct)
                    navController.navigate("umkm_lapak") {
                        popUpTo("umkm_add") { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("umkm_lapak") {
            UmkmLapakScreen(
                orders = umkmOrders,
                onUpdateOrderStatus = { orderId, newStatus ->
                    viewModel.updateOrderStatus(orderId, newStatus)
                },
                onNavigateToAddProduct = { navController.navigate("umkm_add") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("umkm_kurasi") {
            val pendingProducts = umkmProducts.filter { it.status == "Menunggu Kurasi" }
            UmkmKurasiScreen(
                pendingProducts = pendingProducts,
                onCurateProduct = { productId, approved ->
                    viewModel.curateProduct(productId, approved)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
