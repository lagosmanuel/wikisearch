package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader {
    public static byte[] fetchImage(String imageurl) throws IOException {
        try (InputStream in = new URL(imageurl).openStream()){
            return in.readAllBytes();
        }
    }
}
