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
    protected Map map;
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
    protected Projectile[] projectiles = new Projectile[500];  //buffer for projectiles
    private int currProjIndex = 0;
    public static final int FIRE_COOLDOWN = 15;
    protected int currFireCooldown;
    public int score = 0;


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
        currFireCooldown = 0;

    }




    public void tick() throws FileNotFoundException
    {
        //update stuff
        updateHeroPos();
        checkHeroDamage();
        hero.decrementCooldowns();
        moveEnemies();
        if (currFireCooldown > 0)
        {
            currFireCooldown  -= 1;
        }
        for (Enemy enemy: enemies)
        {
            enemy.currfireCD -= 1;
            if (enemy.currfireCD <= 0)
            {
                enemy.currfireCD = enemy.fireCD;
                double dir = (Math.atan2(((hero.getYPos() + 16) -  (enemy.getYPos())) , ((hero.getXPos() + 16) -  (enemy.getXPos()))));
                projectiles[currProjIndex] = new Projectile(dir, enemy.getShotSpeed(), enemy.damage, false, enemy.getXPos(), enemy.getYPos(), 160, this);
                currProjIndex = (currProjIndex + 1) % projectiles.length;

            }
        }
        
        for (int i = 0; i < projectiles.length; i++) {
            
            if(projectiles[i] != null && projectiles[i].life <= 0) {
                
                projectiles[i] = null;
                
            }
            
        }
        
        //draw stuff
        if (hero.isDead()) {
            System.out.println("You died!");
            System.out.println("Final Score: " + score);
            System.exit(0);
        }
        checkProjectileCollisions();
        canvas.render();
    }
    
    public void checkProjectileCollisions() {
            
            //checks projectile collisions
            for (int i = 0; i < projectiles.length; i++)
            {
                if (projectiles[i] != null)
                {
                    projectiles[i].step();

                    for (Enemy enemy : enemies)
                    {
                        if (projectiles[i].friendly && (Math.abs(projectiles[i].x - enemy.getXPos()) <= 16) && (Math.abs((projectiles[i].y - enemy.getYPos())) <= 16))
                        {
                            enemy.takeDamage(projectiles[i].damage);
                            projectiles[i] = null;
                            if(!enemy.isAlive)
                            {
                                enemies.remove(enemy);
                                score += 1;
                                if(enemies.size() <= 0) {

                                    floor.clearCurrentRoom();

                                    for (int t = 0; t < ROOMWIDTH; t++) {

                                        for (int s = 0; s < ROOMHEIGHT; s++) {

                                            if(map.currentMap[t][s].getType() == Block.LOCKEDDOOR) {

                                                map.currentMap[t][s].setType(Block.DOOR);

                                            }

                                            if(map.currentMap[t][s].getType() == Block.LOCKEDEXIT) {

                                                map.currentMap[t][s].setType(Block.EXIT);

                                            }

                                        }

                                    }

                                    canvas.generateBackground();

                                }
                            }
                            break;
                        }

                    }

                    if(projectiles[i] != null && !projectiles[i].friendly && (Math.abs(projectiles[i].x - hero.getXPos()) <= 16) && (Math.abs((projectiles[i].y - hero.getYPos())) <= 16))
                    {
                        hero.takeDamage((projectiles[i].damage));
                        projectiles[i] = null;
                    }

                    }


                    if (projectiles[i] != null && projectiles[i].collides())
                {
                    if (Main.debugMode)
                    {
                        System.out.println("Projectile " + i + " Collided!");
                    }
                    projectiles[i] = null;
                }

            }
            
    }
    
    public void repopulateEnemies() {

        if(!floor.getCurrentRoom().cleared) {

            enemies.removeAll(enemies);

            for (int i = 0; i < ROOMHEIGHT; i++) {

                for (int s = 0; s < ROOMWIDTH; s++) {

                    switch(floor.getCurrentRoom().layout[i][s]) {

                        
                        //BufferedImage enemyImage, int xPos, int yPos, int shotSpeed, int speed, int maxHealth, int curfireCD, int damage
                        case '1':
                            //snake
                            enemies.add(new Enemy(Assets.enemyImage, s*Block.HEIGHT, (i)*Block.WIDTH, 0, 2, 25, 8, 10));
                            //System.out.println("Enemy Added");
                            break;
                            
                        case '2':
                            //turret
                            enemies.add(new Enemy(Assets.enemyImage2, s*Block.HEIGHT, (i)*Block.WIDTH, 4, 0, 20, 40, 5));
                            //System.out.println("Enemy Added");
                            break;
                            
                        case '3':
                            //glasscannon
                            enemies.add(new Enemy(Assets.enemyImage3, s*Block.HEIGHT, (i)*Block.WIDTH, 2, 1, 15, 35, 7));
                            //System.out.println("Enemy Added");
                            break;

                    }

                }

            }


            if(enemies.size() > 0) {

                for (int i = 0; i < ROOMWIDTH; i++) {

                    for (int s = 0; s < ROOMHEIGHT; s++) {

                        if(map.currentMap[i][s].getType() == Block.DOOR) {

                            map.currentMap[i][s].setType(Block.LOCKEDDOOR);

                        }
                        
                        if(map.currentMap[i][s].getType() == Block.EXIT) {

                            map.currentMap[i][s].setType(Block.LOCKEDEXIT);

                        }

                    }

                }

            } else {
                
                floor.clearCurrentRoom();
                
            }

        canvas.generateBackground();
        
        }
        
    }
    
    public void moveEnemies() {
        
        for(int i = 0; i < enemies.size(); i++) {
            
            if(enemies.get(i).getSpeed() > 0) {
                
                int xDir = hero.getXPos() - enemies.get(i).getXPos();
                int yDir = hero.getYPos() - enemies.get(i).getYPos();
                int xDist = Math.abs(xDir);
                int yDist = Math.abs(yDir);
                
                if (xDist > yDist && !collides(enemies.get(i).getXPos() + (enemies.get(i).getSpeed() * Integer.signum(xDir)), enemies.get(i).getYPos())){
                    enemies.get(i).setXPos(enemies.get(i).getXPos() + (enemies.get(i).getSpeed() * Integer.signum(xDir)));
                } else if (!collides(enemies.get(i).getXPos(), enemies.get(i).getYPos()  + (enemies.get(i).getSpeed() * Integer.signum(yDir)))){
                    enemies.get(i).setYPos(enemies.get(i).getYPos() + (enemies.get(i).getSpeed() * Integer.signum(yDir)));
                } else if (!collides(enemies.get(i).getXPos() + (enemies.get(i).getSpeed() * Integer.signum(xDir)), enemies.get(i).getYPos())) {
                    enemies.get(i).setXPos(enemies.get(i).getXPos() + (enemies.get(i).getSpeed() * Integer.signum(xDir)));
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
                    if (Main.debugMode)
                    {
                        //System.out.println("horiz collisionw");
                    }

                }

                if((hero.getYPos() <= enemy.getYPos() + enemy.getHeroImage().getHeight() && hero.getYPos() >= enemy.getYPos()) || (hero.getYPos() + hero.getHeroImage().getHeight() <= enemy.getYPos() + enemy.getHeroImage().getHeight() && hero.getYPos() + hero.getHeroImage().getHeight() >= enemy.getYPos())) {

                    verticalCollision = true;
                    if(Main.debugMode)
                    {
                        //System.out.println("Veritcal coll");
                    }

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
            if (collides(hero.getXPos(), hero.getYPos() - hero.getSpeed())) {
                //do nothing
            } else {
                if(isExit(hero.getXPos(), hero.getYPos() - hero.getSpeed(), 'n') == 't') {
                    clearProjectiles();
                    hero.incrementYPos(Block.HEIGHT*(ROOMHEIGHT-3));
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    floor.RoomUp();
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else if (isExit(hero.getXPos(), hero.getYPos() - hero.getSpeed(), 'n') == 'e') {
                    clearProjectiles();
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    depth += 1;
                    floor = new Floor(ROOMS, FLOORSIZE, seed, depth);
                    map.updateMap(floor.getCurrentRoom());
                    score+=10;
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
            if (collides(hero.getXPos(), hero.getYPos() + hero.getSpeed())) {
                //do nothing
            } else {
                if(isExit(hero.getXPos(), hero.getYPos() + hero.getSpeed(), 's') == 't') {
                    clearProjectiles();
                    hero.incrementYPos(-Block.HEIGHT*(ROOMHEIGHT-3));
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    floor.RoomDown();
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else if (isExit(hero.getXPos(), hero.getYPos() + hero.getSpeed(), 's') == 'e') {
                    clearProjectiles();
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    depth += 1;
                    floor = new Floor(ROOMS, FLOORSIZE, seed, depth);
                    map.updateMap(floor.getCurrentRoom());
                    score+=10;
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
            if (collides(hero.getXPos() - hero.getSpeed(), hero.getYPos())) {
                //do nothing
            } else {
                if(isExit(hero.getXPos() - hero.getSpeed(), hero.getYPos(), 'w') == 't') {
                    clearProjectiles();
                    hero.incrementXPos(Block.WIDTH*(ROOMWIDTH-3));
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    floor.RoomLeft();
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else if (isExit(hero.getXPos() - hero.getSpeed(), hero.getYPos(), 'w') == 'e') {
                    clearProjectiles();
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    depth += 1;
                    floor = new Floor(ROOMS, FLOORSIZE, seed, depth);
                    map.updateMap(floor.getCurrentRoom());
                    score+=10;
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
            if (collides(hero.getXPos() + hero.getSpeed(), hero.getYPos()))
            {
                //do nothing
            } else {
                if(isExit(hero.getXPos() + hero.getSpeed(), hero.getYPos(), 'e') == 't') {
                    clearProjectiles();
                    hero.incrementXPos(-Block.WIDTH*(ROOMWIDTH-3));
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    floor.RoomRight();
                    map.updateMap(floor.getCurrentRoom());
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else if (isExit(hero.getXPos() + hero.getSpeed(), hero.getYPos(), 'e') == 'e') {
                    clearProjectiles();
                    hero.setXPos(GameCanvas.IMAGE_WIDTH/2-hero.getHeroImage().getWidth()/2);
                    hero.setYPos(GameCanvas.IMAGE_HEIGHT/2-hero.getHeroImage().getHeight()/2);
                    depth += 1;
                    floor = new Floor(ROOMS, FLOORSIZE, seed, depth);
                    map.updateMap(floor.getCurrentRoom());
                    score+=10;
                    repopulateEnemies();
                    canvas.generateBackground();
                    canvas.generateMap();
                } else {
                    hero.incrementXPos(hero.getSpeed());
                }
            }
        }
        
    }

    public void clearProjectiles()
    {
        for(int i = 0; i < projectiles.length; i++)
        {
            projectiles[i] = null;
        }
        currProjIndex = 0;
    }
    public void setBlock(int x, int y, Block block)
    {
        map.currentMap[x][y] = block;
        canvas.generateBackground();
    }


    public boolean collides(int newX, int newY)
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

    public void fire(double dir)
    {

        //get hero x,y and then find direction
        //double dir = (Math.atan(hero.getYPos() / hero.getXPos()));
        //Fire projectile at direction with speed and damage that is allied
        if(currFireCooldown == 0)
        {
            projectiles[currProjIndex] = new Projectile(dir, hero.getShotSpeed(), hero.damage, true, hero.getXPos(), hero.getYPos(), 100, this);
            currProjIndex = (currProjIndex + 1) % projectiles.length;
            currFireCooldown = FIRE_COOLDOWN;
        }
        //canvas.projectile = alliedShot;

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
