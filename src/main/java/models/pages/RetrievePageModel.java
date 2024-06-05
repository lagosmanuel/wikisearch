package models.pages;

import models.BaseModel;
import models.PageResult;
import models.repos.apis.APIHelper;

public class RetrievePageModel extends BaseModel {
    private final APIHelper apiHelper;
    private PageResult lastResult;

    public RetrievePageModel(APIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    public PageResult getLastResult() {
        return lastResult;
    }

    public void retrievePage(int pageID) {
        lastResult = apiHelper.retrievePage(pageID);
        notifyListeners();
    }
}
