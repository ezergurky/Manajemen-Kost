package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import views.MainFrameAdmin;
import views.LoginView;

import config.KoneksiDatabase;

public class LoginController {
    private final LoginView view;

    public LoginController(LoginView view) {
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

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(view, "Login berhasil sebagai " + role);

                view.dispose();
                new MainFrameAdmin();
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