package models.repos;

import com.formdev.flatlaf.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.PageResult;
import models.SearchResult;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.ImageDownloader;
import utils.UIStrings;

import java.io.*;
import java.util.*;


public class APIHelper {
    protected final WikipediaSearchAPI searchAPI;
    protected final WikipediaPageAPI pageAPI;
    protected static APIHelper instance;
    protected static Gson gson;

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
        gson = new Gson();
    }

    public Collection<SearchResult> searchTerm(String term) {
        Collection<SearchResult> searchResults = new ArrayList<>();
        try {
            Response<String> callForSearchResponse = searchAPI.searchForTerm(term + UIStrings.API_FILTER).execute();
            JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jsonObject.get(UIStrings.API_QUERY_KEYWORD).getAsJsonObject();
            JsonArray search = query.get(UIStrings.API_SEARCH_KEYWORD).getAsJsonArray();

            for (JsonElement jsonElement:search) {
                JsonObject searchResult = jsonElement.getAsJsonObject();
                searchResults.add(new SearchResult(
                        searchResult.get(UIStrings.API_TITLE_KEYWORD).getAsString(),
                        searchResult.get(UIStrings.API_ID_KEYWORD).getAsInt(),
                        searchResult.get(UIStrings.API_SNIPPET_KEYWORD).getAsString()));
            }
        } catch (IOException e) {System.out.println(UIStrings.API_SEARCHTERM_ERROR + e.getMessage());}
        return searchResults;
    }

    public PageResult retrievePage(int pageId) {
        PageResult pageResult = null;
        try {
            Response<String> callForPageResponse = pageAPI.getExtractByPageID(pageId).execute();
            JsonObject jsonObject = gson.fromJson(callForPageResponse.body(), JsonObject.class);
            JsonObject query = jsonObject.get(UIStrings.API_QUERY_KEYWORD).getAsJsonObject();
            JsonObject pages = query.get(UIStrings.API_PAGES_KEYWORD).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();
            String pageUrl = UIStrings.API_BASEURL + "?curid=%d".formatted(pageId);
            String imageurl = getPageImageUrl(pageId);
            byte[] thumbnail = null;
            if (!imageurl.isEmpty()) thumbnail = ImageDownloader.fetchImage(getPageImageUrl(pageId));

            pageResult = new PageResult(
                    page.get(UIStrings.API_TITLE_KEYWORD).getAsString(),
                    page.get(UIStrings.API_ID_KEYWORD).getAsInt(),
                    page.get(UIStrings.API_EXTRACT_KEYWORD).getAsString(),
                    page.get(UIStrings.API_NS_KEYWORD).getAsInt(),
                    thumbnail,
                    pageUrl
            );

        } catch (IOException e) {System.out.println(UIStrings.API_RETRIEVEPAGE_ERROR + e.getMessage());}
        return pageResult;
    }

    private String getPageImageUrl(int pageId) {
        String imageurl = "";
        try {
            Response<String> callForPageResponse = pageAPI.getPageImagesByPageId(pageId).execute();
            JsonObject jsonObject = gson.fromJson(callForPageResponse.body(), JsonObject.class);
            JsonObject query = jsonObject.get(UIStrings.API_QUERY_KEYWORD).getAsJsonObject();
            JsonObject pages = query.get(UIStrings.API_PAGES_KEYWORD).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();
            JsonElement original = page.getAsJsonObject().get(UIStrings.API_ORIGINAL_KEYWORD);
            if (original == null) return ""; // TODO: no tiene imagen
            JsonElement source = original.getAsJsonObject().get(UIStrings.API_SOURCE_KEYWORD);
            imageurl = source.getAsString();
        } catch (IOException e) {System.out.println(UIStrings.API_RETRIEVEPAGE_ERROR + e.getMessage());}

        return imageurl;
    }
}
