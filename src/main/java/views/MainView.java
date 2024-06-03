package views;

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
        searchView = new SearchView();
        storedInfoView = new StoredInfoView();
        rankingView = new RankingView();
        init();
    }

    private void init() {
        setLookAndFeel();
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

            frame.setContentPane(contentPane);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setSize(UIStrings.MAINVIEW_WINDOW_WIDTH, UIStrings.MAINVIEW_WINDOW_HEIGHT);
            frame.setVisible(true);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - frame.getWidth()) / 2;
            int y = (screenSize.height - frame.getHeight()) / 2;
            frame.setLocation(x, y);
        });
    }

    private void setLookAndFeel() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.put("nimbusSelection", new Color(247,248,250));
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {System.out.println(UIStrings.ERROR_DIALOG_UI);}
        });
    }

    private void insertTabs() {
        SwingUtilities.invokeLater(() -> {
            tabbedPane.insertTab(
                    UIStrings.SEARCHVIEW_TAB_TITLE,
                    null, searchView.getComponent(),
                    null,
                    UIStrings.SEARCHVIEW_TAB_INDEX
            );
            tabbedPane.insertTab(
                    UIStrings.STOREDINFOVIEW_TAB_TITLE,
                    null, storedInfoView.getComponent(),
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
