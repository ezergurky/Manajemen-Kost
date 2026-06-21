package dao.Admin;

import config.KoneksiDatabase;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DashboardAdminDAO {
    private final Connection conn;

    public DashboardAdminDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public int[] getKamarStats() {
        int[] stats = {0, 0}; 
        String sql = "SELECT COUNT(id_kamar) AS total, SUM(CASE WHEN status = 'terisi' THEN 1 ELSE 0 END) AS terisi FROM kamar";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                stats[0] = rs.getInt("total");
                stats[1] = rs.getInt("terisi");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    public int getTotalPenghuniAktif() {
        int total = 0;
        String sql = "SELECT COUNT(id_kontrak) AS total FROM kontrak_sewa WHERE status = 'aktif'";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public double getPendapatanBulanIni() {
        double total = 0;
        String sql = "SELECT SUM(jumlah_bayar) AS total FROM pembayaran WHERE MONTH(tanggal_bayar) = MONTH(CURRENT_DATE()) AND YEAR(tanggal_bayar) = YEAR(CURRENT_DATE())";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Object[]> getAktivitasTerbaru() {
        List<Object[]> aktivitas = new ArrayList<>();
        String sql = "SELECT p.tanggal_bayar, 'Pembayaran Sewa' AS tipe, " +
                     "CONCAT(u.name, ' - Kamar ', k.nomor_kamar, ' (Bulan ', t.bulan, ')') AS keterangan, " +
                     "p.jumlah_bayar " +
                     "FROM pembayaran p " +
                     "JOIN tagihan t ON p.id_tagihan = t.id_tagihan " +
                     "JOIN penghuni ph ON t.id_penghuni = ph.id_penghuni " +
                     "JOIN users u ON ph.id_user = u.id " +
                     "JOIN kontrak_sewa ks ON ph.id_penghuni = ks.id_penghuni " +
                     "JOIN kamar k ON ks.id_kamar = k.id_kamar " +
                     "ORDER BY p.tanggal_bayar DESC LIMIT 10";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getDate("tanggal_bayar").toString(),
                    rs.getString("tipe"),
                    rs.getString("keterangan"),
                    rs.getDouble("jumlah_bayar")
                };
                aktivitas.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aktivitas;
    }
}