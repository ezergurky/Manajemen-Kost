package controllers;

import javax.swing.JOptionPane;

import views.DashboardAdminView;
import views.LoginView;

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

        if(email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Email dan password harus diisi");
        }

        if(email.equals("ezerganteng@kost.com") && password.equals("123")) {
            JOptionPane.showMessageDialog(view, "Login berhasil sebagai Admin");
            view.dispose();
            new DashboardAdminView();
        } else {
            JOptionPane.showMessageDialog(view, "Email atau password salah");
        }
    }
}
