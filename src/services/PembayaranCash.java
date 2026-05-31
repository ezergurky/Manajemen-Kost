package services;

import interfaces.PembayaranService;

public class PembayaranCash implements PembayaranService {
    @Override
    public void prosesPembayaran() {
        System.out.println("Pembayaran cash sedang diproses...");
        System.out.println("Pembayaran cash berhasil.");
    }

    @Override
    public boolean validasiPembayaran() {
        System.out.println("Memvalidasi pembayaran cash...");
        return true;
    }
}