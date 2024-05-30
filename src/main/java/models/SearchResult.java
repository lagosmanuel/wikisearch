package models;

import javax.swing.*;

public class SearchResult extends JMenuItem {
    protected String title;
    protected String pageID;
    protected String snippet;

    public SearchResult(String title, String pageID, String snippet) {
        String itemText = "<html><font face=\"arial\">" + title + ": " + snippet;
        itemText =itemText.replace("<span class=\"searchmatch\">", "")
                .replace("</span>", "");
        this.setText(itemText);
        this.title = title;
        this.pageID = pageID;
        this.snippet = snippet;
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
}
