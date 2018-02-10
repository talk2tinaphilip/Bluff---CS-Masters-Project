package players;

import cardload.CardManagement;
import java.io.IOException;
import java.util.ArrayList;

public class Play {
    static ArrayList<ComputerPlayers> computerPlayerType = new ArrayList<ComputerPlayers>();

    public static void callPlay(int cplayers, int currentPlayer, int[] discardPile, CardManagement cm) throws IOException{
        computerPlayerType = ComputerPlayers.getcomputerPlayerType();
        for(ComputerPlayers p: computerPlayerType){
            p.play(cplayers, currentPlayer, discardPile, cm);
        }
    }

}
