package cardload;

import ai.NoBluffAI;
import ai.QLAI;
import ai.SmartAI;
import ai.SmartBluffAI;
import input.Move;
import players.ComputerPlayers;
import players.Players;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardManagement implements Serializable {
    int len;
    int count = 0;
    int turn = 0;
    static int Logic =0;
    // ArrayList<ComputerPlayers> computerPlayerType = new ArrayList<ComputerPlayers>();
    List<Integer[]> playersList = new ArrayList<Integer[]>();
    List<Integer[]> tempPlayersList = new ArrayList<Integer[]>();
    List<Integer> winnersList = new ArrayList<Integer>();

    public List<Integer> getwinnersList() {
        return winnersList;
    }

    public void setwinnersList(List<Integer> winnersList) {
        this.winnersList = winnersList;
    }

    public String[] cardToPlay = {
            "ACEs", "TWOs", "THREEs", "FOURs", "FIVEs", "SIXs", "SEVENs", "EIGHTs", "NINEs", "TENs", "JACKs", "QUEENs", "KINGs"
    };
    static int[][] caughtCount = new int[8][8];

    public List<Integer[]> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<Integer[]> playersList, int currentPlayer) {
        this.playersList = playersList;
        this.currentPlayer = currentPlayer;
    }

    public void setPlayersList(List<Integer[]> playersList) {
        this.playersList = playersList;
    }

    int currentPlayer;
    int current_card_to_play = 0;

    public int getCurrentCard() {
        return current_card_to_play;
    }

    public void setCurrentCard(int currentCard) {
        this.current_card_to_play = currentCard;
    }

    public void initialise(int hplayers, int cplayers, List<Players> playerType,
                           ArrayList<ComputerPlayers> computerPlayerType, List<Integer> playerTypeNumber)
            throws IOException {

        int[] discardPile = new int[52];
        int players = hplayers + cplayers;
        Card c = new Card();
        Move m = new Move();
        boolean bluff = false;
        boolean bluffCaught = false;
        for(int i = 0; i< caughtCount.length; i++){
            for(int j = 0; j< caughtCount.length; j++){
                caughtCount[i][j] = 0;
            }
        }

        int[] cardsArray = c.shuffle();
        CardManagement cm = new CardManagement();
    //    System.out.println("Deck: ");
    //    for (int t = 0; t < cardsArray.length; t++) {
     //       System.out.print(cardsArray[t] + ", ");
     //   }
        playersList.clear();
        QLAI.setTurn(0);
        tempPlayersList.clear();
        current_card_to_play = 0;
        boolean endGame = false;
        String path = "C:\\Users\\Tina\\workspace\\Bluff_test\\src\\input\\";
        int bluffInt = 0;
        assignCards(players, hplayers, cplayers, cardsArray, cm);
        Arrays.fill(discardPile, -1);

        while (endGame == false) {
            //for(Players p: playerType){
            for (ComputerPlayers p : computerPlayerType) {
                current_card_to_play = (current_card_to_play + 1) % 13;
                if (current_card_to_play == 0) current_card_to_play = 13;
                System.out.println();
                cm.setCurrentCard(current_card_to_play - 1);
                System.out.println("---------------------------------------------------------------------------------- ");
                System.out.println("Current card to be played: " + cardToPlay[cm.getCurrentCard()]);
                System.out.println("Turn for Player " + p.getCurrentPlayer() + ": ");
                //p.playGame(hplayers, cplayers, discardPile,cm);
                //p.play(hplayers, cplayers, discardPile,cm);
                currentPlayer = p.getCurrentPlayer() - 1;
                int[] currentCardsPlayed = p.play(cplayers, currentPlayer, discardPile, cm);
                for (int i = 0; i < playerTypeNumber.size(); i++) {
                    if (i != currentPlayer) {
                        if (playerTypeNumber.get(i) == 1) {
                            int j = i + 1;
                            System.out.println("\nPlayer " + j + ": Do you call Bluff? (Y/N)");
                            bluff = NoBluffAI.callBluff(computerPlayerType, currentPlayer, i, currentCardsPlayed, discardPile, cm);
                        } else if (playerTypeNumber.get(i) == 2) {
                            int j = i + 1;
                            System.out.println("\nPlayer " + j + ": Do you call Bluff? (Y/N)");
                            bluff = SmartAI.callBluff(computerPlayerType, currentPlayer, i, currentCardsPlayed, discardPile, cm);

                        } else if (playerTypeNumber.get(i) == 3) {
                            int j = i + 1;
                            System.out.println("\nPlayer " + j + ": Do you call Bluff? (Y/N)");
                            bluff = QLAI.callBluff(computerPlayerType, currentPlayer, i, currentCardsPlayed, discardPile, cm);
                        } else if (playerTypeNumber.get(i) == 4) {
                            int j = i + 1;
                            System.out.println("\nPlayer " + j + ": Do you call Bluff? (Y/N)");
                            bluff = SmartBluffAI.callBluff(computerPlayerType, currentPlayer, i, currentCardsPlayed, discardPile, cm);
                        }
                    }
                    if (bluff) break;
                }
                // for(int i = 0; i < playerTypeNumber.size(); i++){
                //if( playerTypeNumber.get(i) == 3  && currentPlayer == playerTypeNumber.get(i)){
                if (playerTypeNumber.get(currentPlayer) == 3&& QLAI.getTurn() < 27) { //&& QLAI.getTurn() < 100
                    if (bluff) {
                        bluffCaught = QLAI.getBluff();
                        if (!bluffCaught) bluffInt = 1;
                        else bluffInt = 0;
                    } else bluffInt = 1;
                    int cardToPlay = cm.getCurrentCard();
                    int cardType = c.getCardsType(currentCardsPlayed[0]);
                    m.setStateActionMatrix(cardToPlay, cardType);
                    m.setRewardMatrix(cardToPlay, cardType, bluffInt);
                    Move move = null;
                    try {
                        FileInputStream fileIn = new FileInputStream(path + "Matrices.ser");
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        move = (Move) in.readObject();
                        in.close();
                        fileIn.close();
                    } catch (IOException u) {
                        u.printStackTrace();
                        return;
                    } catch (ClassNotFoundException d) {
                        System.out.println("Move class not found");
                        d.printStackTrace();
                        return;
                    }
                }
                // }
                p.setCurrentPlayer(currentPlayer + 1);
                int winner = cm.findWinner(cplayers, cm);
                if (winner != 0) {
                    System.out.println("Player " + winner + " is the winner!!!");
                    winnersList.add(winner);
                    setwinnersList(winnersList);
                    endGame = true;
                    m.Serialize(m);
                    int[][] rewardMat = m.getRewardMatrix();
                    int[][] caughtMat = getCaughtCount();
                    turn++;
                    FileWriter fw1 = null;
                    FileWriter fw2 = null;
                    File file1 = new File(path + turn + ".txt");
                    File file2 = new File(path + turn + "CaughtMatrix.txt");
                    if (!file1.exists()) file1.createNewFile();
                    if (!file2.exists()) file2.createNewFile();
                    fw1 = new FileWriter(file1.getAbsoluteFile());
                    fw2 = new FileWriter(file2.getAbsoluteFile());
                    StringBuilder builder1 = new StringBuilder();
                    StringBuilder builder2 = new StringBuilder();
                    for (int i = 0; i < rewardMat.length; i++)//for each row
                    {
                        for (int j = 0; j < rewardMat.length; j++)//for each column
                        {
                            builder1.append(rewardMat[i][j] + "");//append to the output string
                            if (j < rewardMat.length - 1)//if this is not the last row element
                                builder1.append(",");//then add comma (if you don't like commas you can use spaces)
                        }
                        builder1.append("\n");//append new line at the end of the row
                    }
                    for (int i = 0; i < caughtMat.length; i++)//for each row
                    {
                        for (int j = 0; j < caughtMat.length; j++)//for each column
                        {
                            builder2.append(caughtMat[i][j] + "");//append to the output string
                            if (j < caughtMat.length - 1)//if this is not the last row element
                                builder2.append(",");//then add comma (if you don't like commas you can use spaces)
                        }
                        builder2.append("\n");//append new line at the end of the row
                    }
                    BufferedWriter bw1 = new BufferedWriter(fw1);
                    BufferedWriter bw2 = new BufferedWriter(fw2);
                    bw1.write(builder1.toString());//save the string representation of the board
                    bw2.write(builder2.toString());//save the string representation of the board
                    bw1.close();
                    bw2.close();
                    break;
                }
            }
        }
    }

    public int[][] getCaughtCount() {
   //     System.out.println("Caught Count in get: ");
    //    for(int i = 0; i< caughtCount.length; i++){
    //        for(int j = 0; j< caughtCount.length; j++){
     //           System.out.print(caughtCount[i][j] + " ");
     //       }
      //      System.out.println();
     //   }
        return caughtCount;
    }

    public void updateCaughtCount(int player, int loser) {
        caughtCount[player][loser]+=1;
       // System.out.println("Caught Count in set: ");
       // for(int i = 0; i< caughtCount.length; i++){
      //      for(int j = 0; j< caughtCount.length; j++){
      //          System.out.print(caughtCount[i][j] + " ");
      //      }
      //      System.out.println();
      //  }
        setCaughtCount(caughtCount);
    }
    public void setCaughtCount(int[][] caughtCount) {
        this.caughtCount = caughtCount;
    }

    public void assignCards(int totplayers, int hplayer, int cplayer, int[] cardsArray, CardManagement cm) {
        int div = 52 / totplayers;
        int start = 0;
        int p = 1;
        int end = div;

        while (p <= totplayers) {
            Integer[] a = intArrayToIntegerArray(Arrays.copyOfRange(cardsArray, start, end));
            tempPlayersList.add(a);
            cm.setPlayersList(tempPlayersList, p);
            start = end;
            end += div;
            p++;
        }
        //		System.out.println(playersList.get(0).toString());
    }

    public static Integer[] intArrayToIntegerArray(int[] array) {
        Integer[] g = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            g[i] = array[i];
        }
        return g;
    }

    public int[] addDiscardPileToPlayerCards(int loser, Integer[] playerCardsArray, int[] discardPile) {
        // TODO Auto-generated method stub

        int[] newDiscardPile = removeUnwantedElements(discardPile);
        Integer[] h = intArrayToIntegerArray(newDiscardPile);
        Integer[] test = new Integer[h.length + playerCardsArray.length];
        System.arraycopy(playerCardsArray, 0, test, 0, playerCardsArray.length);
        System.arraycopy(h, 0, test, playerCardsArray.length, h.length);

        //adding the elemnets from discardPile to player's array
        getPlayersList().add(loser, test);
        int removeVal;
        removeVal = loser + 1;
        getPlayersList().remove(removeVal);
        //System.out.println(loser+ " : " +Arrays.toString(getPlayersList().get(loser)));
        Arrays.fill(discardPile, -1);
        count = 0;
        return discardPile;
    }

    public int[] removeUnwantedElements(int[] array) {
        int i, j;
        for (i = j = 0; j < array.length; ++j)
            if (array[j] != -1) array[i++] = array[j];
        array = Arrays.copyOf(array, i);
        return array;
    }

    //Method to remove elements from the arraylist
    public int[] removeCards(int players, int player, int[] cardNumber, int[] discardPile, CardManagement cm) {
        len = count;

        Integer[] a = cm.getPlayersList().get(player);
        List<Integer> temp = new ArrayList<Integer>();
        for (int j = 0; j < a.length; j++) {
            temp.add(a[j]);
        }
        for (int i = 0; i < cardNumber.length; i++) {
            int remove = cardNumber[i];

            discardPile[len] = a[remove];
            temp.remove(a[remove]);
            count++;
            len++;
        }
        Integer[] b = temp.toArray(new Integer[temp.size()]);
        playersList.add(player, b);

 /*       System.out.println("---------Current State------------------------------");
        for (int count = 0; count < players; count++) {
            Integer[] playerCards = getPlayersList().get(count);
            System.out.println("playerCards " + Arrays.toString(playerCards));
        }
        System.out.println("------------------------------------------------");
*/
        System.out.println();
        int removePlayer;
        removePlayer = player + 1;
        getPlayersList().remove(removePlayer);
    /*    System.out.println("---------Current State after remove------------------------------");
        for (int count = 0; count < players; count++) {
            Integer[] playerCards = getPlayersList().get(count);
            System.out.println("playerCards " + Arrays.toString(playerCards));
        }
        System.out.println("-------------------------------------------");
*/
        return discardPile;
    }

    /*
        public void updateCaughtCount(int loser){
            int caught[] = getCaughtCount();
            for(int i = 0; i < caught.length; i++){
                caught[loser]++;
            }
            setCaughtCount(caught);
        }
    */
    public int findWinner(int players, CardManagement cm) {
        // TODO Auto-generated method stub
        int winner = 0;
        for (int i = 0; i < players; i++) {
            int len = cm.getPlayersList().get(i).length;
            if (len == 0) winner = i + 1;
        }
        return winner;
    }


}