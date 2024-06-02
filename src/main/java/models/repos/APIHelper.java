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
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        searchAPI = retrofit.create(WikipediaSearchAPI.class);
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }

    public Collection<SearchResult> searchTerm(String term) {
        Collection<SearchResult> results = new ArrayList<>();
        Response<String> callForSearchResponse;
        try {
            callForSearchResponse = searchAPI.searchForTerm(term + " (Tv series) articletopic:\"television\"").execute();
            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jobj.get("query").getAsJsonObject();
            JsonArray jsonResults = query.get("search").getAsJsonArray();

            for (JsonElement jsonElement : jsonResults) {
                JsonObject searchResult = jsonElement.getAsJsonObject();
                results.add(new SearchResult(
                        searchResult.get("title").getAsString(),
                        searchResult.get("pageid").getAsInt(),
                        searchResult.get("snippet").getAsString()));
            }
        } catch (IOException e) {e.printStackTrace();}
        return results;
    }

    public PageResult retrievePage(int pageId) {
        PageResult pageResult = null;
        try {
            Response<String> callForPageResponse = pageAPI.getExtractByPageID(String.valueOf(pageId)).execute();
            Gson gson = new Gson();
            JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
            JsonObject query2 = jobj2.get("query").getAsJsonObject();
            JsonObject pages = query2.get("pages").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();

            pageResult = new PageResult(
                    page.get("title").getAsString(),
                    page.get("pageid").getAsInt(),
                    page.get("extract").getAsString());

        } catch (Exception e) {System.out.println(e.getMessage());}
        return pageResult;
    }
}
