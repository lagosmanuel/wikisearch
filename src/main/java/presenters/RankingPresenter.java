package presenters;

import models.SearchResult;
import models.entries.GetSearchResultsModel;
import models.entries.UpdateSearchResultsModel;
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
        getSearchResultsModel.getSavedEntries();
    }

    private void initListeners() {
        getSearchResultsModel.addEventListener(() -> {
            if (!rankingView.getComponent().isVisible()) rankingView.updateComboBox(getSearchResultsModel.getLastResults().keySet().toArray());
            if (rankingView.isItemSelected()) showResult(getSearchResultsModel.getLastResultByTitle(rankingView.getSelectedEntry()));
        });
        updateSearchResultsModel.addEventListener(getSearchResultsModel::getSavedEntries);
    }

    private void showResult(SearchResult result) {
        rankingView.setTitle(result.getTitle());
        rankingView.setScore(result.getScore());
        rankingView.setDescription(result.getSnippet());
        rankingView.setLastModified(result.getLastmoddifed());
    }

    public void onSelectedEntry() {
        new Thread(() -> {
            if(rankingView.isItemSelected()) showResult(getSearchResultsModel.getLastResultByTitle(rankingView.getSelectedEntry()));
        }).start();
    }

    public void onChangedScore() {
        new Thread(() -> {
            if(rankingView.isItemSelected()) updateSearchResultsModel.changeScore(rankingView.getSelectedEntry(), rankingView.getScore());
        }).start();
    }
}
