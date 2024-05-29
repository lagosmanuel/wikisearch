package presenters;

import views.SearchView;

public class SearchPresenterImpl implements SearchPresenter {
    protected SearchView searchView;

    @Override
    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
        searchView.setSearchPresenter(this);
    }
}
