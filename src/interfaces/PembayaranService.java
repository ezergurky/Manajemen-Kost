package interfaces;

public interface PembayaranService {
    String getNamaMetode();
    double hitungTotalBayar(double tagihanAwal);
    boolean prosesPembayaran(int idTagihan, double tagihanAwal);
}
