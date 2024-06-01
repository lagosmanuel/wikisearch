package views;

import models.EventListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StarsView extends JPanel {
    private int score;
    EventListener listener;

    public StarsView() {
        init();
    }

    public void setEventListener(EventListener listener) {
        this.listener = listener;
    }

    public void setScore(int score) {
        this.score = score;
        refreshStars(score);
    }

    public int getScore() {
        return score;
    }

    public void init() {
        for (int i = 0; i < 10; ++i) {
            JButton button = new JButton(i<score?"\u2605":"\u2606");
            button.setFont(new Font("Arial", Font.PLAIN, 40));
            button.setBorder(new EmptyBorder(0,0,0,0));
            button.setForeground(Color.ORANGE);
            button.setContentAreaFilled(false);

            int newScore = i+1;

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    refreshStars(newScore);
                    button.setForeground(Color.RED);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    refreshStars(score);
                    button.setForeground(Color.ORANGE);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    setScore(newScore);
                    button.setForeground(Color.ORANGE);
                    listener.onEvent();
                }
            });

            this.add(button);
        }
    }

    private void refreshStars(int score) {
        for (int i = 0; i < this.getComponentCount(); ++i) {
            JButton button = (JButton) this.getComponent(i);
            button.setText(i<score?"\u2605":"\u2606");
        }
    }
}
