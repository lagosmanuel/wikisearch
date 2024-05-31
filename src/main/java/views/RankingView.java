package views;

import models.SearchResult;
import presenters.RankingPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RankingView {
    private RankingPresenter rankingPresenter;
    private JPanel contentPane;
    private JMenuBar menu;

    public RankingView() {
        contentPane = new JPanel();
        menu = new JMenuBar();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        contentPane.add(menu);
    }

    public Component getComponent() {
        return contentPane;
    }

    public void setRankingPresenter(RankingPresenter presenter) {
        rankingPresenter = presenter;
    }

    public void addEntry(SearchResult entry) {
        menu.add(entry).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("hola" + entry.getTitle());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
