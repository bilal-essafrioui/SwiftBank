package app;

import javax.swing.JOptionPane;
import controller.ClientController;

public class MainApp {
    public static void main(String[] args) {
        try {
            util.UIStyle.applyGlobalStyle();
            ClientController controller = new ClientController();
            controller.startLoginFlow();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Internal Error: Unable to connect to the database.",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
    }
}
