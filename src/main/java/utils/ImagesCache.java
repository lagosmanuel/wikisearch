package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ImagesCache {
    private final Dictionary cache;
    private static ImagesCache instance;

    private ImagesCache() {
        cache = new Hashtable();
    }

    public static ImagesCache getInstance() {
        if (instance == null) instance = new ImagesCache();
        return instance;
    }

    public void saveImageToCache(byte[] image, String key) {
        try {
            String url = UIStrings.IMAGECACHE_BASEURL + "/%s".formatted(key);
            if (cache.get(url) != null || image == null) return;
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
            cache.put(new URL(url), bufferedImage);
        } catch (IOException e) {System.err.println(UIStrings.IMAGECACHE_DIALOG_SAVEERROR);}
    }

    public Dictionary getCache() {
        return cache;
    }
}
