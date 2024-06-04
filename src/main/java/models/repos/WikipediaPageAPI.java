package models.repos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaPageAPI {
  //The parameter explaintext=1 was removed to get a html formated answer... //TODO
  //It should work as intended, but we didn't test it properly.
  @GET("api.php?format=json&action=query&prop=extracts&exlimit=1&exintro=1")
  Call<String> getExtractByPageID(@Query("pageids") int pageid);

  @GET("api.php?action=query&prop=pageimages&format=json&piprop=original&pageids=30304&pilicense=any")
  Call<String> getPageByPageId(@Query("pageids") int pageid);
}
