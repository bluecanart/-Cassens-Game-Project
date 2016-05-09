/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MapGeneration;
/**
 *
 * @author Thoctar
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class Room {
    
    public char[][] layout;
    public char type;
    public String rooms;
    public boolean cleared = false;
    final int ROOMWIDTH = 15;
    final int ROOMHEIGHT = 9;
    
    public Room(String ID, long seed) throws FileNotFoundException {
        
        layout = new char[ROOMHEIGHT][ROOMWIDTH];
        type = ID.charAt(0);
        rooms = ID.substring(1, 4);
        File folder = new File("./src/Rooms/" + ID.substring(0,1) + "/" + ID.substring(1,5));
        File[] listOfFiles = folder.listFiles();
        Random myRand = new Random(seed);
        File roomChoice = listOfFiles[myRand.nextInt(listOfFiles.length)];
        Scanner myScanner = new Scanner(roomChoice);
        int i = 0;
        while(myScanner.hasNextLine()) {
            String tempLine = myScanner.nextLine();
            for(int s = 0; s < tempLine.length(); s++) {
                layout[i][s] = tempLine.charAt(s);
            }
            i++;
        }
        
        //printRoom();
    }
    
    public void printRoom() {
        
        for(int i = 0; i < ROOMHEIGHT; i++) {
            
            for(int s = 0; s < ROOMWIDTH; s++) {
            
                System.out.print(layout[i][s]);
            
            }
            
            System.out.println("");
            
        }
        
    }
    
}
