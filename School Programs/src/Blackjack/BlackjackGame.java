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
	private static BlackjackGame game;

	private int startMoney = 10000;
	private int money = startMoney;
	private int minBet = -999;
	private int index = 0;
	private int gamesWon = 0;
	private int totalGames = 0;
	private int cardNum = 52;

	private boolean textInput;

	private int[] cardValue;
	private String[] suits;
	private String[] cardSuit;
	private String[] cardDisplayName;

	public BlackjackGame() {
		keyboardInput = new Scanner(System.in);
		randomValue = new Random();
		game = new BlackjackGame();
		cardValue = new int[game.cardNum + 1];
		suits = new String[5];
		cardSuit = new String[game.cardNum + 1];
		cardDisplayName = new String[game.cardNum + 1];

		game.suits[1] = "Clubs";
		game.suits[2] = "Hearts";
		game.suits[3] = "Spades";
		game.suits[4] = "Diamonds";
	}

	public static void main(String[] args) {
		game.shuffle();
		game.setDisplayNames();
		game.mainMenu();
	}

	@Override
	public void run() {

	}

	public void shuffle() {
		for (int i = 1; i <= 52; i++) {
			boolean OPEN = false;

			do {
				cardValue[i] = (randomValue.nextInt(12) + 1);
				cardSuit[i] = suits[randomValue.nextInt(4) + 1];
				if (i > 1) {
					for (int x = 1; x < i; x++) {
						if (cardValue[i] == cardValue[x] && cardSuit[i] == cardSuit[x]) {
							OPEN = false;
						} else {
							OPEN = true;
						}
					}
				} else {
					OPEN = true;
				}
			} while (!OPEN);
		}
	}

	public void setDisplayNames() {

		for (int i = 1; i <= 52; i++) {
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
		}
	}

	public void mainMenu() {
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

				switch (selection) {
				case 1:
					game.play();
					break;
				case 2:
					game.settings();
					break;
				case 3:
					game.playerStats();
					break;
				case 4:
					game.quit();
					break;
				default:
					System.out.println("An Error Has Occured, please try again");
					REDO = true;
				}
				break;
			} catch (Exception e) {
				System.out.println("That is not a vaild selection");
				REDO = true;
			}
		} while (REDO);
	}

	private void playerStats() {
		// TODO Auto-generated method stub

	}

	private void settings() {
		// TODO Auto-generated method stub

	}

	public void play() {
		int selection;
		boolean REDO = 0;

		System.out.println("\fWelcome to the Blackjack Table. Here, the minimum bet is $" + minBet);
		System.out.println("Your Info:  $" + money + "  Wins: " + gamesWon + "/" + totalGames + "\n");
		System.out.println("You are dealed a(n) " + cardDisplayName[index + 1] + " & a(n) " + cardDisplayName[index + 2]);
		System.out.println("Your total is: " + (cardValue[index + 1] + cardValue[index + 2]));
		index += 2;

		System.out.println("The dealer is showing a(n) " + cardDisplayName[index + 1] + "\n");
		index += 1;

		do {
			try {
				REDO = false;
				System.out.println("What would you like to do:");
				System.out.println("[1]-Hit");
				System.out.println("[2]-Stay");
				selection = keyboardInput.nextInt();
			} catch (Exception e) {
				System.out.println("That is not a vaild selection");
				REDO = true;
			}

			if (!REDO) {
				switch (selection) {
				case 1:
					System.out.println("You are dealed a(n) " + cardDisplayName[index + 1] + " & a(n) " + cardDisplayName[index + 2]);
					System.out.println("Your total is: " + (cardValue[index + 1] + cardValue[index + 2]));
					index++;
					break;
				case 2:

					break;
				default:
					
					break;
				}
			}

		} while (REDO);

	}

	private void quit() {
		System.out.println("\fSaving...");
		// SAVING CODE HERE

		System.out.println("Saving Complete");
		System.out.println("Thank you for playing!!!");
		System.exit(0);
	}

}

// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
class InputHandler implements KeyListener, FocusListener {

	public boolean[] key = new boolean[68836];

	@Override
	public void focusGained(FocusEvent e) {

	}

	@Override
	public void focusLost(FocusEvent e) {
		for (int i = 0; i < key.length; i++) {
			key[i] = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
	}
}
