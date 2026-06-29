package controllers.Penghuni;

import dao.Penghuni.TagihanDAO;
import interfaces.PembayaranService;
import models.Penghuni;
import models.Tagihan;
import services.PembayaranCash;
import services.PembayaranTransfer;
import utils.FormatUtils;
import views.Penghuni.TagihanPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TagihanController {

    private final TagihanPanel view;
    private final TagihanDAO dao;
    private final int idPenghuni;
    private final List<Tagihan> listTagihan;
    private final Penghuni penghuniAktif;

    public TagihanController(TagihanPanel view, int idPenghuni) {
        this.view = view;
        this.idPenghuni = idPenghuni;
        this.dao = new TagihanDAO();
        this.listTagihan = new ArrayList<>();
        
        this.penghuniAktif = new Penghuni(0, "", "", "", "", idPenghuni, "", "");

        loadTable();
        initAction();
        initSearch();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        listTagihan.clear();

        List<Tagihan> data = penghuniAktif.lihatTagihan(dao);
        for (Tagihan t : data) {
            listTagihan.add(t);
            
            String noInvoice = "INV-" + String.format("%04d", t.getIdTagihan());
            String bulan = t.getBulan() + " " + t.getTahun();
            String jatuhTempo = FormatUtils.formatTanggal(t.getJatuhTempo());
            String pokok = FormatUtils.formatRupiah(t.hitungTotal()); 
            String denda = FormatUtils.formatRupiah(t.getDenda());

            String total = FormatUtils.formatRupiah(t.hitungTotal(true));
            
            String statusTeks = t.getStatus().substring(0, 1).toUpperCase() + t.getStatus().substring(1).toLowerCase();
            if (statusTeks.equalsIgnoreCase("Belum") && dao.cekSudahDibayar(t.getIdTagihan())) {
                statusTeks = "Menunggu Verifikasi";
            }

            Object[] row = {
                noInvoice,
                bulan,
                jatuhTempo,
                pokok,
                denda,
                total,
                statusTeks
            };
            model.addRow(row);
        }
    }

    private void initAction() {
        view.getBtnBayar().addActionListener(e -> prosesBayar());
    }

    private void initSearch() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(view.getTableModel());
        view.getTableTagihan().setRowSorter(sorter);

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

    private void prosesBayar() {
        int row = view.getTableTagihan().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih tagihan yang ingin dibayar pada tabel.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTableTagihan().convertRowIndexToModel(row);
        Tagihan tagihan = listTagihan.get(modelRow);

        if (tagihan.getStatus().equalsIgnoreCase("lunas")) {
            JOptionPane.showMessageDialog(view, "Tagihan ini sudah lunas!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (dao.cekSudahDibayar(tagihan.getIdTagihan())) {
            JOptionPane.showMessageDialog(view, "Pembayaran untuk tagihan ini sedang diproses (Menunggu Verifikasi Admin).", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        double totalBayar = tagihan.hitungTotal(true);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(view), "Proses Pembayaran", true);
        dialog.setSize(400, 320);
        dialog.setLocationRelativeTo(view);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        formPanel.setBackground(Color.WHITE);

        JLabel lblInfo = new JLabel("<html><b>No. Invoice:</b> INV-" + String.format("%04d", tagihan.getIdTagihan()) + "<br><b>Total Tagihan:</b> " + FormatUtils.formatRupiah(totalBayar) + "</html>");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JComboBox<String> cbMetode = new JComboBox<>(new String[]{"Transfer Bank", "Tunai"});
        cbMetode.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        formPanel.add(lblInfo);
        formPanel.add(new JLabel("Pilih Metode Pembayaran:"));
        formPanel.add(cbMetode);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);
        JButton btnBatal = new JButton("Batal");
        JButton btnKonfirmasi = new JButton("Konfirmasi Bayar");

        btnBatal.addActionListener(e -> dialog.dispose());
        btnKonfirmasi.addActionListener(e -> {
            String metode = cbMetode.getSelectedItem().toString();
            PembayaranService servicePembayaran;

            if (metode.equals("Transfer Bank")) {
                servicePembayaran = new PembayaranTransfer();
            } else {
                servicePembayaran = new PembayaranCash();
            }

            double totalAkhir = servicePembayaran.hitungTotalBayar(totalBayar);
            if (totalAkhir != totalBayar) {
                int confirm = JOptionPane.showConfirmDialog(dialog, 
                    "Metode ini memiliki penyesuaian biaya layanan/admin.\nTotal yang harus dibayar menjadi: " + FormatUtils.formatRupiah(totalAkhir) + "\n\nLanjutkan?", 
                    "Konfirmasi Biaya Layanan", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;
            }

            if (penghuniAktif.bayar(tagihan, servicePembayaran)) {
                JOptionPane.showMessageDialog(dialog, "Pembayaran menggunakan " + servicePembayaran.getNamaMetode() + " berhasil disubmit!\nSilakan tunggu admin memverifikasi pembayaran Anda.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                loadTable();
            } else {
                JOptionPane.showMessageDialog(dialog, "Gagal memproses pembayaran.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPanel.add(btnBatal);
        btnPanel.add(btnKonfirmasi);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}