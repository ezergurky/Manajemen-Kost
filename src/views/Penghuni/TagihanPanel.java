package views.Penghuni;

import controllers.Penghuni.TagihanController;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TagihanPanel extends JPanel {

    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color TEAL_DARK    = new Color(13, 148, 136);
    private static final Color BG_PANEL     = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color TEXT_LIGHT   = new Color(148, 163, 184);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);
    private static final Color INPUT_BG     = new Color(249, 251, 253);

    private JTextField txtSearch;
    private JButton btnBayar;
    private JTable tableTagihan;
    private DefaultTableModel tableModel;
    private final int idPenghuni;
    private TagihanController controller;

    public TagihanPanel(int idPenghuni) {
        this.idPenghuni = idPenghuni;
        setLayout(new BorderLayout());
        setBackground(BG_PANEL);
        initComponents();
        this.controller = new TagihanController(this, idPenghuni);
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

        JLabel titleLabel = new JLabel("Tagihan Sewa");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subLabel = new JLabel("Lihat daftar tagihan bulanan Anda dan lakukan pembayaran");
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

        txtSearch = new JTextField("Cari invoice atau bulan...");
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
                if (txtSearch.getText().equals("Cari invoice atau bulan...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(TEXT_DARK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setForeground(TEXT_LIGHT);
                    txtSearch.setText("Cari invoice atau bulan...");
                }
            }
        });

        toolbar.add(txtSearch, BorderLayout.WEST);

        JPanel btnActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnActionPanel.setOpaque(false);

        btnBayar = new JButton("Bayar Tagihan") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? TEAL_DARK : TEAL_PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnBayar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBayar.setForeground(Color.WHITE);
        btnBayar.setPreferredSize(new Dimension(140, 40));
        btnBayar.setOpaque(false);
        btnBayar.setContentAreaFilled(false);
        btnBayar.setBorderPainted(false);
        btnBayar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnActionPanel.add(btnBayar);
        toolbar.add(btnActionPanel, BorderLayout.EAST);

        return toolbar;
    }

    private JPanel createTablePanel() {
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));

        String[] columns = {"No. Invoice", "Bulan", "Jatuh Tempo", "Tagihan Pokok", "Denda", "Total", "Status"};
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

        for (int i = 0; i < tableTagihan.getColumnCount(); i++) {
            if (i != 1 && i != 3 && i != 4 && i != 5) {
                tableTagihan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        JScrollPane scrollPane = new JScrollPane(tableTagihan);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableContainer.add(scrollPane, BorderLayout.CENTER);
        return tableContainer;
    }

    public void refreshData() {
        if (this.controller != null) {
            this.controller.loadTable();
        }
    }

    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnBayar() { return btnBayar; }
    public JTable getTableTagihan() { return tableTagihan; }
    public DefaultTableModel getTableModel() { return tableModel; }
}