package game;

/*
*
* Stores images used by the game
*
*
 */


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//TODO: add method for adding/removing assets being stored by the class
public class Assets
{

    public static final int GRASS_IMAGE_COUNT = 9;
    protected static BufferedImage[] grassImages = new  BufferedImage[GRASS_IMAGE_COUNT];
    protected static BufferedImage rockImage;
    protected static BufferedImage heroImage;
    protected static BufferedImage waterImage;
    protected static BufferedImage exitImage;
    protected static BufferedImage wallImage;
    //loads images
    static
    {
        try
        {
            //TODO: this is fragile, consider what the desired file/folder naming convention will be before enforcing a particular way too far
            for (int i = 0; i < grassImages.length; i++)
            {
                grassImages[i] = ImageIO.read(new File("./src/media/Grass" + (i + 1) + ".png"));
                ImageConverter.convertImage(grassImages[i], BufferedImage.TYPE_4BYTE_ABGR);
            }

            rockImage = ImageIO.read(new File("./src/media/Rock.png"));
            ImageConverter.convertImage(rockImage, BufferedImage.TYPE_4BYTE_ABGR);

            heroImage = ImageIO.read(new File("./src/media/Hero.png"));
            heroImage = ImageConverter.convertImage(heroImage, BufferedImage.TYPE_4BYTE_ABGR);

            waterImage = ImageIO.read(new File("./src/media/Water.png"));
            waterImage = ImageConverter.convertImage(waterImage, BufferedImage.TYPE_4BYTE_ABGR);
            
            exitImage = ImageIO.read(new File("./src/media/Exit.png"));
            exitImage = ImageConverter.convertImage(exitImage, BufferedImage.TYPE_4BYTE_ABGR);
            
            wallImage = ImageIO.read(new File("./src/media/Wall.png"));
            wallImage = ImageConverter.convertImage(wallImage, BufferedImage.TYPE_4BYTE_ABGR);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private Assets()
    {
        //constructer is private, class should never be instantiated (for now)
    }
}
