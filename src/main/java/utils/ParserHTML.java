package utils;

import models.SearchResult;

public class ParserHTML {
    public static String textToHtml(String text) {
        StringBuilder builder = new StringBuilder();
        builder.append("<font face=\"arial\">");
        String fixedText = text
                .replace("'", "`"); //Replace to avoid SQL errors, we will have to find a workaround..
        builder.append(fixedText);
        builder.append("</font>");
        return builder.toString();
    }

    public static String searchResultToHtml(SearchResult searchResult) {
        int score = searchResult.getScore();
        String stars = score>0?"★".repeat(searchResult.getScore()) + "☆".repeat(10 - searchResult.getScore()) + " ":"";
        String rate = "<span style=\"color: #e3c153; font-size: 18px;\">"+stars+"</span><br/>";
        String title = "<span style=\"color: black; font-weight: bold; font-size: 10px; font-family: arial;\">"+searchResult.getTitle()+": </span>";
        String snippet = "<span style=\"font-weight: normal;\">"+ searchResult.getSnippet() +"</span>";
        String itemText = "<html>"+rate+title+snippet+"</html>";
        itemText = itemText.replace("<span class=\"searchmatch\">", "")
                .replace("</span>", ""); //TODO: que es esto
        return itemText;
    }
}
