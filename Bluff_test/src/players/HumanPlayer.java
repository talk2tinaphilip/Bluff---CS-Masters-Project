package players;
import java.util.Arrays;
import java.util.Scanner;
import cardload.BluffVerification;
import cardload.Card;
import cardload.CardManagement;

/**
 * @author Tina
 *
 */
public class HumanPlayer extends Players{
	//BluffVerification p = new BluffVerification();
	//Player r = new Player();
	Scanner in = new Scanner(System.in);
	//public Player pr = new Player();

	int[] cardsOfPlayer; 
	int[] actualCardsToBePlayed;

	private int current_card_to_play;
	public String[] cardToPlay = {
			"ACEs","TWOs","THREEs","FOURs","FIVEs","SIXs","SEVENs","EIGHTs","NINEs","TENs","JACKs","QUEENs","KINGs"
	};
	private int[] discardPile;
	public int getHumanPlayerCount() {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of human players: ");
		int humanPlayerCount = in.nextInt();
		return humanPlayerCount;
	}

	public void playGame(int hplayers, int cplayers, int[] discardPile, CardManagement cm){
		int i = 0;
		int player;
		boolean finished = false;
		boolean bluff = false;
		int players = hplayers + cplayers;
		BluffVerification bf = new BluffVerification();	
		Card c = new Card();
		int counter = 0;

		int playerNumberInArray = currentPlayer - 1;
		//System.out.println( "BluffVerification method " +pr.getPlayersList()) ;
		//System.out.println( "\ncurrentplayer Player list " +pr.getPlayersList(). ;
		for (int ii : cm.getPlayersList().get(playerNumberInArray)){
			if(counter % 2 == 0) {
				System.out.println();
			}
			if(ii != 100){
				String s1 = String.format("%1$-20s", c.getDeck()[ii]);
				System.out.print(counter+" - "+s1);
				counter++;
			}
		}		
		Integer[] playerCardsArray = cm.getPlayersList().get(playerNumberInArray);
		//System.out.println(Arrays.toString(r.getPlayersList().get(currentPlayer)));
		//nb.getCards(currentPlayer);
		discardPile = getCardsToDeal(players, playerNumberInArray, playerCardsArray, discardPile,cm);
		int challenger = 1;
		for(int k = 1; k < players; k++){
			if(challenger == playerNumberInArray) {
				//if(player == 4) challenger = 0;
				if(playerNumberInArray == players) challenger = 0;
				else challenger = challenger +1;
			}
			bluff = bf.callBluff(challenger);
			if(bluff){
				actualCardsToBePlayed = c.getActualCardsToBePlayed(current_card_to_play);
				boolean verdict = bf.bluffVerifier(cardsOfPlayer,actualCardsToBePlayed);
				int loser;
				if(verdict){
					loser = playerNumberInArray;
					System.out.println("Caught! Player "+currentPlayer+ " played wrong cards. The pile is now yours!");
				}else{
					loser = challenger-1;
					System.out.println("Player "+currentPlayer+ " played correct cards. The pile goes to challenger.");
				}
				discardPile = cm.addDiscardPileToPlayerCards(loser, cm.getPlayersList().get(playerNumberInArray), discardPile);

				break;

			} else {
				challenger = challenger +1;
			}
		}
		System.out.println("---------Current State------------------------------");
		for(int count = 0; count < players; count++)	{
			Integer[] playerCards = cm.getPlayersList().get(count);
			System.out.println("playerCards "+Arrays.toString(playerCards));
		}
		System.out.println("------------------------------------------------");	
	}

	private int[] getCardsToDeal(int players, int player, Integer[] playerCardsArray, int[] discardPile, CardManagement cm) {
		// TODO Auto-generated method stub
		this.discardPile = discardPile;
		BluffVerification bf = new BluffVerification();
		System.out.println();
		System.out.println("Enter the no. of cards: "); 
		int num = in.nextInt();		
		System.out.println("Enter card numbers in CSV format: "); //eg: 3,1,2
		String str = in.nextLine();
		str = in.nextLine();
		int positions[] = new int[num];
		String[] temp = str.split(",");
		for(int i = 0; i < temp.length; i++){
			positions[i] = Integer.parseInt(temp[i]);
		}
		//cardsOfPlayer has the cards the player just played. Serve as a temp storage.
		cardsOfPlayer = bf.getCardsOfPlayer(positions, playerCardsArray, discardPile);
		discardPile  = cm.removeCards(players, player, positions, discardPile,cm);
		System.out.println("---------------------------------------------");	
		System.out.println("discardPile: ");
		for(int i = 0; i < discardPile.length; i++){
			System.out.print(discardPile[i]+ " , ");
		}
		System.out.println("---------------------------------------------");
		return discardPile;
	}
}