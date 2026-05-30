package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KostDashboard extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // --- Definisi Warna Tema Modern ---
    private final Color SIDEBAR_BG = new Color(30, 41, 59);    // Slate 800
    private final Color SIDEBAR_HOVER = new Color(51, 65, 85); // Slate 700
    private final Color MAIN_BG = new Color(241, 245, 249);    // Slate 100
    private final Color TEXT_WHITE = new Color(248, 250, 252);
    private final Color TEXT_DARK = new Color(15, 23, 42);

    public KostDashboard() {
        // Pengaturan Jendela Utama
        setTitle("Sistem Manajemen Kost");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Menengahkah aplikasi di layar
        setLayout(new BorderLayout());

        // ==========================================
        // 1. MEMBUAT SIDEBAR (Kiri)
        // ==========================================
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        // Judul Sidebar
        JLabel titleLabel = new JLabel("Ezer Gurki");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        sidebar.add(titleLabel);

        // ==========================================
        // 2. MEMBUAT MAIN PANEL (Kanan / Tengah)
        // ==========================================
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(MAIN_BG);

        // Mendaftarkan halaman-halaman ke dalam CardLayout
        mainPanel.add(createPage("Dashboard", "Ringkasan data, kamar kosong, dan pendapatan bulan ini."), "Dashboard");
        mainPanel.add(createPage("Data Kamar", "Manajemen tipe kamar, fasilitas, dan harga."), "Kamar");
        mainPanel.add(createPage("Data Penghuni", "Informasi identitas dan kontak anak kost."), "Penghuni");
        mainPanel.add(createPage("Transaksi", "Riwayat pembayaran dan cetak struk tagihan."), "Transaksi");
        mainPanel.add(createPage("Pengaturan", "Konfigurasi akun dan sistem admin."), "Pengaturan");

        // ==========================================
        // 3. MENU SIDEBAR (Tombol Navigasi)
        // ==========================================
        String[] menuItems = {"Dashboard", "Kamar", "Penghuni", "Transaksi", "Pengaturan"};
        
        for (String menu : menuItems) {
            JButton btnMenu = createMenuButton(menu);
            // Event listener untuk ganti halaman saat tombol diklik
            btnMenu.addActionListener(e -> cardLayout.show(mainPanel, menu));
            sidebar.add(btnMenu);
        }

        // Rakit ke dalam Frame
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    // Method pembantu untuk merancang tombol agar flat dan modern
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(240, 45));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setForeground(TEXT_WHITE);
        btn.setBackground(SIDEBAR_BG);
        
        // Menghilangkan border dan efek klik bawaan Swing
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // Padding dalam tombol

        // Efek Hover: Berubah warna saat kursor masuk
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(SIDEBAR_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(SIDEBAR_BG);
            }
        });

        return btn;
    }

    // Method pembantu untuk membuat template halaman konten
    private JPanel createPage(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Absolute layout untuk kemudahan contoh, gunakan GridBagLayout untuk responsif
        panel.setBackground(MAIN_BG);

        // Header Putih di setiap halaman
        JPanel headerCard = new JPanel(new BorderLayout());
        headerCard.setBounds(30, 30, 680, 90);
        headerCard.setBackground(Color.WHITE);
        headerCard.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(TEXT_DARK);

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(100, 116, 139)); // Warna teks sekunder (abu-abu)

        headerCard.add(lblTitle, BorderLayout.NORTH);
        headerCard.add(lblSub, BorderLayout.CENTER);

        panel.add(headerCard);
        return panel;
    }
}