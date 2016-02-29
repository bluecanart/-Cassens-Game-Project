package TurtleGame;


public class Main
{
    public static void main(String[] args)
    {

        Thread renderThread;
        GameFrame myFrame = new GameFrame();
        myFrame.addWindowListener(myFrame);
        GameCanvas myCanvas = new GameCanvas();
        myFrame.add(myCanvas);
        myFrame.pack();
        renderThread = new Thread(myCanvas);
        renderThread.start();
        myFrame.setVisible(true);
        GameTimer myTimer = new GameTimer(myCanvas);

    }
}
