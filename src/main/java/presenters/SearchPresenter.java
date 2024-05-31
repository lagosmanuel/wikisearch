package presenters;

import models.*;
import utils.UIStrings;
import views.SearchView;

import java.util.*;

public class SearchPresenter {
    private SearchView searchView;
    private final SearchModel searchModel;
    private final RetrieveModel retrieveModel;
    private final SaveModel saveModel;

    public SearchPresenter(SearchModel searchModel, RetrieveModel retrieveModel, SaveModel saveModel) {
        this.searchModel = searchModel;
        this.retrieveModel = retrieveModel;
        this.saveModel = saveModel;
        initListeners();
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
        searchView.setSearchPresenter(this);
    }

    private void initListeners() {
        searchModel.addEventListener(() -> {
            Collection<SearchResult> results = searchModel.getLastResults();
            if (!results.isEmpty()) searchView.showOptionsMenu(results);
            else searchView.showMessageDialog(UIStrings.SEARCH_DIALOG_NORESULT);
        });

        retrieveModel.addEventListener(() -> {
            String result = retrieveModel.getLastResult();
            if (!result.isEmpty()) searchView.setResultTextPane(result);
            else searchView.showMessageDialog(UIStrings.RETRIEVE_DIALOG_NOPAGEFOUND);
        });

        saveModel.addEventListener(() -> {
            if (searchView.getComponent().isVisible()) searchView.showMessageDialog(UIStrings.SAVE_DIALOG_SUCCESS);
        });
    }

    public void onSearch() {
        new Thread(() -> {
            searchView.setWorkingStatus();
            String termToSearch = searchView.getSearchText();
            if (!termToSearch.isEmpty()) searchModel.searchTerm(termToSearch);
            else searchView.showMessageDialog(UIStrings.SEARCH_DIALOG_EMPTYTERM);
            searchView.setWaitingStatus();
        }).start();
    }

    public void onRetrieve() {
        new Thread(() -> {
            SearchResult selectedResult = searchView.getSelectedResult();
            if (selectedResult != null) {
                searchView.setWorkingStatus();
                retrieveModel.retrievePage(selectedResult.getPageID());
                searchView.setWaitingStatus();
            } else searchView.showMessageDialog(UIStrings.RETRIEVE_DIALOG_NOSELECTEDITEM);
        }).start();
    }

    public void onSave() {
        new Thread(() -> {
            SearchResult selectedResult = searchView.getSelectedResult();
            if (selectedResult != null) {
                String formatedTitle = selectedResult.getTitle().replace("'", "`");
                saveModel.savePage(formatedTitle, searchView.getResultText());
            } else searchView.showMessageDialog(UIStrings.SAVE_DIALOG_NOSELECTEDITEM);
        }).start();
    }
}
