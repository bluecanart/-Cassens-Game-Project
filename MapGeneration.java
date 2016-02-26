/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.*;

/**
 *
 * @author cc228719
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        generateMap(20, 8);
    }

    static void generateMap(int maxRooms, int size) {

        String map[][] = new String[size][size];
        Random myRand = new Random();
        map[myRand.nextInt(size)][myRand.nextInt(size)] = "o ";

        for (int i = 0; i < maxRooms; i++) {
            if (i < maxRooms - 1) {
                
                ArrayList<Integer[]> slots = findAvaliable(map, size);
                int nextSlot = myRand.nextInt(slots.size());
                map[slots.get(nextSlot)[0]][slots.get(nextSlot)[1]] = "+ ";
                
            } else {
                
                ArrayList<Integer[]> slots = findAvaliableBoss(map, size);
                int nextSlot = myRand.nextInt(slots.size());
                map[slots.get(nextSlot)[0]][slots.get(nextSlot)[1]] = "x ";
                
            }
        }

        for (int i = 0; i < size; i++) {

            for (int s = 0; s < size; s++) {
                if (map[i][s] == null) {
                    System.out.print("\u001B[37m"+"- "+"\u001B[0m");
                } else if (map[i][s] == "x ") {
                    System.out.print("\u001B[31m"+map[i][s]+"\u001B[0m");
                } else if (map[i][s] == "o ") {
                    System.out.print("\u001B[34m"+map[i][s]+"\u001B[0m");
                } else {
                    System.out.print("\u001B[32m"+map[i][s]+"\u001B[0m");
                }

            }

            System.out.println("");

        }


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
