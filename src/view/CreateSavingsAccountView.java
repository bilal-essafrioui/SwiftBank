package view;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class CreateSavingsAccountView extends JFrame {

    private JTextField balanceField;
    private JTextField descriptionField;
    private JButton createButton;
    private JButton cancelButton;

    public CreateSavingsAccountView() {
        setTitle("Create Savings Account");
        setSize(350, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel balanceLabel = new JLabel("Initial Balance:");
        balanceField = new JTextField(15);
        
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(15);

        createButton = new JButton("Create");
        cancelButton = new JButton("Cancel");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(balanceLabel, gbc);

        gbc.gridx = 1;
        panel.add(balanceField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        panel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createButton, gbc);

        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        add(panel);
    }

    public String getBalanceInput() {
        return balanceField.getText();
    }
    
    public String getDescriptionInput() {
        return descriptionField.getText();
    }
    
    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    public static void main(String[] args) {
		new CreateSavingsAccountView();
	}
}
