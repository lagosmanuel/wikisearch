package models.repos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.PageResult;
import models.SearchResult;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.UIStrings;

import java.io.IOException;
import java.util.*;

public class APIHelper {
    protected final WikipediaSearchAPI searchAPI;
    protected final WikipediaPageAPI pageAPI;
    protected static APIHelper instance;

    public static APIHelper getInstance() {
        if (instance == null) instance = new APIHelper();
        return instance;
    }

    private APIHelper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UIStrings.API_BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        searchAPI = retrofit.create(WikipediaSearchAPI.class);
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }

    public Collection<SearchResult> searchTerm(String term) {
        Collection<SearchResult> results = new ArrayList<>();
        Response<String> callForSearchResponse;
        try {
            callForSearchResponse = searchAPI.searchForTerm(term + UIStrings.API_FILTER).execute();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jsonObject.get(UIStrings.API_QUERY_KEYWORD).getAsJsonObject();
            JsonArray jsonResults = query.get(UIStrings.API_SEARCH_KEYWORD).getAsJsonArray();

            for (JsonElement jsonElement : jsonResults) {
                JsonObject searchResult = jsonElement.getAsJsonObject();
                results.add(new SearchResult(
                        searchResult.get(UIStrings.API_TITLE_KEYWORD).getAsString(),
                        searchResult.get(UIStrings.API_ID_KEYWORD).getAsInt(),
                        searchResult.get(UIStrings.API_SNIPPET_KEYWORD).getAsString()));
            }
        } catch (IOException e) {System.out.println(UIStrings.API_SEARCHTERM_ERROR + e.getMessage());}
        return results;
    }

    public PageResult retrievePage(int pageId) {
        PageResult pageResult = null;
        try {
            Response<String> callForPageResponse = pageAPI.getExtractByPageID(String.valueOf(pageId)).execute();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(callForPageResponse.body(), JsonObject.class);
            JsonObject query = jsonObject.get(UIStrings.API_QUERY_KEYWORD).getAsJsonObject();
            JsonObject pages = query.get(UIStrings.API_PAGES_KEYWORD).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();

            pageResult = new PageResult(
                    page.get(UIStrings.API_TITLE_KEYWORD).getAsString(),
                    page.get(UIStrings.API_ID_KEYWORD).getAsInt(),
                    page.get(UIStrings.API_EXTRACT_KEYWORD).getAsString(),
                    page.get(UIStrings.API_SOURCE_KEYWORD).getAsInt());

        } catch (IOException e) {System.out.println(UIStrings.API_RETRIEVEPAGE_ERROR + e.getMessage());}
        return pageResult;
    }
}
