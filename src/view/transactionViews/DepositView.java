package view.transactionViews;

import javax.swing.*;
import java.awt.*;

public class DepositView extends JFrame {

    private JTextField amountField;
    private JTextField descriptionField;
    private JButton depositButton;

    public DepositView() {
        setTitle("Deposit");
        setSize(400, 240);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 15, 15)
        ));

        JLabel header = new JLabel("Make a Deposit");
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel amountLabel = new JLabel("Deposit Amount:");
        amountLabel.setFont(labelFont);
        formPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        amountField = new JTextField(15);
        amountField.setFont(fieldFont);
        formPanel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        formPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField(15);
        descriptionField.setFont(fieldFont);
        formPanel.add(descriptionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 10, 5);
        depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        depositButton.setBackground(new Color(33, 150, 243));
        depositButton.setForeground(Color.WHITE);
        depositButton.setFocusPainted(false);
        depositButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formPanel.add(depositButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    public String getAmount() { return amountField.getText(); }
    public String getDescription() { return descriptionField.getText(); }
    public JButton getDepositButton() { return depositButton; }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}