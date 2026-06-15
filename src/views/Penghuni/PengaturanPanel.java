package views.Penghuni;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PengaturanPanel extends JPanel {

    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color TEAL_DARK    = new Color(13, 148, 136);
    private static final Color BG_PANEL     = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);
    private static final Color INPUT_BG     = new Color(249, 251, 253);

    private JPasswordField txtPasswordLama, txtPasswordBaru, txtKonfirmasiPassword;
    private JButton btnSimpanPassword;

    public PengaturanPanel() {
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

        JLabel titleLabel = new JLabel("Pengaturan");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subLabel = new JLabel("Konfigurasi keamanan akun, perbarui kata sandi, dan preferensi privasi Anda");
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
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel settingsCard = new JPanel(new GridBagLayout());
        settingsCard.setBackground(Color.WHITE);
        settingsCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(28, 28, 28, 28)
        ));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        JLabel titleSandi = new JLabel("Perbarui Kata Sandi");
        titleSandi.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleSandi.setForeground(TEXT_DARK);
        settingsCard.add(titleSandi, c);

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        c.gridy = 1;
        c.insets = new Insets(8, 0, 20, 0);
        settingsCard.add(sep, c);

        txtPasswordLama = createPasswordFieldRow(settingsCard, "Kata Sandi Sekarang", 2);
        txtPasswordBaru = createPasswordFieldRow(settingsCard, "Kata Sandi Baru", 4);
        txtKonfirmasiPassword = createPasswordFieldRow(settingsCard, "Konfirmasi Kata Sandi Baru", 6);

        btnSimpanPassword = new JButton("Simpan Perubahan") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color currentBg = getModel().isRollover() ? TEAL_DARK : TEAL_PRIMARY;
                g2.setColor(currentBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnSimpanPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSimpanPassword.setForeground(Color.WHITE);
        btnSimpanPassword.setPreferredSize(new Dimension(160, 40));
        btnSimpanPassword.setOpaque(false);
        btnSimpanPassword.setContentAreaFilled(false);
        btnSimpanPassword.setBorderPainted(false);
        btnSimpanPassword.setFocusPainted(false);
        btnSimpanPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));

        c.gridy = 8;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(12, 0, 0, 0);
        settingsCard.add(btnSimpanPassword, c);

        c.gridy = 9;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        settingsCard.add(new JPanel() {{ setOpaque(false); }}, c);

        mainContent.add(settingsCard, gbc);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPasswordField createPasswordFieldRow(JPanel container, String labelText, int gridy) {
        JLabel lblField = new JLabel(labelText);
        lblField.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblField.setForeground(TEXT_MED);
        
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = gridy;
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(0, 0, 6, 0);
        container.add(lblField, gbcLabel);

        JPasswordField pf = new JPasswordField();
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pf.setBackground(INPUT_BG);
        pf.setForeground(TEXT_DARK);
        pf.setCaretColor(TEAL_PRIMARY);
        pf.setPreferredSize(new Dimension(0, 40));
        pf.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(0, 12, 0, 12)
        ));

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 0;
        gbcField.gridy = gridy + 1;
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.weightx = 1.0;
        gbcField.insets = new Insets(0, 0, 16, 0);
        container.add(pf, gbcField);

        return pf;
    }

    public JPasswordField getTxtPasswordLama() { return txtPasswordLama; }
    public JPasswordField getTxtPasswordBaru() { return txtPasswordBaru; }
    public JPasswordField getTxtKonfirmasiPassword() { return txtKonfirmasiPassword; }
    public JButton getBtnSimpanPassword() { return btnSimpanPassword; }
}