package TurtleGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class GameCanvas extends Canvas implements KeyListener, MouseListener
{
    private double seconds = 0.015;
    private int rendercount = 0;
    public BufferStrategy bufferStrategy;
    private BufferedImage backGround;
    private Controller gameController;
    private boolean upKeyDown = false;
    private boolean downKeyDown = false;
    private boolean leftKeyDown = false;
    private boolean rightKeyDown = false;

    public boolean upButtonDown = false;
    public boolean downButtonDown = false;
    public boolean leftButtonDown = false;
    public boolean rightButtonDown = false;

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
    public static final int IMAGE_WIDTH = 64 * 30;//48 * 30;//for now, width must be divisible by 30
    public static final int IMAGE_HEIGHT = 36 * 30;//27 * 30;//for now, height must be divisible by 30
    public static int BLOCKS_WIDE = IMAGE_WIDTH/30;
    public static int BLOCKS_TALL = IMAGE_HEIGHT/30;

    Hero myHero;


    public GameCanvas()
    {
        backGround = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        setBackground(Color.black); //TODO: this prevented flickering if window was resized to be greater than the drawable area
        setPreferredSize(new Dimension(IMAGE_WIDTH,IMAGE_HEIGHT));

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
        grassBlock = new Block(grassImage, false, false);
        rockBlock = new Block(rockImage, true, false);
        myHero = new Hero(heroImage);

        try
        {
            gameController = new Controller(this);
        }
        catch (UnsatisfiedLinkError e)
        {
            System.out.println("unable to load Controller DLL");
        }

        addKeyListener(this);
        addMouseListener(this);
        generateBackground();
    }


    {

    }


    /*
    public void run()
    {
        while (true)
        {
            render();
        }
    }
    */

    //Generates the background image using the blocks array
    public void generateBackground()
    {
        for (int i = 0; i < BLOCKS_WIDE; i++)
        {
            for (int j = 0; j < BLOCKS_TALL; j++)
            {
                switch (blocks[i][j])
                {
                    case (Block.GRASS):
                        tempBlockRGB = grassBlock.getRGBArray();
                        break;

                    case (Block.ROCK):
                        tempBlockRGB = rockBlock.getRGBArray();
                        break;
                    default:
                        System.out.println("Invalid block");//TODO: perhaps throw an exception, this code should not be reached
                }

                int xPosition = i * Block.WIDTH;
                int yPosition = j * Block.HEIGHT;
                backGround.setRGB(xPosition, yPosition, Block.WIDTH, Block.HEIGHT, tempBlockRGB, 0, Block.WIDTH);//adds each block to the mainDisplay Buffer
            }
        }
    }


    public void render()
    {
        rendercount++;
        tempBlockRGB = myHero.getRGBArray();
        Graphics canvasGraphics = bufferStrategy.getDrawGraphics();
        canvasGraphics.drawImage(backGround, 0, 0, null);//draws image to screen
        canvasGraphics.drawImage(heroImage, myHero.getXPos(), myHero.getYPos(), myHero.getXPos() + Hero.IMAGE_WIDTH, myHero.getYPos() + Hero.IMAGE_HEIGHT, 0, 0, Hero.IMAGE_WIDTH, Hero.IMAGE_HEIGHT, null);
        canvasGraphics.drawString(String.valueOf(rendercount/seconds), 10 ,10);
        bufferStrategy.show();
        canvasGraphics.dispose();

    }

    public void tick()//called by the TurtleGame.GameTimer
    {
        updateHeroPos();
        render();
        if (gameController != null)//will be null if gameController failed to link with JNIXinput.dll
        {
            gameController.tick();
        }
        seconds += .015;


    }


    //changes hero position based on the state of the WASD keys/state of controller
    private void updateHeroPos()
    {
        int speed = 5;


          if (upKeyDown || upButtonDown) {
              if (heroCollides(myHero.getXPos(), myHero.getYPos() - speed)) {
                    //do nothing
                } else {
                    myHero.incrementYPos(-speed);//TODO: Bounds checking on heroes location
                }
            }

        if (downKeyDown || downButtonDown) {
            if (heroCollides(myHero.getXPos(), myHero.getYPos() + speed)) {
                    //do nothing
                } else {
                    myHero.incrementYPos(speed);
                }
            }

        if (leftKeyDown || leftButtonDown) {
            if (heroCollides(myHero.getXPos() -speed, myHero.getYPos())) {
                //do nothing
                } else {
                    myHero.incrementXPos(-speed);
                }
            }


        if (rightKeyDown || rightButtonDown) {
                if (heroCollides(myHero.getXPos() + speed, myHero.getYPos())) {
                    //do nothing
                } else {
                    myHero.incrementXPos(speed);
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

        if ((Block.isBlockCollidable(heroBlockTypeNW))  || (Block.isBlockCollidable(heroBlockTypeNE)) ||
            (Block.isBlockCollidable(heroBlockTypeSW))  || (Block.isBlockCollidable(heroBlockTypeSE)))
        {
            return true;
        }
        else
        {
            return false;
        }


    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case UP:
                upKeyDown = true;
            break;

            case DOWN:
                downKeyDown = true;
            break;

            case LEFT:
                leftKeyDown = true;
            break;

            case RIGHT:
                rightKeyDown = true;
            break;

            //temp event for spacebar, randomizes and regenerates background
            case KeyEvent.VK_SPACE:
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
                generateBackground();
                break;


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
            case UP:
                upKeyDown = false;
            break;

            case DOWN:
                downKeyDown = false;
            break;

            case LEFT:
                leftKeyDown = false;
            break;

            case RIGHT:
                rightKeyDown = false;
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
            generateBackground();
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
