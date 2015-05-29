package Blackjack;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class BlackjackGame implements Runnable {

	// Set up the objects
	private static Scanner keyboardInputInt;
	private static Scanner keyboardInputString;
	private static Scanner sf;
	private static Random randomValue;
	private static FileWriter fw;
	private static PrintWriter output;
	private static Thread thread;

	/*
	 * Set up the variables (These are the default values before being read from
	 * the file)
	 */
	private int startMoney = 10000;
	private int money = 0;
	private int minBet = 1;
	private int bet = 0;
	private int index = 0;
	private int gamesWon = 0;
	private int totalGames = 0;
	private int cardNum = 52;
	private int shuffleCount = 20;
	private int payOutPercent = 2;
	private int playerTotal = 0;
	private int dealerTotal = 0;
	private int playerCardNum = 0;
	private int dealerCardNum = 0;

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
	 * This is my run loop. This is my main run loop for the game thread where
	 * it all stems from to call all of the other methods. This also allows me
	 * to run it as an application if i so please because it implements
	 * runnable.
	 */
	@Override
	public void run() {
		int selection;
		boolean REDO;
		boolean threadRunning = true;

		// Calls the main menu and gets the user's choice
		selection = mainMenu();

		do {
			REDO = true;

			// Calls methods based upon what the user picks
			switch (selection) {
			case 1:
				create();
				shuffle();
				setDisplayNames();
				resetVariables();
				REDO = play();
				break;
			case 2:
				stop();
				break;
			default:
				System.out.println("An Error Has Occured, Please Try Again\n");
				REDO = true;
				break;
			}
			save();

			if (!REDO)
				selection = mainMenu();
			REDO = true;
		} while (REDO);
	}

	/*
	 * This is called when the game exits to save the player's progress and quit
	 * the game. The code about the thread is for joining or ending the thread
	 * that was started when the program started.
	 */
	private void stop() {
		save();
		System.out.println("Thank you for playing!!!");

		if (!running)
			return;
		running = false;

		try {
			keyboardInputInt.close();
			keyboardInputString.close();
			System.exit(0);
			thread.join();
		} catch (InterruptedException e) {

			if (debugMode)
				e.printStackTrace();
		}
	}

	/*
	 * This resets the variables before another game is played
	 */
	private void resetVariables() {
		bet = 0;
		index = 0;
		playerTotal = 0;
		dealerTotal = 0;
		playerCardNum = 0;
		dealerCardNum = 0;
	}

	/*
	 * Saves the statistics after each change to prevent data loss because of a
	 * unexpected termination
	 */
	private void save() {

		try {
			// Save file code
			fw = new FileWriter("Blackjack.out");
			output = new PrintWriter(fw);
			output.println(money);
			output.println(minBet);
			output.println(gamesWon);
			output.println(totalGames);
			fw.close();
			output.close();
		} catch (Exception e1) {

			if (debugMode) {
				e1.printStackTrace();
				System.out.println("Failed to the file");
			}
		}

		if (debugMode)
			System.out.println("Saving Complete");
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
	 * This displays the main menu and then returns the users selection
	 */
	private int mainMenu() {
		boolean REDO;
		int selection = 0;

		// Loading/Creating the file Code
		if (debugMode)
			System.out.println("Now reading from file...");

		/*
		 * Test to see if there is already a file that has been created.This
		 * throws an exception if there isn't one, otherwise it loads it
		 */
		try {
			// Load file code
			sf = new Scanner(new File("Blackjack.out"));
			money = sf.nextInt();
			minBet = sf.nextInt();
			gamesWon = sf.nextInt();
			totalGames = sf.nextInt();
			sf.close();
		} catch (Exception e) {

			if (debugMode)
				System.out.println("A prexsiting setting file doesn't exist, Creating a new one...");
			/*
			 * Sets the player's money to the start money because they aren't
			 * loading a game
			 */
			money = startMoney;

			try {
				// Create new file code
				fw = new FileWriter("Blackjack.out");
				output = new PrintWriter(fw);
				output.println(money);
				output.println(minBet);
				output.println(gamesWon);
				output.println(totalGames);
				fw.close();
				output.close();
			} catch (Exception e1) {

				if (debugMode)
					e1.printStackTrace();
				System.out.println("Failed to create a new file");
			}
		}

		// Display the main menu
		do {
			REDO = false;

			try {
				System.out.println("\fWelcome to Blackjack, please select an option from the menu below");
				System.out.println("[1]-Play Blackjack");
				System.out.println("[2]-Quit");
				selection = keyboardInputInt.nextInt();

				if (selection > 2 || selection < 1)
					REDO = true;
			} catch (Exception e) {
				if (debugMode)
					e.printStackTrace();
				System.out.println("That is not a vaild selection Here\n");
				keyboardInputInt.nextLine();
				REDO = true;
			}
		} while (REDO);
		return selection;
	}

	/*
	 * This method deals with drawing the dealer's and play's first 2 cards, and
	 * then calling methods based upon what the player and dealer decide to do
	 * (stay, hit, if they bust).
	 */
	private boolean play() {
		boolean REDO;
		boolean REPLAY = false;
		bet = 0;
		totalGames++;

		// Gets the player's bet
		System.out.println("\fWelcome to the Blackjack Table. Here, the minimum bet is $" + minBet);
		System.out.println("Your Info:  $" + money + "  Wins: " + gamesWon + "/" + totalGames);

		do {

			try {
				REDO = false;
				System.out.println("What would you like to bet?");
				bet = keyboardInputInt.nextInt();

				if (bet < minBet || bet > money) {
					System.out.println("\nThat is not a vaild bet. Here, the minimum bet is $" + minBet);
					REDO = true;
				}
			} catch (Exception e) {
				if (debugMode)
					e.printStackTrace();
				System.out.println("\nThat is not a vaild bet. Here, the minimum bet is $" + minBet);
				keyboardInputInt.nextLine();
				REDO = true;
			}
		} while (REDO);

		/*
		 * Starts the game The dealers cards are card #1 & #2 and players are #3
		 * & #4 and are drawn after
		 */
		playerTotal = cardValue[index + 3] + cardValue[index + 4];
		index += 4;
		playerCardNum += 2;

		// Checks for player's double Aces
		if (playerTotal > 21) {
			for (int i = playerCardNum + 1, count = 1; i > 1 && count > 0; i--) {

				if (cardValue[index - i] == 11) {
					cardValue[index - i] = 1;
					playerTotal = cardValue[index - 1] + cardValue[index];
					count--;
				}
			}
		}

		System.out.println("\fYou are dealed the " + cardDisplayName[index - 1] + " & the " + cardDisplayName[index]);
		System.out.println("Your total is: " + playerTotal);

		// Checks for dealer's double Aces
		dealerTotal = cardValue[index - 3] + cardValue[index - 2];
		dealerCardNum += 2;

		if (dealerTotal > 21) {
			for (int i = dealerCardNum, count = 1; i > 0 && count > 0; i--) {

				if (cardValue[i] == 11) {
					cardValue[i] = 1;
					dealerTotal = cardValue[index - 3] + cardValue[index - 2];
					count--;
				}
			}
		}

		System.out.println("\nThe dealer is showing the " + cardDisplayName[index - 3] + " & a hidden card");

		if (debugMode) {
			System.out.print(" (" + cardDisplayName[index - 2] + ")");
			System.out.println("The dealers total is: " + dealerTotal);
		}

		// This deals with the player hitting and staying
		do {
			int selection = 0;
			REDO = false;

			if (playerTotal != 21) {

				try {
					System.out.println("\nWhat would you like to do:");
					System.out.println("[1]-Hit");
					System.out.println("[2]-Stay");
					selection = keyboardInputInt.nextInt();
				} catch (Exception e) {

					if (debugMode)
						e.printStackTrace();
					System.out.println("That is not a vaild selection");
					keyboardInputInt.nextLine();
					REDO = true;
				}

				if (!REDO) {
					switch (selection) {
					case 1:
						hit();
						REDO = checkWin("bust");
						break;
					case 2:
						break;
					default:
						System.out.println("That is not a vaild selection");
						REDO = true;
						break;
					}
				}
			}

			dealer();
			checkWin("bust");
			checkWin("blackjack");
			checkWin("winner");

		} while (REDO);

		// After the game, this sees if the player wants to play again
		do {
			REDO = false;
			String playAgain = "ERROR";
			save();

			try {
				System.out.println("\nWould you like to play again?");
				playAgain = keyboardInputString.next();

				if (playAgain.equalsIgnoreCase("yes") || playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("1")) {
					REPLAY = true;
				} else if (playAgain.equalsIgnoreCase("no") || playAgain.equalsIgnoreCase("n") || playAgain.equalsIgnoreCase("2")) {
					REPLAY = false;
				} else {
					System.out.print("\nThat is not a vaild answer");
					REDO = true;
				}
			} catch (Exception e) {
				if (debugMode)
					e.printStackTrace();
				System.out.print("\nThat is not a vaild answer");
				keyboardInputInt.nextLine();
				REDO = true;
			}
		} while (REDO);

		return REPLAY;
	}

	/*
	 * This handles if the player takes a hit when playing. That means printing
	 * out their cards after hitting and adding to their total.
	 */
	private void hit() {
		index++;
		System.out.print("\fYou are dealed the " + cardDisplayName[index] + " along with your ");

		for (int i = playerCardNum; i > 0; i--) {
			System.out.print(cardDisplayName[index - (i + 2)]);

			if (i > 1) {
				System.out.print(" & your ");
			}
		}
		playerCardNum++;
		playerTotal += cardValue[index];
		System.out.println("\nYour total is: " + playerTotal);
	}

	/*
	 * This handles the dealer when they are hitting and is called when the
	 * player stays. This decides if the dealer should hit and acts like the hit
	 * method for the dealer.
	 */
	private void dealer() {
		System.out.println("\fThe dealer shows the " + cardDisplayName[index - (playerCardNum + dealerCardNum) + 1] + " & the " + cardDisplayName[index - (playerCardNum + dealerCardNum) + 2]);
		System.out.println("His total is: " + dealerTotal);

		while (dealerTotal <= 16) {

			System.out.print("\nThe dealer takes a hit and is dealed the " + cardDisplayName[index + 1] + " Along with his ");

			// prints out past cards
			for (int i = dealerCardNum, printCount = 1; i > 0; i--, printCount++) {

				if (printCount <= 2) {
					System.out.print(cardDisplayName[printCount]);
				} else {
					System.out.print(cardDisplayName[index - (i - 2)]);
				}

				if (i > 1) {
					System.out.print(" & his ");
				}
			}

			index++;
			dealerCardNum++;
			dealerTotal += cardValue[index];
			System.out.println("\nHis total is: " + dealerTotal);
		}

		if (dealerTotal <= 21)
			System.out.println("\nThe dealer stays");
	}

	/*
	 * This method deals with checking any type of winning or losing. It does
	 * this by taking the check type string and checking different parts
	 * depending what the value is.
	 * 
	 * Here are the values:
	 * 
	 * [Bust] - checks if the dealer or player has busted
	 * 
	 * [Winner] - Compares the dealer's total to the player's total and
	 * determines a winner or a push
	 */
	private boolean checkWin(String type) {

		boolean hitOption = true;

		switch (type) {
		case "bust":
			boolean playerBust = false;
			boolean dealerBust = false;
			boolean recheck = false;

			// Player bust
			// Deals with Aces
			if (playerTotal > 21) {
				for (int i = playerCardNum + 2, count = 1; i > 2 && count > 0; i--) {

					if (cardValue[i] == 11) {
						cardValue[i] = 1;
						playerTotal = cardValue[index - 1] + cardValue[index];
						count--;
					}
				}

				for (int x = playerCardNum; x > 2; x--) {
					playerTotal += cardValue[index - ((dealerCardNum - 2) + x)];
				}
			}

			if (playerTotal > 21) {
				playerBust = true;
				hitOption = false;
				System.out.println("\nYou busted, the dealer wins your $" + bet);
				money -= bet;
			}

			// Dealer Bust
			if (dealerTotal > 21) {

				for (int i = dealerCardNum, count = 1; i > 2 && count > 0; i--) {

					if (i <= 2) {
						if (cardValue[(index - (playerCardNum + i + (dealerCardNum - 2)))] == 11) {
							cardValue[(index - (playerCardNum + i + (dealerCardNum - 2)))] = 1;
							dealerTotal = cardValue[index - 1] + cardValue[index];
							count++;
						}
					} else if (i >= 3) {
						if (cardValue[index - (dealerCardNum - i)] == 11) {
							cardValue[index - (dealerCardNum - i)] = 1;
							dealerTotal = cardValue[index - (dealerCardNum + playerCardNum) + 1] + cardValue[index - (dealerCardNum + playerCardNum) + 2];

							for (int x = dealerCardNum; x > 2; x--) {
								dealerTotal += cardValue[index - x];
							}
							count--;
						}
					}

					if (cardValue[i] == 11) {
						cardValue[i] = 1;
						count++;
					}
				}
			}

			if (dealerTotal > 21) {
				dealerBust = true;
				hitOption = false;
				System.out.println("\nThe dealer busted, you win $" + (bet * payOutPercent));
				money += bet * payOutPercent;
			}
			break;
		case "winner":
			if (dealerTotal > playerTotal && dealerTotal < 21) {
				System.out.println("\nThe dealer wins " + dealerTotal + " to " + playerTotal + ", you lost your $" + bet);
				money -= bet;
				hitOption = false;
			} else if (playerTotal > dealerTotal && playerTotal < 21) {
				System.out.println("\nYou win " + playerTotal + " to " + dealerTotal + ", you also win $" + (bet * payOutPercent));
				money += bet * payOutPercent;
				gamesWon++;
				hitOption = false;
			} else if (dealerTotal == playerTotal && dealerTotal < 21 && playerTotal < 21) {
				System.out.println("\nThe you and the dealer pushed " + playerTotal + " to " + dealerTotal + ", no money changed hands");
				hitOption = false;
			}

			break;
		case "blackjack":
			hitOption = true;

			if (dealerTotal == 21) {
				System.out.println("\nThe dealer has blackjack");
				hitOption = false;
			}

			if (playerTotal == 21) {
				System.out.println("\nYou got blackjack");
				hitOption = false;
			}
			break;
		}

		return hitOption;
	}

}
