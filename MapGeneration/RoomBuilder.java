package MapGeneration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
        f.setSize(15*50+7, 9*50+30);
        f.setResizable(false);
        f.setVisible(true);
    }
}

class RoomPanel extends JPanel {
    
    final int ROOMWIDTH = 15;
    final int ROOMHEIGHT = 9;
    
    char mode = '-';
    char layout[][] = new char[ROOMHEIGHT][ROOMWIDTH];
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
        stringMap.put('1', "Enemy1");
        stringMap.put('2', "Enemy2");
        stringMap.put('3', "Enemy3");
        
        uniqueMap.put("0000", 1);
        uniqueMap.put("1000", 2);
        uniqueMap.put("0100", 2);
        uniqueMap.put("0010", 2);
        uniqueMap.put("0001", 2);
        uniqueMap.put("0011", 2);
        uniqueMap.put("0110", 2);
        uniqueMap.put("1100", 2);
        uniqueMap.put("1001", 2);
        uniqueMap.put("0101", 1);
        uniqueMap.put("1010", 1);
        uniqueMap.put("1011", 2);
        uniqueMap.put("1101", 2);
        uniqueMap.put("0111", 2);
        uniqueMap.put("1110", 2);
        uniqueMap.put("1111", 1);
        
        String[] options1 = {"Exit Room", "Start Room", "Normal Room", "Generate All Defaults"};
        String[] trueOptions = {"x", "o", "+", "a"};
        type = trueOptions[JOptionPane.showOptionDialog(null, "What type of room are you working on?", "Select Room",
JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
null, options1, options1[0])];
        
        if (type == "a") {
            
            String[] typeOptions = {"x", "o", "+"};
            String[] roomOptions = {"0000", "1111", "1000", "0100", "1100", "1010", "0101", "1001", "1110", "1101"};
            for (String loopType: typeOptions) {
                for (String loopRooms: roomOptions) {
                    if (loopType != "x" || (loopRooms == "1000" || loopRooms == "0100" || loopRooms == "0010" || loopRooms == "0001")) {
                        layout = initLayout(loopRooms, loopType);
                        saveLayout(layout, loopRooms, loopType);
                    }
                }
            }
            
            System.exit(0);
            
        }
        
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
        
