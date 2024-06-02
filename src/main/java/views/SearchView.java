package views;

import models.SearchResult;
import presenters.SearchPresenter;
import utils.UIStrings;
import views.components.SearchResultItem;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class SearchView {
    private JTextField searchTextField;
    private JButton searchButton;
    private JTextPane resultTextPane;
    private JButton saveLocallyButton;
    private JPanel contentPane;
    private JScrollPane resultScrollPane;

    private SearchPresenter searchPresenter;
    private SearchResult selectedResult;

    public SearchView() {
        resultTextPane.setContentType("text/html");
        searchButton.setText(UIStrings.SEARCHVIEW_SEARCHBUTTON_TEXT);
        saveLocallyButton.setText(UIStrings.SEARCHVIEW_SAVELOCALLYBUTTON_TEXT);
        initListeners();
    }

    private void initListeners() {
        searchButton.addActionListener(actionEvent -> {
            searchPresenter.onSearchTerm();
        });

        saveLocallyButton.addActionListener(actionEvent -> {
            searchPresenter.onSavePage();
        });
    }

    public Component getComponent() {
        return contentPane;
    }

    public void setSearchPresenter(SearchPresenter presenter) {
        searchPresenter = presenter;
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

    public void setResultTextPane(String text) {
        resultTextPane.setText(text);
        resultTextPane.setCaretPosition(0);
    }

    public void showOptionsMenu(Collection<SearchResult> results) {
        JPopupMenu searchOptionsMenu = new JPopupMenu(UIStrings.SEARCHVIEW_POPUP_LABEL);
        for (SearchResult result : results) {
            searchOptionsMenu.add(new SearchResultItem(result)).addActionListener(actionEvent -> {
                selectedResult = result;
                searchPresenter.onRetrievePage();
            });
        }
        searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY()+searchTextField.getHeight());
    }

    public void setWorkingStatus() {
        for(Component c: this.contentPane.getComponents()) c.setEnabled(false);
        resultTextPane.setEnabled(false);
    }

    public void setWaitingStatus() {
        for(Component c: this.contentPane.getComponents()) c.setEnabled(true);
        resultTextPane.setEnabled(true);
    }

    public void showMessageDialog(String msg) {
        JOptionPane.showMessageDialog(contentPane, msg);
    }
}
