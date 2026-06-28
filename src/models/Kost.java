package models;

import java.util.ArrayList;
import java.util.List;

public class Kost {
    private int idKost;
    private String namaKost;
    private String alamat;
    private List<Kamar> daftarKamar = new ArrayList<>();

    public Kost(int idKost, String namaKost, String alamat) {
        this.idKost = idKost;
        this.namaKost = namaKost;
        this.alamat = alamat;
    }

    public int getIdKost() {
        return idKost;
    }

    public void setIdKost(int idKost) {
        this.idKost = idKost;
    }

    public String getNamaKost() {
        return namaKost;
    }

    public void setNamaKost(String namaKost) {
        this.namaKost = namaKost;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setDaftarKamar(List<Kamar> daftarKamar) {
        this.daftarKamar = daftarKamar; 
    }
    
    public List<Kamar> getDaftarKamar() { 
        return daftarKamar; 
    }
}