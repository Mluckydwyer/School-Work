package Blackjack;

import java.util.Random;
import java.util.Scanner;

public class BlackjackGame implements Runnable {

	// Set up the objects
	private static Scanner keyboardInputInt;
	private static Scanner keyboardInputString;
	private static Random randomValue;
	private static Thread thread;

	// set up the variables
	private int startMoney = 10000;
	private int money = startMoney;
	private int minBet = 1;
	private int bet = 0;
	private int index = 0;
	private int gamesWon = 0;
	private int totalGames = 0;
	private int cardNum = 52;
	private int shuffleCount = 10;
	private int playerTotal = 0;
	private int dealerTotal = 0;
	private int playerCardNum = 0;
	private int dealerCardNum = 1;

	private boolean debugMode = true; // Enables debug mode
	private boolean running = false; // Tells if threads are running

	// set up the arrays
	private int[] cardValue;
	private String[] cardSuit;
	private String[] suits;
	private String[] cardDisplayName;

	// I use the constructor to set the array length and the 4 suits
	public BlackjackGame() {
		keyboardInputInt = new Scanner(System.in);
		keyboardInputString = new Scanner(System.in);
		randomValue = new Random();
		cardValue = new int[cardNum + 1];
		suits = new String[5];
		cardSuit = new String[cardNum + 1];
		cardDisplayName = new String[cardNum + 1];

		suits[1] = "Clubs";
		suits[2] = "Hearts";
		suits[3] = "Spades";
		suits[4] = "Diamonds";
	}

	// Main method just sets up the main thread
	public static void main(String[] args) {
		BlackjackGame game = new BlackjackGame();
		game.start();
	}

	/*
	 * The first thing that is called. This sets up and starts the main thread
	 * for my game
	 */
	private void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/*
	 * This is my run loop. This is my main run loop where it all stems from to
	 * call all of the other methods. This also allows me to run it as an applet
	 * if i so please
	 */
	@Override
	public void run() {
		int selection;
		boolean REDO;

		create();
		shuffle();
		setDisplayNames();
		selection = mainMenu();

		do {
			REDO = true;
			switch (selection) {
			case 1:
				REDO = play();
				break;
			case 2:
				settings();
				REDO = true;
				break;
			case 3:
				playerStats();
				REDO = true;
				break;
			case 4:
				stop();
				break;
			default:
				System.out.println("An Error Has Occured, please try again\n");
				REDO = true;
				break;
			}
		} while (REDO);
	}

