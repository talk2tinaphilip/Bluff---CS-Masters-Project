package players;

import cardload.CardManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Tina
 *
 */
public class ComputerPlayer extends Players{
    ArrayList<ComputerPlayer> computerPlayerType = new ArrayList<ComputerPlayer>();
	public int getComputerPlayerCount() {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of computer players: ");
		int computerPlayerCount = in.nextInt();
		return computerPlayerCount;
	}

	public void playGame(int hplayers, int cplayers, int[] discardPile, CardManagement cm) throws IOException {
        int playerNumberInArray = currentPlayer - 1;
        Play.callPlay(cplayers, currentPlayer, discardPile, cm);
    }

	/**
	 * @return
	 */
	public int getTrials() {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of trials to play the game: ");
		int trials = in.nextInt();
		return trials;
	}

}
