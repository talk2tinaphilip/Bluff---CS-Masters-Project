package ai;

import cardload.BluffVerification;
import cardload.Card;
import cardload.CardManagement;
import players.ComputerPlayers;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;

public class SmartBluffAI extends ComputerPlayers {
    BluffVerification p = new BluffVerification();
    Card c = new Card();
    int cardsOfCurrentPlayer[];
    Scanner in = new Scanner(System.in);
    private int[] discardPile;
    int[] cardsOfPlayer;
    int reward[] = new int[4];
    int oldReward[] = new int[4];
    int score;
    int cardPlayed;
    int hand[] = new int[52];
    byte cardNumberInBytes;
    int cardCount[] = new int[5];
    static int turn;

    int getTurn() {
        turn++;
        return turn;
    }

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
        return currentCardsPlayed;
    }

    public static boolean callBluff(ArrayList<ComputerPlayers> computerPlayerType, int currentPlayer, int challenger,
                                    int[] cardsOfPlayer, int[] discardPile, CardManagement cm) {
        boolean bluff = false;
        boolean verdict = false;
        BluffVerification b = new BluffVerification();
        Card c = new Card();
        int player = currentPlayer + 1;
        bluff = getIntel(challenger, currentPlayer, cm.getCurrentCard(), cm);
        if (bluff) {
            System.out.println("Yes");
            int[] actualCardsToPlay = c.getActualCardsToBePlayed(cm.getCurrentCard());
            for(int p = 0; p< actualCardsToPlay.length; p++) System.out.print(actualCardsToPlay[p]+ " ");
            System.out.println();
            for(int q = 0; q< cardsOfPlayer.length; q++) System.out.print(cardsOfPlayer[q] + " ");

            verdict = b.bluffVerifier(cardsOfPlayer, actualCardsToPlay);
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
        } else System.out.println("No");
        return bluff;
    }
    private static boolean getIntel(int challenger, int currentPlayer, int currentCard, CardManagement cm) {
        boolean bluff = false;
        int counter = 0;
        double random = 0.0;
        Card c = new Card();
        int opponentHandSize = cm.getPlayersList().get(currentPlayer).length;
        //System.out.println("challenger: " + challenger);
        Integer[] challegerCardsArray = cm.getPlayersList().get(challenger);
      //  for (int j = 0; j < challegerCardsArray.length; j++) {
      //      System.out.print(challegerCardsArray[j] + " ");
      //  }
        int[] currentCardtoPlay = c.getActualCardsToBePlayed(currentCard);

        for (int j = 0; j < challegerCardsArray.length; j++) {
            for (int i : currentCardtoPlay) {
                if (i == challegerCardsArray[j]) {
                    counter++;
                }
            }
        }
        if(opponentHandSize <= 1)bluff = true;
        if (opponentHandSize > 2) {
            random = Math.random();
            if (counter == 2) {// if I have 2 of the same type of card, call bluff when random number >0.5
                if (random > 0.75) {
                    //System.out.println("random: " + random);
                    bluff = true;
                }
            } else if (counter == 3) {
                if (random > 0.25) {
                    //System.out.println("random: " + random);
                    bluff = true;
                }
            } else if (counter == 4) bluff = true;
        }
        return bluff;
    }
    private int[] getCards(int cplayers, int currentPlayer, Integer[] playerCardsArray, int current_card_to_play,
                           int[] discardPile, BitSet discardPileIntel, CardManagement cm) {
        // TODO Auto-generated method stub
        this.discardPile = discardPile;
        int[] currentCardtoPlay = c.getActualCardsToBePlayed(current_card_to_play);
        int verdict[] = findCardsToPlay(cplayers, playerCardsArray, currentCardtoPlay, discardPileIntel, cm);
        //cardsOfPlayer = p.getCardsOfPlayer(verdict, playerCardsArray, discardPile);
        int cardNumbers[] = new int[verdict.length];
        for(int g = 0; g < verdict.length; g++){
            cardNumbers[g] = playerCardsArray[verdict[g]];
        }
        setCardsOfCurrentPlayer(cardNumbers);
        discardPile = cm.removeCards(cplayers, currentPlayer, verdict, discardPile, cm);
        return discardPile;
    }
    public static boolean contains(int[] array, int key) {
        for (int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }
    /**
     * @param computerPlayerCardsArray
     * @param currentCardtoPlay
     * @return
     */
    public int[] findCardsToPlay(int cplayers, Integer[] computerPlayerCardsArray, int[] currentCardtoPlay,
                                 BitSet discardPileIntel, CardManagement cm) {
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
            for (int k = 0; k < bluffCard.length-1; k++) {
                int[] bluffCardtoPlay = c.getActualCardsToBePlayed(bluffCard[k]);
                //System.out.println("bluffCardtoPlay :"+bluffCardtoPlay);
                if(bluffCardtoPlay != null) {
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

    public void setCardsOfCurrentPlayer(int verdict[]) {
        cardsOfCurrentPlayer = verdict.clone();
    }

    public int[] getCardsOfCurrentPlayer() {
        return cardsOfCurrentPlayer;
    }

}
