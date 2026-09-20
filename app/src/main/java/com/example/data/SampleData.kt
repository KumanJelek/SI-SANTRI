package com.example.data

object SampleData {
    const val PESANTREN_LOGO_URL = "https://lh3.googleusercontent.com/aida/AEtjO1WZbgDJkvy4LiMuuAInDj1vEkFKAIommrA7ThQb2aR4uw5-0mmRecNW92ABhIGkgP0hJTv8yLxIyskAlX1IlI-f3xY-wYABEvkL2kiemi8UiT8VePoz775Sh_s-hZx-7nP9kfz4K0m0ds46WkrfvX6xLEG1VrSywALRnlC_3r7V7vEdTsLDIDD4kcJGVq3MhGVyNUlgUwdKTivlHCnX7QYU5xMhstCi3X_1_qHl1UE89OK6doDb4nNoXCtj"

    val defaultSantri = SantriProfile()
    val defaultPengurus = PengurusProfile()

    val jadwals = listOf(
        JadwalItem(
            id = "j1",
            title = "Kajian Akbar Kitab Fathul Qorib & Bidayatul Hidayah",
            subtitle = "Materi: Bab Fiqih Muamalat & Adab Luhur Penuntut Ilmu",
            ustadz = "KH. Abdullah & Gus Faiz, M.Pd.",
            location = "Masjid Jami' Pesantren (Lantai Utama Putra)",
            timeRange = "18:30 - 20:00 WIB",
            date = "Jumat, 19 Sep 2026",
            category = "kitab",
            isLive = true,
            presenceWindow = "18:15 - 18:45 WIB"
        ),
        JadwalItem(
            id = "j2",
            title = "Kajian Kitab Bulughul Maram (Hadits Ahkam)",
            subtitle = "Materi: Bab Shalat Berjama'ah & Shalat Jamak Qashar",
            ustadz = "Dr. KH. Maimun Zubair Jr., M.Ag",
            location = "Aula Serbaguna Asrama Al-Farabi",
            timeRange = "20:15 - 21:45 WIB",
            date = "Jumat, 19 Sep 2026",
            category = "kitab",
            isLive = false,
            presenceWindow = "20:00 - 20:30 WIB"
        ),
        JadwalItem(
            id = "j3",
            title = "Muroja'ah Al-Qur'an Juz 1-5 & Setoran Hafalan Baru",
            subtitle = "Target per-santri: 1 Maqra' Tasmi' + 1 Halaman Mutqin",
            ustadz = "Ust. M. Rizqi Al-Hafidz & Tim Musyrif",
            location = "Musholla Lantai 2 Asrama Al-Farabi",
            timeRange = "05:15 - 06:30 WIB",
            date = "Sabtu, 20 Sep 2026",
            category = "tahfidz",
            isLive = false,
            presenceWindow = "05:10 - 05:40 WIB"
        ),
        JadwalItem(
            id = "j4",
            title = "Roan Akbar & Kerja Bakti Lingkungan Asrama Santri",
            subtitle = "Pembersihan kamar, selasar asrama, taman asri & sanitasi bersama",
            ustadz = "Biro Kebersihan & Pengurus Wilayah",
            location = "Seluruh Area Asrama Al-Farabi & Selasar Barat",
            timeRange = "06:00 - 07:30 WIB",
            date = "Sabtu, 20 Sep 2026",
            category = "asrama",
            isLive = false,
            presenceWindow = "06:00 - 06:30 WIB"
        )
    )

    val pengumumanList = listOf(
        PengumumanItem(
            id = "p1",
            title = "Jadwal Libur Hari Raya & Penertiban Prosedur Perizinan Santri Semester Gasal",
            desc = "Seluruh wali santri dan santri mukim diwajibkan menyelesaikan administrasi perizinan kepulangan maksimal tanggal 10 November 2026 melalui aplikasi SI-SANTRI.",
            date = "24 Okt 2026",
            readCount = 420
        ),
        PengumumanItem(
            id = "p2",
            title = "Ketertiban Sholat Berjamaah & Pakaian Putih Malam Jumat",
            desc = "Seluruh santri wajib mengenakan jubah putih bersih setiap malam Jumat saat menghadiri kajian akbar dan qiyamullail di Masjid Jami'.",
            date = "18 Sep 2026",
            readCount = 389
        )
    )

