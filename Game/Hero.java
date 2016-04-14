package game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by TurtleDesk on 2/17/2016.
 */
public class Hero
{

    public static final int IMAGE_WIDTH = 32;
    public static final int IMAGE_HEIGHT = 32;
    private int xPos;
    private int yPos;
    private int speed;//pixels moved per tick

    private int[] RGBArray;
    private BufferedImage heroImage;

    public Hero(BufferedImage heroImage)
    {
        this.heroImage = heroImage;
        RGBArray = new int[IMAGE_WIDTH * IMAGE_HEIGHT];

        //TODO: perhaps move the transparency conversion elsewhere
        int transColor = this.heroImage.getRGB(0,0);
        for (int i = 0; i < (IMAGE_WIDTH * IMAGE_HEIGHT); i++)
        {
            if(transColor == this.heroImage.getRGB(i % IMAGE_WIDTH, i/IMAGE_HEIGHT))
            {
                heroImage.setRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT, 0);
                RGBArray[i] = 0x00000000;
            }
            else
            {
                RGBArray[i] = this.heroImage.getRGB(i % IMAGE_WIDTH, i / IMAGE_HEIGHT);
            }
        }
        //heroImage.setRGB(0, 0 , 32, 32,RGBArray, 0, 32);
        Assets.heroImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Assets.heroImage.setRGB(0,0,32, 32,RGBArray, 0 ,32);
        //initial position
        xPos = GameCanvas.IMAGE_WIDTH/2-heroImage.getWidth()/2;
        yPos = GameCanvas.IMAGE_HEIGHT/2-heroImage.getHeight()/2;
        this.speed = 4;
    }



    public int[] getRGBArray()
    {
        return RGBArray;
    }
    
    public void setXPos(int newPos) 
    {
        xPos = newPos;
    }
    
    public void setYPos(int newPos) 
    {
        yPos = newPos;
    }

    public BufferedImage getHeroImage()
    {
        return heroImage;
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

    public int getSpeed()
    {
        return speed;
    }

}