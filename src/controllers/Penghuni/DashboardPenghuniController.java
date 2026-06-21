package controllers.Penghuni;

import dao.Penghuni.DashboardPenghuniDAO;
import utils.FormatUtils;
import views.Penghuni.DashboardPenghuniPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.util.List;

public class DashboardPenghuniController {
    private final DashboardPenghuniPanel view;
    private final DashboardPenghuniDAO dao;
    private final int idPenghuni;

    public DashboardPenghuniController(DashboardPenghuniPanel view, int idPenghuni) {
        this.view = view;
        this.idPenghuni = idPenghuni;
        this.dao = new DashboardPenghuniDAO();
        
        loadStatistics();
        loadRecentActivities();
    }

    private void loadStatistics() {
        Object[] infoKamar = dao.getInfoKamar(idPenghuni);
        if (infoKamar != null) {
            view.getLblKamarValue().setText("Kamar " + infoKamar[0].toString());
            view.getLblKamarFooter().setText(infoKamar[1].toString());
        } else {
            view.getLblKamarValue().setText("Belum Ada");
            view.getLblKamarFooter().setText("Hubungi Admin");
        }

        Object[] tagihan = dao.getTagihanTerbaru(idPenghuni);
        if (tagihan != null) {
            double nominal = (Double) tagihan[0];
            String bulan = tagihan[1].toString();
            String tahun = tagihan[2].toString();
            String statusRaw = tagihan[3].toString();
            String status = statusRaw.substring(0, 1).toUpperCase() + statusRaw.substring(1).toLowerCase();

            view.getLblTagihanValue().setText(FormatUtils.formatRupiah(nominal));
            view.getLblTagihanFooter().setText("Bulan: " + bulan + " " + tahun);

            view.getLblStatusValue().setText(status.toUpperCase());
            
            if (statusRaw.equalsIgnoreCase("lunas")) {
                view.getLblStatusValue().setForeground(new Color(16, 185, 129));
                view.getLblStatusFooter().setText("Terverifikasi otomatis");
            } else {
                view.getLblStatusValue().setForeground(new Color(239, 68, 68));
                view.getLblStatusFooter().setText("Menunggu pembayaran");
            }
        } else {
            view.getLblTagihanValue().setText("Rp 0");
            view.getLblTagihanFooter().setText("Belum ada tagihan");
            view.getLblStatusValue().setText("-");
            view.getLblStatusFooter().setText("-");
        }
    }

    private void loadRecentActivities() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Object[]> riwayatList = dao.getRiwayatPembayaran(idPenghuni);

        for (Object[] rowData : riwayatList) {
            double nominal = (Double) rowData[4];
            String formatNominal = FormatUtils.formatRupiah(nominal);
            
            Object tanggal = rowData[2];
            if (tanggal instanceof java.util.Date) {
                tanggal = FormatUtils.formatTanggal((java.util.Date) tanggal);
            }
            
            Object[] row = {
                rowData[0], 
                rowData[1], 
                tanggal, 
                rowData[3], 
                formatNominal
            };
            model.addRow(row);
        }
    }
}