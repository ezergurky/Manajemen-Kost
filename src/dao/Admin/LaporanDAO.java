package dao.Admin;

import config.KoneksiDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LaporanDAO {

    private final Connection conn;

    public LaporanDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Object[]> getLaporanTableData(String bulan, String tahun) {
        List<Object[]> list = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT CONCAT('Tagihan Sewa ', u.name, IFNULL(CONCAT(' (Kamar ', k.nomor_kamar, ')'), '')) AS keterangan, " +
            "CONCAT(t.bulan, ' / ', t.tahun) AS bulan_tahun, " +
            "IFNULL(p.metode, '-') AS metode, " +
            "IFNULL(p.jumlah_bayar, 0) AS pemasukan, " +
            "t.status " +
            "FROM tagihan t " +
            "LEFT JOIN pembayaran p ON t.id_tagihan = p.id_tagihan " +
            "JOIN penghuni ph ON t.id_penghuni = ph.id_penghuni " +
            "JOIN users u ON ph.id_user = u.id " +
            "LEFT JOIN kontrak_sewa ks ON ph.id_penghuni = ks.id_penghuni " +
            "LEFT JOIN kamar k ON ks.id_kamar = k.id_kamar " +
            "WHERE 1=1 "
        );

        if (bulan != null && !bulan.equals("Semua Bulan")) {
            sql.append("AND t.bulan = '").append(bulan).append("' ");
        }
        if (tahun != null && !tahun.equals("Semua Tahun")) {
            sql.append("AND t.tahun = ").append(tahun).append(" ");
        }
        
        sql.append("ORDER BY t.tahun DESC, t.id_tagihan DESC");

        try {
            PreparedStatement pst = conn.prepareStatement(sql.toString());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("keterangan"),
                    rs.getString("bulan_tahun"),
                    rs.getString("metode"),
                    rs.getDouble("pemasukan"),
                    rs.getString("status")
                };
                list.add(row);
            }

            rs.close();
            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public double getTotalPendapatan(String bulan, String tahun) {
        double total = 0;
        StringBuilder sql = new StringBuilder(
            "SELECT SUM(p.jumlah_bayar) AS total FROM pembayaran p " +
            "JOIN tagihan t ON p.id_tagihan = t.id_tagihan WHERE 1=1 "
        );

        if (bulan != null && !bulan.equals("Semua Bulan")) {
            sql.append("AND t.bulan = '").append(bulan).append("' ");
        }
        if (tahun != null && !tahun.equals("Semua Tahun")) {
            sql.append("AND t.tahun = ").append(tahun).append(" ");
        }

        try {
            PreparedStatement pst = conn.prepareStatement(sql.toString());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public double getTotalTunggakan(String bulan, String tahun) {
        double total = 0;
        StringBuilder sql = new StringBuilder(
            "SELECT SUM(jumlah + denda) AS total FROM tagihan WHERE status = 'belum' "
        );

        if (bulan != null && !bulan.equals("Semua Bulan")) {
            sql.append("AND bulan = '").append(bulan).append("' ");
        }
        if (tahun != null && !tahun.equals("Semua Tahun")) {
            sql.append("AND tahun = ").append(tahun).append(" ");
        }

        try {
            PreparedStatement pst = conn.prepareStatement(sql.toString());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}