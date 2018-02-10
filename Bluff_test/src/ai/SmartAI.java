package ai;

import cardload.BluffVerification;
import cardload.Card;
import cardload.CardManagement;
import input.Move;
import players.ComputerPlayers;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tina
 */
//This AI player
public class SmartAI extends ComputerPlayers implements java.io.Serializable {
    Card c = new Card();
    Move m = new Move();
    Scanner in = new Scanner(System.in);
    private int[] discardPile;
    int[] cardsOfPlayer;
    int score;
    int cardPlayed;
    int hand[] = new int[52];
    byte cardNumberInBytes;
    int cardsOfCurrentPlayer[];

    public int[] play(int cplayers, int currentPlayer, int[] discardPile, CardManagement cm) {
        int i = 0;
        int player;
        int delta = 0;
        boolean finished = false;
        BitSet discardPileIntel = new BitSet();
        player = currentPlayer + 1;
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
        discardPile = getCards(cplayers, currentPlayer, playerCardsArray, cm.getCurrentCard(), discardPile, discardPileIntel, cm);
        int[] currentCardsPlayed = getCardsOfCurrentPlayer();
       // System.out.print("Cards Played: ");
        //for (int j = 0; j < currentCardsPlayed.length; j++) System.out.print(currentCardsPlayed[j] + ", ");

        return currentCardsPlayed;
    }

    public static boolean callBluff(ArrayList<ComputerPlayers> computerPlayerType, int currentPlayer, int challenger,
                                    int[] cardsOfPlayer, int[] discardPile, CardManagement cm) {

        boolean bluff = false;
        boolean verdict = false;
        BluffVerification b = new BluffVerification();
        Card c = new Card();
        int player = currentPlayer + 1;
        bluff = getIntel(challenger, cm.getCurrentCard(), cm);
        if (bluff) {
            System.out.println("Yes");
            System.out.println();

           // System.out.println("Actual Cards To Be Played : ");
            int[] actualCardsToBePlayed = c.getActualCardsToBePlayed(cm.getCurrentCard());
            //for (int p = 0; p < actualCardsToBePlayed.length; p++) System.out.print(actualCardsToBePlayed[p] + " ");
            System.out.println();
           // System.out.println("Cards of Player: ");
           //// for (int q = 0; q < cardsOfPlayer.length; q++) System.out.print(cardsOfPlayer[q] + " ");
            System.out.println();
            if (cardsOfPlayer.length > actualCardsToBePlayed.length)
                bluff = true;
            else {
                for (int j = 0; j < cardsOfPlayer.length; j++) {
                    boolean contains = contains(actualCardsToBePlayed, cardsOfPlayer[j]);
                    //System.out.println("contains: "+ contains);
                    if (!contains) {
                        verdict = true;
                        break;
                    }

                }
            }
            int loser;
            if (verdict) {
                loser = currentPlayer;
                cm.updateCaughtCount(loser, challenger);
                System.out.println("Caught! Player " + player + " played wrong cards. The pile is now yours!");
                discardPile = cm.addDiscardPileToPlayerCards(loser, cm.getPlayersList().get(currentPlayer), discardPile);

            } else {
                loser = challenger;
                cm.updateCaughtCount(loser, loser);
                System.out.println("Player " + player + " played correct cards. The pile goes to challenger.");
                discardPile = cm.addDiscardPileToPlayerCards(loser, cm.getPlayersList().get(loser), discardPile);

            }

        } else {
            System.out.println("No");


        }
        return bluff;
    }

    public static boolean contains(int[] array, int key) {
        for (int i : array) {
            if (i == key) {
                return true;
            }

        }
        return false;
    }

    private int[] getCards(int cplayers, int currentPlayer, Integer[] playerCardsArray, int current_card_to_play,
                           int[] discardPile, BitSet discardPileIntel, CardManagement cm) {
        BluffVerification b = new BluffVerification();
        this.discardPile = discardPile;
        int[] currentCardtoPlay = c.getActualCardsToBePlayed(current_card_to_play);
       // System.out.println("Cards to be played: ");
        //for (int j = 0; j < currentCardtoPlay.length; j++) System.out.print(currentCardtoPlay[j] + " ");

        int verdict[] = findCardsToPlay(cplayers, playerCardsArray, currentCardtoPlay, discardPileIntel, cm);
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
            for (int k = 0; k < bluffCard.length - 1; k++) {
                int[] bluffCardtoPlay = c.getActualCardsToBePlayed(bluffCard[k]);
                if (bluffCardtoPlay != null) {
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
            }
            retVal[0] = returnCard;
        }
        return retVal;
    }

    private static boolean getIntel(int challenger, int currentCard, CardManagement cm) {
        boolean bluff = false;
        int counter = 0;
        double random = 0.0;
        Card c = new Card();
        //System.out.println("challenger: " + challenger);
        Integer[] challegerCardsArray = cm.getPlayersList().get(challenger);
        //for (int j = 0; j < challegerCardsArray.length; j++) System.out.print(challegerCardsArray[j] + " ");

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
            if (random > 0.75) {
                //  System.out.println("random: " + random);
                bluff = true;
            }
        } else if (counter == 3) {
            if (random > 0.25) {
                // System.out.println("random: " + random);
                bluff = true;
            }
        } else if (counter == 4) bluff = true;
        return bluff;
    }

    public void setCardsOfCurrentPlayer(int verdict[]) {
        cardsOfCurrentPlayer = verdict.clone();
    }

    public int[] getCardsOfCurrentPlayer() {
        return cardsOfCurrentPlayer;
    }
}