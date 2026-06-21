package controllers.Admin;

import utils.FormatUtils;
import views.Admin.TagihanPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.Admin.TagihanDAO;

import java.util.List;

public class TagihanController {

    private final TagihanPanel view;
    private final TagihanDAO dao;

    public TagihanController(TagihanPanel view) {
        this.view = view;
        this.dao = new TagihanDAO();

        loadTable();
        initAction();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Object[]> listData = dao.getAllTagihanTableData();

        for (Object[] rowData : listData) {
            
            double totalTagihan = (Double) rowData[3];
            String formatTotal = FormatUtils.formatRupiah(totalTagihan);

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
                formatTotal, 
                tanggal,     
                status       
            };

            model.addRow(row);
        }
    }

    private void initAction() {
        view.getBtnBuatTagihan().addActionListener(e -> buatTagihan());
        view.getBtnKirimReminder().addActionListener(e -> kirimReminder());
        view.getBtnHapus().addActionListener(e -> hapus());
    }

    private void buatTagihan() {
        JOptionPane.showMessageDialog(view, "Fitur Buat Tagihan belum dibuat.");
    }

    private void kirimReminder() {
        int row = view.getTableTagihan().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih invoice tagihan terlebih dahulu.");
            return;
        }

        String invoice = view.getTableTagihan().getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(view, "Pengingat untuk " + invoice + " berhasil dikirim (Simulasi).");
    }

    private void hapus() {
        int row = view.getTableTagihan().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih invoice tagihan yang akan dihapus.");
            return;
        }

        String invoice = view.getTableTagihan().getValueAt(row, 0).toString();
        JOptionPane.showMessageDialog(view, "Fitur Batalkan/Hapus untuk " + invoice + " belum dibuat.");
    }
}