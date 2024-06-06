package models.repos.databases;

import models.SearchResult;
import utils.UIStrings;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SearchResultDataBaseImpl extends DataBase implements SearchResultDataBase {
    public SearchResultDataBaseImpl() {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            statement.executeUpdate("create table if not exists searches (id integer, title string primary key, snippet string, score int, lastmodified datetime)");
        } catch (SQLException exception) {System.err.println(UIStrings.DB_LOADDB_ERROR + exception.getMessage());}
    }

    public SearchResult getSearchResultByTitle(String title) {
        SearchResult searchResult = null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("select * from searches where title=?")) {
            preparedStatement.setString(1, title);
            preparedStatement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) searchResult = resultSetToSearchResult(resultSet);
        } catch (SQLException exception) {System.err.println(UIStrings.DB_GETSEARCHRESULTBYTITLE_ERROR + exception.getMessage());}
        return searchResult;
    }

    public Collection<SearchResult> getSearchResults() {
        Collection<SearchResult> results = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet resultSet = statement.executeQuery("select * from searches");
            while(resultSet.next()) results.add(resultSetToSearchResult(resultSet));
        } catch (SQLException exception) {System.err.println(UIStrings.DB_GETSEARCHRESULTS_ERROR + exception.getMessage());}
        return results;
    }

    public void updateSearchResult(SearchResult searchResult) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("insert or replace into searches values (?, ?, ?, ?, CURRENT_TIMESTAMP)")){
            preparedStatement.setInt(1, searchResult.getPageID());
            preparedStatement.setString(2, searchResult.getTitle());
            preparedStatement.setString(3, searchResult.getSnippet());
            preparedStatement.setInt(4, searchResult.getScore());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {System.err.println(UIStrings.DB_UPDATESEARCHRESULT_ERROR + exception.getMessage());}
    }

    private SearchResult resultSetToSearchResult(ResultSet resultSet) throws SQLException {
        return new SearchResult(
                resultSet.getString(UIStrings.DB_TITLE_KEYWORD),
                resultSet.getInt(UIStrings.DB_ID_KEYWORD),
                resultSet.getString(UIStrings.DB_SNIPPET_KEYWORD),
                resultSet.getInt(UIStrings.DB_SCORE_KEYWORD),
                resultSet.getString(UIStrings.DB_LASTMODIFIED_KEYWORD));
    }
}
