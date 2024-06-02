package presenters;

import models.*;
import models.pages.RetrievePageModel;
import models.pages.SavePageModel;
import models.entries.SearchTermModel;
import utils.UIStrings;
import views.SearchView;

import java.util.*;

import static utils.ParserHTML.textToHtml;

public class SearchPresenter {
    private SearchView searchView;
    private final SearchTermModel searchTermModel;
    private final RetrievePageModel retrievePageModel;
    private final SavePageModel savePageModel;

    public SearchPresenter(SearchTermModel searchTermModel, RetrievePageModel retrievePageModel, SavePageModel savePageModel) {
        this.searchTermModel = searchTermModel;
        this.retrievePageModel = retrievePageModel;
        this.savePageModel = savePageModel;
        initListeners();
    }

    public void setSearchView(SearchView view) {
        searchView = view;
        searchView.setSearchPresenter(this);
    }

    private void initListeners() {
        searchTermModel.addEventListener(() -> {
            Collection<SearchResult> results = searchTermModel.getLastResults();
            if (!results.isEmpty()) searchView.showOptionsMenu(results);
            else searchView.showMessageDialog(UIStrings.SEARCH_DIALOG_NORESULT);
        });

        retrievePageModel.addEventListener(() -> {
            PageResult pageResult = formatPageResult(retrievePageModel.getLastResult());
            searchView.setResultTextPane(pageResult.getText());
        });

        savePageModel.addEventListener(() -> {
            if (searchView.getComponent().isVisible()) searchView.showMessageDialog(UIStrings.SAVE_DIALOG_SUCCESS);
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
            SearchResult selectedResult = searchView.getSelectedResult();
            if (selectedResult != null) {
                searchView.setWorkingStatus();
                retrievePageModel.retrievePage(selectedResult.getPageID());
                searchView.setWaitingStatus();
            } else searchView.showMessageDialog(UIStrings.RETRIEVE_DIALOG_NOSELECTEDITEM);
        }).start();
    }

    public void onSavePage() {
        new Thread(() -> {
            SearchResult selectedResult = searchView.getSelectedResult();
            if (selectedResult != null) {
                String formatedTitle = selectedResult.getTitle().replace("'", "`");
                savePageModel.savePage(formatedTitle, searchView.getResultText());
            } else searchView.showMessageDialog(UIStrings.SAVE_DIALOG_NOSELECTEDITEM);
        }).start();
    }

    private PageResult formatPageResult(PageResult pageResult) {
        return new PageResult(
                pageResult.getTitle(),
                pageResult.getPageID(),
                pageResult.getText().isEmpty()? "no results":
                        textToHtml(
                            "<h1>" + pageResult.getTitle() + "</h1>" +
                                    pageResult.getText().replace("\\n", "\n"
                )
        ));
    }
}
