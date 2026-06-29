package controllers.Admin;

import models.Kamar;
import models.Kost;
import utils.ValidasiException;
import views.Admin.DataKamarPanel;
import dao.Admin.KamarDAO;
import dao.Admin.KostDAO;
import dao.Admin.KontrakSewaDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class KamarController {

    private final DataKamarPanel view;
    private final KamarDAO dao;
    private List<Kamar> listKamar;

    public KamarController(DataKamarPanel view) {
        this.view = view;
        this.dao = new KamarDAO();

        loadTable();
        initAction();
        initSearch();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        listKamar = dao.getAll();

        Map<String, Integer> statistikTipeKamar = new HashMap<>();

        for (Kamar kamar : listKamar) {
            String hargaFormat = "Rp " + String.format("%,.0f", kamar.getHarga()).replace(',', '.');
            String statusFormat = kamar.getStatus().substring(0, 1).toUpperCase() + kamar.getStatus().substring(1);

            Object[] row = { 
                    kamar.getNomorKamar(),
                    kamar.getTipeKamar(),
                    kamar.getFasilitas(),
                    hargaFormat,
                    statusFormat
            };
            model.addRow(row);

            String tipe = kamar.getTipeKamar();
            if (tipe != null && !tipe.isEmpty()) {
                statistikTipeKamar.put(tipe, statistikTipeKamar.getOrDefault(tipe, 0) + 1);
            }
        }

        if (statistikTipeKamar.isEmpty()) {
            view.getLblStatistik().setText("Data kamar belum tersedia.");
        } else {
            StringBuilder statText = new StringBuilder("Statistik: ");
            int counter = 0;
            
            for (Map.Entry<String, Integer> entry : statistikTipeKamar.entrySet()) {
                statText.append(entry.getKey()).append(" (").append(entry.getValue()).append(" Kamar)");
                counter++;
                if (counter < statistikTipeKamar.size()) {
                    statText.append("  |  ");
                }
            }
            view.getLblStatistik().setText(statText.toString());
        }
    }

    private void initAction() {
        view.getBtnTambah().addActionListener(e -> tambah());
        view.getBtnEdit().addActionListener(e -> edit());
        view.getBtnHapus().addActionListener(e -> hapus());
    }

    private void initSearch() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(view.getTableModel());
        view.getTableKamar().setRowSorter(sorter);

        view.getTxtSearch().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() {
                String text = view.getTxtSearch().getText();
                if (text.trim().isEmpty() || text.equals("Cari nomor atau tipe kamar...")) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private void tambah() {
        showFormDialog(null);
    }

    private void edit() {
        int row = view.getTableKamar().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih data kamar pada tabel yang akan diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTableKamar().convertRowIndexToModel(row);
        Kamar selectedKamar = listKamar.get(modelRow);
        
        showFormDialog(selectedKamar);
    }

    private void hapus() {
        int row = view.getTableKamar().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih data kamar pada tabel yang akan dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTableKamar().convertRowIndexToModel(row);
        Kamar selectedKamar = listKamar.get(modelRow);

        int confirm = JOptionPane.showConfirmDialog(view, 
                "Apakah Anda yakin ingin menghapus Kamar Nomor: " + selectedKamar.getNomorKamar() + "?\n(Data kontrak sewa terkait mungkin akan terpengaruh)", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = dao.delete(selectedKamar.getIdKamar());
            if (success) {
                JOptionPane.showMessageDialog(view, "Data kamar berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadTable(); 
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menghapus data kamar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private double validasiInputForm(String nomor, String hargaStr) throws ValidasiException {
        if (nomor.isEmpty() || hargaStr.isEmpty()) {
            throw new ValidasiException("Nomor Kamar dan Harga Sewa wajib diisi!");
        }

        double harga;
        try {
            harga = Double.parseDouble(hargaStr);
        } catch (NumberFormatException ex) {
            throw new ValidasiException("Format harga salah!\nPastikan hanya menggunakan angka tanpa huruf atau simbol (Contoh: 1500000)");
        }

        if (harga <= 0) {
            throw new ValidasiException("Harga sewa kamar harus lebih besar dari 0!");
        }

        return harga;
    }

    private void showFormDialog(Kamar kamar) {
        KostDAO kostDAO = new KostDAO();
        List<Kost> listKost = kostDAO.getAll();

        if (listKost.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Data Cabang Kost masih kosong!\nSilakan tambahkan data kost terlebih dahulu di menu Data Kost.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(view), true);
        dialog.setTitle(kamar == null ? "Tambah Data Kamar" : "Edit Data Kamar");
        dialog.setSize(450, 480);
        dialog.setLocationRelativeTo(view);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        formPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        formPanel.setBackground(Color.WHITE);

        JComboBox<String> cbKost = new JComboBox<>();
        for (Kost k : listKost) {
            cbKost.addItem(k.getIdKost() + " - " + k.getNamaKost());
        }

        JTextField txtNomor = new JTextField();
        JTextField txtTipe = new JTextField();
        JTextField txtFasilitas = new JTextField();
        JTextField txtHarga = new JTextField();
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Tersedia", "Terisi"});

        if (kamar != null) {
            for (int i = 0; i < cbKost.getItemCount(); i++) {
                if (cbKost.getItemAt(i).startsWith(kamar.getIdKost() + " -")) {
                    cbKost.setSelectedIndex(i);
                    break;
                }
            }
            txtNomor.setText(kamar.getNomorKamar());
            txtTipe.setText(kamar.getTipeKamar());
            txtFasilitas.setText(kamar.getFasilitas());
            txtHarga.setText(String.format("%.0f", kamar.getHarga())); 
            cbStatus.setSelectedItem(kamar.getStatus().substring(0, 1).toUpperCase() + kamar.getStatus().substring(1).toLowerCase());
        }

        formPanel.add(new JLabel("Pilih Cabang Kost:")); formPanel.add(cbKost);
        formPanel.add(new JLabel("Nomor Kamar:")); formPanel.add(txtNomor);
        formPanel.add(new JLabel("Tipe Kamar (Contoh: VIP):")); formPanel.add(txtTipe);
        formPanel.add(new JLabel("Fasilitas (Dipisah Koma):")); formPanel.add(txtFasilitas);
        formPanel.add(new JLabel("Harga Sewa / Bulan (Rp):")); formPanel.add(txtHarga);
        formPanel.add(new JLabel("Status Ketersediaan:")); formPanel.add(cbStatus);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnPanel.setBackground(Color.WHITE);
        JButton btnBatal = new JButton("Batal");
        JButton btnSimpan = new JButton("Simpan Data");

        btnBatal.addActionListener(e -> dialog.dispose());
        
        btnSimpan.addActionListener(e -> {
            try {
                String nomor = txtNomor.getText().trim();
                String hargaStr = txtHarga.getText().trim();
                
                double harga = validasiInputForm(nomor, hargaStr);

                String selectedKostStr = (String) cbKost.getSelectedItem();
                int idKost = Integer.parseInt(selectedKostStr.split(" - ")[0]);
                String tipe = txtTipe.getText().trim();
                String fasilitas = txtFasilitas.getText().trim();
                String statusBaru = cbStatus.getSelectedItem().toString().toLowerCase();

                if (kamar == null) {
                    Kamar newKamar = new Kamar(0, idKost, nomor, tipe, fasilitas, harga, statusBaru);
                    if (dao.insert(newKamar)) {
                        JOptionPane.showMessageDialog(dialog, "Kamar berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        loadTable();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Gagal menambahkan kamar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    String statusLama = kamar.getStatus(); 
                    kamar.setIdKost(idKost);
                    kamar.setNomorKamar(nomor);
                    kamar.setTipeKamar(tipe);
                    kamar.setFasilitas(fasilitas);
                    kamar.setHarga(harga);
                    kamar.setStatus(statusBaru);
                    
                    if (dao.update(kamar)) {
                        if (statusLama.equalsIgnoreCase("terisi") && statusBaru.equalsIgnoreCase("tersedia")) {
                            KontrakSewaDAO kontrakDAO = new KontrakSewaDAO();
                            kontrakDAO.akhiriKontrakByKamar(kamar.getIdKamar());
                            JOptionPane.showMessageDialog(dialog, "Kamar berhasil diubah menjadi Tersedia!\n(Kontrak penghuni sebelumnya otomatis diakhiri)", "Sukses & Info", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(dialog, "Kamar berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        }
                        dialog.dispose();
                        loadTable();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Gagal mengupdate kamar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (ValidasiException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Kesalahan Validasi", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Terjadi kesalahan sistem: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPanel.add(btnBatal);
        btnPanel.add(btnSimpan);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}