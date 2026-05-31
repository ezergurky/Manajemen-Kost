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

    void generateBulanan() {
        System.out.println("Membuat laporan bulanan...");

        double totalPendapatan = 0;

        for (Pembayaran pembayaran : dataPembayaran) {
            if (pembayaran.getStatus().equalsIgnoreCase("Lunas")) {
                totalPendapatan += pembayaran.getJumlah();
            }
        }

        System.out.println("Total Pendapatan Bulanan : Rp" + totalPendapatan);
    }

    public void generateTahunan() {
        System.out.println("Membuat laporan tahunan...");

        double totalPendapatan = 0;

        for (Pembayaran pembayaran : dataPembayaran) {
            if (pembayaran.getStatus().equalsIgnoreCase("Lunas")) {
                totalPendapatan += pembayaran.getJumlah();
            }
        }

        System.out.println("Total Pendapatan Tahunan : Rp" + totalPendapatan);
    }

    @Override
    public String toString() {
        return "Laporan{" +
                "jumlahDataPembayaran=" + dataPembayaran.size() +
                '}';
    }
}
