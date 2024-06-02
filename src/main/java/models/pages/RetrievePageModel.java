package models.pages;

import models.BaseModel;
import models.PageResult;
import models.repos.APIHelper;

public class RetrievePageModel extends BaseModel {
    private PageResult lastResult;

    public PageResult getLastResult() {
        return lastResult;
    }

    public void retrievePage(int pageID) {
        lastResult = APIHelper.getInstance().retrievePage(pageID);
        notifyListeners();
    }
}
