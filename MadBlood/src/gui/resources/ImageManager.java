package gui.resources;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;


public class ImageManager {
    private ImageManager() { }
    private static final HashMap<String, Image> images = new HashMap<>();

    /**
     * Gets an image saved inside the icons folder with the name received
     * in the filename parameter and returns it saves it in an HashMap for
     * future use instead of getting it again
     * @param filename
     * @return
     */
    public static Image getImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try (InputStream is = ImageManager.class.getResourceAsStream("icons/"+filename)) {
                image = new Image(is);
                images.put(filename,image);
            } catch (Exception e) {
                return null;
            }
        return image;
    }

    /**
     * Gets image outside the icons folder using a full path received
     * in the filename parameter and adds it to the HashMap for future use
     * @param filename
     * @return
     */
    public static Image getExternalImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try {
                image = new Image(filename);
                images.put(filename,image);
            } catch (Exception e) {
                return null;
            }
        return image;
    }

    /**
     * Removes image from HashMap
     * @param filename
     */
    public static void purgeImage(String filename) { images.remove(filename); }
}