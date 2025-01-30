// A hand (list of cards)
// A bankroll (amount of money the player has)
// Methods for betting, receiving cards, calculating hand value, and deciding actions

public class Player {
    private String name;
    private int balance;
    private int bet;
    private Hand hand;
    
    public Player(String name, int balance){
        this.name = name;
        this.balance = balance;
        this.bet = 0;
        this.hand = new Hand();
    }

    public String getName(){
        return name;
    }

    public int getBalance(){
        return balance;
    }

    public int getBet(){
        return bet; 
    }
    
    public Hand getHand(){
        return hand;
    }

    public void setBet(int betAmount){
        this.bet = betAmount;
    }

    public boolean placeBet(int bet) {
        if (bet <= 0) {
            System.out.println("Bet must be greater than 0.");
            return false;
        } else if (bet > balance) {
            System.out.println("Insufficient balance. Your balance is $" + balance + ".");
            return false;
        }
        if (bet > (balance / 2)){
            return true;
        }
        this.bet = bet;
        
        System.out.println("Bet placed: $" + bet);
        return true;
    }

    public void addCard(Card card){
        hand.addCard(card);
    }


    public boolean canPlaceBet(int bet) {
        return bet > 0 && bet <= balance;
    }

    public void winBet(boolean isBlackjack) {
        if (isBlackjack) {
            balance += (int) (bet * 1.5);
            System.out.println(name + " wins with a Blackjack! New balance: $" + balance);
        } else {
            balance += bet;
            System.out.println(name + " wins! New balance: $" + balance);
        }
    }
    

    public void refundBet(){
        balance+=bet;
    }

    public void loseBet(){
        balance -= bet;
    }

    public void clearHand() {
        hand.clear();
    }

    
}
