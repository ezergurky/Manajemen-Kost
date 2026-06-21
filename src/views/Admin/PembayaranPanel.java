package views.Admin;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controllers.Admin.PembayaranController;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PembayaranPanel extends JPanel {

    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color TEAL_DARK    = new Color(13, 148, 136);
    private static final Color BG_PANEL     = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color TEXT_LIGHT   = new Color(148, 163, 184);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);
    private static final Color INPUT_BG     = new Color(249, 251, 253);

    private JTextField txtSearch;
    private JButton btnVerifikasi, btnCetakKwitansi, btnHapus;
    private JTable tablePembayaran;
    private DefaultTableModel tableModel;

    public PembayaranPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_PANEL);
        initComponents();
        
        new PembayaranController(this);
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

        JLabel titleLabel = new JLabel("Transaksi Pembayaran");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subLabel = new JLabel("Pantau masuknya dana sewa, metode pembayaran, serta pencetakan bukti kwitansi penghuni");
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

        txtSearch = new JTextField("Cari ID pembayaran atau nama...");
        txtSearch.setPreferredSize(new Dimension(280, 40));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setBackground(INPUT_BG);
        txtSearch.setForeground(TEXT_LIGHT);
        txtSearch.setCaretColor(TEAL_PRIMARY);
        txtSearch.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(0, 12, 0, 12)
        ));

        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Cari ID pembayaran atau nama...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(TEXT_DARK);
                }
                txtSearch.setBorder(new CompoundBorder(
                        new LineBorder(TEAL_PRIMARY, 2, true),
                        new EmptyBorder(0, 11, 0, 11)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setForeground(TEXT_LIGHT);
                    txtSearch.setText("Cari ID pembayaran atau nama...");
                }
                txtSearch.setBorder(new CompoundBorder(
                        new LineBorder(BORDER_COLOR, 1, true),
                        new EmptyBorder(0, 12, 0, 12)
                ));
            }
        });

        toolbar.add(txtSearch, BorderLayout.WEST);

        JPanel btnActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnActionPanel.setOpaque(false);

        btnVerifikasi = createStyledButton("Verifikasi", TEAL_PRIMARY, TEAL_DARK, true);
        btnCetakKwitansi = createStyledButton("Cetak Kwitansi", Color.WHITE, new Color(240, 244, 248), false);
        btnHapus = createStyledButton("Hapus", new Color(239, 68, 68), new Color(220, 38, 38), true);

        btnActionPanel.add(btnVerifikasi);
        btnActionPanel.add(btnCetakKwitansi);
        btnActionPanel.add(btnHapus);

        toolbar.add(btnActionPanel, BorderLayout.EAST);
        return toolbar;
    }

    private JPanel createTablePanel() {
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));

        String[] columns = {"ID Bayar", "No. Invoice", "Nama Penghuni", "Tanggal Bayar", "Metode", "Jumlah Bayar"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePembayaran = new JTable(tableModel);
        tablePembayaran.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablePembayaran.setRowHeight(40);
        tablePembayaran.setGridColor(new Color(241, 245, 249));
        tablePembayaran.setSelectionBackground(new Color(20, 184, 166, 20));
        tablePembayaran.setSelectionForeground(TEXT_DARK);
        tablePembayaran.setShowVerticalLines(false);

        JTableHeader header = tablePembayaran.getTableHeader();
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

        tablePembayaran.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tablePembayaran.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tablePembayaran.getColumnModel().getColumn(2).setCellRenderer(paddingRenderer);
        tablePembayaran.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tablePembayaran.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tablePembayaran.getColumnModel().getColumn(5).setCellRenderer(paddingRenderer);

        JScrollPane scrollPane = new JScrollPane(tablePembayaran);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableContainer.add(scrollPane, BorderLayout.CENTER);

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

    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnVerifikasi() { return btnVerifikasi; }
    public JButton getBtnCetakKwitansi() { return btnCetakKwitansi; }
    public JButton getBtnHapus() { return btnHapus; }
    public JTable getTablePembayaran() { return tablePembayaran; }
    public DefaultTableModel getTableModel() { return tableModel; }
}