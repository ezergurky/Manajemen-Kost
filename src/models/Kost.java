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

    }

    public void hapusKamar(int id) {
        
    }
}
