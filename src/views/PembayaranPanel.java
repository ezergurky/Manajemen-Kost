package views;

import dao.PembayaranDAO;
import models.Pembayaran;
import utils.FormatUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;


public class PembayaranPanel extends JPanel {

    private final PembayaranDAO pembayaranDAO;

    private JTable tablePembayaran;
    private DefaultTableModel tableModel;

    private JTextField txtId;
    private JTextField txtTanggal;
    private JTextField txtJumlah;
    private JComboBox<String> cbStatus;

    public PembayaranPanel() {
        this.pembayaranDAO = new PembayaranDAO();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(241, 245, 249));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildForm(), BorderLayout.SOUTH);

        refreshTable();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Transaksi Pembayaran");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Riwayat pembayaran dan konfirmasi tagihan penghuni");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(100, 116, 139));

        JPanel textWrap = new JPanel();
        textWrap.setOpaque(false);
        textWrap.setLayout(new BoxLayout(textWrap, BoxLayout.Y_AXIS));
        textWrap.add(title);
        textWrap.add(subtitle);

        header.add(textWrap, BorderLayout.WEST);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> refreshTable());
        header.add(btnRefresh, BorderLayout.EAST);

        return header;
    }

    private JScrollPane buildTable() {
        String[] kolom = {"ID Pembayaran", "Tanggal", "Jumlah", "Status"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePembayaran = new JTable(tableModel);
        tablePembayaran.setRowHeight(28);
        tablePembayaran.getSelectionModel().addListSelectionListener(e -> isiFormDariBaris());

        return new JScrollPane(tablePembayaran);
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Form Pembayaran"));
        form.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(10);
        txtTanggal = new JTextField(10);
        txtJumlah = new JTextField(10);
        cbStatus = new JComboBox<>(new String[]{"Belum Lunas", "Lunas"});

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("ID Pembayaran"), c);
        c.gridx = 1; form.add(txtId, c);
        c.gridx = 2; form.add(new JLabel("Tanggal (dd-MM-yyyy)"), c);
        c.gridx = 3; form.add(txtTanggal, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Jumlah"), c);
        c.gridx = 1; form.add(txtJumlah, c);
        c.gridx = 2; form.add(new JLabel("Status"), c);
        c.gridx = 3; form.add(cbStatus, c);

        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnHapus = new JButton("Hapus");
        JButton btnKonfirmasi = new JButton("Konfirmasi Lunas");
        JButton btnBersihkan = new JButton("Bersihkan");

        btnTambah.addActionListener(e -> tambahPembayaran());
        btnUpdate.addActionListener(e -> updatePembayaran());
        btnHapus.addActionListener(e -> hapusPembayaran());
        btnKonfirmasi.addActionListener(e -> konfirmasiPembayaran());
        btnBersihkan.addActionListener(e -> bersihkanForm());

        JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tombolPanel.setOpaque(false);
        tombolPanel.add(btnTambah);
        tombolPanel.add(btnUpdate);
        tombolPanel.add(btnHapus);
        tombolPanel.add(btnKonfirmasi);
        tombolPanel.add(btnBersihkan);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 4;
        form.add(tombolPanel, c);

        return form;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Pembayaran> daftarPembayaran = pembayaranDAO.tampilkanSemua();

        for (Pembayaran p : daftarPembayaran) {
            tableModel.addRow(new Object[]{
                    p.getIdPembayaran(),
                    FormatUtils.formatTanggal(p.getTanggal()),
                    FormatUtils.formatRupiah(p.getJumlah()),
                    p.getStatus()
            });
        }
    }

    private void isiFormDariBaris() {
        int row = tablePembayaran.getSelectedRow();
        if (row < 0) {
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        Pembayaran p = pembayaranDAO.cariById(id);

        if (p != null) {
            txtId.setText(String.valueOf(p.getIdPembayaran()));
            txtTanggal.setText(FormatUtils.formatTanggal(p.getTanggal()));
            txtJumlah.setText(String.valueOf(p.getJumlah()));
            cbStatus.setSelectedItem(p.getStatus().equalsIgnoreCase("Lunas") ? "Lunas" : "Belum Lunas");
        }
    }

    private void tambahPembayaran() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Date tanggal = FormatUtils.parseTanggal(txtTanggal.getText().trim());
            double jumlah = Double.parseDouble(txtJumlah.getText().trim());
            String status = (String) cbStatus.getSelectedItem();

            if (tanggal == null) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah, gunakan dd-MM-yyyy.");
                return;
            }

            Pembayaran pembayaran = new Pembayaran(id, tanggal, jumlah, status);
            pembayaranDAO.tambah(pembayaran);

            refreshTable();
            bersihkanForm();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID dan Jumlah harus berupa angka.");
        }
    }

    private void updatePembayaran() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data pembayaran yang ingin diupdate.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Date tanggal = FormatUtils.parseTanggal(txtTanggal.getText().trim());
            double jumlah = Double.parseDouble(txtJumlah.getText().trim());
            String status = (String) cbStatus.getSelectedItem();

            if (tanggal == null) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah, gunakan dd-MM-yyyy.");
                return;
            }

            Pembayaran pembayaran = new Pembayaran(id, tanggal, jumlah, status);
            boolean berhasil = pembayaranDAO.update(pembayaran);

            if (berhasil) {
                refreshTable();
                bersihkanForm();
                JOptionPane.showMessageDialog(this, "Data pembayaran berhasil diupdate.");
            } else {
                JOptionPane.showMessageDialog(this, "Data pembayaran tidak ditemukan.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID dan Jumlah harus berupa angka.");
        }
    }

    private void hapusPembayaran() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data pembayaran yang ingin dihapus.");
            return;
        }

        int id = Integer.parseInt(txtId.getText().trim());
        boolean berhasil = pembayaranDAO.hapus(id);

        if (berhasil) {
            refreshTable();
            bersihkanForm();
        } else {
            JOptionPane.showMessageDialog(this, "Data pembayaran tidak ditemukan.");
        }
    }

    /** Memanggil method konfirmasi() pada model Pembayaran, lalu menyimpan perubahan status. */
    private void konfirmasiPembayaran() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data pembayaran yang ingin dikonfirmasi.");
            return;
        }

        int id = Integer.parseInt(txtId.getText().trim());
        Pembayaran pembayaran = pembayaranDAO.cariById(id);

        if (pembayaran == null) {
            JOptionPane.showMessageDialog(this, "Data pembayaran tidak ditemukan.");
            return;
        }

        pembayaran.konfirmasi();
        pembayaranDAO.update(pembayaran);

        refreshTable();
        bersihkanForm();
        JOptionPane.showMessageDialog(this, "Pembayaran dikonfirmasi sebagai Lunas.");
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtTanggal.setText("");
        txtJumlah.setText("");
        cbStatus.setSelectedIndex(0);
        tablePembayaran.clearSelection();
    }
}
