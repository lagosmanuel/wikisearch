package views;

import com.formdev.flatlaf.FlatDarkLaf;
import utils.UIStrings;

import java.awt.*;
import javax.swing.*;

public class MainView {
    private JPanel contentPane;
    private JTabbedPane tabbedPane;

    private final SearchView searchView;
    private final StoredInfoView storedInfoView;
    private final RankingView rankingView;

    public MainView() {
        setLookAndFeel();
        searchView = new SearchView();
        storedInfoView = new StoredInfoView();
        rankingView = new RankingView();
        tabbedPane.setFont(UIStrings.DEFAULT_FONT);
        setupFrameWindow();
        insertTabs();
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public StoredInfoView getStoredInfoView() {
        return storedInfoView;
    }

    public RankingView getRankingView() {
        return rankingView;
    }

    public void changeTab(int index) {
        tabbedPane.setSelectedIndex(index);
    }

    public int getTabCount() {
        return tabbedPane.getTabCount();
    }

    private void setupFrameWindow() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(UIStrings.MAINVIEW_WINDOW_TITLE);
            frame.setIconImage(new ImageIcon(UIStrings.IMAGEICON_PATH).getImage());
            frame.setContentPane(contentPane);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setResizable(true);
            frame.pack();
            frame.setSize(UIStrings.MAINVIEW_WINDOW_WIDTH, UIStrings.MAINVIEW_WINDOW_HEIGHT);
            centerComponentOnScreen(frame);
            frame.setVisible(true);
        });
    }

    private void centerComponentOnScreen(Component component) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - component.getWidth()) / 2;
        int y = (screenSize.height - component.getHeight()) / 2;
        component.setLocation(x, y);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(contentPane);
        } catch (Exception e) {System.out.println(UIStrings.ERROR_DIALOG_UI);}
    }

    private void insertTabs() {
        SwingUtilities.invokeLater(() -> {
            tabbedPane.insertTab(
                    UIStrings.SEARCHVIEW_TAB_TITLE,
                    null,
                    searchView.getComponent(),
                    null,
                    UIStrings.SEARCHVIEW_TAB_INDEX
            );
            tabbedPane.insertTab(
                    UIStrings.STOREDINFOVIEW_TAB_TITLE,
                    null,
                    storedInfoView.getComponent(),
                    null,
                    UIStrings.STOREDINFOVIEW_TAB_INDEX
            );
            tabbedPane.insertTab(
                    UIStrings.RANKINGVIEW_TAB_TITLE,
                    null,
                    rankingView.getComponent(),
                    null,
                    UIStrings.RANKINGVIEW_TAB_INDEX
            );
        });
    }
}