        layout = initLayout(rooms, type);
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        getInputMap().put(KeyStroke.getKeyStroke('-'), "empty");
        getInputMap().put(KeyStroke.getKeyStroke('w'), "wall");
        getInputMap().put(KeyStroke.getKeyStroke('s'), "start");
        getInputMap().put(KeyStroke.getKeyStroke('e'), "exit");
        getInputMap().put(KeyStroke.getKeyStroke('r'), "rock");
        getInputMap().put(KeyStroke.getKeyStroke('d'), "door");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "save");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "new");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "Enemy1");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "Enemy2");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "Enemy3");
        
        getActionMap().put("empty", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = '-';
                System.out.println("Draw mode is now: Empty");
                repaint();
            }
        });
        getActionMap().put("Enemy1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = '1';
                System.out.println("Draw mode is now: Enemy 1");
                repaint();
            }
        });
        getActionMap().put("Enemy2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = '2';
                System.out.println("Draw mode is now: Enemy 2");
                repaint();
            }
        });
        getActionMap().put("Enemy3", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = '3';
                System.out.println("Draw mode is now: Enemy 3");
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
                saveLayout(layout, rooms, type);
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
                stringMap.put('1', "Enemy1");
                stringMap.put('2', "Enemy2");
                stringMap.put('3', "Enemy3");
                
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
                
                layout = initLayout(rooms, type);
                
                repaint();
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = (int)(e.getX()/50);
                int y = (int)(e.getY()/50);
                if (x > ROOMWIDTH-1) {
                    x = ROOMWIDTH-1;
                }
                if (y > ROOMHEIGHT-1) {
                    y = ROOMHEIGHT-1;
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
        myMap.put('1', new Color(255, 242, 0));
        myMap.put('2', new Color(160, 221, 90));
        myMap.put('3', new Color(180, 114, 223));
        
        for (int i = 0; i < ROOMHEIGHT; i++) {
            
            for (int s = 0; s < ROOMWIDTH; s++) {
                
                g.setColor(myMap.get(layout[i][s]));
                g.fillRect(s*50,i*50,50,50);
                
            }
            
        }
        
        g.setColor(Color.WHITE);
        g.drawString("ID: " + type + rooms, 10, 20);
        g.drawString("Mode: " + stringMap.get(mode),10,40);
    }
    
    public char[][] flipLayout(char[][] oldLayout) {
        
        char[][] newLayout = new char[ROOMHEIGHT][ROOMWIDTH];
        
        for(int i = 0; i < ROOMHEIGHT; i++) {
            
            for(int s = 0; s < ROOMWIDTH; s++) {
            
                newLayout[i][s] = oldLayout[ROOMHEIGHT-1-i][ROOMWIDTH-1-s];
            
            }
            
        }
        
        return newLayout;
        
    }
        
    public void saveLayout(char[][] inputLayout, String inputRooms, String inputType) {
            
        boolean sent = false;
                
                if (inputType == "x"){
                    
                    sentloop:
                    for(int i = 0; i < ROOMHEIGHT; i++) {
                        
                        for(int s = 0; s < ROOMWIDTH; s++) {
                            
                            if (inputLayout[i][s] == 'e') {
                                
                                sent = true;
                                break sentloop;
                                
                            } 
                            
                        }
                        
                    }
                    
                } else if (inputType == "o") {
                    
                    sentloop:
                    for(int i = 0; i < ROOMHEIGHT; i++) {
                        
                        for(int s = 0; s < ROOMWIDTH; s++) {
                            
                            if (inputLayout[i][s] == 's') {
                                
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
                    String tempRooms = inputRooms;
                    char[][] tempLayout = inputLayout;

                    for (int t = 0; t < uniqueMap.get(inputRooms); t++) {
                        File folder = new File("./src/Rooms/" + inputType + "/" + tempRooms);
                        File[] listOfFiles = folder.listFiles();
                        String filename = "./src/Rooms/" + inputType + "/" + tempRooms + "/" + Integer.toString(listOfFiles.length + 1) + ".txt";
                        try {
                            PrintWriter writer = new PrintWriter(filename);
                            for (int i = 0; i < ROOMHEIGHT; i++) {
                                String line = "";

                                for (int s = 0; s < ROOMWIDTH; s++) {

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
                        tempRooms = rotateString(tempRooms);
                        tempLayout = flipLayout(tempLayout);
                    }
                } else {
                    
                    System.out.println("Add an entrance/exit before you try saving.");
                    
                }
            
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
    
    public char[][] initLayout(String tempRooms, String type) {
        
        char tempLayout[][] = new char[ROOMHEIGHT][ROOMWIDTH];
        
        for (int i = 0; i < ROOMHEIGHT; i++) {
            
            for (int s = 0; s < ROOMWIDTH; s++) {
                
                if (( i == 0) && (s == (ROOMWIDTH-1)/2)) {
                    
                    if (tempRooms.charAt(0) == '0') {
                        tempLayout[i][s] = 'w';
                    } else {
                        tempLayout[i][s] = 'd';
                    }
                    
                } else if (( i == (ROOMHEIGHT-1)/2) && (s == ROOMWIDTH-1)) {
                    
                    if (tempRooms.charAt(1) == '0') {
                        tempLayout[i][s] = 'w';
                    } else {
                        tempLayout[i][s] = 'd';
                    }
                    
                } else if (( i == ROOMHEIGHT-1) && (s == (ROOMWIDTH-1)/2)) {
                    
                    if (tempRooms.charAt(2) == '0') {
                        tempLayout[i][s] = 'w';
                    } else {
                        tempLayout[i][s] = 'd';
                    }
                    
                } else if (( i == (ROOMHEIGHT-1)/2) && (s == 0)) {
                    
                    if (tempRooms.charAt(3) == '0') {
                        tempLayout[i][s] = 'w';
                    } else {
                        tempLayout[i][s] = 'd';
                    }
                    
                } else if (s == 0 || i == 0 || s == ROOMWIDTH-1 || i == ROOMHEIGHT-1) {
                    
                    tempLayout[i][s] = 'w';
                
                } else if(( i == (ROOMHEIGHT-1)/2) && (s == (ROOMWIDTH-1)/2) && (type == "o")) {
                      
                    tempLayout[i][s] = 's';
                    
                }  else if(( i == (ROOMHEIGHT-1)/2) && (s == (ROOMWIDTH-1)/2) && (type == "x")) {
                    
                    tempLayout[i][s] = 'e';
                    
                } else {
                    
                    tempLayout[i][s] = '-';
                    
                }
                
            }
            
        }
        
        
        
        return tempLayout;
        
    }
}