package models;

import java.util.Date;

public class KontrakSewa {
    private int idKontrak;
    private int idPenghuni;
    private int idKamar;
    private Date tanggalMulai;
    private Date tanggalSelesai;
    private String status;

    public KontrakSewa(int idKontrak, int idPenghuni, int idKamar, Date tanggalMulai, Date tanggalSelesai, String status) {
        this.idKontrak = idKontrak;
        this.idPenghuni = idPenghuni;
        this.idKamar = idKamar;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.status = status;
    }

    public int getIdKontrak() { 
        return idKontrak; 
    }
    
    public void setIdKontrak(int idKontrak) { 
        this.idKontrak = idKontrak; 
    }
    
    public int getIdPenghuni() { 
        return idPenghuni; 
    }
    
    public int getIdKamar() { 
        return idKamar; 
    }
    
    public Date getTanggalMulai() { 
        return tanggalMulai; 
    }
    
    public Date getTanggalSelesai() { 
        return tanggalSelesai; 
    }

    public void setTanggalSelesai(Date tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai; 
    }
    
    public String getStatus() { 
        return status; 
    }
}