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
        System.out.println("WELCOME TO BLACKJACK");


        do { 
            System.out.print("Enter the number of players 1-4: ");
            numPlayers = scn.nextInt();
        } while (numPlayers < 1 || numPlayers > 4);

        players = new Player[numPlayers]; // array of players

        for(int i=0; i < numPlayers; i++){
            System.out.print("Enter the name of player " + (i + 1) + ": ");
            String playerName = scn.next();
            players[i] = new Player(playerName, 100);
        }

        deck = new Deck();
        deck.shuffle();

        for(int i =0; i<numPlayers; i++){
            int bet;
            do{
                System.out.print(players[i].getName() + " place a bet {available balance $: " + players[i].getBalance() + "}: ");
                bet=scn.nextInt();
                if (!players[i].placeBet(bet)) {
                    System.out.println("Invalid bet. Try again!");
                }
            } while(bet <= 0 || bet > players[i].getBalance());
        }

        for (int i =0; i<numPlayers; i++){
            players[i].addCard(deck.dealCard());
            players[i].addCard(deck.dealCard());

        }

        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        dealer.revealFirstCard();


        hitOrStand();
    }


    public void hitOrStand() {
        for(int i =0; i < numPlayers; i++){
            Player currPlayer = players[i];

            System.out.println(currPlayer.getName() + "'s Hand: " + currPlayer.getHand());

            if (currPlayer.getHand().isBusted()){
                System.out.println(currPlayer.getName()+ " has busted!");
                continue;
            }
            String choice;
            do{ 
                System.out.println(currPlayer.getName() + ", would you like to hit or stand? (h/s): ");
                choice = scn.next();
    
                if(choice.equals("h")){
                    currPlayer.addCard(deck.dealCard());
                    System.out.println(currPlayer.getName() + " chose to hit.");
                    System.out.println(currPlayer.getName() + "'s Hand: " + currPlayer.getHand());
                    System.out.println("Current total: " + currPlayer.getHand().calcTotal());

                    if (currPlayer.getHand().isBusted()){
                        System.out.println(currPlayer.getName()+ " has busted!");
                        break;
                    }
                }
            } while(!choice.equals("s") && !currPlayer.getHand().isBusted());
        }
    }

    public void checkBlackjack() {
        for(int i =0; i < numPlayers; i++){
            Player currPlayer = players[i];

            if (currPlayer.getHand().isBlackjack()){
                System.out.println(currPlayer.getName()+ " has Blackjack!");
            }
        }

        if(dealer.getHand().isBlackjack()){
            System.out.println("Dealer has Blackjack!");
        }
    }

    public void playAgain() {   
        System.out.println("Do you want to play again {Y|N}: ");
        String choice = scn.next().toUpperCase();

        if(choice.equals("Y")){
            deck = new Deck();
            deck.shuffle();
            for(int i=0; i < numPlayers; i++){
                players[i].clearHand();
            }
            dealer.clearHand();
            startGame();
        } else {
            System.out.println("Thanks for playing!");
        }

    }


    public void endGame() {
        int dealerTotal = dealer.getHand().calcTotal();
        System.out.println("\nDealer's final hand: " + dealer.getHand() + " (Total: " + dealerTotal + ")");
        
        if (dealerTotal>21){
            System.out.println("Dealer has busted! All remaining players win!");
        }
        
        for(int i=0; i<numPlayers; i++){
            Player currPlayer = players[i];
            int playerTotal = currPlayer.getHand().calcTotal();

            System.out.println(currPlayer.getName() +  " 's hand: " + currPlayer.getHand() + " (Total: " + playerTotal +")");

            if(currPlayer.getHand().isBusted()){
                System.out.println(currPlayer.getName() + " has busted!");
            } else if (dealerTotal > 21){
                System.out.println(currPlayer.getName() + " wins! Dealer busted.");
            } else if (playerTotal > dealerTotal) {
                System.out.println(currPlayer.getName() + " wins!");
            } else if ( playerTotal == dealerTotal) {
                System.out.println(currPlayer.getName() + " pushes with the dealer.");
            } else {
                System.out.println(currPlayer.getName() + " loses.");
            }
        }

        for(int i =0; i < numPlayers; i++){
            players[i].clearHand();
        }
        dealer.clearHand();

        playAgain();
    }
}
