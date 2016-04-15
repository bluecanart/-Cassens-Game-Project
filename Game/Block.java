package Game;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by TurtleDesk on 2/17/2016.
 */
public class Block

{
    Random myRandom = new Random();

    public static final int GRASS = 0;
    public static final int ROCK = 1;
    public static final int WATER = 2;
    public static final int DOOR = 3;
    public static final int EXIT = 4;
    public static final int WALL = 5;

    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    private int[] RGBArray;
    private BufferedImage blockImage;
    protected boolean isCollidable;
    private int type;


    //TODO: consider if this constructor is a good idea, should be adaptable to many types of blocks, may become unwieldy with too many types
    public Block(int type)
    {
        switch(type)
        {
            case GRASS:
                blockImage = Assets.grassImages[myRandom.nextInt(Assets.GRASS_IMAGE_COUNT)];
                this.isCollidable = false;
                this.type = type;
                break;

            case ROCK:
                blockImage = Assets.rockImage;
                this.isCollidable = true;
                this.type = type;
                break;

            case WATER:
                blockImage = Assets.waterImage;
                this.isCollidable = true;
                this.type = type;
                break;
                
            case DOOR:
                blockImage = Assets.grassImages[myRandom.nextInt(Assets.GRASS_IMAGE_COUNT)];
                this.isCollidable = false;
                this.type = type;
                break;
                
            case WALL:
                blockImage = Assets.wallImage;
                this.isCollidable = true;
                this.type = type;
                break;
                
            case EXIT:
                blockImage = Assets.exitImage;
                this.isCollidable = false;
                this.type = type;
                break;

            default:
                System.out.println("WARNING: Invalid Block Type " + type);//TODO: perhaps throw an exception or construct with some default values if code reaches here
        }
    }



    public BufferedImage getBlockImage()
    {
        return blockImage;
    }

    //TODO: consider if this function is needed.
    public int[] getRGBArray()
    {

        RGBArray = new int[WIDTH * HEIGHT];

        for (int i = 0; i < (WIDTH * HEIGHT); i++)
        {
            RGBArray[i] = this.blockImage.getRGB(i % WIDTH, i / WIDTH);
        }

        return RGBArray;
    }

    public int getType()
    {
        return type;
    }

}
