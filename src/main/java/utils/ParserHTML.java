package utils;

import models.PageResult;
import models.SearchResult;

public class ParserHTML {
    // todo: esto vuela
    public static String textToHtml(String text) {
        return "<font face=\"arial\">" + text + "</font>";
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

    public static PageResult formatPageResult(PageResult pageResult) {
        return new PageResult(
                pageResult.getTitle(),
                pageResult.getPageID(),
                pageResult.getExtract().isEmpty()?
                        UIStrings.PAGE_PAGENOTFOUND_EXTRACT:
                        textToHtml(
                                "<h1>" + pageResult.getTitle() + "</h1>"
                                        + pageResult.getExtract().replace("\\n", "\n")
                                        + "<a href=\"%s\">%s</a>".formatted(pageResult.getUrl(), UIStrings.PAGE_LINK_MSG)
                        ),
                pageResult.getSource(),
                pageResult.getThumbnail(),
                pageResult.getUrl()
        );
    }
}
