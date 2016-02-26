/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MapGeneration;

import java.util.*;

/**
 *
 * @author cc228719
 */
public class MapGeneration {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    	int size = 8;
    	int maxRooms = 20;
    	
        String map[][] = generateMap(maxRooms, size);
        map = populateRooms(map, size);

        for (int i = 0; i < size; i++) {

            for (int s = 0; s < size; s++) {
                if (map[i][s] == null) {
                    System.out.print("-----");
                } else {
                    System.out.print(map[i][s]);
                }

            }

            System.out.println("");

        }
    }

    static String[][] generateMap(int maxRooms, int size) {

        String map[][] = new String[size][size];
        Random myRand = new Random();
        map[myRand.nextInt(size)][myRand.nextInt(size)] = "o";

        for (int i = 0; i < maxRooms; i++) {
            if (i < maxRooms - 1) {
                
                ArrayList<Integer[]> slots = findAvaliable(map, size);
                int nextSlot = myRand.nextInt(slots.size());
                map[slots.get(nextSlot)[0]][slots.get(nextSlot)[1]] = "+";
                
            } else {
                
                ArrayList<Integer[]> slots = findAvaliableBoss(map, size);
                int nextSlot = myRand.nextInt(slots.size());
                map[slots.get(nextSlot)[0]][slots.get(nextSlot)[1]] = "x";
                
            }
        }
        
        return map;

    }
    
    static String[][] generateMap(int maxRooms, int size, long seed) {

        String map[][] = new String[size][size];
        Random myRand = new Random(seed);
        map[myRand.nextInt(size)][myRand.nextInt(size)] = "o";

        for (int i = 0; i < maxRooms; i++) {
            if (i < maxRooms - 1) {
                
                ArrayList<Integer[]> slots = findAvaliable(map, size);
                int nextSlot = myRand.nextInt(slots.size());
                map[slots.get(nextSlot)[0]][slots.get(nextSlot)[1]] = "+";
                
            } else {
                
                ArrayList<Integer[]> slots = findAvaliableBoss(map, size);
                int nextSlot = myRand.nextInt(slots.size());
                map[slots.get(nextSlot)[0]][slots.get(nextSlot)[1]] = "x";
                
            }
        }
        
        return map;

    }

    static ArrayList<Integer[]> findAvaliable(String[][] map, int size) {

        ArrayList<Integer[]> avaliableSlots = new ArrayList();

        for (int i = 0; i < size; i++) {

            for (int s = 0; s < size; s++) {

                if (map[i][s] == null) {

                    int tempCount = 0;

                    if (i > 0) {

                        if (map[i - 1][s] != null) {
                            tempCount += 1;
                        }

                    }

                    if (s > 0) {

                        if (map[i][s - 1] != null) {
                            tempCount += 1;
                        }

                    }

                    if (i < size - 1) {

                        if (map[i + 1][s] != null) {
                            tempCount += 1;
                        }

                    }

                    if (s < size - 1) {

                        if (map[i][s + 1] != null) {
                            tempCount += 1;
                        }

                    }
                    
                    if (tempCount >= 3) {

                        Integer[] slot = {i, s};
                        avaliableSlots.add(slot);

                    } else if (tempCount == 2) {
                        
                        Integer[] slot = {i, s};
                        avaliableSlots.add(slot);
                        avaliableSlots.add(slot);
                        avaliableSlots.add(slot);
                        
                    } else if (tempCount == 1) {
                        
                        Integer[] slot = {i, s};
                        avaliableSlots.add(slot);
                        avaliableSlots.add(slot);
                        avaliableSlots.add(slot);
                        avaliableSlots.add(slot);
                        avaliableSlots.add(slot);
                        avaliableSlots.add(slot);
                        avaliableSlots.add(slot);
                        avaliableSlots.add(slot);
                        
                    }

                }

            }

        }
        return avaliableSlots;

    }
    
    static String[][] populateRooms(String[][] map, int size) {

        for (int i = 0; i < size; i++) {

            for (int s = 0; s < size; s++) {
                	
                if (map[i][s] != null){
                	
                	int top = 0;
                    int left = 0;
                    int right = 0;
                    int bottom = 0;

                    if (i > 0) {

                        if (map[i - 1][s] != null) {
                            top += 1;
                        }

                    }

                    if (s > 0) {

                        if (map[i][s - 1] != null) {
                            left += 1;
                        }

                    }

                    if (i < size - 1) {

                        if (map[i + 1][s] != null) {
                            bottom += 1;
                        }

                    }

                    if (s < size - 1) {

                        if (map[i][s + 1] != null) {
                            right += 1;
                        }

                    }
                    
                    map[i][s] = map[i][s] + top + right + bottom + left;
                	
                }

            }

        }
        
        return map;

    }
    
    static ArrayList<Integer[]> findAvaliableBoss(String[][] map, int size) {

        ArrayList<Integer[]> avaliableSlots = new ArrayList();

        for (int i = 0; i < size; i++) {

            for (int s = 0; s < size; s++) {

                if (map[i][s] == null) {

                    int tempCount = 0;

                    if (i > 0) {

                        if (map[i - 1][s] != null) {
                            tempCount += 1;
                        }

                    }

                    if (s > 0) {

                        if (map[i][s - 1] != null) {
                            tempCount += 1;
                        }

                    }

                    if (i < size - 1) {

                        if (map[i + 1][s] != null) {
                            tempCount += 1;
                        }

                    }

                    if (s < size - 1) {

                        if (map[i][s + 1] != null) {
                            tempCount += 1;
                        }

                    }
                    
                    if (tempCount == 1) {
                        
                        Integer[] slot = {i, s};
                        avaliableSlots.add(slot);
                        
                    }

                }

            }

        }
        
        return avaliableSlots;

    }
}
