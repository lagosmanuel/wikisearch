package utils;

import models.PageResult;
import models.SearchResult;

public class ParserHTML {
    public static String searchResultToHtml(SearchResult searchResult) {
        String title = searchResult.getTitle();
        String snippet = searchResult.getSnippet();
        int score = searchResult.getScore();
        String lastmodified = searchResult.getLastmodified();

        String starsHTML = "<span style=\"color: #e3c153; font-size: 18px;\">"+scoreToStarString(score)+"</span><br/>";
        String titleHTML = "<span style=\"color: white; font-weight: bold; font-size: 12px;\">"+title+"</span>";
        String snippetHTML = "<span style=\"font-weight: normal;\">"+snippet+"</span>";
        String lastmodifiedHTML= lastmodifiedStringToHTML(lastmodified);
        String searchResultHTML = "<html>"+starsHTML+" "+titleHTML+": "+snippetHTML+lastmodifiedHTML+"</html>";

        return searchResultHTML.replace("<span class=\"searchmatch\">", "")
                               .replace("</span>", "");
    }

    public static PageResult formatPageResult(PageResult pageResult) {
        return pageResult.setExtract(
            "<font face=\"arial\">"
                + getPageResultImageHTML(pageResult)
                + "<h1>" + pageResult.getTitle() + "</h1>"
                + pageResult.getExtract().replace("\\n", "\n")
                + "<a href=\"%s\">%s</a>".formatted(pageResult.getUrl(), UIStrings.PAGE_LINK_MSG)
            +"</font>"
        );
    }

    public static String getStyleSheet() {
        return
            "p, a { font-size: 18px; margin: 0px; }" +
            " h1 { font-size: 30px; margin: 0px; }";
    }

    private static String scoreToStarString(int score) {
        return score > 0 && score <= UIStrings.SCORE_MAXSCORE?
            UIStrings.STAR_CHAR_FULL.repeat(score)+UIStrings.STAR_CHAR_EMPTY.repeat(UIStrings.SCORE_MAXSCORE-score):"";
    }

    private static String lastmodifiedStringToHTML(String lastmodified) {
        return lastmodified != null?
            "<br><span style=\"font-weight: normal;\">"+UIStrings.RANKINGVIEW_LASTMODIFIED_LABEL+lastmodified+"</span>":"";
    }

    private static String getPageResultImageHTML(PageResult pageResult) {
        return pageResult.hasThumbnail()?
                "<img src=\"%s/%d\">".formatted(UIStrings.IMAGECACHE_BASEURL, pageResult.getPageID()):"";
    }
}
