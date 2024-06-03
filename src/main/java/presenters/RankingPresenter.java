package presenters;

import models.SearchResult;
import models.search.GetSearchResultsModel;
import models.search.UpdateSearchResultsModel;
import utils.UIStrings;
import views.RankingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RankingPresenter {
    private RankingView rankingView;
    private final GetSearchResultsModel getSearchResultsModel;
    private final UpdateSearchResultsModel updateSearchResultsModel;
    private Map<String, SearchResult> lastSearchResults;

    public RankingPresenter(GetSearchResultsModel getSearchResultsModel, UpdateSearchResultsModel updateSearchResultsModel) {
        this.getSearchResultsModel = getSearchResultsModel;
        this.updateSearchResultsModel = updateSearchResultsModel;
        initListeners();
    }

    public void setStoredInfoView(RankingView view) {
        view.setRankingPresenter(this);
        rankingView = view;
        getSearchResultsModel.getSavedSearchResults();
    }

    private void initListeners() {
        getSearchResultsModel.addEventListener(() -> {
            lastSearchResults = filterResultsWithoutScore(getSearchResultsModel.getLastResults());
            rankingView.updateComboBox(getKeysOrdered(lastSearchResults));
            onSelectedEntry();
        });

        updateSearchResultsModel.addEventListener(getSearchResultsModel::getSavedSearchResults);
    }

    public void onSelectedEntry() {
        new Thread(() -> {
            if(rankingView.isItemSelected()) showResult(lastSearchResults.get(rankingView.getSelectedEntry()));
            else clearResults();
        }).start();
    }

    public void onChangedScore() {
        new Thread(() -> {
            if(rankingView.isItemSelected())
                updateSearchResultsModel.updateSearchResult(
                        lastSearchResults
                                .get(rankingView.getSelectedEntry())
                                .setScore(rankingView.getScore()
                        ));
            else rankingView.showDialog(UIStrings.RANKINGVIEW_RATENULLRESULT_DIALOG);
        }).start();
    }

    private void showResult(SearchResult result) {
        rankingView.setTitle(result.getTitle());
        rankingView.setScore(result.getScore());
        rankingView.setDescription(result.getSnippet());
        rankingView.setLastModified(UIStrings.RANKINGVIEW_LASTMODIFIED_LABEL + result.getLastmodified());
    }

    private void clearResults() {
        rankingView.setTitle("");
        rankingView.setScore(0);
        rankingView.setDescription("");
        rankingView.setLastModified("");
    }

    private Object[] getKeysOrdered(Map<String, SearchResult> resultMap) {
        ArrayList list = new ArrayList(resultMap.keySet());
        list.sort((key1, key2)-> Integer.compare(resultMap.get(key2).getScore(), resultMap.get(key1).getScore()));
        return list.toArray();
    }

    private Map<String, SearchResult> filterResultsWithoutScore(Map<String, SearchResult> resultMap) {
        Map<String, SearchResult> resultados = new HashMap<>();
        for (String result : resultMap.keySet())
            if (resultMap.get(result).getScore() > 0)  resultados.put(result, resultMap.get(result));
        return resultados;
    }
}
