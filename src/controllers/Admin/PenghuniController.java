package controllers.Admin;

import utils.FormatUtils;
import views.Admin.DataPenghuniPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.Admin.PenghuniDAO;

import java.util.List;

public class PenghuniController {

    private final DataPenghuniPanel view;
    private final PenghuniDAO dao;

    public PenghuniController(DataPenghuniPanel view) {
        this.view = view;
        this.dao = new PenghuniDAO();

        loadTable();
        initAction();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Object[]> listData = dao.getAllPenghuniTableData();

        for (Object[] rowData : listData) {
            
            Object tanggal = rowData[4];
            if (tanggal instanceof java.util.Date) {
                tanggal = FormatUtils.formatTanggal((java.util.Date) tanggal);
            } else if (tanggal == null) {
                tanggal = "-";
            }

            String status = "-";
            if (rowData[5] != null) {
                String rawStatus = rowData[5].toString();
                status = rawStatus.substring(0, 1).toUpperCase() + rawStatus.substring(1).toLowerCase();
            }

            Object[] row = {
                rowData[0], 
                rowData[1], 
                rowData[2], 
                rowData[3], 
                tanggal,    
                status      
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
        JOptionPane.showMessageDialog(view, "Fitur Tambah Penghuni belum dibuat.");
    }

    private void edit() {
        int row = view.getTablePenghuni().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data penghuni yang akan diedit.");
            return;
        }

        String idPenghuni = view.getTablePenghuni().getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(view, "Fitur Edit untuk " + idPenghuni + " belum dibuat.");
    }

    private void hapus() {
        int row = view.getTablePenghuni().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data penghuni yang akan dihapus.");
            return;
        }

        String idPenghuni = view.getTablePenghuni().getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(view, "Fitur Hapus untuk " + idPenghuni + " belum dibuat.");
    }
}