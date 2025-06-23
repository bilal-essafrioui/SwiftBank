package view;

import model.Client;
import model.Account;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardView extends JFrame {

    private Client client;
    private Account account;

    private JButton addBeneficiaryBtn;
    private JButton depositBtn;
    private JButton transferBtn;
    private JButton withdrawBtn;
    private JButton transactionHistoryBtn;
    private JButton createSavingsAccountBtn;
    private JButton switchToCheckingAccountBtn;
    private JButton transferToCheckingAccountBtn;
    private JButton logoutBtn;

    public DashboardView(Client client, Account account, String accountType) {
        this.client = client;
        this.account = account;
        initComponents();
        if ("SAVINGS".equals(accountType)) {
            addBeneficiaryBtn.setVisible(false);
            depositBtn.setVisible(false);
            transferBtn.setVisible(false);
            createSavingsAccountBtn.setVisible(false);
            withdrawBtn.setVisible(false);
        } else {
            switchToCheckingAccountBtn.setVisible(false);
            transferToCheckingAccountBtn.setVisible(false);
        }
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Dashboard - CASHAURA");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- SIDEBAR (full height) ----------
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS)); // Changé en BoxLayout pour éviter les espaces vides
        sidebarPanel.setBackground(new Color(245, 245, 245)); // gris clair très doux
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        addBeneficiaryBtn = new JButton("Add Beneficiary");
        depositBtn = new JButton("Make a Deposit");
        withdrawBtn = new JButton("Make a Withdrawal");
        transferBtn = new JButton("Make a Transfer");
        transactionHistoryBtn = new JButton("Transaction History");
        createSavingsAccountBtn = new JButton("Switch To Savings Account");
        switchToCheckingAccountBtn = new JButton("Switch to Checking Account");
        transferToCheckingAccountBtn = new JButton("Transfer to Checking Account");

        
        Font btnFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color btnBgColor = new Color(73, 80, 87); 
        Color btnHoverBg = new Color(108, 117, 125); 
        Color btnFgColor = Color.WHITE;

        JButton[] buttons = {
            addBeneficiaryBtn, depositBtn, withdrawBtn, transferBtn,
            transactionHistoryBtn, createSavingsAccountBtn,
            switchToCheckingAccountBtn, transferToCheckingAccountBtn
        };

        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setBackground(btnBgColor);
            btn.setForeground(btnFgColor);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(220, 38));
            btn.setMaximumSize(new Dimension(220, 38)); 
            btn.setMinimumSize(new Dimension(220, 38));  
            btn.setOpaque(true);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 65, 70)), 
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
            ));

            // Hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(btnHoverBg);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(btnBgColor);
                }
            });
        }

        
        boolean firstButton = true;
        JButton[] orderedButtons = {
            addBeneficiaryBtn, depositBtn, withdrawBtn, transferBtn,
            transactionHistoryBtn, createSavingsAccountBtn,
            transferToCheckingAccountBtn, switchToCheckingAccountBtn
        };
        
        for (JButton btn : orderedButtons) {
            if (btn.isVisible()) {
                if (!firstButton) {
                    sidebarPanel.add(Box.createVerticalStrut(10));
                }
                sidebarPanel.add(btn);
                firstButton = false;
            }
        }
        
        sidebarPanel.add(Box.createVerticalGlue()); 

        add(sidebarPanel, BorderLayout.WEST); 

        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel welcomeLabel = new JLabel("Bonjour Mr " + client.getFirstName() + " " + client.getLastName());
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel dateTimeLabel = new JLabel(new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date()));
        dateTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel leftTop = new JPanel(new GridLayout(2, 1));
        leftTop.setOpaque(false);
        leftTop.add(welcomeLabel);
        leftTop.add(dateTimeLabel);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(220, 53, 69));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);

        topPanel.add(leftTop, BorderLayout.WEST);
        topPanel.add(logoutBtn, BorderLayout.EAST);

        // ---------- SUMMARY CENTERED ----------
        JPanel summaryPanel = new JPanel();
        summaryPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), "Account Summary"));
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setPreferredSize(new Dimension(600, 300));
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 16);

        summaryPanel.add(createInfoLabel("Account Number: ", account.getAccountNumber(), labelFont, valueFont));
        summaryPanel.add(createInfoLabel("Balance: ", account.getBalance() + " MAD", labelFont, valueFont));
        summaryPanel.add(createInfoLabel("Account Type: ", account.getType(), labelFont, valueFont));
        summaryPanel.add(createInfoLabel("Status: ", "Active", labelFont, valueFont));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(summaryPanel);

        // ---------- RIGHT PANEL (top + summary) ----------
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(centerPanel, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);
    }


    private JPanel createInfoLabel(String labelText, String valueText, Font labelFont, Font valueFont) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // <-- Ajout ici pour espacement vertical

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);

        JLabel value = new JLabel(valueText);
        value.setFont(valueFont); 

        panel.add(label);
        panel.add(value);
        return panel;
    }



    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                messageType
        );
    }

    public int showConfirmDialog(String message, String title) {
        return JOptionPane.showConfirmDialog(
                this,
                message,
                title,
                JOptionPane.YES_NO_OPTION
        );
    }

    public JButton getAddBeneficiaryBtn() { return addBeneficiaryBtn; }
    public JButton getDepositBtn() { return depositBtn; }
    public JButton getWithdrawBtn() { return withdrawBtn; }
    public JButton getTransferBtn() { return transferBtn; }
    public JButton getTransactionHistoryBtn() { return transactionHistoryBtn; }
    public JButton getCreateSavingsAccountBtn() { return createSavingsAccountBtn; }
    public JButton getSwitchToCheckingAccountBtn() { return switchToCheckingAccountBtn; }
    public JButton getTransferToCheckingAccountBtn() { return transferToCheckingAccountBtn; }
    public JButton getLogoutBtn() { return logoutBtn; }
}