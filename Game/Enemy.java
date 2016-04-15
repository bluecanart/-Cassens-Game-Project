package game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by TurtleDesk on 2/17/2016.
 */
public class Enemy
{

    public static final int IMAGE_WIDTH = 32;
    public static final int IMAGE_HEIGHT = 32;
    private int xPos;
    private int yPos;
    private int speed;//pixels moved per tick
    private int health;

    private int[] RGBArray;
    private BufferedImage enemyImage;

    public Enemy(BufferedImage enemyImage, int xPos, int yPos)
    {
        this.enemyImage = enemyImage;
        //initial position
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = 4;
    }



    public int[] getRGBArray()
    {
        return RGBArray;
    }
    
    public void takeDamage(int amount) {
        
        health -= amount;
        
    }
    
    public int getHealthLeft() {
        
        return health;
        
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
        return enemyImage;
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