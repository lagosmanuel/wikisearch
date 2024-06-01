package views;

import presenters.RankingPresenter;

import javax.swing.*;
import java.awt.*;

public class RankingView {
    private RankingPresenter rankingPresenter;
    private JPanel contentPane;
    private JButton searchButton;
    private JComboBox comboBox;
    private JScrollPane resultScrollPane;
    private JTextPane descriptionTextPane;
    private StarsView starsView;

    public RankingView() {
        starsView = new StarsView();
        initListeners();
    }

    private void initListeners() {
        comboBox.addActionListener(actionEvent -> {
            rankingPresenter.onSelectedEntry();
        });

        starsView.setEventListener(() -> {
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

    public void setScore(int score) {
        starsView.setScore(score);
    }

    public int getScore() {
        return starsView.getScore();
    }

    public boolean isItemSelected() {
        return comboBox.getSelectedIndex() > -1;
    }

    public void setDescription(String text) {
        descriptionTextPane.setText(text);
    }
}
