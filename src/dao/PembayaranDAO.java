package dao;

import config.KoneksiDatabase;
import interfaces.CRUDRepository;
import models.Pembayaran;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PembayaranDAO implements CRUDRepository<Pembayaran, Integer> {

    @Override
    public void tambah(Pembayaran data) {
        String sql = "INSERT INTO pembayaran (tanggal, jumlah, status) VALUES (?, ?, ?)";

        try (Connection conn = KoneksiDatabase.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(data.getTanggal().getTime()));
            ps.setDouble(2, data.getJumlah());
            ps.setString(3, data.getStatus());

            ps.executeUpdate();
            System.out.println("Data pembayaran berhasil ditambahkan.");

        } catch (SQLException e) {
            System.out.println("Gagal menambah data pembayaran: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Pembayaran data) {
        String sql = "UPDATE pembayaran SET tanggal = ?, jumlah = ?, status = ? WHERE id_pembayaran = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(data.getTanggal().getTime()));
            ps.setDouble(2, data.getJumlah());
            ps.setString(3, data.getStatus());
            ps.setInt(4, data.getIdPembayaran());

            int baris = ps.executeUpdate();
            return baris > 0;

        } catch (SQLException e) {
            System.out.println("Gagal mengubah data pembayaran: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean hapus(Integer id) {
        String sql = "DELETE FROM pembayaran WHERE id_pembayaran = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int baris = ps.executeUpdate();
            return baris > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus data pembayaran: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Pembayaran cariById(Integer id) {
        String sql = "SELECT * FROM pembayaran WHERE id_pembayaran = ?";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToPembayaran(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data pembayaran: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Pembayaran> tampilkanSemua() {
        List<Pembayaran> daftarPembayaran = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran";

        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                daftarPembayaran.add(mapToPembayaran(rs));
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data pembayaran: " + e.getMessage());
        }

        return daftarPembayaran;
    }

    private Pembayaran mapToPembayaran(ResultSet rs) throws SQLException {
        return new Pembayaran(
                rs.getInt("id_pembayaran"),
                rs.getDate("tanggal"),
                rs.getDouble("jumlah"),
                rs.getString("status")
        );
    }
}
