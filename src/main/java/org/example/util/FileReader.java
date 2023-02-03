package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import java.io.IOException;
import java.io.InputStream;

public class FileReader {

    private static final Logger log = LoggerFactory.getLogger(FileReader.class);

    public static InputStream getFileFromResourceAsStream(Class className, String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = className.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
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
