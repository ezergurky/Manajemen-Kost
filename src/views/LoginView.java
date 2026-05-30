package views;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JCheckBox chkShowPassword;

    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color TEAL_DARK    = new Color(13, 148, 136);
    private static final Color TEAL_DARKER  = new Color(15, 118, 110);
    private static final Color BG_PAGE      = new Color(240, 244, 248);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color TEXT_LIGHT   = new Color(148, 163, 184);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);
    private static final Color INPUT_BG     = new Color(249, 251, 253);

    public LoginView() {
        setTitle("Sistem Manajemen Kost");
        setSize(900, 580);
        setMinimumSize(new Dimension(900, 580));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_PAGE);
        root.add(createLeftPanel(), BorderLayout.WEST);
        root.add(createRightPanel(), BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel createLeftPanel() {
        GradientPanel panel = new GradientPanel(TEAL_PRIMARY, TEAL_DARKER);
        panel.setPreferredSize(new Dimension(380, 580));
        panel.setLayout(new BorderLayout());

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new GridBagLayout());
        content.setPreferredSize(new Dimension(300, 500));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = GridBagConstraints.RELATIVE;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 1.0;

        ImageIcon rawIcon = new ImageIcon("assets/icons/home.png");
        Image scaledImage = rawIcon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon houseIcon = new ImageIcon(scaledImage);

        JLabel icon = new JLabel(houseIcon);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setPreferredSize(new Dimension(120, 80));
        c.insets = new Insets(10, 0, 14, 0);
        content.add(icon, c);

        JLabel title = new JLabel("<html><div style='text-align:center;width:240px'>Sistem Manajemen Kost</div></html>");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        c.insets = new Insets(0, 0, 10, 0);
        content.add(title, c);

        JLabel sub = new JLabel("<html><div style='text-align:center;width:240px'>Kelola kamar, penghuni, tagihan, dan pembayaran dengan mudah</div></html>");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(204, 245, 240));
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        c.insets = new Insets(0, 0, 28, 0);
        content.add(sub, c);

        String[] features = {
            "•  Manajemen data penghuni",
            "•  Dashboard modern",
            "•  Manajemen data kost",
            "•  Laporan pembayaran"
        };
        JPanel featPanel = new JPanel(new GridLayout(features.length, 1, 0, 6));
        featPanel.setOpaque(false);
        featPanel.setPreferredSize(new Dimension(240, features.length * 26));
        for (String feat : features) {
            JLabel lbl = new JLabel(feat);
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            lbl.setForeground(new Color(224, 250, 246));
            featPanel.add(lbl);
        }
        c.insets = new Insets(0, 0, 24, 0);
        content.add(featPanel, c);

        JLabel version = new JLabel("v1.0.0");
        version.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        version.setForeground(new Color(153, 230, 222));
        version.setHorizontalAlignment(SwingConstants.CENTER);
        c.insets = new Insets(0, 0, 0, 0);
        content.add(version, c);

        wrapper.add(content);
        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG_PAGE);

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(380, 470));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder()
        ));

        JPanel cardInner = new JPanel();
        cardInner.setBackground(Color.WHITE);
        cardInner.setBounds(0, 0, 380, 470);
        cardInner.setLayout(null);
        card.add(cardInner);

        int padH = 40;   
        int fw = 380 - padH * 2; 
        int cy = 36;     

        JLabel welcome = new JLabel("Selamat Datang 👋");
        welcome.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        welcome.setForeground(TEXT_DARK);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBounds(0, cy, 380, 30);
        cardInner.add(welcome);
        cy += 36;

        JLabel subLbl = new JLabel("Masuk ke akun Anda untuk melanjutkan");
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subLbl.setForeground(TEXT_LIGHT);
        subLbl.setHorizontalAlignment(SwingConstants.CENTER);
        subLbl.setBounds(0, cy, 380, 20);
        cardInner.add(subLbl);
        cy += 40;

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(240, 244, 248));
        sep.setBounds(padH, cy, fw, 1);
        cardInner.add(sep);
        cy += 22;

        JLabel lblEmail = makeLabel("Email");
        lblEmail.setBounds(padH, cy, fw, 18);
        cardInner.add(lblEmail);
        cy += 24;

        txtEmail = new JTextField();
        setupField(txtEmail, "contoh@email.com");
        txtEmail.setBounds(padH, cy, fw, 44);
        cardInner.add(txtEmail);
        cy += 56;

        JLabel lblPwd = makeLabel("Password");
        lblPwd.setBounds(padH, cy, fw, 18);
        cardInner.add(lblPwd);
        cy += 24;

        txtPassword = new JPasswordField();
        setupField(txtPassword, "Masukkan password");
        txtPassword.setBounds(padH, cy, fw, 44);
        cardInner.add(txtPassword);
        cy += 52;

        chkShowPassword = new JCheckBox("Tampilkan password");
        chkShowPassword.setBackground(Color.WHITE);
        chkShowPassword.setForeground(TEXT_MED);
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkShowPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkShowPassword.setBounds(padH - 2, cy, fw, 22);
        chkShowPassword.addActionListener(e -> togglePasswordVisibility());
        cardInner.add(chkShowPassword);
        cy += 34;

        btnLogin = makeLoginButton("Masuk");
        btnLogin.setBounds(padH, cy, fw, 46);
        cardInner.add(btnLogin);
        cy += 58;

        JLabel footer = new JLabel("© 2025 Sistem Manajemen Kost");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(TEXT_LIGHT);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setBounds(0, cy, 380, 18);
        cardInner.add(footer);

        ShadowPanel shadowWrap = new ShadowPanel(16, Color.WHITE);
        shadowWrap.setLayout(new BorderLayout());
        shadowWrap.setPreferredSize(new Dimension(388, 478));
        shadowWrap.add(card, BorderLayout.CENTER);

        outer.add(shadowWrap);
        return outer;
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(TEXT_MED);
        return lbl;
    }

    private void setupField(JTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_LIGHT);
        field.setCaretColor(TEAL_PRIMARY);
        field.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(10, 14, 10, 14)
        ));

        if (field instanceof JPasswordField) {
            JPasswordField pf = (JPasswordField) field;
            pf.setEchoChar((char) 0);
            pf.setText(placeholder);
        } else {
            field.setText(placeholder);
        }

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String val = field instanceof JPasswordField
                        ? String.valueOf(((JPasswordField) field).getPassword())
                        : field.getText();
                if (val.equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_DARK);
                    if (field instanceof JPasswordField && !chkShowPassword.isSelected()) {
                        ((JPasswordField) field).setEchoChar('\u2022');
                    }
                }
                field.setBorder(new CompoundBorder(
                        new LineBorder(TEAL_PRIMARY, 2, true),
                        new EmptyBorder(9, 13, 9, 13)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                String val = field instanceof JPasswordField
                        ? String.valueOf(((JPasswordField) field).getPassword())
                        : field.getText();
                if (val.trim().isEmpty()) {
                    field.setForeground(TEXT_LIGHT);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0);
                        field.setText(placeholder);
                    } else {
                        field.setText(placeholder);
                    }
                }
                field.setBorder(new CompoundBorder(
                        new LineBorder(BORDER_COLOR, 1, true),
                        new EmptyBorder(10, 14, 10, 14)
                ));
            }
        });
    }

    private JButton makeLoginButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover() ? TEAL_DARK : TEAL_PRIMARY;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void togglePasswordVisibility() {
        String val = String.valueOf(txtPassword.getPassword());
        if (!val.equals("Masukkan password")) {
            txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '\u2022');
        }
    }

    public JButton getBtnLogin() { return btnLogin; }

    public String getEmail() {
        String t = txtEmail.getText();
        return t.equals("contoh@email.com") ? "" : t;
    }

    public String getPassword() {
        String p = String.valueOf(txtPassword.getPassword());
        return p.equals("Masukkan password") ? "" : p;
    }

    private static class GradientPanel extends JPanel {
        private final Color c1, c2;
        GradientPanel(Color c1, Color c2) { this.c1 = c1; this.c2 = c2; }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setPaint(new GradientPaint(0, 0, c1, 0, getHeight(), c2));
            g2.fillRect(0, 0, getWidth(), getHeight());
            // Decorative circles
            g2.setColor(new Color(255, 255, 255, 22));
            g2.fillOval(200, -60, 220, 220);
            g2.setColor(new Color(255, 255, 255, 16));
            g2.fillOval(-70, 380, 210, 210);
            // Subtle grid
            g2.setColor(new Color(255, 255, 255, 8));
            g2.setStroke(new BasicStroke(1f));
            for (int x = 0; x < getWidth(); x += 28) g2.drawLine(x, 0, x, getHeight());
            for (int y = 0; y < getHeight(); y += 28) g2.drawLine(0, y, getWidth(), y);
            g2.dispose();
        }
    }

    private static class ShadowPanel extends JPanel {
        private final int radius;
        private final Color bg;
        ShadowPanel(int radius, Color bg) {
            this.radius = radius; this.bg = bg;
            setOpaque(false);
            setBorder(new EmptyBorder(4, 4, 8, 8));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth() - 12, h = getHeight() - 12;
            // Shadow layers
            for (int i = 6; i >= 1; i--) {
                g2.setColor(new Color(0, 0, 0, 5 * i));
                g2.fillRoundRect(i + 2, i + 4, w, h, radius + 2, radius + 2);
            }
            g2.setColor(bg);
            g2.fillRoundRect(4, 4, w, h, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}