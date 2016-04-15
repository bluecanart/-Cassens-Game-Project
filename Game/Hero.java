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
    private int invulnerable = 0;
    private boolean rolling = false;
    private int rollCooldown = 0;
    private final int STARTINGHEALTH = 100;
    private int health = STARTINGHEALTH;
    private boolean alive = true;

    private BufferedImage heroImage;

    public Hero(BufferedImage heroImage)
    {
        this.heroImage = heroImage;
        
        //initial position
        xPos = GameCanvas.IMAGE_WIDTH/2-heroImage.getWidth()/2;
        yPos = GameCanvas.IMAGE_HEIGHT/2-heroImage.getHeight()/2;
        this.speed = 4;
    }

    public void takeDamage(int amount) {
        
        if (invulnerable <= 0) {
            health -= amount;
            if (health <= 0) {
                alive = false;
            } else {
                invulnerable = 60;
                heroImage = Assets.heroInvulnerableImage;                
            }
        }
        
    }
    
    public boolean canRoll() {
        
        return rollCooldown < 1;
        
    }
    
    public boolean isDead() {
        
        return !alive;
        
    }
    
    public int getHealthLeft() {
        
        return health;
        
    }
    
    public int getInvulnerable() {
        
        return invulnerable;
        
    }
    
    public void roll() {
        
        this.speed = 8;
        invulnerable = 15;
        rolling = true;
        rollCooldown = 100;
        heroImage = Assets.heroRollImage;
        
    }
    
    public void decrementCooldowns() {
        
        if(rollCooldown > 0) {
            
            rollCooldown -= 1;
            
        }
        
        if(rollCooldown == 85) {
            
            rolling = false;
            speed = 4;
            heroImage = Assets.heroImage;
            
        }
        
        if (invulnerable > 0) {
            
            invulnerable -= 1;
            if (invulnerable <= 0) {
                
                heroImage = Assets.heroImage;
                
            }
            
        }
        
        
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
    
    public int getRollCooldown()
    {
        return rollCooldown;
    }

}