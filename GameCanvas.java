package TurtleGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class GameCanvas extends Canvas implements Runnable, KeyListener, MouseListener
{

    private boolean upKeyDown = false;
    private boolean downKeyDown = false;
    private boolean leftKeyDown = false;
    private boolean rightKeyDown = false;

    public static final int UP = KeyEvent.VK_W;
    public static final int DOWN = KeyEvent.VK_S;
    public static final int LEFT = KeyEvent.VK_A;
    public static final int RIGHT = KeyEvent.VK_D;

    Random myRandom = new Random();// temp for image layout
    BufferedImage grassImage;
    BufferedImage rockImage;
    BufferedImage heroImage;

    int[] tempBlockRGB;//reference to the image used in Blocks/hero

    Block grassBlock;
    Block rockBlock;

    //block size = 30x30 pixels

    int[][] blocks = new int[BLOCKS_WIDE][BLOCKS_TALL];
    public static final int IMAGE_WIDTH = 48*30;//for now, width must be divisible by 30
    public static final int IMAGE_HEIGHT = 27*30;//for now, height must be divisible by 30
    public static int BLOCKS_WIDE = IMAGE_WIDTH/30;
    public static int BLOCKS_TALL = IMAGE_HEIGHT/30;

    Hero myHero;

    private int tickCounter;//number of ticks

    private BufferedImage mainDisplay;
    public GameCanvas()
    {
        tickCounter = 0;

        setPreferredSize(new Dimension(IMAGE_WIDTH,IMAGE_HEIGHT));
        mainDisplay = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        //generates the blockMap
        for(int i = 0; i < blocks.length; i++)
        {
            for (int j = 0; j < blocks[0].length; j++)
            {
                blocks[i][j] = myRandom.nextInt(2);//randomly creates grass/rock blocks
                if (i > BLOCKS_WIDE/2 - 2 && i < BLOCKS_WIDE/2 + 2)//sets a path going N/S
                {
                    blocks[i][j] = Block.GRASS;
                }

                if (j > BLOCKS_TALL/2 -2 && j < (BLOCKS_TALL/2 +2))// sets a path going E/W
                {
                    blocks[i][j] = Block.GRASS;
                }
            }
        }

        //loads images
        try
        {
            grassImage = ImageIO.read(new File("./media/Grass.png"));
            rockImage = ImageIO.read(new File("./media/Rock.png"));
            heroImage = ImageIO.read(new File("./media/Hero.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        grassBlock = new Block(grassImage, false);
        rockBlock = new Block(rockImage, true);
        myHero = new Hero(heroImage);

        addKeyListener(this);
        addMouseListener(this);


    }

    public void run()
    {
        while (true)
        {
            render();
        }
    }


    public void render()
    {

        BufferStrategy bufferStrat = getBufferStrategy();
        if(bufferStrat == null)
        {
            createBufferStrategy(3);
            return;
        }
        else
        {
            //Renders each block

            for (int i = 0; i < BLOCKS_WIDE; i++)
            {
                for (int j = 0; j < BLOCKS_TALL; j++)
                {
                    switch (blocks[i][j])
                    {
                        case (Block.GRASS):
                        {
                            tempBlockRGB = grassBlock.getRGBArray();
                            break;
                        }

                        case (Block.ROCK):
                        {
                            tempBlockRGB = rockBlock.getRGBArray();
                            break;
                        }
                        default:
                        {
                            System.out.println("Invalid block");//TODO: perhaps throw an exception, this code should not be reached
                        }
                    }

                    int xPosition = i * Block.WIDTH;
                    int yPosition = j * Block.HEIGHT;
                    mainDisplay.setRGB(xPosition, yPosition, Block.WIDTH, Block.HEIGHT, tempBlockRGB, 0, Block.WIDTH);//adds each block to the mainDisplay Buffer
                }
            }
            tempBlockRGB = myHero.getRGBArray();

            mainDisplay.setRGB(myHero.getXPos(), myHero.getYPos(), Block.WIDTH, Block.HEIGHT, tempBlockRGB, 0, Block.WIDTH);//adds the hero after adding each block






            Graphics myGraphics = bufferStrat.getDrawGraphics();
            bufferStrat.show();
            myGraphics.drawImage(mainDisplay,0,0,this);//draws image to screen
            myGraphics.dispose();


        }
    }

    public void tick()//called by the TurtleGame.GameTimer
    {
        tickCounter++;//currently unused, will be used for events that occur only after a certain number of ticks.
        updateHeroPos();
    }


    //changes hero position based on the state of the WASD keys
    private void updateHeroPos()
    {

          if (upKeyDown) {
              if (heroCollides(myHero.getXPos(), myHero.getYPos() - 1)) {
                    //do nothing
                } else {
                    myHero.incrementYPos(-1);//TODO: Bounds checking on heroes location
                }
            }

        if (downKeyDown) {
            if (heroCollides(myHero.getXPos(), myHero.getYPos() + 1)) {
                    //do nothing
                } else {
                    myHero.incrementYPos(1);
                }
            }

        if (leftKeyDown) {
            if (heroCollides(myHero.getXPos() -1, myHero.getYPos())) {
                //do nothing
                } else {
                    myHero.incrementXPos(-1);
                }
            }


        if (rightKeyDown) {
                if (heroCollides(myHero.getXPos() + 1, myHero.getYPos())) {
                    //do nothing
                } else {
                    myHero.incrementXPos(1);
                }
            }

    }

    //checks if the location the hero is about to move to is collidable
    public boolean heroCollides(int newX, int newY)
    {

        int heroBlockTypeNW = blocks[(newX)/30][(newY)/30];//block NW of hero that hero partially occupies
        int heroBlockTypeNE = blocks[(newX + 29)/30][(newY)/30];//block NE of hero that hero partially occupies
        int heroBlockTypeSW = blocks[(newX)/30][(newY + 29)/30];//block SW of hero that hero partially occupies
        int heroBlockTypeSE = blocks[(newX + 29)/30][(newY + 29)/30];//block SE of hero that hero partially occupies

        if (Block.isBlockCollidable(heroBlockTypeNW))
        {
            return true;
        }
        if (Block.isBlockCollidable(heroBlockTypeNE))
        {
            return true;
        }
        if (Block.isBlockCollidable(heroBlockTypeSW))
        {
            return true;
        }
        if (Block.isBlockCollidable(heroBlockTypeSE))
        {
            return true;
        }
        return false;


    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case UP:
            {
                upKeyDown = true;
            }break;

            case DOWN:
            {
                downKeyDown = true;
            }break;

            case LEFT:
            {
                leftKeyDown = true;
            }break;

            case RIGHT:
            {
                rightKeyDown = true;
            }break;

            case KeyEvent.VK_SPACE:
            {
                for(int i = 0; i < blocks.length; i++)
                {
                    for (int j = 0; j < blocks[0].length; j++)
                    {
                        blocks[i][j] = myRandom.nextInt(2);//randomly creates grass/rock blocks
                        if (i > 30 && i < 34)//sets a path going N/S
                        {
                            blocks[i][j] = Block.GRASS;
                        }

                        if (j > 16 && j < 20)// sets a path going E/W
                        {
                            blocks[i][j] = Block.GRASS;
                        }
                    }
                }
            }

            default:
            {

            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case UP: {
                upKeyDown = false;
            }
            break;

            case DOWN: {
                downKeyDown = false;
            }
            break;

            case LEFT: {
                leftKeyDown = false;
            }
            break;

            case RIGHT: {
                rightKeyDown = false;
            }
            break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if ((e.getX() >= 0 && e.getX()< IMAGE_WIDTH) && (e.getY() >= 0 && e.getY()< IMAGE_HEIGHT))
        {
            int xBlock = e.getX()/Block.WIDTH;
            int yBlock = e.getY()/Block.HEIGHT;

            blocks[xBlock][yBlock] = (blocks[xBlock][yBlock] + 1) % 2;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
