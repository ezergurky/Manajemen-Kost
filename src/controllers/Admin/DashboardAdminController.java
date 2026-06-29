package controllers.Admin;

import utils.FormatUtils;
import views.Admin.DashboardAdminPanel;

import javax.swing.table.DefaultTableModel;

import dao.Admin.DashboardAdminDAO;
 
import java.util.List;

public class DashboardAdminController {
    private final DashboardAdminPanel view;
    private final DashboardAdminDAO dao;

    public DashboardAdminController(DashboardAdminPanel view) {
        this.view = view;
        this.dao = new DashboardAdminDAO();
        
        loadStatistics();
        loadRecentActivities();
    }

    public void loadStatistics() {
        int[] kamarStats = dao.getKamarStats();
        int totalKamar = kamarStats[0];
        int terisi = kamarStats[1];
        int kosong = totalKamar - terisi;

        view.getLblTotalKamarValue().setText(terisi + " / " + totalKamar);
        view.getLblTotalKamarFooter().setText(kosong + " Kamar Kosong");

        int totalPenghuni = dao.getTotalPenghuniAktif();
        view.getLblTotalPenghuniValue().setText(totalPenghuni + " Orang");
        view.getLblTotalPenghuniFooter().setText("Kontrak aktif saat ini");

        double pendapatan = dao.getPendapatanBulanIni();
        view.getLblPendapatanValue().setText(FormatUtils.formatRupiah(pendapatan));
        view.getLblPendapatanFooter().setText("Bulan ini");
    }

    public void loadRecentActivities() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        List<Object[]> aktivitasList = dao.getAktivitasTerbaru();

        for (Object[] rowData : aktivitasList) {
            double nominal = (Double) rowData[3];
            String formatNominal = FormatUtils.formatRupiah(nominal);
            
            Object tanggal = rowData[0];
            if (tanggal instanceof java.util.Date) {
                tanggal = FormatUtils.formatTanggal((java.util.Date) tanggal);
            }
            
            Object[] row = {
                rowData[0], 
                rowData[1], 
                rowData[2], 
                formatNominal
            };
            model.addRow(row);
        }
    }
}