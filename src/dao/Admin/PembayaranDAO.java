package dao.Admin;

import config.KoneksiDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PembayaranDAO {

    private final Connection conn;

    public PembayaranDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Object[]> getAllPembayaranTableData() {
        List<Object[]> list = new ArrayList<>();
        
        String sql = "SELECT p.id_pembayaran, p.id_tagihan, t.tahun, u.name, " +
                     "p.tanggal_bayar, p.metode, p.jumlah_bayar " +
                     "FROM pembayaran p " +
                     "JOIN tagihan t ON p.id_tagihan = t.id_tagihan " +
                     "JOIN penghuni ph ON t.id_penghuni = ph.id_penghuni " +
                     "JOIN users u ON ph.id_user = u.id " +
                     "ORDER BY p.tanggal_bayar DESC, p.id_pembayaran DESC";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String idBayar = "PMB-" + String.format("%03d", rs.getInt("id_pembayaran"));
                String noInvoice = "INV-" + rs.getInt("tahun") + "-" + String.format("%03d", rs.getInt("id_tagihan"));
                
                Object[] row = {
                    idBayar,
                    noInvoice,
                    rs.getString("name"),
                    rs.getDate("tanggal_bayar"),
                    rs.getString("metode"),
                    rs.getDouble("jumlah_bayar")
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