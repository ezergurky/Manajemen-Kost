package models;

import java.util.ArrayList;
import java.util.List;

public class Laporan {
    private List<Pembayaran> dataPembayaran = new ArrayList<>();

    public Laporan(List<Pembayaran> dataPembayaran) {
        this.dataPembayaran = dataPembayaran;
    }

    public List<Pembayaran> getDataPembayaran() {
        return dataPembayaran;
    }

    public void setDataPembayaran(List<Pembayaran> dataPembayaran) {
        this.dataPembayaran = dataPembayaran;
    }

    public double generateLaporanPendapatan(String bulan, String tahun) {
        double total = 0;
        for (Pembayaran p : dataPembayaran) {
            boolean matchBulan = bulan.equals("Semua Bulan") || p.getTagihan().getBulan().equalsIgnoreCase(bulan);
            boolean matchTahun = tahun.equals("Semua Tahun") || String.valueOf(p.getTagihan().getTahun()).equals(tahun);
            boolean matchStatus = p.getTagihan().getStatus().equalsIgnoreCase("lunas");
            
            if (matchBulan && matchTahun && matchStatus) {
                total += p.getJumlahBayar();
            }
        }
        return total;
    }
}