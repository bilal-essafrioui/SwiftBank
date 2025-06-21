package view;

import javax.swing.*;
import java.awt.*;

public class AddBeneficiaryView extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField ribField;
    private JButton submitButton;

    public AddBeneficiaryView() {
        setTitle("Add Beneficiary");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 300);
        setLocationRelativeTo(null);
        setResizable(false);
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

        JLabel header = new JLabel("Add a New Beneficiary");
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); // réduit l'espace vertical
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5); // espacement vertical équilibré
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel fnLabel = new JLabel("First Name:");
        fnLabel.setFont(labelFont);
        formPanel.add(fnLabel, gbc);

        gbc.gridx = 1;
        firstNameField = new JTextField(15);
        firstNameField.setFont(fieldFont);
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lnLabel = new JLabel("Last Name:");
        lnLabel.setFont(labelFont);
        formPanel.add(lnLabel, gbc);

        gbc.gridx = 1;
        lastNameField = new JTextField(15);
        lastNameField.setFont(fieldFont);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel ribLabel = new JLabel("RIB:");
        ribLabel.setFont(labelFont);
        formPanel.add(ribLabel, gbc);

        gbc.gridx = 1;
        ribField = new JTextField(15);
        ribField.setFont(fieldFont);
        formPanel.add(ribField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 10, 5); // ajoute de l'espace au-dessus du bouton
        submitButton = new JButton("Add Beneficiary");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        submitButton.setBackground(new Color(33, 150, 243));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formPanel.add(submitButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    public String getFirstNameField() { return firstNameField.getText(); }
    public String getLastNameField() { return lastNameField.getText(); }
    public String getRibField() { return ribField.getText(); }
    public JButton getSubmitButton() { return submitButton; }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
