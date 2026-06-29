package controllers.Penghuni;

import dao.Penghuni.ProfileDAO;
import models.Penghuni;
import views.Penghuni.ProfilePanel;

public class ProfileController {
    private final ProfilePanel view;
    private final ProfileDAO dao;
    private final int idPenghuni;

    public ProfileController(ProfilePanel view, int idPenghuni) {
        this.view = view;
        this.idPenghuni = idPenghuni;
        this.dao = new ProfileDAO();
        loadData();
    } 

    private void loadData() {
        Penghuni p = dao.getProfilPenghuni(idPenghuni);
        if (p != null) {
            view.getLblNamaLengkap().setText(p.getNama());
            view.getLblNik().setText(p.getNik());
            view.getLblTelepon().setText(p.getNoHp());
            view.getLblEmail().setText(p.getEmail());
            view.getLblUsername().setText(p.getUsername()); 
            view.getLblRole().setText("Penghuni Kost");

            view.setNamaUser(p.getNama());
        }
    }
}