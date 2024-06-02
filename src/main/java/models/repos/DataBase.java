package models.repos;

import models.PageResult;
import models.SearchResult;
import utils.UIStrings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DataBase {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(UIStrings.DB_URL);
    }

    private static void executeUpdate(String query) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
        statement.executeUpdate(query);
        statement.close();
        connection.close();
    }

    public static void loadDatabase() {
        try {
            executeUpdate("create table if not exists searches (id integer, title string primary key, snippet string, score int, lastmodified datetime)");
            executeUpdate("create table if not exists catalog (id integer, title string primary key, extract string, source integer)");
        } catch (SQLException e) {System.err.println(UIStrings.DB_LOADDB_ERROR + e.getMessage());}
    }

    public static void changeScore(String title, int score) {
        try { executeUpdate("update searches set score=%d, lastmodified=CURRENT_TIMESTAMP where title='%s'".formatted(score, title));
        } catch (SQLException e) {System.err.println(UIStrings.DB_CHANGESCORE_ERROR + e.getMessage());}
    }

    public static void saveSearchResult(SearchResult searchResult) {
        try { executeUpdate("insert into searches values(%d, '%s', '%s', %d, CURRENT_TIMESTAMP);"
                .formatted(searchResult.getPageID(), searchResult.getTitle(), searchResult.getSnippet(), searchResult.getScore()));
        } catch (SQLException e) {System.err.println(UIStrings.DB_SAVESEARCHRESULT_ERROR + e.getMessage());}
    }
    public static void saveInfo(String title, String extract) {
        try { executeUpdate("replace into catalog values(null, '"+ title + "', '" + extract + "', 1)");
        } catch (SQLException e) {System.err.println(UIStrings.DB_SAVEINFO_ERROR + e.getMessage());}
    }

    public static void deleteEntry(String title) {
        try { executeUpdate("delete from catalog where title = '" + title + "'" );
        } catch (SQLException e) {System.err.println(UIStrings.DB_DELETEENTRY_ERROR + e.getMessage());}
    }

    public static int getSearchResultByTitle(String title) {
        int score = 0;
        try (Connection connection = getConnection();) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet rs = statement.executeQuery("select * from searches where title = '" + title + "'" );
            score = rs.next()? rs.getInt(UIStrings.DB_SCORE_KEYWORD):0;
        } catch (SQLException e) {System.err.println(UIStrings.DB_GETSEARCHRESULTBYTITLE_ERROR + e.getMessage());}
        return score;
    }

    public static ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<>();
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet rs = statement.executeQuery("select * from catalog");
            while(rs.next()) titles.add(rs.getString(UIStrings.DB_TITLE_KEYWORD));
        } catch (SQLException e) {System.err.println(UIStrings.DB_GETTITLES_ERROR + e.getMessage());}
        return titles;
    }

    public static PageResult getPage(String title) {
        PageResult pageResult = null;
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet rs = statement.executeQuery("select * from catalog where title = '" + title + "'" );
            if (rs.next())
                pageResult = new PageResult(
                        rs.getString(UIStrings.DB_TITLE_KEYWORD),
                        rs.getInt(UIStrings.DB_ID_KEYWORD),
                        rs.getString(UIStrings.DB_EXTRACT_KEYWORD)
                );
        } catch (SQLException e) {System.err.println(UIStrings.DB_GETPAGE_ERROR + e.getMessage());}
        return pageResult;
    }

    public static Collection<SearchResult> getSearchResults() {
        Collection<SearchResult> results = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet rs = statement.executeQuery("select * from searches");

            while(rs.next())
                results.add(new SearchResult(
                    rs.getString(UIStrings.DB_TITLE_KEYWORD),
                    rs.getInt(UIStrings.DB_ID_KEYWORD),
                    rs.getString(UIStrings.DB_SNIPPET_KEYWORD),
                    rs.getInt(UIStrings.DB_SCORE_KEYWORD),
                    rs.getString(UIStrings.DB_LASTMODIFIED_KEYWORD)));

        } catch (SQLException e) {System.err.println(UIStrings.DB_GETSEARCHRESULTS_ERROR + e.getMessage());}
        return results;
    }
}

