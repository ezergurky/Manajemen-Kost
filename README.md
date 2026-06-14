# Sistem Manajemen Kost

Sistem Manajemen Kost merupakan aplikasi desktop berbasis Java yang dikembangkan untuk membantu proses pengelolaan kost secara digital. Sistem ini mendukung pengelolaan data kamar, penghuni, kontrak sewa, tagihan, pembayaran, serta laporan keuangan.

Project ini dikembangkan sebagai tugas besar mata kuliah Desain dan Pemrograman Berorientasi Objek (DPBO) dengan menerapkan konsep Object-Oriented Programming (OOP) seperti inheritance, encapsulation, abstraction, polymorphism, interface, dan overloading.

---

## Fitur Utama

- Login Multi-Role (Admin & Penghuni)
- Manajemen Data Kamar
- Manajemen Data Penghuni
- Manajemen Kontrak Sewa
- Generate Tagihan Otomatis
- Pencatatan dan Verifikasi Pembayaran
- Laporan Keuangan
- Dashboard Admin dan Penghuni
- GUI berbasis Swing

---

## Teknologi yang Digunakan

- Java
- Swing
- MySQL
- JDBC
- Visual Studio Code

---

## Struktur Folder

```plaintext
src/
├── components/
├── config/
├── controllers/
├── dao/
├── interfaces/
├── main/
├── models/
├── services/
├── utils/
└── views/
```

---

## Konsep OOP yang Digunakan

- Encapsulation
- Inheritance
- Abstraction
- Polymorphism
- Interface
- Constructor
- Method Overloading
- Method Overriding

---

## Cara Menjalankan Project

1. Clone repository:
```bash
git clone https://github.com/ezergurky/Manajemen-Kost.git
```

2. Buka project di Visual Studio Code

3. Import database MySQL menggunakan file:
```plaintext
database/kost.sql
```

4. Pastikan JDBC Driver sudah tersedia pada folder:
```plaintext
lib/
```

5. Jalankan file:
```plaintext
MainApp.java
```

---

## Struktur Database

Database terdiri dari beberapa tabel utama:
- users
- kost
- kamar
- penghuni
- kontrak_sewa
- tagihan
- pembayaran

---

## Tim Pengembang

|               Nama                |
|-----------------------------------|
| Ezer Raditia Nayanta Sembiring    |
| Fikri Al Idris                    |
| Imanuel Putra Palembangan         |
| Muhammad Daffa Satria Sadikin     |

---

## Catatan

Project ini dikembangkan untuk kebutuhan pembelajaran dan implementasi konsep Pemrograman Berorientasi Objek (OOP).