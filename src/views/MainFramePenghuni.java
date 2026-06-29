package views;

import components.SideBarPenghuni;
import views.Penghuni.*;
import models.Penghuni;

import javax.swing.*;
import java.awt.*;

public class MainFramePenghuni extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private int idPenghuni;
    private String namaUser;
    private String emailUser;

    private DashboardPenghuniPanel dashPanel;
    private KamarSayaPanel kamarPanel;
    private TagihanPanel tagihanPanel;
    private RiwayatPembayaranPanel riwayatPanel;
    private ProfilePanel profilePanel;

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

        dashPanel = new DashboardPenghuniPanel(idPenghuni);
        kamarPanel = new KamarSayaPanel(idPenghuni);
        tagihanPanel = new TagihanPanel(idPenghuni);
        riwayatPanel = new RiwayatPembayaranPanel(idPenghuni);
        profilePanel = new ProfilePanel(idPenghuni);

        contentPanel.add(dashPanel, "DASH");
        contentPanel.add(kamarPanel, "ROOM");
        contentPanel.add(tagihanPanel, "BILL");
        contentPanel.add(riwayatPanel, "HISTORY");
        contentPanel.add(profilePanel, "PROFILE");
        contentPanel.add(new PengaturanPanel("Penghuni"), "SETTINGS");

        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);

        navigateTo("DASH");
    }

    public void navigateTo(String key) {
        if (key.equals("LOGOUT")) {
            Penghuni penghuni = new Penghuni(0, namaUser, emailUser, "", "", idPenghuni, "", "");
            penghuni.logout(this);
        } else {
            if (key.equals("DASH") && dashPanel != null) {
                dashPanel.refreshData();
            } else if (key.equals("ROOM") && kamarPanel != null) {
                kamarPanel.refreshData();
            } else if (key.equals("BILL") && tagihanPanel != null) {
                tagihanPanel.refreshData();
            } else if (key.equals("HISTORY") && riwayatPanel != null) {
                riwayatPanel.refreshData();
            }

            cardLayout.show(contentPanel, key);
        }
    }
}