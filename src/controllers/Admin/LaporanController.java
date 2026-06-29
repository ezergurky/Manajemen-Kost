package controllers.Admin;

import utils.FormatUtils;
import views.Admin.LaporanPanel;
import dao.Admin.LaporanDAO;
import dao.Admin.PembayaranDAO;
import models.Laporan;
import models.Pembayaran;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.List;

public class LaporanController {

    private final LaporanPanel view;
    private final LaporanDAO dao;

    public LaporanController(LaporanPanel view) {
        this.view = view;
        this.dao = new LaporanDAO();

        loadData("Semua Bulan", "Semua Tahun");
        initAction();
    }

    private void initAction() {
        view.getBtnFilter().addActionListener(e -> applyFilter());
        view.getBtnExportPDF().addActionListener(e -> exportPDF());
    }

    public void refreshData() {
        applyFilter(); 
    }

    private void applyFilter() {
        String bulan = view.getCbBulan().getSelectedItem().toString();
        String tahun = view.getCbTahun().getSelectedItem().toString();
        loadData(bulan, tahun);
    }

    private void loadData(String bulan, String tahun) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Object[]> listData = dao.getLaporanTableData(bulan, tahun);

        for (Object[] rowData : listData) {
            double pemasukan = (Double) rowData[3];
            String formatPemasukan = pemasukan > 0 ? FormatUtils.formatRupiah(pemasukan) : "-";

            String status = "-";
            if (rowData[4] != null) {
                String rawStatus = rowData[4].toString();
                if (rawStatus.equalsIgnoreCase("belum") && pemasukan > 0) {
                    status = "Menunggu Verifikasi";
                } else {
                    status = rawStatus.substring(0, 1).toUpperCase() + rawStatus.substring(1).toLowerCase();
                }
            }

            Object[] row = {
                rowData[0], 
                rowData[1], 
                rowData[2], 
                formatPemasukan, 
                status      
            };
            model.addRow(row);
        }

        PembayaranDAO pembayaranDAO = new PembayaranDAO();
        List<Pembayaran> semuaPembayaran = pembayaranDAO.getAllPembayaran();
        Laporan laporanObj = new Laporan(semuaPembayaran);

        double totalPendapatan = laporanObj.generateLaporanPendapatan(bulan, tahun);
        double totalTunggakan = dao.getTotalTunggakan(bulan, tahun);

        view.getLblTotalPendapatan().setText(FormatUtils.formatRupiah(totalPendapatan));
        view.getLblTotalTunggakan().setText(FormatUtils.formatRupiah(totalTunggakan));
    }

    private void exportPDF() {
        String bulan = view.getCbBulan().getSelectedItem().toString();
        String tahun = view.getCbTahun().getSelectedItem().toString();
        
        MessageFormat header = new MessageFormat("Laporan Keuangan Kost - Periode: " + bulan + " " + tahun);
        MessageFormat footer = new MessageFormat("Halaman {0}");

        try {
            boolean complete = view.getTableLaporan().print(JTable.PrintMode.FIT_WIDTH, header, footer);
            
            if (complete) {
                JOptionPane.showMessageDialog(view, "Dokumen berhasil diproses!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(view, "Gagal memproses dokumen: " + pe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}