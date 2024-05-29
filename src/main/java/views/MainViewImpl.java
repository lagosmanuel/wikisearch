package views;

import java.awt.*;
import javax.swing.*;
import models.DataBase;

public class MainViewImpl implements MainView {
  private JPanel contentPane;
  private JTabbedPane tabbedPane;

  private SearchView searchView;
  private StoredInfoView storedInfoView;

  public MainViewImpl() {
    searchView = new SearchViewImpl();
    storedInfoView = new StoredInfoViewImpl();

    tabbedPane.addTab("search", searchView.getComponent());
    tabbedPane.addTab("storedInfo", storedInfoView.getComponent());
  }

  public void init() {
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
      System.out.println("Something went wrong with UI!");
    }

    JFrame frame = new JFrame("TV Series Info Repo");

    frame.setContentPane(new MainViewImpl().contentPane);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    DataBase.loadDatabase();
    DataBase.saveInfo("test", "sarasa");

    System.out.println(DataBase.getExtract("test"));
    System.out.println(DataBase.getExtract("nada"));
  }

  @Override
  public SearchView getSearchView() {
    return searchView;
  }

  @Override
  public StoredInfoView getStoredInfoView() {
    return storedInfoView;
  }
}
