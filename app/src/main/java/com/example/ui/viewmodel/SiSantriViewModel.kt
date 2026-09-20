package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.example.data.repository.SiSantriRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SiSantriViewModel(application: Application) : AndroidViewModel(application) {

    val repository = SiSantriRepository.getInstance(application)

    // Current logged-in santri state
    private val _currentSantriNis = MutableStateFlow(SampleData.defaultSantri.nis)
    val currentSantri: StateFlow<SantriProfile> = _currentSantriNis
        .flatMapLatest { nis ->
            repository.getSantriProfileFlow(nis).map { it ?: SampleData.defaultSantri }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SampleData.defaultSantri
        )

    val santriList: StateFlow<List<SantriProfile>> = repository.getAllSantriProfiles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf(SampleData.defaultSantri)
        )

    fun setCurrentSantriNis(nis: String) {
        _currentSantriNis.value = nis
    }

    // Live Attendance feed
    val presensiLiveList: StateFlow<List<PresensiLiveItem>> = repository.allPresensiFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SampleData.presensiLive
        )

    // Izin & Dispensasi Queue
    val izinQueueList: StateFlow<List<IzinQueueItem>> = repository.allIzinFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SampleData.izinQueue
        )

    val izinList: StateFlow<List<IzinQueueItem>> get() = izinQueueList

    val pendingIzinList: StateFlow<List<IzinQueueItem>> = repository.pendingIzinFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SampleData.izinQueue.filter { it.status == "Menunggu" }
        )

    // Jadwal
    val jadwalList: StateFlow<List<JadwalItem>> = repository.allJadwalFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SampleData.jadwals
        )

    // UMKM Products
    val activeUmkmProducts: StateFlow<List<UmkmProduct>> = repository.activeUmkmProductsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SampleData.umkmProducts
        )

    val umkmProducts: StateFlow<List<UmkmProduct>> get() = activeUmkmProducts

    val pendingCurationProducts: StateFlow<List<UmkmProduct>> = repository.pendingCurationProductsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // UMKM Orders
    val lapakOrders: StateFlow<List<UmkmOrderItem>> = repository.allOrdersFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SampleData.lapakOrders
        )

    val umkmOrders: StateFlow<List<UmkmOrderItem>> get() = lapakOrders

    // UMKM Feature Enable/Disable
    val isUmkmEnabled: StateFlow<Boolean> = repository.isUmkmEnabledFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    val isMarketplaceActive: StateFlow<Boolean> get() = isUmkmEnabled

    // ==========================================
    // ACTION MUTATIONS
    // ==========================================

    fun saveSantriProfile(profile: SantriProfile, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            repository.saveOrUpdateSantri(profile)
            _currentSantriNis.value = profile.nis
            onComplete()
        }
    }

    fun submitSwafotoPresensi(
        santri: SantriProfile,
        activity: String,
        userLat: Double = SiSantriRepository.PESANTREN_DEFAULT_LAT,
        userLng: Double = SiSantriRepository.PESANTREN_DEFAULT_LNG,
        photoUri: String = "",
        onSuccess: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            val record = repository.recordPresensi(
                santri = santri,
                activity = activity,
                method = "Swafoto",
                userLat = userLat,
                userLng = userLng,
                photoUri = photoUri
            )
            onSuccess(record.status)
        }
    }

    fun submitQrPresensi(
        santri: SantriProfile,
        activity: String,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            repository.recordPresensi(
                santri = santri,
                activity = activity,
                method = "KTS"
            )
            onSuccess()
        }
    }

    fun submitIzin(
        santri: SantriProfile,
        type: String,
        reason: String,
        attachmentName: String,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            repository.submitIzin(
                santri = santri,
                type = type,
                reason = reason,
                attachmentName = attachmentName
            )
            onSuccess()
        }
    }

    fun approveIzin(izinId: String, reviewerName: String = "Ust. Marzuki") {
        viewModelScope.launch {
            repository.reviewIzin(izinId, "Disetujui", reviewerName)
        }
    }

    fun rejectIzin(izinId: String, reviewerName: String = "Ust. Marzuki") {
        viewModelScope.launch {
            repository.reviewIzin(izinId, "Ditolak", reviewerName)
        }
    }

    fun addJadwal(item: JadwalItem, radiusMeters: Int = 50, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            repository.createJadwal(item, radiusMeters)
            onSuccess()
        }
    }

    fun addUmkmProduct(product: UmkmProduct, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            repository.submitUmkmProduct(product)
            onSuccess()
        }
    }

    fun curateProduct(productId: String, isApproved: Boolean) {
        viewModelScope.launch {
            val status = if (isApproved) "Aktif" else "Ditolak"
            repository.updateProductStatus(productId, status)
        }
    }

    fun updateOrderStatus(orderId: String, status: String) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, status)
        }
    }

    fun toggleUmkmFeature(enabled: Boolean) {
        viewModelScope.launch {
            repository.setUmkmEnabled(enabled)
        }
    }

    fun registerSantri(profile: SantriProfile, onComplete: () -> Unit = {}) = saveSantriProfile(profile, onComplete)

    fun recordPresensi(
        santri: SantriProfile,
        activity: String,
        method: String = "Swafoto",
        onSuccess: () -> Unit = {}
    ) {
        if (method == "KTS") {
            submitQrPresensi(santri, activity, onSuccess)
        } else {
            submitSwafotoPresensi(santri, activity, onSuccess = { onSuccess() })
        }
    }

    fun addProduct(product: UmkmProduct, onSuccess: () -> Unit = {}) = addUmkmProduct(product, onSuccess)

    fun toggleMarketplace(enabled: Boolean) = toggleUmkmFeature(enabled)
}

class SiSantriViewModelFactory(
    private val application: Application
) : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SiSantriViewModel::class.java)) {
            return SiSantriViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
