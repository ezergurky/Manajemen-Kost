package views.Penghuni;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ProfilePanel extends JPanel {

    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color BG_PANEL     = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);

    private JLabel lblNamaLengkap, lblNik, lblTelepon, lblEmail, lblUsername, lblRole;

    public ProfilePanel() {
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

        JLabel titleLabel = new JLabel("Profil Saya");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT_DARK);

        JLabel subLabel = new JLabel("Kelola informasi data diri Anda dan verifikasi akun penghuni kost");
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

        JPanel profileCard = new JPanel(new GridBagLayout());
        profileCard.setBackground(Color.WHITE);
        profileCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(32, 32, 32, 32)
        ));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 24, 0);

        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TEAL_PRIMARY);
                g2.fillOval(0, 0, 80, 80);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
                FontMetrics fm = g2.getFontMetrics();
                String init = "P";
                g2.drawString(init,
                    (80 - fm.stringWidth(init)) / 2,
                    (80 + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        avatar.setOpaque(false);
        avatar.setPreferredSize(new Dimension(80, 80));
        profileCard.add(avatar, c);

        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 0, 0);

        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        c.gridy = 1;
        c.insets = new Insets(8, 0, 20, 0);
        profileCard.add(sep, c);

        lblNamaLengkap = createProfileRow(profileCard, "Nama Lengkap", "Penghuni", 2);
        lblNik = createProfileRow(profileCard, "NIK (Nomor Induk Kependudukan)", "3201234567890001", 3);
        lblTelepon = createProfileRow(profileCard, "No. Telepon / WhatsApp", "081234567890", 4);
        lblEmail = createProfileRow(profileCard, "Alamat Email", "penghuni@kost.id", 5);
        lblUsername = createProfileRow(profileCard, "Username Akun", "penghuni", 6);
        lblRole = createProfileRow(profileCard, "Hak Akses / Role Sistem", "Penghuni Kost", 7);

        c.gridy = 8;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        profileCard.add(new JPanel() {{ setOpaque(false); }}, c);

        mainContent.add(profileCard, gbc);
        add(mainContent, BorderLayout.CENTER);
    }

    private JLabel createProfileRow(JPanel container, String labelText, String valueText, int gridy) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setOpaque(false);
        rowPanel.setBorder(new EmptyBorder(0, 0, 16, 0));

        JLabel lblLabel = new JLabel(labelText);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLabel.setForeground(TEXT_MED);

        JLabel lblValue = new JLabel(valueText);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 14));
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

    public JLabel getLblNamaLengkap() { return lblNamaLengkap; }
    public JLabel getLblNik() { return lblNik; }
    public JLabel getLblTelepon() { return lblTelepon; }
    public JLabel getLblEmail() { return lblEmail; }
    public JLabel getLblUsername() { return lblUsername; }
    public JLabel getLblRole() { return lblRole; }
}