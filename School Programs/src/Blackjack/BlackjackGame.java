package Blackjack;

import java.util.Random;
import java.util.Scanner;

public class BlackjackGame {

	public static void main(String[] args) {

		Scanner keyboardInput = new Scanner(System.in);
		Random randomValue = new Random();

		int startMoney = 10000;
		int money = startMoney;
		int minBet = -999;
		int index = 0;
		int gamesWon = 0;
		int totalGames = 0;
		int cardNum = 52;

		int[] cardValue = new int[cardNum + 1];
		String[] suits = new String[5];
		String[] cardSuit = new String[cardNum + 1];
		String[] cardDisplayName = new String[cardNum + 1];

		suits[1] = "Clubs";
		suits[2] = "Hearts";
		suits[3] = "Spades";
		suits[4] = "Diamonds";

		// Shuffle and create the cards
		for (int i = 1; i <= 52; i++) {
			boolean OPEN = false;

			do {
				cardValue[i] = (randomValue.nextInt(10) + 1);
				cardSuit[i] = suits[randomValue.nextInt(4) + 1];
				// System.out.println("Trying Card " + i + ": " + cardValue[i] +
				// "\t" + cardSuit[i]);
				if (i > 1) {
					for (int x = 1; x < i; x++) {
						if (cardValue[i] == cardValue[x] && cardSuit[i] == cardSuit[x]) {
							OPEN = false;
						} else {
							// System.out.println("Card " + i + " is: " +
							// cardValue[i] + " of " + cardSuit[i]);
							OPEN = true;
						}
					}
				} else {
					OPEN = true;
				}

			} while (!OPEN);

			switch (cardValue[i]) {
			case 1:
				cardDisplayName[i] = "Ace of " + cardSuit[i];
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
		
		do{
		System.out.println("What would you like to do:");
		
		} while ();
	}
}
