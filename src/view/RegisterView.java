package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

public class RegisterView extends JFrame {

    private JTextField firstNameField, lastNameField, birthDateField, cinField, emailField, phoneField, initialBalanceField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginRedirectButton;

    public RegisterView() {
        setTitle("Register");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Register New Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(33, 37, 41));

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        String[] labels = {
            "First Name:", "Last Name:", "Birth Date (yyyy-mm-dd):",
            "CIN:", "Email:", "Phone:", "Password:", "Initial Balance:"
        };

        Component[] fields = {
            firstNameField = new JTextField(15),
            lastNameField = new JTextField(15),
            birthDateField = new JTextField(15),
            cinField = new JTextField(15),
            emailField = new JTextField(15),
            phoneField = new JTextField(15),
            passwordField = new JPasswordField(15),
            initialBalanceField = new JTextField(15)
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            panel.add(label, gbc);

            fields[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            if (fields[i] instanceof JComponent) {
                ((JComponent) fields[i]).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            }

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            panel.add(fields[i], gbc);
        }

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setBackground(new Color(0, 123, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(0, 105, 217));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(0, 123, 255));
            }
        });

        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);
        
        loginRedirectButton = new JButton("Already have an account?");
        loginRedirectButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        loginRedirectButton.setForeground(new Color(0, 123, 255));
        loginRedirectButton.setFocusPainted(false);
        loginRedirectButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginRedirectButton.setContentAreaFilled(false);

        // Hover effect
        loginRedirectButton.addMouseListener(new MouseAdapter() {
            Font originalFont = loginRedirectButton.getFont();
            Font underlinedFont = originalFont.deriveFont(Map.of(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON));

            @Override
            public void mouseEntered(MouseEvent e) {
                loginRedirectButton.setForeground(new Color(0, 105, 217));
                loginRedirectButton.setFont(underlinedFont);
                loginRedirectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginRedirectButton.setForeground(new Color(0, 123, 255));
                loginRedirectButton.setFont(originalFont);
                loginRedirectButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        gbc.gridy = labels.length + 2;
        panel.add(loginRedirectButton, gbc);

    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getBirthDate() {
        return birthDateField.getText();
    }

    public String getCIN() {
        return cinField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getInitialBalance() {
        return initialBalanceField.getText();
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
    
    public JButton getLoginRedirectButton() {
        return loginRedirectButton;
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

}
