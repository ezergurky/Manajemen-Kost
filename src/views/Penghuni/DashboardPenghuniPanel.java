package views.Penghuni;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class DashboardPenghuniPanel extends JPanel {

    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color BG_PANEL     = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color TEXT_LIGHT   = new Color(148, 163, 184);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);

    private JTable tableRiwayatSaya;
    private DefaultTableModel tableModel;

    public DashboardPenghuniPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_PANEL);
        initComponents();
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

        JLabel titleLabel = new JLabel("Dashboard Penghuni");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subLabel = new JLabel("Pantau status hunian, rincian tagihan aktif, dan riwayat pembayaran Anda");
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
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel cardsContainer = new JPanel(new GridLayout(1, 3, 20, 0));
        cardsContainer.setOpaque(false);
        cardsContainer.add(createInfoCard("Kamar Saya", "Kamar A01", "Kost Mawar (Bandung)", TEAL_PRIMARY));
        cardsContainer.add(createInfoCard("Tagihan Bulan Ini", "Rp 750.000", "Bulan: Mei 2026", new Color(239, 68, 68)));
        cardsContainer.add(createInfoCard("Status Pembayaran", "LUNAS", "Terverifikasi otomatis", new Color(16, 185, 129)));

        mainContent.add(cardsContainer, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(24, 0, 0, 0);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JPanel cardHeader = new JPanel(new BorderLayout());
        cardHeader.setOpaque(false);
        cardHeader.setBorder(new EmptyBorder(20, 20, 16, 20));
        
        JLabel cardTitle = new JLabel("Riwayat Transaksi Saya");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cardTitle.setForeground(TEXT_DARK);
        cardHeader.add(cardTitle, BorderLayout.WEST);
        tableCard.add(cardHeader, BorderLayout.NORTH);

        tableCard.add(createRecentActivityTable(), BorderLayout.CENTER);

        mainContent.add(tableCard, gbc);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createInfoCard(String title, String value, String footer, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(accentColor);
                g2.fillRoundRect(0, 0, 6, getHeight(), 0, 0);
                g2.dispose();
            }
        };
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(16, 22, 16, 16)
        ));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitle.setForeground(TEXT_MED);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValue.setForeground(TEXT_DARK);

        JLabel lblFooter = new JLabel(footer);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(TEXT_LIGHT);

        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(lblValue);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(lblFooter);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createRecentActivityTable() {
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(0, 20, 20, 20));

        String[] columns = {"ID Pembayaran", "Bulan Tagihan", "Tanggal Bayar", "Metode", "Jumlah Pembayaran"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableRiwayatSaya = new JTable(tableModel);
        tableRiwayatSaya.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableRiwayatSaya.setRowHeight(38);
        tableRiwayatSaya.setGridColor(new Color(241, 245, 249));
        tableRiwayatSaya.setSelectionBackground(new Color(20, 184, 166, 15));
        tableRiwayatSaya.setSelectionForeground(TEXT_DARK);
        tableRiwayatSaya.setShowVerticalLines(false);

        JTableHeader header = tableRiwayatSaya.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT_MED);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        header.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer paddingRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                return this;
            }
        };

        tableRiwayatSaya.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableRiwayatSaya.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableRiwayatSaya.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableRiwayatSaya.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableRiwayatSaya.getColumnModel().getColumn(4).setCellRenderer(paddingRenderer);

        JScrollPane scrollPane = new JScrollPane(tableRiwayatSaya);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        container.add(scrollPane, BorderLayout.CENTER);

        loadDummyData();

        return container;
    }

    private void loadDummyData() {
        tableModel.addRow(new Object[]{"PMB-001", "Mei 2026", "01 Mei 2026", "Transfer Bank", "Rp 750.000"});
        tableModel.addRow(new Object[]{"PMB-003", "April 2026", "10 Apr 2026", "Cash", "Rp 800.000"});
    }

    public JTable getTableRiwayatSaya() { return tableRiwayatSaya; }
    public DefaultTableModel getTableModel() { return tableModel; }
}