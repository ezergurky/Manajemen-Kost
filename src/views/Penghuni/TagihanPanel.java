package views.Penghuni;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TagihanPanel extends JPanel {

    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color TEAL_DARK    = new Color(13, 148, 136);
    private static final Color BG_PANEL     = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);

    private JButton btnBayar, btnCetakInvoice;
    private JTable tableTagihan;
    private DefaultTableModel tableModel;

    public TagihanPanel() {
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

        JLabel titleLabel = new JLabel("Tagihan Saya");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subLabel = new JLabel("Lihat daftar seluruh kewajiban pembayaran sewa kamar dan biaya tambahan Anda");
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

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(24, 32, 24, 32));

        JPanel contentCard = new JPanel(new BorderLayout());
        contentCard.setBackground(Color.WHITE);
        contentCard.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        contentCard.add(createToolbarPanel(), BorderLayout.NORTH);
        contentCard.add(createTablePanel(), BorderLayout.CENTER);

        mainContent.add(contentCard, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createToolbarPanel() {
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(new EmptyBorder(20, 20, 16, 20));

        JLabel lblSectionTitle = new JLabel("Daftar Invoice");
        lblSectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSectionTitle.setForeground(TEXT_DARK);
        toolbar.add(lblSectionTitle, BorderLayout.WEST);

        JPanel btnActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnActionPanel.setOpaque(false);

        btnBayar = createStyledButton("Bayar Sekarang", TEAL_PRIMARY, TEAL_DARK, true);
        btnCetakInvoice = createStyledButton("Cetak Invoice", Color.WHITE, new Color(240, 244, 248), false);

        btnActionPanel.add(btnBayar);
        btnActionPanel.add(btnCetakInvoice);

        toolbar.add(btnActionPanel, BorderLayout.EAST);
        return toolbar;
    }

    private JPanel createTablePanel() {
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));

        String[] columns = {"No. Invoice", "Bulan / Tahun", "Jumlah Tagihan", "Denda", "Batas Waktu", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableTagihan = new JTable(tableModel);
        tableTagihan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableTagihan.setRowHeight(40);
        tableTagihan.setGridColor(new Color(241, 245, 249));
        tableTagihan.setSelectionBackground(new Color(20, 184, 166, 20));
        tableTagihan.setSelectionForeground(TEXT_DARK);
        tableTagihan.setShowVerticalLines(false);

        JTableHeader header = tableTagihan.getTableHeader();
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

        tableTagihan.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableTagihan.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableTagihan.getColumnModel().getColumn(2).setCellRenderer(paddingRenderer);
        tableTagihan.getColumnModel().getColumn(3).setCellRenderer(paddingRenderer);
        tableTagihan.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableTagihan.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(tableTagihan);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableContainer.add(scrollPane, BorderLayout.CENTER);

        loadDummyData();

        return tableContainer;
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

    private void loadDummyData() {
        tableModel.addRow(new Object[]{"INV-2026-001", "Mei / 2026", "Rp 750.000", "Rp 0", "01 Jun 2026", "Lunas"});
        tableModel.addRow(new Object[]{"INV-2026-002", "Juni / 2026", "Rp 750.000", "Rp 0", "01 Jul 2026", "Belum Bayar"});
    }

    public JButton getBtnBayar() { return btnBayar; }
    public JButton getBtnCetakInvoice() { return btnCetakInvoice; }
    public JTable getTableTagihan() { return tableTagihan; }
    public DefaultTableModel getTableModel() { return tableModel; }
}