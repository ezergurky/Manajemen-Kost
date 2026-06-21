package controllers.Admin;

import dao.Admin.KostDAO;
import models.Kost;
import views.Admin.KostPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class KostController {
    private final KostPanel view;
    private final KostDAO dao;

    public KostController(KostPanel view) {
        this.view = view;
        this.dao = new KostDAO();

        loadTable();
        initAction();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Kost> listData = dao.getAll();

        for (Kost kost : listData) {
            Object[] row = {
                "KST-" + String.format("%03d", kost.getIdKost()), 
                kost.getNamaKost(), 
                kost.getAlamat()
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
        JOptionPane.showMessageDialog(view, "Fitur Tambah Cabang Kost belum dibuat.");
    }

    private void edit() {
        int row = view.getTableKost().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data kost yang akan diedit.");
            return;
        }

        String namaKost = view.getTableKost().getValueAt(row, 1).toString();
        JOptionPane.showMessageDialog(view, "Fitur Edit untuk properti " + namaKost + " belum dibuat.");
    }

    private void hapus() {
        int row = view.getTableKost().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data kost yang akan dihapus.");
            return;
        }

        String namaKost = view.getTableKost().getValueAt(row, 1).toString();
        JOptionPane.showMessageDialog(view, "Fitur Hapus untuk properti " + namaKost + " belum dibuat.");
    }
}