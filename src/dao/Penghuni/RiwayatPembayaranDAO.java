package dao.Penghuni;

import config.KoneksiDatabase;
import models.Pembayaran;
import models.Penghuni;
import models.Tagihan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RiwayatPembayaranDAO {
    private final Connection conn;

    public RiwayatPembayaranDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Pembayaran> getRiwayatByPenghuni(int idPenghuni) {
        List<Pembayaran> list = new ArrayList<>();
        
        String sql = "SELECT p.id_pembayaran, p.id_tagihan, p.tanggal_bayar, p.metode, p.jumlah_bayar, " +
                     "t.bulan, t.tahun, t.status AS status_tagihan, " +
                     "ph.id_penghuni, u.name " +
                     "FROM pembayaran p " +
                     "JOIN tagihan t ON p.id_tagihan = t.id_tagihan " +
                     "JOIN penghuni ph ON t.id_penghuni = ph.id_penghuni " +
                     "JOIN users u ON ph.id_user = u.id " +
                     "WHERE ph.id_penghuni = ? AND t.status = 'lunas' " +
                     "ORDER BY p.tanggal_bayar DESC";
                     
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPenghuni);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Pembayaran pay = new Pembayaran(
                    rs.getInt("id_pembayaran"),
                    rs.getInt("id_tagihan"),
                    rs.getDate("tanggal_bayar"),
                    rs.getString("metode"),
                    rs.getDouble("jumlah_bayar")
                );

                Tagihan tagihan = new Tagihan(
                    rs.getInt("id_tagihan"),
                    rs.getInt("id_penghuni"),
                    rs.getString("bulan"),
                    rs.getInt("tahun"),
                    null, 0, 0, 
                    rs.getString("status_tagihan")
                );

                Penghuni penghuni = new Penghuni(0, rs.getString("name"), "", "", "", rs.getInt("id_penghuni"), "", "");
                
                tagihan.setPenghuni(penghuni);
                pay.setTagihan(tagihan);

                list.add(pay);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}