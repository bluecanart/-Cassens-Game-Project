package TurtleGame;

import java.awt.image.BufferedImage;

/**
 * Created by TurtleDesk on 2/17/2016.
 */
public class Hero
{
    public static final int GRASS = 0;
    public static final int ROCK = 1;
    public static final int IMAGE_WIDTH = 30;
    public static final int IMAGE_HEIGHT = 30;
    private int xPos;
    private int yPos;

    private int[] RGBArray;
    BufferedImage heroImage;

    public Hero(BufferedImage heroImage)
    {
        this.heroImage = heroImage;
        RGBArray = new int[IMAGE_WIDTH * IMAGE_HEIGHT];
        for (int i = 0; i < (IMAGE_WIDTH * IMAGE_HEIGHT); i++)
        {
            RGBArray[i] = this.heroImage.getRGB(i % IMAGE_WIDTH, i/IMAGE_HEIGHT);
        }
        //initial position in blocks (for now)
        xPos = 24 *30;
        yPos = 13 * 30;
    }

    public int[] getRGBArray()
    {
        return RGBArray;
    }

    public void incrementXPos(int increment)
    {
        xPos += increment;
    }
    public void incrementYPos(int increment)
    {
        yPos += increment;
    }

    public int getXPos()
    {
        return xPos;
    }
    public int getYPos()
    {
        return yPos;
    }
}