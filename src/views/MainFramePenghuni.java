package views;

import components.SideBarPenghuni;
import views.Penghuni.*;

import javax.swing.*;
import java.awt.*;

public class MainFramePenghuni extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private int idPenghuni;
    private String namaUser;
    private String emailUser;

    public MainFramePenghuni(int idPenghuni, String namaUser, String emailUser) {
        this.idPenghuni = idPenghuni;
        this.namaUser = namaUser;
        this.emailUser = emailUser;

        setTitle("Manajemen Kost — Penghuni");
        setSize(1200, 700);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());

        SideBarPenghuni sidebar = new SideBarPenghuni(this::navigateTo, namaUser, emailUser);
        root.add(sidebar, BorderLayout.WEST);

        cardLayout   = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new DashboardPenghuniPanel(idPenghuni), "DASH");
        contentPanel.add(new KamarSayaPanel(),      "ROOM");
        contentPanel.add(new TagihanPanel(), "BILL");
        contentPanel.add(new RiwayatPembayaranPanel(),      "HISTORY");
        contentPanel.add(new ProfilePanel(),      "PROFILE");
        contentPanel.add(new PengaturanPanel(),      "SETTINGS");

        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);

        navigateTo("DASH");
    }

    public void navigateTo(String key) {
        if (key.equals("LOGOUT")) {
            logout();
        } else {
            cardLayout.show(contentPanel, key);
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin keluar?", "Konfirmasi Logout", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginPanel(); 
        }
    }
}