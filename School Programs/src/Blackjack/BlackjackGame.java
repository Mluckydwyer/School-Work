package Blackjack;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;

public class BlackjackGame implements Runnable {

	private static Scanner keyboardInput;
	private static Random randomValue;
	private Thread thread;

	private int startMoney = 10000;
	private int money = startMoney;
	private int minBet = 1;
	private int bet = 0;
	private int index = 0;
	private int gamesWon = 0;
	private int totalGames = 0;
	private int cardNum = 52;
	private int playerTotal = 0;
	private int dealerTotal = 0;
	private int playerCardNum = 2;
	private int dealerCardNum = 2;

	private boolean textInput;
	private boolean running = false;

	private int[] cardValue;
	private String[] cardSuit;
	private String[] suits;
	private String[] cardDisplayName;

	public BlackjackGame() {
		keyboardInput = new Scanner(System.in);
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

	public static void main(String[] args) {
		BlackjackGame game = new BlackjackGame();
		game.start();
	}

	@Override
	public void run() {
		int selection;
		boolean REDO = true;

		create();
		shuffle();
		setDisplayNames();
		selection = mainMenu();

		while (REDO) {
			switch (selection) {
			case 1:
				REDO = play();
				break;
			case 2:
				settings();
				break;
			case 3:
				playerStats();
				break;
			case 4:
				quit();
				break;
			default:
				System.out.println("An Error Has Occured, please try again\n");
				REDO = true;
				break;
			}
		}
	}

	private void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private void stop() {
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

	public void create() {
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
		}
	}

	public void shuffle() {
		for (int i = 1; i <= cardNum; i++) {
			int arrayIndex = randomValue.nextInt(cardNum + 1);
			int num = cardValue[arrayIndex];
			cardValue[arrayIndex] = cardValue[i];
			cardValue[i] = num;

			String suit = cardSuit[arrayIndex];
			cardSuit[arrayIndex] = cardSuit[i];
			cardSuit[i] = suit;
		}
	}

	public void setDisplayNames() {
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
			System.out.println(i + "\t\t" + cardValue[i] + "\t\t" + cardSuit[i] + "\t\t" + cardDisplayName[i]);
		}
	}

	public int mainMenu() {
		boolean REDO;
		int selection = 0;

		do {
			REDO = false;
			try {
				System.out.println("Welcome to Blackjack, please select an option from the menu below");
				System.out.println("[1]-Play Blackjack");
				System.out.println("[2]-Settings");
				System.out.println("[3]-Player Stats");
				System.out.println("[4]-Quit");

				selection = keyboardInput.nextInt();
				if (selection > 4 || selection < 1) {
					REDO = true;
				}
			} catch (Exception e) {
				System.out.println("That is not a vaild selection Here\n");
				REDO = true;
			}
		} while (REDO);
		return selection;
	}

	private void playerStats() {
		// TODO Auto-generated method stub

	}

	private void settings() {
		// TODO Auto-generated method stub

	}

	public boolean play() {
		boolean REDO;
		boolean REPLAY = false;

		int bet = 0;

		System.out.println("\fWelcome to the Blackjack Table. Here, the minimum bet is $" + minBet);
		System.out.println("Your Info:  $" + money + "  Wins: " + gamesWon + "/" + totalGames + "\n");
		do {
			try {
				REDO = false;
				System.out.println("What would you like to bet?");
				bet = keyboardInput.nextInt();
			} catch (Exception e) {
				System.out.print("\nThat is not a vaild bet. Here, the minimum bet is $" + minBet);
				REDO = true;
			}
		} while (bet <= 0);

		System.out.println("\f");
		System.out.println("\nYou are dealed the " + cardDisplayName[index + 1] + " & the " + cardDisplayName[index + 2]);
		System.out.println("Your total is: " + (cardValue[index + 1] + cardValue[index + 2]));
		playerTotal = cardValue[index + 1] + cardValue[index + 2];
		index += 2;

		System.out.println("\nThe dealer is showing the " + cardDisplayName[index + 1] + " & a hidden card (" + cardDisplayName[index + 2] + ")");
		dealerTotal = cardValue[index + 1] + cardValue[index + 2];
		index += 2;

		if (playerTotal == 21) {
			System.out.println("\nYou got blackjack!!!");
		}

		do {
			int selection = 0;
			try {
				REDO = false;
				System.out.println("\nWhat would you like to do:");
				System.out.println("[1]-Hit");
				System.out.println("[2]-Stay");
				selection = keyboardInput.nextInt();
				if (selection < 1 && selection > 2) {
					System.out.println("That is not a vaild selection (1 || 2)");
					// REDO = true;
				}
			} catch (Exception e) {
				System.out.println("That is not a vaild selection, Exception caught");
				REDO = true;
			}

			if (!REDO) {
				switch (selection) {
				case 1:
					REDO = true;
					index++;
					System.out.print("You are dealed the " + cardDisplayName[index + 1] + " Along with your ");

					for (int i = playerCardNum; i > 0; i--) {
						if (playerCardNum == 2) {
							System.out.print("\t\t" + cardDisplayName[(index - (i + 2))]);// -2 for the cards already drawn
						} else {
							System.out.print("\t\t" + cardDisplayName[index - (i + 1)]);
						}

						if (i > 1) {
							System.out.print("\t\t" + " & your " + "\t\t");
						}
					}
					playerCardNum++;
					playerTotal += cardValue[index + 1];
					System.out.println("\nYour total is: " + playerTotal);
					if (playerTotal > 21) {
						System.out.println("You Busted");
						money -= bet;
						REDO = false;
					}

					break;
				case 2:

					break;
				default:

					break;
				}
			}

		} while (REDO);

		System.out.println("\nWould you like to play again?");
		do {
			REDO = true;
			String playAgain = "ERROR";
			try {
				REDO = false;
				System.out.println("What would you like to bet?");
				keyboardInput.reset();
				playAgain = keyboardInput.nextLine();
			} catch (Exception e) {
				System.out.print("\nThat is not a vaild answer");
				REDO = true;
			}

			if (playAgain.equalsIgnoreCase("yes") || playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("1")) {
				REPLAY = true;
			}
			if (playAgain.equalsIgnoreCase("no") || playAgain.equalsIgnoreCase("n") || playAgain.equalsIgnoreCase("2")) {
				REPLAY = false;
			} else {
				REDO = true;
			}
		} while (REDO);
		
		return REPLAY;
	}

	private void quit() {
		System.out.println("\fSaving...");
		// SAVING CODE HERE

		System.out.println("Saving Complete");
		System.out.println("Thank you for playing!!!");
		System.exit(0);
	}

}
