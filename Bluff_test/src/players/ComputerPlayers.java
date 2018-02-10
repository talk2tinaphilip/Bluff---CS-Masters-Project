package players;

import cardload.CardManagement;

import java.util.ArrayList;

public abstract class ComputerPlayers {

    int currentPlayer;
    static ArrayList<ComputerPlayers> computerPlayerType = new ArrayList<ComputerPlayers>();
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public void setCurrentComputerPlayer(int currentPlayer) {
            this.currentPlayer = currentPlayer;
        }
    public static ArrayList getcomputerPlayerType(){ return computerPlayerType; }
    public void setcomputerPlayerType(ArrayList computerPlayerType){this.computerPlayerType = computerPlayerType; }

    public abstract int[] play(int cplayers, int currentPlayer, int[] discardPile, CardManagement cm);

    //public abstract static void callBluff(ArrayList<ComputerPlayers> computerPlayerType, int currentPlayer, int[] discardPile, CardManagement cm);

}

