package TurtleGame;

public class Main
{


    public static void main(String[] args)
    {

        GameFrame myFrame = new GameFrame();
        myFrame.addWindowListener(myFrame);
        GameCanvas myCanvas = new GameCanvas();
        myFrame.add(myCanvas);
        myFrame.pack();
        myCanvas.createBufferStrategy(3);
        myCanvas.bufferStrategy = myCanvas.getBufferStrategy();
        myFrame.setVisible(true);
        GameTimer myTimer = new GameTimer(myCanvas);
        //myCanvas.run();

    }
}
