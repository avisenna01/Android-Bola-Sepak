# IF3210-2020-14-BolaSepak
## Deskripsi Aplikasi
Aplikasi BolaSepak adalah sebuah aplikasi android yang memiliki fitur menghitung step count harian yang dilakukan oleh pengguna. Selain itu, aplikasi juga menampilkan jadwal pertandingan sepak bola beserta perkiraan cuaca di lokasi pertandingan. Pengguna dapat <i>subscribe</I> kesebelasan favorit dan mendapatkan notifikasi apabila terdapat pertandingan kesebelasan favorit pada hari tersebut.
Terdapat halaman detail pertandingan dengan:
1. Untuk pertandingan yang sudah selesai, ditampilkan skor, jumlah tembakan, pencetak gol, dan menit tercetaknya gol.
2. Untuk pertandingan yang belum diadakan, ditampilkan prediksi kondisi cuaca pada lokasi pertandingan.

## Cara Kerja
Pengguna membuka aplikasi dan apabila terdapat tim favorit pengguna yang bertanding pada hari tersebut terdapat notifikasi kepada pengguna. Fitur menghitung jumlah langkah juga diinisialisasi pada saat aplikasi dibuka. Pengguna dapat melakukan searching tim favorit untuk melihat riwayat maupun pertandingan yang akan datang. Pengguna dapat klik hasil pencarian dan berpindah ke halaman detail pertandingan. Pengguna dapat klik foto tim untuk masuk ke profil tim, yang menampilkan riwayat pertandingan dan pertandingan yang akan datang.

## Library yang Digunakan
1. Volley: Melakukan HTTP Request
2. Room: Melakukan pengaksesan SQLite
3. Glide: Melakukan operasi pada file gambar
4. RecyclerView cardview: Memudahkan pembuatan layout RecyclerView

## Screenshot