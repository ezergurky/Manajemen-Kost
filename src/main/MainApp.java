package main;

import javax.swing.SwingUtilities;

import views.LoginView;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView());
    }
}