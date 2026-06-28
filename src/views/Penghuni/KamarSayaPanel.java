package views.Penghuni;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import controllers.Penghuni.KamarSayaController;

public class KamarSayaPanel extends JPanel {
    private static final Color BG_PANEL     = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);

    private JLabel lblNomorKamar, lblHarga, lblNamaKost, lblAlamatKost;
    private JLabel lblTanggalMulai, lblTanggalSelesai, lblStatusKontrak;
    private JTextArea txtFasilitas;

    private int idPenghuni;
    private KamarSayaController controller;

    public KamarSayaPanel(int idPenghuni) {
        this.idPenghuni = idPenghuni;

        setLayout(new BorderLayout());
        setBackground(BG_PANEL);
        initComponents();

        this.controller = new KamarSayaController(this, this.idPenghuni);
    }

    private void initComponents() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 72));
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));
        topBar.setLayout(new GridBagLayout());

        JPanel titleContainer = new JPanel();
        titleContainer.setOpaque(false);
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Kamar Saya");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subLabel = new JLabel("Informasi detail mengenai spesifikasi kamar dan masa aktif kontrak sewa Anda");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLabel.setForeground(TEXT_MED);

        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(2));
        titleContainer.add(subLabel);

        GridBagConstraints gbcTop = new GridBagConstraints();
        gbcTop.gridx = 0;
        gbcTop.gridy = 0;
        gbcTop.weightx = 1.0;
        gbcTop.anchor = GridBagConstraints.WEST;
        gbcTop.insets = new Insets(0, 32, 0, 0);
        topBar.add(titleContainer, gbcTop);

        add(topBar, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(24, 32, 24, 32));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel detailKamarCard = new JPanel(new GridBagLayout());
        detailKamarCard.setBackground(Color.WHITE);
        detailKamarCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(24, 24, 24, 24)
        ));

        GridBagConstraints cKamar = new GridBagConstraints();
        cKamar.gridx = 0;
        cKamar.gridy = 0;
        cKamar.anchor = GridBagConstraints.WEST;
        cKamar.fill = GridBagConstraints.HORIZONTAL;
        cKamar.weightx = 1.0;

        JLabel titleKamar = new JLabel("Spesifikasi & Properti Properti");
        titleKamar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleKamar.setForeground(TEXT_DARK);
        detailKamarCard.add(titleKamar, cKamar);

        JSeparator sep1 = new JSeparator();
        sep1.setForeground(BORDER_COLOR);
        cKamar.gridy = 1;
        cKamar.insets = new Insets(12, 0, 16, 0);
        detailKamarCard.add(sep1, cKamar);

        lblNomorKamar = createDetailRow(detailKamarCard, "Nomor Kamar", "-", 2);
        lblHarga = createDetailRow(detailKamarCard, "Harga Sewa / Bulan", "-", 3);
        lblNamaKost = createDetailRow(detailKamarCard, "Nama Properti", "-", 4);
        lblAlamatKost = createDetailRow(detailKamarCard, "Alamat Kost", "-", 5);

        JLabel lblFasilitasTitle = new JLabel("Fasilitas Kamar:");
        lblFasilitasTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFasilitasTitle.setForeground(TEXT_MED);
        cKamar.gridy = 6;
        cKamar.insets = new Insets(12, 0, 6, 0);
        detailKamarCard.add(lblFasilitasTitle, cKamar);

        txtFasilitas = new JTextArea("Memuat data...");
        txtFasilitas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtFasilitas.setForeground(TEXT_DARK);
        txtFasilitas.setEditable(false);
        txtFasilitas.setOpaque(false);
        txtFasilitas.setLineWrap(true);
        txtFasilitas.setWrapStyleWord(true);
        cKamar.gridy = 7; cKamar.weighty = 1.0; cKamar.fill = GridBagConstraints.BOTH; cKamar.insets = new Insets(0, 4, 0, 0);
        detailKamarCard.add(txtFasilitas, cKamar);

        mainContent.add(detailKamarCard, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 24, 0, 0);

        JPanel kontrakCard = new JPanel(new GridBagLayout());
        kontrakCard.setBackground(Color.WHITE);
        kontrakCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(24, 24, 24, 24)
        ));

        GridBagConstraints cKontrak = new GridBagConstraints();
        cKontrak.gridx = 0;
        cKontrak.gridy = 0;
        cKontrak.anchor = GridBagConstraints.WEST;
        cKontrak.fill = GridBagConstraints.HORIZONTAL;
        cKontrak.weightx = 1.0;

        JLabel titleKontrak = new JLabel("Informasi Kontrak Sewa");
        titleKontrak.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleKontrak.setForeground(TEXT_DARK);
        kontrakCard.add(titleKontrak, cKontrak);

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(BORDER_COLOR);
        cKontrak.gridy = 1;
        cKontrak.insets = new Insets(12, 0, 16, 0);
        kontrakCard.add(sep2, cKontrak);

        lblTanggalMulai = createDetailRow(kontrakCard, "Tanggal Mulai Sewa", "-", 2);
        lblTanggalSelesai = createDetailRow(kontrakCard, "Tanggal Selesai Sewa", "-", 3);
        lblStatusKontrak = createDetailRow(kontrakCard, "Status Kontrak", "-", 4);

        cKontrak.gridy = 5;
        cKontrak.weighty = 1.0;
        cKontrak.fill = GridBagConstraints.BOTH;
        kontrakCard.add(new JPanel() {{ setOpaque(false); }}, cKontrak);

        mainContent.add(kontrakCard, gbc);

        add(mainContent, BorderLayout.CENTER);
    }

    private JLabel createDetailRow(JPanel container, String labelText, String valueText, int gridy) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setOpaque(false);
        rowPanel.setBorder(new EmptyBorder(0, 0, 14, 0));

        JLabel lblLabel = new JLabel(labelText);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLabel.setForeground(TEXT_MED);

        JLabel lblValue = new JLabel(valueText);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblValue.setForeground(TEXT_DARK);

        rowPanel.add(lblLabel, BorderLayout.NORTH);
        rowPanel.add(lblValue, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(rowPanel, gbc);

        return lblValue;
    }

    public void refreshData() {
        if (this.controller != null) {
            this.controller.loadData();
        }
    }

    public JLabel getLblNomorKamar() { return lblNomorKamar; }
    public JLabel getLblHarga() { return lblHarga; }
    public JLabel getLblNamaKost() { return lblNamaKost; }
    public JLabel getLblAlamatKost() { return lblAlamatKost; }
    public JLabel getLblTanggalMulai() { return lblTanggalMulai; }
    public JLabel getLblTanggalSelesai() { return lblTanggalSelesai; }
    public JLabel getLblStatusKontrak() { return lblStatusKontrak; }
    public JTextArea getTxtFasilitas() { return txtFasilitas; }
}