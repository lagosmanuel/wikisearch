package views.components;

import models.EventListener;
import utils.UIStrings;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class StarsPanel extends JPanel {
    private int lastScore;
    private int newScore;
    EventListener listener;
    List<JButton> starButtons;
    JButton deleteScoreButton;

    public StarsPanel() {
        init();
    }

    public void setEventListener(EventListener listener) {
        this.listener = listener;
    }

    public void setScore(int score) {
        lastScore = score;
        changeScore(score);
    }

    public int getSelectedScore() {
        return newScore;
    }

    public void init() {
        SwingUtilities.invokeLater(() -> {
            createStarButtons();
            createDeleteButton();
        });
    }

    private void createStarButtons() {
        starButtons = new ArrayList<>();
        for (int i = 0; i < UIStrings.SCORE_MAXSCORE; ++i) {
            JButton starButton = createStarButton();
            starButton.addMouseListener(createStarButtonMouseAdapter(i+1));
            starButtons.add(starButton);
            this.add(starButton);
        }
    }

    private JButton createStarButton() {
        JButton starButton = new JButton(UIStrings.STAR_CHAR_EMPTY);
        starButton.setFont(new Font(UIStrings.STAR_FONT_FAMILY, Font.PLAIN, UIStrings.STAR_FONT_SIZE));
        starButton.setBorder(new EmptyBorder(0,0,0,0));
        starButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        starButton.setForeground(UIStrings.STAR_COLOR);
        starButton.setContentAreaFilled(false);
        starButton.setPreferredSize(new Dimension(UIStrings.STAR_FONT_SIZE - 10, UIStrings.STAR_FONT_SIZE));
        return starButton;
    }

    private MouseAdapter createStarButtonMouseAdapter(int score) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                changeScore(score);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                changeScore(lastScore);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                listener.onEvent();
            }
        };
    }

    private void createDeleteButton() {
        deleteScoreButton = new JButton(UIStrings.STAR_CHAR_DELETE);
        deleteScoreButton.setFont(new Font(UIStrings.STAR_FONT_FAMILY, Font.PLAIN, UIStrings.STAR_FONT_SIZE));
        deleteScoreButton.setBorder(new EmptyBorder(0,0,0,0));
        deleteScoreButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteScoreButton.setContentAreaFilled(false);
        deleteScoreButton.setPreferredSize(new Dimension(UIStrings.STAR_FONT_SIZE, UIStrings.STAR_FONT_SIZE));

        deleteScoreButton.addActionListener(actionEvent -> {
            changeScore(0);
            listener.onEvent();
        });

        this.add(deleteScoreButton);
    }

    private void changeScore(int score) {
        for (int i = 0; i < starButtons.size(); ++i)
            starButtons.get(i).setText(i<score? UIStrings.STAR_CHAR_FULL:UIStrings.STAR_CHAR_EMPTY);
        newScore = score;
    }
}
