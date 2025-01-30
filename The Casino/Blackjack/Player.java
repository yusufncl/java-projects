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

    public boolean placeBet(int amount){
        if(amount > 0 && amount <= balance){
            bet = amount;
            balance -= amount;
            return true;
        } else {
            return false;
        }

    }

    public void addCard(Card card){
        hand.addCard(card);
    }





    public void winBet(){
        balance *= 2;
    }

    public void refundBet(){
        balance+=bet;
    }

    public void clearHand() {
        hand.clear();
    }

    
}
