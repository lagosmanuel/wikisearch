package presenters;

import models.SearchResult;
import models.search.GetSearchResultsModel;
import models.search.UpdateSearchResultsModel;
import utils.UIStrings;
import views.MainView;
import views.RankingView;

import java.util.*;

public class RankingPresenter {
    private RankingView rankingView;
    private final SearchPresenter searchPresenter;
    private final GetSearchResultsModel getSearchResultsModel;
    private final UpdateSearchResultsModel updateSearchResultsModel;

    public RankingPresenter(GetSearchResultsModel getSearchResultsModel, UpdateSearchResultsModel updateSearchResultsModel, SearchPresenter searchPresenter) {
        this.getSearchResultsModel = getSearchResultsModel;
        this.updateSearchResultsModel = updateSearchResultsModel;
        this.searchPresenter = searchPresenter;
        initListeners();
    }

    public void setRankingView(RankingView view, MainView mainView) {
        rankingView = view;
        view.setRankingPresenter(this);
        view.setMainView(mainView);
        getSearchResultsModel.getSavedSearchResults();
    }

    private void initListeners() {
        getSearchResultsModel.addEventListener(() -> rankingView.updateRankingList(orderResults(filterResultsWithoutScore(getSearchResultsModel.getLastResults()))));
        updateSearchResultsModel.addEventListener(getSearchResultsModel::getSavedSearchResults);
    }

    public void onChangedScore() {
        new Thread(() -> {
            if (rankingView.isItemSelected())
                updateSearchResultsModel.updateSearchResult(rankingView.getSelectedResult().setScore(rankingView.getSelectedScore()));
            else rankingView.showDialog(UIStrings.RANKINGVIEW_RATENULLRESULT_DIALOG);
        }).start();
    }

    public void onSearch() {
        if( rankingView.isItemSelected()) {
            searchPresenter.onRetrievePage(rankingView.getSelectedResult());
            rankingView.showSearchView();
        } else rankingView.showDialog(UIStrings.RANKINGVIEW_SEARCHNULL_DIALOG);
    }

    private Collection<SearchResult> orderResults(Collection<SearchResult> searchResults) {
        List<SearchResult> orderedList = new ArrayList<>(searchResults);
        orderedList.sort((key1, key2)-> Integer.compare(key2.getScore(), key1.getScore()));
        return orderedList;
    }

    private Collection<SearchResult> filterResultsWithoutScore(Collection<SearchResult> results) {
        Collection<SearchResult> filteredResults = new ArrayList<>();
        for (SearchResult result:results)
            if (result.getScore() > 0) filteredResults.add(result);
        return filteredResults;
    }
}
