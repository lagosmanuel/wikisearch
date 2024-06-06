package views;

import models.SearchResult;
import presenters.SearchPresenter;
import utils.*;
import views.components.SearchResultItem;
import views.components.StarsPanel;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;

public class SearchView {
    private JTextField searchTextField;
    private JButton searchButton;
    private JTextPane pageTextPane;
    private JButton savepageButton;
    private JPanel contentPane;
    private JScrollPane resultScrollPane;
    private JPanel scorePanel;
    private final StarsPanel starsPanel;
    private Collection<SearchResult> searchResults;
    private SearchResult selectedResult;
    private SearchPresenter searchPresenter;

    public SearchView() {
        starsPanel = new StarsPanel();
        init();
    }

    private void init() {
        searchButton.setText(UIStrings.SEARCHVIEW_SEARCHBUTTON_TEXT);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setFont(UIStrings.DEFAULT_FONT);
        savepageButton.setText(UIStrings.SEARCHVIEW_SAVELOCALLYBUTTON_TEXT);
        savepageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        savepageButton.setFont(UIStrings.DEFAULT_FONT);
        pageTextPane.setContentType("text/html");
        pageTextPane.setEditable(false);
        pageTextPane.getDocument().putProperty("imageCache", ImagesCache.getInstance().getCache());
        searchTextField.setFont(UIStrings.DEFAULT_FONT);
        ((HTMLEditorKit) pageTextPane.getEditorKit()).getStyleSheet().addRule(ParserHTML.getStyleSheet());
        scorePanel.add(starsPanel);
        initListeners();
    }

    public Component getComponent() {
        return contentPane;
    }

    public void setSearchPresenter(SearchPresenter presenter) {
        searchPresenter = presenter;
    }

    private void initListeners() {
        searchButton.addActionListener(actionEvent -> searchPresenter.onSearchTerm());
        savepageButton.addActionListener(actionEvent -> searchPresenter.onSavePage());
        starsPanel.setEventListener(() -> searchPresenter.onChangedScore());
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == UIStrings.SEARCHVIEW_SEARCHBUTTON_KEY) searchPresenter.onSearchTerm();
            }
        });
        pageTextPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) BrowserHelper.openLinkInBrowser(e.getURL());
        });
    }

    public String getSearchText() {
        return searchTextField.getText();
    }

    public String getResultText() {
        return pageTextPane.getText();
    }

    public Collection<SearchResult> getSearchResults() {
        return searchResults;
    }

    public SearchResult getSelectedResult() {
        return selectedResult;
    }

    public int getSelectedScore() {
        return starsPanel.getSelectedScore();
    }

    public void setScore(int score) {
        starsPanel.setScore(score);
    }

    public void setSelectedResult(SearchResult searchResult) {
        selectedResult = searchResult;
    }

    public void setPageTextPane(String text) {
        pageTextPane.setText(text);
        pageTextPane.setCaretPosition(0);
    }

    public void setWorkingStatus() {
        for(Component c: this.contentPane.getComponents()) c.setEnabled(false);
        pageTextPane.setEnabled(false);
    }

    public void setWaitingStatus() {
        for(Component c: this.contentPane.getComponents()) c.setEnabled(true);
        pageTextPane.setEnabled(true);
    }

    public void setSearchTextField(String text) {
        searchTextField.setText(text);
    }

    public void showMessageDialog(String msg) {
        JOptionPane.showMessageDialog(contentPane, msg);
    }

    public void pressSearchButton() {
        searchButton.doClick();
    }

    public StarsPanel getStarsPanel() {
        return starsPanel;
    }

    public void showOptionsMenu(Collection<SearchResult> results) {
        searchResults = results;
        JPopupMenu searchOptionsMenu = new JPopupMenu(UIStrings.SEARCHVIEW_POPUP_LABEL);
        for (SearchResult searchResult : searchResults)
            searchOptionsMenu.add(new SearchResultItem(searchResult)).addActionListener(actionEvent -> {
                selectedResult = searchResult;
                searchPresenter.onRetrievePage();
            });
        searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY()+searchTextField.getHeight());
    }
}