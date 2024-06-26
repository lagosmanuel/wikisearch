package models;

import utils.ParserHTML;

public class SearchResult {
    private final String title;
    private final int pageID;
    private final String snippet;
    private int score;
    private String lastmoddifed;

    public SearchResult(String title, int pageID, String snippet) {
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
    }

    public SearchResult(String title, int pageID, String snippet, int score, String lastmodified) {
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
        this.score = score;
        this.lastmoddifed = lastmodified;
    }

    public String getTitle() {
        return title;
    }

    public int getPageID() {
        return pageID;
    }

    public String getSnippet() {
        return snippet;
    }

    public int getScore() {
        return score;
    }

    public String getLastmodified() {
        return lastmoddifed;
    }

    public SearchResult setScore(int score) {
       this.score = score;
       return this;
    }

    @Override
    public String toString() {
        return ParserHTML.searchResultToHtml(this);
    }
}
