// uyeolma

package sayfalar;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class uyeolma extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel messageLabel;
	
	
	
	
	/**
	 * Create the panel.
	 */
	public uyeolma(CardLayout cl,JPanel contentPane) {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Üye olmak için formu doldurunuz");
		lblNewLabel.setBounds(371, 33, 210, 13);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(390, 133, 131, 19);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(390, 185, 131, 19);
		add(textField_1);
		
		JLabel lblEmailAdresiniz = new JLabel("e-mail adresiniz");
		lblEmailAdresiniz.setBounds(390, 110, 161, 13);
		add(lblEmailAdresiniz);
		
		JLabel lblifreniz = new JLabel("şifreniz ");
		lblifreniz.setBounds(390, 162, 161, 13);
		add(lblifreniz);
		
		JLabel lblifreyiYenidenGirniz = new JLabel("şifreyi yeniden giriniz");
		lblifreyiYenidenGirniz.setBounds(390, 218, 161, 13);
		add(lblifreyiYenidenGirniz);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(390, 241, 131, 19);
		add(textField_2);
		
		 messageLabel = new JLabel("");  // Başlangıçta boş
	        messageLabel.setBounds(371, 64, 200, 20);
	        add(messageLabel);
		
		
		JButton btnNewButton = new JButton("üye ol");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String email = textField.getText();
                String sifre = textField_1.getText();
                String sifreTekrar = textField_2.getText();

                if (email.isEmpty() || sifre.isEmpty() || sifreTekrar.isEmpty()) {
                    messageLabel.setText("Lütfen tüm alanları doldurun.");
                    return;
                }if (!sifre.equals(sifreTekrar)) {
                    messageLabel.setText("Şifreler uyuşmuyor.");
                    return;
                }

                if (VeritabaniYardimcisi.isEmailRegistered(email)) {
                    messageLabel.setText("Zaten üyesiniz!");
                } 
                else {
                	
                        // Kullanıcıyı veritabanına kaydediyoruz
                        boolean isSuccess = VeritabaniYardimcisi.addUser(email, sifre);
                        messageLabel.setText("Kayıt başarıyla tamamlandı.");
                }}
		});
		btnNewButton.setBounds(416, 290, 85, 21);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<--");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(contentPane, "giris");
				
			}
		});
		btnNewButton_1.setBounds(25, 29, 85, 21);
		add(btnNewButton_1);

	}
}