	/*
	 * This is called when the game exits to save the player's progress and quit
	 * the game. The code about the thread is for joining or ending the thread
	 * that was started when the program started.
	 */
	private void stop() {
		System.out.println("\fSaving...");
		// SAVING CODE HERE

		System.out.println("Saving Complete");
		System.out.println("Thank you for playing!!!");

		if (!running)
			return;
		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	/*
	 * This creates the cards with a basic card values and suits This does this
	 * by creating the first 4 Aces and the next 4 2s an so on
	 * 
	 * card count in 4s (4, 8 ,12) because that is how many there are of each
	 * card
	 */
	private void create() {
		int count = 1;
		for (int i = 1; i <= (13 * (cardNum / 52)); i++) {
			cardValue[count] = i;
			cardValue[count + 1] = i;
			cardValue[count + 2] = i;
			cardValue[count + 3] = i;

			cardSuit[count] = suits[1];
			cardSuit[count + 1] = suits[2];
			cardSuit[count + 2] = suits[3];
			cardSuit[count + 3] = suits[4];
			count += 4;

			if (debugMode)
				for (int x = i; x <= (i + 4); x++) {
					System.out.println(i + "\t\t" + cardValue[i] + "\t\t" + cardSuit[i]);
				}
		}
	}

	/*
	 * This takes the already made cards and shuffles them and randomizes there
	 * order
	 * 
	 * shuffle count is how many times the deck is shuffled, this is because one
	 * pass of the shuffler is not enough to make them truly random so it does
	 * it as many times as shuffle count is set to, by default it is set to 10
	 * 
	 * It does this by selecting a card, then second card using the loop count
	 * value, and then sets the first the the second and the second to the first
	 * and the same for the suit
	 */
	private void shuffle() {
		for (int x = 1; x <= shuffleCount; x++) {
			for (int i = 1; i <= cardNum; i++) {
				int arrayIndex = randomValue.nextInt(cardNum) + 1;
				int num = cardValue[arrayIndex];
				cardValue[arrayIndex] = cardValue[i];
				cardValue[i] = num;

				String suit = cardSuit[arrayIndex];
				cardSuit[arrayIndex] = cardSuit[i];
				cardSuit[i] = suit;

				if (debugMode)
					System.out.println("Shuffle count:" + x + " \tSwitch:" + i + " \tSwitched card " + arrayIndex + " \tWith card " + i);
			}
		}
	}

	/*
	 * This sets the display names that the player will see in game to each card
	 * 
	 * It does this by checking and or setting the Ace, Jack, Queen, and King
	 * first before setting the rest to the default # of suit format
	 */
	private void setDisplayNames() {
		for (int i = 1; i <= cardNum; i++) {
			switch (cardValue[i]) {
			case 1:
				cardDisplayName[i] = "Ace of " + cardSuit[i];
				cardValue[i] = 11;
				break;
			case 11:
				cardDisplayName[i] = "Jack of " + cardSuit[i];
				cardValue[i] = 10;
				break;
			case 12:
				cardDisplayName[i] = "Queen of " + cardSuit[i];
				cardValue[i] = 10;
				break;
			case 13:
				cardDisplayName[i] = "King of " + cardSuit[i];
				cardValue[i] = 10;
				break;
			default:
				cardDisplayName[i] = cardValue[i] + " of " + cardSuit[i];
				break;
			}

			if (debugMode)
				System.out.println(i + "\t\t" + cardValue[i] + "\t\t" + cardSuit[i] + "\t\t\t" + cardDisplayName[i]);
		}
	}

	/*
	 * 
	 */
	private int mainMenu() {
		boolean REDO;
		int selection = 0;

		do {
			REDO = false;
			try {
				System.out.println("\fWelcome to Blackjack, please select an option from the menu below");
				System.out.println("[1]-Play Blackjack");
				System.out.println("[2]-Settings");
				System.out.println("[3]-Player Stats");
				System.out.println("[4]-Quit");

				selection = keyboardInputInt.nextInt();
				if (selection > 4 || selection < 1) {
					REDO = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("That is not a vaild selection Here\n");
				REDO = true;
			}
		} while (REDO);
		return selection;
	}

	/*
	 * This will handle the player achievements menu and display a screen where
	 * the player can see there wins to loses and money
	 */
	private void playerStats() {
		// TODO Auto-generated method stub

	}

	/*
	 * This will handle the setting menu and all the things the user can change
	 */
	private void settings() {
		// TODO Auto-generated method stub

	}

	/*
	 * 
	 */
	private boolean play() {
		boolean REDO;
		boolean REPLAY = false;

		int bet = 0;

		System.out.println("\fWelcome to the Blackjack Table. Here, the minimum bet is $" + minBet);
		System.out.println("Your Info:  $" + money + "  Wins: " + gamesWon + "/" + totalGames + "\n");

		do {

			try {
				REDO = false;
				System.out.println("What would you like to bet?");
				bet = keyboardInputInt.nextInt();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.print("\nThat is not a vaild bet. Here, the minimum bet is $" + minBet);
				REDO = true;
			}
		} while (bet <= 0);

		System.out.println("\fYou are dealed the " + cardDisplayName[index + 1] + " & the " + cardDisplayName[index + 2]);
		System.out.println("Your total is: " + (cardValue[index + 1] + cardValue[index + 2]));
		playerTotal = cardValue[index + 1] + cardValue[index + 2];
		index += 2;
		playerCardNum += 2;

		System.out.println("\nThe dealer is showing the " + cardDisplayName[index + 1] + " & a hidden card");
		dealerTotal = cardValue[index + 1] + cardValue[index + 2];

		if (debugMode) {
			System.out.print(" (" + cardDisplayName[index + 2] + ")");
			System.out.println("The dealers total is: " + dealerTotal);
		}

		index += 2;
		dealerCardNum += 2;

		do {
			int selection = 0;

			try {
				REDO = false;
				System.out.println("\nWhat would you like to do:");
				System.out.println("[1]-Hit");
				System.out.println("[2]-Stay");
				selection = keyboardInputInt.nextInt();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("That is not a vaild selection");
				REDO = true;
			}

			if (!REDO) {
				switch (selection) {
				case 1:
					hit();
					break;
				case 2:
					dealer();
					break;
				default:
					System.out.println("That is not a vaild selection");
					REDO = true;
					break;
				}
			}
		} while (REDO);

		do {
			REDO = false;
			String playAgain = "ERROR";

			try {
				System.out.println("\nWould you like to play again?");
				playAgain = keyboardInputString.next();
				System.out.println("Test");

				if (playAgain.equalsIgnoreCase("yes") || playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("1")) {
					REPLAY = true;
				} else if (playAgain.equalsIgnoreCase("no") || playAgain.equalsIgnoreCase("n") || playAgain.equalsIgnoreCase("2")) {
					REPLAY = false;
				} else {
					System.out.print("\nThat is not a vaild answer");
					REDO = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.print("\nThat is not a vaild answer");
				REDO = true;
			}
		} while (REDO);

		return REPLAY;
	}

	/*
	 * This handles if the player takes a hit when playing
	 * 
	 * The -2 & -1 when displaying the cards of there to compensate for the
	 * cards that have already been drawn
	 */
	private void hit() {
		index++;
		System.out.print("\fYou are dealed the " + cardDisplayName[index] + " Along with your ");

		for (int i = playerCardNum; i > 0; i--) {
			if (playerCardNum == 2) {
				System.out.print(cardDisplayName[(index - (i + 2))]);
			} else {
				System.out.print(cardDisplayName[index - (i + 1)]);
			}

			if (i > 1) {
				System.out.print(" & your ");
			}
		}
		playerCardNum++;
		playerTotal += cardValue[index];
		System.out.println("\nYour total is: " + playerTotal);

		if (playerTotal > 21) {

			for (int i = playerCardNum; i > 0; i--) {

				if (i == playerCardNum) {

				}
			}
			System.out.println("You Busted");
			money -= bet;
		}
	}

	/*
	 * This checks if the player has won or got blackjack. This also checks if
	 * the player has lost.
	 */
	private void checkWin() {

	}

	/*
	 * This handles the dealer when they are hitting and is called when the
	 * player stays
	 */
	private void dealer() {
		System.out.println("\nThe dealer shows the " + cardDisplayName[index - (playerCardNum - 1)] + " & the " + cardDisplayName[index - (playerCardNum - 2)]);
		dealerTotal = cardValue[index + 1] + cardValue[index + 2];
	}
}
