package services;

import interfaces.PembayaranService;

public class PembayaranTransfer implements PembayaranService {
    @Override
    public void prosesPembayaran() {
        System.out.println("Pembayaran transfer sedang diproses...");
        System.out.println("Pembayaran transfer berhasil.");
    }

    @Override
    public boolean validasiPembayaran() {
        System.out.println("Memvalidasi pembayaran transfer...");
        return true;
    }
}