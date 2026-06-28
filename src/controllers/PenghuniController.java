package controllers.Admin;

import utils.FormatUtils;
import views.Admin.DataPenghuniPanel;
import dao.Admin.PenghuniDAO;
import models.Penghuni;
import dao.Admin.KamarDAO;
import dao.Admin.KontrakSewaDAO;
import models.Kamar;
import models.KontrakSewa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PenghuniController {

    private final DataPenghuniPanel view;
    private final PenghuniDAO dao;

    private final List<Integer> listIdPenghuni;

    public PenghuniController(DataPenghuniPanel view) {
        this.view = view;
        this.dao = new PenghuniDAO();
        this.listIdPenghuni = new ArrayList<>();

        loadTable();
        initAction();
        initSearch();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        listIdPenghuni.clear();
 
        List<Object[]> listData = dao.getAllPenghuniTableData();

        for (Object[] rowData : listData) {
            String idString = rowData[0].toString();
            int rawId = Integer.parseInt(idString.split("-")[1]); 
            listIdPenghuni.add(rawId);

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
                rowData[3], 
                tanggal,    
                status      
            };

            model.addRow(row);
        }
    }

    private void initAction() {
        view.getBtnAturKamar().addActionListener(e -> aturKamar());
        view.getBtnTambah().addActionListener(e -> tambah());
        view.getBtnEdit().addActionListener(e -> edit());
        view.getBtnHapus().addActionListener(e -> hapus());
    }

    private void initSearch() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(view.getTableModel());
        view.getTablePenghuni().setRowSorter(sorter);

        view.getTxtSearch().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() {
                String text = view.getTxtSearch().getText();
                if (text.trim().isEmpty() || text.equals("Cari nama atau nomor kamar...")) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private void aturKamar() {
        int row = view.getTablePenghuni().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih penghuni terlebih dahulu di tabel!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String statusKamarSaatIni = view.getTablePenghuni().getValueAt(row, 2).toString();
        if (!statusKamarSaatIni.equals("Belum ada kamar")) {
            JOptionPane.showMessageDialog(view, "Penghuni ini sudah menetap di " + statusKamarSaatIni + ".\nHarap akhiri kontrak sebelumnya terlebih dahulu (Belum dibuat).", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTablePenghuni().convertRowIndexToModel(row);
        int idPenghuni = listIdPenghuni.get(modelRow);
        String namaPenghuni = view.getTablePenghuni().getValueAt(row, 1).toString();

        KamarDAO kamarDAO = new KamarDAO();
        List<Kamar> allKamar = kamarDAO.getAll();
        List<Kamar> kamarTersedia = new ArrayList<>();
        for(Kamar k : allKamar) {
            if(k.getStatus().equalsIgnoreCase("tersedia")) kamarTersedia.add(k);
        }

        if(kamarTersedia.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tidak ada kamar kosong yang tersedia saat ini!", "Informasi Penuh", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(view), "Set Kamar untuk: " + namaPenghuni, true);
        dialog.setSize(400, 320);
        dialog.setLocationRelativeTo(view);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 1, 10, 15));
        formPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        formPanel.setBackground(Color.WHITE);

        JComboBox<String> cbKamar = new JComboBox<>();
        for(Kamar k : kamarTersedia) {
            cbKamar.addItem(k.getIdKamar() + " - Kamar " + k.getNomorKamar() + " (" + k.getTipeKamar() + ")");
        }

        String today = new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
        JTextField txtMulai = new JTextField(today);
        JTextField txtSelesai = new JTextField("dd-MM-yyyy");

        formPanel.add(createInputBox("Pilih Kamar Kosong:", cbKamar));
        formPanel.add(createInputBox("Tanggal Mulai Sewa (dd-MM-yyyy):", txtMulai));
        formPanel.add(createInputBox("Tanggal Selesai Sewa:", txtSelesai));

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnPanel.setBackground(Color.WHITE);
        JButton btnBatal = new JButton("Batal");
        JButton btnSimpan = new JButton("Set Kamar");

        btnBatal.addActionListener(e -> dialog.dispose());
        btnSimpan.addActionListener(e -> {
            try {
                String kamarDipilih = (String) cbKamar.getSelectedItem();
                int idKamar = Integer.parseInt(kamarDipilih.split(" - ")[0]);

                java.util.Date tglMulai = utils.FormatUtils.parseTanggal(txtMulai.getText().trim());
                java.util.Date tglSelesai = utils.FormatUtils.parseTanggal(txtSelesai.getText().trim());

                if (tglMulai == null || tglSelesai == null) {
                    JOptionPane.showMessageDialog(dialog, "Format tanggal salah! Gunakan format: dd-MM-yyyy\nContoh: 15-08-2026", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                KontrakSewaDAO kontrakDAO = new KontrakSewaDAO();
                KontrakSewa kontrakBaru = new KontrakSewa(0, idPenghuni, idKamar, tglMulai, tglSelesai, "aktif");
                kontrakDAO.tambah(kontrakBaru);

                Kamar k = kamarDAO.getById(idKamar);
                k.setStatus("terisi");
                kamarDAO.update(k);

                dialog.dispose();
                loadTable(); 
                JOptionPane.showMessageDialog(view, "Kamar berhasil di set untuk " + namaPenghuni + "!\n(Status kamar telah otomatis berubah menjadi terisi)", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPanel.add(btnBatal);
        btnPanel.add(btnSimpan);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createInputBox(String label, JComponent input) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setOpaque(false);
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(input, BorderLayout.CENTER);
        return p;
    }

    private void tambah() {
        showFormDialog(null);
    }

    private void edit() {
        int row = view.getTablePenghuni().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih data penghuni pada tabel yang akan diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = view.getTablePenghuni().convertRowIndexToModel(row);
        int idPenghuni = listIdPenghuni.get(modelRow);
        
        Penghuni detailData = dao.getDetailById(idPenghuni);
        
        if (detailData != null) {
            showFormDialog(detailData);
        } else {
            JOptionPane.showMessageDialog(view, "Gagal memuat detail penghuni.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapus() {
        int row = view.getTablePenghuni().getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data penghuni yang akan dihapus.");
            return;
        }

        int modelRow = view.getTablePenghuni().convertRowIndexToModel(row);
        int idPenghuni = listIdPenghuni.get(modelRow);
        String nama = view.getTablePenghuni().getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(view, 
                "Hapus akun Penghuni: " + nama + "?\n\nPERINGATAN: Menghapus data ini akan ikut menghapus riwayat\nkontrak sewa, tagihan, dan pembayarannya!", 
                "Konfirmasi Hapus Berbahaya", 
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(idPenghuni)) {
                JOptionPane.showMessageDialog(view, "Data penghuni berhasil dihapus secara permanen!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadTable();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menghapus data penghuni.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showFormDialog(Penghuni data) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(view), true);
        dialog.setTitle(data == null ? "Tambah Data Penghuni" : "Edit Data Penghuni");
        dialog.setSize(420, 480);
        dialog.setLocationRelativeTo(view);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        formPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        formPanel.setBackground(Color.WHITE);

        JTextField txtNama = new JTextField();
        JTextField txtUsername = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtPassword = new JTextField(); 
        JTextField txtNik = new JTextField();
        JTextField txtNoHp = new JTextField();

        if (data != null) {
            txtNama.setText(data.getNama());
            txtUsername.setText(data.getUsername());
            txtEmail.setText(data.getEmail() != null ? data.getEmail() : "");
            txtPassword.setText(data.getPassword());
            txtNik.setText(data.getNik() != null ? data.getNik() : "");
            txtNoHp.setText(data.getNoHp() != null ? data.getNoHp() : "");
        }

        formPanel.add(new JLabel("Nama Lengkap (*):")); formPanel.add(txtNama);
        formPanel.add(new JLabel("Username Login (*):")); formPanel.add(txtUsername);
        formPanel.add(new JLabel("Password Akun (*):")); formPanel.add(txtPassword);
        formPanel.add(new JLabel("Email Aktif:")); formPanel.add(txtEmail);
        formPanel.add(new JLabel("NIK (No. KTP):")); formPanel.add(txtNik);
        formPanel.add(new JLabel("No. Telp / WhatsApp:")); formPanel.add(txtNoHp);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnPanel.setBackground(Color.WHITE);
        JButton btnBatal = new JButton("Batal");
        JButton btnSimpan = new JButton("Simpan Data");

        btnBatal.addActionListener(e -> dialog.dispose());
        
        btnSimpan.addActionListener(e -> {
            String nama = txtNama.getText().trim();
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String password = txtPassword.getText().trim();
            String nik = txtNik.getText().trim();
            String noHp = txtNoHp.getText().trim();

            if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Nama Lengkap, Username, dan Password wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (data == null) {
                    Penghuni penghuniBaru = new Penghuni(0, nama, email, password, username, 0, nik, noHp);
                    dao.insert(penghuniBaru);
                    JOptionPane.showMessageDialog(dialog, "Akun Penghuni baru berhasil dibuat!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Penghuni penghuniEdit = new Penghuni(data.getId(), nama, email, password, username, data.getIdPenghuni(), nik, noHp);
                    dao.update(penghuniEdit);
                    JOptionPane.showMessageDialog(dialog, "Data Penghuni berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
                
                dialog.dispose();
                loadTable(); 
            } catch (SQLException ex) {
                if(ex.getMessage().contains("Duplicate") || ex.getMessage().contains("UNIQUE")) {
                    JOptionPane.showMessageDialog(dialog, "Username atau Email tersebut sudah digunakan orang lain!", "Gagal Menyimpan", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Terjadi kesalahan Database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnPanel.add(btnBatal);
        btnPanel.add(btnSimpan);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}