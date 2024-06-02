package models;

public class PageResult {
    private final String title;
    private final int pageID;
    private final String text;

    public PageResult(String title, int pageID, String text) {
        this.title = title;
        this.pageID = pageID;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public int getPageID() {
        return pageID;
    }

    public String getText() {
        return text;
    }
}
