package input;

import java.io.*;

public class Move implements java.io.Serializable{
    int cardToPlay;
    int cardPlayed;
    static int[][] stateActionMatrix = new int[13][13];
    static int[][] rewardMatrix = new int[13][13];




    public int[][] getStateActionMatrix() {

        return stateActionMatrix;
    }

    public void setStateActionMatrix(int cardToPlay, int cardPlayed) {

        stateActionMatrix[cardToPlay][cardPlayed] = 1;
    }

    public int[][] getRewardMatrix() {

        return rewardMatrix;
    }

    public void setRewardMatrix(int cardToPlay, int cardPlayed, int bluffInt) {

        rewardMatrix[cardToPlay][cardPlayed] += bluffInt;
    }

    public void Serialize(Move m){
        try {
        FileOutputStream fileOut =
                new FileOutputStream("C:\\Users\\Tina\\workspace\\Bluff_test\\src\\input\\Matrices.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(m);
        out.close();
        fileOut.close();

    } catch (IOException u) {
        u.printStackTrace();
    }
    }
public void Deserialize(Object m){
        String path ="C:\\Users\\Tina\\workspace\\Bluff_test\\src\\input\\";
    try {
        FileInputStream fileIn = new FileInputStream(path+"Matrices.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        while(in.available() > 0) {
            m = (Move) in.readObject();
            in.close();
            fileIn.close();
        }
    } catch (IOException i) {
        i.printStackTrace();
        return;
    } catch (ClassNotFoundException c) {
        System.out.println("Matrix not found");
        c.printStackTrace();
        return;
    }
}

    /*
    public int[][] getEntireStateActionMatrix() {

        return stateActionMatrix;
    }
    public int[][] setEntireRewardMatrix() {

        return rewardMatrix;
    }
*/
}


