package presenters;

import models.SearchResult;
import models.entries.GetSearchResultsModel;
import models.entries.UpdateSearchResultsModel;
import utils.UIStrings;
import views.RankingView;

public class RankingPresenter {
    private RankingView rankingView;
    private final GetSearchResultsModel getSearchResultsModel;
    private final UpdateSearchResultsModel updateSearchResultsModel;

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
            showResult(getSearchResultsModel.getCurrentResult());
        }, UIStrings.EVENTLISTENER_TOPIC_CURRENTRESULT);

        getSearchResultsModel.addEventListener(() -> {
            if (!rankingView.getComponent().isVisible())
                rankingView.updateComboBox(getSearchResultsModel.getLastResults().keySet().toArray());
            onSelectedEntry();
        });

        updateSearchResultsModel.addEventListener(getSearchResultsModel::getSavedSearchResults);
    }

    private void showResult(SearchResult result) {
        rankingView.setTitle(result.getTitle());
        rankingView.setScore(result.getScore());
        rankingView.setDescription(result.getSnippet());
        rankingView.setLastModified(result.getLastmoddifed());
    }

    public void onSelectedEntry() {
        new Thread(() -> {
            if(rankingView.isItemSelected())
                getSearchResultsModel.getSavedSearchResultByTitle(rankingView.getSelectedEntry());
        }).start();
    }

    public void onChangedScore() {
        new Thread(() -> {
            if(rankingView.isItemSelected())
                updateSearchResultsModel.changeScore(rankingView.getSelectedEntry(), rankingView.getScore());
        }).start();
    }
}
