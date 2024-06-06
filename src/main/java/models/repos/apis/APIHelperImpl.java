package models.repos.apis;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.PageResult;
import models.SearchResult;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import utils.ImageDownloader;
import utils.ParserJSON;
import utils.UIStrings;
import java.io.*;
import java.util.*;

public class APIHelperImpl implements APIHelper {
    protected final WikipediaSearchAPI searchAPI;
    protected final WikipediaPageAPI pageAPI;

    public APIHelperImpl() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(UIStrings.API_BASEURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
        searchAPI = retrofit.create(WikipediaSearchAPI.class);
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }

    public Collection<SearchResult> searchTerm(String term) {
        Collection<SearchResult> searchResults = new ArrayList<>();
        try {
            JsonArray queryResults = ParserJSON.getQueryResults(searchAPI.searchForTerm(term + UIStrings.API_FILTER));
            for (JsonElement jsonElement:queryResults) {
                JsonObject searchResult = jsonElement.getAsJsonObject();
                searchResults.add(new SearchResult(
                    searchResult.get(UIStrings.API_TITLE_KEYWORD).getAsString(),
                    searchResult.get(UIStrings.API_ID_KEYWORD).getAsInt(),
                    searchResult.get(UIStrings.API_SNIPPET_KEYWORD).getAsString())
                );
            }
        } catch (IOException exception) {System.out.println(UIStrings.API_SEARCHTERM_ERROR + exception.getMessage());}
        return searchResults;
    }

    public PageResult retrievePage(int pageId) {
        PageResult pageResult = null;
        try {
            JsonObject page = ParserJSON.getFirstPage(pageAPI.getExtractByPageID(pageId));
            String imageurl = getPageImageUrl(pageId);
            byte[] thumbnail = null;
            if (!imageurl.isEmpty() && !imageurl.contains(".svg")) thumbnail = ImageDownloader.fetchImage(imageurl);

            pageResult = new PageResult(
                page.get(UIStrings.API_TITLE_KEYWORD).getAsString(),
                page.get(UIStrings.API_ID_KEYWORD).getAsInt(),
                page.get(UIStrings.API_EXTRACT_KEYWORD).getAsString(),
                page.get(UIStrings.API_NS_KEYWORD).getAsInt(),
                thumbnail,
                UIStrings.API_BASEURL + "?curid=%d".formatted(pageId)
            );
        } catch (IOException exception) {System.out.println(UIStrings.API_RETRIEVEPAGE_ERROR + exception.getMessage());}
        return pageResult;
    }

    private String getPageImageUrl(int pageId) throws IOException {
        JsonObject page = ParserJSON.getFirstPage(pageAPI.getPageImagesByPageId(pageId));
        JsonElement original = page.getAsJsonObject().get(UIStrings.API_ORIGINAL_KEYWORD);
        if (original == null) return "";
        JsonElement source = original.getAsJsonObject().get(UIStrings.API_SOURCE_KEYWORD);
        return source.getAsString();
    }
}