    val artikelList = listOf(
        ArtikelItem(
            id = "a1",
            title = "Keutamaan Menuntut Ilmu di Era Digital",
            author = "Dr. KH. Abdullah Arifin, M.A.",
            date = "22 Okt 2026",
            readTime = "4 mnt baca",
            category = "Tausiyah"
        ),
        ArtikelItem(
            id = "a2",
            title = "Menjaga Adab Terhadap Guru & Sesama Penuntut Ilmu",
            author = "Gus Faiz, M.Pd.",
            date = "16 Sep 2026",
            readTime = "5 mnt baca",
            category = "Adab & Akhlaq"
        )
    )

    val presensiLive = listOf(
        PresensiLiveItem(
            id = "pr1",
            name = "Ahmad Fauzi",
            initials = "AF",
            kamar = "B-04",
            group = "b01-b06",
            activity = "Hadir • Halaqah Subuh",
            timeAgo = "2 mnt lalu",
            method = "KTS"
        ),
        PresensiLiveItem(
            id = "pr2",
            name = "Zainal Muttaqin",
            initials = "ZM",
            kamar = "B-01",
            group = "b01-b06",
            activity = "Hadir • Sholat Ashar",
            timeAgo = "5 mnt lalu",
            method = "GPS"
        ),
        PresensiLiveItem(
            id = "pr3",
            name = "Hafidz Syaifullah",
            initials = "HS",
            kamar = "B-05",
            group = "b01-b06",
            activity = "Izin Klinik • Istirahat",
            timeAgo = "12 mnt lalu",
            method = "Posko"
        ),
        PresensiLiveItem(
            id = "pr4",
            name = "Lukman Hakim",
            initials = "LH",
            kamar = "B-08",
            group = "b07-b12",
            activity = "Hadir • Muwajjahah Kitab",
            timeAgo = "18 mnt lalu",
            method = "KTS"
        ),
        PresensiLiveItem(
            id = "pr5",
            name = "Ilyas Basayev",
            initials = "IB",
            kamar = "A-03",
            group = "b01-b06",
            activity = "Izin Pulang • Wali Kamar",
            timeAgo = "24 mnt lalu",
            method = "Posko"
        )
    )

    val izinQueue = listOf(
        IzinQueueItem(
            id = "iz1",
            santriName = "Ahmad Fauzi",
            kamar = "Kamar B-04",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCqEB0quAUuMr7G9DDcrEf_Rso6EQ3dqTPMSIB7Hdt5jG87DFIoJynu3CKh_S6a23sHEk5sBGdXeo09S4IVoMjmhonf31x4oe5HzVYd25h8vpIeXs5Z4yQGPZ2pU8M_ZSqe3yveQ28ZFuXvew_hjJ3deDPmpRQASHPryawFlRSUPh8qR10Q3btszDC3BpAQW6GPRhyV9WHEQhUUHDH4600dIwBpjGK5zxQvCgDa7NuRe9nDo5JYJF--Ww",
            type = "Sakit",
            reason = "Sakit Demam (Klinik Pondok Pesantren)",
            attachmentName = "Resep Dokter Klinik.pdf",
            submittedTime = "Hari ini, 09:20 WIB"
        ),
        IzinQueueItem(
            id = "iz2",
            santriName = "Ilyas Basayev",
            kamar = "Kamar A-03",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDxxkE6WjuKTI-7ijfWci4VjmY4_w4WgVHKhOxTu_bWcMgUE_nsQCIIsTuTQkE8jvU_vPg3djUvHh1rpaVdn-Ln0zD__bjkxwmxnkJcP3sdk6XCzqGDniDzAtN-CsFE2Ztp2aiDsVUCmaqmA0uBmdYvlrmx1Qc1tkebYWd-mMDlSschwxJQn9nTQ9LW7WwcO0XEvIuIwOPih9HikZSnnYq0L2V9kiLRatYTYs4PIMetFE5CJa0qbyPLlA",
            type = "Izin Pulang",
            reason = "Pernikahan Saudara Kandung (2 Hari)",
            attachmentName = "Disetujui Wali Kamar: Ust. Halim",
            submittedTime = "Kemarin, 21:15 WIB"
        ),
        IzinQueueItem(
            id = "iz3",
            santriName = "Muhammad Rizqi",
            kamar = "Kamar B-02",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDqgOQhdi1p0U7ZorKYvMnFhEVK1y7WyhrBBQu4frEAaDJDldWUMgAC7KPH77HjDpgyKsOcWnZUJU_OmSexj72MHMS5ea_PtV3UAZdgMuJt_3hTREPnTP0PwEleNukwFU-gWHLtR-3lSXht9cqBSb0pB7M1hDVGJFYJohaLjKMx4AOQM3J5QxH1JTatUP7MOGdU_Xzl2kKlgoKacXd6W0PvhwxYOv_fIMLT48pJTkq_Dz5RSkqkbQ6oTQ",
            type = "Sakit",
            reason = "Demam & Flu (38.2°C) Rawat Inap Klinik",
            attachmentName = "Surat Dokter PKU Pusat.pdf",
            submittedTime = "Hari ini, 11:00 WIB"
        )
    )

