package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;


public class LoginView extends JFrame {

	private JTextField accountNumberField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton registerButton;

	public LoginView() {
	    setTitle("Login");
	    setSize(500, 600);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    initComponents();
	    setVisible(true);
	}

	private void initComponents() {
	    // Panel setup
	    JPanel panel = new JPanel(new GridBagLayout());
	    panel.setBackground(Color.WHITE); 
	    setContentPane(panel);

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 20, 10, 20);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    JLabel titleLabel = new JLabel("Banking App Login", SwingConstants.CENTER);
	    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
	    titleLabel.setForeground(new Color(40, 40, 40));
	    titleLabel.setForeground(new Color(33, 37, 41));
	    
	    gbc.gridwidth = 2;
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    panel.add(titleLabel, gbc);

	    // Labels
	    JLabel accountNumberLabel = new JLabel("Account Number:");
	    accountNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    gbc.gridwidth = 1;
	    gbc.gridy = 1;
	    gbc.anchor = GridBagConstraints.LINE_END;
	    panel.add(accountNumberLabel, gbc);

	    accountNumberField = new JTextField(20);
	    accountNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    gbc.gridx = 1;
	    gbc.anchor = GridBagConstraints.LINE_START;
	    panel.add(accountNumberField, gbc);

	    JLabel passwordLabel = new JLabel("Password:");
	    passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.anchor = GridBagConstraints.LINE_END;
	    panel.add(passwordLabel, gbc);

	    passwordField = new JPasswordField(20);
	    passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	    gbc.gridx = 1;
	    gbc.anchor = GridBagConstraints.LINE_START;
	    panel.add(passwordField, gbc);

	    // Buttons
	    loginButton = new JButton("Login");
	    loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
	    loginButton.setBackground(new Color(0, 123, 255));
	    loginButton.setForeground(Color.WHITE);
	    loginButton.setFocusPainted(false);
	    loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	    
	    // Add hover effect:
	    loginButton.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	            loginButton.setBackground(new Color(0, 105, 217)); // darker blue on hover
	        }
	        @Override
	        public void mouseExited(MouseEvent e) {
	            loginButton.setBackground(new Color(0, 123, 255)); // original color
	        }
	    });

	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    gbc.gridwidth = 2;
	    gbc.anchor = GridBagConstraints.CENTER;
	    panel.add(loginButton, gbc);

	    registerButton = new JButton("Register");
	    registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
	    registerButton.setForeground(new Color(0, 123, 255));
	    registerButton.setFocusPainted(false);
	    registerButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	    registerButton.setContentAreaFilled(false);
	    
	    // Add hover effect 
	    registerButton.addMouseListener(new MouseAdapter() {
	        Font originalFont = registerButton.getFont();
	        Font underlinedFont = originalFont.deriveFont(Map.of(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON));

	        @Override
	        public void mouseEntered(MouseEvent e) {
	            registerButton.setForeground(new Color(0, 105, 217));
	            registerButton.setFont(underlinedFont);
	            registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	            registerButton.setForeground(new Color(0, 123, 255));
	            registerButton.setFont(originalFont);
	            registerButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	        }
	    });

	    gbc.gridy = 4;
	    panel.add(registerButton, gbc);
	}

	public String getAccountNumber() {
	    return accountNumberField.getText();
	}

	public String getPassword() {
	    return new String(passwordField.getPassword());
	}

	public JButton getLoginButton() {
	    return loginButton;
	}

	public JButton getRegisterButton() {
	    return registerButton;
	}

	public void showMessage(String message, String title, int messageType) {
	    JOptionPane.showMessageDialog(
	        this,
	        message,
	        title,
	        messageType
	    );
	}


}