package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by TurtleDesk on 3/13/2016.
 */
public class Game
{
    private GameFrame frame;
    private GameCanvas canvas;
    private GameTimer timer;
    private Map map;
    private GameInput input;

    private Hero hero;


    public Game()
    {
        //initializes frame and canvas
        frame = new GameFrame();
        frame.addWindowListener(frame);
        map = new Map();
        canvas = new GameCanvas(this);
        frame.add(canvas);
        frame.pack();
        canvas.createBufferStrategy(3);//buffer strategy needs to be added after canvas is added to frame
        canvas.bufferStrategy = canvas.getBufferStrategy();

        timer = new GameTimer(this);
        hero = new Hero(Assets.heroImage);

        frame.setVisible(true);
        input = new GameInput(this);
        canvas.addMouseListener(input);
        canvas.addKeyListener(input);
        timer.start();

    }




    public void tick()
    {
        //update stuff
        updateHeroPos();
        //draw stuff
        canvas.render();
    }

    //updates position of hero based on current state of the input
    //TODO: when hero collides, the hero should likely be placed next to the wall, rather than denied movement
    public void updateHeroPos()
    {



        if (input.isUpPressed())
        {
            if (heroCollides(hero.getXPos(), hero.getYPos() - hero.getSpeed())) {
                //do nothing
            } else {
                hero.incrementYPos(-hero.getSpeed());//TODO: Bounds checking on heroes location
            }
        }

        if (input.isDownPressed())
        {
            if (heroCollides(hero.getXPos(), hero.getYPos() + hero.getSpeed())) {
                //do nothing
            } else {
                hero.incrementYPos(hero.getSpeed());
            }
        }

        if (input.isLeftPressed())
        {
            if (heroCollides(hero.getXPos() -hero.getSpeed(), hero.getYPos())) {
                //do nothing
            } else {
                hero.incrementXPos(-hero.getSpeed());
            }
        }


        if (input.isRightPressed())
        {
            if (heroCollides(hero.getXPos() + hero.getSpeed(), hero.getYPos()))
            {
                //do nothing
            } else
            {
                hero.incrementXPos(hero.getSpeed());
            }
        }
    }

    public void setBlock(int x, int y, Block block)
    {
        map.currentMap[x][y] = block;
        canvas.generateBackground();
    }


    public boolean heroCollides(int newX, int newY)
    {


        Block heroBlockTypeNW = map.currentMap[(newX)/Block.WIDTH][(newY)/Block.HEIGHT];//block NW of hero that hero partially occupies
        Block heroBlockTypeNE = map.currentMap[(newX + (Block.WIDTH - 1))/Block.WIDTH][(newY)/Block.HEIGHT];//block NE of hero that hero partially occupies
        Block heroBlockTypeSW = map.currentMap[(newX)/Block.WIDTH][(newY + (Block.HEIGHT - 1))/Block.HEIGHT];//block SW of hero that hero partially occupies
        Block heroBlockTypeSE = map.currentMap[(newX + (Block.WIDTH - 1))/Block.WIDTH][(newY + (Block.HEIGHT - 1))/Block.HEIGHT];//block SE of hero that hero partially occupies

        return ((heroBlockTypeNW.isCollidable)  || (heroBlockTypeNE.isCollidable) ||
                (heroBlockTypeSW.isCollidable)  || (heroBlockTypeSE.isCollidable) );


    }

    public Map getMap()
    {
        return map;
    }

    public Hero getHero()
    {
        return hero;
    }


}