    val umkmProducts = listOf(
        UmkmProduct(
            id = "p1",
            name = "Minyak Angin Herbal Santri 10ml",
            price = 18000,
            originalPrice = 20000,
            category = "makanan",
            sellerName = "Ahmad (Asrama Al-Farabi B-04)",
            sellerRoom = "Asrama Al-Farabi B-04",
            sellerPhone = "6281234567890",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDYQ4fSEGviituQWmi7bPrfDUiEe6J6jVKVMRQD2hy6eNqjaHqDjmnn_kodKeVzt59F_39SPlaYSXAcdf0OiwAkZuDPrW0wxcJ3O-753ipcdZSrqph5fQP69ACRE_L0HI88WFMKj2Jkwv8UpY3XPolSXV25wDHQbIaa0ZfDueO2YJ5qZvlCuvQcb6RYkKj52ICbbxEd5kl_nW8OYzd_WqVi-PwJco40uVmuXmqDq47f11gknEklIBuQqA",
            badge = "Terlaris",
            rating = 4.9,
            soldCount = 340,
            stock = 48,
            description = "Racikan murni ekstrak daun bidara sidr, minyak zaitun perasan pertama, peppermint oil, dan habbatus sauda. Membantu meredakan pusing, mabuk perjalanan, serta memberikan sensasi tenang saat mengaji dan qiyamullail.",
            ingredients = listOf(
                "Ekstrak Daun Bidara Arab Sidr",
                "Extra Virgin Olive Oil",
                "Minyak Habbatussauda murni dingin",
                "Essential Peppermint & Camphor alami"
            )
        ),
        UmkmProduct(
            id = "p2",
            name = "Paket Kitab Kuning Makna Pesantren",
            price = 28000,
            originalPrice = 28000,
            category = "kitab",
            sellerName = "Zubair (Asrama Ibnu Rusyd)",
            sellerRoom = "Asrama Ibnu Rusyd C-02",
            sellerPhone = "6281234567891",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCg_9VaVy3nigKJ7NOqvcNLTGD5yV7LsGP1RygKFVCH8pRXzCCxvkC2tHWvu3XNVzQ5RXk2v2C25BayP9XsR7l4txDh9y7ySkbvcyQJAX3mEW3JDfanNZOGvuHo13HHAw77nEwaqsfR6JcUOxgRamApJBXPEGsoaYWh1_NSOC4A56GGKx0mJF3b1hif9TLiodGD-miLMsZG6wC6JkLDsiLcnnmqzcBAHjjZFautZ_jVzXSJFW4fMDRI1Q",
            badge = "Original",
            rating = 5.0,
            soldCount = 85,
            stock = 25,
            description = "Kitab kuning matan lengkap dengan makna pesantren ala pegon Jawa dan penjelasan ringkas bab fiqih dan tasawuf dasar.",
            ingredients = listOf("Kertas kuning tebal", "Jilid rapi kuat", "Makna pegon terverifikasi ustadz")
        ),
        UmkmProduct(
            id = "p3",
            name = "Kripik Pedas Renyah Asrama 250g",
            price = 12000,
            originalPrice = 12000,
            category = "makanan",
            sellerName = "Kop. Santriwati Khadijah",
            sellerRoom = "Kompleks Putri Khadijah",
            sellerPhone = "6281234567892",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB93yxiHSId9jzwx4x5y4TjZBBJh1CUR_Psud3jK0OPndLDEl7vTILEZpgfwmuCTiLGrg9bCYNuZaUbzPkp2tZVhQRiyzGHD4jkcab3Z2T6agftbf6XDdw-F-oSdQf_Jp9LG6ykTCXwjelj8PoZ-c4zc6mMpgOYhKPWgzyLH9iloRr0UypEn4gHrqdWvjKlApwvDlmQNtZscQpAb6YdybyquVV0Q9X0NwUcnUoAynHpCgBJnbcqVbUC8Q",
            badge = "Camilan",
            rating = 4.8,
            soldCount = 210,
            stock = 60,
            description = "Keripik singkong renyah balado daun jeruk tanpa pengawet sintetis. Olahan tangan terampil santriwati dengan cita rasa gurih nagih.",
            ingredients = listOf("Singkong pilihan", "Cabai asli", "Daun jeruk segar", "Bumbu rempah alami")
        ),
        UmkmProduct(
            id = "p4",
            name = "Sarung Tenun Goyor Halus Pesantren",
            price = 85000,
            originalPrice = 95000,
            category = "pakaian",
            sellerName = "Rifqi (Asrama Al-Ghazali)",
            sellerRoom = "Asrama Al-Ghazali",
            sellerPhone = "6281234567893",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAg_hynUVzYsev60rp_u7inQfqaopJtH3nKeOaOIKgo8QpQ9MBDdaNNnSXOJWRh7KCHz3I9R4U5rH-HybJuH0c1PLbMEdfr8fcYBpm0jhI9h96eFXqmjOK6cgVrPF9LWotM-WO-h8F2hKKHqemMfXqn6Ei5a-5cdU7myYTSc-KXVpCWT_9n6x9kd1vozCXN2MFk4M7M-iFlk6Vis24vH2fO6F-V_hkTwAMPtWyRWZV2gb3_h440q7JtYg",
            badge = "Eksklusif",
            rating = 4.9,
            soldCount = 45,
            stock = 14,
            description = "Sarung tenun goyor halus adem motif tradisional khas santri mukim. Jahitan rapi, tidak luntur, dan sangat nyaman dipakai shalat.",
            ingredients = listOf("Rayon viscose premium", "Pewarna tekstil ramah lingkungan")
        ),
        UmkmProduct(
            id = "p5",
            name = "Jasa Laundry Bersih & Rapi Santri",
            price = 6000,
            originalPrice = 6000,
            category = "jasa",
            sellerName = "Tim Mandiri Asrama B-02",
            sellerRoom = "Asrama Al-Farabi B-02",
            sellerPhone = "6281234567894",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC6vRjG_s81hNY2oT1lL2c5MAJ5nC3TUuJhWHDRoHyg5LjOrvjtFMzZJBgKpdJQZuYKACIWHD29HCeWF08BqenccpnHcNmFLIBXBMrIpdIpVQRyI9zQ-GwwchQpJVTULR7wPlzOQegclYQAe3LWoax3wfBe3cnT8ogVCUkZat5I20beNcdnnkfzql_smqom2h78wwN4fSzEcCct4xMTNArzLD0N_ZTPUu8iu8_uJ1AYH9kn-AxlRDV5SQ",
            badge = "Layanan",
            rating = 4.9,
            soldCount = 340,
            stock = 99,
            description = "Jasa cuci setrika harum rapi khusus baju koko, sarung, gamis, dan pakaian harian santri. Antar jemput kamar asrama.",
            ingredients = listOf("Deterjen wangi suci", "Setrika uap rapi", "Kemasan plastik rapi per paket")
        ),
        UmkmProduct(
            id = "p6",
            name = "Kaligrafi Kayu Ukir Ayat Kursi",
            price = 120000,
            originalPrice = 135000,
            category = "seni",
            sellerName = "Faris (Divisi Seni Kaligrafi)",
            sellerRoom = "Asrama Al-Farabi B-04",
            sellerPhone = "6281234567895",
            photoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDKd9-DPXSKIueAK7GBF00VETuF7jhgWGlB3KoZttXkpc-JRNlv__afpvOis1M3KX7NUBz4OxuD3J5kW76UTNV_vVlRopc8dVNSnLr3I3bW19giQn1ZDoQkIFtt6ZJolfmIkeVvQNCHZYML1LAtFbx6wJxyphqq9rkoEC0nel9opznFq-QxvO37YPl_JB6-aDPrsJLWu59fVJDp7ky_2iwKb0NI6HKSQN22i60VGZKMDpBXDl_msVCs2w",
            badge = "Karya Seni",
            rating = 5.0,
            soldCount = 18,
            stock = 5,
            description = "Ukiran kayu jati belanda handmade khat Tsuluts kaligrafi Ayat Kursi. Dilapisi cat gold premium dan finishing vernis natural.",
            ingredients = listOf("Kayu jati belanda oven", "Cat gold metalik", "Kait gantungan dinding")
        )
    )

