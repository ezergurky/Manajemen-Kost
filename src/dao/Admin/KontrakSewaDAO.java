package dao.Admin;

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
    private final Connection conn;

    public KontrakSewaDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    @Override
    public void tambah(KontrakSewa data) {
        String sql = "INSERT INTO kontrak_sewa (id_penghuni, id_kamar, tanggal_mulai, tanggal_selesai, status) VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, data.getIdPenghuni());
            ps.setInt(2, data.getIdKamar());
            ps.setDate(3, new java.sql.Date(data.getTanggalMulai().getTime()));
            ps.setDate(4, new java.sql.Date(data.getTanggalSelesai().getTime()));
            ps.setString(5, data.getStatus());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Gagal menambah data kontrak sewa: " + e.getMessage());
        }
    }

    @Override
    public boolean update(KontrakSewa data) {
        String sql = "UPDATE kontrak_sewa SET id_penghuni = ?, id_kamar = ?, tanggal_mulai = ?, tanggal_selesai = ?, status = ? WHERE id_kontrak = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, data.getIdPenghuni());
            ps.setInt(2, data.getIdKamar());
            ps.setDate(3, new java.sql.Date(data.getTanggalMulai().getTime()));
            ps.setDate(4, new java.sql.Date(data.getTanggalSelesai().getTime()));
            ps.setString(5, data.getStatus());
            ps.setInt(6, data.getIdKontrak());

            boolean result = ps.executeUpdate() > 0;
            ps.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean hapus(Integer id) {
        String sql = "DELETE FROM kontrak_sewa WHERE id_kontrak = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            boolean result = ps.executeUpdate() > 0;
            ps.close();
            return result;
        } catch (SQLException e) { 
            return false; 
        }
    }

    @Override
    public KontrakSewa cariById(Integer id) {
        String sql = "SELECT * FROM kontrak_sewa WHERE id_kontrak = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                KontrakSewa ks = mapToKontrakSewa(rs);
                rs.close();
                ps.close();
                return ks;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {}
        return null;
    }

    @Override
    public List<KontrakSewa> tampilkanSemua() {
        List<KontrakSewa> daftar = new ArrayList<>();
        String sql = "SELECT * FROM kontrak_sewa";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                daftar.add(mapToKontrakSewa(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {}
        return daftar;
    }

    private KontrakSewa mapToKontrakSewa(ResultSet rs) throws SQLException {
        return new KontrakSewa(
                rs.getInt("id_kontrak"),
                rs.getInt("id_penghuni"), 
                rs.getInt("id_kamar"),  
                rs.getDate("tanggal_mulai"),
                rs.getDate("tanggal_selesai"),
                rs.getString("status")
        );
    }

    public void akhiriKontrakByKamar(int idKamar) {
        String sql = "UPDATE kontrak_sewa SET status = 'selesai', tanggal_selesai = CURRENT_DATE WHERE id_kamar = ? AND status = 'aktif'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idKamar);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Gagal mengakhiri kontrak otomatis: " + e.getMessage());
        }
    }
}