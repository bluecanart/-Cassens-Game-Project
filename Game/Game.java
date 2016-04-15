package game;

import MapGeneration.Floor;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

    Floor floor;
    private Hero hero;
    private long seed;
    int depth = 1;
    static final int ROOMWIDTH = 15;
    static final int ROOMHEIGHT = 9;
    private final int ROOMS = 15;
    final int FLOORSIZE = 8;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();


    public Game() throws FileNotFoundException
    {
        //initializes frame and canvas\
        Random myRand = new Random();
        seed = myRand.nextLong();
//        seed = 10;
        floor = new Floor(ROOMS, FLOORSIZE, seed, depth);
        frame = new GameFrame();
        frame.addWindowListener(frame);
        map = new Map(floor.getCurrentRoom(), ROOMWIDTH, ROOMHEIGHT);
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
        repopulateEnemies();

    }




    public void tick() throws FileNotFoundException
    {
        //update stuff
        updateHeroPos();
        checkHeroDamage();
        hero.decrementCooldowns();
        //draw stuff
        if (hero.isDead()) {
            System.out.println("You died!");
            System.exit(0);
        }
        canvas.render();
    }

    public void repopulateEnemies() {
        
        enemies.removeAll(enemies);
        
        for (int i = 0; i < ROOMHEIGHT; i++) {
            
            for (int s = 0; s < ROOMWIDTH; s++) {
                
                switch(floor.getCurrentRoom().layout[i][s]) {
                    
                    case '1':
                        enemies.add(new Enemy(Assets.enemyImage, s*Block.HEIGHT, (i)*Block.WIDTH));
                        //System.out.println("Enemy Added");
                        break;
                    
                }
                
            }
            
        }
        
    }
    
    public void checkHeroDamage() {
        
        boolean horizontalCollision = false;
        boolean verticalCollision = false;
        
        if(hero.getInvulnerable() <= 0) {
        
            for(Enemy enemy: enemies) {

                if((hero.getXPos() <= enemy.getXPos() + enemy.getHeroImage().getWidth() && hero.getXPos() >= enemy.getXPos()) || (hero.getXPos() + hero.getHeroImage().getWidth() <= enemy.getXPos() + enemy.getHeroImage().getWidth() && hero.getXPos() + hero.getHeroImage().getWidth() >= enemy.getXPos())) {

                    horizontalCollision = true;

                }

                if((hero.getYPos() <= enemy.getYPos() + enemy.getHeroImage().getHeight() && hero.getYPos() >= enemy.getYPos()) || (hero.getYPos() + hero.getHeroImage().getHeight() <= enemy.getYPos() + enemy.getHeroImage().getHeight() && hero.getYPos() + hero.getHeroImage().getHeight() >= enemy.getYPos())) {

                    verticalCollision = true;

                }

                if(horizontalCollision && verticalCollision) {

                    //System.out.println("Collision");
                    hero.takeDamage(10);
                    break;

                } else {

                    horizontalCollision = false;
                    verticalCollision = false;

                }

            }
        
        }
        
    }
    
    //updates position of hero based on current state of the input
    //TODO: when hero collides, the hero should likely be placed next to the wall, rather than denied movement
    public void updateHeroPos() throws FileNotFoundException
    {
        
        if (input.isControlPressed()) {
            
            if(hero.canRoll()) {
                hero.roll();
            }
            
        }
        
        if (input.isUpPressed())
        {
            if (heroCollides(hero.getXPos(), hero.getYPos() - hero.getSpeed())) {
                //do nothing
            } else {
                if(isExit(hero.getXPos(), hero.getYPos() - hero.getSpeed(), 'n') == 't') {
                    hero.incrementYPos(Block.HEIGHT*(ROOMHEIGHT-3));
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    floor.RoomUp();
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else if (isExit(hero.getXPos(), hero.getYPos() - hero.getSpeed(), 'n') == 'e') {
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    depth += 1;
                    floor = new Floor(ROOMS, FLOORSIZE, seed, depth);
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else {
                    hero.incrementYPos(-hero.getSpeed());
                }
            }
        }

        if (input.isDownPressed())
        {
            if (heroCollides(hero.getXPos(), hero.getYPos() + hero.getSpeed())) {
                //do nothing
            } else {
                if(isExit(hero.getXPos(), hero.getYPos() + hero.getSpeed(), 's') == 't') {
                    hero.incrementYPos(-Block.HEIGHT*(ROOMHEIGHT-3));
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    floor.RoomDown();
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else if (isExit(hero.getXPos(), hero.getYPos() + hero.getSpeed(), 's') == 'e') {
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    depth += 1;
                    floor = new Floor(ROOMS, FLOORSIZE, seed, depth);
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else {
                    hero.incrementYPos(hero.getSpeed());
                }
            }
        }

        if (input.isLeftPressed())
        {
            if (heroCollides(hero.getXPos() - hero.getSpeed(), hero.getYPos())) {
                //do nothing
            } else {
                if(isExit(hero.getXPos() - hero.getSpeed(), hero.getYPos(), 'w') == 't') {
                    hero.incrementXPos(Block.WIDTH*(ROOMWIDTH-3));
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    floor.RoomLeft();
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else if (isExit(hero.getXPos() - hero.getSpeed(), hero.getYPos(), 'w') == 'e') {
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    depth += 1;
                    floor = new Floor(ROOMS, FLOORSIZE, seed, depth);
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else {
                    hero.incrementXPos(-hero.getSpeed());
                }
            }
        }


        if (input.isRightPressed())
        {
            if (heroCollides(hero.getXPos() + hero.getSpeed(), hero.getYPos()))
            {
                //do nothing
            } else {
                if(isExit(hero.getXPos() + hero.getSpeed(), hero.getYPos(), 'e') == 't') {
                    hero.incrementXPos(-Block.WIDTH*(ROOMWIDTH-3));
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    floor.RoomRight();
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else if (isExit(hero.getXPos() + hero.getSpeed(), hero.getYPos(), 'e') == 'e') {
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    depth += 1;
                    floor = new Floor(ROOMS, FLOORSIZE, seed, depth);
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else {
                    hero.incrementXPos(hero.getSpeed());
                }
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
    
    public char isExit(int newX, int newY, char direction)
    {
        Block heroBlockTypeNW = map.currentMap[(newX)/Block.WIDTH][(newY)/Block.HEIGHT];//block NW of hero that hero partially occupies
        Block heroBlockTypeNE = map.currentMap[(newX + (Block.WIDTH - 1))/Block.WIDTH][(newY)/Block.HEIGHT];//block NE of hero that hero partially occupies
        Block heroBlockTypeSW = map.currentMap[(newX)/Block.WIDTH][(newY + (Block.HEIGHT - 1))/Block.HEIGHT];//block SW of hero that hero partially occupies
        Block heroBlockTypeSE = map.currentMap[(newX + (Block.WIDTH - 1))/Block.WIDTH][(newY + (Block.HEIGHT - 1))/Block.HEIGHT];//block SE of hero that hero partially occupies
        
        switch(direction) {
            
            case 'n':
                if(heroBlockTypeNW.getType() == Block.DOOR && heroBlockTypeNE.getType() == Block.DOOR) {
                    return 't';
                }
                break;
            case 'e':
                if(heroBlockTypeNE.getType() == Block.DOOR && heroBlockTypeSE.getType() == Block.DOOR) {
                    return 't';
                }
                break;
            case 's':
                if(heroBlockTypeSW.getType() == Block.DOOR && heroBlockTypeSE.getType() == Block.DOOR) {
                    return 't';
                }
                break;
            case 'w':
                if(heroBlockTypeNW.getType() == Block.DOOR && heroBlockTypeSW.getType() == Block.DOOR) {
                    return 't';
                }
                break;
                
        }
        
        if(heroBlockTypeNE.getType() == Block.EXIT || heroBlockTypeSE.getType() == Block.EXIT || heroBlockTypeNW.getType() == Block.EXIT || heroBlockTypeSW.getType() == Block.EXIT) {
            return 'e';
        }
        
        return '-';

    }

    public Map getMap()
    {
        return map;
    }

    public Hero getHero()
    {
        return hero;
    }
    
    public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }


}
