
//vertabanıyardımıcısı sayfası

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
	private static final String URL = "jdbc:mysql://localhost:3306/ayka"; // Veritabanı URL
    private static final String USER = "root"; // Veritabanı kullanıcı adı
    private static final String PASSWORD = "Hk795104199"; // Veritabanı şifresi
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    
    
    // Veritabanı bağlantısını kuran ve kullanıcıyı doğrulayan metod
    public static boolean rolKontrolu(String email, String sifre) {
        boolean adminMi = false;
        
        try {
            Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD);

            // Kullanıcının e-posta ve şifresini sorgulayan SQL cümlesi
            String sql = "SELECT rol FROM kullanicilar WHERE email = ? AND sifre = ?";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            komut.setString(1, email);  // E-posta parametresini ekliyoruz
            komut.setString(2, sifre);   // Şifre parametresini ekliyoruz

            // SQL sorgusunu çalıştırıyoruz
            ResultSet sonuc = komut.executeQuery();

            if (sonuc.next()) {
                String rol = sonuc.getString("rol");  // Kullanıcının rolünü alıyoruz
                if (rol.equals("admin")) {
                    adminMi = true;  // Kullanıcı yönetici ise true döndürüyoruz
                }
            }

            baglanti.close();  // Bağlantıyı kapatıyoruz
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return adminMi;  // Sonuç olarak admin olup olmadığını döndürüyoruz
    }
    
    // Veritabanında e-posta adresinin olup olmadığını kontrol eden metod
    
    public static boolean isEmailRegistered(String email) {
        boolean emailVarMi = false;
        try {
            Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM kullanicilar WHERE email = ?";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            komut.setString(1, email);  // E-posta parametresini ekliyoruz

            ResultSet sonuc = komut.executeQuery();

            if (sonuc.next()) {
                emailVarMi = true;  // Eğer e-posta bulunduysa, kullanıcı zaten kayıtlıdır
            }

            baglanti.close();  // Bağlantıyı kapatıyoruz
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailVarMi;  // E-posta veritabanında varsa true, yoksa false döner
    }
    
    // Kullanıcıyı veritabanına eklemek için metot
    
    public static boolean addUser(String email, String sifre) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Veritabanı bağlantısını oluştur
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL sorgusunu hazırla
            String sql = "INSERT INTO kullanicilar (email, sifre, rol) VALUES (?, ?, 'personel')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, sifre);

            // Sorguyu çalıştır
            int result = pstmt.executeUpdate();
            return result > 0; // Başarılıysa true döner
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

        return false; // Hata durumunda false döner
    }
    
// veritabanındaki e-posta adresini ve şifrenin doğru  olup olmadığını kontrol eden method
    
    public static boolean girisBilgileriDogruMu(String eposta, String sifre) {
        boolean bilgilerDogru = false;

        try {
            Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD);

            // E-posta ve şifreyi sorgulayan SQL cümlesi
            String sql = "SELECT * FROM kullanicilar WHERE email = ? AND sifre = ?";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            komut.setString(1, eposta);  // E-posta parametresini ekliyoruz
            komut.setString(2, sifre);   // Şifre parametresini ekliyoruz

            // SQL sorgusunu çalıştırıyoruz
            ResultSet sonuc = komut.executeQuery();

            if (sonuc.next()) {
                bilgilerDogru = true;  // Eğer veritabanında böyle bir kullanıcı varsa, giriş bilgileri doğru demektir
            }

            baglanti.close();  // Bağlantıyı kapatıyoruz
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bilgilerDogru;  // Eğer doğru bilgilerle bir kullanıcı varsa true döner, yoksa false
    }

 // Veritabanından e-posta adreslerini döndüren metot
    public static ArrayList<String> getEmailList() {
        ArrayList<String> emailList = new ArrayList<>();
        try {
            Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD);

            // E-posta adreslerini sorgulayan SQL cümlesi
            String sql = "SELECT email FROM kullanicilar";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            ResultSet sonuc = komut.executeQuery();

            while (sonuc.next()) {
                emailList.add(sonuc.getString("email")); // E-postaları listeye ekliyoruz
            }

            baglanti.close(); // Bağlantıyı kapatıyoruz
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailList; // E-posta adreslerini döndürüyoruz
    }
   
    // Görev ekleme metodu
    public static void addGorevToDatabase(String tarih, String gorev, String personel_email,String personel) {
        // Tarih formatını doğru hale getiriyoruz
        String formattedDate = formatDate(tarih);

        // Eğer formatDate null dönerse, demek ki tarih yanlış formatta. O yüzden işlemi durduruyoruz.
        if (formattedDate == null) {
            System.out.println("Geçersiz tarih formatı");
            return;
        }

        String sql = "INSERT INTO gorevler (tarih, gorev, personel_email ,personel) VALUES (?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, formattedDate);  // Şimdi formatlanmış tarihi kullanıyoruz
            stmt.setString(2, gorev);
            stmt.setString(3, personel_email);
            stmt.setString(4, personel);
            stmt.executeUpdate();  // Veriyi veritabanına ekliyoruz
        } catch (SQLException ex) {
            ex.printStackTrace();  // Eğer veritabanı hatası olursa, bu hatayı yazdırıyoruz
        }
    }

 // Tarihi formatlayan metot
    public static String formatDate(String tarih) {
        try {
            // Kullanıcıdan gelen tarihi dd.MM.yyyy formatında alıyoruz
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = inputFormat.parse(tarih);  // Bu adımda tarihi objeye çeviriyoruz
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Tarihi MySQL'in istediği formata çeviriyoruz
            return outputFormat.format(date);  // Sonuç olarak yyyy-MM-dd formatında dönecek
        } catch (Exception e) {
            e.printStackTrace();  // Eğer hata olursa, hata mesajı yazdırılacak
            return null;  // Eğer tarih geçersizse, null döner
        
        }}

    
    // Görevleri çeken ve sadece belirli bir personele ait olanları döndüren metot
    public static ArrayList<String> getGorevListByPersonel(String personelEmail) {
        ArrayList<String> GorevList = new ArrayList<>();
        try (Connection baglanti = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Sadece belirli bir personel için görevleri sorgulayan SQL cümlesi
            String sql = "SELECT gorev FROM gorevler WHERE personel_email = ?";
            PreparedStatement komut = baglanti.prepareStatement(sql);
            komut.setString(1, personelEmail);  // Belirtilen personelin e-posta adresini ekliyoruz

            ResultSet sonuc = komut.executeQuery();

            while (sonuc.next()) {
                GorevList.add(sonuc.getString("gorev"));  // Personelin görevlerini listeye ekliyoruz
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Eğer veritabanı hatası olursa, hata mesajı yazdırılacak
        }
        return GorevList;  // Personelin görevlerini döndürüyoruz
    }

   

}


  
    
    





	
	