import java.util.Random;

public class Simulation {

    private static final int numSimulations = 10000;
    private static final int betAmount = 10;
    private static final int initialBalance = 100;
    public static void main(String[] args) {
        Random rand = new Random();
        int wins = 0;
        int losses = 0;
        int draws = 0;

        for(int i =0; i < numSimulations; i++){
            int result = monteCarloSimulation(rand);
            if(result == 1){
                wins++;
            }else if (result == -1){
                losses++;
            } else {
                draws++;
            }


            double winRate = (double) wins / numSimulations;
            double lossRate = (double) losses / numSimulations;
            double drawRate = (double) draws / numSimulations;
    
            System.out.println("Monte Carlo Simulation Results:");
            System.out.println("Win Rate: " + winRate);
            System.out.println("Loss Rate: " + lossRate);
            System.out.println("Push Rate: " + drawRate);            
        }
    }

    public static int monteCarloSimulation(Random rand){
        Deck deck = new Deck();
        deck.shuffle();
        Dealer dealer = new Dealer();
        Player player = new Player("null", initialBalance);
        player.placeBet(betAmount);
        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        if(player.getHand().isBlackjack()){
            return 1;
        }
        if(dealer.getHand().isBlackjack()){
            return -1;
        }
        int playerTotal = player.getHand().calcTotal();
        int dealerTotal = dealer.getHand().calcTotal();

        while(playerTotal < 17){
            player.addCard(deck.dealCard());
            if (player.getHand().isBusted()) {
                return -1; 
            }
        }

        while (dealerTotal < 17) {
            dealer.addCard(deck.dealCard());
            if (dealer.getHand().isBusted()) {
                return 1;
            }
        }
        if (playerTotal > dealerTotal) {
            return 1;  
        } else if (playerTotal < dealerTotal) {
            return -1; 
        } else {
            return 0; 
        }
    }
}
