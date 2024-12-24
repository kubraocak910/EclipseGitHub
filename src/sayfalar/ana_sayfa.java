
//Ana sayfa

package sayfalar;



import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ana_sayfa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ana_sayfa frame = new ana_sayfa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	

	public ana_sayfa() { // constructor
		
	
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // pencere kapanınca uygulamanın kapanması için var 
		setBounds(100, 100, 987, 452);
		contentPane = new JPanel(); // ana paneli oluşturuyor
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane); // OLUŞTURULAN PANELLERİ ANA SAYFAYA EKLİYOR 
		CardLayout cl = new CardLayout(0, 0); // CARDLOYUT DÜZENLEYİCİSİ PANELLERİ ÜST ÜSTE YERLEŞTİRİP ARALARINDA GEÇİŞ SAĞLIYOR bu yaptığımızı giriş sayfasına ekleyerek giriş sayfasındada bu ögleri kullanabileceğiz 
		contentPane.setLayout(cl); // ?????????
		//*****// giriş sayfasında kullanığımız constructor
		giris giris =new giris(cl, contentPane); // GİRİŞ SAYFASINDAN GİRİŞ İLE İLGLİ BİR NESNE OLUŞTURDUK  ???
		
		contentPane.add(giris, "giris");// ANA SAYFAYA (CONTENT PANE)EKLEDİK
		giris.setLayout(null); //GİRİŞ SAYFAINSA UYGULAM YAPAMN İÇİN VAR AMN ŞUAN NULL DÖNDÜRÜYOR 
		
	
		uyeolma uyeolma =new uyeolma(cl, contentPane);
		contentPane.add(uyeolma ,"uyeolma");
		
		uyeolma.setLayout(null);
		
		yonetici yonetici =new yonetici();	
		contentPane.add(yonetici,"yonetici");
		yonetici.setLayout(null);
		
		personel personel  =new personel();	
		contentPane.add(personel,"personel");
		
		// ilk başta görünmedi çünkü   celips bunu çalıştıramadı 
//		cl.show(contentPane, "uyeolma"); // uygulamayı ilk çalıştırdığımızda hangi sayfa görünecek ise o sayfa için bu kodu yazıyoruz 
		
	}
}
