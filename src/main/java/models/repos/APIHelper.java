package models.repos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.SearchResult;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.*;

import static utils.ParserHTML.textToHtml;

public class APIHelper {
    protected WikipediaSearchAPI searchAPI;
    protected WikipediaPageAPI pageAPI;
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
        Collection<SearchResult> results = new ArrayList<>(); //TODO: mientras está buscando no modifica los results
        Response<String> callForSearchResponse;
        try {
            callForSearchResponse = searchAPI.searchForTerm(term + " (Tv series) articletopic:\"television\"").execute();
            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jobj.get("query").getAsJsonObject();
            Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
            JsonArray jsonResults = query.get("search").getAsJsonArray();

            for (JsonElement je : jsonResults) {
                JsonObject searchResult = je.getAsJsonObject();
                String searchResultTitle = searchResult.get("title").getAsString();
                String searchResultPageId = searchResult.get("pageid").getAsString();
                String searchResultSnippet = searchResult.get("snippet").getAsString();
                results.add(new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return results;
    }

    public String retrievePage(String pageId) {
        String text = "";

        try {
            Response<String> callForPageResponse = pageAPI.getExtractByPageID(pageId).execute();

            Gson gson = new Gson();
            JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
            JsonObject query2 = jobj2.get("query").getAsJsonObject();
            JsonObject pages = query2.get("pages").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
            JsonObject page = first.getValue().getAsJsonObject();
            JsonElement searchResultExtract2 = page.get("extract");
            JsonElement searchResultExtract3 = page.get("title");
            if (searchResultExtract2 == null) {
                text = "No Results";
            } else {
                text = "<h1>" + searchResultExtract3.getAsString() + "</h1>";
                text += searchResultExtract2.getAsString().replace("\\n", "\n");
                text = textToHtml(text);
            }
        } catch (Exception e12) {
            System.out.println(e12.getMessage());
        }

        return text;
    }
}
