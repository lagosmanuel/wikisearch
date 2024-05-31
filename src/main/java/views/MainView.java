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
    tabbedPane.addTab(UIStrings.SEARCHVIEW_TAB_TITLE, searchView.getComponent());
    tabbedPane.addTab(UIStrings.STOREDINFOVIEW_TAB_TITLE, storedInfoView.getComponent());
    tabbedPane.addTab(UIStrings.RANKINGVIEW_TAB_TITLE, rankingView.getComponent());
    init();
  }

  private void init() {
    setLookAndFeel();
    JFrame frame = new JFrame(UIStrings.MAINVIEW_WINDOW_TITLE);
    frame.setContentPane(contentPane);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.pack();
    frame.setVisible(true);
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

  private void setLookAndFeel() {
    try {
      // Set System L&F
      UIManager.put("nimbusSelection", new Color(247,248,250));
      //UIManager.put("nimbusBase", new Color(51,98,140)); //This is redundant!

      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }
    catch (Exception e) {
      System.out.println(UIStrings.ERROR_DIALOG_UI);
    }
  }
}
