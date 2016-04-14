package MapGeneration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
        f.pack();
        f.setSize(568,591);
        f.setVisible(true);
    }
}

class MyPanel extends JPanel {
    
    final int ROOMWIDTH = 15;
    final int ROOMHEIGHT = 9;

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }
        });
    }

    public Dimension getPreferredSize() {
        return new Dimension(568,591);
    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);       
        Room floor[][];
        Random myRand = new Random();
        int size = 10;
        long seed = myRand.nextLong();
//        seed = 11;
        int rooms = 20;
        
        try {
            
            floor = MapGeneration.generateMap(rooms, size, seed);
            Map<Character, Color> myMap = new HashMap<Character, Color>();
            myMap.put('w', Color.BLACK);
            myMap.put('-', new Color(200, 200, 200));
            myMap.put('e', new Color(237, 114, 107));
            myMap.put('s', new Color(97, 199, 248));
            myMap.put('r', new Color(125, 114, 98));
            myMap.put('d', new Color(255, 255, 255));
            
//            for (int i = 0; i < 10; i++) {
//                for (int s = 0; s < 10; s++) {
//                    
//                    if(floor[i][s] != null) {
//                        System.out.print(floor[i][s].type);
//                    } else {
//                        System.out.print("-");
//                    }
//                    
//                }
//                System.out.println("");
//            }
        
            for (int i = 0; i < size; i++) {
            
                for (int s = 0; s < size; s++) {
            
                    if (floor[i][s] == null) {
                        g.setColor(Color.WHITE);
                        g.fillRect(s*ROOMWIDTH*5, i*ROOMHEIGHT*5, ROOMWIDTH*5, ROOMHEIGHT*5);
                    } else {
                        
                        for (int x = 0; x < ROOMHEIGHT; x++) {
                            
                            for(int y = 0; y < ROOMWIDTH; y++) {
                                
                                //System.out.println(floor[i][s].layout)
                                g.setColor(myMap.get(floor[i][s].layout[x][y]));
                                g.fillRect(s*ROOMWIDTH*5+(y)*5, i*ROOMHEIGHT*5+(x)*5, 5, 5);
                                
                            }
                            
                        }
                        
                    }
            
                }
            
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Draw Text
        //g.drawString("This is my custom Panel!",10,20);
    }  
}