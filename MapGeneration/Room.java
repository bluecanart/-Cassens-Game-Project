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
    
    public Room(String ID, long seed) throws FileNotFoundException {
        
        layout = new char[11][11];
        type = ID.charAt(0);
        File folder = new File("./src/Game Project/-Cassens-Game-Project/" + ID.substring(0,1) + "/" + ID.substring(1,5));
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
        
        for(int i = 0; i < 11; i++) {
            
            for(int s = 0; s < 11; s++) {
            
                System.out.print(layout[i][s]);
            
            }
            
            System.out.println("");
            
        }
        
    }
    
}
