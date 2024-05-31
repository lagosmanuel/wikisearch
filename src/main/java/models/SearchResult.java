package models;

import utils.ParserHTML;

import javax.swing.*;

public class SearchResult extends JMenuItem {
    private final String title;
    private final String pageID;
    private final String snippet;
    private int score;

    public SearchResult(String title, String pageID, String snippet) {
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
        updateText();
    }

    public String getTitle() {
        return title;
    }

    public String getPageID() {
        return pageID;
    }

    public String getSnippet() {
        return snippet;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        updateText();
    }

    private void updateText() {
        this.setText(ParserHTML.searchResultToHtml(this));
    }
}
