package game;


import java.awt.image.BufferedImage;

public class Projectile {
    double x;
    double y;
    double xSpeed;
    double ySpeed;
    int damage;
    boolean friendly;
    BufferedImage projImage = Assets.projectileImage;
    Game game;

    public Projectile(double inDir, int inSpeed, int inDamage, boolean inFriend, int initialX, int initialY, Game game){
        x = initialX;
        y = initialY;
        xSpeed = inSpeed * Math.cos(inDir);
        ySpeed = inSpeed * Math.sin(inDir);
        damage = inDamage;
        friendly = inFriend;
        this.game = game;
    }

     public void step(){
        x += xSpeed;
        y += ySpeed;

    }

    public boolean collides()
    {

        Block projBlockTypeNW;
        Block projBlockTypeNE;
        Block projBlockTypeSW;
        Block projBlockTypeSE;
        try
        {


            projBlockTypeNW = game.map.currentMap[((int) x + 10) / Block.WIDTH][((int) y+ 10) / Block.HEIGHT];//block NW of hero that hero partially occupies
            projBlockTypeNE = game.map.currentMap[(((int) x) + (Block.WIDTH - 10)) / Block.WIDTH][(((int) y)) / Block.HEIGHT];//block NE of hero that hero partially occupies
            projBlockTypeSW = game.map.currentMap[(((int) x)) / Block.WIDTH][(((int) y) + (Block.HEIGHT - 10)) / Block.HEIGHT];//block SW of hero that hero partially occupies
            projBlockTypeSE = game.map.currentMap[(((int) x) + (Block.WIDTH - 10)) / Block.WIDTH][(((int) y) + (Block.HEIGHT - 1)) / Block.HEIGHT];//block SE of hero that hero partially occupies
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return true;
        }
        return ((projBlockTypeNW.isCollidable)  || (projBlockTypeNE.isCollidable) ||
                (projBlockTypeSW.isCollidable)  || (projBlockTypeSE.isCollidable) );

    }
}
