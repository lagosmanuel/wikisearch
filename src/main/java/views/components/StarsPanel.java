package views.components;

import models.EventListener;

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
        for (int i = 0; i < 10; ++i) {
            JButton button = new JButton(i<lastScore?"\u2605":"\u2606");
            button.setFont(new Font("Arial", Font.PLAIN, 40));
            button.setBorder(new EmptyBorder(0,0,0,0));
            button.setForeground(Color.ORANGE);
            button.setContentAreaFilled(false);

            int aux = i+1;

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    changeScore(aux);
                    button.setForeground(Color.RED);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    changeScore(lastScore);
                    button.setForeground(Color.ORANGE);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    button.setForeground(Color.ORANGE);
                    listener.onEvent();
                }
            });

            this.add(button);
        }
    }

    private void changeScore(int score) {
        for (int i = 0; i < this.getComponentCount(); ++i) {
            JButton button = (JButton) this.getComponent(i);
            button.setText(i<score?"\u2605":"\u2606");
        }
        newScore = score;
    }
}
