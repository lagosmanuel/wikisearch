package utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class UIStrings {
    public static final String SEARCH_DIALOG_EMPTYTERM = "You must enter a term before searching!";
    public static final String SEARCH_DIALOG_NORESULT = "No results have been found!";
    public static final String SEARCH_DIALOG_RATEDNULLPAGE = "There is no page to rate!";
    public static final String SEARCH_DIALOG_NODESKTOP = "This feature is not available on this system!";
    public static final String RETRIEVE_DIALOG_NOSELECTEDITEM = "There is no page to retrieve!";
    public static final String SAVE_DIALOG_NOSELECTEDITEM = "There is no page to save!";
    public static final String SAVE_DIALOG_SUCCESS = "The page was saved successfully!";
    public static final String DELETE_DIALOG_SUCCESS = "The page was deleted successfully!";
    public static final String ERROR_DIALOG_UI = "Something went wrong with UI!";
    public static final String ERROR_DIALOG_EXTRACTEMPTY = "Something went wrong extracting a page from the DB!";
    public static final String RANKINGVIEW_RATENULLRESULT_DIALOG = "There is no TV serie to rate!";
    public static final String RANKINGVIEW_SEARCHNULL_DIALOG = "There is no TV serie to search!";
    public static final String IMAGECACHE_DIALOG_SAVEERROR = "Someting went wront saving the thumbnail!";

    public static final String MAINVIEW_WINDOW_TITLE = "TV Series Info Repo";
    public static final int MAINVIEW_WINDOW_WIDTH = 700;
    public static final int MAINVIEW_WINDOW_HEIGHT = 1000;

    public static final String SEARCHVIEW_TAB_TITLE = "Search!";
    public static final int SEARCHVIEW_TAB_INDEX = 0;
    public static final String SEARCHVIEW_SEARCHBUTTON_TEXT = "Search!";
    public static final String SEARCHVIEW_SAVELOCALLYBUTTON_TEXT = "Save!";
    public static final String SEARCHVIEW_POPUP_LABEL = "Search Results";
    public static final int SEARCHVIEW_SEARCHBUTTON_KEY = KeyEvent.VK_ENTER;

    public static final String STOREDINFOVIEW_TAB_TITLE = "StoredInfo";
    public static final int STOREDINFOVIEW_TAB_INDEX = 1;
    public static final String STOREDINFOVIEW_SAVEITEM_TITLE = "Save!";
    public static final String STOREDINFOVIEW_DELETEITEM_TITLE = "Delete!";

    public static final String STAR_CHAR_EMPTY = "☆";
    public static final String STAR_CHAR_FULL = "★";
    public static final String STAR_CHAR_DELETE = "⌫";
    public static final Color STAR_COLOR = new Color(0xE3C153);
    public static final String STAR_FONT_FAMILY = "Arial";
    public static final int STAR_FONT_SIZE = 25;
    public static final int SCORE_MAXSCORE = 10;

    public static final String RANKINGVIEW_TAB_TITLE = "Ranking";
    public static final int RANKINGVIEW_TAB_INDEX = 2;
    public static final String RANKINGVIEW_LASTMODIFIED_LABEL = "Last Modified: ";
    public static final String RANKINGVIEW_SEARCHBUTTON_LABEL = "Search!";
    public static final int RANKINGVIEW_SEARCHBUTTON_KEY = KeyEvent.VK_ENTER;
    public static final String RANKINGVIEW_STARSPANEL_TITLE = "Change Score";

    public static final String API_BASEURL = "https://en.wikipedia.org/w/";
    public static final String API_FILTER = " (Tv series) articletopic:\"television\"";
    public static final String API_QUERY_KEYWORD = "query";
    public static final String API_PAGES_KEYWORD = "pages";
    public static final String API_SEARCH_KEYWORD = "search";
    public static final String API_TITLE_KEYWORD = "title";
    public static final String API_ORIGINAL_KEYWORD = "original";
    public static final String API_SOURCE_KEYWORD = "source";
    public static final String API_ID_KEYWORD = "pageid";
    public static final String API_SNIPPET_KEYWORD = "snippet";
    public static final String API_EXTRACT_KEYWORD = "extract";
    public static final String API_NS_KEYWORD = "ns";
    public static final String API_SEARCHTERM_ERROR = "Something went wrong searching the term in the API!";
    public static final String API_RETRIEVEPAGE_ERROR = "Something went wrong retrieving the page!";

    public static final String DB_URL = "jdbc:sqlite:./dictionary.db";
    public static final int DB_QUERY_TIMEOUT = 30;
    public static final String DB_TITLE_KEYWORD = "title";
    public static final String DB_ID_KEYWORD = "id";
    public static final String DB_SNIPPET_KEYWORD = "snippet";
    public static final String DB_SCORE_KEYWORD = "score";
    public static final String DB_LASTMODIFIED_KEYWORD = "lastmodified";
    public static final String DB_EXTRACT_KEYWORD = "extract";
    public static final String DB_SOURCE_KEYWORD = "source";
    public static final String DB_THUMBNAIL_KEYWORD = "thumbnail";
    public static final String DB_URL_KEYWORD = "url";
    public static final String DB_LOADDB_ERROR = "Something went wrong loading the database: ";
    public static final String DB_UPDATEPAGE_ERROR = "Something went wrong updating a page: ";
    public static final String DB_UPDATESEARCHRESULT_ERROR = "Something went wrong saving a search result: ";
    public static final String DB_DELETEPAGE_ERROR = "Something went wrong deleting a search result: ";
    public static final String DB_GETSEARCHRESULTBYTITLE_ERROR = "Something went wrong getting a search result by title: ";
    public static final String DB_GETPAGETITLES_ERROR = "Something went wrong getting the saved titles: ";
    public static final String DB_GETPAGEBYTITLE_ERROR = "Something went wrong getting a saved page: ";
    public static final String DB_GETSEARCHRESULTS_ERROR = "Something went wrong getting the saved search results: ";

    public static final String EVENTLISTENER_TOPIC_DEFAULT = "all";

    public static final String PAGE_PAGENOTFOUND = "No page has been found!";
    public static final String PAGE_LINK_MSG = "view in wikipedia";

    public static final String IMAGECACHE_BASEURL = "http://buffered";

    public static final Font DEFAULT_FONT = new Font("arial", Font.BOLD, 18);

    public static final String IMAGEICON_PATH = "images/icon.png";
}
