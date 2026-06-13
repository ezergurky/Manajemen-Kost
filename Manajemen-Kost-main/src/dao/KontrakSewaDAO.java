package dao;

import config.KoneksiDatabase;
import interfaces.CRUDRepository;
import models.KontrakSewa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class KontrakSewaDAO implements CRUDRepository<KontrakSewa, Integer> {

    @Override
    public void tambah(KontrakSewa data) {
        String sql = "INSERT INTO kontrak_sewa (tanggal_mulai, tanggal_selesai, status) VALUES (?, ?, ?)";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(data.getTanggalMulai().getTime()));
            ps.setDate(2, new java.sql.Date(data.getTanggalSelesai().getTime()));
            ps.setString(3, data.getStatus());

            ps.executeUpdate();
            System.out.println("Data kontrak sewa berhasil ditambahkan.");

        } catch (SQLException e) {
            System.out.println("Gagal menambah data kontrak sewa: " + e.getMessage());
        }
    }

    @Override
    public boolean update(KontrakSewa data) {
        String sql = "UPDATE kontrak_sewa SET tanggal_mulai = ?, tanggal_selesai = ?, status = ? WHERE id_kontrak = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(data.getTanggalMulai().getTime()));
            ps.setDate(2, new java.sql.Date(data.getTanggalSelesai().getTime()));
            ps.setString(3, data.getStatus());
            ps.setInt(4, data.getIdKontrak());

            int baris = ps.executeUpdate();
            return baris > 0;

        } catch (SQLException e) {
            System.out.println("Gagal mengubah data kontrak sewa: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean hapus(Integer id) {
        String sql = "DELETE FROM kontrak_sewa WHERE id_kontrak = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int baris = ps.executeUpdate();
            return baris > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus data kontrak sewa: " + e.getMessage());
            return false;
        }
    }

    @Override
    public KontrakSewa cariById(Integer id) {
        String sql = "SELECT * FROM kontrak_sewa WHERE id_kontrak = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToKontrakSewa(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data kontrak sewa: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<KontrakSewa> tampilkanSemua() {
        List<KontrakSewa> daftarKontrak = new ArrayList<>();
        String sql = "SELECT * FROM kontrak_sewa";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftarKontrak.add(mapToKontrakSewa(rs));
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data kontrak sewa: " + e.getMessage());
        }

        return daftarKontrak;
    }


    private KontrakSewa mapToKontrakSewa(ResultSet rs) throws SQLException {
        return new KontrakSewa(
                rs.getInt("id_kontrak"),
                rs.getDate("tanggal_mulai"),
                rs.getDate("tanggal_selesai"),
                rs.getString("status")
        );
    }
}
