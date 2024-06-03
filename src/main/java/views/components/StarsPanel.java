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
        JButton button = new JButton(UIStrings.STAR_CHAR_EMPTY);
        button.setFont(new Font(UIStrings.STAR_FONT_FAMILY, Font.PLAIN, UIStrings.STAR_FONT_SIZE));
        button.setBorder(new EmptyBorder(0,0,0,0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setForeground(UIStrings.STAR_COLOR);
        button.setContentAreaFilled(false);
        return button;
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
