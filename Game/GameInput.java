package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;

/**
 * Created by TurtleDesk on 3/14/2016.
 */
public class GameInput implements KeyListener, MouseListener
{
    public static final int UP = KeyEvent.VK_W;
    public static final int DOWN = KeyEvent.VK_S;
    public static final int LEFT = KeyEvent.VK_A;
    public static final int RIGHT = KeyEvent.VK_D;
    public static final int CONTROL = KeyEvent.VK_CONTROL;
    public static final int FIRE = MouseEvent.BUTTON1;

    protected boolean upKeyDown = false;
    protected boolean downKeyDown = false;
    protected boolean leftKeyDown = false;
    protected boolean rightKeyDown = false;
    protected boolean controlKeyDown = false;
    protected boolean fireKeyDown = false;

    protected boolean upButtonDown = false;
    protected boolean downButtonDown = false;
    protected boolean leftButtonDown = false;
    protected boolean rightButtonDown = false;
    protected boolean aButtonDown = false;

    protected Game game;
    private Controller controller;
    private Timer controllerPoller;
    public static final int POLL_RATE = 5;//time in milliseconds between controller polls


    public GameInput(Game game)
    {
        this.game = game;

        //TODO: no error message is given if link with JNIXinput is succesful, but link with xinput is unsuccessful (however it shouldnt crash)
        try
        {
            controller = new Controller(this);
            controllerPoller = new Timer();
            controllerPoller.scheduleAtFixedRate(controller,0,POLL_RATE);

        }
        catch (UnsatisfiedLinkError e)
        {
            System.out.println("unable to load Controller DLL");
        }
    }

    public boolean isUpPressed()
    {
        return (upButtonDown || upKeyDown);
    }

    public boolean isDownPressed()
    {
       return (downButtonDown || downKeyDown);
    }

    public boolean isLeftPressed()
    {
        return (leftButtonDown || leftKeyDown);
    }

    public boolean isRightPressed()
    {
        return (rightButtonDown || rightKeyDown);
    }
    
    public boolean isControlPressed()
    {
        return (aButtonDown || controlKeyDown);
    }

    public void controllerFire(double dir)
    {
        game.fire(dir);
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
                
            case CONTROL:
                controlKeyDown = true;
                break;

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
                
            case CONTROL:
                controlKeyDown = false;
                break;

            case FIRE:
                fireKeyDown = false;
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

        switch(e.getButton()) {
            case FIRE:
                //fireKeyDown = true;
                double dir = (Math.atan2((e.getY() -  (game.getHero().getYPos() + 16) * 2) , (e.getX() -  (game.getHero().getXPos() + 16) * 2)));//s Magic numbers indicate scale factor, and center of hero
                game.fire(dir);
                if (Main.debugMode)
                {
                    System.out.println("Projectile Fired from Mouse!\nDirection :" + Math.toDegrees(dir) + "\nMouse X: " + e.getX() + "\nMouseY: " + e.getY() + "\nHeroX: " + game.getHero().getXPos() + "\nHeroY: " + game.getHero().getYPos() + "\n");
                }
                break;
        }
//        Map gameMap = game.getMap();
//
//        if ((e.getX() >= 0 && e.getX()< GameCanvas.IMAGE_WIDTH) && (e.getY() >= 0 && e.getY()< GameCanvas.IMAGE_HEIGHT))
//        {
//            int xBlock = e.getX() / Block.WIDTH;
//            int yBlock = e.getY() / Block.HEIGHT;
//            switch (gameMap.currentMap[xBlock][yBlock].getType())
//            {
//                case Block.GRASS:
//                    game.setBlock(xBlock, yBlock, new Block(Block.ROCK));
//                    break;
//                case Block.ROCK:
//                    game.setBlock(xBlock, yBlock, new Block(Block.WATER));
//                    break;
//                case Block.WATER:
//                    game.setBlock(xBlock, yBlock, new Block(Block.GRASS));
//                    break;
//                default:
//                    System.out.println("invalid block type on mouse press");
//
//            }
//        }





    }

    @Override
    public void mouseReleased(MouseEvent e) {

        /*
        switch(e.getButton()) {
            case FIRE:
                fireKeyDown = false;
                break;
        }
        */

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}

