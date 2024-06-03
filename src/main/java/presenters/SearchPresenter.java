package presenters;

import models.*;
import models.search.UpdateSearchResultsModel;
import models.pages.RetrievePageModel;
import models.pages.SavePageModel;
import models.search.SearchTermModel;
import utils.UIStrings;
import views.SearchView;

import java.util.*;

import static utils.ParserHTML.textToHtml;

public class SearchPresenter {
    private SearchView searchView;
    private final SearchTermModel searchTermModel;
    private final RetrievePageModel retrievePageModel;
    private final SavePageModel savePageModel;
    private final UpdateSearchResultsModel updateSearchResultsModel;
    private PageResult lastPageResult;

    public SearchPresenter(SearchTermModel searchTermModel, RetrievePageModel retrievePageModel, SavePageModel savePageModel, UpdateSearchResultsModel updateSearchResultsModel) {
        this.searchTermModel = searchTermModel;
        this.retrievePageModel = retrievePageModel;
        this.savePageModel = savePageModel;
        this.updateSearchResultsModel = updateSearchResultsModel;
        initListeners();
    }

    public void setSearchView(SearchView view) {
        searchView = view;
        searchView.setSearchPresenter(this);
    }

    private void initListeners() {
        searchTermModel.addEventListener(() -> {
            Collection<SearchResult> searchResults = searchTermModel.getLastSearchResults();
            if (!searchResults.isEmpty()) searchView.showOptionsMenu(searchResults);
            else searchView.showMessageDialog(UIStrings.SEARCH_DIALOG_NORESULT);
        });

        retrievePageModel.addEventListener(() -> {
            lastPageResult = formatPageResult(retrievePageModel.getLastResult());
            searchView.setResultTextPane(lastPageResult.getExtract());
            searchView.setScore(searchView.getSelectedResult().getScore());
        });

        savePageModel.addEventListener(() -> {
            if (searchView.getComponent().isVisible()) searchView.showMessageDialog(UIStrings.SAVE_DIALOG_SUCCESS);
        });

        updateSearchResultsModel.addEventListener(() -> {
            SearchResult selectedSearchResult = searchView.getSelectedResult();
            SearchResult lastUpdatedSearchResult = updateSearchResultsModel.getLastUpdatedSearchResult();
            if (selectedSearchResult != null && selectedSearchResult.getTitle().equals(lastUpdatedSearchResult.getTitle()))
                searchView.setScore(lastUpdatedSearchResult.getScore());
        });
    }

    public void onSearchTerm() {
        new Thread(() -> {
            searchView.setWorkingStatus();
            String termToSearch = searchView.getSearchText();
            if (!termToSearch.isEmpty()) searchTermModel.searchTerm(termToSearch);
            else searchView.showMessageDialog(UIStrings.SEARCH_DIALOG_EMPTYTERM);
            searchView.setWaitingStatus();
        }).start();
    }

    public void onRetrievePage() {
        new Thread(() -> {
            if (searchView.getSelectedResult() != null) {
                searchView.setWorkingStatus();
                retrievePageModel.retrievePage(searchView.getSelectedResult().getPageID());
                searchView.setWaitingStatus();
            } else searchView.showMessageDialog(UIStrings.RETRIEVE_DIALOG_NOSELECTEDITEM);
        }).start();
    }

    public void onSavePage() {
        new Thread(() -> {
            SearchResult selectedResult = searchView.getSelectedResult();
            if (selectedResult != null) savePageModel.savePage(lastPageResult.setExtract(searchView.getResultText()));
            else searchView.showMessageDialog(UIStrings.SAVE_DIALOG_NOSELECTEDITEM);
        }).start();
    }

    public void onChangedScore() {
        new Thread(() -> {
            if (searchView.getSelectedResult() != null) updateSearchResultsModel.updateSearchResult(searchView.getSelectedResult().setScore(searchView.getScore()));
            else searchView.showMessageDialog(UIStrings.SEARCH_DIALOG_RATEDNULLPAGE);
        }).start();
    }

    private PageResult formatPageResult(PageResult pageResult) {
        return new PageResult(
                pageResult.getTitle(),
                pageResult.getPageID(),
                pageResult.getExtract().isEmpty()?
                        UIStrings.PAGE_PAGENOTFOUND_EXTRACT:
                        textToHtml(
                            "<h1>" + pageResult.getTitle() + "</h1>"
                                   + pageResult.getExtract().replace("\\n", "\n")
                        ),
               pageResult.getSource()
        );
    }
}
