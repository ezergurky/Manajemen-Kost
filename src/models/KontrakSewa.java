package models;

import java.util.Date;

public class KontrakSewa {
    private int idKontrak;
    private Date tanggalMulai;
    private Date tanggalSelesai;
    private String status;

    public KontrakSewa(int idKontrak, Date tanggalMulai, Date tanggalSelesai, String status) {
        this.idKontrak = idKontrak;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.status = status;
    }

    public void aktifkan() {

    }

    public void selesai() {
        
    }
}
