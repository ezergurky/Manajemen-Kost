package controllers.Admin;

import utils.FormatUtils;
import views.Admin.PembayaranPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.Admin.PembayaranDAO;

import java.util.List;

public class PembayaranController {

    private final PembayaranPanel view;
    private final PembayaranDAO dao;

    public PembayaranController(PembayaranPanel view) {
        this.view = view;
        this.dao = new PembayaranDAO();

        loadTable();
        initAction();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Object[]> listData = dao.getAllPembayaranTableData();

        for (Object[] rowData : listData) {
            
            Object tanggal = rowData[3];
            if (tanggal instanceof java.util.Date) {
                tanggal = FormatUtils.formatTanggal((java.util.Date) tanggal);
            } else if (tanggal == null) {
                tanggal = "-";
            }

            double jumlahBayar = (Double) rowData[5];
            String formatJumlah = FormatUtils.formatRupiah(jumlahBayar);

            Object[] row = {
                rowData[0], 
                rowData[1], 
                rowData[2], 
                tanggal,    
                rowData[4], 
                formatJumlah 
            };

            model.addRow(row);
        }
    }

    private void initAction() {
        view.getBtnVerifikasi().addActionListener(e -> verifikasi());
        view.getBtnCetakKwitansi().addActionListener(e -> cetakKwitansi());
        view.getBtnHapus().addActionListener(e -> hapus());
    }

    private void verifikasi() {
        JOptionPane.showMessageDialog(view, "Fitur Verifikasi Pembayaran belum dibuat.");
    }

    private void cetakKwitansi() {
        int row = view.getTablePembayaran().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data pembayaran terlebih dahulu untuk dicetak.");
            return;
        }

        String idBayar = view.getTablePembayaran().getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(view, "Mencetak kwitansi untuk " + idBayar + " (Simulasi).");
    }

    private void hapus() {
        int row = view.getTablePembayaran().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data pembayaran yang akan dihapus.");
            return;
        }

        String idBayar = view.getTablePembayaran().getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(view, "Fitur Hapus untuk " + idBayar + " belum dibuat.");
    }
}