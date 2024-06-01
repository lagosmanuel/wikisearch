package models.pages;

import models.BaseModel;
import models.repos.APIHelper;

public class RetrievePageModel extends BaseModel {
    private String lastResult;

    public String getLastResult() {
        return lastResult;
    }

    public void retrievePage(String pageID) {
        lastResult = APIHelper.getInstance().retrievePage(pageID);
        notifyListeners();
    }
}
