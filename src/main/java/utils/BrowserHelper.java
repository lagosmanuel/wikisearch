package utils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class BrowserHelper {
    public static void openLinkInBrowser(URL url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(url.toURI());
            } else if (System.getProperty("os.name").toLowerCase().contains("linux")){
                Runtime.getRuntime().exec(new String[]{"xdg-open", url.toString()});
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    UIStrings.SEARCH_DIALOG_NODESKTOP,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (IOException | URISyntaxException e) {throw new RuntimeException(e);}
    }
}
