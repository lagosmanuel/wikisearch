package utils;

import models.SearchResult;

public class ParserHTML {
    public static String textToHtml(String text) {
        StringBuilder builder = new StringBuilder();
        builder.append("<font face=\"arial\">");
        builder.append(text);
        builder.append("</font>");
        return builder.toString();
    }

    public static String searchResultToHtml(SearchResult searchResult) {
        String title = searchResult.getTitle();
        String snippet = searchResult.getSnippet();
        int score = searchResult.getScore();
        String lastmodified = searchResult.getLastmodified();

        String starsString = score>0?UIStrings.STAR_CHAR_FULL.repeat(score)+UIStrings.STAR_CHAR_EMPTY.repeat(UIStrings.SCORE_MAXSCORE - score)+" ":"";
        String rateHTML = "<span style=\"color: #e3c153; font-size: 18px;\">"+starsString+"</span><br/>";
        String titleHTML = "<span style=\"color: black; font-weight: bold; font-size: 10px; font-family: arial;\">"+title+": </span>";
        String snippetHTML = "<span style=\"font-weight: normal;\">"+snippet+"</span>";
        String lastmodifiedHTML= lastmodified != null?"<br><span style=\"font-weight: normal;\">"+UIStrings.RANKINGVIEW_LASTMODIFIED_LABEL+lastmodified+"</span><br/>":"";
        String searchResultHTML = "<html>"+rateHTML+titleHTML+snippetHTML+lastmodifiedHTML+"</html>";

        return searchResultHTML.replace("<span class=\"searchmatch\">", "")
                               .replace("</span>", ""); //TODO: que es esto;
    }
}
