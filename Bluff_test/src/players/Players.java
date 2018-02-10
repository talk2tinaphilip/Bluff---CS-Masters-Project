package players;
import cardload.CardManagement;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Tina
 *
 */
public abstract class Players {

	int currentPlayer;
	public abstract void playGame(int hplayers, int cplayers, int[] discardPile, CardManagement cm) throws IOException;
	
	/**
	 * @return the currentPlayer
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	/**
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	ArrayList<ComputerPlayer> computerPlayerType = new ArrayList<ComputerPlayer>();

	public void setCurrentComputerPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public ArrayList getcomputerPlayerType(){ return computerPlayerType; }
	public void setcomputerPlayerType(ArrayList computerPlayerType){this.computerPlayerType = computerPlayerType; }

}
	