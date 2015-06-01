package Battleship;

public class BattleshipGame {

	// Creates the arrays 10x10
	public static boolean[][] playerShips = new boolean[11][11];
	public static boolean[][] cpuShips = new boolean[11][11];
	public static boolean[][] playerHits = new boolean[11][11];
	public static boolean[][] cpuHits = new boolean[11][11];

	public static BattleshipGame game;

	public static void main(String[] args) {

		game = new BattleshipGame();

		System.out.println("Hello and welcome to Batleship");
		game.clearBoard();
		game.printBoard();
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

		System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10");

		for (int y = 1; y < 11; y++) {

			System.out.println(y);

			for (int x = 1; x < 11; x++) {

				if (playerShips[x][y] = true) {
					space = "S";
				} else if (playerShips[x][y] = false) {
					space = "X";
				}

				if (x < 10)
					System.out.print("\t" + space);
				else if (x == 10)
					System.out.println("\t" + space);
			}
		}
	}

}
