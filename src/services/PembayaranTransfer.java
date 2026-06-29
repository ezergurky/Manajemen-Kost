package services;

import dao.Penghuni.TagihanDAO;
import interfaces.PembayaranService;

public class PembayaranTransfer implements PembayaranService {
    private final double biayaAdmin = 2500.0;
    private final TagihanDAO dao = new TagihanDAO();

    @Override
    public String getNamaMetode() {
        return "Transfer Bank";
    }

    @Override
    public double hitungTotalBayar(double tagihanAwal) {
        return tagihanAwal + biayaAdmin;
    }

    @Override
    public boolean prosesPembayaran(int idTagihan, double tagihanAwal) {
        return dao.bayarTagihan(idTagihan, getNamaMetode(), hitungTotalBayar(tagihanAwal));
    }
}