package ai;

import cardload.BluffVerification;
import cardload.Card;
import cardload.CardManagement;

import java.io.IOException;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;

public class QAI {
    BluffVerification p = new BluffVerification();
    Card c = new Card();
    static int turn;
    double EPSILON = 0.1;
    Random rand = new Random();
    //1. Initialize replay memory D
    BitSet bs = new BitSet(62);


    //2. Initialize Q with random weights(size of Q TBD)
    //3. Observe initial state s
    //4. Repeat until the end:
    public void play(int cplayers, int currentPlayer, int[] discardPile, CardManagement cm) throws IOException {
        int player;
        HashSet<Integer> h = new HashSet<Integer>();
        player = currentPlayer+1;
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
        System.out.println();
        Integer[] playerCardsArray = cm.getPlayersList().get(currentPlayer);
        int no_of_cards_to_play;
        if(turn < 30 || EPSILON  < 0.1) {
            //5. Select action a  in 2 ways
            //1. if epsilon < 0.1, select random action
            int max =cm.getPlayersList().get(currentPlayer).length-1 ;
            System.out.println("max "+ max);
            if(max>0) {
                no_of_cards_to_play = (rand.nextInt(4-0)+1);
                //System.out.println("no_of_cards_to_play "+ no_of_cards_to_play);
                int[] verdict = new int[no_of_cards_to_play];
                for(int i = 0; i < no_of_cards_to_play; i++){
                    h = getCardToPlay(h, max);
                }
                for(int i : h){
                    verdict[i] = i;
                }
                discardPile = cm.removeCards(cplayers, currentPlayer, verdict, discardPile, cm);
            }
        }else if(EPSILON > 0.1){
            //2. else select a with max from Q
        }


        //6. perform action a
        //7. store the result <s, a, r, s'> in D

        //8. sample random transitions from D
        //9. if end state calculate reward
        //10. else reward + gamma(maxQ())
        //11. Gradient descent -> loss
    }
    int getTurn(){
        turn++;
        return turn;
    }
    HashSet getCardToPlay(HashSet hashSet, int max){
        int random_no = (rand.nextInt(max-0+1)+0);
        if(!hashSet.contains(random_no)) {
            hashSet.add(random_no);
        }else{
            getCardToPlay(hashSet, max);
        }
        return hashSet;
    }
}
