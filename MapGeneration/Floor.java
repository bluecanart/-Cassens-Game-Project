/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapGeneration;

import static MapGeneration.MapGeneration.generateMap;
import java.io.FileNotFoundException;

/**
 *
 * @author Thoctar
 */


public class Floor {
    
    public Room[][] floor;
    int size;
    public int curX, curY = 0;
    
    public Floor(int maxRooms, int size, long seed, int depth) throws FileNotFoundException {
        this.size = size;
        seed = Math.round(1.0*seed/10)*10 + depth;
        this.floor = generateMap(maxRooms, size, seed);
        for (int i = 0; i < size; i++) {
        
            for (int s = 0; s < size; s++) {
        
                if (floor[i][s] != null) {
                    
                    if(floor[i][s].type == 'o') {
                        
                        curX = s;
                        curY = i;
                        
                    }
                    
                }
        
            }
        
        }
    }

    public Floor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void RoomUp() {
        
        if (curY > 0) {
            curY -= 1;
        }
        
    }
    
    public void RoomRight() {
        
        if (curX < size) {
            curX += 1;
        }
        
    }
    
    public void RoomDown() {
        
        if (curY < size) {
            curY += 1;
        }
        
    }
    
    public void RoomLeft() {
        
        if (curX > 0) {
            curX -= 1;
        }
        
    }
    
    public Room getCurrentRoom() {
//        System.out.println("Print");
//        floor[curY][curX].printRoom();
        return floor[curY][curX];
        
    }
}
