package models;

import java.util.Date;

public class Tagihan {
    private int idTagihan;
    private int idPenghuni;
    private String bulan;
    private int tahun;
    private Date jatuhTempo;
    private double jumlah;
    private double denda;
    private String status;

    private Penghuni penghuni;

    public Tagihan(int idTagihan, int idPenghuni, String bulan, int tahun, Date jatuhTempo, double jumlah, double denda, String status) {
        this.idTagihan = idTagihan;
        this.idPenghuni = idPenghuni;
        this.bulan = bulan;
        this.tahun = tahun;
        this.jatuhTempo = jatuhTempo;
        this.jumlah = jumlah;
        this.denda = denda;
        this.status = status;
    }

    public Tagihan(int idTagihan, int idPenghuni, String bulan, int tahun, Date jatuhTempo, double jumlah, String status) {
        this(idTagihan, idPenghuni, bulan, tahun, jatuhTempo, jumlah, 0.0, status);
    }

    public double hitungTotal() {
        return this.jumlah; 
    }

    public double hitungTotal(boolean tambahDenda) {
        if (tambahDenda) {
            return this.jumlah + this.denda;
        }
        return this.jumlah;
    }

    public int getIdTagihan() { 
        return idTagihan; 
    }

    public int getIdPenghuni() { 
        return idPenghuni; 
    }

    public String getBulan() { 
        return bulan; 
    }

    public int getTahun() { 
        return tahun; 
    }

    public Date getJatuhTempo() { 
        return jatuhTempo; 
    }

    public double getJumlah() { 
        return jumlah; 
    }

    public double getDenda() { 
        return denda; 
    }

    public String getStatus() { 
        return status; 
    }

    public Penghuni getPenghuni() { 
        return penghuni; 
    }

    public void setPenghuni(Penghuni penghuni) {
        this.penghuni = penghuni; 
    }
}