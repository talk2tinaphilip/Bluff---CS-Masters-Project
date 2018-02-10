package ai;

import cardload.BluffVerification;
import cardload.Card;
import cardload.CardManagement;
import input.Move;
import players.ComputerPlayers;

import java.io.*;
import java.util.*;

public class QLAI extends ComputerPlayers implements java.io.Serializable {
    BluffVerification p = new BluffVerification();
    Card c = new Card();
    Move m = new Move();
    static int turn = 0;
    private int[] discardPile;
    static int[] cardsOfPlayer;
    int reward[] = new int[4];
    int oldReward[] = new int[4];
    int score;
    int cardPlayed;
    int hand[] = new int[52];
    byte cardNumberInBytes;
    static int[][] stateActionMatrix = new int[13][13];
    static int[][] rewardMatrix = new int[13][13];
    int cardsOfCurrentPlayer[];
    static boolean bluffVal;

    public QLAI() throws IOException {
        new QLAI(score, cardNumberInBytes);
        m.Deserialize(m);
    }

    public QLAI(int score, byte cardNumberInBytes) throws IOException {
        this.score = score;
        this.cardNumberInBytes = cardNumberInBytes;
    }

    public int[] play(int cplayers, int currentPlayer, int[] discardPile, CardManagement cm) {
        String path = "C:\\Users\\Tina\\workspace\\Bluff_test\\src\\input\\";
        //System.out.println("Deserialized stateActionMatrix ");
        //int[][] stateActionMatrix = m.getStateActionMatrix();
        //prettyPrint(stateActionMatrix);
       // System.out.println("Deserialized rewardMatrix ");
        //int[][] rewardMatrix = m.getRewardMatrix();
        //prettyPrint(rewardMatrix);

        int i = 0;
        int bluffInt = 0;
        int delta = 0;
        byte bluffCaught = 0;
        boolean finished = false;
        boolean bluff = false;
        int player;

        player = currentPlayer + 1;
        //System.out.println("player: " + player);
        turn = getTurn();
        //System.out.println("turn: " + turn);
        int counter = 0;
        for (int ii : cm.getPlayersList().get(currentPlayer)) {
            if (counter % 2 == 0) System.out.println();
            if (ii != 100) {
                String s1 = String.format("%1$-20s", c.getDeck()[ii]);
                System.out.print(counter + " - " + s1);
                counter++;
            }
        }
        System.out.println();
        Integer[] playerCardsArray = cm.getPlayersList().get(currentPlayer);

        BitSet discardPileIntel = new BitSet();
        if (turn < 27) {
            setTurn(turn);
            discardPile = getCards(cplayers, currentPlayer, playerCardsArray, cm.getCurrentCard(),
                    discardPile, discardPileIntel, cm);
            int[] currentCardsPlayed = getCardsOfCurrentPlayer();
            //System.out.print("Cards Played: ");
           // for (int j = 0; j < currentCardsPlayed.length; j++) System.out.print(currentCardsPlayed[j] + ", ");
            return currentCardsPlayed;
        } else {
            setTurn(turn);
            int max = 1;
            List<Integer> actionsFromState = new ArrayList<>();
            for (int m = 0; m < 13; m++) {
                if ((stateActionMatrix[cm.getCurrentCard()][m] == 1) && (rewardMatrix[cm.getCurrentCard()][m] > 0)) {
                    if (rewardMatrix[cm.getCurrentCard()][m] > max) {
                        max = rewardMatrix[cm.getCurrentCard()][m];
                    }
                }
            }
            for (int m = 0; m < 13; m++) {
                if (rewardMatrix[cm.getCurrentCard()][m] == max) {
                    actionsFromState.add(m);
                    actionsFromState.add(m + 13);
                    actionsFromState.add(m + 26);
                    actionsFromState.add(m + 39);
                }
            }
            //System.out.println("actionsFromStateMatrix ");
           // for (int d = 0; d < actionsFromState.size(); d++) {
           //     System.out.print(actionsFromState.get(d) + " ");
           // }
            System.out.println();
            int nextState = 0;
            boolean found = false;

            int size2;
            List<Integer> cardsToPlay2 = new ArrayList<>();

            for (int k = 0; k < cm.getPlayersList().get(currentPlayer).length; k++) {
                for (int j = 0; j < actionsFromState.size(); j++) {
                    if (actionsFromState.get(j) == cm.getPlayersList().get(currentPlayer)[k]) {
                        cardsToPlay2.add(k);
                        found = true;
                        nextState = getActionsFromState(j, nextState, actionsFromState);
                    }
                }
            }
            if (cardsToPlay2.size() == 0) size2 = 1;
            else size2 = cardsToPlay2.size();
            int retVal[] = new int[size2];
            if (found) {
                for (int m = 0; m < cardsToPlay2.size(); m++)
                    retVal[m] = cardsToPlay2.get(m);
            }
            if (found == true) {
                discardPile = cm.removeCards(cplayers, currentPlayer, retVal, discardPile, cm);
            } else
                discardPile = getRandomCards(cplayers, currentPlayer, playerCardsArray, cm.getCurrentCard(), discardPile, cm);

        }
        int[] currentCardsPlayed = getCardsOfCurrentPlayer();
        return currentCardsPlayed;
    }

