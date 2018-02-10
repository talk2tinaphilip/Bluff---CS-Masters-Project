package ai;

import cardload.BluffVerification;
import cardload.Card;
import cardload.CardManagement;
import players.ComputerPlayers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tina
 *
 */
//This AI player never tries to cheat. If he doesn't have the card to play, he plays the first available card.
public class NoBluffAI extends ComputerPlayers {

	BluffVerification p = new BluffVerification();
	Card c = new Card();
	Scanner in = new Scanner(System.in);
	private int[] discardPile;
	int[] cardsOfPlayer;
    int cardsOfCurrentPlayer[];
	public int[] play(int cplayers, int currentPlayer, int[] discardPile, CardManagement cm){
		int i = 0;
		int player;
		boolean finished = false;
		boolean bluff = false;

        player = currentPlayer + 1;
		int counter = 0;
		for (int ii : cm.getPlayersList().get(currentPlayer)){
			if(counter % 2 == 0) {
				System.out.println();
			}
			if(ii != 100){
				String s1 = String.format("%1$-20s", c.getDeck()[ii]);
				System.out.print(counter+" - "+s1);
				counter++;
			}
		}		
		Integer[] playerCardsArray = cm.getPlayersList().get(currentPlayer);
		discardPile = getCards(cplayers, currentPlayer, playerCardsArray, cm.getCurrentCard(), discardPile, cm);
        int[] currentCardsPlayed = getCardsOfCurrentPlayer();
        System.out.println();

        return currentCardsPlayed;
	}
	public static boolean callBluff(ArrayList<ComputerPlayers> computerPlayerType, int currentPlayer, int challenger,
                                 int[] cardsOfPlayer,  int[] discardPile, CardManagement cm){

        boolean bluff = false;
        if (bluff) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
        return bluff;
	}
	/**
	 * @param playerCardsArray 
	 * @return
	 */
	private int[] getCards(int cplayers, int currentPlayer, Integer[] playerCardsArray, int current_card_to_play, int[] discardPile, CardManagement cm) {
		// TODO Auto-generated method stub
		this.discardPile = discardPile;		
		int[] currentCardtoPlay = c.getActualCardsToBePlayed(current_card_to_play);
		int verdict[] = findCardsToPlay(playerCardsArray, currentCardtoPlay);
		int cardNumbers[] = new int[verdict.length];
		for (int g = 0; g < verdict.length; g++) {
			cardNumbers[g] = playerCardsArray[verdict[g]];
		}
		System.out.println();
       // System.out.println("Cards Played: ");
       // for(int j = 0; j < cardNumbers.length; j++) System.out.print(cardNumbers[j] + ", ");

		setCardsOfCurrentPlayer(cardNumbers);
		discardPile  = cm.removeCards(cplayers, currentPlayer, verdict, discardPile,cm);
		return discardPile;
	}

	/**
	 * @param computerPlayerCardsArray
	 * @param currentCardtoPlay
	 * @return
	 */
	public static int[] findCardsToPlay(Integer[] computerPlayerCardsArray, int[] currentCardtoPlay) {
		// TODO Auto-generated method stub
		boolean contains = false;
		int size;
        List<Integer> cardsToPlay = new ArrayList<>();
		for(int j = 0; j < computerPlayerCardsArray.length; j++){			
			for (int i : currentCardtoPlay) {
				if (i == computerPlayerCardsArray[j]) {
                    cardsToPlay.add(j);
					contains = true;
				}
			}
		}
		if(cardsToPlay.size() == 0)  size = 1;
        else size = cardsToPlay.size();
        int retVal[] = new int[size];

		if(contains) {
		    for(int i = 0; i < cardsToPlay.size(); i++) {
                retVal[i] = cardsToPlay.get(i);
            }
        }
		else retVal[0] = 0;

		return retVal;
	}
    public void setCardsOfCurrentPlayer(int verdict[]) {
       cardsOfCurrentPlayer = verdict.clone();
    }

    public int[] getCardsOfCurrentPlayer() {
        return cardsOfCurrentPlayer;
    }
}