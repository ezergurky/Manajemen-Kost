package controllers.Admin;

import utils.FormatUtils;
import views.Admin.TagihanPanel;
import dao.Admin.TagihanDAO;
import models.Tagihan;

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
    private final List<Object[]> listDetailTagihan;

    public TagihanController(TagihanPanel view) {
        this.view = view;
        this.dao = new TagihanDAO();
        this.listDetailTagihan = new ArrayList<>();

        loadTable();
        initAction();
        initSearch();
    }

    public void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        listDetailTagihan.clear();

        List<Object[]> listData = dao.getAllTableData();
        
        for (Object[] rowData : listData) {
            listDetailTagihan.add(rowData);

            String idInvoice = rowData[0].toString();
            String nama = rowData[1].toString();
            String kamar = rowData[2].toString();
            String periode = rowData[3].toString();
            double total = (Double) rowData[4];
            String status = rowData[5].toString().substring(0,1).toUpperCase() + rowData[5].toString().substring(1).toLowerCase();

            Object[] row = {
                idInvoice,
                nama,
                kamar,
                periode,
                FormatUtils.formatRupiah(total),
                status
            };
            model.addRow(row);
        }
    }

    private void initAction() {
        view.getBtnTambah().addActionListener(e -> showFormDialog(null));
        view.getBtnEdit().addActionListener(e -> edit());
        view.getBtnHapus().addActionListener(e -> hapus());
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
                if (text.trim().isEmpty() || text.equals("Cari invoice, nama penghuni atau kamar...")) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private void edit() {
        int row = view.getTableTagihan().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih data tagihan pada tabel yang akan diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = view.getTableTagihan().convertRowIndexToModel(row);
        Object[] detailData = listDetailTagihan.get(modelRow);
        showFormDialog(detailData);
    }

    private void hapus() {
        int row = view.getTableTagihan().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Silakan pilih data tagihan yang akan dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int modelRow = view.getTableTagihan().convertRowIndexToModel(row);
        int idTagihan = (Integer) listDetailTagihan.get(modelRow)[6];

        int confirm = JOptionPane.showConfirmDialog(view, "Apakah Anda yakin ingin menghapus tagihan ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(idTagihan)) {
                JOptionPane.showMessageDialog(view, "Data tagihan berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadTable();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menghapus data tagihan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showFormDialog(Object[] data) {
        List<Object[]> penghuniList = dao.getPenghuniAktif();
        if (penghuniList.isEmpty() && data == null) {
            JOptionPane.showMessageDialog(view, "Tidak ada penghuni dengan kontrak aktif saat ini.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(view), true);
        dialog.setTitle(data == null ? "Buat Tagihan Baru" : "Edit Tagihan");
        dialog.setSize(450, 500);
        dialog.setLocationRelativeTo(view);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 15));
        formPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        formPanel.setBackground(Color.WHITE);

        JComboBox<String> cbPenghuni = new JComboBox<>();
        for (Object[] p : penghuniList) {
            cbPenghuni.addItem(p[0] + " - " + p[1] + " (Kamar " + p[2] + ")");
        }

        JComboBox<String> cbBulan = new JComboBox<>(new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"});
        JTextField txtTahun = new JTextField(String.valueOf(java.time.Year.now().getValue()));
        JTextField txtJatuhTempo = new JTextField();
        JTextField txtJumlah = new JTextField();
        JTextField txtDenda = new JTextField("0");
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Belum", "Lunas"});

        if (data != null) {
            int idPenghuniData = (Integer) data[7];
            for (int i = 0; i < cbPenghuni.getItemCount(); i++) {
                if (cbPenghuni.getItemAt(i).startsWith(idPenghuniData + " -")) {
                    cbPenghuni.setSelectedIndex(i);
                    break;
                }
            }
            cbPenghuni.setEnabled(false);
            cbBulan.setSelectedItem(data[8].toString());
            txtTahun.setText(data[9].toString());
            txtJumlah.setText(String.format("%.0f", (Double) data[10]));
            txtDenda.setText(String.format("%.0f", (Double) data[11]));
            txtJatuhTempo.setText(FormatUtils.formatTanggal((java.util.Date) data[12]));
            cbStatus.setSelectedItem(data[5].toString().substring(0,1).toUpperCase() + data[5].toString().substring(1).toLowerCase());
        } else {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.add(java.util.Calendar.DATE, 7);
            txtJatuhTempo.setText(new java.text.SimpleDateFormat("dd-MM-yyyy").format(cal.getTime()));
        }

        formPanel.add(new JLabel("Pilih Penghuni:")); formPanel.add(cbPenghuni);
        formPanel.add(new JLabel("Bulan Tagihan:")); formPanel.add(cbBulan);
        formPanel.add(new JLabel("Tahun:")); formPanel.add(txtTahun);
        formPanel.add(new JLabel("Jumlah Tagihan Pokok:")); formPanel.add(txtJumlah);
        formPanel.add(new JLabel("Denda Keterlambatan:")); formPanel.add(txtDenda);
        formPanel.add(new JLabel("Jatuh Tempo (dd-MM-yyyy):")); formPanel.add(txtJatuhTempo);
        formPanel.add(new JLabel("Status Pembayaran:")); formPanel.add(cbStatus);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnPanel.setBackground(Color.WHITE);
        JButton btnBatal = new JButton("Batal");
        JButton btnSimpan = new JButton("Simpan");

        btnBatal.addActionListener(e -> dialog.dispose());
        btnSimpan.addActionListener(e -> {
            try {
                String selectedPenghuni = (String) cbPenghuni.getSelectedItem();
                int idPenghuni = Integer.parseInt(selectedPenghuni.split(" - ")[0]);
                String bulan = cbBulan.getSelectedItem().toString();
                int tahun = Integer.parseInt(txtTahun.getText().trim());
                double jumlah = Double.parseDouble(txtJumlah.getText().trim());
                double denda = Double.parseDouble(txtDenda.getText().trim());
                java.util.Date jatuhTempo = FormatUtils.parseTanggal(txtJatuhTempo.getText().trim());
                String status = cbStatus.getSelectedItem().toString().toLowerCase();

                if (jatuhTempo == null) {
                    JOptionPane.showMessageDialog(dialog, "Format tanggal jatuh tempo salah! Gunakan: dd-MM-yyyy", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (data == null) {
                    Tagihan t;
                    if (denda == 0.0) {
                        t = new Tagihan(0, idPenghuni, bulan, tahun, jatuhTempo, jumlah, status);
                    } else {
                        t = new Tagihan(0, idPenghuni, bulan, tahun, jatuhTempo, jumlah, denda, status);
                    }
                    
                    if(dao.insert(t)){
                        JOptionPane.showMessageDialog(dialog, "Tagihan berhasil dibuat!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        loadTable();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Gagal membuat tagihan.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    int idTagihan = (Integer) data[6];
                    Tagihan t = new Tagihan(idTagihan, idPenghuni, bulan, tahun, jatuhTempo, jumlah, denda, status);
                    if(dao.update(t)){
                        JOptionPane.showMessageDialog(dialog, "Tagihan berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        loadTable();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Gagal mengupdate tagihan.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Pastikan Jumlah, Denda, dan Tahun berupa angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPanel.add(btnBatal);
        btnPanel.add(btnSimpan);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}