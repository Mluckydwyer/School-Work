package Battleship;

import java.util.Scanner;

public class BattleshipGame {
	
	// Creates the arrays 10x10 for the boards
	public static boolean[][]		playerShips	= new boolean[11][11];
	public static boolean[][]		cpuShips	= new boolean[11][11];
	public static boolean[][]		playerHits	= new boolean[11][11];
	public static boolean[][]		cpuHits		= new boolean[11][11];
	public static String[]			shipName	= new String[6];
	public static int[]				shipLength	= new int[6];
	
	public static BattleshipGame	game;
	public static Scanner			keyboardInt	= new Scanner(System.in);
	
	public static void main(String[] args) {
		game = new BattleshipGame();
		int choice;
		int cordX = 0;
		int cordY = 0;
		
		boolean redo = false;
		boolean orientation = false; // true for horizontal, false for vertical
		
		shipName[1] = "Patrol Boat";
		shipName[2] = "Destroyer";
		shipName[3] = "Submarine";
		shipName[4] = "Battleship";
		shipName[5] = "Aircraft Carrier";
		
		shipLength[1] = 2;
		shipLength[2] = 3;
		shipLength[3] = 3;
		shipLength[4] = 4;
		shipLength[5] = 5;
		
		System.out.println("Hello and welcome to Batleship");
		game.clearBoard();
		
		for (int x = 1; x < 6; x++) {
			game.printBoard();
			
			do {
				
				System.out.println("How would you like to orient your " + shipName[x] + " that is " + shipLength[x] + " spaces long?");
				System.out.println("[1] - Horizontaly (==)");
				System.out.println("[2] - Verticaly (||)");
				choice = keyboardInt.nextInt();
				
				if (choice < 1 && choice > 2) {
					redo = true;
				}
				else {
					if (choice == 1) orientation = true; // true for horizontal, false for vertical
					else if (choice == 2) orientation = false; // true for horizontal, false for vertical
				}
				
				if (!redo) {
					System.out.println("what cordinates would you like the uper left corrner of your " + shipName[x] + " that is " + shipLength[x] + " spaces long to be in (x then y)?");
					cordX = keyboardInt.nextInt();
					cordY = keyboardInt.nextInt();
					redo = !game.checkShipPlacement(orientation, shipLength[x], cordX, cordY); // toggles the result, it does this because checkShip placement returns true if it is ok
				}
				
				if (redo){
					System.out.println("\f");
					game.printBoard();
					System.out.println("That isn't a valid loaction for that ship");
				}
				
			} while (redo);
			
			for (int i = shipLength[x]; i > 0; i--) {
				
				if (i == shipLength[x]) playerShips[cordX][cordY] = true;
				
				if (orientation) playerShips[cordX + (shipLength[x] - i)][cordY] = true;
				else if (!orientation) playerShips[cordX][cordY + (shipLength[x] - i)] = true;
			}
			
			
			
			
			
			
			
			
		}
	}
	
	private boolean checkShipPlacement(boolean orin, int length, int cordX, int cordY) {
		boolean valid = true;
		// orin is true for horizontal, false for vertical
		if (cordX < 1) return false;
		if (cordY < 1) return false;
		
		if (orin) {
			if (cordX > 11 - length) return false;
			if (cordY > 11) return false;
		}
		else if (!orin) {
				if (cordX > 11) return false;
				if (cordY > 11 - length) return false;
		}
		return valid;
	}
	
	private void clearBoard() {
		// Set all spaces to nothing
		for (int x = 1; x < 11; x++) {
			
			for (int y = 1; y < 11; y++) {
				playerShips[x][y] = false;
				cpuShips[x][y] = false;
				playerHits[x][y] = false;
				cpuHits[x][y] = false;
			}
		}
	}
	
	private void printBoard() {
		String space = "ERROR";
		
		System.out.println("\tYour Ships\t\tYour Hits");
		System.out.println("   1 2 3 4 5 6 7 8 9 10\t   1 2 3 4 5 6 7 8 9 10");
		
		for (int y = 1; y < 11; y++) {
			
			if (y < 10 || y < 20) System.out.print(y + " ");
			else System.out.print(y);
			
			for (int x = 1; x < 11; x++) {
				
						if (playerShips[x][y]) {
							space = "O";
						}
						else if (!playerShips[x][y]) {
							space = "+"; // ///////////////Change TEST Purposes
						}
						if (y == 10 && x == 10) System.out.print("" + space);
						else if (y < 10) System.out.print(" " + space);
			}
			
			System.out.print("\t" + y);
			
			for (int x = 1; x < 11; x++) {
				
				if (playerShips[x][y]) {
					space = "O";
				}
				else if (!playerShips[x][y]) {
					space = "+"; // ///////////////Change TEST Purposes
				}
				if (y == 10 && x == 10) System.out.print("" + space);
				else if (y < 10) System.out.print(" " + space);
			}
			System.out.print("\n");
		}
	}
	
}
