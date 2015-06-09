package Battleship;

import java.util.Random;
import java.util.Scanner;

public class BattleshipGame {

	// Creates the arrays 10x10 for the boards
	private static boolean[][] playerShips = new boolean[11][11];
	private static boolean[][] cpuShips = new boolean[11][11];
	private static boolean[][] playerHits = new boolean[11][11];
	private static boolean[][] cpuHits = new boolean[11][11];
	private static String[] shipName = new String[6];
	private static int[] shipLength = new int[6];

	private static BattleshipGame game;
	private static Scanner keyboardInt = new Scanner(System.in);
	private static Random random;

	public static int cpuHitTotal;
	public static int playerHitTotal;

	public static void main(String[] args) {
		boolean replay = false;

		do {
			game = new BattleshipGame();
			random = new Random();
			int choice;
			int cordX = 0;
			int cordY = 0;
			cpuHitTotal = 0;
			playerHitTotal = 0;

			boolean redo = false;
			boolean orientation = false; // true for horizontal, false for
											// vertical
			redo = false;

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

			System.out.println("Hello and Welcome to Batleship");
			game.clearBoard();
			game.cpuShipPlacement();

			for (int x = 1; x < 6; x++) {
				game.printBoard();

				do {
					redo = false;
					System.out.println("How would you like to orient your " + shipName[x] + " that is " + shipLength[x] + " spaces long?");
					System.out.println("[1] - Horizontaly (==)");
					System.out.println("[2] - Verticaly (||)");
					choice = keyboardInt.nextInt();

					if (choice < 1 && choice > 2) {
						redo = true;
					} else {
						if (choice == 1)
							orientation = true; // true for horizontal, false
												// for
												// vertical
						else if (choice == 2)
							orientation = false; // true for horizontal, false
													// for
													// vertical
					}

					if (!redo) {
						System.out.println("What cordinates would you like the uper left corrner of your " + shipName[x] + " that is " + shipLength[x] + " spaces long to be in (x then y)?");
						cordX = keyboardInt.nextInt();
						cordY = keyboardInt.nextInt();
						// toggles the result, it does this because checkShip
						// placement returns true if it is ok
						redo = !game.checkShipPlacement(orientation, shipLength[x], cordX, cordY, "player");
					}

					if (redo) {
						System.out.println("\f");
						game.printBoard();
						System.out.println("That isn't a valid loaction for that ship");
					}

				} while (redo);

				for (int i = shipLength[x]; i > 0; i--) {

					if (i == shipLength[x])
						playerShips[cordX][cordY] = true;

					if (orientation)
						playerShips[cordX + (shipLength[x] - i)][cordY] = true;
					else if (!orientation)
						playerShips[cordX][cordY + (shipLength[x] - i)] = true;
				}
			}

			while (playerHitTotal < 17 && cpuHitTotal < 17) {

				do {
					redo = false;
					game.printBoard();
					System.out.println("What cordinates would you like to fire upon?");
					cordX = keyboardInt.nextInt();
					cordY = keyboardInt.nextInt();

					if (cordX > 10 || cordX < 1 || cordY > 10 || cordY < 1)
						redo = true;

					if (redo) {
						System.out.println("\f");
						game.printBoard();
						System.out.println("That isn't a valid loaction");
					}
				} while (redo);
				playerHits[cordX][cordY] = true;

				if (cpuShips[cordX][cordY]) {
					System.out.println("\nYou hit the CPU's battleship at (" + cordX + "," + cordY + ")");
					playerHitTotal++;
				} else {
					System.out.println("\nYou missed at (" + cordX + "," + cordY + ")");
				}

				if (playerHitTotal < 17 && cpuHitTotal < 17)
					game.cpuShot();
			}

			if (playerHitTotal == 17)
				System.out.println("\nYou Won!!!");
			else if (cpuHitTotal == 17)
				System.out.println("\nYou Lost.");

			do {
				redo = false;
				System.out.println("Would you like to play again (1:Yes, 2:No)?");
				choice = keyboardInt.nextInt();
				
				if (choice == 1) replay = true;
				else if (choice == 2) replay = false;	
				else if (choice != 1 && choice != 2) redo = true;
					
					
			} while (redo);
			
		} while (replay);
		System.out.println("\fThanks For Playing");
	}

