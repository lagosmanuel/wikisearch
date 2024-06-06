package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;

public class ParserJSON {
    public static Gson gson = new Gson();

    public static JsonObject getFirstPage(Call<String> request) throws IOException {
        Response<String> callForPageResponse = request.execute();
        JsonObject jsonObject = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        JsonObject query = jsonObject.get(UIStrings.API_QUERY_KEYWORD).getAsJsonObject();
        JsonObject pages = query.get(UIStrings.API_PAGES_KEYWORD).getAsJsonObject();

        return pages
                .entrySet()
                .iterator()
                .next()
                .getValue()
                .getAsJsonObject();
    }

    public static JsonArray getQueryResults(Call<String> request) throws IOException {
        Response<String> callForSearchResponse = request.execute();
        JsonObject jsonObject = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jsonObject.get(UIStrings.API_QUERY_KEYWORD).getAsJsonObject();
        return query.get(UIStrings.API_SEARCH_KEYWORD).getAsJsonArray();
    }
}