    public static boolean callBluff(ArrayList<ComputerPlayers> computerPlayerType, int currentPlayer, int challenger,
                                    int[] cardsOfPlayer, int[] discardPile, CardManagement cm) {

        boolean bluff = false;
        boolean bluffCaught = false;
        boolean verdict = false;
        BluffVerification b = new BluffVerification();
        Card c = new Card();
        int player = currentPlayer + 1;
        bluff = getIntel(challenger, cm.getCurrentCard(), cm);
        if (bluff) {
            System.out.println("Yes");
            int[] actualCardsToPlay = c.getActualCardsToBePlayed(cm.getCurrentCard());
            verdict = b.bluffVerifier(cardsOfPlayer, actualCardsToPlay);
            int loser;
            if (verdict) {
                loser = currentPlayer;
                bluffCaught = true;
                cm.updateCaughtCount(loser, challenger);
                System.out.println("Caught! Player " + player + " played wrong cards. The pile is now yours!");
                discardPile = cm.addDiscardPileToPlayerCards(loser, cm.getPlayersList().get(currentPlayer), discardPile);
            } else {
                loser = challenger;
                bluffCaught = false;
                cm.updateCaughtCount(loser, loser);
                System.out.println("Player " + player + " played correct cards. The pile goes to challenger.");
                discardPile = cm.addDiscardPileToPlayerCards(loser, cm.getPlayersList().get(loser), discardPile);
            }
        } else {
            System.out.println("No");
        }
        setBluff(bluffCaught);
        return bluff;
    }

    private static boolean getIntel(int challenger, int currentCard, CardManagement cm) {
        boolean bluff = false;
        int counter = 0;
        double random = 0.0;
        Card c = new Card();
        Integer[] challegerCardsArray = cm.getPlayersList().get(challenger);
        //for (int j = 0; j < challegerCardsArray.length; j++) {
        //    System.out.print(challegerCardsArray[j] + " ");
        //}
        int[] currentCardtoPlay = c.getActualCardsToBePlayed(currentCard);
        for (int j = 0; j < challegerCardsArray.length; j++) {
            for (int i : currentCardtoPlay) {
                if (i == challegerCardsArray[j]) {
                    counter++;
                }
            }
        }
        random = Math.random();
        if (counter == 2) {// if I have 2 of the same type of card, call bluff when random number >0.5
            if (random > 0.75)
                bluff = true;
        } else if (counter == 3) {
            if (random > 0.25)
                bluff = true;
        } else if (counter == 4) bluff = true;
        return bluff;
    }


    private int getActionsFromState(int j, int nextState, List<Integer> actionsFromState) {
        if (j <= 3) nextState = actionsFromState.get(0);
        else if (j > 3 && j <= 7) nextState = actionsFromState.get(4);
        else if (j > 7 && j <= 11) nextState = actionsFromState.get(8);
        else if (j > 11 && j <= 15) nextState = actionsFromState.get(12);
        else if (j > 15 && j <= 19) nextState = actionsFromState.get(16);
        else if (j > 19 && j <= 23) nextState = actionsFromState.get(20);
        else if (j > 23 && j <= 27) nextState = actionsFromState.get(24);
        else if (j > 27 && j <= 31) nextState = actionsFromState.get(28);
        else if (j > 31 && j <= 35) nextState = actionsFromState.get(32);
        else if (j > 35 && j <= 39) nextState = actionsFromState.get(36);
        else if (j > 39 && j <= 43) nextState = actionsFromState.get(40);
        else if (j > 43 && j <= 47) nextState = actionsFromState.get(44);
        else if (j > 47 && j <= 51) nextState = actionsFromState.get(48);
        else if (j > 51) nextState = actionsFromState.get(52);
        return nextState;
    }


