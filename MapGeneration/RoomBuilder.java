package MapGeneration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class RoomBuilder {
    
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
        f.add(new RoomPanel());
        f.pack();
        f.setSize(560, 580);
        f.setVisible(true);
    }
}

class RoomPanel extends JPanel {
    
    char mode = '-';
    char layout[][] = new char[11][11];
    String type;
    String rooms;
    Map<Character, String> stringMap = new HashMap<Character, String>();
    Map<String, Integer> uniqueMap = new HashMap<String, Integer>();

    public RoomPanel () {
        stringMap.put('w', "Wall");
        stringMap.put('-', "Empty");
        stringMap.put('e', "Exit");
        stringMap.put('s', "Start");
        stringMap.put('r', "Rock");
        stringMap.put('d', "Door");
        
        uniqueMap.put("0000", 1);
        uniqueMap.put("1000", 4);
        uniqueMap.put("0100", 4);
        uniqueMap.put("0010", 4);
        uniqueMap.put("0001", 4);
        uniqueMap.put("0011", 4);
        uniqueMap.put("0110", 4);
        uniqueMap.put("1100", 4);
        uniqueMap.put("1001", 4);
        uniqueMap.put("0101", 2);
        uniqueMap.put("1010", 2);
        uniqueMap.put("1011", 4);
        uniqueMap.put("1101", 4);
        uniqueMap.put("0111", 4);
        uniqueMap.put("1110", 4);
        uniqueMap.put("1111", 1);
        
        String[] options1 = {"Exit Room", "Start Room", "Normal Room"};
        String[] trueOptions = {"x", "o", "+"};
        type = trueOptions[JOptionPane.showOptionDialog(null, "What type of room are you working on?", "Select Room",
JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
null, options1, options1[0])];
        
        JCheckBox north = new JCheckBox("North");
        JCheckBox east = new JCheckBox("East");
        JCheckBox south = new JCheckBox("South");
        JCheckBox west = new JCheckBox("West");
        String message = "Select the adjacent rooms.";
        Object[] params = {message, north, east, south, west};
        JOptionPane.showConfirmDialog(null, params, "Adjacent Rooms", JOptionPane.DEFAULT_OPTION, JOptionPane.OK_OPTION);
        rooms = "";
        if(north.isSelected()) {
            rooms+="1";
        } else {
            rooms+="0";
        }
        if(east.isSelected()) {
            rooms+="1";
        } else {
            rooms+="0";
        }
        if(south.isSelected()) {
            rooms+="1";
        } else {
            rooms+="0";
        }
        if(west.isSelected()) {
            rooms+="1";
        } else {
            rooms+="0";
        }

        final String typesave = type;
        final String roomssave = rooms;
        
        layout = initLayout(rooms);
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        getInputMap().put(KeyStroke.getKeyStroke('-'), "empty");
        getInputMap().put(KeyStroke.getKeyStroke('w'), "wall");
        getInputMap().put(KeyStroke.getKeyStroke('s'), "start");
        getInputMap().put(KeyStroke.getKeyStroke('e'), "exit");
        getInputMap().put(KeyStroke.getKeyStroke('r'), "rock");
        getInputMap().put(KeyStroke.getKeyStroke('d'), "door");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "save");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "new");
        getActionMap().put("empty", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = '-';
                System.out.println("Draw mode is now: Empty");
                repaint();
            }
        });
        getActionMap().put("wall", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = 'w';
                System.out.println("Draw mode is now: Wall");
                repaint();
            }
        });
        getActionMap().put("door", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = 'd';
                System.out.println("Draw mode is now: Door");
                repaint();
            }
        });
        getActionMap().put("start", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = 's';
                System.out.println("Draw mode is now: Start");
                repaint();
            }
        });
        getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = 'e';
                System.out.println("Draw mode is now: Exit");
                repaint();
            }
        });
        getActionMap().put("rock", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = 'r';
                System.out.println("Draw mode is now: Rock");
                repaint();
            }
        });
        getActionMap().put("save", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boolean sent = false;
                
                if (type == "x"){
                    
                    sentloop:
                    for(int i = 0; i < 11; i++) {
                        
                        for(int s = 0; s < 11; s++) {
                            
                            if (layout[i][s] == 'e') {
                                
                                sent = true;
                                break sentloop;
                                
                            } 
                            
                        }
                        
                    }
                    
                } else if (type == "o") {
                    
                    sentloop:
                    for(int i = 0; i < 11; i++) {
                        
                        for(int s = 0; s < 11; s++) {
                            
                            if (layout[i][s] == 's') {
                                
                                sent = true;
                                break sentloop;
                                
                            } 
                            
                        }
                        
                    }
                    
                } else {
                    
                    sent = true;
                    
                }
                
                if (sent) {
                    System.out.println("Saving");
                    String tempRooms = rooms;
                    char[][] tempLayout = layout;

                    for (int t = 0; t < uniqueMap.get(rooms); t++) {
                        File folder = new File("./src/Rooms/" + type + "/" + tempRooms);
                        File[] listOfFiles = folder.listFiles();
                        String filename = "./src/Rooms/" + type + "/" + tempRooms + "/" + Integer.toString(listOfFiles.length + 1) + ".txt";
                        try {
                            PrintWriter writer = new PrintWriter(filename);
                            for (int i = 0; i < 11; i++) {
                                String line = "";

                                for (int s = 0; s < 11; s++) {

                                    line += tempLayout[i][s];

                                }

                                writer.println(line);

                            }
                            writer.close();
                            System.out.println("File saved at: " + filename);
                        } catch (IOException ex) {
                            Logger.getLogger(RoomPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        tempRooms = rotateString(tempRooms);
                        tempLayout = rotateLayout(tempLayout);
                    }
                } else {
                    
                    System.out.println("Add an entrance/exit before you try saving.");
                    
                }
            }
        });
        getActionMap().put("new", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Creating new room.");
                stringMap.put('w', "Wall");
                stringMap.put('-', "Empty");
                stringMap.put('e', "Exit");
                stringMap.put('s', "Start");
                stringMap.put('r', "Rock");
                stringMap.put('d', "Door");
                String[] options1 = {"Boss Room", "Start Room", "Normal Room"};
                String[] trueOptions = {"x", "o", "+"};
                type = trueOptions[JOptionPane.showOptionDialog(null, "What type of room are you working on?", "Select Room",
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
        null, options1, options1[0])];

                JCheckBox north = new JCheckBox("North");
                JCheckBox east = new JCheckBox("East");
                JCheckBox south = new JCheckBox("South");
                JCheckBox west = new JCheckBox("West");
                String message = "Select the adjacent rooms.";
                Object[] params = {message, north, east, south, west};
                JOptionPane.showConfirmDialog(null, params, "Adjacent Rooms", JOptionPane.DEFAULT_OPTION, JOptionPane.OK_OPTION);
                rooms = "";
                if(north.isSelected()) {
                    rooms+="1";
                } else {
                    rooms+="0";
                }
                if(east.isSelected()) {
                    rooms+="1";
                } else {
                    rooms+="0";
                }
                if(south.isSelected()) {
                    rooms+="1";
                } else {
                    rooms+="0";
                }
                if(west.isSelected()) {
                    rooms+="1";
                } else {
                    rooms+="0";
                }

                final String typesave = type;
                final String roomssave = rooms;
                
                layout = initLayout(rooms);
                
                repaint();
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = (int)(e.getX()/50);
                int y = (int)(e.getY()/50);
                if (x > 10) {
                    x = 10;
                }
                if (y > 10) {
                    y = 10;
                }
                layout[y][x] = mode;
                repaint();
            }
        });
    }

    public Dimension getPreferredSize() {
        return new Dimension(550,550);
    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Map<Character, Color> myMap = new HashMap<Character, Color>();
        myMap.put('w', Color.BLACK);
        myMap.put('-', new Color(200, 200, 200));
        myMap.put('e', new Color(237, 114, 107));
        myMap.put('s', new Color(97, 199, 248));
        myMap.put('r', new Color(125, 114, 98));
        myMap.put('d', new Color(255, 255, 255));
        
        for (int i = 0; i < 11; i++) {
            
            for (int s = 0; s < 11; s++) {
                
                g.setColor(myMap.get(layout[i][s]));
                g.fillRect(s*50,i*50,50,50);
                
            }
            
        }
        
        g.setColor(Color.WHITE);
        g.drawString("ID: " + type + rooms, 10, 20);
        g.drawString("Mode: " + stringMap.get(mode),10,40);
    }
    
    public char[][] rotateLayout(char[][] oldLayout) {
        
        char[][] newLayout = new char[11][11];
        
        for(int i = 0; i < 11; i++) {
            
            for(int s = 0; s < 11; s++) {
            
                newLayout[s][10-i] = oldLayout[i][s];
            
            }
            
        }
        
        return newLayout;
        
    }
    
    public String rotateString(String oldString) {
        
        String newString = "";
        
        for(int i = 0; i < oldString.length(); i++) {
            
            if (i == 0) {
                newString += oldString.charAt(oldString.length()-1);
            } else {
                newString += oldString.charAt(i-1);
            }
            
        }
        
        return newString;
        
    }
    
    public char[][] initLayout(String tempRooms) {
        
        char tempLayout[][] = new char[11][11];
        
        for (int i = 0; i < 11; i++) {
            
            for (int s = 0; s < 11; s++) {
                
                if (( i == 0) && (s == 5)) {
                    
                    if (tempRooms.charAt(0) == '0') {
                        tempLayout[i][s] = 'w';
                    } else {
                        tempLayout[i][s] = 'd';
                    }
                    
                } else if (( i == 5) && (s == 10)) {
                    
                    if (tempRooms.charAt(1) == '0') {
                        tempLayout[i][s] = 'w';
                    } else {
                        tempLayout[i][s] = 'd';
                    }
                    
                } else if (( i == 10) && (s == 5)) {
                    
                    if (tempRooms.charAt(2) == '0') {
                        tempLayout[i][s] = 'w';
                    } else {
                        tempLayout[i][s] = 'd';
                    }
                    
                } else if (( i == 5) && (s == 0)) {
                    
                    if (tempRooms.charAt(3) == '0') {
                        tempLayout[i][s] = 'w';
                    } else {
                        tempLayout[i][s] = 'd';
                    }
                    
                } else if (s == 0 || i == 0 || s == 10 || i == 10) {
                    
                    tempLayout[i][s] = 'w';
                
                } else {
                    
                    tempLayout[i][s] = '-';
                    
                }
                
            }
            
        }
        
        
        
        return tempLayout;
        
    }
}