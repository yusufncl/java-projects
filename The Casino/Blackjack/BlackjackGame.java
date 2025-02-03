import java.util.Scanner;

public class BlackjackGame {

    private Scanner scn = new Scanner(System.in);
    private int numPlayers;
    private Player[] players;
    private Dealer dealer;
    private Deck deck;

    public BlackjackGame() {
        dealer = new Dealer();
        deck = new Deck();
    }

    public void startGame() {
        System.out.println("WELCOME TO BLACKJACK");

        do {
            System.out.print("Enter the number of players 1-4: ");
            numPlayers = scn.nextInt();
        } while (numPlayers < 1 || numPlayers > 4);

        players = new Player[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter the name of player " + (i + 1) + ": ");
            String playerName = scn.next();
            players[i] = new Player(playerName, 100);
        }

        startRound();
    }

    public void startRound() {
        deck = new Deck();
        deck.shuffle();
        dealer.clearHand();

        for (Player player : players) {
            player.clearHand();
            int bet;
            do {
                System.out.print(player.getName() + " place a bet {available balance $" + player.getBalance() + "}: ");
                bet = scn.nextInt();
            } while (!player.placeBet(bet));
        }

        for (Player player : players) {
            player.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
        }

        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        System.out.println("Dealer's first card: " + dealer.getHand().getCards().get(0));

        hitOrStand();
        dealerTurn();
        endGame();
    }

    public void hitOrStand() {
        for (Player player : players) {
            System.out.println(player.getName() + "'s Hand: " + player.getHand());

            if (player.getHand().isBusted()) {
                System.out.println(player.getName() + " has busted!");
                continue;
            }
            
            String choice;
            do {
                System.out.print(player.getName() + ", would you like to hit or stand? (h/s): ");
                choice = scn.next();
                
                if (choice.equals("h")) {
                    player.addCard(deck.dealCard());
                    System.out.println(player.getName() + "'s new hand: " + player.getHand());
                    if (player.getHand().isBusted()) {
                        System.out.println(player.getName() + " has busted!");
                        break;
                    }
                }
            } while (!choice.equals("s"));
        }
    }

    public void dealerTurn() {
        System.out.println("Dealer's turn!");
        System.out.println("Dealer's full hand: " + dealer.getHand());
        
        while (dealer.getHand().calcTotal() < 17) {
            dealer.addCard(deck.dealCard());
        }

        System.out.println("Dealer's final hand: " + dealer.getHand() + " (Total: " + dealer.getHand().calcTotal() + ")");
    }

    public void endGame() {
        int dealerTotal = dealer.getHand().calcTotal();
    
        for (Player player : players) {
            int playerTotal = player.getHand().calcTotal();
            System.out.println(player.getName() + "'s final hand: " + player.getHand() + " (Total: " + playerTotal + ")");
    
            if (player.hasBlackjack()) {
                System.out.println(player.getName() + " has a Blackjack!");
                player.blackjack(); 
            } else if (playerTotal > 21) {
                System.out.println(player.getName() + " has busted!");
                player.loss();
            } else if (playerTotal == dealerTotal) {
                System.out.println(player.getName() + " has pushed.");
                player.push();
            } else if (dealerTotal > 21 || playerTotal > dealerTotal) {
                System.out.println(player.getName() + " has won!");
                player.win();
            } else {
                System.out.println(player.getName() + " has lost.");
                player.loss();
            }
    
            if (player.getBalance() == 0) {
                System.out.println(player.getName() + ", you no longer have any funds left. You cannot play anymore.");
                System.exit(0);  
            }
        }
        playAgain();
    }
    
    public void playAgain() {
        System.out.print("Do you want to play again {Y|N}: ");
        String choice = scn.next().toUpperCase();

        if (choice.equals("Y")) {
            startRound();
        } else {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
    }
}
