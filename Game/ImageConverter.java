package Game;//TODO: this class belongs in another package

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by TurtleDesk on 3/14/2016.
 *
 * Somehow when I changed the the tile images from 30x30 to 32x32 the transparency broke, the cause was the images
 * were now of the type BufferedImage.TYPE_3BYTE_BGR. Was slightly puzzling.
 * is for enforcing a particular image type to be used, currently (3/14/16) BufferedImage.TYPE_4BYTE_ABGR is
 * what is being enforced.
 */
public class ImageConverter
{


    //converts a BufferedImage to a BufferedImage of a specified type.
    public static BufferedImage convertImage(BufferedImage img, int type)
    {

        BufferedImage convertedImage = new BufferedImage(img.getWidth(), img.getHeight(), type);
        Graphics imgGraphics = convertedImage.getGraphics();
        imgGraphics.drawImage(img, 0, 0, null);
        imgGraphics.dispose();
        return convertedImage;
    }
}
