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
    protected boolean isTransparent;
    protected boolean isCollidable;//TODO: implement collision detection using this value

    public Block(BufferedImage blockImage, boolean isCollidable, boolean isTransparent)
    {
        this.blockImage = blockImage;
        this.isCollidable = isCollidable;
        this.isTransparent = isTransparent;
        RGBArray = new int[WIDTH * HEIGHT];
        if(isTransparent)
        {
            int transColor = this.blockImage.getRGB(0,0);


            for (int i = 0; i < (WIDTH * HEIGHT); i++)
            {
                if ((this.blockImage.getRGB(i % WIDTH, i / WIDTH) == transColor))
                {
                    RGBArray[i]= 0x00000000;

                }
                else
                {
                    RGBArray[i] = this.blockImage.getRGB(i % WIDTH, i / WIDTH);
                }
            }
        }
        else
        {
            for (int i = 0; i < (WIDTH * HEIGHT); i++)
            {
                RGBArray[i] = this.blockImage.getRGB(i % WIDTH, i / WIDTH);
            }
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
