package models;

import java.util.List;
import dao.Penghuni.TagihanDAO;
import interfaces.PembayaranService;
import views.MainFramePenghuni;

public class Penghuni extends User {
    private int idPenghuni;
    private String nik;
    private String noHp;

    public Penghuni(int idUser, String nama, String email, String password, String username, int idPenghuni, String nik, String noHp) {
        super(idUser, nama, email, password, username);
        this.idPenghuni = idPenghuni;
        this.nik = nik;
        this.noHp = noHp;
    }

    public int getIdPenghuni() { 
        return idPenghuni;
    }
    
    public String getNik() {
        return nik; 
    }
    
    public String getNoHp() {
        return noHp; 
    }

    public List<Tagihan> lihatTagihan(TagihanDAO dao) {
        return dao.getTagihanByPenghuni(this.idPenghuni);
    }

    public boolean bayar(Tagihan tagihan, PembayaranService metodePembayaran) {
        double totalTagihan = tagihan.hitungTotal(true);
        return metodePembayaran.prosesPembayaran(tagihan.getIdTagihan(), totalTagihan);
    }

    @Override
    public void displayDashboard() {
        new MainFramePenghuni(this.getIdPenghuni(), super.getNama(), super.getEmail()).setVisible(true);
    }
}