package controller;

import service.BeneficiaryService;
import view.AddBeneficiaryView;

import model.Account;
import model.Client;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class BeneficiaryController {
    private AddBeneficiaryView addBeneficiaryView;
    private BeneficiaryService beneficiaryService;
    private Client client;           
    private Account account;

    public BeneficiaryController(Client client, Account account) throws SQLException {
        this.addBeneficiaryView = new AddBeneficiaryView();
        this.beneficiaryService = new BeneficiaryService();
        this.client = client;
        this.account = account;
        
        addBeneficiaryView.getSubmitButton().addActionListener(e -> addBeneficiary());
    }

    // 
    private void addBeneficiary() {
    	
        String firstName = addBeneficiaryView.getFirstNameField();
        String lastName = addBeneficiaryView.getLastNameField();
        String rib = addBeneficiaryView.getRibField();
        
        if (firstName.isEmpty() || lastName.isEmpty() || rib.isEmpty()) {
            addBeneficiaryView.showMessage("Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            boolean success = beneficiaryService.addBeneficiary(
                account.getIdAccount(),
                firstName, lastName, rib
            );

            if (success) {
                addBeneficiaryView.showMessage("Beneficiary added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                addBeneficiaryView.showMessage("Failed to add beneficiary.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            addBeneficiaryView.showMessage("Error: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
}
