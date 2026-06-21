package controllers.Admin;

import utils.FormatUtils;
import views.Admin.LaporanPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.Admin.LaporanDAO;

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
                status = rawStatus.substring(0, 1).toUpperCase() + rawStatus.substring(1).toLowerCase();
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

        double totalPendapatan = dao.getTotalPendapatan(bulan, tahun);
        double totalTunggakan = dao.getTotalTunggakan(bulan, tahun);

        view.getLblTotalPendapatan().setText(FormatUtils.formatRupiah(totalPendapatan));
        view.getLblTotalTunggakan().setText(FormatUtils.formatRupiah(totalTunggakan));
    }

    private void exportPDF() {
        JOptionPane.showMessageDialog(view, "Fitur Ekspor PDF Laporan belum dibuat.");
    }
}