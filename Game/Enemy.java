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
    private int maxHealth;
    protected boolean isAlive = true;
    int damage;
    private int shotSpeed;
    int fireCD;
    int currfireCD;

    private int[] RGBArray;
    private BufferedImage enemyImage;

    public Enemy(BufferedImage enemyImage, int xPos, int yPos)
    {
        this.enemyImage = enemyImage;
        //initial position
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = 4;
        shotSpeed = 3;
        health = 20;
        maxHealth = 20;
        isAlive = true;
        fireCD = 40;
        currfireCD = 40;
        damage = 5;
    }
    
    public Enemy(BufferedImage enemyImage, int xPos, int yPos, int shotSpeed, int speed, int maxHealth, int curfireCD, int damage)
    {
        this.enemyImage = enemyImage;
        //initial position
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.shotSpeed = shotSpeed;
        this.maxHealth = maxHealth;
        health = maxHealth;
        fireCD = curfireCD;
        this.currfireCD = curfireCD;
        this.damage = damage;
    }



    public int[] getRGBArray()
    {
        return RGBArray;
    }
    
    public void takeDamage(int amount) {
        
        health -= amount;
        if (health <= 0)
        {
            health = 0;
            isAlive = false;

        }
        
    }
    
    public int getHealthLeft() {
        
        return health;
        
    }
    public int getShotSpeed() {
        
        return shotSpeed;
        
    }
    public int getMaxHealth()
    {
        return maxHealth;
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