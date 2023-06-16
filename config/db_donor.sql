-- phpMyAdmin SQL Dump
-- version 5.3.0-dev+20220621.da7c7a84e1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 04 Jun 2023 pada 18.44
-- Versi server: 10.4.24-MariaDB
-- Versi PHP: 8.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_donor`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `blood_requests`
--

CREATE TABLE `blood_requests` (
  `id` int(11) NOT NULL,
  `id_request` varchar(255) DEFAULT NULL,
  `nama_pasien` varchar(255) DEFAULT NULL,
  `jml_kantong` varchar(255) DEFAULT NULL,
  `tipe_darah` varchar(255) DEFAULT NULL,
  `rhesus` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `prov` varchar(255) DEFAULT NULL,
  `kota` varchar(255) DEFAULT NULL,
  `nama_rs` varchar(255) DEFAULT NULL,
  `deskripsi` varchar(255) DEFAULT NULL,
  `nama_keluarga` varchar(255) DEFAULT NULL,
  `telp_keluarga` varchar(255) DEFAULT NULL,
  `verified` tinyint(1) DEFAULT 0,
  `createdBy` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `blood_requests`
--

INSERT INTO `blood_requests` (`id`, `id_request`, `nama_pasien`, `jml_kantong`, `tipe_darah`, `rhesus`, `gender`, `prov`, `kota`, `nama_rs`, `deskripsi`, `nama_keluarga`, `telp_keluarga`, `verified`, `createdBy`, `createdAt`, `updatedAt`) VALUES
(1, 'req-B0a4jGDOHu', 'Joni sumarjoni', '5', 'A', 'Positif', 'Male', 'Riau', 'Pekanbaru', 'RSUD Kota Dumai', 'buthuh minum darah', 'vampir', '2431234', 0, 'user-1P2ytZCYCt', '2023-06-03 19:44:39', '2023-06-03 19:44:39'),
(2, 'req-jJd7Dtigxa', 'Jono sumarjono', '5', 'A', 'Negative', 'Male', 'Riau', 'Bengkalis', 'RSUD Bengkalis', 'buthuh minum darah', 'Vampir', '2431234', 0, 'user-1P2ytZCYCt', '2023-06-03 19:47:42', '2023-06-03 19:47:42'),
(3, 'req-nbto7ZhNY1', 'Jeni jeni', '5', 'A', 'Negative', 'Male', 'Riau', 'Bengkalis', 'RSUD Bengkalis', 'buthuh minum darah', 'Vampir', '2431234', 0, '', '2023-06-04 12:22:24', '2023-06-04 12:22:24'),
(4, 'req-_Ydj7AvpLE', 'Jeni jeni', '5', 'A', 'Negative', 'Male', 'Riau', 'Bengkalis', 'RSUD Bengkalis', 'buthuh minum darah', 'Vampir', '2431234', 0, '', '2023-06-04 14:32:26', '2023-06-04 14:32:26');

-- --------------------------------------------------------

--
-- Struktur dari tabel `blood_types`
--

CREATE TABLE `blood_types` (
  `id` int(11) NOT NULL,
  `tipe_darah` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `blood_types`
--

INSERT INTO `blood_types` (`id`, `tipe_darah`, `createdAt`, `updatedAt`) VALUES
(1, 'A', '2023-05-26 14:25:34', '2023-05-26 14:25:34'),
(2, 'B', '2023-05-26 14:25:34', '2023-05-26 14:25:34'),
(3, 'AB', '2023-05-26 14:25:34', '2023-05-26 14:25:34'),
(4, 'O', '2023-05-26 14:25:34', '2023-05-26 14:25:34');

-- --------------------------------------------------------

--
-- Struktur dari tabel `cities`
--

CREATE TABLE `cities` (
  `id` int(11) NOT NULL,
  `id_prov` int(11) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `cities`
--

INSERT INTO `cities` (`id`, `id_prov`, `city`, `createdAt`, `updatedAt`) VALUES
(1, 1, 'Pekanbaru', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(2, 1, 'Bengkalis', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(3, 1, 'Dumai', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(4, 1, 'Indragiri Hilir', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(5, 1, 'Indragiri Hulu', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(6, 1, 'Kampar', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(7, 1, 'Kepulauan Meranti', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(8, 1, 'Pelalawan', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(9, 1, 'Rokan Hilir', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(10, 1, 'Rokan Hulu', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(11, 1, 'Siak', '2023-05-25 15:45:06', '2023-05-25 15:45:06'),
(12, 1, 'Kuantan Singingi', '2023-05-25 15:45:06', '2023-05-25 15:45:06');

-- --------------------------------------------------------

--
-- Struktur dari tabel `donors`
--

CREATE TABLE `donors` (
  `id` int(11) NOT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `id_donor` varchar(255) DEFAULT NULL,
  `id_request` varchar(255) DEFAULT NULL,
  `nama_pendonor` varchar(255) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `telp` varchar(255) DEFAULT NULL,
  `gol_darah` varchar(255) DEFAULT NULL,
  `rhesus` varchar(255) DEFAULT NULL,
  `last_donor` datetime DEFAULT NULL,
  `nama_rs` varchar(255) DEFAULT NULL,
  `alamat_rs` varchar(255) DEFAULT NULL,
  `nama_pasien` varchar(255) DEFAULT NULL,
  `verified` tinyint(1) DEFAULT 0,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `donors`
--

INSERT INTO `donors` (`id`, `uid`, `id_donor`, `id_request`, `nama_pendonor`, `alamat`, `telp`, `gol_darah`, `rhesus`, `last_donor`, `nama_rs`, `alamat_rs`, `nama_pasien`, `verified`, `createdAt`, `updatedAt`) VALUES
(1, 'user-1P2ytZCYCt', 'donor-mJ9kpD3xrV', NULL, 'Sasuke', 'Konoha', '08123456789', 'Z', 'Netral', '0000-00-00 00:00:00', 'RSUD Bengkalis', 'Negara Api', 'Sakura', 0, '2023-06-03 19:39:58', '2023-06-03 19:39:58'),
(2, NULL, 'donor-GB9sv92prp', NULL, 'Sasuke', 'Konoha', '08123456789', 'Z', 'Netral', '0000-00-00 00:00:00', 'RSUD Bengkalis', 'Negara Api', 'Sakura', 0, '2023-06-04 12:22:49', '2023-06-04 12:22:49');

-- --------------------------------------------------------

--
-- Struktur dari tabel `hospitals`
--

CREATE TABLE `hospitals` (
  `id` int(11) NOT NULL,
  `id_city` int(11) DEFAULT NULL,
  `nama_rs` varchar(255) DEFAULT NULL,
  `alamat_rs` varchar(255) DEFAULT NULL,
  `telp_rs` varchar(255) DEFAULT NULL,
  `koordinat` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `hospitals`
--

INSERT INTO `hospitals` (`id`, `id_city`, `nama_rs`, `alamat_rs`, `telp_rs`, `koordinat`, `latitude`, `longitude`, `createdAt`, `updatedAt`) VALUES
(1, 2, 'Rumah Sakit Umum Daerah Kecamatan Mandau', 'Jl. Stadion No.10, Air Jamban, Kec. Mandau, Kabupaten Bengkalis, Riau 28784', '(0765) 596341', '1.2797288434312866, 101.16866829713983', '1.2797288434312866', '101.16866829713983', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(2, 2, 'Rumah Sakit Permata Hati Mandau', 'Jl. Jenderal Sudirman No.37, Gajah Sakti, Kec. Mandau, Kabupaten Bengkalis, Riau 28784', '0812-3001-1650', '1.270759112321488, 101.20181726108062', '1.270759112321488', '101.20181726108062', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(3, 2, 'RSU MUTIASARI', 'Jl. Kebun Karet, Air Jamban, Kec. Mandau, Kabupaten Bengkalis, Riau 28784', '(0765) 560043', '1.2858192298369098, 101.19228613891758', '1.2858192298369098', '101.19228613891758', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(4, 2, 'RSUD Bengkalis', 'Jl. Kelapapati Tengah No.90, Klp. Pati, Kec. Bengkalis, Kabupaten Bengkalis, Riau 28711', '(0766) 7008100', '1.4883969808015551, 102.08867313743507', '1.4883969808015551', '102.08867313743507', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(5, 1, 'Arifin Achmad General Hospital Riau Province', 'Jl. Diponegoro No.2, Sumahilang, Kec. Pekanbaru Kota, Kota Pekanbaru, Riau 28156', '(0761) 21618', '0.5233522054895853, 101.45187688783554', '0.5233522054895853', '101.45187688783554', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(6, 1, 'Rumah Sakit Islam Ibnu Sina Pekanbaru', 'Jl. Melati No.60, Harjosari, Kec. Sukajadi, Kota Pekanbaru, Riau 28122', '(0761) 24242', '0.526270443890083, 101.43695367516973', '0.526270443890083', '101.43695367516973', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(7, 1, 'RS Awal Bros Pekanbaru A. Yani', 'Jl. Jend. Ahmad Yani No.73, Tanah Datar, Kec. Pekanbaru Kota, Kota Pekanbaru, Riau 28156', '(0761) 21000', '0.5209785560371697, 101.44346327837145', '0.5209785560371697', '101.44346327837145', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(8, 3, 'RSUD Kota Dumai', 'Jl. Tj. Jati No.4, Buluh Kasap, Kec. Dumai Tim., Kota Dumai, Riau 28812', '(0765) 440992', '1.6803151913295324, 101.45953141121467', '1.6803151913295324', '101.45953141121467', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(9, 3, 'Graha Yasmine Hospital', 'Jl. Marlan Jaya, Bukit Datuk, Kec. Dumai Bar., Kota Dumai, Riau 28826', '0822-8824-7142', '1.6587087880101483, 101.43456011330801', '1.6587087880101483', '101.43456011330801', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(10, 4, 'Rumah sakit Bunda Puja', 'Jl. Batang Tuaka, Tembilahan Kota, Kec. Tembilahan, Kabupaten Indragiri Hilir, Riau 29214', '(0768) 7026111', '-0.31636356431849005, 103.14737250614415', '-0.31636356431849005', '103.14737250614415', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(11, 4, 'RSUD Tengku Sulung', 'Pulau Kijang, Reteh, Indragiri Hilir Regency, Riau 29273', '0852-2015-2875', '-0.6968303039389497, 103.18901639083333', '-0.6968303039389497', '103.18901639083333', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(12, 5, 'RSUD Indrasari Rengat', 'Pematang Reba, Kec. Rengat Bar., Kabupaten Indragiri Hulu, Riau 29351', '', '-0.39440003310224103, 102.44186222639101', '-0.39440003310224103', '102.44186222639101', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(13, 5, 'RS KASIH IBU RENGAT', 'Jalan Azki Aris, Sekip Hulu, Rengat, Indragiri Hulu Regency, Riau 29314', '07692320132', '-0.3732729979504814, 102.53364777726414', '-0.3732729979504814', '102.53364777726414', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(14, 6, 'RSUD Bangkinang', 'Ring Roads Bangkinang-BatuBelah Km.01, Kumantan, Kec. Bangkinang, Kabupaten Kampar, Riau 28463', '', '0.35225817166538903, 101.04839887141632', '0.35225817166538903', '101.04839887141632', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(15, 6, 'Mesra Hospital', 'Jalan Raya Pasir Putih No. 3 A-C, Siak Hulu, Tanah Merah, Kec. Siak Hulu, Kabupaten Kampar, Riau 28284', '(0761) 71965', '0.4396750502796694, 101.45231700575543', '0.4396750502796694', '101.45231700575543', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(16, 7, 'RSUD Kab. Kepulauan Meranti', 'Jl. Dorak, Banglas, Kec. Tebing Tinggi, Kabupaten Kepulauan Meranti, Riau 28753', '', '0.9988017316563698, 102.72498552183455', '0.9988017316563698', '102.72498552183455', '2023-05-25 16:44:18', '2023-05-25 16:44:18'),
(17, 7, 'RSUD Meranti', 'Jl. Dorak, Banglas, Kec. Tebing Tinggi, Kabupaten Kepulauan Meranti, Riau 28753', NULL, '1.002468459378901, 102.73336514987714', '1.002468459378901', '102.73336514987714', '2023-05-25 17:51:54', '2023-05-25 17:51:54'),
(18, 8, 'RSUD SELASIH', 'Pangkalan Kerinci Barat, Pangkalan Kerinci, Pangkalan Kerinci Bar., Kec. Pelalawan, Kabupaten Pelalawan, Riau 28654', '(0761) 493986', '0.4124289960952447, 101.84014585713717', '0.4124289960952447', '101.84014585713717', '2023-05-25 17:51:54', '2023-05-25 17:51:54'),
(19, 8, 'RS Amalia Medika', 'Jl. Lintas Sumatra No.433, Pangkalan Kerinci Kota, Kec. Pangkalan Kerinci, Kabupaten Pelalawan, Riau 28353', '0853-4040-2332', '0.40650592396749274, 101.85844619879151', '0.40650592396749274', '101.85844619879151', '2023-05-25 17:51:54', '2023-05-25 17:51:54'),
(20, 9, 'RSUD Dr. RM Pratomo Bagansiapiapi', 'Bagan Timur, Bangko, Rokan Hilir Regency, Riau 28912', '(0767) 21731', '2.157636827650475, 100.80961267725978', '2.157636827650475', '100.80961267725978', '2023-05-25 17:51:54', '2023-05-25 17:51:54'),
(21, 9, 'Rumah Sakit Umum Indah', 'JL. Jendral Sudirman, RT. 005/01, Bagan Batu, Kec. Bagan Sinembah, Kabupaten Rokan Hilir, Riau 21464', '(0765) 552008', '1.6850819036415878, 100.41843045957657', '1.6850819036415878', '100.41843045957657', '2023-05-25 17:51:54', '2023-05-25 17:51:54'),
(22, 10, 'Hospital Kab.Rokan Hulu', 'Jl. Syekh Ismail, Pematang Berangan, Kec. Rambah, Kabupaten Rokan Hulu, Riau 28558', '(0762) 91777', '0.8811896078045751, 100.2866842675198', '0.8811896078045751', '100.2866842675198', '2023-05-25 17:51:54', '2023-05-25 17:51:54'),
(23, 10, 'RS Surya Insani', 'Jl. Diponegoro No.KM. 4, Koto Tinggi, Kec. Rambah, Kabupaten Rokan Hulu, Riau 28557', '0823-9058-4363', '0.849376154583353, 100.32391065564998', '0.849376154583353', '100.32391065564998', '2023-05-25 17:51:54', '2023-05-25 17:51:54'),
(24, 11, 'Siak District General Hospital', 'Jl. Raja Kecik No.1, Kp. Dalam, Kec. Siak, Kabupaten Siak, Riau 28773', '(0764) 20011', '0.8001768066354951, 102.04788724601059', '0.8001768066354951', '102.04788724601059', '2023-05-25 17:51:54', '2023-05-25 17:51:54'),
(25, 11, 'RSUD Perawang', 'Perawang Barat, Tualang, Siak Regency, Riau 28685', '', '0.6880240194356381, 101.57225551800879', '0.6880240194356381', '101.57225551800879', '2023-05-25 17:51:54', '2023-05-25 17:51:54');

-- --------------------------------------------------------

--
-- Struktur dari tabel `otps`
--

CREATE TABLE `otps` (
  `id` int(11) NOT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `expiredAt` datetime DEFAULT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `provinces`
--

CREATE TABLE `provinces` (
  `id` int(11) NOT NULL,
  `provinsi` varchar(255) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `provinces`
--

INSERT INTO `provinces` (`id`, `provinsi`, `createdAt`, `updatedAt`) VALUES
(1, 'Riau', '2023-05-25 15:10:02', '2023-05-25 15:10:02');

-- --------------------------------------------------------

--
-- Struktur dari tabel `rhesus`
--

CREATE TABLE `rhesus` (
  `id` int(11) NOT NULL,
  `rhesus` varchar(255) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `rhesus`
--

INSERT INTO `rhesus` (`id`, `rhesus`, `createdAt`, `updatedAt`) VALUES
(1, 'Positive', '2023-05-26 14:38:47', '2023-05-26 14:38:47'),
(2, 'Negative', '2023-05-26 14:38:47', '2023-05-26 14:38:47');

-- --------------------------------------------------------

--
-- Struktur dari tabel `stocks`
--

CREATE TABLE `stocks` (
  `id` int(11) NOT NULL,
  `id_rs` int(11) DEFAULT NULL,
  `id_darah` int(11) DEFAULT NULL,
  `id_rhesus` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `stocks`
--

INSERT INTO `stocks` (`id`, `id_rs`, `id_darah`, `id_rhesus`, `stock`, `createdAt`, `updatedAt`) VALUES
(1, 1, 1, 1, 12, '2023-05-26 14:39:44', '2023-05-26 14:39:44'),
(2, 2, 2, 2, 7, '2023-05-26 14:39:44', '2023-05-26 14:39:44'),
(3, 7, 3, 1, 14, '2023-05-26 14:51:15', '2023-05-26 14:51:15'),
(4, 5, 4, 1, 10, '2023-05-26 14:51:15', '2023-05-26 14:51:15');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telp` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nik` varchar(255) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `ttl` datetime DEFAULT NULL,
  `gol_darah` varchar(255) DEFAULT NULL,
  `rhesus` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_donor` datetime DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `refresh_token` text DEFAULT NULL,
  `verified` tinyint(1) DEFAULT 0,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `blood_requests`
--
ALTER TABLE `blood_requests`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `blood_types`
--
ALTER TABLE `blood_types`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `cities`
--
ALTER TABLE `cities`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `donors`
--
ALTER TABLE `donors`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `hospitals`
--
ALTER TABLE `hospitals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nama_rs` (`nama_rs`);

--
-- Indeks untuk tabel `otps`
--
ALTER TABLE `otps`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `provinces`
--
ALTER TABLE `provinces`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `rhesus`
--
ALTER TABLE `rhesus`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `stocks`
--
ALTER TABLE `stocks`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `blood_requests`
--
ALTER TABLE `blood_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `blood_types`
--
ALTER TABLE `blood_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `cities`
--
ALTER TABLE `cities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT untuk tabel `donors`
--
ALTER TABLE `donors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `hospitals`
--
ALTER TABLE `hospitals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT untuk tabel `otps`
--
ALTER TABLE `otps`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=106;

--
-- AUTO_INCREMENT untuk tabel `provinces`
--
ALTER TABLE `provinces`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `rhesus`
--
ALTER TABLE `rhesus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `stocks`
--
ALTER TABLE `stocks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;



