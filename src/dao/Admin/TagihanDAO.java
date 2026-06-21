package dao.Admin;

import config.KoneksiDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TagihanDAO {

    private final Connection conn;

    public TagihanDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Object[]> getAllTagihanTableData() {
        List<Object[]> list = new ArrayList<>();
        
        String sql = "SELECT t.id_tagihan, t.tahun, u.name, k.nomor_kamar, " +
                     "(t.jumlah + t.denda) AS total_tagihan, t.jatuh_tempo, t.status " +
                     "FROM tagihan t " +
                     "JOIN penghuni p ON t.id_penghuni = p.id_penghuni " +
                     "JOIN users u ON p.id_user = u.id " +
                     "LEFT JOIN kontrak_sewa ks ON p.id_penghuni = ks.id_penghuni " +
                     "LEFT JOIN kamar k ON ks.id_kamar = k.id_kamar " +
                     "ORDER BY t.jatuh_tempo DESC";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String noInvoice = "INV-" + rs.getInt("tahun") + "-" + String.format("%03d", rs.getInt("id_tagihan"));
                
                Object[] row = {
                    noInvoice,
                    rs.getString("name"),
                    rs.getString("nomor_kamar") != null ? "Kamar " + rs.getString("nomor_kamar") : "-",
                    rs.getDouble("total_tagihan"),
                    rs.getDate("jatuh_tempo"),
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