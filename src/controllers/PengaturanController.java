package controllers;

import dao.PengaturanDAO;
import views.PengaturanPanel;

import javax.swing.*;
import java.awt.*;

public class PengaturanController {
    private final PengaturanPanel view;
    private final PengaturanDAO dao;

    public PengaturanController(PengaturanPanel view) {
        this.view = view;
        this.dao = new PengaturanDAO();
        initAction();
    }

    private void initAction() {
        view.getBtnSimpanPassword().addActionListener(e -> updatePassword());
    }

    private void updatePassword() {
        String passLama = String.valueOf(view.getTxtPasswordLama().getPassword());
        String passBaru = String.valueOf(view.getTxtPasswordBaru().getPassword());
        String konfirmasi = String.valueOf(view.getTxtKonfirmasiPassword().getPassword());

        if (passLama.isEmpty() || passBaru.isEmpty() || konfirmasi.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Semua kolom kata sandi harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!passBaru.equals(konfirmasi)) {
            JOptionPane.showMessageDialog(view, "Kata sandi baru dan konfirmasi tidak cocok!", "Kesalahan Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int konfirmasiDialog = JOptionPane.showConfirmDialog(view, 
                "Apakah Anda yakin ingin memperbarui kata sandi?\nSistem akan mengeluarkan Anda (Logout) untuk login ulang setelah perubahan disimpan.", 
                "Konfirmasi Perubahan", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);

        if (konfirmasiDialog != JOptionPane.YES_OPTION) {
            return;
        }

        String roleStr = view.getRole();
        
        boolean isUpdated = dao.updatePassword(passLama, passBaru, roleStr);

        if (isUpdated) {
            JOptionPane.showMessageDialog(view, "Kata sandi berhasil diperbarui!\nSilakan login ulang dengan sandi baru Anda.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            Window window = SwingUtilities.getWindowAncestor(view);
            if (window instanceof JFrame) {
                window.dispose();
                new views.LoginPanel(); 
            }
        } else { 
            JOptionPane.showMessageDialog(view, "Gagal memperbarui kata sandi!\nPastikan Kata Sandi Lama yang dimasukkan sudah benar.", "Autentikasi Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}