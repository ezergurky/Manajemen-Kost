package services;

import dao.KontrakSewaDAO;
import models.KontrakSewa;

import java.util.Date;
import java.util.List;


public class KontrakService {

    private final KontrakSewaDAO kontrakSewaDAO;

    public KontrakService() {
        this.kontrakSewaDAO = new KontrakSewaDAO();
    }

    public KontrakService(KontrakSewaDAO kontrakSewaDAO) {
        this.kontrakSewaDAO = kontrakSewaDAO;
    }

    public boolean buatKontrakBaru(Date tanggalMulai, Date tanggalSelesai) {
        if (tanggalMulai == null || tanggalSelesai == null) {
            System.out.println("Tanggal mulai dan tanggal selesai wajib diisi.");
            return false;
        }

        if (tanggalSelesai.before(tanggalMulai)) {
            System.out.println("Tanggal selesai tidak boleh sebelum tanggal mulai.");
            return false;
        }

        KontrakSewa kontrakBaru = new KontrakSewa(0, tanggalMulai, tanggalSelesai, "Aktif");
        kontrakSewaDAO.tambah(kontrakBaru);
        return true;
    }

    public boolean aktifkanKontrak(int idKontrak) {
        KontrakSewa kontrak = kontrakSewaDAO.cariById(idKontrak);
        if (kontrak == null) {
            System.out.println("Kontrak dengan ID " + idKontrak + " tidak ditemukan.");
            return false;
        }
        kontrak.aktifkan();
        return kontrakSewaDAO.update(kontrak);
    }

    public boolean akhiriKontrak(int idKontrak) {
        KontrakSewa kontrak = kontrakSewaDAO.cariById(idKontrak);
        if (kontrak == null) {
            System.out.println("Kontrak dengan ID " + idKontrak + " tidak ditemukan.");
            return false;
        }
        kontrak.selesai();
        return kontrakSewaDAO.update(kontrak);
    }

    public List<KontrakSewa> getSemuaKontrak() {
        return kontrakSewaDAO.tampilkanSemua();
    }

    public KontrakSewa cariKontrak(int idKontrak) {
        return kontrakSewaDAO.cariById(idKontrak);
    }

    public boolean hapusKontrak(int idKontrak) {
        return kontrakSewaDAO.hapus(idKontrak);
    }

    public boolean isKontrakKedaluwarsa(KontrakSewa kontrak) {
        if (kontrak == null || kontrak.getTanggalSelesai() == null) {
            return false;
        }
        Date sekarang = new Date();
        return kontrak.getStatus().equalsIgnoreCase("Aktif")
                && kontrak.getTanggalSelesai().before(sekarang);
    }
}
