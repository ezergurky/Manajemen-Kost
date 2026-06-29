package controllers.Admin;

import dao.Admin.PembayaranDAO;
import models.Pembayaran;
import utils.FormatUtils;
import views.Admin.PembayaranPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PembayaranController {
    private final PembayaranPanel view;
    private final PembayaranDAO dao;
    private final List<Pembayaran> listPembayaran;

    public PembayaranController(PembayaranPanel view) {
        this.view = view;
        this.dao = new PembayaranDAO();
        this.listPembayaran = new ArrayList<>();

        loadTable();
        initAction();
        initSearch();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        listPembayaran.clear();

        List<Pembayaran> data = dao.getAllPembayaran();
        for (Pembayaran pay : data) {
            listPembayaran.add(pay);

            String idPembayaran = "PAY-" + String.format("%04d", pay.getIdPembayaran());
            String noInvoice = "INV-" + String.format("%04d", pay.getIdTagihan());
            String namaPenghuni = pay.getTagihan().getPenghuni().getNama();
            String tglBayar = FormatUtils.formatTanggal(pay.getTanggalBayar());
            String metode = pay.getMetode();
            String jumlahBayar = FormatUtils.formatRupiah(pay.getJumlahBayar());
            
            String status = pay.getTagihan().getStatus().equalsIgnoreCase("lunas") ? "Lunas" : "Menunggu Verifikasi";

            Object[] row = {
                idPembayaran,   
                noInvoice,      
                namaPenghuni,   
                tglBayar,       
                metode,         
                jumlahBayar,
                status    
            };
            model.addRow(row);
        }
    }

    private void initAction() {
        view.getBtnVerifikasi().addActionListener(e -> verifikasi());
        view.getBtnCetak().addActionListener(e -> cetakKwitansi());
        view.getBtnHapus().addActionListener(e -> hapus());
    }

    private void initSearch() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(view.getTableModel());
        view.getTablePembayaran().setRowSorter(sorter);

        view.getTxtSearch().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() {
                String text = view.getTxtSearch().getText();
                if (text.trim().isEmpty() || text.contains("Cari")) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private void verifikasi() {
        int row = view.getTablePembayaran().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih data pembayaran yang akan diverifikasi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTablePembayaran().convertRowIndexToModel(row);
        Pembayaran pay = listPembayaran.get(modelRow);

        if (pay.getTagihan().getStatus().equalsIgnoreCase("lunas")) {
            JOptionPane.showMessageDialog(view, "Pembayaran ini sudah diverifikasi sebelumnya (Tagihan Lunas).", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Verifikasi pembayaran dari " + pay.getTagihan().getPenghuni().getNama() + "?\n(Tagihan terkait akan otomatis diubah menjadi Lunas).", "Verifikasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.verifikasi(pay.getIdTagihan())) {
                JOptionPane.showMessageDialog(view, "Pembayaran berhasil diverifikasi!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadTable();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal memverifikasi pembayaran.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cetakKwitansi() {
        int row = view.getTablePembayaran().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih data pembayaran untuk dicetak.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTablePembayaran().convertRowIndexToModel(row);
        Pembayaran pay = listPembayaran.get(modelRow);

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

    private void hapus() {
        int row = view.getTablePembayaran().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih data pembayaran yang akan dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTablePembayaran().convertRowIndexToModel(row);
        Pembayaran pay = listPembayaran.get(modelRow);

        int confirm = JOptionPane.showConfirmDialog(view, "Apakah Anda yakin ingin menghapus transaksi PAY-" + String.format("%04d", pay.getIdPembayaran()) + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(pay.getIdPembayaran())) {
                JOptionPane.showMessageDialog(view, "Data pembayaran berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadTable();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menghapus data pembayaran.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}