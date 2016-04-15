package Game;

import java.io.FileNotFoundException;
import java.util.Random;

import MapGeneration.Floor;

public class UnitTests {
	
	public static void main(String args[]) throws FileNotFoundException {
		
		testHeroDamage();
		testHeroInvulnerable();
		testHeroDeath();
		testHeroRoll();
		testFloorEnterExit();
		testHeroEnemyCollision();
		testAllRoomsConnected();
		
	}
	
	private static void testHeroDamage() {
		
		Random myRand = new Random();
		Hero testHero = new Hero(1, 1);
		int oldHealth = testHero.getHealthLeft();
		int damage = myRand.nextInt(50);
		testHero.takeDamage(damage);
		if(testHero.getHealthLeft() == oldHealth - damage) {
			System.out.println("Hero Damage Test Passed.");
		} else {
			System.out.println("Hero Damage Test Failed.");
		}
		
	}
	
	private static void testHeroInvulnerable() {
		
		Random myRand = new Random();
		Hero testHero = new Hero(1, 1);
		int oldHealth = testHero.getHealthLeft();
		int damage = myRand.nextInt(50);
		testHero.roll();
		testHero.takeDamage(damage);
		if(testHero.getHealthLeft() == oldHealth) {
			System.out.println("Hero Invulnerable Test Passed.");
		} else {
			System.out.println("Hero Invulnerable Test Failed.");
		}
		
	}
	
	private static void testHeroRoll() {
		
		Hero testHero = new Hero(1, 1);
		testHero.roll();
		if(testHero.getSpeed() == 8) {
			System.out.println("Hero Roll Test Passed.");
		} else {
			System.out.println("Hero Roll Test Failed.");
		}
		
	}
	
	private static void testHeroDeath() {
		
		Hero testHero = new Hero(1, 1);
		int damage = 100;
		testHero.takeDamage(damage);
		if(testHero.isDead()) {
			System.out.println("Hero Death Test Passed.");
		} else {
			System.out.println("Hero Death Test Failed.");
		}
		
	}
	
	private static void testHeroEnemyCollision() {
		
		Random myRand = new Random();
		Hero testHero = new Hero(10, 10);
		Enemy testEnemy = new Enemy(9, myRand.nextInt(30));
		if(Game.checkEnemyCollision(testHero, testEnemy)) {
			System.out.println("Enemy Collision Test Passed.");
		} else {
			System.out.println("Enemy Collision Test Failed.");
		}
		
	}
	
	private static void testFloorEnterExit() throws FileNotFoundException {
		
		Random myRand = new Random();
		int maxSize = 5;
		int maxRooms = myRand.nextInt(12) + 2;
		int seed = 10;
		int depth = 1;
		Floor testFloor = new Floor(maxRooms, maxSize, seed, depth);
		boolean exitFound = false;
		boolean entranceFound = false;
		
		for(int i = 0; i < maxSize; i++) {
			
			for(int s = 0; s < maxSize; s++) {
				
				if(testFloor.floor[i][s] != null) {
					if(testFloor.floor[i][s].type == 'x' && !exitFound) {
						exitFound = true;
					} else if(testFloor.floor[i][s].type == 'o' && !entranceFound) {
						entranceFound = true;
					} else if(exitFound && entranceFound) {
						break;
					}
				}
				
			}
			
		}
			
		
		if(entranceFound && exitFound) {
			System.out.println("Entrance and Exit Test Passed.");
		} else {
			System.out.println("Entrance and Exit Test Failed.");
		}
		
		
	}
	
	private static void testAllRoomsConnected() throws FileNotFoundException {
		
		Random myRand = new Random();
		int maxSize = 5;
		int maxRooms = myRand.nextInt(12) + 2;
		int seed = 10;
		int depth = 1;
		Floor testFloor = new Floor(maxRooms, maxSize, seed, depth);
		boolean unconnectedFound = false;
		
		for(int i = 0; i < maxSize; i++) {
			
			for(int s = 0; s < maxSize; s++) {
				
				if(testFloor.floor[i][s] != null) {
					if(testFloor.floor[i][s].rooms == "0000") {
						unconnectedFound = true;
						break;
					}
				}
				
			}
			
		}
			
		
		if(!unconnectedFound) {
			System.out.println("Room Connection Test Passed.");
		} else {
			System.out.println("Room Connection Test Failed.");
		}
		
		
	}

}
