package common;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.apache.logging.log4j.core.util.Throwables.rethrow;

public class FileReader {

    public static String readFile(String filepath) {
        try {
            URL resourceUrl = FileReader.class.getClassLoader().getResource(filepath);

            // Ensure the resource was found
            Objects.requireNonNull(resourceUrl, "Resource file not found: " + filepath);

            // Convert the URL to a Path object
            Path path = Paths.get(resourceUrl.toURI());

            // Read all bytes from the file into a string using standard Charset
            return new String(Files.readAllBytes(path));
        } catch (Exception ex) {
            rethrow(ex);
            return null;
        }
    }
}
