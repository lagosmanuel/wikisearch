package models.repos.databases;

import models.PageResult;
import models.repos.DataBase;
import utils.UIStrings;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class CatalogDataBase extends DataBase {
    public static void load() {
        try{ executeUpdate("create table if not exists catalog (id integer, title string primary key, extract string, source integer, thumbnail blob, url string)");
        } catch (SQLException e) {System.err.println(UIStrings.DB_LOADDB_ERROR + e.getMessage());}
    }

    public static PageResult getPageByTitle(String title) {
        PageResult pageResult = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from catalog where title=?");
            preparedStatement.setString(1, title);
            preparedStatement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) pageResult = resultsetToPageResult(resultSet);
        } catch (SQLException e) {System.err.println(UIStrings.DB_GETPAGEBYTITLE_ERROR + e.getMessage());}
        return pageResult;
    }

    public static Collection<String> getPageTitles() {
        Collection<String> titles = new ArrayList<>();
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet resultSet = statement.executeQuery("select %s from catalog".formatted(UIStrings.DB_TITLE_KEYWORD));
            while(resultSet.next()) titles.add(resultSet.getString(UIStrings.DB_TITLE_KEYWORD));
        } catch (SQLException e) {System.err.println(UIStrings.DB_GETPAGETITLES_ERROR + e.getMessage());}
        return titles;
    }

    public static void updatePage(PageResult pageResult) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("replace into catalog values(?, ?, ?, ?, ?, ?)")){
            preparedStatement.setInt(1, pageResult.getPageID());
            preparedStatement.setString(2, pageResult.getTitle());
            preparedStatement.setString(3, pageResult.getExtract());
            preparedStatement.setInt(4, pageResult.getSource());
            preparedStatement.setBytes(5, pageResult.getThumbnail());
            preparedStatement.setString(6, pageResult.getUrl());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {System.err.println(UIStrings.DB_UPDATEPAGE_ERROR + e.getMessage());}
    }

    public static void deletePageByTitle(String title) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from catalog where title=?");
            preparedStatement.setString(1, title);
            preparedStatement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {System.err.println(UIStrings.DB_DELETEPAGE_ERROR + e.getMessage());}
    }

    private static PageResult resultsetToPageResult (ResultSet resultSet) throws SQLException {
        return new PageResult(
            resultSet.getString(UIStrings.DB_TITLE_KEYWORD),
            resultSet.getInt(UIStrings.DB_ID_KEYWORD),
            resultSet.getString(UIStrings.DB_EXTRACT_KEYWORD),
            resultSet.getInt(UIStrings.DB_SOURCE_KEYWORD),
            resultSet.getBytes(UIStrings.DB_THUMBNAIL_KEYWORD),
            resultSet.getString(UIStrings.DB_URL_KEYWORD)
        );
    }
}

