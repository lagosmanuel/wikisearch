package presenters;

import models.ItemsModel;
import models.SearchResult;
import views.RankingView;

public class RankingPresenter {
    private RankingView rankingView;
    private ItemsModel itemsModel;

    public RankingPresenter(ItemsModel model) {
        itemsModel = model;
        initListeners();
    }

    private void initListeners() {
        itemsModel.addEventListener(this::populate);
    }

    public void setStoredInfoView(RankingView view) {
        view.setRankingPresenter(this);
        rankingView = view;
        onUpdate();
    }

    public void onUpdate() {
        itemsModel.getItems();
    }

    private void populate() {
        for (SearchResult result: itemsModel.getLastResults()) {
            result.setScore((int) (Math.random() * 10));
            rankingView.addEntry(result);
        }
    }
}
