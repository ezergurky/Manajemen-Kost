package controllers.Admin;

import models.Kamar;
import views.Admin.DataKamarPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.Admin.KamarDAO;

import java.util.List;

public class KamarController {

    private final DataKamarPanel view;
    private final KamarDAO dao;
    private List<Kamar> listKamar;

    public KamarController(DataKamarPanel view) {
        this.view = view;
        this.dao = new KamarDAO();

        loadTable();
        initAction();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        listKamar = dao.getAll();

        for (Kamar kamar : listKamar) {
            
            String hargaFormat = "Rp " + String.format("%,.0f", kamar.getHarga()).replace(',', '.');
            String statusFormat = kamar.getStatus().substring(0, 1).toUpperCase() + kamar.getStatus().substring(1);

            Object[] row = {
                    kamar.getNomorKamar(),
                    kamar.getTipeKamar(),
                    kamar.getFasilitas(),
                    hargaFormat,
                    statusFormat
            };

            model.addRow(row);
        }
    }

    private void initAction() {
        view.getBtnTambah().addActionListener(e -> tambah());
        view.getBtnEdit().addActionListener(e -> edit());
        view.getBtnHapus().addActionListener(e -> hapus());
    }

    private void tambah() {
        JOptionPane.showMessageDialog(view, "Fitur Tambah Kamar belum dibuat.");
    }

    private void edit() {
        int row = view.getTableKamar().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data yang akan diedit.");
            return;
        }

        Kamar selectedKamar = listKamar.get(row);
        JOptionPane.showMessageDialog(view, "Fitur Edit untuk Kamar " + selectedKamar.getNomorKamar() + " belum dibuat.");
    }

    private void hapus() {
        int row = view.getTableKamar().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data yang akan dihapus.");
            return;
        }

        Kamar selectedKamar = listKamar.get(row);
        JOptionPane.showMessageDialog(view, "Fitur Hapus untuk Kamar " + selectedKamar.getNomorKamar() + " belum dibuat.");
    }
}