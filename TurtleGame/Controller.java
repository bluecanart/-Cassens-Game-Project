package TurtleGame;

/**
 * Created by TurtleDesk on 3/9/2016.
 */
public class Controller
{

    public static final int BUTTONS = 0;
    public static final int LSTICKX = 1;
    public static final int LSTICKY = 2;

    private int[] currentState;

    private native int[] getControllerState();
    private native void loadXinput();




    public static final short DPAD_UP =       0x0001;
    public static final short DPAD_DOWN =     0x0002;
    public static final short DPAD_LEFT =     0x0004;
    public static final short DPAD_RIGHT =    0x0008;

    public static final int DEADZONE = 7000;

    static
    {
        System.loadLibrary("JNIXinput");

    }

    private GameCanvas canvas;
    public Controller(GameCanvas canvas)
    {

        this.canvas = canvas;
        loadXinput();

    }

    public void tick()
    {

        currentState = getControllerState();
        canvas.upButtonDown = ((currentState[BUTTONS] & DPAD_UP) == DPAD_UP) || currentState[LSTICKY] >= DEADZONE;
        canvas.downButtonDown = ((currentState[BUTTONS] & DPAD_DOWN) == DPAD_DOWN) || currentState[LSTICKY] <= -DEADZONE;
        canvas.leftButtonDown = ((currentState[BUTTONS] & DPAD_LEFT) == DPAD_LEFT)|| currentState[LSTICKX] <= -DEADZONE;
        canvas.rightButtonDown = ((currentState[BUTTONS] & DPAD_RIGHT) == DPAD_RIGHT)|| currentState[LSTICKX] >= DEADZONE;


    }



}
