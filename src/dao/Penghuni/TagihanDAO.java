package dao.Penghuni;

import config.KoneksiDatabase;
import models.Tagihan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagihanDAO {
    private final Connection conn;

    public TagihanDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Tagihan> getTagihanByPenghuni(int idPenghuni) {
        List<Tagihan> list = new ArrayList<>();
        String sql = "SELECT * FROM tagihan WHERE id_penghuni = ? ORDER BY jatuh_tempo DESC";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPenghuni);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Tagihan(
                        rs.getInt("id_tagihan"),
                        rs.getInt("id_penghuni"),
                        rs.getString("bulan"),
                        rs.getInt("tahun"),
                        rs.getDate("jatuh_tempo"),
                        rs.getDouble("jumlah"),
                        rs.getDouble("denda"),
                        rs.getString("status")
                ));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
        }
        return list;
    }

    public boolean cekSudahDibayar(int idTagihan) {
        String sql = "SELECT COUNT(*) FROM pembayaran WHERE id_tagihan = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idTagihan);
            ResultSet rs = pst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) return true;
        } catch (SQLException e) {}
        return false;
    }

    public boolean bayarTagihan(int idTagihan, String metode, double jumlah) {
        String sql = "INSERT INTO pembayaran (id_tagihan, tanggal_bayar, metode, jumlah_bayar) VALUES (?, CURRENT_DATE, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idTagihan);
            pst.setString(2, metode);
            pst.setDouble(3, jumlah);
            boolean res = pst.executeUpdate() > 0;
            pst.close();
            return res;
        } catch (SQLException e) {
            return false;
        }
    }
}