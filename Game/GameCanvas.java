package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;


public class GameCanvas extends Canvas
{

    public BufferStrategy bufferStrategy;
    private BufferedImage backGround;
    private BufferedImage map;

    //block size = 30x30 pixels
    public static final int ROOMWIDTH = 15;
    public static final int ROOMHEIGHT = 9;
    public static final int IMAGE_WIDTH = ROOMWIDTH * Block.WIDTH;
    public static final int IMAGE_HEIGHT = ROOMHEIGHT * Block.HEIGHT;
    public static int BLOCKS_WIDE = ROOMWIDTH;
    public static int BLOCKS_TALL = ROOMHEIGHT;

    Game game;


    public GameCanvas(Game game)
    {
        backGround = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        map = new BufferedImage(ROOMWIDTH * game.FLOORSIZE + 6, ROOMHEIGHT * game.FLOORSIZE + 6, BufferedImage.TYPE_INT_ARGB);
        setBackground(Color.BLACK);//TODO: this prevented flickering if window was resized to be greater than the drawable area
        setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        this.game = game;
        generateBackground();
        generateMap();


    }


    //Generates the background image using the blocks array
    public void generateBackground()
    {
        for (int i = 0; i < game.getMap().currentMap.length; i++)
        {
            for (int j = 0; j < game.getMap().currentMap[0].length; j++)
            {
                int xPosition = i * Block.WIDTH;
                int yPosition = j * Block.HEIGHT;
                Graphics2D backGroundGraphics = (Graphics2D) backGround.getGraphics();
                backGroundGraphics.drawImage(game.getMap().currentMap[i][j].getBlockImage(), xPosition, yPosition, null);
                backGroundGraphics.dispose();
            }
        }
    }
    
    public void generateMap() {
        
        map = new BufferedImage(ROOMWIDTH * game.FLOORSIZE + 6, ROOMHEIGHT * game.FLOORSIZE + 6, BufferedImage.TYPE_INT_ARGB);
        
        java.util.Map<Character, Color> myMap = new HashMap<Character, Color>();
        myMap.put('w', Color.BLACK);
        myMap.put('-', new Color(200, 200, 200, 130));
        myMap.put('e', new Color(237, 114, 107, 130));
        myMap.put('s', new Color(97, 199, 248, 130));
        myMap.put('r', new Color(125, 114, 98, 130));
        myMap.put('d', new Color(255, 255, 255, 130));
        Graphics2D g = (Graphics2D) map.getGraphics();
        
        g.setColor(new Color(100, 100, 100, 130));
        g.fillRect(3, 3, ROOMWIDTH * game.FLOORSIZE, ROOMHEIGHT * game.FLOORSIZE);
        
        for (int i = 0; i < game.FLOORSIZE; i++) {
            
            for (int j = 0; j < game.FLOORSIZE; j++) {
                
                if (game.floor.floor[i][j] != null) {
                    
                    if (i == game.floor.curY && j == game.floor.curX) {
                    
                        myMap.put('-', new Color(100, 100, 100, 130));
                    
                    }
                    
                    for (int x = 0; x < ROOMHEIGHT; x++) {
                            
                        for(int y = 0; y < ROOMWIDTH; y++) {
                                
                            g.setColor(myMap.get(game.floor.floor[i][j].layout[x][y]));
                            g.fillRect(j*ROOMWIDTH+(y)*1+3, i*ROOMHEIGHT+(x)*1+3, 1, 1);
                                
                        }
                            
                    }
                    
                        if (i == game.floor.curY && j == game.floor.curX) {
                    
                            myMap.put('-', new Color(200, 200, 200, 130));
                    
                        }
                        
                }
                
            }
            
        }
        
        g.setColor(new Color(0, 0, 0, 110));
        g.fillRect(0, 0, ROOMWIDTH * game.FLOORSIZE + 6, 3);
        g.fillRect(0, 0, 3, ROOMWIDTH * game.FLOORSIZE + 6);
        g.fillRect(ROOMWIDTH * game.FLOORSIZE + 3, 0, 3, ROOMWIDTH * game.FLOORSIZE + 6);
        g.fillRect(0, ROOMHEIGHT * game.FLOORSIZE + 3, ROOMWIDTH * game.FLOORSIZE + 6, 3);
        g.dispose();
        
    }


    public void render()
    {
        Graphics2D canvasGraphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        canvasGraphics.drawImage(backGround, 0, 0, null);//draws image to screen
        
        //canvasGraphics.drawImage(game.getHero().getHeroImage(), game.getHero().getXPos(), game.getHero().getYPos(), game.getHero().getXPos() + Hero.IMAGE_WIDTH, game.getHero().getYPos() + Hero.IMAGE_HEIGHT, 0, 0, Hero.IMAGE_WIDTH, Hero.IMAGE_HEIGHT, null);
        canvasGraphics.drawImage(game.getHero().getHeroImage(), game.getHero().getXPos(), game.getHero().getYPos(), null);
        canvasGraphics.drawImage(map, IMAGE_WIDTH - (ROOMWIDTH*game.FLOORSIZE) - 6 - 3, 3, null);//draws image to screen
        canvasGraphics.setColor(new Color(255, 255, 255, 150));
        canvasGraphics.fillRect(4, 10, 90, 13);
        canvasGraphics.setColor(Color.BLACK);
        canvasGraphics.drawString("Current Floor: " + game.depth, 6, 20);
        bufferStrategy.show();
        canvasGraphics.dispose();

    }
}


