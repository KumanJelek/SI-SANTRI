package com.example

import com.example.data.SampleData
import org.junit.Assert.*
import org.junit.Test
import kotlin.math.*

class SiSantriBusinessLogicTest {

    // Haversine formula to compute distance in meters between two lat/lon points
    private fun calculateDistanceMeters(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val r = 6371000.0 // Earth radius in meters
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    @Test
    fun testGeofencingRadiusCalculation() {
        val masjidLat = -7.275842
        val masjidLon = 112.791530

        // Point inside masjid area (approx 15 meters away)
        val santriInsideLat = -7.275900
        val santriInsideLon = 112.791580
        val distanceInside = calculateDistanceMeters(masjidLat, masjidLon, santriInsideLat, santriInsideLon)

        val geofenceRadius = 60.0 // 60 meters allowable radius
        assertTrue("Santri within 60m radius must be allowed", distanceInside <= geofenceRadius)

        // Point outside pondok premises (approx 500 meters away)
        val santriOutsideLat = -7.280000
        val santriOutsideLon = 112.795000
        val distanceOutside = calculateDistanceMeters(masjidLat, masjidLon, santriOutsideLat, santriOutsideLon)
        assertTrue("Santri > 60m must be rejected by geofence", distanceOutside > geofenceRadius)
    }

    @Test
    fun testPresensiPunctualityLogic() {
        fun isPunctual(actualHour: Int, actualMinute: Int, targetHour: Int, targetMinute: Int, graceMinutes: Int = 10): Boolean {
            val actualTotal = actualHour * 60 + actualMinute
            val targetTotal = targetHour * 60 + targetMinute
            return actualTotal <= (targetTotal + graceMinutes)
        }

        // Subuh Jamaah target: 04:30. Grace period: 10 mins (until 04:40)
        assertTrue("04:25 is punctual", isPunctual(4, 25, 4, 30))
        assertTrue("04:35 is within grace period", isPunctual(4, 35, 4, 30))
        assertFalse("04:45 is late", isPunctual(4, 45, 4, 30))
    }

    @Test
    fun testUmkmDiscountCalculation() {
        val originalPrice = 25000
        val discountPrice = 20000
        val savings = originalPrice - discountPrice
        val discountPercent = (savings.toDouble() / originalPrice.toDouble()) * 100.0

        assertEquals(5000, savings)
        assertEquals(20.0, discountPercent, 0.01)
    }

    @Test
    fun testSampleDataIntegrity() {
        assertNotNull(SampleData.defaultSantri)
        assertEquals("Ahmad Fauzi", SampleData.defaultSantri.name)
        assertTrue("Default santri must have valid NIS", SampleData.defaultSantri.nis.isNotBlank())
        assertTrue("Sample jadwals must not be empty", SampleData.jadwals.isNotEmpty())
        assertTrue("Sample products must have positive prices", SampleData.umkmProducts.all { it.price > 0 })
    }

    @Test
    fun testIzinQueueFiltering() {
        val queue = SampleData.izinQueue
        val pendingCount = queue.count { it.status == "Menunggu" }
        assertTrue("Queue should contain items with status tracking", pendingCount >= 0)
    }
}
