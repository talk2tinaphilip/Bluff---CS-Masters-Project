package cardload;
import java.util.Arrays;

/**
 * @author Tina
 * Created on: 2/16/17
 * File name: Card.java
 * This class contains functions to 
 * specify the value and suit of the card that is being created.
 */
public class Card {
	private static int[] cards = new int[52];
	private String[] Deck = {
			"AceofSpades","TwoofSpades","ThreeofSpades","FourofSpades","FiveofSpades",
			"SixofSpades","SevenofSpades","EightofSpades","NineofSpades","TenofSpades",
			"JackofSpades","QueenofSpades","KingofSpades",
			"AceofClubs","TwoofClubs","ThreeofClubs","FourofClubs","FiveofClubs",
			"SixofClubs","SevenofClubs","EightofClubs","NineofClubs","TenofClubs",
			"JackofClubs","QueenofClubs","KingofClubs",
			"AceofHearts","TwoofHearts","ThreeofHearts","FourofHearts","FiveofHearts",
			"SixofHearts","SevenofHearts","EightofHearts","NineofHearts","TenofHearts",
			"JackofHearts","QueenofHearts","KingofHearts",
			"AceofDiamonds","TwoofDiamonds","ThreeofDiamonds","FourofDiamonds",
			"FiveofDiamonds","SixofDiamonds","SevenofDiamonds","EightofDiamonds",
			"NineofDiamonds","TenofDiamonds","JackofDiamonds","QueenofDiamonds",
			"KingofDiamonds"
	};

	int current_card_to_play;
	int cardNumber;
	int[] playerCards;

	//shuffle() to shuffle the deck into a random order.
	public int[] shuffle(){
		for(int i = 0; i< Deck.length; i++){
			cards[i] = i;
		}
		for(int i = 51; i >= 0; i--){			
			int rand  = (int)(Math.floor(Math.random() * (i+1)));			
			int temp  = cards[i];
			cards[i]  = cards[rand];
			cards[rand] = temp;
		}
		return cards;
	}


