package TurtleGame;

import java.awt.image.BufferedImage;

/**
 * Created by TurtleDesk on 2/17/2016.
 */
public class Hero
{

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
        int transColor = this.heroImage.getRGB(0,0);
        for (int i = 0; i < (IMAGE_WIDTH * IMAGE_HEIGHT); i++)
        {
            if(transColor == this.heroImage.getRGB(i % IMAGE_WIDTH, i/IMAGE_HEIGHT))
            {
                RGBArray[i] = 0x00000000;
            }
            else
            {
                RGBArray[i] = this.heroImage.getRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT);
            }
        }
        heroImage.setRGB(0, 0 , 30, 30,RGBArray, 0, 30);
        //initial position
        xPos = GameCanvas.IMAGE_WIDTH/2;
        yPos = GameCanvas.IMAGE_HEIGHT/2;
    }


    public int[] calcBlockLocation()
    {
        int[] heroBlockLocation = new int[2];
        heroBlockLocation[0] = xPos/30;
        heroBlockLocation[1] = yPos/30;
        return heroBlockLocation;
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