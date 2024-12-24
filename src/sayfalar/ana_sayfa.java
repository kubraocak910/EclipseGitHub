package sayfalar;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ana_sayfa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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

	public ana_sayfa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 987, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		CardLayout cl = new CardLayout(0, 0);
		contentPane.setLayout(cl);

		giris giris = new giris(cl, contentPane);
		contentPane.add(giris, "giris");
		giris.setLayout(null);

		uyeolma uyeolma = new uyeolma(cl, contentPane);
		contentPane.add(uyeolma, "uyeolma");

		uyeolma.setLayout(null);

		yonetici yonetici = new yonetici();
		contentPane.add(yonetici, "yonetici");
		yonetici.setLayout(null);

		personel personel = new personel();
		contentPane.add(personel, "personel");
	}
}
