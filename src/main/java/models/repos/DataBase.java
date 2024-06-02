package models.repos;

import models.PageResult;
import models.SearchResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DataBase {
    private static final String URL = "jdbc:sqlite:./dictionary.db";
    private static final int QUERY_TIMEOUT = 30;

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void executeUpdate(String query) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(QUERY_TIMEOUT);
        statement.executeUpdate(query);
        statement.close();
        connection.close();
    }

    public static void loadDatabase() {
        try {
            executeUpdate("create table if not exists searches (id INTEGER, title string PRIMARY KEY, snippet string, score int, lastmodified datetime)");
            executeUpdate("create table if not exists catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
        } catch (SQLException e) {System.out.println(e.getMessage());}
    }

    public static void changeScore(String title, int score) {
        try { executeUpdate("update searches set score=%d, lastmodified=CURRENT_TIMESTAMP where title='%s'".formatted(score, title));
        } catch (SQLException e) {throw new RuntimeException(e);}
    }

    public static void saveSearchResult(SearchResult searchResult) {
        try { executeUpdate("insert into searches values(%d, '%s', '%s', %d, CURRENT_TIMESTAMP);"
                .formatted(searchResult.getPageID(), searchResult.getTitle(), searchResult.getSnippet(), searchResult.getScore()));
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
    public static void saveInfo(String title, String extract) {
        try { executeUpdate("replace into catalog values(null, '"+ title + "', '" + extract + "', 1)");
        } catch (SQLException e) {throw new RuntimeException(e);}
    }

    public static void deleteEntry(String title) {
        try { executeUpdate("delete from catalog where title = '" + title + "'" );
        } catch (SQLException e) {throw new RuntimeException(e);}
    }

    public static int getSearchResultByTitle(String title) {
        int score = 0;
        try (Connection connection = getConnection();) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);
            ResultSet rs = statement.executeQuery("select * from searches where title = '" + title + "'" );
            score = rs.next()? rs.getInt("score"):0;
        } catch(SQLException e) {System.err.println("Get title error " + e.getMessage());}
        return score;
    }

    public static ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<>();
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);
            ResultSet rs = statement.executeQuery("select * from catalog");
            while(rs.next()) titles.add(rs.getString("title"));
        } catch(SQLException e) {System.err.println(e.getMessage());}
        return titles;
    }

    public static PageResult getPage(String title) {
        PageResult pageResult = null;
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);
            ResultSet rs = statement.executeQuery("select * from catalog where title = '" + title + "'" );
            if (rs.next())
                pageResult = new PageResult(
                        rs.getString("title"),
                        rs.getInt("id"),
                        rs.getString("extract")
                );
        } catch (SQLException e) {System.err.println("Get title error " + e.getMessage());}
        return pageResult;
    }

    public static Collection<SearchResult> getSearchResults() {
        Collection<SearchResult> results = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(QUERY_TIMEOUT);
            ResultSet rs = statement.executeQuery("select * from searches");

            while(rs.next())
                results.add(new SearchResult(
                    rs.getString("title"),
                    rs.getInt("id"),
                    rs.getString("snippet"),
                    rs.getInt("score"),
                    rs.getString("lastmodified")));

        } catch(SQLException e) {System.err.println(e.getMessage());}
        return results;
    }
}

