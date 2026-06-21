package views.Admin;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controllers.Admin.DashboardAdminController;

import java.awt.*;

public class DashboardAdminPanel extends JPanel {
    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color BG_PANEL     = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color TEXT_LIGHT   = new Color(148, 163, 184);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);

    private JTable tableAktivitas;
    private DefaultTableModel tableModel;

    private JLabel lblTotalKamarValue, lblTotalKamarFooter;
    private JLabel lblTotalPenghuniValue, lblTotalPenghuniFooter;
    private JLabel lblPendapatanValue, lblPendapatanFooter;

    public DashboardAdminPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_PANEL);
        initComponents();

        new DashboardAdminController(this);
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

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subLabel = new JLabel("Selamat datang kembali, Admin! Berikut ringkasan operasional kost hari ini.");
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

        lblTotalKamarValue = new JLabel("- / -");
        lblTotalKamarFooter = new JLabel("Memuat data...");
        cardsContainer.add(createInfoCard("Total Kamar", lblTotalKamarValue, lblTotalKamarFooter, TEAL_PRIMARY));

        lblTotalPenghuniValue = new JLabel("- Orang");
        lblTotalPenghuniFooter = new JLabel("Memuat data...");
        cardsContainer.add(createInfoCard("Total Penghuni Aktif", lblTotalPenghuniValue, lblTotalPenghuniFooter, new Color(59, 130, 246)));

        lblPendapatanValue = new JLabel("Rp 0");
        lblPendapatanFooter = new JLabel("Bulan ini");
        cardsContainer.add(createInfoCard("Pendapatan Bulan Ini", lblPendapatanValue, lblPendapatanFooter, new Color(16, 185, 129)));

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
        
        JLabel cardTitle = new JLabel("Aktivitas & Transaksi Terbaru");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cardTitle.setForeground(TEXT_DARK);
        cardHeader.add(cardTitle, BorderLayout.WEST);
        tableCard.add(cardHeader, BorderLayout.NORTH);

        tableCard.add(createRecentActivityTable(), BorderLayout.CENTER);

        mainContent.add(tableCard, gbc);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createInfoCard(String title, JLabel lblValue, JLabel lblFooter, Color accentColor) {
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

        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValue.setForeground(TEXT_DARK);

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

        String[] columns = {"Tanggal", "Tipe Aktivitas", "Keterangan", "Nominal"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableAktivitas = new JTable(tableModel);
        tableAktivitas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableAktivitas.setRowHeight(38);
        tableAktivitas.setGridColor(new Color(241, 245, 249));
        tableAktivitas.setSelectionBackground(new Color(20, 184, 166, 15));
        tableAktivitas.setSelectionForeground(TEXT_DARK);
        tableAktivitas.setShowVerticalLines(false);

        JTableHeader header = tableAktivitas.getTableHeader();
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

        tableAktivitas.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableAktivitas.getColumnModel().getColumn(1).setCellRenderer(paddingRenderer);
        tableAktivitas.getColumnModel().getColumn(2).setCellRenderer(paddingRenderer);
        tableAktivitas.getColumnModel().getColumn(3).setCellRenderer(paddingRenderer);

        JScrollPane scrollPane = new JScrollPane(tableAktivitas);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        container.add(scrollPane, BorderLayout.CENTER);

        return container;
    }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JLabel getLblTotalKamarValue() { return lblTotalKamarValue; }
    public JLabel getLblTotalKamarFooter() { return lblTotalKamarFooter; }
    public JLabel getLblTotalPenghuniValue() { return lblTotalPenghuniValue; }
    public JLabel getLblTotalPenghuniFooter() { return lblTotalPenghuniFooter; }
    public JLabel getLblPendapatanValue() { return lblPendapatanValue; }
    public JLabel getLblPendapatanFooter() { return lblPendapatanFooter; }
}