    val lapakOrders = listOf(
        UmkmOrderItem(
            id = "ord1",
            buyerName = "Ust. Mansyur",
            buyerRole = "Pengurus Asrama Al-Ghazali",
            timeAgo = "10 mnt lalu",
            productName = "Minyak Angin Aromaterapi Barakah Santri 10ml",
            qty = 2,
            totalPrice = 36000,
            variant = "Original Mint Segar",
            pickupMethod = "COD Depan Masjid Jami (Usai Isya Berjamaah)",
            status = "Menunggu Siap"
        ),
        UmkmOrderItem(
            id = "ord2",
            buyerName = "Zubair",
            buyerRole = "Santri Asrama Ibnu Rusyd C-02",
            timeAgo = "35 mnt lalu",
            productName = "Minyak Angin Aromaterapi Barakah Santri 10ml",
            qty = 1,
            totalPrice = 18000,
            variant = "Lavender Sidr Herbal",
            pickupMethod = "Titip Meja Kasir Koperasi OSPAI",
            status = "Sedang Disiapkan"
        )
    )

    val supportMessages = listOf(
        SupportMessage(
            id = "m1",
            sender = "Santri",
            senderName = "Ahmad Fauzi (Santri)",
            message = "Assalamu'alaikum ustadz, bagaimana alur pengajuan izin sakit dan dispensasi keluar pondok jika lebih dari 2 hari?",
            time = "14:15 WIB"
        ),
        SupportMessage(
            id = "m2",
            sender = "Bot",
            senderName = "Jawaban 1: Asisten Bot AI (Instan)",
            message = "Wa'alaikumsalam wr. wb. Untuk perizinan >2 hari, santri wajib melampirkan surat dokter di menu Perizinan dan meminta persetujuan Ketua Asrama.",
            time = "14:15 WIB",
            isBot = true
        ),
        SupportMessage(
            id = "m3",
            sender = "Pengurus",
            senderName = "Ust. Marzuki (Piket)",
            message = "Santri Ahmad Fauzi, laporan izin Anda telah kami terima ke meja piket Asrama Al-Farabi. Silakan konfirmasi ke kantor sekretariat ba'da Ashar untuk pengambilan surat jalan resmi atau hubungi WhatsApp Pengurus.",
            time = "14:20 WIB"
        )
    )
}
