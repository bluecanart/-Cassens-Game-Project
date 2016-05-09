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
    protected static BufferedImage heroRollImage;
    protected static BufferedImage heroInvulnerableImage;
    protected static BufferedImage waterImage;
    protected static BufferedImage exitImage;
    protected static BufferedImage wallImage;
    protected static BufferedImage enemyImage;
    protected static BufferedImage projectileImage;
    protected static BufferedImage enemyProjectileImage;
    protected static BufferedImage lockedExitImage;
    //loads images
    static
    {
        try
        {

            for (int i = 0; i < grassImages.length; i++)
            {
                grassImages[i] = ImageIO.read(new File("./src/media/Grass" + (i + 1) + ".png"));
                ImageConverter.convertImage(grassImages[i], BufferedImage.TYPE_4BYTE_ABGR);
            }

            rockImage = ImageIO.read(new File("./src/media/Rock.png"));
            ImageConverter.convertImage(rockImage, BufferedImage.TYPE_4BYTE_ABGR);

            //hero images
            
            heroImage = ImageIO.read(new File("./src/media/Hero.png"));
            heroImage = ImageConverter.convertImage(heroImage, BufferedImage.TYPE_4BYTE_ABGR);
            
            int IMAGE_WIDTH = heroImage.getWidth();
            int IMAGE_HEIGHT = heroImage.getHeight();
            
            int[] RGBArray = new int[IMAGE_WIDTH * IMAGE_HEIGHT];

            int transColor = heroImage.getRGB(0,0);
            for (int i = 0; i < (IMAGE_WIDTH * IMAGE_HEIGHT); i++)
            {
                if(transColor == heroImage.getRGB(i % IMAGE_WIDTH, i/IMAGE_HEIGHT))
                {
                    heroImage.setRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT, 0);
                    RGBArray[i] = 0x00000000;
                }
                else
                {
                    RGBArray[i] = heroImage.getRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT);
                }
                
            }
            
            heroImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            heroImage.setRGB(0,0,32, 32,RGBArray, 0 ,32);
            
            //Roll
            
            heroRollImage = ImageIO.read(new File("./src/media/HeroRoll.png"));
            heroRollImage = ImageConverter.convertImage(heroRollImage, BufferedImage.TYPE_4BYTE_ABGR);

            projectileImage = ImageIO.read(new File("./src/media/Projectile.png"));
            projectileImage = ImageConverter.convertImage(projectileImage, BufferedImage.TYPE_4BYTE_ABGR);


            transColor = projectileImage.getRGB(0,0);

            for (int i = 0; i < (IMAGE_WIDTH * IMAGE_HEIGHT); i++)
            {
                if(transColor == projectileImage.getRGB(i % IMAGE_WIDTH, i/IMAGE_HEIGHT))
                {
                    projectileImage.setRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT, 0);
                    RGBArray[i] = 0x00000000;
                }
                else
                {
                    RGBArray[i] = projectileImage.getRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT);
                }

            }
            projectileImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            projectileImage.setRGB(0,0,32,32,RGBArray, 0, 32);

            IMAGE_WIDTH = heroRollImage.getWidth();
            IMAGE_HEIGHT = heroRollImage.getHeight();
            
            RGBArray = new int[IMAGE_WIDTH * IMAGE_HEIGHT];

            transColor = heroRollImage.getRGB(0,0);
            
            for (int i = 0; i < (IMAGE_WIDTH * IMAGE_HEIGHT); i++)
            {
                if(transColor == heroRollImage.getRGB(i % IMAGE_WIDTH, i/IMAGE_HEIGHT))
                {
                    heroRollImage.setRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT, 0);
                    RGBArray[i] = 0x00000000;
                }
                else
                {
                    RGBArray[i] = heroRollImage.getRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT);
                }
                
            }
            
            heroRollImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            heroRollImage.setRGB(0,0,32, 32,RGBArray, 0 ,32);
            
            //Invulnerable
            
            heroInvulnerableImage = ImageIO.read(new File("./src/media/HeroInvulnerable.png"));
            heroInvulnerableImage = ImageConverter.convertImage(heroInvulnerableImage, BufferedImage.TYPE_4BYTE_ABGR);
            
            IMAGE_WIDTH = heroInvulnerableImage.getWidth();
            IMAGE_HEIGHT = heroInvulnerableImage.getHeight();
            
            RGBArray = new int[IMAGE_WIDTH * IMAGE_HEIGHT];

            transColor = heroInvulnerableImage.getRGB(0,0);
            
            for (int i = 0; i < (IMAGE_WIDTH * IMAGE_HEIGHT); i++)
            {
                if(transColor == heroInvulnerableImage.getRGB(i % IMAGE_WIDTH, i/IMAGE_HEIGHT))
                {
                    heroInvulnerableImage.setRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT, 0);
                    RGBArray[i] = 0x00000000;
                }
                else
                {
                    RGBArray[i] = heroInvulnerableImage.getRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT);
                }
                
            }
            
            heroInvulnerableImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            heroInvulnerableImage.setRGB(0,0,32, 32,RGBArray, 0 ,32);
            
            enemyProjectileImage = ImageIO.read(new File("./src/media/Projectile2.png"));
            enemyProjectileImage = ImageConverter.convertImage(enemyProjectileImage, BufferedImage.TYPE_4BYTE_ABGR);


            transColor = enemyProjectileImage.getRGB(0,0);

            for (int i = 0; i < (IMAGE_WIDTH * IMAGE_HEIGHT); i++)
            {
                if(transColor == enemyProjectileImage.getRGB(i % IMAGE_WIDTH, i/IMAGE_HEIGHT))
                {
                    enemyProjectileImage.setRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT, 0);
                    RGBArray[i] = 0x00000000;
                }
                else
                {
                    RGBArray[i] = enemyProjectileImage.getRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT);
                }

            }
            enemyProjectileImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            enemyProjectileImage.setRGB(0,0,32,32,RGBArray, 0, 32);
            
            enemyImage = ImageIO.read(new File("./src/media/Enemy.png"));
            enemyImage = ImageConverter.convertImage(enemyImage, BufferedImage.TYPE_4BYTE_ABGR);
            
            IMAGE_WIDTH = enemyImage.getWidth();
            IMAGE_HEIGHT = enemyImage.getHeight();
            
            RGBArray = new int[IMAGE_WIDTH * IMAGE_HEIGHT];

            transColor = enemyImage.getRGB(0,0);
            
            for (int i = 0; i < (IMAGE_WIDTH * IMAGE_HEIGHT); i++)
            {
                if(transColor == enemyImage.getRGB(i % IMAGE_WIDTH, i/IMAGE_HEIGHT))
                {
                    enemyImage.setRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT, 0);
                    RGBArray[i] = 0x00000000;
                }
                else
                {
                    RGBArray[i] = enemyImage.getRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT);
                }
                
            }
            
            enemyImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            enemyImage.setRGB(0,0,32, 32,RGBArray, 0 ,32);

            waterImage = ImageIO.read(new File("./src/media/Water.png"));
            waterImage = ImageConverter.convertImage(waterImage, BufferedImage.TYPE_4BYTE_ABGR);
            
            exitImage = ImageIO.read(new File("./src/media/Exit.png"));
            exitImage = ImageConverter.convertImage(exitImage, BufferedImage.TYPE_4BYTE_ABGR);
            
            lockedExitImage = ImageIO.read(new File("./src/media/Exit.png"));
            lockedExitImage = ImageConverter.convertImage(exitImage, BufferedImage.TYPE_4BYTE_ABGR);
            
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
