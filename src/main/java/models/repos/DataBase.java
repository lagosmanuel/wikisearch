package models.repos;

import models.SearchResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DataBase {

  private static Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:sqlite:./hola.db");
  }

  public static void executeUpdate(String query) throws SQLException {
    Connection connection = getConnection();
    Statement statement = connection.createStatement();
    statement.setQueryTimeout(30);
    statement.executeUpdate(query);
    statement.close();
    connection.close();
  }

  public static void loadDatabase() {
    try {
      executeUpdate("create table searches (id INTEGER, title string PRIMARY KEY, snippet string, score int, lastmodified datetime)");
      executeUpdate("create table catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
      //If the DB was created before, a SQL error is reported but it is not harmfull...
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }


  public static void changeScore(String title, int score) {
    try {
      executeUpdate("update searches set score=%d, lastmodified=CURRENT_TIMESTAMP where title='%s'".formatted(score, title));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  public static void saveSearchResult(SearchResult searchResult) {
      try {
          executeUpdate("insert into searches values(%d, '%s', '%s', %d, CURRENT_TIMESTAMP);"
                  .formatted(searchResult.getPageID(), searchResult.getTitle(), searchResult.getSnippet(), searchResult.getScore()));
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }

  public static void saveInfo(String title, String extract) {
    try {
      executeUpdate("replace into catalog values(null, '"+ title + "', '"+ extract + "', 1)");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void deleteEntry(String title) {
    try {
      executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'" );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static int getSearchResultByTitle(String title) {
    int score = 0;
    Connection connection = null;
    try {
      connection = getConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      ResultSet rs = statement.executeQuery("select * from searches where title = '" + title + "'" );
      score = rs.next()? rs.getInt("score"):0;
    }
    catch(SQLException e) {
      System.err.println("Get title error " + e.getMessage());
    } finally {
      try {
        if(connection != null) connection.close();
      }
      catch(SQLException e) {
        System.err.println(e);
      }
    }

    return score;
  }

  public static ArrayList<String> getTitles() {
    ArrayList<String> titles = new ArrayList<>();
    Connection connection = null;
    try {
      connection = getConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      ResultSet rs = statement.executeQuery("select * from catalog");
      while(rs.next()) titles.add(rs.getString("title"));
    } catch(SQLException e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        if (connection != null) connection.close();
      } catch(SQLException e) {
        System.err.println(e);
      }

      return titles;
    }
  }

  public static String getExtract(String title) {
    Connection connection = null;
    try {
      connection = getConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'" );
      return rs.next()? rs.getString("extract"):"";
    }
    catch(SQLException e) {
      System.err.println("Get title error " + e.getMessage());
    } finally {
      try {
        if(connection != null) connection.close();
      }
      catch(SQLException e) {
        System.err.println(e);
      }
    }

    return null;
  }

  public static Collection<SearchResult> getSearchResults() {
    Collection<SearchResult> results = new ArrayList<>();
    Connection connection = null;
    try {
      connection = getConnection();
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      ResultSet rs = statement.executeQuery("select * from searches");
      while(rs.next()) {
        results.add(new SearchResult(rs.getString("title"), rs.getInt("id"), rs.getString("snippet"), rs.getInt("score"), rs.getString("lastmodified")));
      }
    } catch(SQLException e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        if (connection != null) connection.close();
      } catch(SQLException e) {
        System.err.println(e);
      }
    }
    return results;
  }
}

