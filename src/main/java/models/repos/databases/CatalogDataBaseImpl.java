package models.repos.databases;

import models.PageResult;
import utils.UIStrings;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class CatalogDataBaseImpl extends DataBase implements CatalogDataBase {
    public CatalogDataBaseImpl() {
        try (Statement statement = getConnection().createStatement()) {
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            statement.executeUpdate("create table if not exists catalog (id integer, title string primary key, extract string, source integer, thumbnail blob, url string)");
        } catch (SQLException exception) {System.err.println(UIStrings.DB_LOADDB_ERROR + exception.getMessage());}
    }

    public PageResult getPageResultByTitle(String title) {
        PageResult pageResult = null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("select * from catalog where title=?")) {
            preparedStatement.setString(1, title);
            preparedStatement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) pageResult = resultsetToPageResult(resultSet);
        } catch (SQLException exception) {System.err.println(UIStrings.DB_GETPAGEBYTITLE_ERROR + exception.getMessage());}
        return pageResult;
    }

    public Collection<String> getPageTitles() {
        Collection<String> titles = new ArrayList<>();
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            ResultSet resultSet = statement.executeQuery("select %s from catalog".formatted(UIStrings.DB_TITLE_KEYWORD));
            while(resultSet.next()) titles.add(resultSet.getString(UIStrings.DB_TITLE_KEYWORD));
        } catch (SQLException exception) {System.err.println(UIStrings.DB_GETPAGETITLES_ERROR + exception.getMessage());}
        return titles;
    }

    public void updatePage(PageResult pageResult) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("replace into catalog values(?, ?, ?, ?, ?, ?)")){
            preparedStatement.setInt(1, pageResult.getPageID());
            preparedStatement.setString(2, pageResult.getTitle());
            preparedStatement.setString(3, pageResult.getExtract());
            preparedStatement.setInt(4, pageResult.getSource());
            preparedStatement.setBytes(5, pageResult.getThumbnail());
            preparedStatement.setString(6, pageResult.getUrl());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {System.err.println(UIStrings.DB_UPDATEPAGE_ERROR + exception.getMessage());}
    }

    public void deletePageByTitle(String title) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("delete from catalog where title=?")) {
            preparedStatement.setString(1, title);
            preparedStatement.setQueryTimeout(UIStrings.DB_QUERY_TIMEOUT);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {System.err.println(UIStrings.DB_DELETEPAGE_ERROR + exception.getMessage());}
    }

    private PageResult resultsetToPageResult (ResultSet resultSet) throws SQLException {
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

