package views;
import models.SearchResult;
import presenters.RankingPresenter;
import utils.UIStrings;
import views.components.StarsPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;

public class RankingView {
    private RankingPresenter rankingPresenter;
    private JPanel contentPane;
    private JButton searchButton;
    private JScrollPane scrollPane;
    private JList<SearchResult> rankingList;
    private final StarsPanel starsPanel;
    private final JDialog starsPanelDialog;

    public RankingView() {
        starsPanel = new StarsPanel();
        starsPanelDialog = new JDialog();
        init();
    }

    private void init() {
        starsPanelDialog.setTitle(UIStrings.RANKINGVIEW_STARSPANEL_TITLE);
        starsPanelDialog.setIconImage(new ImageIcon(UIStrings.IMAGEICON_PATH).getImage());
        starsPanelDialog.setLocationRelativeTo(contentPane);
        starsPanelDialog.setContentPane(starsPanel);
        starsPanelDialog.setResizable(false);
        starsPanelDialog.pack();
        searchButton.setText(UIStrings.RANKINGVIEW_SEARCHBUTTON_LABEL);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setFont(UIStrings.DEFAULT_FONT);
        initListeners();
    }

    private void initListeners() {
        starsPanel.setEventListener(() -> {
            rankingPresenter.onChangedScore();
            starsPanelDialog.setVisible(false);
        });
        searchButton.addActionListener(actionEvent -> rankingPresenter.onSearch());
        rankingList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == UIStrings.RANKINGVIEW_SEARCHBUTTON_KEY) starsPanelDialog.setVisible(true);
            }
        });
    }

    public Component getComponent() {
        return contentPane;
    }

    public void setRankingPresenter(RankingPresenter presenter) {
        rankingPresenter = presenter;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void updateRankingList(Collection<SearchResult> results) {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel listModel = new DefaultListModel<SearchResult>();
            for (SearchResult result:results) listModel.addElement(result);
            rankingList.setModel(listModel);
        });
    }

    public boolean isItemSelected() {
        return rankingList.getSelectedIndex() != -1;
    }

    public SearchResult getSelectedResult() {
        return rankingList.getSelectedValue();
    }

    public int getSelectedScore() {
        return starsPanel.getSelectedScore();
    }

    public void showDialog(String dialog) {
        JOptionPane.showMessageDialog(contentPane, dialog);
    }

    public JList<SearchResult> getRankingList() {
        return rankingList;
    }

    public void pressSearchButton() {
        searchButton.doClick();
    }
}
