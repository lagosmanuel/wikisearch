package utils;

import java.awt.*;

public class UIStrings {
    // Dialogs
    public static final String SEARCH_DIALOG_EMPTYTERM = "no term to search";
    public static final String SEARCH_DIALOG_NORESULT = "no term has found";
    public static final String SEARCH_DIALOG_RATEDNULLPAGE = "no page to rate";
    public static final String RETRIEVE_DIALOG_NOSELECTEDITEM = "no selected item to retrieve";
    public static final String SAVE_DIALOG_NOSELECTEDITEM = "no selected item to save";
    public static final String SAVE_DIALOG_SUCCESS = "the page was saved successfully";
    public static final String DELETE_DIALOG_SUCCESS = "the page was deleted successfully";
    public static final String ERROR_DIALOG_UI = "Something went wrong with UI!";
    public static final String ERROR_DIALOG_EXTRACTEMPTY = "Something went wrong extracting a page from the DB";
    public static final String RANKINGVIEW_RATENULLRESULT_DIALOG = "cannot rank null search result";

    // MainView
    public static final String MAINVIEW_WINDOW_TITLE = "TV Series Info Repo";

    // SearchView
    public static final String SEARCHVIEW_TAB_TITLE = "Search";
    public static final String SEARCHVIEW_SEARCHBUTTON_TEXT = "Search!";
    public static final String SEARCHVIEW_SAVELOCALLYBUTTON_TEXT = "Save locally!";
    public static final String SEARCHVIEW_POPUP_LABEL = "Search Results";

    // StoredInfoView
    public static final String STOREDINFOVIEW_TAB_TITLE = "StoredInfo";
    public static final String STOREDINFOVIEW_SAVEITEM_TITLE = "Save!";
    public static final String STOREDINFOVIEW_DELETEITEM_TITLE = "Delete!";

    // RankingView
    public static final String RANKINGVIEW_TAB_TITLE = "Ranking";
    public static final String RANKINGVIEW_LASTMODIFIED_LABEL = "Last Modified: ";
    public static final String RANKINGVIEW_SEARCHBUTTON_LABEL = "search";

    // Stars
    public static final String STAR_CHAR_EMPTY = "☆";
    public static final String STAR_CHAR_FULL = "★";
    public static final Color STAR_COLOR = Color.ORANGE;
    public static final String STAR_FONT_FAMILY = "Arial";
    public static final int STAR_FONT_SIZE = 40;
    public static final int SCORE_MAXSCORE = 10;

    // Wikipedia API
    public static final String API_BASEURL = "https://en.wikipedia.org/w/";
    public static final String API_FILTER = " (Tv series) articletopic:\"television\"";
    public static final String API_QUERY_KEYWORD = "query";
    public static final String API_PAGES_KEYWORD = "pages";
    public static final String API_SEARCH_KEYWORD = "search";
    public static final String API_TITLE_KEYWORD = "title";
    public static final String API_ID_KEYWORD = "pageid";
    public static final String API_SNIPPET_KEYWORD = "snippet";
    public static final String API_EXTRACT_KEYWORD = "extract";
    public static final String API_SOURCE_KEYWORD = "ns";
    public static final String API_SEARCHTERM_ERROR = "error searching term in api";
    public static final String API_RETRIEVEPAGE_ERROR = "error retrieven page";

    // Database
    public static final String DB_URL = "jdbc:sqlite:./dictionary.db";
    public static final int DB_QUERY_TIMEOUT = 30;
    public static final String DB_TITLE_KEYWORD = "title";
    public static final String DB_ID_KEYWORD = "id";
    public static final String DB_SNIPPET_KEYWORD = "snippet";
    public static final String DB_SCORE_KEYWORD = "score";
    public static final String DB_LASTMODIFIED_KEYWORD = "lastmodified";
    public static final String DB_EXTRACT_KEYWORD = "extract";
    public static final String DB_SOURCE_KEYWORD = "source";

    public static final String DB_LOADDB_ERROR = "error loading the database: ";
    public static final String DB_UPDATEPAGE_ERROR = "error updating a page: ";
    public static final String DB_UPDATESEARCHRESULT_ERROR = "error saving the results: ";
    public static final String DB_DELETEPAGE_ERROR = "error deleting entry: ";
    public static final String DB_GETSEARCHRESULTBYTITLE_ERROR = "error getting search result by title: ";
    public static final String DB_GETPAGETITLES_ERROR = "error getting titles: ";
    public static final String DB_GETPAGEBYTITLE_ERROR = "error getting a page: ";
    public static final String DB_GETSEARCHRESULTS_ERROR = "error getting search results: ";

    // EventListener
    public static final String EVENTLISTENER_TOPIC_DEFAULT = "all";
    public static final String EVENTLISTENER_TOPIC_CURRENTRESULT = "current_result";

    // Page
    public static final String PAGE_PAGENOTFOUND_EXTRACT = "no result";
}
