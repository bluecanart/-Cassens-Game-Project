package TurtleGame;

import java.awt.image.BufferedImage;

/**
 * Created by TurtleDesk on 2/17/2016.
 */
public class Block

{
    public static final int GRASS = 0;
    public static final int ROCK = 1;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

    private int[] RGBArray;
    private BufferedImage blockImage;
    protected boolean isCollidable;//TODO: implement collision detection using this value

    public Block(BufferedImage blockImage, boolean isCollidable)
    {
        this.blockImage = blockImage;
        this.isCollidable = isCollidable;
        RGBArray = new int[WIDTH * HEIGHT];
        for (int i = 0; i < (WIDTH * HEIGHT); i++)
        {
            RGBArray[i] = this.blockImage.getRGB(i % WIDTH, i/WIDTH);
        }
    }

    public int[] getRGBArray()
    {
        return RGBArray;
    }

    //TODO: temp method for detecting collision, look into more when more block types are added.
    public static boolean isBlockCollidable(int type)
    {
        return type == ROCK;
    }

}
