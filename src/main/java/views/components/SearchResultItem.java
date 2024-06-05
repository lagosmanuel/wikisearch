package views.components;

import models.SearchResult;
import javax.swing.*;

public class SearchResultItem extends JMenuItem {
    public SearchResultItem(SearchResult searchResult) {
        this.setText(searchResult.toString());
    }
}
