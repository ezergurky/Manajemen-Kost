package dao.Penghuni;

import config.KoneksiDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DashboardPenghuniDAO {
    private final Connection conn;

    public DashboardPenghuniDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public Object[] getInfoKamar(int idPenghuni) {
        Object[] info = null;
        String sql = "SELECT k.nomor_kamar, ko.nama_kost FROM kontrak_sewa ks JOIN kamar k ON ks.id_kamar = k.id_kamar JOIN kost ko ON k.id_kost = ko.id_kost WHERE ks.id_penghuni = ? AND ks.status = 'aktif'";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPenghuni);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                info = new Object[] {
                    rs.getString("nomor_kamar"),
                    rs.getString("nama_kost")
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    public Object[] getTagihanTerbaru(int idPenghuni) {
        Object[] tagihan = null;
        String sql = "SELECT (jumlah + denda) AS total, bulan, tahun, status FROM tagihan WHERE id_penghuni = ? ORDER BY id_tagihan DESC LIMIT 1";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPenghuni);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                tagihan = new Object[]{
                    rs.getDouble("total"),
                    rs.getString("bulan"),
                    rs.getInt("tahun"),
                    rs.getString("status")
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tagihan;
    }

    public List<Object[]> getRiwayatPembayaran(int idPenghuni) {
        List<Object[]> riwayat = new ArrayList<>();
        String sql = "SELECT p.id_pembayaran, t.bulan, t.tahun, p.tanggal_bayar, p.metode, p.jumlah_bayar FROM pembayaran p JOIN tagihan t ON p.id_tagihan = t.id_tagihan WHERE t.id_penghuni = ? ORDER BY p.tanggal_bayar DESC, p.id_pembayaran DESC LIMIT 10";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPenghuni);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String idBayar = "PMB-" + String.format("%03d", rs.getInt("id_pembayaran"));
                String bulanTahun = rs.getString("bulan") + " " + rs.getInt("tahun");
                Object[] row = {
                    idBayar,
                    bulanTahun,
                    rs.getDate("tanggal_bayar"),
                    rs.getString("metode"),
                    rs.getDouble("jumlah_bayar")
                };
                riwayat.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return riwayat;
    }
}
