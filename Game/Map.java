package game;

import MapGeneration.Room;
import java.awt.*;
import java.util.Random;

/**
 * Created by TurtleDesk on 3/13/2016.
 */
public class Map
{
    private Random myRandom = new Random();
 
    public static int BLOCKS_WIDE;
    public static int BLOCKS_TALL;

    protected Block[][] currentMap;

    public Map(Room room, int ROOMWIDTH, int ROOMHEIGHT)
    {
        BLOCKS_WIDE = ROOMWIDTH;
        BLOCKS_TALL = ROOMHEIGHT;
        currentMap = new Block[BLOCKS_WIDE][BLOCKS_TALL];

        updateMap(room);

    }

    //TODO: this is just a temp method for generating a map
//    public void generateRandomMap()
//    {
//        for (int i = 0; i < currentMap.length; i++)
//        {
//            for(int j = 0; j < currentMap[0].length; j++)
//            {
//                switch (myRandom.nextInt(3))
//                {
//                    case 0:
//                        currentMap[i][j] = new Block(Block.GRASS);
//                        break;
//                    case 1:
//                        currentMap[i][j] = new Block(Block.ROCK);
//                        break;
//                    case 2:
//                        currentMap[i][j] = new Block(Block.WATER);
//                        break;
//
//                }
//
//
//            }
//
//        }
//    }
    
    public void updateMap(Room currentRoom) {
        
        for (int i = 0; i < BLOCKS_TALL; i++)
        {
            for(int j = 0; j < BLOCKS_WIDE; j++)
            {
                switch (currentRoom.layout[i][j])
                {
                    case 'r':
                        currentMap[j][i] = new Block(Block.ROCK);
                        break;
                    case 'd':
                        currentMap[j][i] = new Block(Block.DOOR);
                        break;
                    case 'w':
                        currentMap[j][i] = new Block(Block.WALL);
                        break;
                    case 'e':
                        currentMap[j][i] = new Block(Block.EXIT);
                        break;
                    default:
                        currentMap[j][i] = new Block(Block.GRASS);
                        break;

                }


            }

        }
        
    }

//    public void generatePaths()
//    {
//        for (int i = 0; i < currentMap.length; i++)
//        {
//            for (int j = 0; j <currentMap[0].length; j++)
//            {
//                if(Math.abs(currentMap.length/2 - i) < 4)
//                {
//                    currentMap[i][j] = new Block(Block.GRASS);
//                }
//                if (Math.abs(currentMap[0].length/2 - j) < 4)
//                {
//                    currentMap[i][j] = new Block(Block.GRASS);
//                }
//            }
//        }
//    }
}
