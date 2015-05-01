package Blackjack;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;

public class BlackjackGame implements KeyListener, FocusListener {

	public static Scanner keyboardInput;
	public static Random randomValue;
	public static BlackjackGame game;

	public int startMoney = 10000;
	public int money = startMoney;
	public int minBet = -999;
	public int index = 0;
	public int gamesWon = 0;
	public int totalGames = 0;
	public int cardNum = 52;
	public int selection;

	public boolean textInput;

	public int[] cardValue = new int[game.cardNum + 1];
	public String[] suits = new String[5];
	public String[] cardSuit = new String[game.cardNum + 1];
	public String[] cardDisplayName = new String[game.cardNum + 1];

	public static void main(String[] args) {

		keyboardInput = new Scanner(System.in);
		randomValue = new Random();
		game = new BlackjackGame();
		
		game.suits[1] = "Clubs";
		game.suits[2] = "Hearts";
		game.suits[3] = "Spades";
		game.suits[4] = "Diamonds";
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

		do {
			REDO = true;
			try {
				System.out.println("Welcome to Blackjack, please select an option from the menu below");
				System.out.println("[1]-Play Blackjack");
				System.out.println("[2]-Settings");
				System.out.println("[3]-Player Stats");
				System.out.println("[4]-Quit");

				selection = keyboardInput.nextInt();
				REDO = false;
				break;
			} catch (Exception e) {
				System.out.println("That is not a vaild selection");
			}
		} while (REDO);

	}

	public void play() {
		System.out.println("\fWelcome to the Blackjack Table. Here, the minimum bet is $" + minBet);
		System.out.println("Your Info:  $" + money + "  Wins: " + gamesWon + "/" + totalGames + "\n");
		System.out.println("You are dealed a(n) " + cardDisplayName[index + 1] + " & a(n) " + cardDisplayName[index + 2]);
		System.out.println("Your total is: " + (cardValue[index + 1] + cardValue[index + 2]));
		index += 2;

		System.out.println("The dealer is showing a(n) " + cardDisplayName[index + 1] + "\n");
		index += 1;

		System.out.println("What would you like to do:");
		System.out.println("[1]-Hit");
		System.out.println("[2]-");
		System.out.println("[3]-");
	}
}
	
	
	
class InputHandler implements KeyListener, FocusListener{	
	
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
