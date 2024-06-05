package views;

import models.SearchResult;
import presenters.SearchPresenter;
import utils.ImagesCache;
import utils.ParserHTML;
import utils.UIStrings;
import views.components.SearchResultItem;
import views.components.StarsPanel;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

public class SearchView {
    private JTextField searchTextField;
    private JButton searchButton;
    private JTextPane resultTextPane;
    private JButton saveLocallyButton;
    private JPanel contentPane;
    private JScrollPane resultScrollPane;
    private JPanel scorePanel;
    private final StarsPanel starsPanel;

    private SearchPresenter searchPresenter;
    private SearchResult selectedResult;

    public SearchView() {
        starsPanel = new StarsPanel();
        init();
    }

    private void init() {
        searchButton.setText(UIStrings.SEARCHVIEW_SEARCHBUTTON_TEXT);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setFont(UIStrings.DEFAULT_FONT);
        saveLocallyButton.setText(UIStrings.SEARCHVIEW_SAVELOCALLYBUTTON_TEXT);
        saveLocallyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveLocallyButton.setFont(UIStrings.DEFAULT_FONT);
        resultTextPane.setContentType("text/html");
        resultTextPane.setEditable(false);
        resultTextPane.getDocument().putProperty("imageCache", ImagesCache.getInstance().getCache());
        ((HTMLEditorKit) resultTextPane.getEditorKit()).getStyleSheet().addRule(ParserHTML.getStyleSheet());
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
        saveLocallyButton.addActionListener(actionEvent -> searchPresenter.onSavePage());
        starsPanel.setEventListener(() -> searchPresenter.onChangedScore());
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == UIStrings.SEARCHVIEW_SEARCHBUTTON_KEY)
                    searchPresenter.onSearchTerm();
            }
        });
        resultTextPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) openLinkInBrowser(e.getURL());
        });
    }

    public String getSearchText() {
        return searchTextField.getText();
    }

    public String getResultText() {
        return resultTextPane.getText();
    }

    public SearchResult getSelectedResult() {
        return selectedResult;
    }

    public int getScore() {
        return starsPanel.getSelectedScore();
    }

    public void setScore(int score) {
        starsPanel.setScore(score);
    }

    public void setSelectedResult(SearchResult searchResult) {
        selectedResult = searchResult;
    }

    public void setResultTextPane(String text) {
        resultTextPane.setText(text);
        resultTextPane.setCaretPosition(0);
    }

    public void setWorkingStatus() {
        for(Component c: this.contentPane.getComponents()) c.setEnabled(false);
        resultTextPane.setEnabled(false);
    }

    public void setWaitingStatus() {
        for(Component c: this.contentPane.getComponents()) c.setEnabled(true);
        resultTextPane.setEnabled(true);
    }

    public void clearSearchTextField() {
        searchTextField.setText("");
    }

    public void showMessageDialog(String msg) {
        JOptionPane.showMessageDialog(contentPane, msg);
    }

    public void showOptionsMenu(Collection<SearchResult> results) {
        JPopupMenu searchOptionsMenu = new JPopupMenu(UIStrings.SEARCHVIEW_POPUP_LABEL);
        for (SearchResult result : results)
            searchOptionsMenu.add(new SearchResultItem(result)).addActionListener(actionEvent -> {
                selectedResult = result;
                searchPresenter.onRetrievePage();
            });
        searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY()+searchTextField.getHeight());
    }

    private void openLinkInBrowser(URL url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) Desktop.getDesktop().browse(url.toURI());
            else showMessageDialog(UIStrings.SEARCH_DIALOG_NODESKTOP);
        } catch (IOException | URISyntaxException e) {throw new RuntimeException(e);}
    }
}
