package presenters;
import utils.*;
import models.*;
import models.search.*;
import models.pages.*;
import views.SearchView;
import java.util.Collection;

public class SearchPresenter {
    private SearchView searchView;
    private final SearchTermModel searchTermModel;
    private final RetrievePageModel retrievePageModel;
    private final SavePageModel savePageModel;
    private final UpdateSearchResultsModel updateSearchResultsModel;
    private PageResult lastPageResult;

    public SearchPresenter(SearchTermModel searchTermModel, RetrievePageModel retrievePageModel,
                           SavePageModel savePageModel, UpdateSearchResultsModel updateSearchResultsModel) {
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
            lastPageResult = ParserHTML.formatPageResult(retrievePageModel.getLastPageResult());
            if (lastPageResult != null) {
                ImagesCache.getInstance().saveImageToCache(lastPageResult.getThumbnail(), String.valueOf(lastPageResult.getPageID()));
                searchView.setPageTextPane(lastPageResult.getExtract());
                searchView.setScore(searchView.getSelectedResult().getScore());
            } else searchView.showMessageDialog(UIStrings.PAGE_PAGENOTFOUND);
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
                retrievePageModel.retrievePageByID(searchView.getSelectedResult().getPageID());
                searchView.setWaitingStatus();
            } else searchView.showMessageDialog(UIStrings.RETRIEVE_DIALOG_NOSELECTEDITEM);
        }).start();
    }

    public void onRetrievePage(SearchResult searchResult) {
        new Thread(() -> {
            searchView.setSearchTextField("");
            searchView.setSelectedResult(searchResult);
            onRetrievePage();
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
            if (searchView.getSelectedResult() != null) updateSearchResultsModel.updateSearchResult(
                searchView
                    .getSelectedResult()
                    .setScore(searchView.getSelectedScore())
            );
            else searchView.showMessageDialog(UIStrings.SEARCH_DIALOG_RATEDNULLPAGE);
        }).start();
    }
}