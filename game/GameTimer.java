package game;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends TimerTask
{
    private Timer timer;
    private Game game;
    public static final int TICK_RATE = 15;
    public GameTimer(Game game)
    {
        this.game = game;
        this.timer = new Timer();


    }

    public void start()
    {
        timer.scheduleAtFixedRate(this, 0, TICK_RATE);
    }

    @Override
    public void run()//method is called repeatedly by timer
    {
        game.tick();
    }




}
