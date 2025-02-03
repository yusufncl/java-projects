import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class BlackjackGame {

    private Scanner scn = new Scanner(System.in);
    private int numPlayers;
    private Player[] players;
    private Dealer dealer = new Dealer();
    private Deck deck;

    int boardWidth = 600;
    int boardHeight = boardWidth;

    int cardWidth = 110;
    int cardHeight = 154;

    JFrame frame = new JFrame("Blackjack");
    JPanel gamePanel = new JPanel(){
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                //draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                // draw dealer's hand
                int dealerHandSize = dealer.getHandSize();
                for (int i = 0; i < dealerHandSize; i++) {
                    Card card = dealer.getHand().getCards().get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null);
                }

                // Draw all players' hands 
                int yOffset = 180; 
                
                for (int p = 0; p < numPlayers; p++) {
                    Player player = players[p];
                    int playerHandSize = player.getHandSize();

                    // Draw player's name
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 14));
                    g.drawString(player.getName(), 20, yOffset - 10);

                    // Draw player's hand
                    for (int i = 0; i < playerHandSize; i++) {
                        Card card = player.getHand().getCards().get(i);
                        Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                        g.drawImage(cardImg, 20 + (cardWidth + 5) * i, yOffset, cardWidth, cardHeight, null);
                    }

                    yOffset += cardHeight + 20; 
                }


            } catch(Exception e) {
                e.printStackTrace();
            }

        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton standButton = new JButton("Stand");


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
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        gamePanel.setLayout(new BorderLayout());    
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        standButton.setFocusable(false);
        buttonPanel.add(standButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

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

        for(int i = 0; i < numPlayers; i++) {
            int bet;
            do {
                System.out.print(players[i].getName() + " place a bet {available balance $ " + players[i].getBalance() + "}: ");
                bet = scn.nextInt();
                
                if (!players[i].placeBet(bet)) {
                    System.out.println("Invalid bet. Try again!");
                }
            } while (bet <= 0 || bet > players[i].getBalance()); 
        }

        for (int i =0; i<numPlayers; i++){
            players[i].addCard(deck.dealCard());
            players[i].addCard(deck.dealCard());

        }

        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        dealer.revealFirstCard();


        hitOrStand();
        endGame();
    }

    public void dealerTurn(){
        System.out.println("Dealer's turn!");

        while (dealer.getHand().calcTotal() < 17) {
            dealer.addCard(deck.dealCard());
            System.out.println("Dealer hits: " + dealer.getHand());
            System.out.println("Dealer's total: " + dealer.getHand().calcTotal());
        }

        System.out.println("\nDealer's final hand: " + dealer.getHand() + " (Total: " + dealer.getHandTotal() + ")");
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
                System.out.print(currPlayer.getName() + ", would you like to hit or stand? (h/s): ");
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
        System.out.print("Do you want to play again {Y|N}: ");
        String choice = scn.next().toUpperCase();

        if(choice.equals("Y")){
            deck = new Deck();
            deck.shuffle();
            for(int i=0; i < numPlayers; i++){
                players[i].clearHand();
            }
            dealer.clearHand();
            startRound();
        } else {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
    }

    public void startRound(){
        for(int i = 0; i < numPlayers; i++) {
            int bet;
            do {
                System.out.print(players[i].getName() + " place a bet {available balance $: " + players[i].getBalance() + "}: ");
                bet = scn.nextInt();
                
                if (!players[i].placeBet(bet)) {
                    System.out.println("Invalid bet. Try again!");
                }
            } while (bet <= 0 || bet > players[i].getBalance()); 
        }

        for (int i =0; i<numPlayers; i++){
            players[i].addCard(deck.dealCard());
            players[i].addCard(deck.dealCard());

        }

        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        dealer.revealFirstCard();


        hitOrStand();
        endGame();
    }

    // private void kickBrokePlayers(){
    //     int remainingPlayers = 0;
    //     Player[] remaining = new Player[numPlayers];

    //     for(int i=0; i < numPlayers; i++){
    //         if(players[i].getBalance() >= 0){
    //             remaining[remainingPlayers++] = players[i];
    //         } else {
    //             System.out.println(players[i].getName() + "has gone broke and been kicked out of the casino!");
    //         }
    //     }

    //     players = new Player[remainingPlayers];
    //     System.arraycopy(remaining, 0, players, 0, remainingPlayers);
    //     numPlayers = remainingPlayers;
    // }

    public void endGame() {
        int dealerTotal = dealer.getHand().calcTotal();
        dealerTurn();
        for(int i =0; i < numPlayers; i++){
            Player currPlayer = players[i];
            int playerTotal = currPlayer.getHand().calcTotal();
            String playerName = currPlayer.getName();

            System.out.println(currPlayer.getName() + "'s hand: " + currPlayer.getHand() + " (Total: " + playerTotal + ")");


            if(playerTotal > 0){
                if(playerTotal > 21){
                    System.out.println(playerName + " has busted");
                    currPlayer.bust(); 
                } else if (playerTotal == dealerTotal){
                    System.out.println(playerName + " has pushed");
                    currPlayer.push();
                } else if (playerTotal < dealerTotal && dealerTotal <= 21){
                    System.out.println(playerName + " has lost!");
                    currPlayer.loss();
                } else if (playerTotal == 21) { 
                    System.out.println(playerName + " has won with blackjack!");
                    currPlayer.blackjack();
                } else { 
                    System.out.println(playerName + " has won!");
                    currPlayer.win();
                }
            }
            playAgain();
        }
    }
    
}
