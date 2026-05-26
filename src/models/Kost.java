package models;

import java.util.ArrayList;
import java.util.List;

public class Kost {
    private String namaKost;
    private String alamat;
    private List<Kamar> daftarKamar = new ArrayList<>();

    public Kost(String namaKost, String alamat) {
        this.namaKost = namaKost;
        this.alamat = alamat;
    }

    public void tambahKamar(Kamar kamar) {
        if (kamar == null) {
            throw new IllegalArgumentException("Data kamar tidak boleh null!");
        }
        daftarKamar.add(kamar);
        System.out.println("Kamar berhasil ditambahkan.");
    }

    public void hapusKamar(int id) {
        for (int i = 0; i < daftarKamar.size(); i++) {
            if (daftarKamar.get(i).getIdKamar() == id) {
                daftarKamar.remove(i);
                System.out.println("Kamar berhasil dihapus.");
                return;
            }
        }
        System.out.println("Kamar dengan ID " + id + " tidak ditemukan.");
    }
}