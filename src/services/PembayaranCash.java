package services;

import dao.Penghuni.TagihanDAO;
import interfaces.PembayaranService;

public class PembayaranCash implements PembayaranService {
    private final TagihanDAO dao = new TagihanDAO();

    @Override
    public String getNamaMetode() {
        return "Tunai (Cash)";
    }

    @Override
    public double hitungTotalBayar(double tagihanAwal) {
        return tagihanAwal;
    }

    @Override
    public boolean prosesPembayaran(int idTagihan, double tagihanAwal) {
        return dao.bayarTagihan(idTagihan, getNamaMetode(), hitungTotalBayar(tagihanAwal));
    }
}