package models;

import models.repos.APIHelper;

public class RetrieveModel extends BaseModel {
    private String lastResult; //TODO: esto está bien?

    public String getLastResult() {
        return lastResult;
    }

    public void retrievePage(String pageId) {
        lastResult = APIHelper.getInstance().retrievePage(pageId);
        notifyListeners();
    }
}
