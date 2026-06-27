package dao.Admin;

import config.KoneksiDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PenghuniDAO {

    private final Connection conn;

    public PenghuniDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Object[]> getAllPenghuniTableData() {
        List<Object[]> list = new ArrayList<>();
        
        String sql = "SELECT p.id_penghuni, u.name, k.nomor_kamar, p.no_hp, ks.tanggal_mulai, ks.status " +
                     "FROM penghuni p " +
                     "JOIN users u ON p.id_user = u.id " +
                     "LEFT JOIN kontrak_sewa ks ON p.id_penghuni = ks.id_penghuni " +
                     "LEFT JOIN kamar k ON ks.id_kamar = k.id_kamar " +
                     "ORDER BY p.id_penghuni ASC";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    "P-" + String.format("%03d", rs.getInt("id_penghuni")),
                    rs.getString("name"),
                    rs.getString("nomor_kamar") != null ? "Kamar " + rs.getString("nomor_kamar") : "Belum ada kamar",
                    rs.getString("no_hp"),
                    rs.getDate("tanggal_mulai"),
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
}