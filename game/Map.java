package game;

import java.awt.*;
import java.util.Random;

/**
 * Created by TurtleDesk on 3/13/2016.
 */
public class Map
{
    private Random myRandom = new Random();

    public static final int BLOCKS_WIDE = GameCanvas.IMAGE_WIDTH / Block.WIDTH;
    public static final int BLOCKS_TALL = GameCanvas.IMAGE_HEIGHT / Block.HEIGHT;

    protected Block[][] currentMap;

    public Map()
    {
        currentMap = new Block[BLOCKS_WIDE][BLOCKS_TALL];

        generateRandomMap();
        generatePaths();

    }

    //TODO: this is just a temp method for generating a map
    public void generateRandomMap()
    {
        for (int i = 0; i < currentMap.length; i++)
        {
            for(int j = 0; j < currentMap[0].length; j++)
            {
                switch (myRandom.nextInt(3))
                {
                    case 0:
                        currentMap[i][j] = new Block(Block.GRASS);
                        break;
                    case 1:
                        currentMap[i][j] = new Block(Block.ROCK);
                        break;
                    case 2:
                        currentMap[i][j] = new Block(Block.WATER);
                        break;

                }


            }

        }
    }

    public void generatePaths()
    {
        for (int i = 0; i < currentMap.length; i++)
        {
            for (int j = 0; j <currentMap[0].length; j++)
            {
                if(Math.abs(currentMap.length/2 - i) < 4)
                {
                    currentMap[i][j] = new Block(Block.GRASS);
                }
                if (Math.abs(currentMap[0].length/2 - j) < 4)
                {
                    currentMap[i][j] = new Block(Block.GRASS);
                }
            }
        }
    }
}
