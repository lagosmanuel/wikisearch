package models.repos.databases;

import models.SearchResult;
import models.repos.DataBase;
import utils.UIStrings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SearchResultDataBase extends DataBase {
    public static void load() {
        try {executeUpdate("create table if not exists searches (id integer, title string primary key, snippet string, score int, lastmodified datetime)");
        } catch (SQLException e) {System.err.println(UIStrings.DB_LOADDB_ERROR + e.getMessage());}
    }

    public static SearchResult getSearchResultByTitle(String title) {
        SearchResult searchResult = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from searches where title=?");
            preparedStatement.setString(1, title);
            preparedStatement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) searchResult = resultSetToSearchResult(resultSet);
        } catch (SQLException e) {System.err.println(UIStrings.DB_GETSEARCHRESULTBYTITLE_ERROR + e.getMessage());}
        return searchResult;
    }

    public static Collection<SearchResult> getSearchResults() {
        Collection<SearchResult> results = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet rs = statement.executeQuery("select * from searches");

            while(rs.next())
                results.add(resultSetToSearchResult(rs));

        } catch (SQLException e) {System.err.println(UIStrings.DB_GETSEARCHRESULTS_ERROR + e.getMessage());}
        return results;
    }

    public static void updateSearchResult(SearchResult searchResult) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("insert or replace into searches values (?, ?, ?, ?, CURRENT_TIMESTAMP)")){
            preparedStatement.setInt(1, searchResult.getPageID());
            preparedStatement.setString(2, searchResult.getTitle());
            preparedStatement.setString(3, searchResult.getSnippet());
            preparedStatement.setInt(4, searchResult.getScore());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {System.err.println(UIStrings.DB_UPDATESEARCHRESULT_ERROR + e.getMessage());}
    }

    private static SearchResult resultSetToSearchResult(ResultSet rs) throws SQLException {
        return new SearchResult(
                rs.getString(UIStrings.DB_TITLE_KEYWORD),
                rs.getInt(UIStrings.DB_ID_KEYWORD),
                rs.getString(UIStrings.DB_SNIPPET_KEYWORD),
                rs.getInt(UIStrings.DB_SCORE_KEYWORD),
                rs.getString(UIStrings.DB_LASTMODIFIED_KEYWORD));
    }
}
