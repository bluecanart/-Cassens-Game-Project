package TurtleGame;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends TimerTask
{
    private Timer timer;
    private GameCanvas canvas;
    public GameTimer(GameCanvas canvas)
    {
        this.canvas = canvas;//reference to canvas used to it's tick() method repeatedly
        this.timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, 5);//not final values for the timer
    }

    @Override
    public void run()//method is called repeatedly by timer
    {
        canvas.tick();
    }




}
