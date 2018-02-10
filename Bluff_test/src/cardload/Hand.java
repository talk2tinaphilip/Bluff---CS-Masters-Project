package cardload;
/**
 * @author Tina
 * Created on: 2/16/17
 * File name: Hand.java
 */

public class Hand {
	int len;
	boolean flag = false;
	int count = 0;
	int position_to_insert = 0;

	// function to replace the removed cards with -1 in the cardsArray
	public Object[] removeCards(int[] positions, Integer[] playerCardsArray, int[] discardPile){	
		len = count;
		System.out.println("count: "+ count);
		System.out.print("discardPile: ");
		for(int j = 0; j < positions.length; j++){
			//remove cards at positions
			int remove = positions[j];
			discardPile[len] = playerCardsArray[remove];
			count++;
			System.out.print(discardPile[len]+ ", ");
			playerCardsArray[remove] = -1;
			len++;
		}
		System.out.println();
		System.out.println("playerCardsArray: ");
		for(int j = 0; j < positions.length; j++){
			System.out.println(playerCardsArray[j]);
		}

		return new Object[]{playerCardsArray, discardPile};
	}

}