package models;

import java.util.ArrayList;
import java.util.List;

public class Laporan {
    private List<Pembayaran> dataPembayaran = new ArrayList<>();

    public Laporan(List<Pembayaran> dataPembayaran) {
        this.dataPembayaran = dataPembayaran;
    }

    protected void generateBulanan() {

    }

    public void generateTahunan() {

    }
}
