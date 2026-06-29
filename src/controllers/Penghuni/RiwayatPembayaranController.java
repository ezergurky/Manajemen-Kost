package controllers.Penghuni;

import dao.Penghuni.RiwayatPembayaranDAO;
import models.Pembayaran;
import utils.FormatUtils;
import views.Penghuni.RiwayatPembayaranPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RiwayatPembayaranController {
    private final RiwayatPembayaranPanel view;
    private final RiwayatPembayaranDAO dao;
    private final int idPenghuni;
    
    private final List<Pembayaran> listRiwayat;

    public RiwayatPembayaranController(RiwayatPembayaranPanel view, int idPenghuni) {
        this.view = view;
        this.idPenghuni = idPenghuni;
        this.dao = new RiwayatPembayaranDAO();
        this.listRiwayat = new ArrayList<>();

        initAction();
        loadTable();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        listRiwayat.clear();

        List<Pembayaran> data = dao.getRiwayatByPenghuni(idPenghuni);

        for (Pembayaran pay : data) {
            listRiwayat.add(pay); // Simpan objek untuk cetak kwitansi nanti

            String idBayar = "PAY-" + String.format("%04d", pay.getIdPembayaran());
            String noInvoice = "INV-" + String.format("%04d", pay.getIdTagihan());
            
            String tanggal = "-";
            if (pay.getTanggalBayar() != null) {
                tanggal = FormatUtils.formatTanggal(pay.getTanggalBayar());
            }
            
            String metode = pay.getMetode();
            String formatJumlah = FormatUtils.formatRupiah(pay.getJumlahBayar());

            Object[] row = { idBayar, noInvoice, tanggal, metode, formatJumlah };
            model.addRow(row);
        }
    }

    private void initAction() {
        view.getBtnCetakKwitansi().addActionListener(e -> cetakKwitansi());
    }

    private void cetakKwitansi() {
        int row = view.getTableRiwayat().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih data riwayat pembayaran pada tabel untuk dicetak.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTableRiwayat().convertRowIndexToModel(row);
        Pembayaran pay = listRiwayat.get(modelRow);

        String kwitansi = "=========================================\n"
                        + "           KWITANSI PEMBAYARAN\n"
                        + "=========================================\n\n"
                        + "No. Transaksi : PAY-" + String.format("%04d", pay.getIdPembayaran()) + "\n"
                        + "Nama Penghuni : " + pay.getTagihan().getPenghuni().getNama() + "\n"
                        + "Keterangan    : Tagihan " + pay.getTagihan().getBulan() + " " + pay.getTagihan().getTahun() + "\n"
                        + "Tanggal Bayar : " + FormatUtils.formatTanggal(pay.getTanggalBayar()) + "\n"
                        + "Metode Bayar  : " + pay.getMetode() + "\n"
                        + "Total Dibayar : " + FormatUtils.formatRupiah(pay.getJumlahBayar()) + "\n\n"
                        + "Status Tagihan: " + (pay.getTagihan().getStatus().equalsIgnoreCase("lunas") ? "LUNAS (Terverifikasi)" : "Menunggu Verifikasi") + "\n\n"
                        + "=========================================\n"
                        + "       Terima Kasih Atas Pembayaran Anda";

        JTextArea textArea = new JTextArea(kwitansi);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(new Color(245, 245, 245));
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JOptionPane.showMessageDialog(view, textArea, "Kwitansi Pembayaran", JOptionPane.PLAIN_MESSAGE);
    }
}