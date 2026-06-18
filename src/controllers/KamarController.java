package controllers;

import dao.KamarDAO;
import models.Kamar;
import views.Admin.DataKamarPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class KamarController {

    private final DataKamarPanel view;
    private final KamarDAO dao;

    public KamarController(DataKamarPanel view) {
        this.view = view;
        this.dao = new KamarDAO();

        loadTable();
        initAction();
    }

    // ==========================
    // LOAD DATA KE TABEL
    // ==========================
    public void loadTable() {

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Kamar> list = dao.getAll();

        for (Kamar kamar : list) {

            Object[] row = {
                    kamar.getNomor(),
                    "-",
                    "-",
                    kamar.getHarga(),
                    kamar.getStatus()
            };

            model.addRow(row);
        }
    }

    // ==========================
    // ACTION BUTTON
    // ==========================
    private void initAction() {

        view.getBtnTambah().addActionListener(e -> tambah());

        view.getBtnEdit().addActionListener(e -> edit());

        view.getBtnHapus().addActionListener(e -> hapus());

    }

    // ==========================
    // TAMBAH
    // ==========================
    private void tambah() {

        JOptionPane.showMessageDialog(view,
                "Fitur Tambah Kamar belum dibuat.");

    }

    // ==========================
    // EDIT
    // ==========================
    private void edit() {

        int row = view.getTableKamar().getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(view,
                    "Pilih data yang akan diedit.");

            return;
        }

        JOptionPane.showMessageDialog(view,
                "Fitur Edit Kamar belum dibuat.");

    }

    // ==========================
    // HAPUS
    // ==========================
    private void hapus() {

        int row = view.getTableKamar().getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(view,
                    "Pilih data yang akan dihapus.");

            return;
        }

        JOptionPane.showMessageDialog(view,
                "Fitur Hapus Kamar belum dibuat.");

    }

}