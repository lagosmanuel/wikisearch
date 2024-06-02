package views;

import presenters.RankingPresenter;
import views.components.StarsPanel;

import javax.swing.*;
import java.awt.*;

public class RankingView {
    private RankingPresenter rankingPresenter;
    private JPanel contentPane;
    private JComboBox comboBox;
    private JButton searchButton;
    private JScrollPane descriptionScrollPane;
    private JTextPane descriptionTextPane;
    private JLabel titleLabel;
    private JLabel lastEditedLabel;
    private JPanel scorePanel;
    private final StarsPanel starsPanel;

    public RankingView() {
        starsPanel = new StarsPanel();
        scorePanel.add(starsPanel);
        initListeners();
    }

    private void initListeners() {
        comboBox.addActionListener(actionEvent -> {
            rankingPresenter.onSelectedEntry();
        });

        starsPanel.setEventListener(() -> {
            rankingPresenter.onChangedScore();
        });
    }

    public Component getComponent() {
        return contentPane;
    }

    public void setRankingPresenter(RankingPresenter presenter) {
        rankingPresenter = presenter;
    }

    public void updateComboBox(Object[] results) {
        comboBox.setModel(new DefaultComboBoxModel(results));
    }

    public String getSelectedEntry() {
        return comboBox.getSelectedItem().toString();
    }

    public boolean isItemSelected() {
        return comboBox.getSelectedIndex() > -1;
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setDescription(String text) {
        descriptionTextPane.setText(text);
    }

    public void setLastModified(String lastEdited) {
        lastEditedLabel.setText("last modified: " + lastEdited);
    }

    public void setScore(int score) {
        starsPanel.setScore(score);
    }

    public int getScore() {
        return starsPanel.getSelectedScore();
    }
}
