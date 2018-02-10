package cardload;

import java.util.Scanner;
public class BluffVerification {
	//	Scanner in = new Scanner(System.in);

	/**
	 * @param challenger
	 * @return 
	 */
	public boolean callBluff(int challenger) {
		Scanner in = new Scanner(System.in);
		boolean bluff = false;
		System.out.println("\nPlayer "+challenger+": Do you call Bluff? (Y/N)");
		String str = in.nextLine();
		if (str.equals("Y") || str.equals("y") || str.equals("yes")) {
			bluff = true; 
		} else if(str.equals("N") || str.equals("n") || str.equals("no")){
			bluff = false;
		}
		return bluff;
	}


	/**
	 * 
	 */
	public boolean bluffVerifier(int[]cardsOfPlayer, int[]actualCardsToBePlayed ) {
		// TODO Auto-generated method stub
		boolean bluff = false;
		System.out.println();
	//	System.out.println("Cards Played: ");
	//	for(int j = 0; j < cardsOfPlayer.length; j++){
	//		System.out.print(cardsOfPlayer[j] + ", ");
	//	}
		System.out.println();
		//System.out.println("Cards to be Played: ");
		//for(int j = 0; j < actualCardsToBePlayed.length; j++){
		//	System.out.print(actualCardsToBePlayed[j] + ", ");
		//}
		System.out.println();
		if(cardsOfPlayer.length > actualCardsToBePlayed.length)
			bluff = true;
		else{
			for(int j = 0; j < cardsOfPlayer.length; j++){
				boolean contains = contains(actualCardsToBePlayed, cardsOfPlayer[j]);
				//System.out.println("contains: "+ contains);
				if (!contains) {
					bluff = true;
					break;
				}

			}
		}
		return bluff;
	}

	public int[] getCardsOfPlayer(int[] positions, Integer[] playerCardsArray, int[] discardPile) {
		int[] cardsOfPlayer = new int[positions.length];
		for(int j = 0; j < positions.length; j++){
			int remove = positions[j];
			cardsOfPlayer[j] = playerCardsArray[remove];
		}
		return cardsOfPlayer;
	}
	public boolean contains(int[] array, int key) {
		for (int i : array) {
			if (i == key) {
				return true;
			}
		}
		return false;
	}
	public int getNumberOfRemainingCards(int[] cardsArray, int start, int end){
		int count = 0;
		for(int i : cardsArray ){
			if (i != -1) count++;
		}
		return count;
	}
}