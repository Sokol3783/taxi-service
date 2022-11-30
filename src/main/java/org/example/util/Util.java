package org.example.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

  public static void runSQLScript(String path, Connection connection) {

    try (Statement statement = connection.createStatement()) {
      List<String> queriesList = Stream
          .of(FileReader.readFile(path).collect(Collectors.joining()).split(";"))
          .collect(Collectors.toList());
      queriesList.forEach(sqlQuery -> {
        try {
          statement.execute(sqlQuery);
        } catch (SQLException e) {
          e.getMessage();
        }
      });
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  static class FileReader {
    public static Stream<String> readFile(String fileName) {
      InputStream inputStream = null;
      try {
        inputStream = new FileInputStream(fileName);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      if (inputStream != null) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines();
      }
      return null;
    }
  }

}
