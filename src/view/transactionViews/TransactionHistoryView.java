package view.transactionViews;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TransactionHistoryView extends JFrame {
    private JTable transactionTable;
    private JComboBox<String> typeComboBox;
    
    // Modern color palette
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color HEADER_COLOR = new Color(33, 150, 243);
    private static final Color PANEL_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    private static final Color TEXT_COLOR = new Color(45, 45, 45);
    private static final Color POSITIVE_AMOUNT = new Color(34, 139, 34);
    private static final Color NEGATIVE_AMOUNT = new Color(220, 20, 60);
    private static final Color ALTERNATE_ROW = new Color(248, 248, 248);

    public TransactionHistoryView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Transaction History");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Set modern font with better size
        Font modernFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 13);
        UIManager.put("Table.font", modernFont);
        
        // Create title panel
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
        // Create filter panel
        JPanel filterPanel = createFilterPanel();
        add(filterPanel, BorderLayout.NORTH);
        
        // Create table panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
    }
    
    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(HEADER_COLOR);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Transaction History");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        return titlePanel;
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(PANEL_COLOR);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setBackground(PANEL_COLOR);
        
        JLabel filterLabel = new JLabel("Filter by type:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filterLabel.setForeground(TEXT_COLOR);
        filterLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        
        typeComboBox = new JComboBox<>(new String[]{"ALL", "DEPOSIT", "TRANSFER", "WITHDRAWAL"});
        typeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        typeComboBox.setPreferredSize(new Dimension(150, 30));
        typeComboBox.setBackground(Color.WHITE);
        typeComboBox.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        
        leftPanel.add(filterLabel);
        leftPanel.add(typeComboBox);
        filterPanel.add(leftPanel, BorderLayout.WEST);
        
        return filterPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(PANEL_COLOR);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        transactionTable = new JTable(new DefaultTableModel(
            new Object[]{"Date", "Type", "Amount", "Status", "Description", "Full Name", "RIB"}, 0
        ));
        
        // Style the table
        styleTable();
        
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }
    
    private void styleTable() {
        transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        transactionTable.setRowHeight(38);
        transactionTable.setShowGrid(false);
        transactionTable.setIntercellSpacing(new Dimension(0, 0));
        transactionTable.setSelectionBackground(new Color(230, 240, 255));
        transactionTable.setSelectionForeground(TEXT_COLOR);
        transactionTable.setBackground(Color.WHITE);
        transactionTable.setForeground(TEXT_COLOR);
        
        // Style header
        JTableHeader header = transactionTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, HEADER_COLOR));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 42));
        
        // Set custom renderers
        transactionTable.getColumnModel().getColumn(2).setCellRenderer(new AmountCellRenderer());
        transactionTable.getColumnModel().getColumn(1).setCellRenderer(new TypeCellRenderer());
        transactionTable.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer());
        
        // Set default renderer for alternating rows
        transactionTable.setDefaultRenderer(Object.class, new AlternatingRowRenderer());
        
        // Set column widths
        transactionTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Date
        transactionTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Type
        transactionTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Amount
        transactionTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Status
        transactionTable.getColumnModel().getColumn(4).setPreferredWidth(200); // Description
        transactionTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Full Name
        transactionTable.getColumnModel().getColumn(6).setPreferredWidth(120); // RIB
    }

    public JComboBox<String> getTypeComboBox() {
        return typeComboBox;
    }

    public void updateTableData(java.util.List<String[]> history, int idAccount) {
        DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
        model.setRowCount(0);
        for (String[] row : history) {
            String displayAmount = row[1].equalsIgnoreCase("DEPOSIT") 
                    || (row[1].equalsIgnoreCase("TRANSFER") && row[8].equalsIgnoreCase(String.valueOf(idAccount))) ?
                "+ " + row[2] : "- " + row[2];
            model.addRow(new Object[]{row[0], row[1], displayAmount, row[3], row[4], row[5], row[6]});
        }
    }

    // Custom renderer for alternating row colors
    private static class AlternatingRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                } else {
                    c.setBackground(ALTERNATE_ROW);
                }
            }
            return c;
        }
    }

    // Custom renderer for amount column
    private static class AmountCellRenderer extends DefaultTableCellRenderer {
        public AmountCellRenderer() {
            setHorizontalAlignment(CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            
            if (!isSelected) {
                if (row % 2 == 0) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(ALTERNATE_ROW);
                }
                
                if (value != null && value.toString().startsWith("+")) {
                    setForeground(POSITIVE_AMOUNT);
                } else {
                    setForeground(NEGATIVE_AMOUNT);
                }
            }
            
            return c;
        }
    }
    
    // Custom renderer for type column
    private static class TypeCellRenderer extends DefaultTableCellRenderer {
        public TypeCellRenderer() {
            setHorizontalAlignment(CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                if (row % 2 == 0) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(ALTERNATE_ROW);
                }
            }
            
            return c;
        }
    }
    
    // Custom renderer for status column
    private static class StatusCellRenderer extends DefaultTableCellRenderer {
        public StatusCellRenderer() {
            setHorizontalAlignment(CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                if (row % 2 == 0) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(ALTERNATE_ROW);
                }
                
                // Color code status
                if (value != null) {
                    String status = value.toString().toUpperCase();
                    if (status.contains("SUCCESS") || status.contains("COMPLETED")) {
                        setForeground(POSITIVE_AMOUNT);
                    } else if (status.contains("FAILED") || status.contains("ERROR")) {
                        setForeground(NEGATIVE_AMOUNT);
                    } else {
                        setForeground(TEXT_COLOR);
                    }
                }
            }
            
            return c;
        }
    }
}