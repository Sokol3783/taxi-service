package org.example.util;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReader {

  private static final Logger log = LoggerFactory.getLogger(FileReader.class);

  public static InputStream getFileFromResourceAsStream(Class className, String fileName) {
    ClassLoader classLoader = className.getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return inputStream;
    }
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