	public int[] displayCards(int start, int end){
		for(int i = start; i< end; i++){
			//if(cards[i] != -1){
			System.out.print(cards[i] + ", ");
			//}

		}
		System.out.println();
		playerCards = Arrays.copyOfRange(cards, start, end);
		return playerCards;
	}
	/**
	 * @param 
	 * @return
	 */
	public int[] getActualCardsToBePlayed(int current_card_to_play) {
		this.current_card_to_play = current_card_to_play;
		// TODO Auto-generated method stub
		int[] actualCardsToBePlayed = null;
		if(current_card_to_play == 0)//Aces
			actualCardsToBePlayed = new int[] {0,13,26,39};
		else if(current_card_to_play == 1)//Twos
			actualCardsToBePlayed = new int[] {1,14,27,40};
		else if(current_card_to_play == 2)//Threes
			actualCardsToBePlayed = new int[] {2,15,28,41};
		else if(current_card_to_play == 3)//Fours
			actualCardsToBePlayed = new int[] {3,16,29,42};
		else if(current_card_to_play == 4)//Fives
			actualCardsToBePlayed = new int[] {4,17,30,43};		
		else if(current_card_to_play == 5)//Sixes
			actualCardsToBePlayed = new int[] {5,18,31,44};
		else if(current_card_to_play == 6)//Sevens
			actualCardsToBePlayed = new int[] {6,19,32,45};
		else if(current_card_to_play == 7)//Eights
			actualCardsToBePlayed = new int[] {7,20,33,46};
		else if(current_card_to_play == 8)//Nines
			actualCardsToBePlayed = new int[] {8,21,34,47};
		else if(current_card_to_play == 9)//Tens
			actualCardsToBePlayed = new int[] {9,22,35,48};
		else if(current_card_to_play == 10)//Jacks
			actualCardsToBePlayed = new int[] {10,23,36,49};
		else if(current_card_to_play == 11)//Queens
			actualCardsToBePlayed = new int[] {11,24,37,50};
		else if(current_card_to_play == 12)//Kings
			actualCardsToBePlayed = new int[] {12,25,38,51};
		return actualCardsToBePlayed;
	}
	public int getCardsType(int cardNumber) {
		this.cardNumber = cardNumber;
		int cardType = 0;
		// TODO Auto-generated method stub
		if(cardNumber == 0 || cardNumber == 13 || cardNumber == 26 || cardNumber == 39) cardType = 0;//Aces
		else if(cardNumber == 1 || cardNumber == 14 || cardNumber == 27 || cardNumber == 40) cardType = 1;//Twos
		else if(cardNumber == 2 || cardNumber == 15 || cardNumber == 28 || cardNumber == 41) cardType = 2;//Threes
		else if(cardNumber == 3 || cardNumber == 16 || cardNumber == 29 || cardNumber == 42) cardType = 3;//Fours
		else if(cardNumber == 4 || cardNumber == 17 || cardNumber == 30 || cardNumber == 43) cardType = 4;//Fives
		else if(cardNumber == 5 || cardNumber == 18 || cardNumber == 31 || cardNumber == 44) cardType = 5;//Sixes
		else if(cardNumber == 6 || cardNumber == 19 || cardNumber == 32 || cardNumber == 45) cardType = 6;//Sevens
		else if(cardNumber == 7 || cardNumber == 20 || cardNumber == 33 || cardNumber == 46) cardType = 7;//Eights
		else if(cardNumber == 8 || cardNumber == 21 || cardNumber == 34 || cardNumber == 47) cardType = 8;//Nines
		else if(cardNumber == 9 || cardNumber == 22 || cardNumber == 35 || cardNumber == 48) cardType = 9;//Tens
		else if(cardNumber == 10 || cardNumber == 23 || cardNumber == 36 || cardNumber == 49) cardType = 10;//Jacks
		else if(cardNumber == 11 || cardNumber == 24 || cardNumber == 37 || cardNumber == 50) cardType = 11;//Queens
		else if(cardNumber == 12 || cardNumber == 25 || cardNumber == 38 || cardNumber == 51) cardType = 12;//Kings
		return cardType;
	}
	public int[] returnCardType(int players, int current_card_to_play){
		int[] cardType = null;
		if(players == 2) {
			if (current_card_to_play == 0)//Aces
				cardType = new int[]{11, 9, 7, 5, 3, 1, 12, 10, 8};
            else if(current_card_to_play == 1)//Twos
                cardType = new int[]{12, 10, 8, 6, 4, 2, 0, 11, 9};
            else if(current_card_to_play == 2)//Threes
                cardType = new int[]{0, 11, 9, 7, 5, 3, 1, 12, 10};
            else if(current_card_to_play == 3)//Fours
                cardType = new int[]{1, 12, 10, 8, 6, 4, 2, 0, 11};
            else if(current_card_to_play == 4)//Fives
                cardType = new int[]{2, 0, 11, 9, 7, 5, 3, 1, 12};
            else if(current_card_to_play == 5)//Sixes
                cardType = new int[]{3, 1, 12, 10, 8, 6, 4, 2, 0};
            else if(current_card_to_play == 6)//Sevens
                cardType = new int[]{4, 2, 0, 11, 9, 7, 5, 3, 1};
            else if(current_card_to_play == 7)//Eights
                cardType = new int[]{5, 3, 1, 12, 10, 8, 6, 4, 2};
            else if(current_card_to_play == 8)//Nines
                cardType = new int[]{6, 4, 2, 0, 11, 9, 7, 5, 3};
            else if(current_card_to_play == 9)//Tens
                cardType = new int[]{7, 5, 3, 1, 12, 10, 8, 6, 4};
            else if(current_card_to_play == 10)//Jacks
                cardType = new int[]{8, 6, 4, 2, 0, 11, 9, 7, 5};
            else if(current_card_to_play == 11)//Queens
                cardType = new int[]{9, 7, 5, 3, 1, 12, 10, 8, 6};
            else if(current_card_to_play == 12)//Kings
                cardType = new int[]{10, 8, 6, 4, 2, 0, 11, 9, 7};
		}
		if(players == 3) {
			if (current_card_to_play == 0)//Aces
				cardType = new int[]{10, 7, 4, 1, 11, 8, 5, 2, 12};
			else if(current_card_to_play == 1)//Twos
				cardType = new int[]{11, 8, 5, 2, 12, 9, 6, 3, 0};
			else if(current_card_to_play == 2)//Threes
				cardType = new int[]{12, 9, 6, 3, 0, 10, 7, 4, 1};
			else if(current_card_to_play == 3)//Fours
				cardType = new int[]{0, 10, 7, 4, 1, 11, 8, 5, 2};
			else if(current_card_to_play == 4)//Fives
				cardType = new int[]{1, 11, 8, 5, 2, 12, 9, 6, 3};
			else if(current_card_to_play == 5)//Sixes
				cardType = new int[]{2, 12, 9, 6, 3, 0, 10, 7, 4};
			else if(current_card_to_play == 6)//Sevens
				cardType = new int[]{3, 0, 10, 7, 4, 1, 11, 8, 5};
			else if(current_card_to_play == 7)//Eights
				cardType = new int[]{4, 1, 11, 8, 5, 2, 12, 9, 6};
			else if(current_card_to_play == 8)//Nines
				cardType = new int[]{5, 2, 12, 9, 6, 3, 0, 10, 7};
			else if(current_card_to_play == 9)//Tens
				cardType = new int[]{6, 3, 0, 10, 7, 4, 1, 11, 8};
			else if(current_card_to_play == 10)//Jacks
				cardType = new int[]{7, 4, 1, 11, 8, 5, 2, 12, 9};
			else if(current_card_to_play == 11)//Queens
				cardType = new int[]{8, 5, 2, 12, 9, 6, 3, 0, 10};
			else if(current_card_to_play == 12)//Kings
				cardType = new int[]{9, 6, 3, 0, 10, 7, 4, 1, 11};

		}
		if(players == 4) {
			if (current_card_to_play == 0)//Aces
				cardType = new int[]{9, 5, 1, 10, 6, 2, 11, 7, 3};
			else if(current_card_to_play == 1)//Twos
				cardType = new int[]{10, 6, 2, 11, 7, 3, 12, 8, 4};
			else if(current_card_to_play == 2)//Threes
				cardType = new int[]{11, 7, 3, 12, 8, 4, 0, 9, 5};
			else if(current_card_to_play == 3)//Fours
				cardType = new int[]{12, 8, 4, 0, 9, 5, 1, 10, 6};
			else if(current_card_to_play == 4)//Fives
				cardType = new int[]{0, 9, 5, 1, 10, 6, 2, 11, 7};
			else if(current_card_to_play == 5)//Sixes
				cardType = new int[]{1, 10, 6, 2, 11, 7, 3, 12, 8};
			else if(current_card_to_play == 6)//Sevens
				cardType = new int[]{2, 11, 7, 3, 12, 8, 4, 0, 9};
			else if(current_card_to_play == 7)//Eights
				cardType = new int[]{3, 12, 8, 4, 0, 9, 5, 1, 10};
			else if(current_card_to_play == 8)//Nines
				cardType = new int[]{4, 0, 9, 5, 1, 10, 6, 2, 11};
			else if(current_card_to_play == 9)//Tens
				cardType = new int[]{5, 1, 10, 6, 2, 11, 7, 3, 12};
			else if(current_card_to_play == 10)//Jacks
				cardType = new int[]{6, 2, 11, 7, 3, 12, 8, 4, 0};
			else if(current_card_to_play == 11)//Queens
				cardType = new int[]{7, 3, 12, 8, 4, 0, 9, 5, 1};
			else if(current_card_to_play == 12)//Kings
                cardType = new int[]{8, 4, 0, 9, 5, 1, 10, 6, 2};

		}
        if(players == 5) {
            if (current_card_to_play == 0)//Aces
                cardType = new int[]{8, 3, 11, 6, 1, 9, 4, 12, 7};
            else if(current_card_to_play == 1)//Twos
                cardType = new int[]{9, 4, 12, 7, 2, 10, 5, 0, 8};
            else if(current_card_to_play == 2)//Threes
                cardType = new int[]{10, 5, 0, 8, 3, 11, 6, 1, 9};
            else if(current_card_to_play == 3)//Fours
                cardType = new int[]{11, 6, 1, 9, 4, 12, 7, 2, 10};
            else if(current_card_to_play == 4)//Fives
                cardType = new int[]{12, 7, 2, 10, 5, 0, 8, 3, 11};
            else if(current_card_to_play == 5)//Sixes
                cardType = new int[]{0, 8, 3, 11, 6, 1, 9, 4, 12};
            else if(current_card_to_play == 6)//Sevens
                cardType = new int[]{1, 9, 4, 12, 7, 2, 10, 5, 0};
            else if(current_card_to_play == 7)//Eights
                cardType = new int[]{2, 10, 5, 0, 8, 3, 11, 6, 1};
            else if(current_card_to_play == 8)//Nines
                cardType = new int[]{3, 11, 6, 1, 9, 4, 12, 7, 2};
            else if(current_card_to_play == 9)//Tens
                cardType = new int[]{4, 12, 7, 2, 10, 5, 0, 8, 3};
            else if(current_card_to_play == 10)//Jacks
                cardType = new int[]{5, 0, 8, 3, 11, 6, 1, 9, 4};
            else if(current_card_to_play == 11)//Queens
                cardType = new int[]{6, 1, 9, 4, 12, 7, 2, 10, 5};
            else if(current_card_to_play == 12)//Kings
                cardType = new int[]{7, 2, 10, 5, 0, 8, 3, 11, 6};

        }
        if(players == 6) {
            if (current_card_to_play == 0)//Aces
                cardType = new int[]{7, 1, 8, 2, 9, 3, 10, 4, 11};
            else if(current_card_to_play == 1)//Twos
                cardType = new int[]{8, 2, 9, 3, 10, 4, 11, 5, 12};
            else if(current_card_to_play == 2)//Threes
                cardType = new int[]{9, 3, 10, 4, 11, 5, 12, 6, 0};
            else if(current_card_to_play == 3)//Fours
                cardType = new int[]{10, 4, 11, 5, 12, 6, 0, 7, 1};
            else if(current_card_to_play == 4)//Fives
                cardType = new int[]{11, 5, 12, 6, 0, 7, 1, 8, 2};
            else if(current_card_to_play == 5)//Sixes
                cardType = new int[]{12, 6, 0, 7, 1, 8, 2, 9, 3};
            else if(current_card_to_play == 6)//Sevens
                cardType = new int[]{0, 7, 1, 8, 2, 9, 3, 10, 4};
            else if(current_card_to_play == 7)//Eights
                cardType = new int[]{1, 8, 2, 9, 3, 10, 4, 11, 5};
            else if(current_card_to_play == 8)//Nines
                cardType = new int[]{2, 9, 3, 10, 4, 11, 5, 12, 6};
            else if(current_card_to_play == 9)//Tens
                cardType = new int[]{3, 10, 4, 11, 5, 12, 6, 0, 7};
            else if(current_card_to_play == 10)//Jacks
                cardType = new int[]{4, 11, 5, 12, 6, 0, 7, 1, 8};
            else if(current_card_to_play == 11)//Queens
                cardType = new int[]{5, 12, 6, 0, 7, 1, 8, 2, 9};
            else if(current_card_to_play == 12)//Kings
                cardType = new int[]{6, 0, 7, 1, 8, 2, 9, 3, 10};

        }
		return cardType;
	}

	/**
	 * @return the deck
	 */
	public String[] getDeck() {
		return Deck;
	}

}
