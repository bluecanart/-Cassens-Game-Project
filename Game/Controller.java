package game;

import java.util.TimerTask;

/**
 * Created by TurtleDesk on 3/9/2016.
 */
public class Controller extends TimerTask
{

    public static final int BUTTONS = 0;
    public static final int LSTICKX = 1;
    public static final int LSTICKY = 2;
    public static final int RSTICKX = 3;
    public static final int RSTICKY = 4;

    private int[] currentState;

    private native int[] getControllerState();
    private native void loadXinput();




    public static final short DPAD_UP    = 0x0001;
    public static final short DPAD_DOWN  = 0x0002;
    public static final short DPAD_LEFT  = 0x0004;
    public static final short DPAD_RIGHT = 0x0008;
    public static final short GAMEPAD_A  = 0x1000;

    public static final int DEADZONE = 10000;


    private GameInput input;
    public Controller(GameInput input)
    {
        System.loadLibrary("JNIXinput");
        this.input = input;
        loadXinput();

    }

    //called by a timer (GameInput)
    public void run()
    {

        currentState = getControllerState();
        input.upButtonDown = ((currentState[BUTTONS] & DPAD_UP) == DPAD_UP) || currentState[LSTICKY] >= DEADZONE;
        input.downButtonDown = ((currentState[BUTTONS] & DPAD_DOWN) == DPAD_DOWN) || currentState[LSTICKY] <= -DEADZONE;
        input.leftButtonDown = ((currentState[BUTTONS] & DPAD_LEFT) == DPAD_LEFT)|| currentState[LSTICKX] <= -DEADZONE;
        input.rightButtonDown = ((currentState[BUTTONS] & DPAD_RIGHT) == DPAD_RIGHT)|| currentState[LSTICKX] >= DEADZONE;
        input.aButtonDown = (currentState[BUTTONS] & GAMEPAD_A) == GAMEPAD_A;

        if (Math.abs(currentState[RSTICKX]) >= DEADZONE || Math.abs((currentState[RSTICKY]))>= DEADZONE)
        {
            double dir = Math.atan2(currentState[RSTICKX] , currentState[RSTICKY]) - (Math.PI /2);
            if(Main.debugMode && input.game.currFireCooldown == 0 )
            {
                System.out.println("X: " + currentState[RSTICKX] + "\nY: " + currentState[RSTICKY] +"\n");
            }
            input.controllerFire(dir);
        }

    }



}
