package views;

import models.Kamar;
import models.Kost;
import utils.FormatUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class KostPanel extends JPanel {

    private final Kost kost;

    private JTable tableKamar;
    private DefaultTableModel tableModel;

    private JTextField txtId;
    private JTextField txtNomor;
    private JTextField txtHarga;
    private JComboBox<String> cbStatus;

    private static final Color PRIMARY = new Color(20, 184, 166);

    public KostPanel(Kost kost) {
        this.kost = kost;

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

        JLabel title = new JLabel("Data Kamar - " + kost.getNamaKost());
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel(kost.getAlamat());
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(100, 116, 139));

        JPanel textWrap = new JPanel();
        textWrap.setOpaque(false);
        textWrap.setLayout(new BoxLayout(textWrap, BoxLayout.Y_AXIS));
        textWrap.add(title);
        textWrap.add(subtitle);

        header.add(textWrap, BorderLayout.WEST);
        return header;
    }

    private JScrollPane buildTable() {
        String[] kolom = {"ID Kamar", "Nomor", "Harga", "Status"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableKamar = new JTable(tableModel);
        tableKamar.setRowHeight(28);
        tableKamar.getSelectionModel().addListSelectionListener(e -> isiFormDariBaris());

        return new JScrollPane(tableKamar);
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Form Kamar"));
        form.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(10);
        txtNomor = new JTextField(10);
        txtHarga = new JTextField(10);
        cbStatus = new JComboBox<>(new String[]{"Tersedia", "Terisi"});

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("ID Kamar"), c);
        c.gridx = 1; form.add(txtId, c);
        c.gridx = 2; form.add(new JLabel("Nomor Kamar"), c);
        c.gridx = 3; form.add(txtNomor, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Harga"), c);
        c.gridx = 1; form.add(txtHarga, c);
        c.gridx = 2; form.add(new JLabel("Status"), c);
        c.gridx = 3; form.add(cbStatus, c);

        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnHapus = new JButton("Hapus");
        JButton btnBersihkan = new JButton("Bersihkan");

        btnTambah.addActionListener(e -> tambahKamar());
        btnUpdate.addActionListener(e -> updateKamar());
        btnHapus.addActionListener(e -> hapusKamar());
        btnBersihkan.addActionListener(e -> bersihkanForm());

        JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tombolPanel.setOpaque(false);
        tombolPanel.add(btnTambah);
        tombolPanel.add(btnUpdate);
        tombolPanel.add(btnHapus);
        tombolPanel.add(btnBersihkan);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 4;
        form.add(tombolPanel, c);

        return form;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Kamar kamar : kost.getDaftarKamar()) {
            tableModel.addRow(new Object[]{
                    kamar.getIdKamar(),
                    kamar.getNomor(),
                    FormatUtils.formatRupiah(kamar.getHarga()),
                    kamar.getStatus()
            });
        }
    }

    private void isiFormDariBaris() {
        int row = tableKamar.getSelectedRow();
        if (row < 0) {
            return;
        }
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtNomor.setText(tableModel.getValueAt(row, 1).toString());

        Kamar kamar = cariKamarById(Integer.parseInt(txtId.getText()));
        if (kamar != null) {
            txtHarga.setText(String.valueOf(kamar.getHarga()));
            cbStatus.setSelectedItem(kamar.getStatus().equalsIgnoreCase("Terisi") ? "Terisi" : "Tersedia");
        }
    }

    private void tambahKamar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nomor = FormatUtils.rapikanTeks(txtNomor.getText());
            double harga = Double.parseDouble(txtHarga.getText().trim());
            String status = (String) cbStatus.getSelectedItem();

            if (nomor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nomor kamar tidak boleh kosong.");
                return;
            }

            if (cariKamarById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID Kamar sudah digunakan.");
                return;
            }

            kost.tambahKamar(new Kamar(id, nomor, harga, status));
            refreshTable();
            bersihkanForm();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID dan Harga harus berupa angka.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateKamar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data kamar yang ingin diupdate.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Kamar kamar = cariKamarById(id);

            if (kamar == null) {
                JOptionPane.showMessageDialog(this, "Kamar dengan ID " + id + " tidak ditemukan.");
                return;
            }

            kamar.setNomor(FormatUtils.rapikanTeks(txtNomor.getText()));
            kamar.setHarga(Double.parseDouble(txtHarga.getText().trim()));
            kamar.setStatus((String) cbStatus.getSelectedItem());

            refreshTable();
            bersihkanForm();
            JOptionPane.showMessageDialog(this, "Data kamar berhasil diupdate.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void hapusKamar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data kamar yang ingin dihapus.");
            return;
        }

        int id = Integer.parseInt(txtId.getText().trim());
        kost.hapusKamar(id);
        refreshTable();
        bersihkanForm();
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtNomor.setText("");
        txtHarga.setText("");
        cbStatus.setSelectedIndex(0);
        tableKamar.clearSelection();
    }

    private Kamar cariKamarById(int id) {
        for (Kamar kamar : kost.getDaftarKamar()) {
            if (kamar.getIdKamar() == id) {
                return kamar;
            }
        }
        return null;
    }
}
