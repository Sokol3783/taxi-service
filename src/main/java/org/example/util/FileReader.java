package org.example.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import javax.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReader {

  private static final Logger log = LoggerFactory.getLogger(FileReader.class);

  public static Stream<String> readFile(String fileName) {
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(fileName);
    } catch (FileNotFoundException e) {
      log.error("File not found", e);
    }
    if (inputStream != null) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      return reader.lines();
    }
    return null;
  }

  public static InputStream readStreamFromWeb(ServletContextEvent sce, String path)
      throws IOException {
    try (InputStream input = sce.getServletContext().getResourceAsStream(path)) {
      return input;
    } catch (IOException e) {
      String message = "Data to stream not found";
      log.error(message, e);
      throw new IOException(message);
    }
  }

}
