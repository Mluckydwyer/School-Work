package Blackjack;

import java.util.Random;
import java.util.Scanner;

public class BlackjackGame {

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

	public int[] cardValue;
	public String[] suits;
	public String[] cardSuit;
	public String[] cardDisplayName;

	public static void main(String[] args) {

		keyboardInput = new Scanner(System.in);
		randomValue = new Random();
		game = new BlackjackGame();

		int[] cardValue = new int[game.cardNum + 1];
		String[] suits = new String[5];
		String[] cardSuit = new String[game.cardNum + 1];
		String[] cardDisplayName = new String[game.cardNum + 1];

		game.suits[1] = "Clubs";
		suits[2] = "Hearts";
		suits[3] = "Spades";
		suits[4] = "Diamonds";

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
						}
						else {
							OPEN = true;
						}
					}
				} 
				else {
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

		boolean BET = true;
		do {

			try {
				System.out.println("\fWelcome to Blackjack, What would you like to set the minimum bet at?");
				minBet = keyboardInput.nextInt();
				break;
			} catch (Exception e) {
				System.out.println("That is not a vaild minimum bet");
			}
		} while (BET);

		System.out.println("\fWelcome to the Blackjack Table. Here, the minimum bet is $" + minBet);
		System.out.println("Your Info:  $" + money + "  Wins: " + gamesWon + "/" + totalGames + "\n");
		System.out.println("You are dealed a(n) " + cardDisplayName[index + 1] + " & a(n) " + cardDisplayName[index + 2]);
		System.out.println("Your total is: " + (cardValue[index + 1] + cardValue[index + 2]));
		index += 2;

		System.out.println("The dealer is showing a(n) " + cardDisplayName[index + 1] + "\n");
		index += 1;

		// do{
		System.out.println("What would you like to do:");

		// } while ();
	}
}
