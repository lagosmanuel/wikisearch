package views;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.DataBase;
import models.SearchResult;
import models.WikipediaPageAPI;
import models.WikipediaSearchAPI;
import presenters.SearchPresenter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static utils.Util.textToHtml;

public class SearchViewImpl implements SearchView {
    private JTextField searchTextField;
    private JButton searchButton;
    private JTextPane resultTextPane;
    private JButton saveLocallyButton;
    private JPanel contentPane;
    private JScrollPane resultScrollPane;

    private SearchPresenter searchPresenter;

    private String text = ""; //Last searched text! this variable is central for everything
    String selectedResultTitle = null; //For storage purposes, it may not coincide with the searched term (see below)

    public SearchViewImpl() {
        resultTextPane.setContentType("text/html");
        initListeners();
    }

    private void initListeners() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
        WikipediaPageAPI pageAPI = retrofit.create(WikipediaPageAPI.class);

        // From here on is where the magic happends: querying wikipedia, showing results, etc.
        searchButton.addActionListener(e -> new Thread(() -> {
            //This may take some time, dear user be patient in the meanwhile!
            setWorkingStatus();
            // get from service
            Response<String> callForSearchResponse;
            try {

                //ToAlberto: First, lets search for the term in Wikipedia
                callForSearchResponse = searchAPI.searchForTerm(searchTextField.getText() + " (Tv series) articletopic:\"television\"").execute();

                //Show the result for testing reasons, if it works, dont forget to delete!
                System.out.println("JSON " + callForSearchResponse.body());

                //ToAlberto: Very Important Comment 1
                //This is the code parses the string with the search results for the query
                //The string uses the JSON format to the describe the query and the results
                //So we will use the Google library for JSONs (Gson) for its parsing and manipulation
                //Basically, we will turn the string into a JSON object,
                //With such object we can acceses to its fields using get(fieldname) method provided by Gson
                Gson gson = new Gson();
                JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
                JsonObject query = jobj.get("query").getAsJsonObject();
                Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
                JsonArray jsonResults = query.get("search").getAsJsonArray();

                //toAlberto: shows each result in the JSonArry in a Popupmenu
                JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
                for (JsonElement je : jsonResults) {
                    JsonObject searchResult = je.getAsJsonObject();
                    String searchResultTitle = searchResult.get("title").getAsString();
                    String searchResultPageId = searchResult.get("pageid").getAsString();
                    String searchResultSnippet = searchResult.get("snippet").getAsString();

                    SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
                    searchOptionsMenu.add(sr);

                    //toAlberto: Adding an event to retrive the wikipage when the user clicks an item in the Popupmenu
                    sr.addActionListener(actionEvent -> {
                        try {
                            //This may take some time, dear user be patient in the meanwhile!
                            setWorkingStatus();
                            //Now fetch the info of the select page
                            Response<String> callForPageResponse = pageAPI.getExtractByPageID(sr.pageID).execute();

                            System.out.println("JSON " + callForPageResponse.body());

                            //toAlberto: This is similar to the code above, but here we parse the wikipage answer.
                            //For more details on Gson look for very important coment 1, or just google it :P
                            JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
                            JsonObject query2 = jobj2.get("query").getAsJsonObject();
                            JsonObject pages = query2.get("pages").getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
                            Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
                            JsonObject page = first.getValue().getAsJsonObject();
                            JsonElement searchResultExtract2 = page.get("extract");
                            if (searchResultExtract2 == null) {
                                text = "No Results";
                            } else {
                                text = "<h1>" + sr.title + "</h1>";
                                selectedResultTitle = sr.title;
                                text += searchResultExtract2.getAsString().replace("\\n", "\n");
                                text = textToHtml(text);
                            }
                            resultTextPane.setText(text);
                            resultTextPane.setCaretPosition(0);
                            //Back to edit time!
                            setWatingStatus();
                        } catch (Exception e12) {
                            System.out.println(e12.getMessage());
                        }
                    });
                }
                searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            //Now you can keep searching stuff!
            setWatingStatus();
        }).start());

        saveLocallyButton.addActionListener(actionEvent -> {
            if(text != ""){
                // save to DB  <o/
                DataBase.saveInfo(selectedResultTitle.replace("'", "`"), text);  //Dont forget the ' sql problem
                //comboBox1.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
            }
        });

    }

    public void setWorkingStatus() {
        for(Component c: this.contentPane.getComponents()) c.setEnabled(false);
        resultTextPane.setEnabled(false);
    }

    public void setWatingStatus() {
        for(Component c: this.contentPane.getComponents()) c.setEnabled(true);
        resultTextPane.setEnabled(true);
    }

    @Override
    public Component getComponent() {
        return contentPane;
    }

    @Override
    public void setSearchPresenter(SearchPresenter presenter) {
        this.searchPresenter = presenter;
    }
}
