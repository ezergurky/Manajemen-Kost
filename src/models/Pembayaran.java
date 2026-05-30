package models;

import java.util.Date;

public class Pembayaran {
    private int idPembayaran;
    private Date tanggal;
    private double jumlah;
    private String status;

    public Pembayaran(int idPembayaran, Date tanggal, double jumlah, String status) {
        this.idPembayaran = idPembayaran;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.status = status;
    }

    public void konfirmasi() {
        
    }

    public void cetakStruk() {
        
    }
}
