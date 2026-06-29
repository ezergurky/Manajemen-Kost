package dao.Penghuni;

import config.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KamarSayaDAO {
    private final Connection conn;

    public KamarSayaDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public Object[] getDetailKamarPenghuni(int idPenghuni) {
        Object[] detail = null;
        String sql = "SELECT k.nomor_kamar, k.harga, k.fasilitas, " +
                     "ko.nama_kost, ko.alamat, " +
                     "ks.tanggal_mulai, ks.tanggal_selesai, ks.status " +
                     "FROM kontrak_sewa ks " +
                     "JOIN kamar k ON ks.id_kamar = k.id_kamar " +
                     "JOIN kost ko ON k.id_kost = ko.id_kost " +
                     "WHERE ks.id_penghuni = ? AND ks.status = 'aktif' " +
                     "ORDER BY ks.id_kontrak DESC LIMIT 1";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPenghuni);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                detail = new Object[]{
                    rs.getString("nomor_kamar"),
                    rs.getDouble("harga"),
                    rs.getString("fasilitas"),
                    rs.getString("nama_kost"),
                    rs.getString("alamat"),
                    rs.getDate("tanggal_mulai"),
                    rs.getDate("tanggal_selesai"),
                    rs.getString("status")
                };
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}