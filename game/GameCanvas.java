package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class GameCanvas extends Canvas
{

    public BufferStrategy bufferStrategy;
    private BufferedImage backGround;

    //block size = 30x30 pixels
    public static final int IMAGE_WIDTH = 59 * Block.WIDTH;
    public static final int IMAGE_HEIGHT = 32 * Block.HEIGHT;
    public static int BLOCKS_WIDE = IMAGE_WIDTH / Block.WIDTH;//TODO: see if these are all needed
    public static int BLOCKS_TALL = IMAGE_HEIGHT / Block.HEIGHT;

    Game game;
    Map gameMap;


    public GameCanvas(Game game)
    {
        backGround = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        setBackground(Color.BLACK);//TODO: this prevented flickering if window was resized to be greater than the drawable area
        setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        this.game = game;
        this.gameMap = game.getMap();
        generateBackground();


    }


    //Generates the background image using the blocks array
    public void generateBackground()
    {
        for (int i = 0; i < gameMap.currentMap.length; i++)
        {
            for (int j = 0; j < gameMap.currentMap[0].length; j++)
            {
                int xPosition = i * Block.WIDTH;
                int yPosition = j * Block.HEIGHT;
                Graphics2D backGroundGraphics = (Graphics2D) backGround.getGraphics();
                backGroundGraphics.drawImage(gameMap.currentMap[i][j].getBlockImage(), xPosition, yPosition, null);
                backGroundGraphics.dispose();
            }
        }
    }


    public void render()
    {
        Graphics2D canvasGraphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        canvasGraphics.drawImage(backGround, 0, 0, null);//draws image to screen
        //canvasGraphics.drawImage(game.getHero().getHeroImage(), game.getHero().getXPos(), game.getHero().getYPos(), game.getHero().getXPos() + Hero.IMAGE_WIDTH, game.getHero().getYPos() + Hero.IMAGE_HEIGHT, 0, 0, Hero.IMAGE_WIDTH, Hero.IMAGE_HEIGHT, null);
        canvasGraphics.drawImage(game.getHero().getHeroImage(), game.getHero().getXPos(), game.getHero().getYPos(), null);
        bufferStrategy.show();
        canvasGraphics.dispose();

    }
}


