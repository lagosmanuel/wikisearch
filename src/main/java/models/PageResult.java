package models;

public class PageResult {
    private final String title;
    private final int pageID;
    private String extract;
    private final int source;
    private byte[] thumbnail;

    public PageResult(String title, int pageID, String extract, int source, byte[] thumbnail) {
        this.title = title;
        this.pageID = pageID;
        this.extract = extract;
        this.source = source;
        this.thumbnail = thumbnail;
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

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public PageResult setExtract(String extract) {
        this.extract = extract;
        return this;
    }
}
