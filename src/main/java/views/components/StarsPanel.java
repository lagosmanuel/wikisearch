package views.components;

import models.EventListener;
import utils.UIStrings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StarsPanel extends JPanel {
    private int lastScore;
    private int newScore;
    EventListener listener;

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
        for (int i = 0; i < UIStrings.SCORE_MAXSCORE; ++i) {
            JButton button = new JButton(i<lastScore? UIStrings.STAR_CHAR_FULL:UIStrings.STAR_CHAR_EMPTY);
            button.setFont(new Font(UIStrings.STAR_FONT_FAMILY, Font.PLAIN, UIStrings.STAR_FONT_SIZE));
            button.setBorder(new EmptyBorder(0,0,0,0));
            button.setForeground(UIStrings.STAR_COLOR);
            button.setContentAreaFilled(false);
            int aux = i+1;

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    changeScore(aux);
                    button.setForeground(UIStrings.STAR_ALTCOLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    changeScore(lastScore);
                    button.setForeground(UIStrings.STAR_COLOR);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    button.setForeground(UIStrings.STAR_COLOR);
                    listener.onEvent();
                }
            });

            this.add(button);
        }
    }

    private void changeScore(int score) {
        for (int i = 0; i < this.getComponentCount(); ++i) {
            JButton button = (JButton) this.getComponent(i);
            button.setText(i<score? UIStrings.STAR_CHAR_FULL:UIStrings.STAR_CHAR_EMPTY);
        }
        newScore = score;
    }
}
