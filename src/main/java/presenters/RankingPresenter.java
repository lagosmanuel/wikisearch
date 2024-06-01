package presenters;

import models.SearchResult;
import models.entries.SavedEntriesModel;
import views.RankingView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RankingPresenter {
    private RankingView rankingView;
    private final SavedEntriesModel savedEntriesModel;
    private Map<String, SearchResult> entries;

    public RankingPresenter(SavedEntriesModel model) {
        savedEntriesModel = model;
        entries = new HashMap<>();
        initListeners();
    }

    public void setStoredInfoView(RankingView view) {
        view.setRankingPresenter(this);
        rankingView = view;
        savedEntriesModel.getSavedEntries();
    }

    private void initListeners() {
        savedEntriesModel.addEventListener(() -> {
            Collection<SearchResult> results = savedEntriesModel.getLastResults();
            ArrayList<String> titles = new ArrayList<>();
            for (SearchResult result:results) {
                titles.add(result.getTitle());
                entries.put(result.getTitle(), result);
            }
            rankingView.updateComboBox(titles.toArray());
            if (rankingView.isItemSelected()) onSelectedEntry();
        });
    }

    public void onSelectedEntry() {
        SearchResult result = entries.get(rankingView.getSelectedEntry());
        rankingView.setScore(result.getScore());
        rankingView.setDescription(result.getSnippet());
    }

    public void onChangedScore() {
        entries.get(rankingView.getSelectedEntry()).setScore(rankingView.getScore());
    }
}
