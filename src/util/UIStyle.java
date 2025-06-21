package util;

import javax.swing.*;
import java.awt.*;

public class UIStyle {
    public static void applyGlobalStyle() {
        UIManager.put("OptionPane.messageForeground", new Color(33, 37, 41));
        UIManager.put("OptionPane.foreground", new Color(33, 37, 41));       
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.background", new Color(0, 123, 255));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.select", new Color(0, 105, 217));
        UIManager.put("Button.focus", new Color(0, 105, 217));
    }
}
