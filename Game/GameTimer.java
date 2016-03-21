package game;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            game.tick();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameTimer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




}
