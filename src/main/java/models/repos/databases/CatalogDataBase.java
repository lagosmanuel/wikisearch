package models.repos.databases;

import models.PageResult;
import java.util.Collection;

public interface CatalogDataBase {
    PageResult getPageResultByTitle(String title);
    Collection<String> getPageTitles();
    void updatePage(PageResult pageResult);
    void deletePageByTitle(String title);
}
