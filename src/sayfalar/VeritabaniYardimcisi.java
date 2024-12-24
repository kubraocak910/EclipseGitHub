package sayfalar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VeritabaniYardimcisi {
    private static final String URL = "jdbc:mysql://localhost:3306/ayka";
    private static final String USER = "root";
    private static final String PASSWORD = "Hk795104199";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static boolean rolKontrolu(String email, String sifre) {
        boolean adminMi = false;
        
        try {
            Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT rol FROM kullanicilar WHERE email = ? AND sifre = ?";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            komut.setString(1, email);
            komut.setString(2, sifre);
            ResultSet sonuc = komut.executeQuery();

            if (sonuc.next()) {
                String rol = sonuc.getString("rol");
                if (rol.equals("admin")) {
                    adminMi = true;
                }
            }

            baglanti.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return adminMi;
    }
    
    public static boolean isEmailRegistered(String email) {
        boolean emailVarMi = false;
        try {
            Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM kullanicilar WHERE email = ?";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            komut.setString(1, email);
            ResultSet sonuc = komut.executeQuery();

            if (sonuc.next()) {
                emailVarMi = true;
            }

            baglanti.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailVarMi;
    }
    
    public static boolean addUser(String email, String sifre) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "INSERT INTO kullanicilar (email, sifre, rol) VALUES (?, ?, 'personel')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, sifre);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    
    public static boolean girisBilgileriDogruMu(String eposta, String sifre) {
        boolean bilgilerDogru = false;

        try {
            Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM kullanicilar WHERE email = ? AND sifre = ?";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            komut.setString(1, eposta);
            komut.setString(2, sifre);
            ResultSet sonuc = komut.executeQuery();

            if (sonuc.next()) {
                bilgilerDogru = true;
            }

            baglanti.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bilgilerDogru;
    }

    public static ArrayList<String> getEmailList() {
        ArrayList<String> emailList = new ArrayList<>();
        try {
            Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT email FROM kullanicilar";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            ResultSet sonuc = komut.executeQuery();

            while (sonuc.next()) {
                emailList.add(sonuc.getString("email"));
            }

            baglanti.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailList;
    }

    public static void addGorevToDatabase(String tarih, String gorev, String personel_email, String personel) {
        String formattedDate = formatDate(tarih);

        if (formattedDate == null) {
            System.out.println("Geçersiz tarih formatı");
            return;
        }

        String sql = "INSERT INTO gorevler (tarih, gorev, personel_email ,personel) VALUES (?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, formattedDate);
            stmt.setString(2, gorev);
            stmt.setString(3, personel_email);
            stmt.setString(4, personel);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static String formatDate(String tarih) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = inputFormat.parse(tarih);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getGorevListByPersonel(String personelEmail) {
        ArrayList<String> GorevList = new ArrayList<>();
        try (Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT gorev FROM gorevler WHERE personel_email = ?";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            komut.setString(1, personelEmail);
            ResultSet sonuc = komut.executeQuery();

            while (sonuc.next()) {
                GorevList.add(sonuc.getString("gorev"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return GorevList;
    }
}