    public void prettyPrint(int[][] array) {
        System.out.println(Arrays.deepToString(array).replace("], ", "]\n").replace("[[",
                "[").replace("]]", "]"));
    }

    int R(int s, int a) {
        return rewardMatrix[s][a];
    }

    private int[] getCards(int cplayers, int currentPlayer, Integer[] playerCardsArray, int current_card_to_play,
                           int[] discardPile, BitSet discardPileIntel, CardManagement cm) {
        // TODO Auto-generated method stub
        this.discardPile = discardPile;
        int[] currentCardtoPlay = c.getActualCardsToBePlayed(current_card_to_play);
        int verdict[] = findCardsToPlay(cplayers, playerCardsArray, currentCardtoPlay, discardPileIntel, cm);
        //cardsOfPlayer = p.getCardsOfPlayer(verdict, playerCardsArray, discardPile);
        int cardNumbers[] = new int[verdict.length];
        for (int g = 0; g < verdict.length; g++) {
            cardNumbers[g] = playerCardsArray[verdict[g]];
        }
        setCardsOfCurrentPlayer(cardNumbers);
        discardPile = cm.removeCards(cplayers, currentPlayer, verdict, discardPile, cm);
        return discardPile;
    }

    public int[] findCardsToPlay(int cplayers, Integer[] computerPlayerCardsArray, int[] currentCardtoPlay,
                                 BitSet discardPileIntel, CardManagement cm) {
        // TODO Auto-generated method stub
        boolean contains = false;
        int size;
        List<Integer> cardsToPlay = new ArrayList<>();
        int card = 0;
        for (int j = 0; j < computerPlayerCardsArray.length; j++) {
            for (int i : currentCardtoPlay) {
                if (i == computerPlayerCardsArray[j]) {
                    cardsToPlay.add(j);
                    contains = true;
                }
            }
        }
        if (cardsToPlay.size() == 0) size = 1;
        else size = cardsToPlay.size();
        int retVal[] = new int[size];

        if (contains) {
            for (int i = 0; i < cardsToPlay.size(); i++) retVal[i] = cardsToPlay.get(i);
        } else {
            boolean returnCardContains = false;
            int[] bluffCard = c.returnCardType(cplayers, cm.getCurrentCard());
            int returnCard = 0;
            for (int k = 0; k < bluffCard.length; k++) {
                int[] bluffCardtoPlay = c.getActualCardsToBePlayed(bluffCard[k]);
                for (int j = 0; j < computerPlayerCardsArray.length; j++) {
                    for (int l : bluffCardtoPlay) {
                        if (l == computerPlayerCardsArray[j]) {
                            cardPlayed = c.getCardsType(computerPlayerCardsArray[j]);
                            returnCardContains = true;
                            returnCard = j;
                            break;
                        }
                    }
                    if (returnCardContains == true) break;
                }
            }
            retVal[0] = returnCard;
        }
        return retVal;
    }

    public int[] getRandomCards(int cplayers, int currentPlayer, Integer[] playerCardsArray, int current_card_to_play,
                                int[] discardPile, CardManagement cm) {
        // TODO Auto-generated method stub
        this.discardPile = discardPile;
        int retVal[] = new int[1];
        //select a random number b/n 1 and current number of cards in the hand
        Random rand = new Random();
        int max = cm.getPlayersList().get(currentPlayer).length - 1;
        if (max > 0) {
            retVal[0] = (rand.nextInt(max - 0) + 1);
            cardsOfPlayer = p.getCardsOfPlayer(retVal, playerCardsArray, discardPile);
            discardPile = cm.removeCards(cplayers, currentPlayer, retVal, discardPile, cm);
        }
        return discardPile;
    }

    public static void setBluff(boolean bluffCaught) {
        bluffVal = bluffCaught;
    }

    public static boolean getBluff() {
        return bluffVal;
    }

    public void setCardsOfCurrentPlayer(int verdict[]) {
        cardsOfCurrentPlayer = verdict.clone();
    }

    public int[] getCardsOfCurrentPlayer() {
        return cardsOfCurrentPlayer;
    }

    public static void setTurn(int turn) {
        QLAI.turn = turn;
    }

    public static int getTurn() {

        return turn + 1;
    }
}
