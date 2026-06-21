package views.Admin;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controllers.Admin.LaporanController;

import java.awt.*;

public class LaporanPanel extends JPanel {

    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color TEAL_DARK    = new Color(13, 148, 136);
    private static final Color BG_PANEL     = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);

    private JComboBox<String> cbBulan, cbTahun;
    private JButton btnFilter, btnExportPDF;
    private JTable tableLaporan;
    private DefaultTableModel tableModel;
    private JLabel lblTotalPendapatan, lblTotalTunggakan;

    public LaporanPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_PANEL);
        initComponents();
        
        new LaporanController(this);
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

        JLabel titleLabel = new JLabel("Laporan Keuangan");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subLabel = new JLabel("Analisis akumulasi pendapatan bulanan, rekapitulasi tunggakan, dan cetak pembukuan kost");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLabel.setForeground(TEXT_MED);

        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(2));
        titleContainer.add(subLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 32, 0, 0);
        topBar.add(titleContainer, gbc);

        add(topBar, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(24, 32, 24, 32));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;

        JPanel summaryContainer = new JPanel(new GridLayout(1, 2, 24, 0));
        summaryContainer.setOpaque(false);
        
        lblTotalPendapatan = new JLabel("Rp 0");
        lblTotalTunggakan = new JLabel("Rp 0");

        JPanel cardPendapatan = createSummaryCard("Total Pendapatan Terinput", lblTotalPendapatan, new Color(16, 185, 129));
        JPanel cardTunggakan = createSummaryCard("Total Tunggakan Belum Bayar", lblTotalTunggakan, new Color(239, 68, 68));

        summaryContainer.add(cardPendapatan);
        summaryContainer.add(cardTunggakan);
        mainContent.add(summaryContainer, c);

        c.gridy = 1;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(24, 0, 0, 0);

        JPanel contentCard = new JPanel(new BorderLayout());
        contentCard.setBackground(Color.WHITE);
        contentCard.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        contentCard.add(createToolbarPanel(), BorderLayout.NORTH);
        contentCard.add(createTablePanel(), BorderLayout.CENTER);

        mainContent.add(contentCard, c);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createSummaryCard(String title, JLabel lblValue, Color accentColor) {
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

        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(lblValue);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createToolbarPanel() {
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(new EmptyBorder(20, 20, 16, 20));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setOpaque(false);

        String[] bulan = {"Semua Bulan", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        cbBulan = new JComboBox<>(bulan);
        setupComboBox(cbBulan);

        String[] tahun = {"Semua Tahun", "2024", "2025", "2026", "2027"};
        cbTahun = new JComboBox<>(tahun);
        setupComboBox(cbTahun);

        btnFilter = createStyledButton("Filter", Color.WHITE, new Color(240, 244, 248), false);
        btnFilter.setPreferredSize(new Dimension(80, 40));

        filterPanel.add(cbBulan);
        filterPanel.add(cbTahun);
        filterPanel.add(btnFilter);

        toolbar.add(filterPanel, BorderLayout.WEST);

        btnExportPDF = createStyledButton("Ekspor PDF", TEAL_PRIMARY, TEAL_DARK, true);
        toolbar.add(btnExportPDF, BorderLayout.EAST);

        return toolbar;
    }

    private JPanel createTablePanel() {
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));

        String[] columns = {"Keterangan Transaksi", "Bulan/Tahun", "Metode", "Pemasukan", "Keterangan Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableLaporan = new JTable(tableModel);
        tableLaporan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableLaporan.setRowHeight(40);
        tableLaporan.setGridColor(new Color(241, 245, 249));
        tableLaporan.setSelectionBackground(new Color(20, 184, 166, 20));
        tableLaporan.setSelectionForeground(TEXT_DARK);
        tableLaporan.setShowVerticalLines(false);

        JTableHeader header = tableLaporan.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT_MED);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
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

        tableLaporan.getColumnModel().getColumn(0).setCellRenderer(paddingRenderer);
        tableLaporan.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableLaporan.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableLaporan.getColumnModel().getColumn(3).setCellRenderer(paddingRenderer);
        tableLaporan.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(tableLaporan);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableContainer.add(scrollPane, BorderLayout.CENTER);

        return tableContainer;
    }

    private void setupComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(TEXT_DARK);
        comboBox.setPreferredSize(new Dimension(140, 40));
        comboBox.setBorder(new LineBorder(BORDER_COLOR, 1, true));
    }

    private JButton createStyledButton(String text, Color bg, Color hoverBg, boolean isFilled) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color currentBg = getModel().isRollover() ? hoverBg : bg;
                g2.setColor(currentBg);
                
                if (isFilled) {
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.setColor(BORDER_COLOR);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(isFilled ? Color.WHITE : TEXT_MED);
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 12, 40));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    public JComboBox<String> getCbBulan() { return cbBulan; }
    public JComboBox<String> getCbTahun() { return cbTahun; }
    public JButton getBtnFilter() { return btnFilter; }
    public JButton getBtnExportPDF() { return btnExportPDF; }
    public JTable getTableLaporan() { return tableLaporan; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JLabel getLblTotalPendapatan() { return lblTotalPendapatan; }
    public JLabel getLblTotalTunggakan() { return lblTotalTunggakan; }
}