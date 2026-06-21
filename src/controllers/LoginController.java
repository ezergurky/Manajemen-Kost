package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import views.MainFrameAdmin;
import views.MainFramePenghuni;
import views.LoginPanel;

import config.KoneksiDatabase;

public class LoginController {
    private final LoginPanel view;

    public LoginController(LoginPanel view) {
        this.view = view;
        initEvent();
    }

    public void initEvent() {
        view.getBtnLogin().addActionListener(e -> handleLogin());
    }

    public void handleLogin() {
        String email = view.getEmail();
        String password = view.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Email dan password harus diisi");
            return;
        }

        try {
            Connection con = KoneksiDatabase.getConnection();

            String query = "SELECT u.*, p.id_penghuni FROM users u LEFT JOIN penghuni p ON u.id = p.id_user WHERE u.email = ? AND u.password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                String name = rs.getString("name");
                String emailUser = rs.getString("email");

                JOptionPane.showMessageDialog(view, "Login berhasil sebagai " + role);
                view.dispose();

                if(role.equals("admin")) {
                    new MainFrameAdmin();
                } else if(role.equals("penghuni")) {
                    int idPenghuni = rs.getInt("id_penghuni");
                    new MainFramePenghuni(idPenghuni, name, emailUser);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Email atau password salah");
            }

            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
}