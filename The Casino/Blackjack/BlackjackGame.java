import java.util.Scanner;

public class BlackjackGame {

    private Scanner scn = new Scanner(System.in);
    private int numPlayers;
    private Player[] players;
    private Dealer dealer = new Dealer();
    private Deck deck;


    public BlackjackGame(){
        dealer = new Dealer();
        deck = new Deck();
    }

    public int getNumPlayers(){
        return numPlayers;
    }

    public void setPlayers(Player[] players){
        this.players = players;
    }

    public void startGame() {
        String playerName;
        System.out.println("WELCOME TO BLACKJACK");


        do { 
            System.out.println("Enter the number of players 1-4: ");
            numPlayers = scn.nextInt();
        } while (numPlayers < 1 || numPlayers > 4);

        players = new Player[numPlayers]; // array of players

        for(int i=0; i < numPlayers; i++){
            System.out.println("Enter the name of player " + (i + 1) + ":");
            playerName = scn.next();
        }

    }


    public void hitOrStand() {

    }


    public void checkBlackjack() {

    }

    public void playAgain() {

    }


    public void endGame() {

    }
}
