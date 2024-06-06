package models.pages;

import models.BaseModel;
import models.PageResult;
import models.repos.apis.APIHelper;

public class RetrievePageModel extends BaseModel {
    private final APIHelper apiHelper;
    private PageResult lastPageResult;

    public RetrievePageModel(APIHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    public PageResult getLastPageResult() {
        return lastPageResult;
    }

    public void retrievePageByID(int pageID) {
        lastPageResult = pageID >= 0? apiHelper.retrievePage(pageID):null;
        notifyListeners();
    }
}
