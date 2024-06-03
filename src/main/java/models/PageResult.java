package models;

public class PageResult {
    private final String title;
    private final int pageID;
    private String extract;
    private final int source;

    public PageResult(String title, int pageID, String extract, int source) {
        this.title = title;
        this.pageID = pageID;
        this.extract = extract;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public int getPageID() {
        return pageID;
    }

    public String getExtract() {
        return extract;
    }

    public int getSource() {
        return source;
    }

    public PageResult setExtract(String extract) {
        this.extract = extract;
        return this;
    }
}
