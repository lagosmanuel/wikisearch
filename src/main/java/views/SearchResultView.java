package views;

import models.SearchResult;
import utils.ParserHTML;

import javax.swing.*;

public class SearchResultView extends JMenuItem {
    public SearchResultView(SearchResult result) {
        this.setText(ParserHTML.searchResultToHtml(result));
    }
}
