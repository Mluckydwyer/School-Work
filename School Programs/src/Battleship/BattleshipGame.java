package Battleship;

import java.util.Scanner;

public class BattleshipGame {

	// Creates the arrays 10x10 for the boards
	public static boolean[][] playerShips = new boolean[11][11];
	public static boolean[][] cpuShips = new boolean[11][11];
	public static boolean[][] playerHits = new boolean[11][11];
	public static boolean[][] cpuHits = new boolean[11][11];
	public static String[] shipName = new String[6];
	public static int[] shipLength = new int[6];

	public static BattleshipGame game;
	public static Scanner keyboardInt = new Scanner(System.in);

	public static void main(String[] args) {
		game = new BattleshipGame();
		int choice;
		boolean redo = false;

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
		game.printBoard("playerShips");

		for (int x = 1; x < 6; x++) {
			do {

				System.out.println("How would you like to orient your ?" + shipName[x]);
				System.out.println("[1] - Horizontaly (=====)");
				System.out.println("[2] - Verticaly (||)");
				choice = keyboardInt.nextInt();

				switch (choice) {
				case 1:

					break;
				case 2:

					break;
				default:

					break;
				}
			} while (redo);
			
			do {

				System.out.println("what cor ?" + shipName[x]);
				System.out.println("[1] - Horizontaly (=====)");
				System.out.println("[2] - Verticaly (||)");
				choice = keyboardInt.nextInt();

				switch (choice) {
				case 1:

					break;
				case 2:

					break;
				default:

					break;
				}
			} while (redo);
		}
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

	private void printBoard(String board) {
		String space = "ERROR";

		System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10");

		for (int y = 1; y < 11; y++) {

			System.out.println(y);

			for (int x = 1; x < 11; x++) {

				switch (board) {
				case "playerShips":
					if (playerShips[x][y] = true) {
						space = "S";
					} else if (playerShips[x][y] = false) {
						space = "X";
					}
					break;
				case "cpuShips":
					if (cpuShips[x][y] = true) {
						space = "S";
					} else if (cpuShips[x][y] = false) {
						space = "X";
					}
					break;
				case "playerHits":
					if (playerHits[x][y] = true) {
						space = "S";
					} else if (playerHits[x][y] = false) {
						space = "X";
					}
					break;
				case "cpuHits":
					if (cpuHits[x][y] = true) {
						space = "S";
					} else if (cpuHits[x][y] = false) {
						space = "X";
					}
					break;
				}

				if (x < 10)
					System.out.print("\t" + space);
				else if (x == 10)
					System.out.println("\t" + space);
			}
		}
	}

}