	private void cpuShot() {
		int cordX = random.nextInt(10) + 1;
		int cordY = random.nextInt(10) + 1;

		cpuHits[cordX][cordY] = true;

		if (playerShips[cordX][cordY]) {
			System.out.println("The CPU hit your battleship at (" + cordX + "," + cordY + ")");
			cpuHitTotal++;
		} else {
			System.out.println("The CPU missed at (" + cordX + "," + cordY + ")");
		}
	}

	private void cpuShipPlacement() {
		boolean redo = false;
		int cordX;
		int cordY;
		// orin is true for horizontal, false for vertical
		boolean orin;

		for (int x = 1; x < 6; x++) {

			do {
				redo = false;
				orin = random.nextBoolean();
				cordX = random.nextInt(10) + 1;
				cordY = random.nextInt(10) + 1;

				redo = !game.checkShipPlacement(orin, shipLength[x], cordX, cordY, "cpu");
			} while (redo);

			for (int i = shipLength[x]; i > 0; i--) {

				if (i == shipLength[x])
					cpuShips[cordX][cordY] = true;

				if (orin)
					cpuShips[cordX + (shipLength[x] - i)][cordY] = true;
				else if (!orin)
					cpuShips[cordX][cordY + (shipLength[x] - i)] = true;
			}
		}
	}

	private boolean checkShipPlacement(boolean orin, int length, int cordX, int cordY, String player) {
		// orin is true for horizontal, false for vertical
		if (cordX < 1)
			return false;
		if (cordY < 1)
			return false;

		if (orin) {
			if (cordX + length > 11)
				return false;
			if (cordY > 11)
				return false;
		} else if (!orin) {
			if (cordX > 11)
				return false;
			if (cordY + length > 11)
				return false;
		}

		if (player.equals("player")) {
			for (int i = length; i > 0; i--) {
				if (orin && playerShips[cordX + i - 1][cordY])
					return false;
				if (!orin && playerShips[cordX][cordY + i - 1])
					return false;
			}
		} else if (player.equals("cpu")) {
			for (int i = length; i > 0; i--) {
				if (orin && cpuShips[cordX + i - 1][cordY])
					return false;
				if (!orin && cpuShips[cordX][cordY + i - 1])
					return false;
			}
		}
		return true;
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

		System.out.println("\n\tYour Ships\t\tCPU Ships");
		System.out.println("   1 2 3 4 5 6 7 8 9 10\t   1 2 3 4 5 6 7 8 9 10");

		for (int y = 1; y < 11; y++) {

			if (y < 10 || y < 20)
				System.out.print(y + " ");
			else
				System.out.print(y);

			for (int x = 1; x < 11; x++) {
				
				if (playerShips[x][y] && cpuHits[x][y]) {
					space = "X";
				} else if (!playerShips[x][y]) {
					space = ".";
				} else if (playerShips[x][y]) {
					space = "O";
				}

				if (y == 10 && x == 1)
					System.out.print("" + space);
				else
					System.out.print(" " + space);
			}

			System.out.print("\t" + y);
			// ///////////////////////Printing out CPU ships for testing
			// purposes
			for (int x = 1; x < 11; x++) {

				if (cpuShips[x][y] && playerHits[x][y]) {
					space = "X";
				} else if (!cpuShips[x][y]) {
					space = ".";
				} else if (cpuShips[x][y]) {
					space = "O";
				}

				if (y == 10 && x == 1)
					System.out.print(" " + space);
				else if (x == 1)
					System.out.print("  " + space);
				else
					System.out.print(" " + space);
			}
			System.out.print("\n");
		}
	}

}
