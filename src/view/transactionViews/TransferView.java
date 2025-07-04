package view.transactionViews;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransferView extends JFrame {

    private JComboBox<String> beneficiaryComboBox;
    private JTextField amountField;
    private JTextField descriptionField;
    private JButton submitButton;
    private JButton cancelButton;

    public TransferView(List<String[]> beneficiaries) {
        setTitle("Transfer Funds");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(460, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        JLabel header = new JLabel("Transfer Funds");
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(labelFont);
        formPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        amountField = new JTextField();
        amountField.setFont(fieldFont);
        amountField.setPreferredSize(new Dimension(280, 30));
        formPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel beneficiaryLabel = new JLabel("Beneficiary:");
        beneficiaryLabel.setFont(labelFont);
        formPanel.add(beneficiaryLabel, gbc);

        gbc.gridx = 1;
        String[] beneficiaryLabels = beneficiaries.stream()
                .map(b -> b[0] + " - " + b[1] + " " + b[2] + " || " + b[3])
                .toArray(String[]::new);

        beneficiaryComboBox = new JComboBox<>(beneficiaryLabels);
        beneficiaryComboBox.setFont(fieldFont);
        beneficiaryComboBox.setPreferredSize(new Dimension(280, 30));

        beneficiaryComboBox.setRenderer(new DefaultListCellRenderer() {
            private final Color selectionBackground = new Color(33, 150, 243);
            private final Color selectionForeground = Color.WHITE;
            private final Color background = Color.WHITE;
            private final Color foreground = new Color(50, 50, 50);

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                
            	if (value == null) {
                    JLabel label = new JLabel("No beneficiaries available");
                    label.setOpaque(true);
                    label.setBackground(background);
                    label.setForeground(foreground);
                    label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    return label;
                }
            	
            	String fullText = (String) value;
                String[] parts = fullText.split("\\|\\|");
                String line1 = parts[0].trim();
                String line2 = parts.length > 1 ? parts[1].trim() : "";

                String display = (index == -1) ? line1 : line1 + " - " + line2;

                JLabel label = (JLabel) super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
                label.setOpaque(true);

                if (index == -1) {
                    label.setBackground(background);
                    label.setForeground(foreground);
                } else {
                    
                    label.setBackground(isSelected ? selectionBackground : background);
                    label.setForeground(isSelected ? selectionForeground : foreground);
                }
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });

        formPanel.add(beneficiaryComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        formPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField();
        descriptionField.setFont(fieldFont);
        descriptionField.setPreferredSize(new Dimension(280, 30));
        formPanel.add(descriptionField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        submitButton.setBackground(new Color(33, 150, 243));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelButton.setBackground(new Color(200, 200, 200));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    public String getSelectedBeneficiary() {
        return (String) beneficiaryComboBox.getSelectedItem();
    }

    public String getAmount() {
        return amountField.getText();
    }

    public String getDescription() {
        return descriptionField.getText();
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
