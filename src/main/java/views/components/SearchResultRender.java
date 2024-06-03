package views.components;

import models.SearchResult;
import utils.ParserHTML;

import javax.swing.*;
import java.awt.*;

public class SearchResultRender extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        SearchResult searchResult = (SearchResult) value;
        setText(ParserHTML.searchResultToHtml(searchResult));
        return this;
    }
}
