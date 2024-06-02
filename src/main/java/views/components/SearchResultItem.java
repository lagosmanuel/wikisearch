package views.components;

import models.SearchResult;
import utils.ParserHTML;

import javax.swing.*;

public class SearchResultItem extends JMenuItem {
    public SearchResultItem(SearchResult result) {
        this.setText(ParserHTML.searchResultToHtml(result));
    }
}
