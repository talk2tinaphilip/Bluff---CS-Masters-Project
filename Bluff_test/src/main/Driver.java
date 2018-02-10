package main;
import ai.NoBluffAI;
import ai.QLAI;
import ai.SmartAI;
import ai.SmartBluffAI;
import cardload.CardManagement;
import players.ComputerPlayer;
import players.ComputerPlayers;
import players.HumanPlayer;
import players.Players;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
	int[] discardPile = new int[52];
	public static List<Players> playerType = new ArrayList<Players>();
    public static List<Integer> playerTypeNumber = new ArrayList<Integer>();
    public static ArrayList<ComputerPlayers> complayerTypes = new ArrayList<ComputerPlayers>();

	public static void main(String[] args) throws IOException {
		int m = getModes();
		startGame(m);
	}

	public static void startGame(int mode) throws IOException {
		HumanPlayer hp = new HumanPlayer();
		ComputerPlayer cp = new ComputerPlayer();
		CardManagement cm = new CardManagement();
		/* Human-Computer Game consists of humanPlayers and compPlayers */
		if (mode == 1) {
			int hplayers = hp.getHumanPlayerCount(); //get count from user in player.java
			int cplayers = cp.getComputerPlayerCount();//get count from user;
			if(hplayers <1 || cplayers<1){
				System.out.println("Please enter a valid number more than 0!");
				hplayers = hp.getHumanPlayerCount(); //get count from user in player.java
				cplayers = cp.getComputerPlayerCount();//get count from user;
			}
			int totplayers = hplayers+cplayers;
			for (int i = 1; i <= totplayers; i++) {
				while (i <= hplayers) {
					Players p = new HumanPlayer();
					p.setCurrentPlayer(i);
					playerType.add(p);
					i++;
				}
				while (i <= totplayers) {
					Players p = new ComputerPlayer();
					p.setCurrentPlayer(i);
					playerType.add(p);
					i++;
				}
			}
            playerTypeNumber = getplayerTypeNumber();
            cm.initialise(hplayers, cplayers, playerType, complayerTypes, playerTypeNumber);

        }
		/* Computer-Computer Game consists only computer players */
		else if (mode == 2) {
			int hplayers = 0;
			int cplayers = cp.getComputerPlayerCount();
			if(cplayers <=1){
				System.out.println("Please enter a valid number more than 1!");
				cplayers = cp.getComputerPlayerCount();//get count from user;
			}
			int j = 1;char ch; boolean bad_input;
			while (j <= cplayers){
                System.out.println("Choose the type of Computer player "+ j+": ");
                System.out.print("1.No-Bluff AI \t 2.Smart AI \t 3.Learning AI \t 4.Smart-Bluff AI \n");
                Scanner sc = new Scanner(System.in);
                ch = sc.next().charAt(0);
                switch (ch) {
                    case '1'://No-Bluff AI
                        ComputerPlayers computerPlayer1 = new NoBluffAI();
                        computerPlayer1.setCurrentComputerPlayer(j);
                        complayerTypes.add(computerPlayer1);
                        computerPlayer1.setcomputerPlayerType(complayerTypes);
                        playerTypeNumber.add(1);
                        bad_input = false;
                        break;
                    case '2': //Smart AI
                        ComputerPlayers computerPlayer2 = new SmartAI();
                        computerPlayer2.setCurrentComputerPlayer(j);
                        complayerTypes.add(computerPlayer2);
                        computerPlayer2.setcomputerPlayerType(complayerTypes);
                        playerTypeNumber.add(2);
                        bad_input = false;
                        break;
                    case '3'://Learning AI
                        ComputerPlayers computerPlayer3 = new QLAI();
                        computerPlayer3.setCurrentComputerPlayer(j);
                        complayerTypes.add(computerPlayer3);
                        computerPlayer3.setcomputerPlayerType(complayerTypes);
                        playerTypeNumber.add(3);
                        bad_input = false;
                        break;
                    case '4'://Smart-Bluff AI
                        ComputerPlayers computerPlayer4 = new SmartBluffAI();
                        computerPlayer4.setCurrentComputerPlayer(j);
                        complayerTypes.add(computerPlayer4);
                        computerPlayer4.setcomputerPlayerType(complayerTypes);
                        playerTypeNumber.add(4);
                        bad_input = false;
                        break;
                    default://Invalid input
                        System.out.println("Invalid input !! Try again..");
                        bad_input = true;
                        break;
                }
                j++;
            }
            setplayerTypeNumber(playerTypeNumber);
            setcomplayerTypes(complayerTypes);
            int trials = cp.getTrials();
			for(int t = 0; t < trials; t++){
				playerType.clear();
                complayerTypes = getcomplayerTypes();
                playerTypeNumber = getplayerTypeNumber();
                cm.initialise(hplayers, cplayers, playerType, complayerTypes, playerTypeNumber);
			}
            List<Integer> winners = cm.getwinnersList();
			int counter1 = 0;
			int counter2 = 0;
			int counter3 = 0;
			int counter4 = 0;
			int counter5 = 0;
			int counter6 = 0;
            System.out.println("Winners : ");
            for(int i = 0; i < winners.size(); i++) {

				System.out.print(winners.get(i) + " ");

				if(winners.get(i) == 1) counter1++;
				else if(winners.get(i) == 2) counter2++;
				else if(winners.get(i) == 3) counter3++;
				else if(winners.get(i) == 4) counter4++;
				else if(winners.get(i) == 5) counter5++;
				else if(winners.get(i) == 6) counter6++;
			}
            System.out.println();
            System.out.println("Winners Total: ");
			System.out.println(counter1 + ", "+ counter2+ ", " + counter3 + ","+ counter4 + ","+ counter5+ ","+ counter6  );
      //      int[] caughtCount = cm.getCaughtCount();
      //      System.out.println("Caught Count: ");
        //    System.out.println(caughtCount[0] + ", "+ caughtCount[1]+ ", " + caughtCount[2] + ","+ caughtCount[3] );

		}
		/* Human - Human Game consists only human players */
		else if (mode == 3) {
			int cplayers = 0;
			int hplayers = hp.getHumanPlayerCount(); //gets the total count of players
			if(hplayers <=1){
				System.out.println("Please enter a valid number more than 1!");
				hplayers = hp.getHumanPlayerCount();//get count from user;
			}
			int i = 1;
			while (i <= hplayers) {
				Players p = new HumanPlayer();
				p.setCurrentPlayer(i);
				playerType.add(p);
				i++;
			}
            playerTypeNumber = getplayerTypeNumber();
            cm.initialise(hplayers, cplayers, playerType, complayerTypes, playerTypeNumber);

        }
	}

	public static int getModes() {
		int mode = 0;
		char ch;
		boolean yn = true;
		while (yn) {
			System.out.println("Choose Game Mode (1-4)");
			System.out.print("1.Human-Computer \t 2.Computer-Computer \t3.Human solo\t 4.Exit \n");
			Scanner sc = new Scanner(System.in);
			ch = sc.next().charAt(0);
			switch (ch) {
			case '1':
				mode = 1;
				yn = false;
				break;
			case '2':
				mode = 2;
				yn = false;
				break;
			case '3':
				mode = 3;
				yn = false;
				break;
			case '4':
				System.out.println("You exited");
				yn = false;
				break;
			default:
				System.out.println("Invalid input !! Try again..");
				yn = true;
				break;
			}
		}
		return mode;
	}


    public static void setcomplayerTypes(ArrayList<ComputerPlayers> complayerTypes) {
        Driver.complayerTypes = complayerTypes;
    }
    public static ArrayList<ComputerPlayers> getcomplayerTypes() {
        return complayerTypes;
    }

    public static void setplayerTypeNumber(List<Integer> playerTypeNumber) {
        Driver.playerTypeNumber = playerTypeNumber;
    }
    public static List<Integer> getplayerTypeNumber() {
        return playerTypeNumber;
    }
}